package move;

import checkers.CheckerPosition;
import board.Board;
import logic.Coordinate;

import java.io.Serializable;

/**
 * A class that describes the movement of checkers
 *
 * @author Dmitriy Stepanov
 */
public abstract class Move implements Serializable {
    protected Coordinate destination;
    protected CheckerPosition checker;
    protected Move next = null;

    public abstract boolean isJump();

    public abstract String toString();

    public abstract Move copy(Board newBoard);

    public abstract Move copy();

    public CheckerPosition getChecker() {
        return checker;
    }

    public Coordinate getDestination() {
        return destination;
    }

    public void setNext(Move next) {
        this.next = next;
    }

    public Move getNext() {
        return next;
    }
}