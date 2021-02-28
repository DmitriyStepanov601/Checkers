package graphics;
import checkers.Board;
import checkers.Coordinate;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * A class that describes a game board
 * @author Dmitriy Stepanov
 */
public class CheckersBoard extends JPanel {
    private final static String BLACK_PAWN = "/black_pawn.png";
    private final static String WHITE_PAWN = "/white_pawn.png";
    private final static String BOARD = "/board.png";
    private final static String BOARD_1 = "/board1.png";
    private final static String BOARD_2 = "/board2.png";
    private final static String WHITE_KING = "/white_pawn_king.png";
    private final static String BLACK_KING = "/black_pawn_king.png";

    private final ImageIcon black_pawn;
    private final ImageIcon white_pawn;
    private final ImageIcon board;
    private final ImageIcon board1;
    private final ImageIcon board2;
    private final ImageIcon black_king;
    private final ImageIcon white_king;

    private ImageIcon indicatePosition;
    public boolean newBoard = true;

    private final ArrayList<Point> blackPositions = new ArrayList<>();
    private final ArrayList<Point> whitePositions = new ArrayList<>();

    public ArrayList<Pawn> pawns = new ArrayList<>();
    public ArrayList<Point> allBoardPoints = new ArrayList<>();
    public ArrayList<Integer> possibleMovesIndex = new ArrayList<>();
    public ArrayList<Integer> bestMovesFromHelp = new ArrayList<>();

    public Board boardO = new Board();
    public String turn = "your turn";
    public String user_move = "";
    public String computer_move = "";
    public int theme = 1;

    /**
     * Constructor - creating a new game board
     * @see CheckersBoard#CheckersBoard()
     */
    public CheckersBoard() {
        initAllPositions();
        black_pawn = new ImageIcon(Checkers.loadImage(BLACK_PAWN));
        white_pawn = new ImageIcon(Checkers.loadImage(WHITE_PAWN));
        board = new ImageIcon(Checkers.loadImage(BOARD));
        board1 = new ImageIcon(Checkers.loadImage(BOARD_1));
        board2 = new ImageIcon(Checkers.loadImage(BOARD_2));
        white_king = new ImageIcon(Checkers.loadImage(WHITE_KING));
        black_king = new ImageIcon(Checkers.loadImage(BLACK_KING));
        boardO.initialize();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (theme == 1) {
            board.paintIcon(this, g, 0, 0);
        } else if (theme == 2) {
            board1.paintIcon(this, g, 0, 0);
        } else if (theme == 3) {
            board2.paintIcon(this, g, 0, 0);
        }

        if (newBoard) {
            drawBoard(g);
        }

        drawPawns(g);
        drawPossibleMoves(g);
        drawBestMovesFromHelp(g);
    }

    public void drawInitBoard(Graphics g) {
        for (Point p : allBoardPoints) {
            System.out.println("" + p.getX() + " " + p.getY());
            black_pawn.paintIcon(this, g, (int) p.getX(), (int) p.getY());
        }
    }

    private void initAllPositions() {
        int lignes = 0;
        for (int i = 0; i < 32; i++) {
            Point blackpos1 = new Point(5, 5);
            if (i != 0 && i % 4 == 0) {
                lignes++;
            }

            if (lignes % 2 == 0) {
                blackpos1.x = (i % 4) * 75 * 2 + 5;
                blackpos1.y = lignes * 75 + 5;
            } else {
                blackpos1.x = (i % 4) * 75 * 2 + 5 + 75;
                blackpos1.y = lignes * 75 + 5;
            }
            allBoardPoints.add(blackpos1);
        }
    }

    public void drawPawnAtPosition(Graphics g, int pos, int player) {
        if (player == 1) {
            Pawn p = new Pawn(allBoardPoints.get(pos), black_pawn);
            p.posindex = pos;
            pawns.add(p);
        } else {
            Pawn p = new Pawn(allBoardPoints.get(pos), white_pawn);
            p.posindex = pos;
            pawns.add(p);
        }
    }

    public void drawPawns(Graphics g) {
        for (Pawn p : pawns) {
            p.image.paintIcon(this, g, (int) p.point.getX(), (int) p.point.getY());
        }
    }

    public void drawBoard(Graphics g) {
        pawns.clear();
        for (int i = 1; i < 33; i++) {
            Coordinate c = new Coordinate(i);
            int color = 0;
            if (boardO.getChecker(c) != null) {
                color = boardO.getChecker(c).getColor();
            }

            if (color == 2) {
                Pawn p;
                if (boardO.getChecker(c).isKing()) {
                    p = new Pawn(allBoardPoints.get(i - 1), white_king);
                } else {
                    p = new Pawn(allBoardPoints.get(i - 1), white_pawn);
                }

                p.posindex = i;
                pawns.add(p);
            }

            if (color == 1) {
                Pawn p;
                if (boardO.getChecker(c).isKing()) {
                    p = new Pawn(allBoardPoints.get(i - 1), black_king);
                } else {
                    p = new Pawn(allBoardPoints.get(i - 1), black_pawn);
                }

                p.posindex = i;
                pawns.add(p);
            }
        }
    }

    private void drawBestMovesFromHelp(Graphics g) {
        for (Integer integer : bestMovesFromHelp) {
            indicatePosition.paintIcon(this, g, (int) allBoardPoints.get(integer - 1).getX() + 5,
                    (int) allBoardPoints.get(integer - 1).getY() + 5);
        }
    }

    public void drawPossibleMoves(Graphics g) {
        for (Integer integer : possibleMovesIndex) {
            indicatePosition.paintIcon(this, g, (int) allBoardPoints.get(integer - 1).getX() + 5,
                    (int) allBoardPoints.get(integer - 1).getY() + 5);
        }
    }

    public Pawn getPawnOfPosition(int pos) {
        for (Pawn p : pawns) {
            if (p.posindex == pos) {
                return p;
            }
        }
        return null;
    }
}