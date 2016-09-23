
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.util.Timer;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Timer;

import java.awt.SystemColor;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Main_GUI {

	private JFrame frame; 
	private JTextField textFieldFieldPlayer2Name, textFieldFieldPlayer1Name;
	private Player player1, player2, currentPlayer;
	private static JLabel lbl_player, lbl_dealer, lbl_DealerHand[], lbl_PlayerHand[], lbl_DeclareWinner, lbl_DealerSum, lbl_PlayerSum,WinnerBackground_Label, lbl_Player1,lbl_Player2;
	private JButton btn_dealerHit, btn_dealerStay, btn_playerStay, btn_playerHit,btn_Quit,btn_startNewGame, btn_Play ;
	private JCheckBox checkBox_isDealerPlayer1, checkBox_isDealerPlayer2; 
	private static JPanel NamePanel, GamePanel,ultimateWinnerPanel;
	private DeckOfCards deck;
	private static ArrayList<cardNode> playerHand, dealerHand;//,lbl_DealerHand,lbl_PlayerHand;
	private int cardsHandedOut = 0, sumOfPlayer, sumOfDealer;
	private final int STARTING_CASH=1000;
	private JLabel lblNewLabel;
	private Timer timer;
	private static java.io.File autoSaveDirectory;
	private Object currentGuiElement;
	private static java.util.Date startTimer;
	private File file;
	private long secondsPassed=0;
	private static PrintWriter output;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main_GUI window = new Main_GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the application.
	 */
	public Main_GUI() {
		initialize();

	}
	private class TimerListener implements ActionListener{
		/*		public TimerListener(File file) {
			// TODO Auto-generated constructor stub
		}
		 */
		public void actionPerformed(ActionEvent e){
			secondsPassed++;
			//System.out.println("print plus second" +e.getSource().toString());   textFieldFieldPlayer2Name
			if(currentGuiElement != null){ 
				if( currentGuiElement.equals(lbl_Player1) )
					System.out.println("we are in lbl player1");
				else if( currentGuiElement.equals( lbl_Player2) )
					System.out.println("we are in lbl player2");
				else if( currentGuiElement.equals( textFieldFieldPlayer1Name) )
					System.out.println("we are in textFieldFieldPlayer1Name");
				else if( currentGuiElement.equals( textFieldFieldPlayer2Name) )
					System.out.println("we are in textFieldFieldPlayer2Name");
				else if( currentGuiElement.equals( checkBox_isDealerPlayer1) )
					System.out.println("we are in checkBox_isDealerPlayer1");
				else if( currentGuiElement.equals( checkBox_isDealerPlayer2) )
					System.out.println("we are in checkBox_isDealerPlayer2");
				else if( currentGuiElement.equals( btn_Play) )
					System.out.println("we are in btn_Play");
				else if( currentGuiElement.equals( lbl_player)){
					System.out.println("we are in lbl_player");
				}
				else if( currentGuiElement.equals( lbl_dealer)){
					System.out.println("we are in lbl_dealer");
				}
				/**    */
				else if( currentGuiElement.equals( btn_dealerHit)){
					System.out.println("we are in btn_dealerHit");
					appendToLogFile("btn_dealerHit", "dealer");					
				}
				else if( currentGuiElement.equals( btn_dealerStay)){
					System.out.println("we are in btn_dealerStay");	
					appendToLogFile("btn_dealerStay", "dealer");	
				}
				else if( currentGuiElement.equals( btn_playerHit)){
					System.out.println("we are in btn_playerHit");
					appendToLogFile("btn_playerHit", "player");	
				}
				else if( currentGuiElement.equals( btn_playerStay)){
					System.out.println("we are in btn_playerStay");
					appendToLogFile("btn_playerStay", "player");
				}
				else if( currentGuiElement.equals( btn_startNewGame)){
					System.out.println("we are in btn_startNewGame");
					appendToLogFile("btn_startNewGame", "nobody");
				}
				else if( currentGuiElement.equals( btn_Quit)){
					System.out.println("we are in btn_Quit");
					if(btn_dealerHit.isVisible())
						appendToLogFile("btn_Quit", "dealer");
					else if(btn_playerHit.isVisible())
						appendToLogFile("btn_Quit", "player");
					else if(!btn_playerHit.isVisible() && !btn_dealerHit.isVisible())
						appendToLogFile("btn_Quit", "nobody");
				}

			}
		}


		private void appendToLogFile(String guiElement, String playerOrDealer) {
			//Here true is to append the content to file
			FileWriter fw;
			BufferedWriter bw;
			try {
				fw = new FileWriter(file,true);
				bw = new BufferedWriter(fw);
				if(playerOrDealer.equals("dealer"))
					bw.write("On [Dealer] "+findDealer(player1, player2).getName()+ "'s turn, ["+secondsPassed+"] the mouse was hovering over ["
							+guiElement+"] with "+findDealer(player1, player2).getName()+ "'s [$" +findDealer(player1, player2).getMoney()+"]");
				else if(playerOrDealer.equals("player"))
					bw.write("On [Player] "+findPlayer(player1, player2).getName()+ "'s turn, ["+secondsPassed+"] the mouse was hovering over ["
							+guiElement+"] with "+findPlayer(player1, player2).getName()+ "'s [$" +findPlayer(player1, player2).getMoney()+"]");
				else if(playerOrDealer.equals("nobody"))
					bw.write("On [NOBODY's] ["+secondsPassed+"] the mouse was hovering over ["+guiElement+"] with "+
							findPlayer(player1, player2).getName()+ "'s [$" +findPlayer(player1, player2).getMoney()+"] AND "
							+findDealer(player1, player2).getName()+ "'s [$" +findDealer(player1, player2).getMoney()+"]");
				bw.newLine();
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		lbl_DealerHand= new JLabel[52];
		lbl_PlayerHand= new JLabel[52];
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 530);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		NamePanel = new JPanel();
		frame.getContentPane().add(NamePanel, "name_10998886105217");
		NamePanel.setLayout(null);
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/**
		 * This is the players Names
		 */
		/**here are the labels thats stay fixed as either "player1" or "player2"*/
		lbl_Player1 = new JLabel("Player 1:");
		lbl_Player1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				currentGuiElement = lbl_Player1;
				//System.out.println("entered");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				currentGuiElement = null;
				//System.out.println("exited");
			}
		});
		lbl_Player1.setBounds(10, 37, 59, 14);
		NamePanel.add(lbl_Player1);

		lbl_Player2 = new JLabel("Player 2:");
		lbl_Player2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				currentGuiElement = lbl_Player2;
				//System.out.println("entered");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				currentGuiElement = null;
				//System.out.println("exited");
			}
		});
		lbl_Player2.setBounds(10, 124, 59, 14);
		NamePanel.add(lbl_Player2);

		/**
		 * this is the name fields where player 1 can enter his name
		 * */
		textFieldFieldPlayer1Name = new JTextField();
		textFieldFieldPlayer1Name.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				currentGuiElement = textFieldFieldPlayer1Name;
				//System.out.println("entered");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				currentGuiElement = null;
				//System.out.println("exited");
			}
		});
		//textFieldFieldPlayer1Name.addActionListener(new TimerListener());
		textFieldFieldPlayer1Name.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				if(e.getDot()==0)
					btn_Play.setVisible(false);				
				if((checkBox_isDealerPlayer1.isSelected() || checkBox_isDealerPlayer2.isSelected()) && (!textFieldFieldPlayer2Name.getText().equals(""))  &&(!textFieldFieldPlayer1Name.getText().equals(""))  ) 
					btn_Play.setVisible(true);
			}
		});
		//textFieldFieldPlayer1Name.setText("Brian");
		textFieldFieldPlayer1Name.setBounds(66, 34, 174, 20);
		NamePanel.add(textFieldFieldPlayer1Name);
		textFieldFieldPlayer1Name.setColumns(10);
		/**
		 * this is the name fields where player 2 can enter his name
		 */
		textFieldFieldPlayer2Name = new JTextField();
		textFieldFieldPlayer2Name.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				currentGuiElement = textFieldFieldPlayer2Name;
				//System.out.println("entered");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				currentGuiElement = null;
				//System.out.println("exited");
			}
		});
		textFieldFieldPlayer2Name.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				if(e.getDot()==0)
					btn_Play.setVisible(false);				
				if((checkBox_isDealerPlayer1.isSelected() || checkBox_isDealerPlayer2.isSelected()) && (!textFieldFieldPlayer2Name.getText().equals(""))  &&(!textFieldFieldPlayer1Name.getText().equals(""))  ) 
					btn_Play.setVisible(true);
			}
		});
		//textFieldFieldPlayer2Name.setText("Roy");
		textFieldFieldPlayer2Name.setColumns(10);
		textFieldFieldPlayer2Name.setBounds(66, 121, 174, 20);
		NamePanel.add(textFieldFieldPlayer2Name);
		/**
		 * this is the check boxes
		 */
		checkBox_isDealerPlayer1 = new JCheckBox("Dealer");
		checkBox_isDealerPlayer1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				currentGuiElement = checkBox_isDealerPlayer1;
				//System.out.println("entered");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				currentGuiElement = null;
				//System.out.println("exited");
			}
		});
		checkBox_isDealerPlayer1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				//System.out.println(e.getStateChange());
				if(e.getStateChange() == e.DESELECTED && (!checkBox_isDealerPlayer2.isSelected())){
					btn_Play.setVisible(false);				
					//System.out.println("Deselected player1s button"+e.getStateChange());
				}
				if(e.getStateChange() == e.SELECTED){
					if(textFieldFieldPlayer1Name.getText().equals("") || textFieldFieldPlayer2Name.getText().equals(""))
						btn_Play.setVisible(false);
					else
						btn_Play.setVisible(true);
					if(checkBox_isDealerPlayer2.isSelected())
						checkBox_isDealerPlayer2.setSelected(false);

					//System.out.println("Selected player1s button"+e.getStateChange());
				}
				//System.out.println("player 1 part"+e.getStateChange());
			}
		});
		checkBox_isDealerPlayer1.setBounds(263, 34, 97, 23);
		NamePanel.add(checkBox_isDealerPlayer1);
		/**
		 * HERE WE ADD THE SECOND BUTTON
		 */
		checkBox_isDealerPlayer2 = new JCheckBox("Dealer");
		checkBox_isDealerPlayer2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				currentGuiElement = checkBox_isDealerPlayer2;
				//System.out.println("entered");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				currentGuiElement = null;
				//System.out.println("exited");
			}
		});
		checkBox_isDealerPlayer2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				//	System.out.println(e.getStateChange());
				if(e.getStateChange() == e.DESELECTED && (!checkBox_isDealerPlayer1.isSelected())){
					btn_Play.setVisible(false);				
					//System.out.println("Deselected player2s button"+e.getStateChange());
				}  
				if(e.getStateChange() == e.SELECTED){
					if(textFieldFieldPlayer1Name.getText().equals("") || textFieldFieldPlayer2Name.getText().equals(""))
						btn_Play.setVisible(false);
					else
						btn_Play.setVisible(true);	
					if(checkBox_isDealerPlayer1.isSelected())
						checkBox_isDealerPlayer1.setSelected(false);
					//System.out.println("Selected player2s button"+e.getStateChange());
				}
				//System.out.println("player 2 part"+e.getStateChange());
			}
		});
		/**
		 * THIS IS THE PLAY BUTTON AND ITS ACTION LISTENER//////////////////////////////////////////////////////////////////////////////////////////////////////
		 */
		btn_Play = new JButton("Play");
		btn_Play.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				currentGuiElement = btn_Play;
				//System.out.println("entered");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				currentGuiElement = null;
				//System.out.println("exited");
			}
		});
		btn_Play.setVisible(false);
		btn_Play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String player1Name = textFieldFieldPlayer1Name.getText();
				String player2Name = textFieldFieldPlayer2Name.getText();
				player1 = new Player(player1Name,STARTING_CASH );
				player2 = new Player(player2Name,STARTING_CASH);
				if(checkBox_isDealerPlayer1.isSelected()){
					player1.setAsDealer();
					player2.setAsPlayer();
					currentPlayer=player2;
				}
				else if(checkBox_isDealerPlayer2.isSelected()){
					player2.setAsDealer();
					player1.setAsPlayer();
					currentPlayer= player1;
				}
				//THIS IS THE TIMER AND CREATE A LOG FILE//
				startTimer = new java.util.Date();
				long millis = (startTimer.getTime());//start time-end time
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH_mm_ss");
				Date date = new Date(millis);
				//System.out.println(dateFormat.format(date));
				String fileName="\\"+player1Name+"~VS~"+player2Name+" At Time--"+(dateFormat.format(date))+".txt";
				autoSaveDirectory = new java.io.File("c:\\GameLogs");
				autoSaveDirectory.mkdirs();
				System.out.println(fileName);
				file = new File(autoSaveDirectory.getAbsolutePath()+ fileName);
				try {
					output = new PrintWriter(file);
					output.println("First Game started at:" + new java.util.Date(file.lastModified()));
					//output.println("testing 2nd line");
					//output.println("testing 3rd line");
					//output.println("testing 4th line");
					output.close();

				} catch (FileNotFoundException e2) {
					e2.printStackTrace();
				}
				timer = new Timer(1000, new TimerListener());
				timer.start();	



				showPlayerAndDealerNames();
				//make a new deck of cards
				deck = new DeckOfCards();//makes a new deck of a 1D array
				playerHand = new ArrayList<cardNode>(); 
				dealerHand = new ArrayList<cardNode>(); 
				initializePlayerAndDealerCardSpacing();
				initializeTwoCards(playerHand, dealerHand, deck);
				//show(deck.arrayDeck);
				cardsHandedOut = 4;
				sumOfPlayer = sumOfHand(playerHand);
				//System.out.println("sum of players intial hand is "+ sumOfPlayer);
				//System.out.println("Player hand");
				show(playerHand);
				//System.out.println("-------------------------");
				//System.out.println("Dealerhand");//delete this when done          asdf asdf 
				show(dealerHand);
				//System.out.println("-------------------------");
				//printList(dealerHand, "dealer" ); 
				printListToGameBoard(playerHand, "player" ); 
				//System.out.println("########################################");
				NamePanel.setVisible(false);
				GamePanel.setVisible(true);
				//ultimateWinnerPanel.setVisible(true);
				boolean	isBLACKJACK = (sumOfPlayer == 21) ? true : false;
				if(isBLACKJACK){//player wins
					printCurrentMoneyWithWinOrLoseAmount("player");
					//findPlayer(player1, player2).increase100();
					//findDealer(player1, player2).decrease100();
					lbl_PlayerSum.setText("Sum: "+ sumOfPlayer);
					//System.out.println("Player got Black Jack");
					lbl_DeclareWinner.setText("Winner: " + findPlayer(player1, player2).getName() + " GOT BLACKJACK!!!!");
					lbl_DeclareWinner.setVisible(true);
					btn_playerStay.setVisible(false);
					btn_playerHit.setVisible(false);
					btn_dealerHit.setVisible(false);
					btn_dealerStay.setVisible(false);
					btn_startNewGame.setEnabled(true);
					btn_startNewGame.setVisible(true);
				}
			}
		});
		btn_Play.setBounds(366, 37, 109, 68);
		NamePanel.add(btn_Play);
		checkBox_isDealerPlayer2.setBounds(263, 121, 97, 23);
		NamePanel.add(checkBox_isDealerPlayer2);
		/**
		 ************************************************************************
		 * ******************************GAME BOARD******************************
		 * **********************************************************************
		 */
		GamePanel = new JPanel();
		GamePanel.setBackground(new Color(0, 128, 0));
		frame.getContentPane().add(GamePanel, "name_11002943968577");
		GamePanel.setLayout(null);
		//DISPLAY PLAYER NAME AND BUTTONS
		lbl_player = new JLabel("Player:Money"  );
		lbl_player.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				currentGuiElement = lbl_player;
				//System.out.println("entered");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				currentGuiElement = null;
				//System.out.println("exited");
			}
		});

		lbl_player.setFont(new Font("Tahoma", Font.BOLD, 12));
		lbl_player.setForeground(Color.WHITE);
		lbl_player.setBounds(10, 427, 333, 14);
		GamePanel.add(lbl_player);
		btn_playerHit = new JButton("Hit");
		btn_playerHit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				currentGuiElement = btn_playerHit;
				//System.out.println("entered");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				currentGuiElement = null;
				//System.out.println("exited");
			}
		});
		btn_playerHit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playerHand.add(deck.arrayDeck.get(cardsHandedOut++));
				printListToGameBoard(playerHand, "player" );
				sumOfPlayer = sumOfHand(playerHand);
				lbl_PlayerSum.setText("Sum: "+ sumOfPlayer);
				//System.out.println("***************************");
				show(playerHand);
				//System.out.println("***************************");
				//GamePanel.add
				if(sumOfPlayer == 21){//player wins
					printCurrentMoneyWithWinOrLoseAmount("player");
					//findPlayer(player1, player2).increase100();
					//	findDealer(player1, player2).decrease100();
					lbl_DeclareWinner.setText("Winner is " + findPlayer(player1, player2).getName() + ".    Player Won Got 21!!");
					lbl_DeclareWinner.setVisible(true);
					btn_playerStay.setVisible(false);
					btn_playerHit.setVisible(false);
					btn_dealerHit.setVisible(false);
					btn_dealerStay.setVisible(false);
					btn_startNewGame.setEnabled(true);
					btn_startNewGame.setVisible(true);				
				}
				if(sumOfHand(playerHand)>21){//dealer wins
					printCurrentMoneyWithWinOrLoseAmount("dealer");
					//findPlayer(player1, player2).decrease100();
					//findDealer(player1, player2).increase100();
					lbl_DeclareWinner.setText("Winner is " + findDealer(player1, player2).getName() + ".    Player Busted!!");
					lbl_DeclareWinner.setVisible(true);
					btn_playerStay.setVisible(false);
					btn_playerHit.setVisible(false);
					btn_dealerHit.setVisible(false);
					btn_dealerStay.setVisible(false);
					btn_startNewGame.setEnabled(true);
					btn_startNewGame.setVisible(true);
				}
			}
		});
		btn_playerHit.setBounds(353, 422, 89, 23);
		GamePanel.add(btn_playerHit);
		btn_playerStay = new JButton("Stay");
		btn_playerStay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				currentGuiElement =btn_playerStay ;
				//System.out.println("entered");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				currentGuiElement = null;
				//System.out.println("exited");
			}
		});
		btn_playerStay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btn_playerStay.setVisible(false);
				btn_playerHit.setVisible(false);
				btn_dealerHit.setVisible(true);
				btn_dealerStay.setVisible(true);
				printListToGameBoard(dealerHand, "dealer" ); 
				sumOfDealer= sumOfHand(dealerHand);
				lbl_DealerSum.setText("Sum: "+ sumOfDealer);

				if(sumOfPlayer > 21){//dealer wins
					printCurrentMoneyWithWinOrLoseAmount("dealer");
					//findPlayer(player1, player2).decrease100();
					//findDealer(player1, player2).increase100();
					lbl_DeclareWinner.setText("Winner is " + findDealer(player1, player2) + "   Player Busted!!");
					lbl_DeclareWinner.setVisible(true);
					btn_playerStay.setVisible(false);
					btn_playerHit.setVisible(false);
					btn_dealerHit.setVisible(false);
					btn_dealerStay.setVisible(false);
					btn_startNewGame.setEnabled(true);
					btn_startNewGame.setVisible(true);
				}
			}
		});
		btn_playerStay.setBounds(465, 422, 89, 23);
		GamePanel.add(btn_playerStay);
		//DISPLAY DEALER NAME AND BUTTONS
		lbl_dealer = new JLabel("Dealer:");
		lbl_dealer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				currentGuiElement = lbl_dealer;
				//System.out.println("entered");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				currentGuiElement = null;
				//System.out.println("exited");
			}
		});
		lbl_dealer.setFont(new Font("Tahoma", Font.BOLD, 12));
		lbl_dealer.setForeground(Color.WHITE);
		lbl_dealer.setBounds(10, 11, 333, 14);
		GamePanel.add(lbl_dealer);
		btn_dealerHit = new JButton("Hit");
		btn_dealerHit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				currentGuiElement = btn_dealerHit;
				//System.out.println("entered");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				currentGuiElement = null;
				//System.out.println("exited");
			}
		});
		btn_dealerHit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dealerHand.add(deck.arrayDeck.get(cardsHandedOut++));
				printListToGameBoard(dealerHand, "dealer" ); 
				sumOfDealer= sumOfHand(dealerHand);
				lbl_DealerSum.setText("Sum: "+ sumOfDealer);
				//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
				show(dealerHand);
				//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

				if(sumOfDealer == 21){//dealer wins
					printCurrentMoneyWithWinOrLoseAmount("dealer");
					//	findPlayer(player1, player2).decrease100();
					//findDealer(player1, player2).increase100();
					lbl_DeclareWinner.setText("Winner is " + findDealer(player1, player2).getName() + " Dealer Won Got 21!!");
					lbl_DeclareWinner.setVisible(true);
					btn_playerStay.setVisible(false);
					btn_playerHit.setVisible(false);
					btn_dealerHit.setVisible(false);
					btn_dealerStay.setVisible(false);
					btn_startNewGame.setEnabled(true);
					btn_startNewGame.setVisible(true);
				}
				if(sumOfDealer > 21){//player wins
					printCurrentMoneyWithWinOrLoseAmount("player");
					//findPlayer(player1, player2).increase100();
					//findDealer(player1, player2).decrease100();
					//System.out.println("Dealer went bust");
					lbl_DeclareWinner.setText("Winner is " + findPlayer(player1, player2).getName() + ".    Dealer Busted!!");
					lbl_DeclareWinner.setVisible(true);
					btn_playerStay.setVisible(false);
					btn_playerHit.setVisible(false);
					btn_dealerHit.setVisible(false);
					btn_dealerStay.setVisible(false);
					btn_startNewGame.setEnabled(true);
					btn_startNewGame.setVisible(true);
				}
			}
		});
		btn_dealerHit.setVisible(false);
		btn_dealerHit.setBounds(353, 11, 89, 23);
		GamePanel.add(btn_dealerHit);
		btn_dealerStay = new JButton("Stay");
		btn_dealerStay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				currentGuiElement = btn_dealerStay;
				//System.out.println("entered");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				currentGuiElement = null;
				//System.out.println("exited");
			}
		});
		btn_dealerStay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sumOfPlayer = sumOfHand(playerHand);
				sumOfDealer = sumOfHand(dealerHand);
				if(sumOfDealer> sumOfPlayer){//dealer wins
					printCurrentMoneyWithWinOrLoseAmount("dealer");
					//findPlayer(player1, player2).decrease100();
					//findDealer(player1, player2).increase100();
					lbl_DeclareWinner.setText("Winner is " + findDealer(player1, player2).getName() + ".    Dealer Hand Wins!!");
					lbl_DeclareWinner.setVisible(true);
					btn_playerStay.setVisible(false);
					btn_playerHit.setVisible(false);
					btn_dealerHit.setVisible(false);
					btn_dealerStay.setVisible(false);
					btn_startNewGame.setEnabled(true);
					btn_startNewGame.setVisible(true);

				}
				else{//player wins
					printCurrentMoneyWithWinOrLoseAmount("player");
					//findPlayer(player1, player2).increase100();
					//findDealer(player1, player2).decrease100();
					lbl_DeclareWinner.setText("Winner is " + findPlayer(player1, player2).getName() + ".    Player Hand Wins!!");
					lbl_DeclareWinner.setVisible(true);
					btn_playerStay.setVisible(false);
					btn_playerHit.setVisible(false);
					btn_dealerHit.setVisible(false);
					btn_dealerStay.setVisible(false);
					btn_startNewGame.setEnabled(true);
					btn_startNewGame.setVisible(true);
				}
			}
		});
		btn_dealerStay.setVisible(false);
		btn_dealerStay.setBounds(465, 11, 89, 23);
		GamePanel.add(btn_dealerStay);
		btn_Quit = new JButton("Quit");
		btn_Quit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				currentGuiElement = btn_Quit;
				//System.out.println("entered");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				currentGuiElement = null;
				//System.out.println("exited");
			}
		});
		btn_Quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btn_Quit.setBounds(611, 35, 151, 127);
		GamePanel.add(btn_Quit);
		btn_startNewGame = new JButton("Play Again");
		btn_startNewGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				currentGuiElement = btn_startNewGame;
				//System.out.println("entered");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				currentGuiElement = null;
				//System.out.println("exited");
			}
		});

		btn_startNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startNewGame();
			}
		});
		btn_startNewGame.setVisible(false);
		btn_startNewGame.setEnabled(true);
		btn_startNewGame.setBounds(611, 288, 151, 127);
		GamePanel.add(btn_startNewGame);
		lbl_DeclareWinner = new JLabel("Winner:");
		lbl_DeclareWinner.setForeground(Color.WHITE);
		lbl_DeclareWinner.setVisible(false);
		lbl_DeclareWinner.setFont(new Font("Batang", Font.PLAIN, 24));
		lbl_DeclareWinner.setVisible(true);
		lbl_DeclareWinner.setBounds(10, 194, 561, 81);
		GamePanel.add(lbl_DeclareWinner);
		lbl_PlayerSum = new JLabel("Sum:");
		lbl_PlayerSum.setFont(new Font("Tahoma", Font.BOLD, 12));
		lbl_PlayerSum.setForeground(Color.WHITE);
		lbl_PlayerSum.setBounds(10, 285, 61, 23);
		GamePanel.add(lbl_PlayerSum);
		lbl_DealerSum = new JLabel("Sum:");
		lbl_DealerSum.setFont(new Font("Tahoma", Font.BOLD, 12));
		lbl_DealerSum.setForeground(Color.WHITE);
		lbl_DealerSum.setBounds(10, 144, 61, 23);
		GamePanel.add(lbl_DealerSum);

		ultimateWinnerPanel = new JPanel();
		ultimateWinnerPanel.setBackground(SystemColor.textHighlight);
		frame.getContentPane().add(ultimateWinnerPanel, "name_463724659180705");
		ultimateWinnerPanel.setLayout(null);

		WinnerBackground_Label = new JLabel();
		WinnerBackground_Label.setBounds(-16, 0, 800	,311);
		WinnerBackground_Label.setIcon(new ImageIcon("Z:\\School\\Spring 2015\\Advanced Object\\BlackJack Lab3\\cards_gif\\winner2.gif"));
		ultimateWinnerPanel.add(WinnerBackground_Label);

		lblNewLabel = new JLabel();
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 28));
		lblNewLabel.setBounds(-6, 322, 655, 169);
		ultimateWinnerPanel.add(lblNewLabel);

		JButton btn_StartNewFreshGame = new JButton("Start A New Game");
		btn_StartNewFreshGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							Main_GUI window = new Main_GUI();
							window.frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		btn_StartNewFreshGame.setBounds(613, 322, 161, 158);
		ultimateWinnerPanel.add(btn_StartNewFreshGame);
	}

	protected void startNewGame() {
		lbl_DealerSum.setText("Sum: ");
		btn_startNewGame.setVisible(false);
		lbl_DeclareWinner.setVisible(false);
		swapPlayerAndDealer();
		showPlayerAndDealerNames();
		//with Play Again we need to refresh everything
		cardsHandedOut = 0;
		clearPreviousIcons();
		lbl_DealerHand= new JLabel[52];// 79
		lbl_PlayerHand= new JLabel[52];// 80
		deck = new DeckOfCards();//makes a new deck of a 1D array 109
		playerHand = new ArrayList<cardNode>(); //110
		dealerHand = new ArrayList<cardNode>();//111
		initializePlayerAndDealerCardSpacing();//112
		initializeTwoCards(playerHand, dealerHand, deck);//113
		cardsHandedOut = 4;//115
		printListToGameBoard(playerHand, "player" ); 
		btn_playerHit.setVisible(true);
		btn_playerStay.setVisible(true);

		sumOfPlayer = sumOfHand(playerHand);
		boolean	isBLACKJACK = (sumOfPlayer == 21) ? true : false;
		if(isBLACKJACK){//player wins
			printCurrentMoneyWithWinOrLoseAmount("player");
			//findPlayer(player1, player2).increase100();
			//	findDealer(player1, player2).decrease100();
			lbl_PlayerSum.setText("Sum: "+ sumOfPlayer);
			//System.out.println("Player got Black Jack");
			lbl_DeclareWinner.setText("Winner: " + findPlayer(player1, player2).getName() + " GOT BLACKJACK!!!!");
			lbl_DeclareWinner.setVisible(true);
			btn_playerStay.setVisible(false);
			btn_playerHit.setVisible(false);
			btn_dealerHit.setVisible(false);
			btn_dealerStay.setVisible(false);
			//btn_startNewGame.setVisible(true);
			btn_startNewGame.setVisible(true);
		}
	}
	protected void swapPlayerAndDealer() {
		//SWAP PLAYER AND DEALER
		if(player1.isPlayer()){
			player1.setAsDealer();
			player2.setAsPlayer();
		}
		else if(player2.isPlayer()){
			player2.setAsDealer();
			player1.setAsPlayer();
		}
	}
	//THIS METHOD HANDS OUT TWO CARDS TO PLAYER AND 2 CARDS TO DEALER, AND ALTERNATES BUT FIRST CARD GOES TO PLAYER
	protected void initializeTwoCards(ArrayList<cardNode> playerHand2,ArrayList<cardNode> dealerHand2, DeckOfCards deck2) {
		//HERE WE HAND OUT THE CARDS
		for(int i=0; i<4; i++){
			if(i%2 == 0){//does this because it alternates odd and even for alternating cards between dealer and player
				playerHand.add(deck.arrayDeck.get(i)); //put back OR REMOVE TO SIMULATE A BLACKJACK
				System.out.println();
			}
			else{
				dealerHand.add(deck.arrayDeck.get(i));
			}
		}
		for(int i=0; i < 2; i++){			
			String location =  "cards_gif/BottomUp.gif";
			ImageIcon icon = new ImageIcon(location);
			lbl_DealerHand[i].setIcon(icon);
			GamePanel.add(lbl_DealerHand[i]);
		}
		//USED TO FORCE A BLACKJACK AT START FOR DEBUGGING
		//put back OR REMOVE TO SIMULATE A BLACKJACK
		/*
		for(int i=0; i<52;i++){
			if(deck.arrayDeck.get(i).cardNumber == 0 || deck.arrayDeck.get(i).cardNumber == 12 ){
				playerHand.add(deck.arrayDeck.get(i));
			}	
		}*/
		sumOfPlayer = sumOfHand(playerHand);
		lbl_PlayerSum.setText("Sum: "+ sumOfPlayer);
		sumOfDealer = sumOfHand(dealerHand);
	}
	//THIS METHOD PRINTS THE HAND OF THE PLAYER OR DEALER
	private static void printListToGameBoard(ArrayList<cardNode> listX, String playerORdealer) {
		if(playerORdealer == "dealer"){
			for(int i=0; i<listX.size(); i++){
				cardNode temp = (listX.get(i));
				String location =  temp.gifFileName;
				ImageIcon icon = new ImageIcon(location);
				lbl_DealerHand[i].setIcon(icon);
				GamePanel.add(lbl_DealerHand[i]);
			}
		}
		else if(playerORdealer.equals("player")){
			for(int i=0; i<listX.size(); i++){
				cardNode temp = (listX.get(i));
				String location =  temp.gifFileName;
				ImageIcon icon = new ImageIcon(location);
				lbl_PlayerHand[i].setIcon(icon);
				GamePanel.add(lbl_PlayerHand[i]);
			}
		}
	}
	protected void showPlayerAndDealerNames() {
		if(player1.isPlayer()){
			lbl_player.setText("Player: " + player1.getName() + "  Money: $"+ player1.getMoney());
			lbl_dealer.setText("Dealer: "+ player2.getName()  + "  Money: $"+ player2.getMoney());
		}
		else{
			lbl_player.setText("Player: " + player2.getName() + "  Money: $"+ player2.getMoney());
			lbl_dealer.setText("Dealer: "+ player1.getName() + "  Money: $"+ player1.getMoney());
		}
	}
	//THIS METHOD PRINTS THE HAD OF THE PLAYER
	private static void show(ArrayList<cardNode> listX) {
		for(int i=0; i<listX.size(); i++){
			cardNode temp = (listX.get(i));
			System.out.println(temp.rank + " of " + temp.suit);
		}
	}
	protected void clearPreviousIcons() {
		for(int i=0; i<52; i++){ 
			lbl_DealerHand[i].setVisible(false);
			lbl_PlayerHand[i].setVisible(false);		
		}
	}
	protected Player findPlayer(Player player1, Player player2) {
		if(player1.isPlayer())
			return player1;
		else
			return player2;
	}
	protected Player findDealer(Player player1, Player player2) {
		if(player1.isPlayer())
			return player2;
		else
			return player1;
	}
	protected void initializePlayerAndDealerCardSpacing() {
		for(int i=0; i<52; i++){ 
			lbl_DealerHand[i] = new JLabel();
			lbl_DealerHand[i].setBounds(10 + ((i) *12), 50, 81, 106);
			lbl_PlayerHand[i] = new JLabel();
			lbl_PlayerHand[i].setBounds(10 + ((i) *12), 298, 81, 106);
		}
	}
	//THIS METHOD FINDS SUM OF HAND AND FINDS ALONE IF IT IS BEST TO USE ACE AS 11 OR 1
	private static int sumOfHand(ArrayList<cardNode> playerHand) {
		int sumWithACEas11= 0;
		int sumWithACEas1= 0;
		boolean hasACE=false;

		for (int i = 0; i < playerHand.size(); i++) {
			if( playerHand.get(i).valueOfCard == 1){
				//System.out.println("found an ace");
				sumWithACEas11 +=  11;
				sumWithACEas1 +=  1;						
			}
			else{
				sumWithACEas11 +=  playerHand.get(i).valueOfCard;
				sumWithACEas1 +=  playerHand.get(i).valueOfCard;				
			}
		}
		if(sumWithACEas11 > 21)
			return sumWithACEas1;
		if(sumWithACEas1 > 21)
			return sumWithACEas11;
		if(sumWithACEas11 > sumWithACEas1){
			return sumWithACEas11;			
		}
		else 
			return sumWithACEas1;
	}
	//this method recieves a sting parameter to tell who the winner is PLAYER or DEALER
	private void printCurrentMoneyWithWinOrLoseAmount(String string) {
		if(string.equals("player")){
			lbl_player.setText("Player: " + findPlayer(player1, player2).getName() + "  Money: $"+ findPlayer(player1, player2).getMoney() + "+ $100=$" + (findPlayer(player1, player2).getMoney()+100));
			lbl_dealer.setText("Dealer: "+ findDealer(player1, player2).getName()  + "  Money: $"+ findDealer(player1, player2).getMoney() + "- $100=$" + (findDealer(player1, player2).getMoney()-100));
			findPlayer(player1, player2).increase100();
			findDealer(player1, player2).decrease100();
		}
		else{
			lbl_player.setText("Player: " + findPlayer(player1, player2).getName() + "  Money: $"+ findPlayer(player1, player2).getMoney() + "- $100=$" + (findPlayer(player1, player2).getMoney()-100));
			lbl_dealer.setText("Dealer: "+ findDealer(player1, player2).getName()  + "  Money: $"+ findDealer(player1, player2).getMoney() + "+ $100=$" + (findDealer(player1, player2).getMoney()+100));
			findPlayer(player1, player2).decrease100();
			findDealer(player1, player2).increase100();
		}
		if(findPlayer(player1, player2).getMoney() == 0 ){
			NamePanel.setVisible(false);
			GamePanel.setVisible(false);
			lblNewLabel.setText(findDealer(player1, player2).getName()+ " YOU ARE THE ULTIMATE WINNER "); 
			ultimateWinnerPanel.setVisible(true);
		}
		if(findDealer(player1, player2).getMoney() == 0 ){
			NamePanel.setVisible(false);
			GamePanel.setVisible(false);
			lblNewLabel.setText(findPlayer(player1, player2).getName()+ " YOU ARE THE ULTIMATE WINNER ");
			ultimateWinnerPanel.setVisible(true);
		}


	}
}