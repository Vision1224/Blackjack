import java.util.ArrayList;
public class DeckOfCards {
	ArrayList<cardNode> arrayDeck = new ArrayList<cardNode>(); 
	int[] deck = new int[52];
	String[] suits = {"Spades", "Hearts", "Diamonds", "Clubs"};
	String[] ranks = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9","10", "Jack", "Queen", "King"};
	int[] handValue = {1,2,3,4,5,6,7,8,9,10,10,10,10};

	DeckOfCards(){
		intialize();
		shuffle();
		arrayToList();
		
	}

	private void arrayToList() {
		for(int i=0; i<deck.length; i++){
			int valueOfCard = handValue[((deck[i]%13))];
			arrayDeck.add(new cardNode(deck[i], ranks[deck[i] % 13] ,suits[deck[i] / 13], valueOfCard));
		}
		
	}

	private void show() {
		// Display the first four cards
		for (int i = 0; i <6; i++) {
			String suit = suits[deck[i] / 13];//used the divide to find the suit 0-3
			String rank = ranks[deck[i] % 13];//used the divide to find  rank 0-
			System.out.println("Card number " + deck[i] + ": " 
					+ rank + " of " + suit);
		}
	}
	private void intialize() {
		for (int i = 0; i < deck.length; i++)
			deck[i] = i;
	}

	private void shuffle(){
		// Shuffle the cards
		for (int i = 0; i < deck.length; i++) {
			// Generate an index randomly
			int index = (int)(Math.random() * deck.length);
			int temp = deck[i];
			deck[i] = deck[index]; 
			deck[index] = temp;
		}
	}
}
