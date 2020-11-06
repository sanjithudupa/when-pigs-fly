package engine;

public class Timer {

    private double lastLoopTime;
    private double startTime;
    
    public void init() {
        lastLoopTime = getTime();
        startTime = getTime();
    }

    public double getTime() {
        return System.nanoTime() / 1_000_000_000.0;
    }

    public float getElapsedTime() {
        double time = getTime();
        float elapsedTime = (float) (time - lastLoopTime);
        lastLoopTime = time;
        return elapsedTime;
    }

    public float getTimePassed(){
        return (float) (getTime() - startTime);
    }

    public double getLastLoopTime() {
        return lastLoopTime;
    }
}