package state;

import checkers.CheckerPosition;
import board.Board;
import move.MoveList;

/**
 * Interface describing the state of the checker
 *
 * @author Dmitriy Stepanov
 */
public interface CheckerState {
    boolean findValidMoves(CheckerPosition checker, Board board, MoveList validMoves);

    boolean findValidJumps(CheckerPosition checker, Board board, MoveList validJumps);
}