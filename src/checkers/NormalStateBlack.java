package checkers;
import java.io.Serializable;

/**
 * A class that describes the normal state of a black checker
 * @author Dmitriy Stepanov
 */
public class NormalStateBlack implements CheckerState, Serializable {
   public boolean findValidMoves(final CheckerPosition c, final Board board, MoveList validMoves) {
      if (!findValidJumps(c, board, validMoves)) {
         if (GameSearch.validBlackMove(c.getPosition(), c.getPosition().downLeftMove(), 
             board))
            validMoves.add(new MoveNormal(c, c.getPosition().downLeftMove()));

         if (GameSearch.validBlackMove(c.getPosition(), c.getPosition().downRightMove(), board))
            validMoves.add(new MoveNormal(c, c.getPosition().downRightMove()));
         return false;
      }
      else
         return true;
   }

   public boolean findValidJumps(final CheckerPosition c, final Board board, MoveList validJumps) {
      boolean found = false;
      if (GameSearch.validBlackJump(c.getPosition(), c.getPosition().downLeftJump(), board)) {
         validJumps.add(new MoveJump(c, c.getPosition().downLeftJump()));
         found = true;
      }
      
      if (GameSearch.validBlackJump(c.getPosition(), c.getPosition().downRightJump(), board)) {
         validJumps.add(new MoveJump(c, c.getPosition().downRightJump()));
         found = true;
      }
      return found;
   }
}