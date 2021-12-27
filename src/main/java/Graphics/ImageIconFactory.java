package Graphics;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ImageIconFactory {

    private final static Map<String, ImageIcon> iconMap = new HashMap<>();

    public static ImageIcon getIcon(String iconName){
        ImageIcon result= iconMap.get(iconName);
        if(result == null){
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            String path = "/assets/cards/white/" + iconName;
            result = new ImageIcon(Objects.requireNonNull(classLoader.getResource(path)));
            iconMap.put(iconName, result);
            return result;
        }
        return result;
    }
}
