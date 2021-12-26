package Graphics;

import javax.swing.*;
import java.awt.*;

public class Window {

    private JFrame jframe;

    public Window(int width, int height){
        jframe = new JFrame("Memory");
        jframe.setPreferredSize(new Dimension(width, height));
        jframe.pack();
        jframe.setVisible(true);

        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
