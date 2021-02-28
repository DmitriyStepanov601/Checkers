package graphics;
import java.awt.Point;
import javax.swing.ImageIcon;

/**
 * A class that describes a checker
 * @author Dmitriy Stepanov
 */
public class Pawn {
    public Point point;
    public int posindex;
    public ImageIcon image;
    public int type;

    /**
     * Constructor - create a new checkers
     * @param p - the point where the checker is located
     * @param image - image of the checkers
     * @see Pawn#Pawn(Point,ImageIcon)
     */
    public Pawn(Point p, ImageIcon image) {
        this.point = p;
        this.image = image;
        posindex = 0;
    }

    public Point getP() {
        return point;
    }
    public void setP(Point p) {
        this.point = p;
    }
    public ImageIcon getImage() {
        return image;
    }
    public void setImage(ImageIcon image) {
        this.image = image;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public void movePawn(int from , int to){}
}
