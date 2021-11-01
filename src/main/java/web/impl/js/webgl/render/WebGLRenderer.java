package web.impl.js.webgl.render;

import client.textures.IndexedTexture;
import client.textures.RGBTexture;
import client.textures.Renderer;
import org.teavm.jso.typedarrays.Float32Array;
import org.teavm.jso.webgl.*;
import web.impl.js.JSImage;
import web.impl.js.webgl.M4;
import web.util.WebGL;

public class WebGLRenderer extends Renderer {

    private final WebGLRenderingContext gl;

    //rect program
    private final Float32Array arr = Float32Array.create(12);
    private WebGLProgram draw_rect;
    private WebGLBuffer rect_buffer;
    private WebGLUniformLocation rectColorLocation;
    int aVertexPosition;
    //drawImage program
    private WebGLProgram draw_img;
    private WebGLBuffer imagePositionBuffer, imageCoordBuffer;
    private int imagePositionLocation, imageCoordLocation;
    private WebGLUniformLocation imageTexLocation, imageMatrixLocation;

    public WebGLRenderer(WebGLRenderingContext gl){
        this.gl = gl;
        init();
    }

    @Override
    public RGBTexture createRGB(int width, int height) {
        return new WebGLRGBTexture(gl, width, height);
    }

    @Override
    public RGBTexture decodeRGB(byte[] data) {
        return null;
    }

    @Override
    public IndexedTexture createIndexed(int[] palette, int width, int height) {
        return null;
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
        imagePositionLocation = gl.getAttribLocation(draw_img, "a_position");
        imageCoordLocation = gl.getAttribLocation(draw_img, "a_texcoord");

        // lookup uniforms
        imageMatrixLocation = gl.getUniformLocation(draw_img, "u_matrix");
        imageTexLocation = gl.getUniformLocation(draw_img, "u_texture");
    }

    public void drawImage(JSImage img, int x, int y, int viewWidth, int viewHeight){
        WebGLTexture texture = img.getTexture(gl);

        gl.useProgram(draw_img);

        gl.bindTexture(gl.TEXTURE_2D, texture);

        // Setup the attributes to pull data from our buffers
        gl.bindBuffer(gl.ARRAY_BUFFER, imagePositionBuffer);
        gl.enableVertexAttribArray(imagePositionLocation);
        gl.vertexAttribPointer(imagePositionLocation, 2, gl.FLOAT, false, 0, 0);

        gl.bindBuffer(gl.ARRAY_BUFFER, imageCoordBuffer);
        gl.enableVertexAttribArray(imageCoordLocation);
        gl.vertexAttribPointer(imageCoordLocation, 2, gl.FLOAT, false, 0, 0);
        // this matrix will convert from pixels to clip space
        M4 m4 = M4.get();
        float[] matrix = m4.orthographic(0, viewWidth, viewHeight, 0, -1, 1);

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

    public void drawRect(float[] color, int x, int y, int width, int height, int destWidth, int destHeight, boolean fill) {
        x -= destWidth / 2;
        y -= destHeight / 2;

        float canvasWidth = destWidth / 2f;
        float canvasHeight = destHeight / 2f;

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
