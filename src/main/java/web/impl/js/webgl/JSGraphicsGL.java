package web.impl.js.webgl;

import org.teavm.jso.dom.html.HTMLCanvasElement;
import org.teavm.jso.typedarrays.Float32Array;
import org.teavm.jso.webgl.*;
import web.IFont;
import web.IGraphics;
import web.IImage;
import web.impl.js.JSImage;
import web.util.Color;
import web.util.WebGL;

public class JSGraphicsGL implements IGraphics {

    private final HTMLCanvasElement canvas;
    private final WebGLRenderingContext gl;
    private final Float32Array arr = Float32Array.create(12);
    int aVertexPosition;
    private float[] color = Color.black.asFloatArray();
    //rect program
    private WebGLProgram draw_rect;
    private WebGLBuffer rect_buffer;
    private WebGLUniformLocation rectColorLocation;
    //drawImage program
    private WebGLProgram draw_img;
    private WebGLBuffer imagePositionBuffer, imageCoordBuffer;
    private int positionLocation, texcoordLocation;
    private WebGLUniformLocation imageTexLocation, imageMatrixLocation;

    public JSGraphicsGL(HTMLCanvasElement canvas, WebGLRenderingContext context) {
        this.canvas = canvas;
        this.gl = context;
        init();
    }

    private void init() {
        draw_rect = WebGL.loadAndCompile(gl, "draw_rect");
        draw_img = WebGL.loadAndCompile(gl, "draw_img");

        rect_buffer = gl.createBuffer();
        rectColorLocation = gl.getUniformLocation(draw_rect, "uColor");
        aVertexPosition = gl.getAttribLocation(draw_rect, "aVertexPosition");

        imagePositionBuffer = gl.createBuffer();
        gl.bindBuffer(gl.ARRAY_BUFFER, imagePositionBuffer);
        Float32Array texturePosition = Float32Array.create(12);
        texturePosition.set(new float[]{
                0, 0,
                0, 1,
                1, 0,
                1, 0,
                0, 1,
                1, 1,
        });
        gl.bufferData(gl.ARRAY_BUFFER, texturePosition, gl.STATIC_DRAW);
        imageCoordBuffer = gl.createBuffer();
        gl.bindBuffer(gl.ARRAY_BUFFER, imageCoordBuffer);
        Float32Array texCoords = Float32Array.create(12);
        texCoords.set(new float[]{
                0, 0,
                0, 1,
                1, 0,
                1, 0,
                0, 1,
                1, 1
        });
        gl.bufferData(gl.ARRAY_BUFFER, texCoords, gl.STATIC_DRAW);

        // look up where the vertex data needs to go.
        positionLocation = gl.getAttribLocation(draw_img, "a_position");
        texcoordLocation = gl.getAttribLocation(draw_img, "a_texcoord");

        // lookup uniforms
        imageMatrixLocation = gl.getUniformLocation(draw_img, "u_matrix");
        imageTexLocation = gl.getUniformLocation(draw_img, "u_texture");
    }

    @Override
    public void setColor(Color color) {
        this.color = color.asFloatArray();
    }

    @Override
    public void fillRect(int x, int y, int width, int height) {
        drawRect(x, y, width, height, true);
    }

    @Override
    public void drawRect(int x, int y, int width, int height) {
        drawRect(x, y, width, height, false);
    }

    @Override
    public void setFont(IFont font) {

    }

    @Override
    public void drawString(String str, int x, int y) {
        //TODO impl, or at least figure out how I want to implement it
    }

    @Override
    public void drawImage(IImage _img, int x, int y, Object observer) {
        JSImage img = (JSImage) _img;
        WebGLTexture texture = img.getTexture(gl);

        gl.useProgram(draw_img);

        gl.bindTexture(gl.TEXTURE_2D, texture);

        // Setup the attributes to pull data from our buffers
        gl.bindBuffer(gl.ARRAY_BUFFER, imagePositionBuffer);
        gl.enableVertexAttribArray(positionLocation);
        gl.vertexAttribPointer(positionLocation, 2, gl.FLOAT, false, 0, 0);

        gl.bindBuffer(gl.ARRAY_BUFFER, imageCoordBuffer);
        gl.enableVertexAttribArray(texcoordLocation);
        gl.vertexAttribPointer(texcoordLocation, 2, gl.FLOAT, false, 0, 0);
        // this matrix will convert from pixels to clip space
        M4 m4 = M4.get();
        float[] matrix = m4.orthographic(0, canvas.getWidth(), canvas.getHeight(), 0, -1, 1);

        // this matrix will translate our quad to dstX, dstY
        matrix = m4.translate(matrix, x, y, 0);
        // this matrix will scale our 1 unit quad
        // from 1 unit to texWidth, texHeight units
        matrix = m4.scale(matrix, img.getWidth(), img.getHeight(), 1);
        // Set the matrix.
        gl.uniformMatrix4fv(imageMatrixLocation, false, matrix);

        // Tell the shader to get the texture from texture unit 0
        gl.uniform1i(imageTexLocation, 0);

        // draw the quad (2 triangles, 6 vertices)
        gl.drawArrays(gl.TRIANGLES, 0, 6);

    }

    private void drawRect(int x, int y, int width, int height, boolean fill) {
        x -= canvas.getWidth() / 2;
        y -= canvas.getHeight() / 2;

        float canvasWidth = canvas.getWidth() / 2f;
        float canvasHeight = canvas.getHeight() / 2f;

        float startX = x / canvasWidth;
        float startY = (y / canvasHeight);
        float endX = (x + width) / canvasWidth;
        float endY = ((y + height) / canvasHeight);

        if (fill) {
            arr.set(new float[]{
                    startX, startY,
                    startX, endY,
                    endX, startY, // Triangle 1
                    endX, startY,
                    startX, endY,
                    endX, endY // Triangle 2
            });
        } else {
            arr.set(new float[]{
                    startX, startY,
                    endX, startY,
                    endX, endY,
                    startX, endY
            });
        }
        gl.bindBuffer(gl.ARRAY_BUFFER, rect_buffer);
        gl.bufferData(gl.ARRAY_BUFFER, arr, gl.STATIC_DRAW);

        gl.useProgram(draw_rect);
        gl.uniform4fv(rectColorLocation, color);

        gl.enableVertexAttribArray(aVertexPosition);
        gl.vertexAttribPointer(aVertexPosition, 2, gl.FLOAT, false, 0, 0);
        gl.drawArrays(fill ? gl.TRIANGLES : gl.LINE_LOOP, 0, fill ? 6 : 4);
    }
}
