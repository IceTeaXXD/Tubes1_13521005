import java.util.*;
import javafx.scene.control.Button;


public class StochasticHCBot extends Bot {
    private static final int N_MAX = 64;
    private Random random = new Random();

    public StochasticHCBot(String symbol) {
        this.symbol = symbol;
        this.enemySymbol = symbol.equals("X") ? "O" : "X";
    }

    public int[] move(Button[][] board, int roundsLeft) {
        int[] selection = new int[2];
        int eval = Integer.MIN_VALUE;
        for (int i = 0; i < N_MAX; i++) {
            // get a random move
            int[] move = getRandomMove(board);
            // evaluate the board
            int currEval = evaluate(updateVirtualBoard(move[0], move[1], board, true));
            if (currEval > eval) {
                eval = currEval;
                selection[0] = move[0];
                selection[1] = move[1];
            }
        }
        return selection;
    }

    public int evaluate(Button[][] board) {
        // calculate the difference in the number of pieces
        int bot = 0;
        int enemy = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j].getText().equals(symbol)) {
                    bot++;
                } else if (board[i][j].getText().equals(enemySymbol)) {
                    enemy++;
                }
            }
        }
        return bot - enemy;
    }

    public Button[][] updateVirtualBoard(int x, int y, Button[][] board, boolean bot) {
        // Copy the game board
        Button[][] finalBoard = new Button[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                Button copiedButton = new Button();
                copiedButton.setText(board[i][j].getText());
                finalBoard[i][j] = copiedButton;
            }
        }

        // Bot's turn
        if (bot) {
            finalBoard[x][y].setText(symbol);
            if (x > 0 && finalBoard[x - 1][y].getText().equals(enemySymbol)) {
                finalBoard[x - 1][y].setText(symbol);
            }
            if (x < 7 && finalBoard[x + 1][y].getText().equals(enemySymbol)) {
                finalBoard[x + 1][y].setText(symbol);
            }
            if (y > 0 && finalBoard[x][y - 1].getText().equals(enemySymbol)) {
                finalBoard[x][y - 1].setText(symbol);
            }
            if (y < 7 && finalBoard[x][y + 1].getText().equals(enemySymbol)) {
                finalBoard[x][y + 1].setText(symbol);
            }
            // Player's turn
        } else {
            finalBoard[x][y].setText(enemySymbol);
            if (x > 0 && finalBoard[x - 1][y].getText().equals(symbol)) {
                finalBoard[x - 1][y].setText(enemySymbol);
            }
            if (x < 7 && finalBoard[x + 1][y].getText().equals(symbol)) {
                finalBoard[x + 1][y].setText(enemySymbol);
            }
            if (y > 0 && finalBoard[x][y - 1].getText().equals(symbol)) {
                finalBoard[x][y - 1].setText(enemySymbol);
            }
            if (y < 7 && finalBoard[x][y + 1].getText().equals(symbol)) {
                finalBoard[x][y + 1].setText(enemySymbol);
            }
        }
        return finalBoard;
    }

    private int[] getRandomMove(Button[][] board) {
        int[] move = new int[2];
        do {
            move[0] = random.nextInt(8);
            move[1] = random.nextInt(8);
        } while (!board[move[0]][move[1]].getText().equals(""));
        return move;
    }
}
