package web.impl.js;

import org.teavm.jso.dom.html.HTMLCanvasElement;
import org.teavm.jso.typedarrays.Float32Array;
import org.teavm.jso.typedarrays.Uint16Array;
import org.teavm.jso.webgl.*;
import web.IFont;
import web.IGraphics;
import web.IImage;
import web.impl.js.webgl.M4;
import web.util.Color;

public class JSGraphicsGL implements IGraphics {
    private final HTMLCanvasElement canvas;
    private final WebGLRenderingContext gl;
    private float[] color = Color.yellow.asFloatArray();
    private WebGLProgram fill_rect;
    private WebGLProgram draw_img;

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

        indices.set(new short[]{0,1,2,3});

        rect_buffer = gl.createBuffer();
        indBuf = gl.createBuffer();

        draw_img = gl.createProgram();
        WebGLShader tex_vertex = createAndCompile(gl.VERTEX_SHADER, "attribute vec4 a_position;\n" +
                "attribute vec2 a_texcoord;\n" +
                "\n" +
                "uniform mat4 u_matrix;\n" +
                "\n" +
                "varying vec2 v_texcoord;\n" +
                "\n" +
                "void main() {\n" +
                "   gl_Position = u_matrix * a_position;\n" +
                "   v_texcoord = a_texcoord;\n" +
                "}");
        WebGLShader tex_fragment = createAndCompile(gl.FRAGMENT_SHADER, "precision mediump float;\n" +
                "\n" +
                "varying vec2 v_texcoord;\n" +
                "\n" +
                "uniform sampler2D u_texture;\n" +
                "\n" +
                "void main() {\n" +
                "   gl_FragColor = texture2D(u_texture, v_texcoord);\n" +
                "}");
        gl.attachShader(draw_img, tex_vertex);
        gl.attachShader(draw_img, tex_fragment);
        gl.linkProgram(draw_img);
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
        gl.useProgram(fill_rect);
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
            JSImage img = (JSImage) _img;
            WebGLTexture texture = img.getTexture(gl);

        gl.useProgram(draw_img);

            // look up where the vertex data needs to go.
            int positionLocation = gl.getAttribLocation(draw_img, "a_position");
            int texcoordLocation = gl.getAttribLocation(draw_img, "a_texcoord");

            // lookup uniforms
            WebGLUniformLocation matrixLocation = gl.getUniformLocation(draw_img, "u_matrix");
            WebGLUniformLocation textureLocation = gl.getUniformLocation(draw_img, "u_texture");

            // Create a buffer.
            WebGLBuffer positionBuffer = gl.createBuffer();
            gl.bindBuffer(gl.ARRAY_BUFFER, positionBuffer);
            // Put a unit quad in the buffer
            float[] positions = {
                    0, 0,
                    0, 1,
                    1, 0,
                    1, 0,
                    0, 1,
                    1, 1,
            };
            Float32Array arr = Float32Array.create(12);
            arr.set(positions);
            gl.bufferData(gl.ARRAY_BUFFER, arr, gl.STATIC_DRAW);

            WebGLBuffer texcoordBuffer = gl.createBuffer();
            gl.bindBuffer(gl.ARRAY_BUFFER, texcoordBuffer);

            // Put texcoords in the buffer
            float[] texcoords = new float[]{
                    0, 0,
                    0, 1,
                    1, 0,
                    1, 0,
                    0, 1,
                    1, 1
            };
            arr = Float32Array.create(12);
            arr.set(texcoords);
            gl.bufferData(gl.ARRAY_BUFFER, arr, gl.STATIC_DRAW);

            gl.bindTexture(gl.TEXTURE_2D, texture);


            // Setup the attributes to pull data from our buffers
            gl.bindBuffer(gl.ARRAY_BUFFER, positionBuffer);
            gl.enableVertexAttribArray(positionLocation);
            gl.vertexAttribPointer(positionLocation, 2, gl.FLOAT, false, 0, 0);
            gl.bindBuffer(gl.ARRAY_BUFFER, texcoordBuffer);
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
            gl.uniformMatrix4fv(matrixLocation, false, matrix);

            // Tell the shader to get the texture from texture unit 0
            gl.uniform1i(textureLocation, 0);

            // draw the quad (2 triangles, 6 vertices)
            gl.drawArrays(gl.TRIANGLES, 0, 6);

    }
}
