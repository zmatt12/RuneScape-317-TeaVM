package ui;

import org.teavm.jso.browser.Window;

public abstract class SoundEngine {

    public static final int DEFAULT_UPDATE_INTERVAL = 10;
    private int updateInterval;

    public SoundEngine(){
        this(DEFAULT_UPDATE_INTERVAL);
    }

    public SoundEngine(int interval){
        this.updateInterval = interval;
    }

    public final void start(){
        init();
        WindowEngine.getDefault().schedule(this::run, 0);
    }

    protected void init(){

    }

    public final void run() {
        update();
        WindowEngine.getDefault().schedule(this::run, updateInterval);
    }

    protected abstract void update();
}
