public class CardModel {

    private final int cardNumber;

    public CardModel(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardModel card = (CardModel) o;
        return cardNumber == card.cardNumber;
    }

}
