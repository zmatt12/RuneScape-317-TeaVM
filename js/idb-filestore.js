window.indexedDB = window.indexedDB || window.mozIndexedDB || window.webkitIndexedDB || window.msIndexedDB;
window.IDBTransaction = window.IDBTransaction || window.webkitIDBTransaction || window.msIDBTransaction || {READ_WRITE: "readwrite"};
window.IDBKeyRange = window.IDBKeyRange || window.webkitIDBKeyRange || window.msIDBKeyRange;

class IDBFileStore {
    #dbName;
    #storeName;
    #version;
    #request;
    #db;
    constructor(dbName = "Cache", storeName = "Cache"){
        this.#dbName = dbName;
        this.#storeName = storeName;
        this.#version = 1; //manually increment when update the store
    }

    open(onsuccess, onerror){
        if(!onsuccess){
            throw "Missing onsuccess handler";
        }
        if(!onerror){
            throw "Missing onerror handler";
        }
        if(!this.#dbName || !this.#storeName){
            throw "Missing name(s)";
        }
        if(this.#db){
            throw "Already open!";
        }
        var store = this;
        let request = window.indexedDB.open(this.#dbName, this.#version);
        request.onerror = onerror;
        request.onupgradeneeded = function(event) {
          var db = event.target.result;
          if(event.oldVersion < 1){ // new DB
              var objectStore = db.createObjectStore(store.#storeName, {keyPath: ["storeId", "fileId"]});
          }
        };
        request.onsuccess = function(event){
            store.#db = event.target.result;
            onsuccess();
        };
    }

    #validate(storeId, fileId){
        if(!this.#db){
            throw "Not open!";
        }
        if(typeof fileId !== 'number'){
            throw "fileId must be a number!";
        }
        if(typeof storeId !== 'number'){
           throw "storeId must be a number!";
        }
    }

    write(storeId, fileId, data, onsuccess, onerror){
        this.#validate(storeId, fileId);
        if(!(data instanceof Int8Array)) {
            throw "data must be an Int8Array!";
        }
        var filestore = this;

        let store = filestore.#db.transaction(filestore.#storeName, "readwrite").objectStore(filestore.#storeName);
        let request = store.put({storeId:storeId, fileId: fileId, data: data});
        request.onerror = onerror;
        request.onsuccess = function(event){
            onsuccess(request.result);
        }
    }

    read(storeId, fileId, onsuccess, onerror) {
        this.#validate(storeId, fileId);
        var filestore = this;
        let store = filestore.#db.transaction(filestore.#storeName, "readonly").objectStore(filestore.#storeName);
        let request = store.get([storeId, fileId]);
        request.onerror = onerror;
        request.onsuccess = function(event){
            if(request.result){
                onsuccess(request.result.data);
            }else{
                onsuccess(null);
            }
        };
    }

    static isSupported(){
        return typeof window.indexedDB !== 'undefined';
    }
}