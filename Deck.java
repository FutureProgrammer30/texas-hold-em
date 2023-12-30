import java.util.ArrayList;

public class Deck {

    private final ArrayList<Card> deck;

    public Deck() {
        deck = new ArrayList<>();
        createShuffledDeck();
    }
    
    private void createShuffledDeck() {
        for (int i = 0; i < 52; i++) {
            deck.add(new Card(i));
        }

        for (int i = 0; i < deck.size(); i++) {
            deck.set(i, deck.set((int) (Math.random() * deck.size()), deck.get(i)));
        }
    }
    
    public Card dealCard() {
        return deck.isEmpty() ? null : deck.remove(0);
    }
    
    public void removeCard(Card c) {
        for (int i = 0; i < deck.size(); i++) {
            if (c.getValue() == deck.get(i).getValue() && c.getSuit() == deck.get(i).getSuit()) {
                deck.remove(i);
                i--;
            }
        }
    }
}