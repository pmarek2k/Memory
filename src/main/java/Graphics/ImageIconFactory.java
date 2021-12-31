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
            String path = "/cards/scaled/" + iconName;
            result = new ImageIcon(Objects.requireNonNull(ImageIconFactory.class.getResource(path)));
            iconMap.put(iconName, result);
            return result;
        }
        return result;
    }
}
