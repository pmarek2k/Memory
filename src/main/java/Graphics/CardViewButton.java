package Graphics;

import Card.Card;

import javax.swing.*;

public class CardViewButton extends JButton {

    private Card card;

    public Card getCard() {
        return card;
    }

    public CardViewButton(String title, Icon icon, Card card){
        super(title, icon);
        this.card = card;
    }

    public void reverseCard(){
        if(this.getIcon().equals(ImageIconFactory.getIcon(card.getName() + ".png"))){
            this.setIcon(ImageIconFactory.getIcon("REVERSE.png"));
        }
        else{
            this.setIcon(ImageIconFactory.getIcon(card.getName() + ".png"));
        }
    }
}
