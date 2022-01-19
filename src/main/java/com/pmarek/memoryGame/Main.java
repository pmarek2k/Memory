package com.pmarek.memoryGame;

import com.pmarek.memoryGame.Card.Card;
import com.pmarek.memoryGame.Graphics.GameWindow;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.pmarek.memoryGame.utils.InputManager;

import java.util.Set;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args){
        logger.info("Getting cardPairNumber user input");
        int cardPairNumber = InputManager.getCardPairNumber();
        logger.info("Initializing card Set");
        Set<Card> cardSet = Card.initializeCardSet(cardPairNumber);
        logger.info("Creating new window");
        new GameWindow("Memory", cardSet);
    }
}
