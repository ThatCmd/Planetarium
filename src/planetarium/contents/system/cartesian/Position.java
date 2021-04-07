/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetarium.contents.system.cartesian;

/**
 * Semplice rappresentazione di un punto del piano.
 * Rappresentazione in coordinate 2D.
 *
 * @author TTT
 */
public class Position {

    private final double x;
    private final double y;

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Position) {
            Position p = (Position) obj;
            return p.x == x && p.y == y;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 47 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        return hash;
    }

    @Override
    public String toString() {
        return "(" + x + " , " + y + ")";
    }

    /**
     * Crea una nuova posizione pesata moltiplicando una posizione per la massa.
     *
     * @param p La posizione da moltiplicare
     * @param mass La massa
     * @return La nuova posizione in base alla massa.
     */
    public static Position multiply(Position p, double mass) {
        return p != null ? new Position(p.getX() * mass, p.getY() * mass) : new Position(0, 0);
    }

    /**
     * Crea una nuova posizione pesata dividendo una posizione per la massa.
     *
     * @param p La posizione da moltiplicare
     * @param mass La massa
     * @return La nuova posizione in base alla massa.
     */
    public static Position divide(Position p, double mass) {
        return p != null ? new Position(p.getX() / mass, p.getY() / mass) : new Position(0, 0);
    }

    /**
     * Crea una nuova posizione come risultato della somma di altre due
     * posizioni.
     *
     * @param p1 Posizione 1.
     * @param p2 Posizione 2.
     * @return Posizione sommata.
     */
    public static Position sum(Position p1, Position p2) {
        return p1 == null ? (p2 == null ? null : p2) : (p2 == null ? new Position(p1.getX(), p1.getY()) : new Position(p1.getX() + p2.getX(), p1.getY() + p2.getY()));
    }
}
