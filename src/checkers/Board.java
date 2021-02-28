package checkers;
import java.io.Serializable;

public class Board implements Serializable {
   private final CheckerPosition[] board = new CheckerPosition[32];
   private MoveList history = new MoveList();
   protected Board next = null;     

   public Board copy() {
      Board newBoard = new Board();
      Coordinate temp;
      newBoard.setHistory(history.copy());

      for (int i = 1; i < 33; i++) {
         temp = new Coordinate(i);
         if (getChecker(temp) != null)
            newBoard.setChecker(getChecker(temp).copy(), temp);
      }

      return newBoard;
   }

   public CheckerPosition getChecker(Coordinate c) {
      return board[c.get() - 1];
   }

   public void setChecker(CheckerPosition checker, Coordinate c) {
      board[c.get() - 1] = checker;
      checker.setPosition(c);
   }

   public void removeChecker(CheckerPosition checker) {
      board[checker.getPosition().get() - 1] = null;
   }
   public boolean vacantCoordinate(Coordinate c) {
      return (getChecker(c) == null);
   }

   public int evaluate() {
      int score = 0;
      Coordinate c;

      for (int i = 1; i < 33; i++) {
         c = new Coordinate(i);

         if (getChecker(c) != null)
            score = score + getChecker(c).getValue();
      }

      return score;
   }

   public void setHistory(MoveList history) {
      this.history = history;
   }
   public MoveList getHistory() {
      return history;
   }
   public void addMoveToHistory(Move move) {
      history.add(move);
   }

   public void initialize() {
      initializeTop();
      initializeMiddle();
      initializeBottom();
   }     

   private void initializeTop() {
      for (int i = 1; i < 13; i++)
         board[i - 1] = new BlackChecker(new Coordinate(i));   
   }     

   private void initializeMiddle() {
      for (int i = 13; i < 21; i++)
         board[i - 1] = null; 
   }   

   private void initializeBottom() {
      for (int i = 21; i < 33; i++)
         board[i - 1] = new WhiteChecker(new Coordinate(i));   
   }

   public void setNext(Board next) { 
      this.next = next;   
   }
   public Board getNext() {
      return next;
   }

   public String toString() {
      String[][] stringBoard = new String[8][8];
      Coordinate temp;

      for (int i = 1;i < 33; i++) {
         temp = new Coordinate(i);

         if (getChecker(temp) != null)
            stringBoard[temp.column()][temp.row()] = getChecker(temp).toString();
      }

      StringBuilder s = new StringBuilder(" +---+---+---+---+---+---+---+---+\n ");
      for (int y = 0; y < 8; y++) {
         for (int x = 0; x < 8; x++) {
            if (stringBoard[x][y] != null)
               s.append("| ").append(stringBoard[x][y]).append(" ");
            else
               s.append("|   ");
         }
         s.append("| (").append((y * 4) + 1).append("-").append((y * 4) + 4).append(")");
         s.append("\n +---+---+---+---+---+---+---+---+\n ");
      }

      return s.toString();
   }
}