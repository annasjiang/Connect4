/*  Name: Anna Jiang
 *  PennKey: annajg
 *  Recitation: 210
 *
 *  the big boi class that implements/draws the board of tiles, 
 *  updates the board with each mouse input, checks for the winner,
 *  and creates the winning screen 
 *
 */

public class Board {
    
    // fields
    private int rows, columns, numMoves, mode, player;
    private String nextPlayer, winner;
    private boolean mouseListeningMode, mouseWasPressedLastUpdate;
    private Tile[][] board;
    
    /* CONSTRUCTOR
     * 
     * sets up canvas size and scale, initializes field variables,
     * takes in int input to decide the player mode of the game
     */ 
    public Board(int mode) {
        PennDraw.setCanvasSize(700, 700);
        PennDraw.setXscale(0, 7);
        PennDraw.setYscale(0, 7);
        
        rows = 7; // num rows
        columns = 6; // num columns
        numMoves = 0; // keeps track of moves; 42 = full board
        player = 0; // keeps track of player; even = P1, odd = P2
        this.mode = mode; // keeps track of which player mode was selected
        
        nextPlayer = "RED";
        winner = "";
        
        mouseListeningMode = true;
        mouseWasPressedLastUpdate = false;
        
        board = new Tile[rows][columns];
    }
    
    /* DRAW EMPTY BOARD
     * 
     * draws an empty board (2D array of tile objects)
     */
    public void draw() {
        PennDraw.clear(3, 116, 185);
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                board[x][y] = new Tile();
                board[x][y].drawEmpty(x, y);
            }
        }
    }
    
    /* UPDATE BOARD
     * 
     * updates the game board based on mouse input and player turn
     */
    public void update() {
        int column = 0; // decided later by mouse press
        
        // randomize first player in one player mode
        if (mode == 1) {
            player = (int) Math.random() * 2; // 50% chance comp is first
            if (player == 0) { // you make first move
                PennDraw.setFont("Marker Felt");
                PennDraw.setFontSize(30);
                PennDraw.setPenColor(230, 80, 36);
                PennDraw.text(0.5 * 7, 0.5, "YOUR TURN");
            }
            if (player == 1) { // computer makes first move
                dropRandom();
                updateNextPlayer();
            } 
        }
        
        // red always starts in Two Player Mode
        if (mode == 2) { 
            PennDraw.setFont("Marker Felt");
            PennDraw.setFontSize(30);
            PennDraw.setPenColor(230, 80, 36);
            PennDraw.text(0.5 * 7, 0.5, "RED'S TURN");
        }
        
        // game loop
        while (!gameOver()) {
            if (mouseListeningMode) {
                if (PennDraw.mousePressed()) { 
                    mouseWasPressedLastUpdate = true; 
                    mouseListeningMode = false;
                    
                    // get column based off mouse pos
                    column = (int) PennDraw.mouseX();
                }
                else {
                    if (mouseWasPressedLastUpdate) { 
                        if (!isFull(column)) { // if column isn't full...
                            if (mode == 1) { // One Player Mode
                                drop(column, player); // p1 drop
                                updateNextPlayer();
                                
                                // check if game over before comp draws
                                if (gameOver()) { 
                                    break;
                                }
                                
                                // delay computer's move
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                
                                dropRandom(); // computer drop
                                updateNextPlayer();
                            }
                            if (mode == 2) { // Two Player Mode
                                drop(column, player); // player drop
                                updateNextPlayer();
                            }
                        }
                        mouseWasPressedLastUpdate = false;
                        mouseListeningMode = true;
                    }
                }
            }
            else {
                mouseListeningMode = true;
            }
        }
    }
    
    /* UPDATE NEXT PLAYER
     * 
     * helper function that keeps track of whose turn it is and the number of moves 
     * made based on the int player and the String nextPlayer
     */
    private void updateNextPlayer() {
        PennDraw.setPenColor(3, 116, 185);
        PennDraw.filledRectangle(0.5 * 7, 0.5, 0.25 * 7, 0.25);
        PennDraw.setFont("Marker Felt");
        PennDraw.setFontSize(30);
        if (player % 2 == 1) { // yellow just went, red next
            nextPlayer = "RED";
            PennDraw.setPenColor(230, 80, 36);
            if (mode == 1) {
                PennDraw.text(0.5 * 7, 0.5, "YOUR TURN");
            }
            if (mode == 2) {
                PennDraw.text(0.5 * 7, 0.5, nextPlayer + "'S TURN");
            }
        }
        if (player % 2 == 0) { // red just went, yellow next
            nextPlayer = "YELLOW";
            PennDraw.setPenColor(251, 201, 61);
            if (mode == 1) {
                PennDraw.text(0.5 * 7, 0.5, "COMPUTER'S TURN");
            }
            if (mode == 2) {
                PennDraw.text(0.5 * 7, 0.5,  nextPlayer + "'S TURN");
            }
        }
        player++;
        numMoves++; 
    }
    
    /* FULL COLUMN CHECKER
     * 
     * helper function that checks if a selected column (input) is full 
     * and returns a boolean
     */
    private boolean isFull(int col) {
        for (int i = 0; i < columns; i++) {
            if (board[col][i].getValue() == 0) { // if any slot empty, col not full
                return false;
            }
        }
        return true;
    }
    
    /* FIND EMPTY SLOT
     * 
     * helper function that checks a specific colomn (input) 
     * for the first empty slot (given that it's not full)
     */
    private int findEmptySlot(int column) {
        int placement = 0;
        for (int i = 0; i < columns; i++) {
            if (board[column][i].getValue() == 0) { // checks for first empty slot
                placement = i;  
                break;
            }
        }
        return placement;
    }
    
    /* PLAYER DROP
     * 
     * helper function that drops a red/yellow tile (based on player input) 
     * in the selected column (based on column input)
     */
    private void drop(int column, int player) {
        if (player % 2 == 0) { // player 1 (red)
            int placement = findEmptySlot(column);
            board[column][placement].drawRed(column, placement);
            return;
        }
        else if (player % 2 == 1) { // player 2 (yellow)
            int placement = findEmptySlot(column);
            board[column][placement].drawYellow(column, placement);
            return;
        }
    }
    
    /* COMPUTER/RANDOM DROP
     * 
     * helper function that drops a yellow tile in a random column
     */
    private void dropRandom() {
        int randomCol = (int) Math.random() * 8;
        while (isFull(randomCol)) {
            // if column is full, keep choosing until you find one that isnt
            randomCol = (int) Math.random() * 8;
        }
        if (!isFull(randomCol)) {
            int placement = findEmptySlot(randomCol);
            board[randomCol][placement].drawYellow(randomCol, placement);
        }
    }
    
    /* GAME OVER
     * 
     * boolean that checks if anyone has won / if the board is full
     */
    public boolean gameOver() {
        return checkVert() || checkHorz() || checkDiag1() || checkDiag2() ||
            checkDraw();
    }
    
    /* CHECK WINNER - VERTICAL
     * 
     * helper function that checks for vertical 4 in a row
     */
    private boolean checkVert() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns - 3; j++) {
                if (board[i][j].getValue() != 0) {
                    if (board[i][j].getValue() == 1 && 
                        board[i][j + 1].getValue() == 1 && 
                        board[i][j + 2].getValue() == 1 && 
                        board[i][j + 3].getValue() == 1) {
                        // System.out.println("red vert");
                        winner = "RED";
                        return true;
                    }
                    else if (board[i][j].getValue() == 2 && 
                             board[i][j + 1].getValue() == 2 && 
                             board[i][j + 2].getValue() == 2 && 
                             board[i][j + 3].getValue() == 2) {
                        // System.out.println("yellow vert");
                        winner = "YELLOW";
                        return true; 
                    }
                }
            }
        }
        return false;
    }
    
    /* CHECK WINNER - HORIZONTAL
     * 
     * helper function that checks for horizontal 4 in a row
     */
    private boolean checkHorz() {
        for (int i = 0; i < rows - 3; i++) {
            for (int j = 0; j < columns; j++) {
                if (board[i][j].getValue() != 0) {
                    if (board[i][j].getValue() == 1 && 
                        board[i + 1][j].getValue() == 1 && 
                        board[i + 2][j].getValue() == 1 && 
                        board[i + 3][j].getValue() == 1) {
                        // System.out.println("red horz");
                        winner = "RED";
                        return true;
                    }
                    else if (board[i][j].getValue() == 2 && 
                             board[i + 1][j].getValue() == 2 && 
                             board[i + 2][j].getValue() == 2 && 
                             board[i + 3][j].getValue() == 2) {
                        // System.out.println("yellow horz");
                        winner = "YELLOW";
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    /* CHECK WINNER - DIAGONAL (/)
     * 
     * helper function that checks for diagonal (/) 4 in a row
     */
    private boolean checkDiag1() {
        for (int i = 0; i < rows - 3; i++) {
            for (int j = 0; j < columns - 3; j++) {
                if (board[i][j].getValue() != 0) {
                    if (board[i][j].getValue() == 1 && 
                        board[i + 1][j + 1].getValue() == 1 && 
                        board[i + 2][j + 2].getValue() == 1 && 
                        board[i + 3][j + 3].getValue() == 1) {
                        // System.out.println("red diag 1");
                        winner = "RED";
                        return true;
                    }
                    else if (board[i][j].getValue() == 2 && 
                             board[i + 1][j + 1].getValue() == 2 && 
                             board[i + 2][j + 2].getValue() == 2 && 
                             board[i + 3][j + 3].getValue() == 2) {
                        // System.out.println("yellow diag 1");
                        winner = "YELLOW";
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    /* CHECK WINNER - DIAGONAL (\)
     * 
     * helper function that checks for diagonal (\) 4 in a row
     */
    private boolean checkDiag2() {
        for (int i = 0; i < rows - 3; i++) {
            for (int j = 3; j < columns; j++) {
                if (board[i][j].getValue() != 0) {
                    if (board[i][j].getValue() == 1 && 
                        board[i + 1][j - 1].getValue() == 1 && 
                        board[i + 2][j - 2].getValue() == 1 && 
                        board[i + 3][j - 3].getValue() == 1) {
                        // System.out.println("red diag 2");
                        winner = "RED";
                        return true;
                    }
                    else if (board[i][j].getValue() == 2 && 
                             board[i + 1][j - 1].getValue() == 2 && 
                             board[i + 2][j - 2].getValue() == 2 && 
                             board[i + 3][j - 3].getValue() == 2) {
                        // System.out.println("yellow diag 2");
                        winner = "YELLOW";
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    /* CHECK DRAW
     * 
     * helper function that checks for a draw (filled board with no winner)
     */
    private boolean checkDraw() {
        return numMoves == 42;
    }
    
    /* GAME OVER SCREEN
     * 
     * draws a game over screen that signifies the end of the game and names the 
     * winner (unless it's a draw); user can press any key to restart the game
     */
    public void gameOverScreen() {
        // delay game over screen
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        PennDraw.clear(3, 116, 185);
        PennDraw.setFont("Marker Felt");
        PennDraw.setPenColor(PennDraw.WHITE);
        PennDraw.setFontSize(25);
        PennDraw.text(0.5 * 7, 0.5 * 7 - 0.5, "game will restart soon");
        PennDraw.setFontSize(50);
        if (checkDraw()) { // draw
            PennDraw.setPenColor(PennDraw.WHITE);
            PennDraw.text(0.5 * 7, 0.5 * 7, "NO MOVES LEFT - DRAW");
        }
        else if (winner.equals("RED")) { // red won
            PennDraw.setPenColor(230, 80, 36);
            if (mode == 1) {
                PennDraw.text(0.5 * 7, 0.5 * 7, "YOU WIN!");
            }
            if (mode == 2) {
                PennDraw.text(0.5 * 7, 0.5 * 7, winner + " WINS!");
            }
        }
        else if (winner.equals("YELLOW")) { // yellow won
            PennDraw.setPenColor(251, 201, 61);
            if (mode == 1) {
                PennDraw.text(0.5 * 7, 0.5 * 7, "YOU LOST!");
            }
            if (mode == 2) {
                PennDraw.text(0.5 * 7, 0.5 * 7, winner + " WINS!");
            }
        }
    }
}