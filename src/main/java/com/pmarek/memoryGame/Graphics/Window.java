package com.pmarek.memoryGame.Graphics;

import com.pmarek.memoryGame.Card.Card;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.pmarek.memoryGame.utils.DatabaseConnection;
import com.pmarek.memoryGame.utils.GameResult;
import com.pmarek.memoryGame.utils.InputManager;

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

    private static final Logger logger = LogManager.getLogger(Window.class);

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
            logger.info("Game has ended");
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
        logger.info("Setting up UI");
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
        logger.info("UI has been set up.");
    }

    private void buttonClick(CardViewButton button) {
        logger.info("Button " + button.toString() + " has been clicked");
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
            } else if (activeReversedCardButtons.size() > 2) {
                logger.info("More than 2 buttons have been clicked, reversing all active cards");
                reverseTimer.stop();
                reverseActiveButtons();
            }
        }
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(0, 0));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 6, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(panel1, BorderLayout.SOUTH);
        final JLabel label1 = new JLabel();
        label1.setText("Score:");
        panel1.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        scoreLabel = new JLabel();
        scoreLabel.setText("0");
        panel1.add(scoreLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Time:");
        panel1.add(label2, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        timeLabel = new JLabel();
        timeLabel.setText("0");
        panel1.add(timeLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        restartButton = new JButton();
        restartButton.setText("Restart");
        panel1.add(restartButton, new com.intellij.uiDesigner.core.GridConstraints(0, 5, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 30), null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(0, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, new Dimension(52, 11), null, 0, false));
        cardPanel = new JPanel();
        cardPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        mainPanel.add(cardPanel, BorderLayout.CENTER);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }
}
