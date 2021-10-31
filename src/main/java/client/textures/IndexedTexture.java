package client.textures;

public abstract class IndexedTexture extends CroppedTexture{

    public abstract void shrink();

    public abstract void crop();

    public abstract void flipH();

    public abstract void flipV();

    public abstract byte[] getIndices();

    public abstract void setIndices(byte[] indices);

    public abstract int[] getPalette();
}
