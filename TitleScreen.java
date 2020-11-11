/*  Name: Anna Jiang
 *  PennKey: annajg
 *  Recitation: 210
 *
 *  class that draws a title screen and takes a mouse input to decide
 *  the game mode of Connect4 (One Player vs Two Player)
 *
 */

public class TitleScreen {
    
    // fields
    private boolean mouseListeningMode;
    private int mode;
    
    /* CONSTRUCTOR
     * 
     * initializes mouseListeningMode and player mode (arbitrary 0)
     */ 
    public TitleScreen() {  
        mouseListeningMode = true;
        mode = 0;
    }
    
    /* DRAW TITLE SCREEN
     * 
     * draws title screen with 2 options: 1 player or 2 player
     * takes mouse input to set the player mode
     */
    public void draw() { 
        PennDraw.setCanvasSize(700, 700);
        PennDraw.clear(3, 116, 185);
        PennDraw.setFont("Marker Felt");
        PennDraw.setPenColor(PennDraw.WHITE);
        
        PennDraw.setFontSize(75);
        PennDraw.text(0.5, 0.75, "Connect Four");
        
        PennDraw.setFontSize(50);
        PennDraw.text(0.5, 0.5, "One Player");
        PennDraw.text(0.5, 0.25, "Two Players");
        
        while (mouseListeningMode) {
            if (PennDraw.mousePressed()) {
                // get the coordinates of the mouse cursor
                double y = PennDraw.mouseY();
                
                // check which region the mouse click was in
                if (y > 0.375 && y < 0.625) {
                    PennDraw.setPenColor(230, 80, 36);
                    PennDraw.text(0.5, 0.5, "One Player");
                    mode = 1;
                    
                    // delay board by 1 second
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else if (y < 0.375) { 
                    PennDraw.setPenColor(251, 201, 61);
                    PennDraw.text(0.5, 0.25, "Two Players");
                    mode = 2;
                    
                    // delay board by 1 second
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                mouseListeningMode = false;
            }
        }
    }
    
    
    /* GETTER FUNCTION - MODE
     * 
     * returns the mode to determine the game play
     */
    public int getMode() {
        return mode;
    }
}