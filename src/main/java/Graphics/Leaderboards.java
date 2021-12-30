package Graphics;

import Card.Card;
import utils.GameResult;
import utils.InputManager;
import utils.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

public class Leaderboards extends JFrame {
    private JButton playAgainButton;
    private JTable table;
    private JPanel mainPanel;
    private JScrollPane scrollPane;

    private ArrayList<GameResult> gameResults;

    public Leaderboards() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);

        playAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                int cardPairNumber = InputManager.getCardPairNumber();
                Set<Card> cardSet = Card.initializeCardSet(cardPairNumber);
                Window window = new Window("Memory", cardSet);
            }
        });
        DatabaseConnection connection = new DatabaseConnection();
        connection.createNewTable();
        gameResults = connection.selectAllResults();
        createUIComponents();

        this.setVisible(true);
        this.pack();
    }

    private void createUIComponents() {
        String[] columnNames = {"Nickname",
                "Score"};
        Object[][] data = null;
        if(gameResults != null){
            data = gameResults.stream().map(gameResult -> gameResult.toArray()).toArray(Object[][]::new);
        }
        table = new JTable(data, columnNames);

        scrollPane.setViewportView(table);
        table.setEnabled(false);

    }
}
