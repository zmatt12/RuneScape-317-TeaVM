package client;// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import web.IGraphics;
import web.IImage;

public class DrawArea {

	public final int[] pixels;
	public final int width;
	public final int height;
	public final IImage image;

	public DrawArea(int width, int height) {
		this.width = width;
		this.height = height;
		this.image = IImage.create(this.width, this.height, IImage.TYPE_INT_RGB);
		this.pixels = this.image.getBufferAsIntegers();
		bind();
	}

	public DrawArea(Image24 image) {
		this(image.width, image.height);
		image.blitOpaque(0, 0);
	}

	public void bind() {
		Draw2D.bind(pixels, width, height);
	}

	public void draw(IGraphics g, int x, int y) {
		g.drawImage(image, x, y, null);
	}

}
