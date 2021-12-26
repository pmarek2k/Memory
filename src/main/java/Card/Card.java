package Card;

import java.util.Objects;

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
        return "Card{" +
                "cardColor=" + cardColor +
                ", cardValue=" + cardValue +
                '}';
    }
}
