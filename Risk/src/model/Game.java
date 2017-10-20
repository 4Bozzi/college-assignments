package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Observable;

import view.RiskFrame;

/**
 * 
 * @author Super Kewl Team
 * 
 * Assigned to: Matt, Paul
 *         This is the GameModel class that acts as the constructor for the
 *         board at the beginning of the game and contains an instance of the
 *         board, a list of the players, and an integer representing the
 *         difficulty of the game.
 * 
 *         Methods Contained: getBoard()
 */
public class Game extends Observable {

  public static StageEnum stage;
  public static Game game;
  private RiskFrame riskFrame;
  public Board board;

  public int difficulty; // 0 = Easy, 1 = Hard

  /**
   * 
   * @param numPlayers
   * @param humanPlayerName
   * @param difficulty
   * 
   *          This constructor takes three args and initializes the classes
   *          instance variables as well as, dealing with the most of the
   *          pre-game conditions like setting up the board and such.
   */
  public Game(int numPlayers, String humanPlayerName, int difficulty) {
    game = this;
    this.difficulty = difficulty;
    board = new Board();

    // Add Human Player to the Board
    board.addPlayer(humanPlayerName);
    if (difficulty == 0) {
      for (int i = 1; i < numPlayers; i++)
        board.addPlayer(i, new EasyAI());
    } else {
      for (int i = 1; i < numPlayers; i++)
        board.addPlayer(i, new HardAI());
    }
    ArrayList<Integer> territoryList = new ArrayList<Integer>();

    for (int i = 0; i < 42; i++) {
      territoryList.add(i);
    }
    Collections.shuffle(territoryList);

    for (int i = 0; i < 42; i++) {
      board.getPlayers().get(i % numPlayers).addTerritories(Board.getBoard().getGlobe().getTerritoryByID(territoryList.get(i)));
    }

    // Create a Risk Frame
    riskFrame = new RiskFrame();

    // Add Observers
    this.addObserver(riskFrame.getMapPanel());
    this.addObserver(riskFrame.getPlayerPanel());

    board.setPlayersDefaultArmies();
    Iterator<Player> iter = board.getPlayers().iterator();
    while (iter.hasNext()) {
      Player npc = (Player) iter.next();

      Bonus theBonus = new Bonus();
      npc.addReinforcements(theBonus.getBonus(npc));

      if (!npc.isPlayerHuman())
        npc.getAI().reinforce(npc);

    }
    Board.getBoard().getPlayers().get(0).draw();
    Board.getBoard().getPlayers().get(0).draw();

    Board.getBoard().getPlayers().get(0).draw();

    Board.getBoard().getPlayers().get(0).draw();
    Board.getBoard().getPlayers().get(0).draw();

    this.stage = StageEnum.REINFORCE;
    this.update();
  }

  public void runAIs() {
    ArrayList<Player> AIs = (ArrayList<Player>) Board.getBoard().getPlayers();
    AIs.remove(0); // Remove Human Player

    // Run the Turn for Each AI
   // for (Player theAI : AIs)
     // AITurn(theAI);
  }

  public void AITurn(Player input) {
    AI theAI = input.getAI();
   
    // Run the reinforce Stage
    input.setReinforcements(new Bonus().getBonus(input));
    theAI.reinforce(input);
    
    // Run the Default Attack Stage
    theAI.attack(input);
    
    // Run the Fortify Stage
    theAI.fortify(input);
  }

  public Game getGame() {
    return Game.game;
  }

  public void update(Object O) {
    this.setChanged();
    this.notifyObservers(O);
    riskFrame.getPlayerPanel().repaint();
  }

  public void update() {
    this.setChanged();
    this.notifyObservers();
    riskFrame.getPlayerPanel().repaint();
  }

  public Board getBoard() {
    return board;
  }

  public RiskFrame getRiskFrame() {
    return riskFrame;
  }
}
