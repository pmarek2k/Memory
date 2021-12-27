import Card.Card;
import Graphics.Window;

import javax.swing.*;
import java.util.Set;

public class Game {

    private Window window;
    int cardPairNumber;
    Set<Card> cardSet;

    public Game(){
        while(true){
            String userInput = JOptionPane.showInputDialog("How many card pairs would you like to have? <5-16>");
            if(userInput == null || userInput.isEmpty()){
                System.exit(1);
            }
            try {
                cardPairNumber = Integer.parseInt(userInput);
                if(cardPairNumber < 5 || cardPairNumber > 16){
                    throw new IllegalStateException();
                }
                break;
            }
            catch (NumberFormatException | IllegalStateException e){
                JOptionPane.showMessageDialog(new JFrame(),"Please, enter correct number!");
            }
        }
        Set<Card> cardSet = Card.initializeCardSet(cardPairNumber);
        window = new Window("Memory", cardSet);
    }
}
