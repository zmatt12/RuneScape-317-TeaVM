package ui.tea;

import org.teavm.jso.JSObject;
import org.teavm.jso.browser.TimerHandler;
import org.teavm.jso.browser.Window;
import org.teavm.jso.dom.html.HTMLAnchorElement;
import org.teavm.jso.dom.html.HTMLElement;
import org.teavm.jso.dom.html.HTMLInputElement;
import org.teavm.jso.dom.xml.NodeList;
import org.teavm.jso.typedarrays.Uint8Array;
import ui.poly.InputStreamPolyFill;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

public class FileSystemViewer implements TimerHandler{

    private final HTMLElement table;
    private final HTMLAnchorElement downloadAnchor;
    private final HTMLInputElement numberInput;
    private String currentId;

    private static final String PREFIX = "fileIndex";

    private File[] files;

    public FileSystemViewer(String tableId, String numberId){
        this.table = Objects.requireNonNull(getElementById(tableId));
        this.numberInput = Objects.requireNonNull(getElementById(numberId)).cast();
        this.downloadAnchor = Window.current().getDocument().createElement("a").cast();
        Window.current().getDocument().getBody().appendChild(downloadAnchor);
        downloadAnchor.getStyle().setProperty("display", "hidden");
        Window.setTimeout(this, 0);
    }

    public void download(File f){
        try {
            byte[] data = InputStreamPolyFill.readAllBytes(new FileInputStream(f));
            Uint8Array wrapped = Uint8Array.create(data.length);
            wrapped.set(data);
            JSObject blob = JSMethods.blobify(wrapped, "application/x-binary");
            downloadAnchor.setHref(JSMethods.createObjectUrl(blob));
            downloadAnchor.setDownload(f.getName());
            downloadAnchor.click();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setCurrentId(String id){
        this.currentId = id;
    }

    public String getCurrentPath(){
        return getElementById(currentId).getInnerHTML();
    }

    private void setCurrentPath(String path) {
        getElementById(currentId).setInnerHTML(path);
    }

    public void refresh(){
        File f = new File(getCurrentPath());
        files = f.listFiles();
        if(files == null){
            files = new File[0];
        }
        int row = 0;
        if(f.getParentFile() != null) {
            File[] tmp = new File[files.length + 1];
            System.arraycopy(files, 0, tmp, 1, files.length);
            tmp[0] = new File(f, "..");
            this.files = tmp;
        }
        for(int i = 0; i < files.length; i++) {
            updateRow(row++);
        }

        HTMLElement e;
        for(;(e = getFileRow(row)) != null; row++)
        {
            table.removeChild(e);
        }
    }

    private HTMLElement getFileRow(int index){
        String dataId = PREFIX + index;
        return findElementWithDataType("tr", dataId);
    }

    public void updateRow(int index){
        HTMLElement row = getFileRow(index);
        if(row == null){
            row = Window.current().getDocument().createElement("tr");
            row.setAttribute("data-id", PREFIX + index);
            row.setInnerHTML("<td/><td/><td/>");
            table.appendChild(row);
            row.listenClick(event ->{
                event.preventDefault();
                File f= files[index];
                if(f.isDirectory()){
                    try {
                        setCurrentPath(f.getCanonicalPath());
                        refresh();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    download(f);
                }
            });
        }
        File file = files[index];
        NodeList<HTMLElement> children = row.getChildNodes().cast();
        HTMLElement name = children.item(0);
        HTMLElement type = children.item(1);
        HTMLElement size = children.item(2);
        name.setInnerHTML(file.getName());
        type.setInnerHTML(file.isDirectory() ? "Dir" : "File");
        row.setAttribute("class", file.isDirectory() ? "dir" : "file");
        size.setInnerHTML(String.valueOf(file.length()));
    }

    private HTMLElement findElementWithDataType(String tag, String dataId){
        return findElement(tag, f -> dataId.equals(f.getAttribute("data-id")));
    }

    private HTMLElement findElement(String tag, HTMLFilter f) {
        NodeList<HTMLElement> list =  Window.current().getDocument().getElementsByTagName(tag).cast();
        for(int i = 0 ; i < list.getLength(); i++){
            HTMLElement e = list.item(i);
            if(f.filter(e)) {
                return e;
            }
        }
        return null;
    }

    private HTMLElement getElementById(String id){
        return Window.current().getDocument().getElementById(id);
    }

    @Override
    public void onTimer() {
        refresh();
        Window.setTimeout(this, Integer.parseInt(numberInput.getValue()));
    }

    private interface  HTMLFilter {
        boolean filter(HTMLElement element);
    }
}
