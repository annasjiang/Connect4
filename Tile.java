/*  Name: Anna Jiang
 *  PennKey: annajg
 *  Recitation: 210
 *
 *  class that creates a tile object (empty, red, or yellow)
 *
 */

public class Tile {
    
    // fields
    private static double radius;
    private int value; // 0 = empty, 1 = P1 tile, 2 = P2 tile
    
    /* CONSTRUCTOR
     * 
     * initializes radius and value of the tile (arbitrary 0)
     */ 
    public Tile() {
        radius = 1.0 / 2.5;
        value = 0;
    }
    
    /* EMPTY TILE
     * 
     * draws an empty tile (val: 0) at a given point in the board
     * takes in x and y coordinates from array
     */
    public void drawEmpty(double x, double y) {
        PennDraw.setPenColor(PennDraw.WHITE);
        PennDraw.filledCircle(x + 0.5, y + 0.5 + 1, radius);
        value = 0;
    }
    
    /* RED TILE
     * 
     * draws a red tile (val: 1) at a given point in the board
     * takes in x and y coordinates from array
     */
    public void drawRed(double x, double y) {
        PennDraw.setPenColor(230, 80, 36);
        PennDraw.filledCircle(x + 0.5, y + 0.5 + 1, radius);
        value = 1;
    }
    
    /* YELLOW TILE
     * 
     * draws a yellow tile (val: 2) at a given point in the board
     * takes in x and y coordinates from array
     */
    public void drawYellow(double x, double y) {
        PennDraw.setPenColor(251, 201, 61);
        PennDraw.filledCircle(x + 0.5, y + 1.5, radius);
        value = 2;
    }
    
    /* GETTER FUNCTION - VALUE
     * 
     * returns the value of a specific tile
     */
    public int getValue() {
        return value;
    }
}
    
    
    
    