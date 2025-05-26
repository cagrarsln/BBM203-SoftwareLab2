import java.io.Serializable;

public class Point implements Serializable {
    static final long serialVersionUID = 22L;
    
    public int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double distance(Point other) {
        int dx = this.x - other.x;
        int dy = this.y - other.y;
        return Math.sqrt((dx * dx) + (dy * dy));
    }


}
