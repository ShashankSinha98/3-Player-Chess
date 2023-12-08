package controller;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

import model.common.Agent;
import model.common.Colour;
import model.common.Position;
import model.common.agents.GUIAgent;
import model.common.agents.ManualAgent;
import model.utilities.ImpossiblePositionException;
import view.ThreeChessDisplay;

/**
 * Class with static methods for running tournaments and playing threeChess matches.
 * @author Tim French
 * **/
public class ThreeChess{
  
  /**
   * A private class for representing the statistics of an agent in a tournament.
   * **/
  private static class Statistics implements Comparable{
    private int won; // no of won games 
    private int lost; // no of lost games 
    private int pass; // no of draw games
    private int played; // total no of games played
    private Agent agent;

    /**
     * Constructs a statistics object for the given agent
     * **/
    public Statistics(Agent a){agent = a;}

    /**
     * Updates the Statistics objects with the score from a game.
     * @param score -2 if an illegal move is attempt, -1 for a loss, 0 for a draw and +1 for a win.
     * **/
    public void update(int score){
      switch(score){
        case -2: lost+=2; break;
        case -1: lost++;break;
        case 0: pass++;break;
        case 1: won++; break;
        default:
      }
      played++;
    }

    /**
     * @return the average score of the player
     * **/
    public double average(){return (1.0*(won-lost))/played;}

    /**
     * @return a JSON representation of the Statistics for an agent.
     * **/
    public String toString(){return "name:"+ agent+", won:"+won+", lost:"+lost+", played:"+played+", avg:"+average();}

    /**
     * @param o the object to compare to.
     * @return -1 if this object has a higher average than the paramater, 0 if the averages are equivalent and +1 if it has a lower average score.
     * **/
    public int compareTo(Object o){
      if(o instanceof Statistics){
        Statistics stats = (Statistics) o;
        return Double.compare(stats.average(), average());
      } else return -1;
    }
  }



  /**
   * Runs a tournament for a group of agents.
   * The games in the tournament will have the specified time limit.
   * If a non-zero number of games are specified, the agents will be randomly assigned to games.
   * If 0 is specified, every agent will play every other pair of agents, with the colours of the pieces randomly assigned.
   * @param bots an array of Agents to compete in the contest.
   * @param timeLimit the cumulative time each player has (in seconds). To specify an untimed game, set as less than or equal to zero.
   * @param displayOn a boolean flag for whether the game should be graphically displayed
   * @param logFile a FileName to print the game logs to. If this can't be found, or is null, System.out will be used instead.
   * **/
  public static void  tournament(Agent[] bots, int numGames, String logFile){
    HashMap<Agent, Statistics> scoreboard = new HashMap<Agent,Statistics>();
    PrintStream logger = System.out;
    try{
      if(logFile!=null) logger = new PrintStream(new File(logFile));
    }
    catch(IOException e){System.out.println(logFile+"not found: "+e.getMessage()+"\nUsing System.out instead.");}
    for(Agent a: bots) scoreboard.put(a, new Statistics(a));
    
    // play a manual game
    int[] res = play(bots[0],bots[1],bots[2], logger);
    
    for(int o = 0; o<3;o++)scoreboard.get(bots[o]).update(res[o]);
    for(Agent a: bots)logger.println(scoreboard.get(a));
    logger.println("Rank\tAgent\t\tWon\tLost\tPlayed\tAvg\n");
    Statistics[] results = (Statistics[]) scoreboard.values().toArray(new Statistics[0]);
    Arrays.sort(results);
    int rank = 1;
    for(Statistics stat:results)
      logger.println(rank++ +"\t"+stat.agent+"\t\t"+stat.won+"\t"+stat.lost+"\t"+stat.played+"\t"+stat.average()+"\n");
  }
  
  /**
   * Runs a threeChess game between three players.
   * There are options to display the board, and log the game to a text file.
   * A time limit may also be specified for a timed game.
   * @param blue the agent playing the blue pieces.
   * @param green the agent playing the green pieces.
   * @param red the Agent playing the red pieces.
   * @param timeLimit the cumulative time each player has (in seconds). To specify an untimed game, set as less than or equal to zero.
   * @param logger a printStream to write the game moves to.
   * @param displayOn a boolean flag for whether the game should be graphically displayed
   * @return an array of three ints, the scores for blue, green and red, in that order.
   * **/
  public static int[] play(Agent blue, Agent green, Agent red, PrintStream logger){
    // Board board = new Board(timeLimit>0?timeLimit*1000:1);
	Board board = new Board();
    logger.println("======NEW GAME======");
    logger.println("BLUE: "+blue.toString());
    logger.println("GREEN: "+green.toString());
    logger.println("RED: "+red.toString());
    ThreeChessDisplay display = null;
    
    display = new ThreeChessDisplay(board, blue.toString(), green.toString(), red.toString());
    GUIAgent.currentDisplay = display;
    
    while(!board.gameOver()){//note in an untimed game, this loop can run infinitely.
      Colour currentTurn = board.getTurn();
      Agent current = (currentTurn==Colour.BLUE?blue:(currentTurn==Colour.GREEN?green:red));
      long startTime = System.nanoTime();
      Position[] move = null;
      try{
        // User will play his move, move arr will have start position and end position
        // Expected from view part
        move = current.playMove((Board) board.clone());
        System.out.println("xlr8: Move: "+Arrays.toString(move));
      }catch(CloneNotSupportedException e){}

      //How to deal with infinite loops here?
      //make agents runnable abstract classes and provide a final method for running a move?
      //set board as a variable
      //run executes the move method
      //setup a timeout?
      long time = (System.nanoTime() - startTime + 500_000L) / 1_000_000L; // Rounds to nearest millisecond
      if(move!=null && move.length==2 && board.isLegalMove(move[0],move[1])){
        try{
          // board.move(move[0],move[1],(timed?(int)time:0));
          board.move(move[0],move[1]);
          logger.println(currentTurn + ": " + move[0] + '-' + move[1] + " t:" + time);
        }
        catch(ImpossiblePositionException e){logger.println(e.getMessage());}
      }
      else{//Illegal move results in immediate loss, -2 penalty, and a win awarded to the other two players.
        int[] ret = {1,1,1};
        ret[board.getTurn().ordinal()] = -2;
        return ret;
      }
    }
    GUIAgent.currentDisplay = null;
    logger.println("=====Game Over=====");
    int[] ret = {0,0,0};
    ret[board.getWinner().ordinal()] = 1;
    ret[board.getLoser().ordinal()] = -1;
    for(Colour c:Colour.values())
    	logger.println(c+" score:"+ret[c.ordinal()]+" time: N/A points:"+ ret[c.ordinal()]);
      //logger.println(c+" score:"+ret[c.ordinal()]+" time:"+board.getTimeLeft(c)+" points:"+ ret[c.ordinal()]);
    return ret;
  }

  /**
   * This method can be customised to run tournaments with agents added in the code (add them to array bots), 
   * or manual games between players, or a cheat mode which is effectively a board that can be freely manipulated.
   * Run program with parameter "manual" for a game with moves added in the command line, "cheat" to ignore all rules, and no parameters to run a tournament between agents listed in bots.
   **/
  public static void main(String[] args){
	
	  //String[] playerNames = inputPlayerNames();
	  String[] playerNames = {"A", "B", "C"};
	  Agent[] bots = null;
	  
	  if(args.length > 0 && args[0].equals("manual")){
	      bots = new Agent[] {new ManualAgent(playerNames[0]), new ManualAgent(playerNames[1]), new ManualAgent(playerNames[2])};
	      tournament(bots,0, null);
	    }
	    else {
	      bots = new Agent[] {new GUIAgent(playerNames[0]), new GUIAgent(playerNames[1]), new GUIAgent(playerNames[2])};
	      tournament(bots,0, null);
	    }
  }
  
  private static String[] inputPlayerNames() {
	  Scanner sc = new Scanner(System.in);
	  String[] colors = {"BLUE", "GREEN", "RED"};
	  String[] pNames = {"A", "B", "C"}; // random player names at start
	  
	  for(int i=0; i<colors.length; i++) {
		  System.out.println("Input name of "+colors[i]+" Player: ");
		  String input = sc.nextLine();
		  if(!"".equals(input)) pNames[i] = input;
	  }
	  sc.close();
	  return pNames;
  }
}
