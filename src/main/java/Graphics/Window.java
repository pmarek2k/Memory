package Graphics;

import Card.Card;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

public class Window extends JFrame{
    private JPanel mainPanel;
    private JPanel userPanel;
    private JPanel cardPanel;
    private JLabel scoreLabel;
    private JLabel timeLabel;
    private JButton restartButton;

    private final int rows = 8;
    private int columns;

    public Window(String title, Set<Card> cardSet){
        super(title);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.setVisible(true);
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Restart the game
            }
        });

        // Initialize cards
        columns = (int) Math.ceil(cardSet.size() * 2 / (double) rows);
        cardPanel.setLayout(new GridLayout(rows, columns, 10, 10));

        // TODO: Set cards randomly in grid
        for(Card c : cardSet){
            JButton button = new JButton("", ImageIconFactory.getIcon(c.getName() + ".png"));
            button.setPreferredSize(new Dimension(100, 50));
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //TODO: Implement clicking on card
                }
            });
            cardPanel.add(button);
        }

        this.pack();
    }
}
