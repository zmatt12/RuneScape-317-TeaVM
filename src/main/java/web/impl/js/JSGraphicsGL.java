package web.impl.js;

import org.teavm.jso.dom.html.HTMLCanvasElement;
import org.teavm.jso.typedarrays.Float32Array;
import org.teavm.jso.typedarrays.Uint16Array;
import org.teavm.jso.webgl.*;
import web.IFont;
import web.IGraphics;
import web.IImage;
import web.util.Color;

public class JSGraphicsGL implements IGraphics {
    private final HTMLCanvasElement canvas;
    private final WebGLRenderingContext gl;
    private float[] color = Color.yellow.asFloatArray();
    private WebGLProgram fill_rect;
    private WebGLProgram draw_rect;

    private WebGLBuffer rect_buffer, indBuf;

    private final Float32Array arr = Float32Array.create(12);
    private final Uint16Array indices = Uint16Array.create(4);

    public JSGraphicsGL(HTMLCanvasElement canvas, WebGLRenderingContext context) {
        this.canvas = canvas;
        this.gl = context;
        init();
    }

    private void init(){
        WebGLShader v_shader = createAndCompile(gl.VERTEX_SHADER,"attribute vec2 aVertexPosition;\n" +
                "\n" +
                "void main() {\n" +
                "gl_Position = vec4(aVertexPosition, 0.0, 1.0);\n" +
                "}");

        WebGLShader f_color_shader = createAndCompile(gl.FRAGMENT_SHADER, "#ifdef GL_ES\n" +
                "precision highp float;\n" +
                "#endif\n" +
                "\n" +
                "uniform vec4 uColor;\n" +
                "\n" +
                "void main() {\n" +
                "gl_FragColor = uColor;\n" +
                "}");

        fill_rect = gl.createProgram();
        gl.attachShader(fill_rect, v_shader);
        gl.attachShader(fill_rect, f_color_shader);
        gl.linkProgram(fill_rect);

        draw_rect = gl.createProgram();
        gl.attachShader(draw_rect, f_color_shader);
        gl.linkProgram(draw_rect);

        indices.set(new short[]{0,1,2,3});

        rect_buffer = gl.createBuffer();
        indBuf = gl.createBuffer();
    }

    private WebGLShader createAndCompile(int type, String source){
        WebGLShader f_shader = gl.createShader(type);
        gl.shaderSource(f_shader, source);
        gl.compileShader(f_shader);
        return f_shader;
    }

    @Override
    public void setColor(Color color) {
        this.color = color.asFloatArray();
    }

    @Override
    public void fillRect(int x, int y, int width, int height) {
        x -= canvas.getWidth() / 2;
        y -= canvas.getHeight() / 2;

        float canvasWidth = canvas.getWidth() / 2f;
        float canvasHeight = canvas.getHeight() / 2f;

        float startX = x / canvasWidth;
        float startY = (y / canvasHeight);
        float endX =  (x + width) / canvasWidth;
        float endY =  ((y + height) / canvasHeight);

        arr.set(new float[]{
                startX, startY,
                startX, endY,
                endX,startY, // Triangle 1
                endX, startY,
                startX,endY,
                endX,endY // Triangle 2
        });

        WebGLBuffer vbuffer = gl.createBuffer();
        gl.bindBuffer(gl.ARRAY_BUFFER, vbuffer);
        gl.bufferData(gl.ARRAY_BUFFER, arr, gl.STATIC_DRAW);

        gl.useProgram(fill_rect);
        WebGLUniformLocation uColor = gl.getUniformLocation(fill_rect, "uColor");
        gl.uniform4fv(uColor, color);

        int aVertexPosition = gl.getAttribLocation(fill_rect, "aVertexPosition");
        gl.enableVertexAttribArray(aVertexPosition);
        gl.vertexAttribPointer(aVertexPosition, 2, gl.FLOAT, false, 0, 0);
        gl.drawArrays(gl.TRIANGLES, 0, 6);
    }

    @Override
    public void drawRect(int x, int y, int width, int height) {
        x -= canvas.getWidth() / 2;
        y -= canvas.getHeight() / 2;

        float canvasWidth = canvas.getWidth() / 2f;
        float canvasHeight = canvas.getHeight() / 2f;

        float startX = x / canvasWidth;
        float startY = (y / canvasHeight);
        float endX =  (x + width) / canvasWidth;
        float endY =  ((y + height) / canvasHeight);

        arr.set(new float[]{
                startX, startY,
                endX, startY,
                endX,endY,
                startX, endY
        });
        gl.useProgram(draw_rect);
        WebGLUniformLocation uColor = gl.getUniformLocation(fill_rect, "uColor");
        gl.uniform4fv(uColor, color);

        gl.bindBuffer(gl.ARRAY_BUFFER, rect_buffer);
        gl.bufferData(gl.ARRAY_BUFFER, arr, gl.STATIC_DRAW);
        gl.vertexAttribPointer(0, 2, gl.FLOAT, false, 0, 0);
        gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, indBuf);
        gl.bufferData(gl.ELEMENT_ARRAY_BUFFER, indices, gl.STATIC_DRAW);

        gl.drawArrays(gl.LINE_LOOP, 0, 4);
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
        //TODO implement
    }
}
