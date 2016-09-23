import java.util.ArrayList;


public class Player {
	private String playerName;
	private boolean isDealer, isPlayer;
	private int timesWon, timesLost, money;
	ArrayList<cardNode> playerHand = new ArrayList<cardNode>(); 
	


	public Player(String name, int startingmoney){
		playerName = name;
		timesWon = 0;
		timesLost = 0;
		money = startingmoney;
	}
	public int getMoney(){
		return money;
	}

	public void setAsPlayer(){
		this.isDealer = false;
		this.isPlayer = true;
	}

	public void setAsDealer(){
		this.isDealer = true;
		this.isPlayer = false;
	}
	public void decrease100(){
		money-=100;
	}
	public void increase100(){
		money+=100;
	}
	public void info(){
		System.out.print("Name:" + playerName);
		if(isDealer)
			System.out.print(" is dealer");
		else
			System.out.print(" is player");
		System.out.println();
	}

	public String getName(){
		return this.playerName;
	}
	
	public boolean isPlayer(){
		return isPlayer;
	}
	
	public void increaseTimesWon(){
		this.timesWon++;
	}
	public void increaseTimesLost(){
		this.timesLost++;
	}
	
	public int howManyTimesWon(){
		return this.timesWon;
	}
	public int howManyTimesLost(){
		return this.timesLost;
	}
	
}
