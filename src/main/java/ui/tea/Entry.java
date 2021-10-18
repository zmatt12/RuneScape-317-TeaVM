package ui.tea;


public class Entry {

    //Don't actually try to run this, you'd be in for a bad time.
    public static void main(String[] args) throws Exception{
//        if(IDBFactory.isSupported()) {
//            IDBFactory factory = IDBFactory.getInstance();
//            IDBDatabase db= factory.open(args[0], 1).getResult();
//                    VirtualFileSystemProvider.setInstance(new VirtualIndexedFileSystem(db));
//        }else{
//            System.err.println("IDB not supported, you'll be downloading the whole game every start!");
//        }
        TeaEngine.init(args[0]);
        client.Game.main(new String[]{"0", "0", "highmem", "free", "10"});
    }
}
