//BrowserFS.install(window);

BrowserFS.configure({
    fs: "MountableFileSystem",
    options: {
        "/tmp": {
            fs: "InMemory"
        },
//        "/tmp": {
//            fs: "LocalStorage"
//        }
        }
}, function(e) {
    if (e) {
        // An error occurred.
        throw e;
    }
});