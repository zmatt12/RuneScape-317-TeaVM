package ui.tea.sound;

import client.Signlink;
import org.teavm.jso.JSObject;
import org.teavm.jso.typedarrays.Uint8Array;
import ui.SoundEngine;
import ui.tea.JSMethods;
import ui.tea.sound.howl.Howl;
import ui.tea.sound.howl.HowlConfig;
import ui.tea.sound.midi.MIDIPlayer;

public class JSSoundEngine extends SoundEngine {

    private final Howl[] cache = new Howl[5];

    public JSSoundEngine(){
        this(DEFAULT_UPDATE_INTERVAL);
    }

    public JSSoundEngine(int interval){
        super(interval);
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
        if(Signlink.midiplay && MIDIPlayer.isSupported()){
            Signlink.midiplay = false;
            System.err.println("TODO:Midi");
        }
    }

    private Howl getSound(){
        Howl cached = cache[Signlink.wavepos];
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
        return cache[Signlink.wavepos] = sound;
    }
}
