package engine;

import org.joml.Vector3f;

public class Terrain {

    private final Entity[] entities;
    private final HeightMap heightMapMesh;

    private final int terrainSize;
    private final int verticesPerCol;
    private final int verticesPerRow;

    private final Box2D[][] boundingBoxes;

    // private final boolean[][] aboveHalf;

    public Terrain(int terrainSize, float scale, float minY, float maxY, String heightMap, String textureFile, int textInc) throws Exception {
        entities = new Entity[terrainSize * terrainSize];
        this.heightMapMesh = new HeightMap(minY, maxY, heightMap, textureFile, textInc);

        this.terrainSize = terrainSize;
        this.verticesPerCol = this.heightMapMesh.verticesPerCol;
        this.verticesPerRow = this.heightMapMesh.verticesPerRow;
        // this.aboveHalf = this.heightMapMesh.aboveHalf;
        
        boundingBoxes = new Box2D[terrainSize][terrainSize];

        for (int row = 0; row < terrainSize; row++) {
            for (int col = 0; col < terrainSize; col++) {
                float xDisplacement = (col - ((float) terrainSize - 1) / (float) 2) * scale * HeightMap.getXLength();
                float zDisplacement = (row - ((float) terrainSize - 1) / (float) 2) * scale * HeightMap.getZLength();

                Entity terrainBlock = new Entity(heightMapMesh.getMesh());
                terrainBlock.setScale(scale);
                terrainBlock.setPosition(xDisplacement, 0, zDisplacement);
                entities[row * terrainSize + col] = terrainBlock;

                boundingBoxes[row][col] = getBoundingBox(terrainBlock);
            }
        }
    }

    private Box2D getBoundingBox(Entity terrainBlock) {
        float scale = terrainBlock.getScale();
        Vector3f position = terrainBlock.getPosition();
    
        float topLeftX = HeightMap.STARTX * scale + position.x;
        float topLeftZ = HeightMap.STARTZ * scale + position.z;
        float width = Math.abs(HeightMap.STARTX * 2) * scale;
        float height = Math.abs(HeightMap.STARTZ * 2) * scale;
        Box2D boundingBox = new Box2D(topLeftX, topLeftZ, width, height);
        return boundingBox;
    }

    public Entity[] getEntities() {
        return entities;
    }

    public float getHeight(Vector3f position) {
        float result = Float.MIN_VALUE;
        // For each terrain block we get the bounding box, translate it to view coodinates
        // and check if the position is contained in that bounding box
        Box2D boundingBox = null;
        boolean found = false;
        Entity terrainBlock = null;
        for (int row = 0; row < terrainSize && !found; row++) {
            for (int col = 0; col < terrainSize && !found; col++) {
                terrainBlock = entities[row * terrainSize + col];
                boundingBox = boundingBoxes[row][col];
                found = boundingBox.contains(position.x, position.z);
            }
        }
    
        // If we have found a terrain block that contains the position we need
        // to calculate the height of the terrain on that position
        if (found) {
            Vector3f[] triangle = getTriangle(position, boundingBox, terrainBlock);
            result = interpolateHeight(triangle[0], triangle[1], triangle[2], position.x, position.z);
        }
    
        return result;
    }

    protected Vector3f[] getTriangle(Vector3f position, Box2D boundingBox, Entity terrainBlock) {
        // Get the column and row of the heightmap associated to the current position
        float cellWidth = boundingBox.width / (float) verticesPerCol;
        float cellHeight = boundingBox.height / (float) verticesPerRow;
        int col = (int) ((position.x - boundingBox.x) / cellWidth);
        int row = (int) ((position.z - boundingBox.y) / cellHeight);

        Vector3f[] triangle = new Vector3f[3];
        triangle[1] = new Vector3f(
                boundingBox.x + col * cellWidth,
                getWorldHeight(row + 1, col, terrainBlock),
                boundingBox.y + (row + 1) * cellHeight);
        triangle[2] = new Vector3f(
                boundingBox.x + (col + 1) * cellWidth,
                getWorldHeight(row, col + 1, terrainBlock),
                boundingBox.y + row * cellHeight);
        if (position.z < getDiagonalZCoord(triangle[1].x, triangle[1].z, triangle[2].x, triangle[2].z, position.x)) {
            triangle[0] = new Vector3f(
                    boundingBox.x + col * cellWidth,
                    getWorldHeight(row, col, terrainBlock),
                    boundingBox.y + row * cellHeight);
        } else {
            triangle[0] = new Vector3f(
                    boundingBox.x + (col + 1) * cellWidth,
                    getWorldHeight(row + 2, col + 1, terrainBlock),
                    boundingBox.y + (row + 1) * cellHeight);
        }

        return triangle;
    }

    protected float getDiagonalZCoord(float x1, float z1, float x2, float z2, float x) {
        float z = ((z1 - z2) / (x1 - x2)) * (x - x1) + z1;
        return z;
    }

    protected float getWorldHeight(int row, int col, Entity gameItem) {
        float y = heightMapMesh.getHeight(row, col);
        return y * gameItem.getScale() + gameItem.getPosition().y;
    }

    protected float interpolateHeight(Vector3f pA, Vector3f pB, Vector3f pC, float x, float z) {
        // Plane equation ax+by+cz+d=0
        float a = (pB.y - pA.y) * (pC.z - pA.z) - (pC.y - pA.y) * (pB.z - pA.z);
        float b = (pB.z - pA.z) * (pC.x - pA.x) - (pC.z - pA.z) * (pB.x - pA.x);
        float c = (pB.x - pA.x) * (pC.y - pA.y) - (pC.x - pA.x) * (pB.y - pA.y);
        float d = -(a * pA.x + b * pA.y + c * pA.z);
        // y = (-d -ax -cz) / b
        float y = (-d - a * x - c * z) / b;
        return y;
    }

    static class Box2D {

        public float x;

        public float y;

        public float width;

        public float height;

        public Box2D(float x, float y, float width, float height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        public boolean contains(float x2, float y2) {
            return x2 >= x
                    && y2 >= y
                    && x2 < x + width
                    && y2 < y + height;
        }
    }
}
