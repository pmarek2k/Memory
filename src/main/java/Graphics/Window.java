package Graphics;

import Card.Card;
import utils.DatabaseConnection;
import utils.GameResult;
import utils.InputManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class Window extends JFrame {
    private JPanel mainPanel;
    private JPanel cardPanel;
    private JLabel scoreLabel;
    private JLabel timeLabel;
    private JButton restartButton;

    private ArrayList<CardViewButton> cardViewButtons;

    private final Timer reverseTimer;

    private ArrayList<CardViewButton> activeReversedCardButtons;

    public Window(String title, Set<Card> cardSet) {
        super(title);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.setVisible(true);

        reverseTimer = new Timer(1000, e -> reverseActiveButtons());
        reverseTimer.setRepeats(false);

        Timer gameTimer = new Timer(1000, e -> incrementTimer());
        gameTimer.setRepeats(true);

        restartButton.addActionListener(e -> restartGame());
        setupUI(cardSet);
        this.pack();
        gameTimer.start();
    }

    private void reverseActiveButtons() {
        for (CardViewButton button : cardViewButtons) {
            if (button.showsValue() && button.isEnabled()) {
                button.reverseCard();
            }
        }
        activeReversedCardButtons.clear();
    }

    private void incrementTimer() {
        timeLabel.setText(Integer.toString(Integer.parseInt(timeLabel.getText()) + 1));
        scoreLabel.setText(Integer.toString(Integer.parseInt(scoreLabel.getText()) - 1));
    }

    private void restartGame() {
        this.dispose();
        EventQueue.invokeLater(() -> {
            int cardPairNumber = InputManager.getCardPairNumber();
            Set<Card> cardSet = Card.initializeCardSet(cardPairNumber);
            new Window("Memory", cardSet);
        });
    }

    private void checkIfGameHasEnded() {
        if (cardViewButtons.stream().allMatch(cardViewButton -> cardViewButton.showsValue())) {
            dispose();
            EventQueue.invokeLater(() -> {
                DatabaseConnection connection = new DatabaseConnection();
                String nickname = InputManager.getPlayerNickname();
                connection.insertResult(new GameResult(nickname, Integer.parseInt(scoreLabel.getText())));
                new Leaderboards();
            });
        }
    }

    private void setupUI(Set<Card> cardSet) {
        int columns = 8;
        int rows = (int) Math.ceil(cardSet.size() * 2 / (double) columns);
        cardPanel.setLayout(new GridLayout(rows, columns, 10, 10));

        activeReversedCardButtons = new ArrayList<>();
        cardViewButtons = new ArrayList<>();

        ArrayList<Card> cardArrayList = new ArrayList<>(cardSet.stream().toList());
        cardArrayList.addAll(cardSet);

        int size = cardArrayList.size();
        for (int i = 0; i < size; i++) {
            int index = Math.abs(new Random().nextInt()) % cardArrayList.size();
            CardViewButton button = new CardViewButton("", ImageIconFactory.getIcon("REVERSE.png"), cardArrayList.get(index));
            cardArrayList.remove(index);
            button.setPreferredSize(new Dimension(164, 233));
            button.setBackground(new Color(255, 255, 255));
            button.addActionListener(e -> buttonClick(button));
            cardViewButtons.add(button);
            cardPanel.add(button);
        }
        scoreLabel.setText(Integer.toString(cardSet.size() * 2));
    }

    private void buttonClick(CardViewButton button) {
        if (!button.showsValue()) {
            button.reverseCard();
            activeReversedCardButtons.add(button);
            boolean pairFound = false;
            if (activeReversedCardButtons.size() == 2) {
                CardViewButton button1 = activeReversedCardButtons.get(0);
                CardViewButton button2 = activeReversedCardButtons.get(1);
                if (button1.getCard().equals(button2.getCard())) {
                    button1.setEnabled(false);
                    button2.setEnabled(false);
                    int currentScore = Integer.parseInt(scoreLabel.getText());
                    scoreLabel.setText(Integer.toString(currentScore + 2));
                    pairFound = true;
                    checkIfGameHasEnded();
                }
                if (!pairFound) {
                    reverseTimer.start();
                }
            }
            else if (activeReversedCardButtons.size() > 2) {
                reverseTimer.stop();
                reverseActiveButtons();
            }
        }
    }
}
