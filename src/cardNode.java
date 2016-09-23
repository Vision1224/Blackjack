
public class cardNode {
	int cardNumber, valueOfCard;
	String suit, rank, gifFileName;
	
	
	
	cardNode(int numb, String rank, String suit, int valueOfCard){
		cardNumber = numb;
		this.suit=suit;
		this.rank = rank;		
		this.valueOfCard = valueOfCard;
		gifFileName = "cards_gif/" + rank + suit+".gif";
		
	}
	
}
