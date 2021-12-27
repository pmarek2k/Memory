package Graphics;

import Card.Card;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class Window extends JFrame{
    private JPanel mainPanel;
    private JPanel userPanel;
    private JPanel cardPanel;
    private JLabel scoreLabel;
    private JLabel timeLabel;
    private JButton restartButton;

    private int rows;
    private int columns = 8;

    public Window(String title, Set<Card> cardSet){
        super(title);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.setVisible(true);
        restartButton.addActionListener(e -> {
                    //TODO: Implement reset button
                    this.dispose();
                    EventQueue.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                        }
                    });
                });
        // Initialize cards
        rows = (int) Math.ceil(cardSet.size() * 2 / (double) columns);
        cardPanel.setLayout(new GridLayout(rows, columns, 10, 10));

        ArrayList<Card> cardArrayList = new ArrayList<Card>(cardSet.stream().toList());
        cardArrayList.addAll(cardSet);
        for(int i = 0; i < cardArrayList.size(); i++){
            int index = Math.abs(new Random().nextInt()) % cardArrayList.size();
            CardViewButton button = new CardViewButton("", ImageIconFactory.getIcon("REVERSE.png"), cardArrayList.get(index));
            button.setPreferredSize(new Dimension(164, 233));
            button.setBackground(new Color(255, 255, 255));
            button.addActionListener(e -> {
                button.reverseCard();
                // TODO: don't let two or more cards get reversed
            });
            cardPanel.add(button);
        }
        this.pack();
    }
}
