package web.impl.js.event;

import org.teavm.jso.dom.events.KeyboardEvent;
import web.event.KeyEvent;

import java.util.HashMap;
import java.util.Map;

public class JSKeyEvent extends KeyEvent {

    private static final Map<String, Integer> keyMap = new HashMap<>();

    private final int type;
    private final KeyboardEvent event;

    static {
        keyMap.put("ArrowLeft", VK_LEFT);
        keyMap.put("ArrowRight", VK_RIGHT);
        keyMap.put("ArrowUp", VK_UP);
        keyMap.put("ArrowDown", VK_DOWN);

        keyMap.put("Control", VK_CONTROL);
        keyMap.put("Alt", VK_ALT);
        keyMap.put("Enter", VK_ENTER);
        keyMap.put("Backspace", VK_BACK_SPACE);
        keyMap.put("Tab", VK_TAB);
        keyMap.put("Escape", VK_ESC);
        keyMap.put("Shift", VK_SHIFT);

        for(int i = 1; i <= 12; i++){
            keyMap.put("F" + i, VK_F1 + i - 1);
        }

        keyMap.put("Insert", VK_INSERT);
        keyMap.put("Delete", VK_DELETE);
        keyMap.put("Home", VK_HOME);
        keyMap.put("End", VK_END);
        keyMap.put("PageUp", VK_PAGE_UP);
        keyMap.put("PageDown", VK_PAGE_DOWN);

        keyMap.put("PrintScreen", VK_PRINT_SCREEN);
        keyMap.put("Pause", VK_PAUSE);
        keyMap.put("ScrollLock", VK_SCROLL_LOCK);
        keyMap.put("AudioVolumeMute", VK_AUDIO_MUTE);
        keyMap.put("AudioVolumeDown", VK_AUDIO_DOWN);
        keyMap.put("AudioVolumeUp", VK_AUDIO_UP);
    }

    public JSKeyEvent(int type, KeyboardEvent event){
        this.type = type;
        this.event = event;
    }

    @Override
    public int getEventType() {
        return type;
    }

    @Override
    public int getKeyCode() {
        String key = event.getKey();
        if(keyMap.containsKey(key)) {
            return keyMap.get(key);
        }
        return event.getKeyCode();
    }

    @Override
    public char getKeyChar() {
        String key = event.getKey();
        if(key.length() > 1){
            return 0;
        }
        return key.charAt(0);
    }

}
