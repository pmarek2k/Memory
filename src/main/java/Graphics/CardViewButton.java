package Graphics;

import Card.Card;

import javax.swing.*;

public class CardViewButton extends JButton {

    private Card card;
    private boolean showsValue = false;

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
            this.showsValue = false;
        }
        else{
            this.setIcon(ImageIconFactory.getIcon(card.getName() + ".png"));
            this.showsValue = true;
        }
    }

    public boolean showsValue() {
        return showsValue;
    }
}
