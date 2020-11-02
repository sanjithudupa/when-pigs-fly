package engine;

public class GameEngine implements Runnable {

    public static final int TARGET_FPS = 75;
    public static final int TARGET_UPS = 30;

    private final Thread gameLoopThread;
    private final Window window;
    private final Timer timer;
    private final GameLogic gameLogic;

    public GameEngine(String windowTitle, int width, int height, boolean vsSync, GameLogic gameLogic) throws Exception {
        gameLoopThread = new Thread(this, "GAME_LOOP_THREAD");
        window = new Window(windowTitle, width, height, vsSync);
        this.gameLogic = gameLogic;
        this.timer = new Timer();
    }

    public void start() {
        String os = System.getProperty("os.name");
        System.out.println(os);
        if (os.contains("Mac OS X")) {
            gameLoopThread.run();
        } else {
            gameLoopThread.start();
        }
    }
    
    @Override
    public void run() {
        try {
            init();
            gameLoop();
        } catch (Exception excp) {
            excp.printStackTrace();
        } finally {
            cleanup();
        }
    }


    protected void init() throws Exception {
        window.init();
        timer.init();
        gameLogic.init(window);
    }

    protected void gameLoop() {
        float elapsedTime;
        float accumulator = 0f;
        float interval = 1f / TARGET_UPS;

        boolean running = true;
        while (running && !window.windowShouldClose()) {
            elapsedTime = timer.getElapsedTime();
            accumulator += elapsedTime;

            input();

            while (accumulator >= interval) {
                update(interval);
                accumulator -= interval;
            }

            render();

            if (!window.isvSync()) {
                sync();
            }
        }
    }

    private void sync() {
        float loopSlot = 1f / TARGET_FPS;
        double endTime = timer.getLastLoopTime() + loopSlot;
        while (timer.getTime() < endTime) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ie) {
            }
        }
    }

    protected void input() {
        gameLogic.input(window);
    }

    protected void update(float interval) {
        gameLogic.update(interval);
    }

    protected void render() {
        gameLogic.render(window);
        window.update();
    }

    protected void cleanup() {
        gameLogic.cleanup();                
    }

}