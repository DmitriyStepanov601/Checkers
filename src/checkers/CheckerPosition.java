package checkers;

import board.Board;
import state.CheckerState;
import logic.Coordinate;
import move.MoveList;

import java.io.Serializable;
import javax.swing.ImageIcon;

/**
 * A class that describes the position of the checkers on the board
 *
 * @author Dmitriy Stepanov
 */
public abstract class CheckerPosition implements Serializable {
    public static final int BLACK = 1;
    public static final int WHITE = 2;
    public static final int WHITE_VALUE_NORMAL = 2;
    public static final int BLACK_VALUE_NORMAL = -2;
    public static final int WHITE_VALUE_KING = 3;
    public static final int BLACK_VALUE_KING = -3;

    protected CheckerState checkerState;
    protected Coordinate position;
    protected int value;
    protected String stringRep;

    public abstract boolean isKing();

    public abstract int getColor();

    public abstract void makeKing();

    public abstract CheckerPosition copy();

    public abstract boolean kingRow();

    public abstract ImageIcon getIcon();

    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(Coordinate c) {
        position = c;
    }

    public int getValue() {
        return value;
    }

    public boolean findValidMoves(MoveList moveList, final Board board) {
        return checkerState.findValidMoves(this, board, moveList);
    }

    public boolean findValidJumps(MoveList moveList, final Board board) {
        return checkerState.findValidJumps(this, board, moveList);
    }

    public String toString() {
        return stringRep;
    }
}