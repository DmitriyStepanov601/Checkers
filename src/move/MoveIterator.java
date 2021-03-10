package move;

/**
 * A class describing the iterator of moves of checkers
 *
 * @author Dmitriy Stepanov
 */
public class MoveIterator {
    private final MoveList moveList;
    private int current;

    /**
     * Constructor - creating a new move iterator
     *
     * @param moveList - list of moves
     * @see MoveIterator#MoveIterator(MoveList)
     */
    public MoveIterator(MoveList moveList) {
        this.moveList = moveList;
        current = 0;
    }

    public boolean hasNext() {
        return (current < moveList.size());
    }

    public Move next() {
        return moveList.get(current++);
    }
}