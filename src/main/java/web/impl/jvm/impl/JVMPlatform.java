package web.impl.jvm.impl;

import client.Entity;
import client.Game;
import client.Model;
import web.*;
import web.impl.jvm.event.AWTEventAdapter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Hashtable;

public class JVMPlatform extends Platform {

    private static final JVMAllocator alloc = new JVMAllocator();
    private static final JVMSoundEngine sound = new JVMSoundEngine();
    private static final JVMFileManager files = new JVMFileManager();

    @Override
    public IComponent createComponent() {
        Canvas res = new Canvas();
        IComponent component = new JVMComponent(res);
        AWTEventAdapter adapter = new AWTEventAdapter(component);
        res.addFocusListener(adapter);
        res.addKeyListener(adapter);
        res.addMouseListener(adapter);
        res.addMouseMotionListener(adapter);
        res.addMouseWheelListener(adapter);
        return component;
    }

    @Override
    public IFont getFont(String name, int style, int size) {
        return new JVMFont(name, style, size);
    }

    @Override
    public IImage createImage(byte[] data) {
        try {
            BufferedImage br = ImageIO.read(new ByteArrayInputStream(data));
            if (br.getType() != BufferedImage.TYPE_INT_RGB) {
                BufferedImage tmp = new BufferedImage(br.getWidth(), br.getHeight(), BufferedImage.TYPE_INT_RGB);
                tmp.getGraphics().drawImage(br, 0, 0, null);
                br = tmp;
            }
            return new JVMImage(br);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public IImage createImage(int width, int height, int type) {
        int t;
        switch (type) {
            case IImage.TYPE_INT_RGB:
                t = BufferedImage.TYPE_INT_RGB;
                break;
            case IImage.TYPE_INT_ARGB:
                t = BufferedImage.TYPE_INT_ARGB;
                break;
            default:
                throw new RuntimeException("Bad type:" + type);
        }
        return new JVMImage(new BufferedImage(width, height, t));
    }

    @Override
    public IImage<?> createImage(int width, int height, int[] pixels) {
        DataBufferInt buffer = new DataBufferInt(pixels, pixels.length);
        DirectColorModel model = new DirectColorModel(32, 0xFF0000, 0x00FF00, 0x0000FF);
        WritableRaster raster = Raster.createWritableRaster(model.createCompatibleSampleModel(width, height), buffer, null);
        BufferedImage img = new BufferedImage(model, raster, false, new Hashtable<>());
        return new JVMImage(img);
    }

    @Override
    public ISocket openSocket(String server, int port) throws IOException {
        return new JVMSocket(new Socket(InetAddress.getByName(server), port));
    }

    @Override
    public IAllocator alloc() {
        return alloc;
    }

    @Override
    public SoundEngine sound() {
        return sound;
    }

    @Override
    public IFileManager files() {
        return files;
    }

    @Override
    public String getCodeBase() {
        return "http://world1.deltascape.net:4380/";
    }

    @Override
    public Model getModel(Entity entity) {
        return null;
    }


}
