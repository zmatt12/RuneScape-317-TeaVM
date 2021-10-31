package client.textures;

public abstract class CroppedTexture extends BaseTexture{
    private int cropX;
    private int cropY;
    private int cropW;
    private int cropH;

    public int getCropX() {
        return cropX;
    }

    public void setCropX(int cropX) {
        this.cropX = cropX;
    }

    public int getCropY() {
        return cropY;
    }

    public void setCropY(int cropY) {
        this.cropY = cropY;
    }

    public int getCropW() {
        return cropW;
    }

    public void setCropW(int cropW) {
        this.cropW = cropW;
    }

    public int getCropH() {
        return cropH;
    }

    public void setCropH(int cropH) {
        this.cropH = cropH;
    }

    public void setCrop(int x, int y, int width, int height){
        this.cropX = x;
        this.cropY = y;
        this.cropW = width;
        this.cropH = height;
    }
}
