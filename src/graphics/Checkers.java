package graphics;
import checkers.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

/**
 * A class that describes the game's graphical interface
 * @author Dmitriy Stepanov
 */
public class Checkers extends JFrame implements MouseListener, MouseMotionListener {
    public CheckersBoard pan;
    public ArrayList<Board> boardHistory = new ArrayList<>();

    private int clickedHere = 0;
    private int FromPawnIndex = 0;
    private int ToPawnIndex = 0;

    private final int userColor = CheckerPosition.WHITE;
    private final int thinkDepth = 2;

    private boolean alreadyMoved = true;
    private boolean moving;

    private int nbrBack = 0;
    private int nbrForward = 0;

    private boolean isBack = false;
    private String output = "";
    public int currentPositionInBoardHistory = 0;
    private static int algorithm = 1;

    /**
     * Constructor - creating a new game
     * @see Checkers#Checkers()
     */
    public Checkers() {
        setTitle("Checkers");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);

        Image windowIcon = loadImage("/englishcheckers.png");
        setIconImage(windowIcon);

        pan = new CheckersBoard();
        setSize(605, 650);
        setResizable(false);
        setLocationRelativeTo(null);

        createMenu();
        add(pan);

        addMouseListener(this);
        addMouseMotionListener(this);
        setVisible(true);
    }

    public static BufferedImage loadImage(String path){
        try {
            return ImageIO.read(Checkers.class.getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    public void createMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu game = new JMenu("Game");
        menuBar.add(game);
        JMenu levels = new JMenu("Difficulty");
        game.add(levels);

        ButtonGroup levelsGroup = new ButtonGroup();
        JRadioButtonMenuItem easy = new JRadioButtonMenuItem("easy");
        JRadioButtonMenuItem medium = new JRadioButtonMenuItem("medium");
        JRadioButtonMenuItem hard = new JRadioButtonMenuItem("hard");
        levelsGroup.add(easy);
        levelsGroup.add(medium);
        levelsGroup.add(hard);

        levels.add(easy);
        levels.add(medium);
        levels.add(hard);
        easy.setSelected(true);
        medium.setSelected(false);
        hard.setSelected(false);

        JMenu settings = new JMenu("Settings");
        game.add(settings);

        ButtonGroup algorithmGroup = new ButtonGroup();
        JMenu levelAi = new JMenu("Level AI");
        JRadioButtonMenuItem rbMiniMax = new JRadioButtonMenuItem("medium"); // minimax
        rbMiniMax.addActionListener(e -> Checkers.algorithm = 1);
        JRadioButtonMenuItem rbMiniMaxAB = new JRadioButtonMenuItem("hard");  // minimax AB
        rbMiniMaxAB.addActionListener(e -> Checkers.algorithm = 2);
        algorithmGroup.add(rbMiniMax);
        algorithmGroup.add(rbMiniMaxAB);

        if(Checkers.algorithm == 1) {
            rbMiniMax.setSelected(true);
            rbMiniMaxAB.setSelected(false);
        } else {
            rbMiniMax.setSelected(false);
            rbMiniMaxAB.setSelected(true);
        }

        levelAi.add(rbMiniMax);
        levelAi.add(rbMiniMaxAB);
        settings.add(levelAi);

        JMenu options = new JMenu("Options");
        settings.add(options);

        JMenuItem style = new JMenuItem("background");
        style.addActionListener(e -> {
            String[] themes = {"BrownCanary", "BlackWhite", "GreenCanary"};
            String[] Algorithms = {"MiniMax", "MiniMax AB"};
            JComboBox<String> ThemesCombo = new JComboBox<>(themes);

            if (pan.theme == 1) {
                ThemesCombo.setSelectedIndex(0);
            } else if (pan.theme == 2) {
                ThemesCombo.setSelectedIndex(1);
            } else if (pan.theme == 3) {
                ThemesCombo.setSelectedIndex(2);
            }

            final JComponent[] inputs = new JComponent[]{ new JLabel("Styles Background"), ThemesCombo };
            JOptionPane.showMessageDialog(Checkers.this, inputs, "Background",
                    JOptionPane.PLAIN_MESSAGE);

            switch (ThemesCombo.getSelectedItem().toString()) {
                case "BrownCanary":
                    pan.theme = 1;
                    pan.repaint();
                    break;
                case "BlackWhite":
                    pan.theme = 2;
                    pan.repaint();
                    break;
                case "GreenCanary":
                    pan.theme = 3;
                    pan.repaint();
                    break;
            }
        });

        JMenuItem back = new JMenuItem("back");
        back.addActionListener(e -> {
            if ((currentPositionInBoardHistory - (++nbrBack)) >= 0) {
                isBack = true;
                pan.boardO = boardHistory.get(currentPositionInBoardHistory - nbrBack);
                currentPositionInBoardHistory = currentPositionInBoardHistory - nbrBack;
                pan.repaint();
            }
            nbrBack = 0;
        });

        JMenuItem forward = new JMenuItem("forward");
        forward.addActionListener(e -> {
            if (currentPositionInBoardHistory + (++nbrForward) < boardHistory.size()) {
                pan.boardO = boardHistory.get(currentPositionInBoardHistory + nbrForward);
                currentPositionInBoardHistory = currentPositionInBoardHistory + nbrForward;
            }
            nbrForward = 0;
            pan.repaint();
        });

        JMenuItem resetBoard = new JMenuItem("clear board");
        resetBoard.addActionListener(e -> {
            pan.pawns.clear();
            pan.boardO.initialize();
            boardHistory.clear();
            currentPositionInBoardHistory = 0;
            isBack = false;
            pan.repaint();
        });

        options.add(back);
        options.add(forward);
        options.add(resetBoard);
        options.add(style);

        JMenu importExport = new JMenu("Import / Export");
        settings.add(importExport);
        JMenuItem importGame = new JMenuItem("import game");
        importGame.addActionListener(e -> {
            File folder = new File("savegames");
            File[] listOfFiles = folder.listFiles();
            int nbrfile = 0;

            for (File listOfFile : listOfFiles) {
                if (listOfFile.isFile()) {
                    nbrfile++;
                }
            }

            String[] comboTypes = new String[nbrfile];

            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    comboTypes[i] = listOfFiles[i].getName();
                } else if (listOfFiles[i].isDirectory()) {
                    System.out.println("Directory " + listOfFiles[i].getName());
                }
            }

            ArrayList<String> filename = new ArrayList<>();
            JComboBox<String> comboTypesList = new JComboBox<>(comboTypes);

            final JComponent[] inputs = new JComponent[]{ new JLabel("FileName"), comboTypesList };
            JOptionPane.showMessageDialog(Checkers.this, inputs,
                    "Import a game", JOptionPane.PLAIN_MESSAGE);
            importGameFromSavegames(comboTypesList.getSelectedItem().toString());
        });

        JMenuItem exportGame = new JMenuItem("export game", null);
        exportGame.addActionListener(e -> {
            System.out.println("export Game");
            JTextField fileName = new JTextField();

            final JComponent[] inputs = new JComponent[]{ new JLabel("FileName"), fileName };
            JOptionPane.showMessageDialog(Checkers.this, inputs,
                    "Export a game", JOptionPane.PLAIN_MESSAGE);

            if(fileName.getText().compareTo("") != 0){
                try {
                    SaveHistoryOfGame(fileName.getText());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else{
                System.out.println("no name file entered");
            }
        });

        importExport.add(importGame);
        importExport.add(exportGame);
        this.setJMenuBar(menuBar);
    }

    public static void main(String[] args) {
        new Checkers();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int test = 0;

        for (int i = 0; i < pan.allBoardPoints.size(); i++) {
            if (e.getX() > (int) pan.allBoardPoints.get(i).getX()
                    && e.getX() < (int) (pan.allBoardPoints.get(i).getX() + 75)
                    && e.getY() - 40 < (int) (pan.allBoardPoints.get(i).getY() + 75)
                    && e.getY() - 40 > (int) (pan.allBoardPoints.get(i).getY())) {
                test = (i + 1);
                break;
            }
        }

        for (int i = 0; i < pan.pawns.size(); i++) {
            if (e.getX() > (int) pan.pawns.get(i).point.getX() && e.getX() <
                    (int) (pan.pawns.get(i).point.getX() + 75)
                    && e.getY() - 27 < (int) (pan.pawns.get(i).point.getY() + 75) &&
                    e.getY() - 27 > (int) (pan.pawns.get(i).point.getY())) {
                clickedHere = i;
                break;
            }
        }

        MoveList validMoves;
        validMoves = GameSearch.findAllValidMoves(pan.boardO, userColor);
        pan.possibleMovesIndex.clear();

        for (int i = 0; i < validMoves.size(); i++) {
            if ((test - 1) >= 0 && pan.boardO.getChecker(new Coordinate(test)) != null &&
                    validMoves.get(i).getChecker().getPosition() ==
                            pan.boardO.getChecker(new Coordinate(test)).getPosition()) {
                pan.possibleMovesIndex.add(validMoves.get(i).getDestination().get());
                pan.repaint();
            }
        }

        if (e.getX() > 690 && e.getX() < 690 + 54 && e.getY() - 27 > 530 && e.getY() - 27 < 530 + 54) {
            pan.pawns.clear();
            pan.boardO.initialize();
            boardHistory.clear();
            currentPositionInBoardHistory = 0;
            isBack = false;
            pan.repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {
        if (alreadyMoved) {
            FromPawnIndex = clickedHere + 1;

            for (int i = 0; i < pan.allBoardPoints.size(); i++) {
                if (e.getX() > (int) pan.allBoardPoints.get(i).getX() && e.getX() <
                        (int) (pan.allBoardPoints.get(i).getX() + 75)
                        && e.getY() - 27 < (int) (pan.allBoardPoints.get(i).getY() + 75) && e.getY() - 27 >
                        (int) (pan.allBoardPoints.get(i).getY())) {
                    ToPawnIndex = i + 1;
                    break;
                }
            }

            if (clickedHere >= 0) {
                moveUser(new Coordinate((pan.pawns.get(clickedHere).posindex)), new Coordinate(ToPawnIndex));
            }

            pan.newBoard = true;
            pan.repaint();
            clickedHere = -48;
            setCursor(Cursor.DEFAULT_CURSOR);
            alreadyMoved = false;
            moving = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {
        alreadyMoved = true;
        pan.possibleMovesIndex.clear();
        pan.bestMovesFromHelp.clear();

        if (!moving) {
            for (int i = 0; i < pan.pawns.size(); i++) {
                if (e.getX() > (int) pan.pawns.get(i).point.getX()
                        && e.getX() < (int) (pan.pawns.get(i).point.getX() + 75)
                        && e.getY() - 27 < (int) (pan.pawns.get(i).point.getY() + 75)
                        && e.getY() - 27 > (int) (pan.pawns.get(i).point.getY())) {
                    clickedHere = i;
                    break;
                }
            }
        }

        if (clickedHere >= 0) {
            pan.newBoard = false;
            moving = true;
            pan.pawns.get(clickedHere).setP(new Point(e.getX() - 75 / 2, e.getY() - 40 - 75 / 2));
            pan.repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {}

    public void moveUser(Coordinate from, Coordinate to) {
        pan.turn = "your turn";
        Move move = validateUserMove(from, to);

        if (move == null) {
            System.out.println(" The Move is not Valid ");
            outputText("Invalid move.");
        } else if (move.isJump()) {
            if (isBack) {
                removeBoardsAfter(currentPositionInBoardHistory + 1);
                isBack = false;
                currentPositionInBoardHistory = boardHistory.size() + 1;
            } else if (boardHistory.size() == 0) {
                currentPositionInBoardHistory = 0;
                boardHistory.add(pan.boardO);
            }

            pan.boardO = GameSearch.executeUserJump(move, pan.boardO);
            CheckerPosition multipleJumpsChecker = pan.boardO.getChecker(move.getDestination());

            if (mandatoryJump(multipleJumpsChecker, pan.boardO)) {
                outputText("A multiple jump must be completed.");
            } else {
                computerMoves();
            }
        } else
         if (GameSearch.existJump(pan.boardO, userColor)) {
                outputText("Invalid move. If you can jump, you must.");
            } else {
                if (isBack) {
                    removeBoardsAfter(currentPositionInBoardHistory + 1);
                    isBack = false;
                    currentPositionInBoardHistory = boardHistory.size() + 1;
                } else if (boardHistory.size() == 0) {
                    currentPositionInBoardHistory = 0;
                    boardHistory.add(pan.boardO);
                }

                pan.boardO = GameSearch.executeMove(move, pan.boardO);
                pan.user_move = move.toString();
                computerMoves();
            }
    }

    public Move validateUserMove(Coordinate from, Coordinate to) {
        Move move = null;
        CheckerPosition checker = pan.boardO.getChecker(from);

        if (checker != null) {
            if (checker.getColor() == CheckerPosition.WHITE) {
                if (checker.getValue() == CheckerPosition.WHITE_VALUE_KING) {
                    if (Math.abs(from.row() - to.row()) == 1) {
                        if (GameSearch.validKingMove(from, to, pan.boardO)) {
                            move = new MoveNormal(checker, to);
                        }
                    } else if (GameSearch.validKingJump(from, to, pan.boardO)) {
                        move = new MoveJump(checker, to);
                    }
                } else // Normal white checker.
                 if (from.row() - to.row() == 1) {
                     if (GameSearch.validWhiteMove(from, to, pan.boardO)) {
                         move = new MoveNormal(checker, to);
                     }
                 } else if (GameSearch.validWhiteJump(from, to, pan.boardO)) {
                     move = new MoveJump(checker, to);
                 }
            }
        }
        return move;
    }

    private void outputText(String s) {
        output = "\n>>> " + s;
        System.out.println("" + (output));
    }

    private boolean mandatoryJump(CheckerPosition checker, Board board) {
        MoveList movelist = new MoveList();
        checker.findValidJumps(movelist, board);
        return movelist.size() != 0;
    }

    public void computerMoves() {
        pan.turn = " Computer turn ";
        int computerColor = CheckerPosition.BLACK;
        MoveList validMoves = GameSearch.findAllValidMoves(pan.boardO, computerColor);

        if (validMoves.size() == 0) {
            ImageIcon win = new ImageIcon(loadImage("/win.png"));
            JOptionPane.showMessageDialog(this, "\nCongratulations!"
                    + "You win\n", "Victory", JOptionPane.INFORMATION_MESSAGE, win);
            outputText("You win.");
        } else {
            pan.boardO.getHistory().reset();
            Board comBoard = null;

            if (algorithm == 2) {
                comBoard = GameSearch.minimaxAB(pan.boardO, thinkDepth, computerColor,
                        GameSearch.minusInfinityBoard(),
                        GameSearch.plusInfinityBoard());
            }

            if (algorithm == 1) {
                comBoard = GameSearch.minimax(pan.boardO, thinkDepth, computerColor);
            }

            Move move = comBoard.getHistory().first();
            pan.boardO = GameSearch.executeMove(move, pan.boardO);
            boolean isForward = false;

            if (!isBack && !isForward) {
                boardHistory.add(pan.boardO);
                currentPositionInBoardHistory = boardHistory.size() - 1;
            }

            MoveIterator iterator = pan.boardO.getHistory().getIterator();
            StringBuilder moves = new StringBuilder();

            while (iterator.hasNext()) {
                moves.append(iterator.next());
                if (iterator.hasNext()) {
                    moves.append(" , ");
                }
            }

            pan.computer_move = moves.toString();
            int s = moves.indexOf("(");
            int ss = moves.indexOf(")");
            String[] values = moves.substring(s + 1, ss).split("-");
            outputText("the computer make this move : " + moves);
            validMoves = GameSearch.findAllValidMoves(pan.boardO, userColor);

            if (validMoves.size() == 0) {
                ImageIcon defeat = new ImageIcon(loadImage("/lose.png"));
                JOptionPane.showMessageDialog(this, "Computer wins!",
                        "Defeat", JOptionPane.INFORMATION_MESSAGE, defeat);
                outputText("Sorry. The computer wins.");
            }
        }
    }

    private void removeBoardsAfter(int i) {
        int tile = boardHistory.size();
        if (tile > i) {
            boardHistory.subList(i, tile).clear();
        }
    }

    private void importGameFromSavegames(String toString) {
        ArrayList<Board> importedHistory;
        try {
            FileInputStream fileIn = new FileInputStream("savegames/" + toString);
            ObjectInputStream in = new ObjectInputStream(fileIn);

            //noinspection unchecked
            importedHistory = (ArrayList<Board>) in.readObject();
            boardHistory = importedHistory;
            currentPositionInBoardHistory = boardHistory.size() - 1;
            pan.boardO = boardHistory.get(boardHistory.size() - 1);

            pan.repaint();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException i) {
            i.printStackTrace();
        }
    }

    private void SaveHistoryOfGame(String toString) throws IOException {
        FileOutputStream fos = new FileOutputStream("savegames/" + toString + ".game");
        ObjectOutputStream os = new ObjectOutputStream(fos);
        try {
            os.writeObject(boardHistory);
            os.flush();
        } finally {
            try {
                os.close();
            } finally {
                fos.close();
            }
        }
    }
}
