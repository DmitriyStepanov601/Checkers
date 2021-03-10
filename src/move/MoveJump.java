package move;

import checkers.CheckerPosition;
import board.Board;
import logic.Coordinate;

/**
 * Class describing the jump of checkers
 *
 * @author Dmitriy Stepanov
 */
public class MoveJump extends Move {

    /**
     * Constructor - create a new checkers jump
     *
     * @param checker     - the initial position of the checkers
     * @param destination - the final position of the checkers
     * @see MoveJump#MoveJump(CheckerPosition, Coordinate)
     */
    public MoveJump(CheckerPosition checker, Coordinate destination) {
        this.checker = checker;
        this.destination = destination;
    }

    public boolean isJump() {
        return true;
    }

    public Move copy() {
        return new MoveJump(checker.copy(), destination);
    }

    public Move copy(Board newBoard) {
        return new MoveJump(newBoard.getChecker(checker.getPosition()), destination);
    }

    public Coordinate capturedCoordinate() {
        if (checker.getPosition().row() - destination.row() == 2) {          // Up
            if (checker.getPosition().column() - destination.column() == 2)   // Up,left
                return checker.getPosition().upLeftMove();
            else
                return checker.getPosition().upRightMove();
        } else {                                                                // Down
            if (checker.getPosition().column() - destination.column() == 2)    // Down,left
                return checker.getPosition().downLeftMove();
            else
                return checker.getPosition().downRightMove();
        }
    }

    public String toString() {
        String s;
        if (checker.getColor() == CheckerPosition.BLACK) s = "Black-J:";
        else s = "White-J:";
        s = s + "(" + checker.getPosition() + "-" + destination + ")";
        return s;
    }
}