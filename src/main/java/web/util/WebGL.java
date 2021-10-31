package web.util;

import org.teavm.jso.webgl.WebGLProgram;
import org.teavm.jso.webgl.WebGLRenderingContext;
import org.teavm.jso.webgl.WebGLShader;

import java.io.*;

public final class WebGL {

    public static final String SHADERS_DIR = "shaders/";

    private WebGL(){

    }

    public static WebGLProgram loadAndCompile(WebGLRenderingContext gl, String name){
        WebGLShader vertex = null;
        WebGLShader fragment = null;
        try{
            vertex =  createAndCompile(gl, gl.VERTEX_SHADER, getShaderFromResources(name + ".vert"));
        } catch (FileNotFoundException ignored) {

        } catch (IOException e){
            e.printStackTrace();
        }

        try{
            fragment =  createAndCompile(gl, gl.FRAGMENT_SHADER, getShaderFromResources(name + ".frag"));
        } catch (FileNotFoundException ignored) {

        } catch (IOException e){
            e.printStackTrace();
        }
        WebGLProgram program = gl.createProgram();

        if(fragment != null){
            gl.attachShader(program, fragment);
        }
        if(vertex != null){
            gl.attachShader(program, vertex);
        }
        gl.linkProgram(program);
        return program;
    }

    public static WebGLShader createAndCompile(WebGLRenderingContext gl, int type, String source){
        WebGLShader f_shader = gl.createShader(type);
        gl.shaderSource(f_shader, source);
        gl.compileShader(f_shader);
        return f_shader;
    }

    public static String getShaderFromResources(String name) throws IOException {
        String file = SHADERS_DIR + name;
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        InputStream in = loader.getResourceAsStream(file);
        if(in == null){
            throw new FileNotFoundException(file);
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();
        String line;
        while((line = br.readLine()) != null){
            sb.append(line).append('\n');
        }
        return sb.toString();
    }
}
