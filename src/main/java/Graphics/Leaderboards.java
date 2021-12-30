package Graphics;

import Card.Card;
import utils.GameResult;
import utils.InputManager;
import utils.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

public class Leaderboards extends JFrame {
    private JButton playAgainButton;
    private JTable table;
    private JPanel mainPanel;

    private Set<GameResult> gameResults;

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
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("nickname");
        tableModel.addColumn("score");
        if(!(gameResults == null)){
            for(GameResult g : gameResults){
                tableModel.addRow(new Object[] {g.nickname(), g.score()});
            }
        }
        table = new JTable(tableModel);
    }
}
