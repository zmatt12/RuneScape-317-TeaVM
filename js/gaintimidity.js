var origTim = timidity;

class GainTimidity extends origTim{
    constructor(baseUrl='/') {
        super(baseUrl);

        //disconnect from the speaker
        this._node.disconnect();
        //setup gain stuff
        this._gain = this._audioContext.createGain();
        this._gain.connect(this._audioContext.destination);

        //reconnect using the gain
        this._node.connect(this._gain);
    }
}

timidity = GainTimidity