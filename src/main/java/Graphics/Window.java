package Graphics;

import javax.swing.*;

public class Window extends JFrame{
    private JPanel mainPanel;
    private JPanel userPanel;
    private JPanel cardPanel;
    private JLabel scoreLabel;
    private JLabel timeLabel;
    private JButton restartButton;

    public Window(){
        super("Memory");

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
