package ui.tea;

import org.teavm.classlib.java.util.TTimerTask;
import org.teavm.jso.JSFunctor;
import org.teavm.jso.JSObject;
import org.teavm.jso.browser.TimerHandler;
import org.teavm.jso.browser.Window;
import org.teavm.jso.dom.events.EventListener;
import org.teavm.jso.dom.events.MouseEvent;
import org.teavm.jso.dom.html.HTMLAnchorElement;
import org.teavm.jso.dom.html.HTMLElement;
import org.teavm.jso.dom.xml.NodeList;
import ui.poly.InputStreamPolyFill;
import ui.tea.fs.bfs.Buffer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

public class FileSystemViewer implements TimerHandler{

    private final HTMLElement table;
    private final HTMLAnchorElement downloadAnchor;
    private String currentId;

    public static final int UPDATE_INTERVAL = 100;

    private static final String PREFIX = "file_view_" + ((int)Math.random() * 100000);

    public FileSystemViewer(String tableId){
        this.table = Objects.requireNonNull(getElementById(tableId));
        this.downloadAnchor = Window.current().getDocument().createElement("a").cast();
        Window.current().getDocument().getBody().appendChild(downloadAnchor);
        downloadAnchor.getStyle().setProperty("display", "hidden");

        HTMLElement elem = findElementWithDataType("th", "file-refresh");
        if(elem != null){
            elem.listenClick(e -> refresh());
        }
        Window.setTimeout(this, 0);
    }

    public void download(File f){
        try {
            byte[] data = InputStreamPolyFill.readAllBytes(new FileInputStream(f));
            JSObject blob = JSMethods.blobify(Buffer.from(data).cast(), "application/x-binary");
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
        File[] children = f.listFiles();
        if(children == null){
            //TODO delete the files
            return;
        }
        HTMLElement e;
        for(int row = 0;(e = getFileRow(row)) != null; row++)
        {
            table.removeChild(e);
        }
        int row = 0;
        if(f.getParentFile() != null) {
            updateRow(row++, new File(f, ".."));
        }
        for(int i = 0; i < children.length; i++) {
            updateRow(row++, children[i]);
        }
    }

    private HTMLElement getFileRow(int index){
        String dataId = PREFIX + index;
        return findElementWithDataType("tr", dataId);
    }

    public void updateRow(int index, File file){
        HTMLElement row = getFileRow(index);
        if(row == null){
            row = Window.current().getDocument().createElement("tr");
            row.setAttribute("data-id", PREFIX + index);
            row.setInnerHTML("<td/><td/><td/>");
            table.appendChild(row);
        }
        NodeList<HTMLElement> children = row.getChildNodes().cast();
        HTMLElement name = children.item(0);
        HTMLElement type = children.item(1);
        HTMLElement size = children.item(2);
        name.setInnerHTML(file.getName());
        type.setInnerHTML(file.isDirectory() ? "Dir" : "File");
        row.setAttribute("class", file.isDirectory() ? "dir" : "file");
        size.setInnerHTML(String.valueOf(file.length()));
        if(file.isDirectory()){
            row.listenClick(event -> {
                try {
                    setCurrentPath(file.getCanonicalPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                refresh();
            });
        }else{
            row.listenClick(e -> download((file)));
        }
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
        Window.setTimeout(this, UPDATE_INTERVAL);
    }

    private interface  HTMLFilter {
        boolean filter(HTMLElement element);
    }
}
