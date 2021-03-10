package move;

import java.io.Serializable;

/**
 * A class that describes the list of moves of the checkers
 *
 * @author Dmitriy Stepanov
 */
public class MoveList implements Serializable {
    private Move moveList;
    private Move last;
    private int listSize;

    /**
     * Constructor - creating a new list of moves
     *
     * @see MoveList#MoveList()
     */
    public MoveList() {
        listSize = 0;
        moveList = null;
    }

    public void add(Move c) {
        if (moveList == null) {
            moveList = c;
        } else {
            last.setNext(c);
        }

        last = c;
        listSize++;
    }

    public void remove(Move move) {
        if (move == moveList) {
            moveList = moveList.getNext();
            listSize--;
        } else {
            MoveIterator iterator = getIterator();
            Move previous = iterator.next();
            Move current;

            while (iterator.hasNext()) {
                current = iterator.next();

                if (move == current) {
                    previous.setNext(current.getNext());
                    listSize--;
                }
            }
        }
    }

    public int size() {
        return listSize;
    }

    public Move first() {
        return moveList;
    }

    public void reset() {
        listSize = 0;
        moveList = null;
    }

    public Move get(int index) throws IndexOutOfBoundsException {
        int current = 0;
        Move move = moveList;

        while (current != index) {
            move = move.getNext();

            if (move == null) throw new IndexOutOfBoundsException();
            current++;
        }
        return move;
    }

    public MoveIterator getIterator() {
        return new MoveIterator(this);
    }

    public MoveList copy() {
        MoveIterator iterator = getIterator();
        MoveList newList = new MoveList();

        while (iterator.hasNext())
            newList.add(iterator.next().copy());
        return newList;
    }

    public String toString() {
        MoveIterator iterator = getIterator();
        StringBuilder s = new StringBuilder("Movelist: ");

        while (iterator.hasNext()) {
            s.append(iterator.next().toString());
            if (iterator.hasNext()) s.append(" , ");
        }
        return s.toString();
    }
}