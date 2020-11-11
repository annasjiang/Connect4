/*  Name: Anna Jiang
 *  PennKey: annajg
 *  Recitation: 210
 *
 *  play a classic game of Connect4 in either One Player or Two Player mode!
 *
 */

public class ConnectFour {
    public static void main(String[] args) {
        PennDraw.setCanvasSize(700, 700);
        
        while (true) {
            TitleScreen title = new TitleScreen();
            title.draw();
            
            Board board = new Board(title.getMode());
            board.draw();
            
            while (!board.gameOver()) {
                board.update();
            }
            board.gameOverScreen();
            
            // delay replay
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

