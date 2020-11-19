package engine;

public class Terrain {
    private final Entity[] entities;

    public Terrain(int blocksPerRow, float scale, float minY, float maxY, String heightMap, String textureFile, int textInc) throws Exception {
        entities = new Entity[blocksPerRow * blocksPerRow];
        HeightMap heightMapMesh = new HeightMap(minY, maxY, heightMap, textureFile, textInc);
        for (int row = 0; row < blocksPerRow; row++) {
            for (int col = 0; col < blocksPerRow; col++) {
                float xDisplacement = (col - ((float) blocksPerRow - 1) / (float) 2) * scale * HeightMap.getXLength();
                float zDisplacement = (row - ((float) blocksPerRow - 1) / (float) 2) * scale * HeightMap.getZLength();

                Entity terrainBlock = new Entity(heightMapMesh.getMesh());
                terrainBlock.setScale(scale);
                terrainBlock.setPosition(xDisplacement, 0, zDisplacement);
                entities[row * blocksPerRow + col] = terrainBlock;
            }
        }
    }

    public Entity[] getEntities() {
        return entities;
    }
}
