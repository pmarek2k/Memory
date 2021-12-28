package utils;

import javax.swing.*;

public class InputManager {

    public static int getCardPairNumber(){
        int cardPairNumber;
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
        return cardPairNumber;
    }

}
