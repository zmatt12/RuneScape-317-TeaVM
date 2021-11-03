WebClient = function() {
    function WebClient(canvas){
        this.canvas = canvas;
        this.context = canvas.getContext("2d");
    }
    WebClient.prototype.load = function() {
        TeaVM.wasm.run("target/javascript/classes.wasm", {
            installImports: installImports.bind(this)
        });
    };

    function installImports(o){
        var canvas = this.canvas;
        var canvasId = 0;
        o.component = {
            create: function() {
                return canvasId;
            },
            getHeight: function(id) {
                if(id != canvasId){
                    console.log(`Bad id from assembly: ${id}`);
                    return 0;
                }
                return canvas.height;
            },
            getWidth: function(id) {
                if(id != canvasId){
                    console.log(`Bad id from assembly: ${id}`);
                    return 0;
                }
                return canvas.width;
            },
            setSize: function(id, width, height) {
                if(id != canvasId){
                    console.log(`Bad id from assembly: ${id}`);
                    return 0;
                }
                canvas.width = width;
                canvas.height = height;
                //TODO fire resize event
            },
            requestFocus: function(id) {
                if(id != canvasId){
                    console.log(`Bad id from assembly: ${id}`);
                    return 0;
                }
                canvas.focus();
            }
        },
        o.platform = {
            getCodebase: function() {
                return window.location;
            }
        }
    }
    return WebClient;
}();