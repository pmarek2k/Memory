import Card.Card;
import Graphics.Window;
import utils.InputManager;

import java.util.Set;

public class Main {

    public static void main(String[] args){
        int cardPairNumber = InputManager.getCardPairNumber();
        Set<Card> cardSet = Card.initializeCardSet(cardPairNumber);
        Window window = new Window("Memory", cardSet);
    }
}
