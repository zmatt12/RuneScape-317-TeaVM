package web.impl.js.sound;

import client.Signlink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.teavm.interop.Async;
import org.teavm.interop.AsyncCallback;
import org.teavm.jso.JSObject;
import org.teavm.jso.browser.TimerHandler;
import org.teavm.jso.browser.Window;
import org.teavm.jso.typedarrays.Int8Array;
import org.teavm.jso.typedarrays.Uint8Array;
import org.teavm.jso.webaudio.*;
import web.SoundEngine;
import web.impl.js.JSConfig;
import web.impl.js.JSMethods;
import web.impl.js.sound.howl.Howl;
import web.impl.js.sound.midi.Timidity;
import web.util.InputStreamPolyFill;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class JSSoundEngine extends SoundEngine implements TimerHandler {

    private static final int UPDATE_INTERVAL = 50;

    private final Logger logger = LoggerFactory.getLogger(JSSoundEngine.class);

    private GainNode gain;
    private AudioBufferSourceNode wav;
    private String currentWav = null;

    private AudioDestinationNode dest;
    private AudioContext context;

    private Timidity timidity;
    private String currentMidi = null;

    @Override
    public void init() {
        context = AudioContext.create();
        gain = context.createGain();
        dest = gain.cast();
        gain.connect(context.getDestination());

        logger.info("Howl:{} Timidity:{}", Howl.isSupported(), Timidity.isSupported());
        if (Timidity.isSupported()) {
            timidity = Timidity.create(JSConfig.get().timidity().getBaseUrl());
        }
        Window.setInterval(this, UPDATE_INTERVAL);
    }

    private String getWavUrl() {
        Uint8Array wrapped = Uint8Array.create(Signlink.savebuf.length);
        wrapped.set(Signlink.savebuf);
        JSObject blob = JSMethods.blobify(wrapped, "audio/wav");
        return JSMethods.createObjectUrl(blob);
    }

    public void update() {

    }

    @Override
    public void pause() {
//        if(Timidity.isSupported()){
//            timidity.pause();
//        }
//        if(wav != null){
//            wav.stop();
//        }
    }

    @Override
    public void play() {
//        if (Timidity.isSupported()) {
//            timidity.play();
//        }
//        if (wav != null) {
//            wav.play();
//        }
    }

    private float convertVol(int vol) {
        float res = vol + 1500;
        return res / 1500.0f;
    }

    private void updateMidi() {
        String midi = Signlink.midi;
        int vol = Signlink.midivol;
        float fVol = convertVol(vol);
        if (midi == null || !Timidity.isSupported()) {
            return;
        }
        if (midi.equals("voladjust")) {
            timidity.setVolume(fVol);
            return;
        }
        if (midi.equals("stop")) {
            timidity.pause();
            return;
        }
        if (!midi.equals(currentMidi)) {
            logger.info("Midi:{} play:{}", Signlink.midi, Signlink.midiplay);
            byte[] buffer = Signlink.savebuf;
            Uint8Array buf = Uint8Array.create(buffer.length);
            buf.set(buffer);
            timidity.load(buf);
            timidity.play();
            currentMidi = midi;
        }
    }

    private void updateWav() {
        String wave = Signlink.wave;
        if(Signlink.waveplay && wav != null){
            Signlink.waveplay = false;
            wav.stop();
            wav.disconnect();
            wav = createAndConnect(getFile(wave));
            return;
        }
        if (wave == null) {
            return;
        }
        if (wave.equals("voladjust") && wav != null) {
            gain.getGain().setValue(convertVol(Signlink.wavevol));
            //wav.setVolume(convertVol(Signlink.wavevol));
            return;
        }
        if (Signlink.wave.equals("stop") && wav != null) {
            wav.stop();
            return;
        }
        if(!wave.equals(currentWav) || wav == null) {
            logger.info("Wav:{} Play:{}", Signlink.wave, Signlink.waveplay);
            wav = createAndConnect(getFile(wave));
            currentWav = wave;
        }
    }


    private byte[] getFile(String path){
        File f = new File(path);
        try(FileInputStream fin = new FileInputStream(f)) {
            return InputStreamPolyFill.readAllBytes(fin);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    private AudioBufferSourceNode createAndConnect(byte[] data){
        AudioBufferSourceNode res = context.createBufferSource();
        Int8Array arr = Int8Array.create(data.length);
        arr.set(data);
        context.decodeAudioData(arr.getBuffer(), b ->{
            res.setBuffer(b);
            res.connect(dest);
            res.start();
        }, err ->{
            logger.info("Error:{}", err);
        });
        return res;
    }

    @Async
    private native AudioBuffer decode(byte[] data);
    private void decode(byte[] data, AsyncCallback<AudioBuffer> callback){
        Int8Array arr = Int8Array.create(data.length);
        arr.set(data);
        context.decodeAudioData(arr.getBuffer(), callback::complete, err ->{
            callback.error(new Exception(err.toString()));
        });
    }

    @Override
    public void onTimer() {
        try {
            updateMidi();
            updateWav();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
