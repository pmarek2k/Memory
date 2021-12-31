package com.pmarek.memoryGame.Card;

import java.util.HashSet;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class Card {

    private final CardColor cardColor;
    private final CardValue cardValue;

    public Card(CardColor cardColor, CardValue cardValue) {
        this.cardColor = cardColor;
        this.cardValue = cardValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return cardColor == card.cardColor && cardValue == card.cardValue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardColor, cardValue);
    }

    @Override
    public String toString() {
        return "com.pmarek.memoryGame.Card{" +
                "cardColor=" + cardColor +
                ", cardValue=" + cardValue +
                '}';
    }

    public String getName(){
        return cardColor.toString() + "_" + cardValue.toString();
    }

    public static Set<Card> initializeCardSet(int cardPairNumber){
        Set<Card>cardSet = new HashSet<>();
        boolean setContainsCard;
        Random random = new Random();
        for(int i = 0; i < cardPairNumber; i++){
            setContainsCard = true;
            while(setContainsCard){
                CardColor color = CardColor.values()[Math.abs(random.nextInt()%4)];
                CardValue value = CardValue.values()[Math.abs(random.nextInt()%13)];
                Card card = new Card(color, value);
                if(!cardSet.contains(card)){
                    setContainsCard = false;
                    cardSet.add(card);
                }
            }
        }
        return cardSet;
    }
}
