package state;

import checkers.CheckerPosition;
import board.Board;
import logic.GameSearch;
import move.MoveJump;
import move.MoveList;
import move.MoveNormal;

import java.io.Serializable;

/**
 * A class that describes the normal state of a white checker
 *
 * @author Dmitriy Stepanov
 */
public class NormalStateWhite implements CheckerState, Serializable {
    public boolean findValidMoves(final CheckerPosition c, final Board board, MoveList validMoves) {
        if (!findValidJumps(c, board, validMoves)) {
            if (GameSearch.validWhiteMove(c.getPosition(), c.getPosition().upLeftMove(), board))
                validMoves.add(new MoveNormal(c, c.getPosition().upLeftMove()));

            if (GameSearch.validWhiteMove(c.getPosition(), c.getPosition().upRightMove(), board))
                validMoves.add(new MoveNormal(c, c.getPosition().upRightMove()));
            return false;
        } else
            return true;

    }

    public boolean findValidJumps(CheckerPosition c, Board board, MoveList validJumps) {
        boolean found = false;
        if (GameSearch.validWhiteJump(c.getPosition(), c.getPosition().upLeftJump(), board)) {
            validJumps.add(new MoveJump(c, c.getPosition().upLeftJump()));
            found = true;
        }

        if (GameSearch.validWhiteJump(c.getPosition(), c.getPosition().upRightJump(), board)) {
            validJumps.add(new MoveJump(c, c.getPosition().upRightJump()));
            found = true;
        }
        return found;
    }
}

