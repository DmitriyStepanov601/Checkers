package board;

public class BoardIterator {
    private final BoardList boardList;
    private int current;

    public BoardIterator(BoardList boardList) {
        this.boardList = boardList;
        current = 0;
    }

    public boolean hasNext() {
        return (current < boardList.size());
    }

    public Board next() {
        return boardList.get(current++);
    }
}