package web.impl.js.sound;

import client.Signlink;
import org.teavm.jso.JSObject;
import org.teavm.jso.typedarrays.Uint8Array;
import web.SoundEngine;
import web.impl.js.JSMethods;
import web.impl.js.sound.howl.Howl;
import web.impl.js.sound.howl.HowlConfig;
import web.impl.js.sound.midi.Timidity;

public class JSSoundEngine extends SoundEngine {

    private final Howl[] wavCache = new Howl[5];
    private final Uint8Array[] midiCache = new Uint8Array[5];
    private int currentWav = -1, currentMidi = -1;

    private Timidity timidity;

    @Override
    public void init() {
        System.out.println("Howl:" + Howl.isSupported());
        System.out.println("Timidity:" + Timidity.isSupported());
        if(Timidity.isSupported()){
            timidity = Timidity.get();
            timidity.on("playing", () ->{
                System.out.println("Playing track");
            });
            timidity.on("ended", () ->{
                System.out.println("Looping");
                timidity.play();
            });
        }
    }

    private String getWavUrl(){
        Uint8Array wrapped = Uint8Array.create(Signlink.savebuf.length);
        wrapped.set(Signlink.savebuf);
        JSObject blob = JSMethods.blobify(wrapped, "audio/wav");
        return JSMethods.createObjectUrl(blob);
    }

    public void update() {
        if(Signlink.waveplay && Howl.isSupported() && currentWav != Signlink.wavepos){
            currentWav = Signlink.wavepos;
            System.out.println("Playing:" + Signlink.savereq + " - " + Signlink.wavepos);
            Howl sound = getSound();
            sound.play();
        }
        if(Signlink.midiplay && Timidity.isSupported() && currentMidi != Signlink.midipos){
            currentMidi = Signlink.midipos;
            System.out.println("Playing:" + Signlink.midi);
            Uint8Array buf;
            if(Signlink.savebuf != null){
                buf = Uint8Array.create(Signlink.savebuf.length);
                buf.set(Signlink.savebuf);
                midiCache[Signlink.midipos] = buf;
            }else{
                buf = midiCache[Signlink.midipos];
            }
            timidity.load(buf);
            timidity.play();
        }
    }

    @Override
    public void pause() {
        if(Timidity.isSupported()){
            timidity.pause();
        }
    }

    @Override
    public void play() {
        if(Timidity.isSupported()){
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
        String url = getWavUrl();
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
