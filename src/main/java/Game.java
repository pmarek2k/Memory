import Card.Card;
import Card.CardColor;
import Card.CardValue;
import Graphics.Window;

import javax.swing.*;
import java.util.HashSet;
import java.util.Random;
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
        initializeCardSet();
        window = new Window(800, 600);
    }

    private void initializeCardSet(){
        cardSet = new HashSet<Card>();
        boolean setContainsCard;
        for(int i = 0; i < cardPairNumber; i++){
            setContainsCard = true;
            while(setContainsCard){
                Random random = new Random();
                CardColor color = CardColor.values()[Math.abs(random.nextInt()%4)];
                CardValue value = CardValue.values()[Math.abs(random.nextInt()%13)];
                Card card = new Card(color, value);
                if(cardSet.contains(card)){
                    setContainsCard = true;
                }
                else{
                    setContainsCard = false;
                    cardSet.add(card);
                    System.out.println(card);
                }
            }
        }
    }
}
