package Graphics;

import Card.Card;
import utils.InputManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    private ArrayList<CardViewButton> cardViewButtons;

    private int rows;
    private int columns = 8;

    private int cardsReversed = 0;

    private Timer reverseTimer;
    private Timer gameTimer;

    ActionListener reverseButtonActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < cardViewButtons.size(); i++){
                CardViewButton button = cardViewButtons.get(i);
                if(button.showsValue() && button.isEnabled()){
                    button.reverseCard();
                }
            }
            cardsReversed = 0;
        }};

    public Window(String title, Set<Card> cardSet){
        super(title);

        reverseTimer = new Timer(1000, reverseButtonActionListener);
        reverseTimer.setRepeats(false);
        //gameTimer = new Timer();

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.setVisible(true);
        restartButton.addActionListener(e -> {
                    this.dispose();
                    EventQueue.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            int cardPairNumber = InputManager.getCardPairNumber();
                            Set<Card> cardSet = Card.initializeCardSet(cardPairNumber);
                            Window window = new Window("Memory", cardSet);
                        }
                    });
                });
        // Initialize cards
        rows = (int) Math.ceil(cardSet.size() * 2 / (double) columns);
        cardPanel.setLayout(new GridLayout(rows, columns, 10, 10));

        ArrayList<Card> cardArrayList = new ArrayList<Card>(cardSet.stream().toList());
        cardArrayList.addAll(cardSet);
        cardViewButtons = new ArrayList<>();
        int size = cardArrayList.size();
        for(int i = 0; i < size; i++){
            int index = Math.abs(new Random().nextInt()) % cardArrayList.size();
            CardViewButton button = new CardViewButton("", ImageIconFactory.getIcon("REVERSE.png"), cardArrayList.get(index));
            cardArrayList.remove(index);
            button.setPreferredSize(new Dimension(164, 233));
            button.setBackground(new Color(255, 255, 255));
            button.addActionListener(e -> {
                // TODO: don't let two or more cards get reversed
                button.reverseCard();
                cardsReversed += 1;
                boolean pairFound = false;
                if(cardsReversed == 2){
                    for(int j = 0; j < cardViewButtons.size() - 1; j++){
                        for(int k = j+1; k < cardViewButtons.size(); k++){
                            CardViewButton button1 = cardViewButtons.get(j);
                            CardViewButton button2 = cardViewButtons.get(k);
                            if(button1.showsValue() && button2.showsValue()){
                                if(button1.getCard().equals(button2.getCard()) && (button1.isEnabled() && button2.isEnabled())){
                                    button1.setEnabled(false);
                                    button2.setEnabled(false);
                                    int currentScore = Integer.parseInt(scoreLabel.getText());
                                    scoreLabel.setText(Integer.toString(currentScore + 2));
                                    pairFound = true;
                                    cardsReversed = 0;
                                    break;
                                }
                            }
                        }
                    }
                    if(!pairFound){
                        reverseTimer.start();
                    }
                }
                else if(cardsReversed > 2) {
                    reverseTimer.stop();
                    for (int j = 0; j < cardViewButtons.size(); j++){
                        CardViewButton cardButton = cardViewButtons.get(j);
                        if(cardButton.showsValue() && cardButton.isEnabled()){
                            cardButton.reverseCard();
                        }
                    }
                    cardsReversed = 0;
                }
            });
            cardViewButtons.add(button);
            cardPanel.add(button);
        }
        this.pack();
    }
}
