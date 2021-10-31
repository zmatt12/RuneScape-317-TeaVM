package web.impl.js.webgl;

import org.teavm.classlib.ResourceSupplier;
import org.teavm.classlib.ResourceSupplierContext;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ShaderResourceSupplier implements ResourceSupplier {

    public static final String[] RESOURCE_DIRS = { JSGraphicsGL.SHADERS_DIR};

    @Override
    public String[] supplyResources(ResourceSupplierContext context) {
        List<String> res = new ArrayList<>();
        for(String dir : RESOURCE_DIRS) {
            URL u = context.getClassLoader().getResource(dir);
            if(u == null){
                continue;
            }
            try {
                Path path = Paths.get(u.toURI());
                Files.list(path).forEach(file ->{
                    String p = dir + path.relativize(file);
                    res.add(p);
                    System.out.println("Adding:" + p);
                });
            } catch (URISyntaxException | IOException e) {
                e.printStackTrace();
            }
        }

        return res.toArray(new String[0]);
    }
}
