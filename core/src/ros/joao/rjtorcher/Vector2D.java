package ros.joao.rjtorcher;

/**
 * Class that contains 2 related numbers (doubles)
 */
public class Vector2D
{
    public double x;
    public double y;

    /**
     * constructor
     * @param x
     * @param y
     */
    public Vector2D (double x, double y)  { this.x =x; this.y= y; }

    /**
     * Copies the objects values
     * @param vector
     */
    public Vector2D (final Vector2D vector) {
        if (vector == null)
            throw new NullPointerException("Vector2D constructor failed");
        this.x = vector.x; this.y = vector.y; }
    public Vector2D() {}

    /**
     * Returns a hard copy of the object
     * @return
     */
    @Override
    public Object clone() {
        return new Vector2D(x,y);
    }

    /**
     * sets both fields
     * @param newX
     * @param newY
     */
    public void setXY(double newX, double newY) {
        this.x = newX; this.y = newY;
    }
}
