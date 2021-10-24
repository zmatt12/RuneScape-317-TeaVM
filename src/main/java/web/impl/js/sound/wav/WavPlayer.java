package web.impl.js.sound.wav;

import netscape.javascript.JSObject;
import org.teavm.interop.Async;
import org.teavm.interop.AsyncCallback;
import org.teavm.jso.typedarrays.Int8Array;
import org.teavm.jso.webaudio.AudioBuffer;
import org.teavm.jso.webaudio.AudioBufferSourceNode;
import org.teavm.jso.webaudio.AudioContext;
import org.teavm.jso.webaudio.GainNode;

public class WavPlayer{

    private AudioContext context;
    private AudioBufferSourceNode node;
    private GainNode gain;

    private WavPlayer(AudioContext context){
        this.context = context;
        this.node = context.createBufferSource();
        this.gain = context.createGain();
    }

    public float getVolume(){
        return gain.getGain().getValue();
    }

    public void setVolume(float volume){
        gain.getGain().setValue(volume);
    }

    public void connect(){
        this.node.connect(gain);
        this.gain.connect(context.getDestination());
    }

    public void disconnect(){
        this.node.disconnect();
        this.gain.disconnect();
    }

    public static WavPlayer load(AudioContext context, byte[] data){
        WavPlayer res = new WavPlayer(context);
        res.node.setBuffer(decode(context, data));
        return res;
    }

    @Async
    private static native AudioBuffer decode(AudioContext context, byte[] data);
    private static void decode(AudioContext context, byte[] data, AsyncCallback<AudioBuffer> callback){
        Int8Array arr = Int8Array.create(data.length);
        arr.set(data);
        context.decodeAudioData(arr.getBuffer(), callback::complete, err ->{
            callback.error(new Exception(err.toString()));
        });
    }

    public void stop() {
        node.stop();
    }

    public void play() {
        node.start();
    }
}
