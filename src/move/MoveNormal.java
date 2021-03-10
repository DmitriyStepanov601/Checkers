package move;

import checkers.CheckerPosition;
import board.Board;
import logic.Coordinate;

/**
 * A class that describes the normal movement of a checker
 *
 * @author Dmitriy Stepanov
 */
public class MoveNormal extends Move {

    /**
     * Constructor - create a new checkers movement
     *
     * @param checker     - the initial position of the checkers
     * @param destination - the final position of the checkers
     * @see MoveNormal#MoveNormal(CheckerPosition, Coordinate)
     */
    public MoveNormal(CheckerPosition checker, Coordinate destination) {
        this.checker = checker;
        this.destination = destination;
    }

    public boolean isJump() {
        return false;
    }

    public Move copy() {
        return new MoveNormal(checker.copy(), destination);
    }

    public Move copy(Board newBoard) {
        return new MoveNormal(newBoard.getChecker(checker.getPosition()), destination);
    }

    public String toString() {
        String s;
        if (checker.getColor() == CheckerPosition.BLACK) s = "Black:";
        else s = "White:";
        s = s + "(" + checker.getPosition() + "-" + destination + ")";
        return s;
    }
}