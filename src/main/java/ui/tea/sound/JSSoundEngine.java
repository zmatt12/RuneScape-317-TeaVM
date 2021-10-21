package ui.tea.sound;

import client.Signlink;
import org.teavm.jso.JSObject;
import org.teavm.jso.typedarrays.Uint8Array;
import ui.SoundEngine;
import ui.tea.JSMethods;
import ui.tea.sound.howl.Howl;
import ui.tea.sound.howl.HowlConfig;
import ui.tea.sound.midi.Timidity;

public class JSSoundEngine extends SoundEngine {

    private final Howl[] wavCache = new Howl[5];
    private final String[] midiCache = new String[5];

    private Timidity timidity;

    public JSSoundEngine(){
        this(DEFAULT_UPDATE_INTERVAL);
    }

    public JSSoundEngine(int interval){
        super(interval);
    }

    @Override
    protected void init() {
        System.out.println("Howl:" + Howl.isSupported());
        System.out.println("Timidity:" + Timidity.isSupported());
        if(Timidity.isSupported()){
            timidity = Timidity.get();
        }
    }

    @Override
    protected void update(){
        checkForAudio();
    }

    private String getAudioUrl(boolean wav){
        Uint8Array wrapped = Uint8Array.create(Signlink.savebuf.length);
        wrapped.set(Signlink.savebuf);
        JSObject blob = JSMethods.blobify(wrapped, wav ? "audio/wav" : "audio/midi");
        return JSMethods.createObjectUrl(blob);
    }

    private void checkForAudio() {
        if(Signlink.waveplay && Howl.isSupported()){
            Signlink.waveplay = false;
            System.out.println("Playing:" + Signlink.savereq + " - " + Signlink.wavepos);
            Howl sound = getSound();
            sound.play();
        }
        if(Signlink.midiplay && Timidity.isSupported()){
            Signlink.midiplay = false;
            System.out.println("Playing:" + Signlink.midi);
            String url;
            if(Signlink.savebuf != null){
                url = getAudioUrl(false);
                int index = Signlink.midipos;
                if(midiCache[index] != null){
                    JSMethods.revokeObjectURL(midiCache[index]);
                }
                midiCache[index] = url;
            }else{
                url = midiCache[Signlink.midipos];
            }
            timidity.load(url);
            timidity.play();
        }
    }

    private Howl getSound(){
        Howl cached = wavCache[Signlink.wavepos];
        if(Signlink.savebuf == null){
            return cached;
        }
        if(cached != null){
            System.out.println("unload");
            cached.stop();
            cached.unload();
        }
        String url = getAudioUrl(true);
        HowlConfig config = HowlConfig.create(url);
        config.setFormat("wav");
        config.setOnStop(event -> {
            System.out.println("Cleaning up..");
            JSMethods.revokeObjectURL(url);
        });
        Howl sound = Howl.create(config);
        return wavCache[Signlink.wavepos] = sound;
    }
}
