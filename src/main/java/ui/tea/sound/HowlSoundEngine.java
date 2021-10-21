package ui.tea.sound;

import client.Signlink;
import org.teavm.jso.JSObject;
import org.teavm.jso.typedarrays.Uint8Array;
import ui.SoundEngine;
import ui.tea.JSMethods;

public class HowlSoundEngine extends SoundEngine {

    private Howl current = null;

    public HowlSoundEngine(){
        this(DEFAULT_UPDATE_INTERVAL);
    }

    public HowlSoundEngine(int interval){
        super(interval);
    }

    @Override
    protected void update(){
        if(current == null){
            checkForAudio();
        }else{
            if(current.playing()){
                return;
            }else{
                //TODO
            }
        }
    }

    private void checkForAudio() {
        if(Signlink.waveplay){
            Signlink.waveplay = false;
            System.out.println("Playing:" + Signlink.savereq + " - " + Signlink.wavepos);
            Uint8Array wrapped = Uint8Array.create(Signlink.savebuf.length);
            wrapped.set(Signlink.savebuf);
            JSObject blob = JSMethods.blobify(wrapped, "audio/wav");
            HowlConfig config = HowlConfig.create(JSMethods.createObjectUrl(blob));
            config.setFormat("wav");
            config.setOnPlayError(e ->{
                System.out.println("guess I gotta do error handling");
            });
            config.setOnload(e ->{
                System.out.println("loaded!");
                current.play();
            });
            config.setOnEnd(e ->{
                System.out.println("Finished");
                current = null;
            });
            current = Howl.create(config);
        }
    }
}
