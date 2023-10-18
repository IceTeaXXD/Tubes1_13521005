import javafx.scene.control.Button;

public class MinimaxBot extends Bot {
    public long endTime;
    private boolean isFallback = false;

    public MinimaxBot(String symbol) {
        this.symbol = symbol;
        this.enemySymbol = symbol.equals("X") ? "O" : "X";
    }

    public int[] move(Button[][] board, int roundsLeft) {
        int[] move = new int[2];
        this.endTime = System.currentTimeMillis() + 5000; // 5 seconds from now
        int[] selection = minimax(board, 5, roundsLeft, Integer.MIN_VALUE, Integer.MAX_VALUE, true, 1);
        move[0] = selection[1];
        move[1] = selection[2];
        if (this.isFallback) {
            int[] fallback = this.fallbackHC(board, roundsLeft);
            move[0] = fallback[0];
            move[1] = fallback[1];
            System.out.println("fallback");
        }

        return move;
    }

    // Minimax evaluation based on the number of O and X in position
    public int minimaxEvaluation(Button[][] board) {
        int evaluation = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j].getText().equals(symbol)) {
                    evaluation++;
                }
                if (board[i][j].getText().equals(enemySymbol)) {
                    evaluation--;
                }
            }
        }
        return evaluation;
    }

    // Create virtual board to evaluate
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

    // Minimax algorithm
    public int[] minimax(Button[][] board, int depth, int roundsLeft, int alpha, int beta, boolean bot, int curDepth) {
        // basis
        if (depth == 0 || roundsLeft == 0 || System.currentTimeMillis() >= endTime) {
            if (System.currentTimeMillis() >= endTime) {
                this.isFallback = true;
            }
            int evaluation = minimaxEvaluation(board);
            return new int[] { evaluation, -1, -1 };
        }

        // recursion for bot's turn
        if (bot) {
            int maxEval = Integer.MIN_VALUE;
            int bestX = -1;
            int bestY = -1;

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (board[i][j].getText().equals("")) {
                        Button[][] newBoard = updateVirtualBoard(i, j, board, bot);
                        int[] selection = minimax(newBoard, depth - 1, roundsLeft - 1, alpha, beta, !bot, curDepth + 1);
                        int eval = selection[0];

                        if (eval > maxEval) {
                            maxEval = eval;
                            bestX = i;
                            bestY = j;
                        }
                        
                        // Pruning, ignore if beta is smaller than alpha
                        alpha = Math.max(alpha, eval);
                        if ((beta < Integer.MAX_VALUE || alpha > Integer.MIN_VALUE ) && beta <= alpha) {
                            break;
                        }
                    }
                }
            }

            return new int[] { maxEval, bestX, bestY };
        }
        // recursion for player's turn
        else {
            int minEval = Integer.MAX_VALUE;
            int bestX = -1;
            int bestY = -1;

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (board[i][j].getText().equals("")) {
                        Button[][] newBoard = updateVirtualBoard(i, j, board, bot);
                        int[] selection = minimax(newBoard, depth - 1, roundsLeft, alpha, beta, !bot, curDepth + 1);
                        int eval = selection[0];

                        if (eval < minEval) {
                            minEval = eval;
                            bestX = i;
                            bestY = j;
                        }

                        // Pruning, ignore if beta is smaller than alpha
                        beta = Math.min(beta, eval);
                        if ((beta < Integer.MAX_VALUE || alpha > Integer.MIN_VALUE) && beta <= alpha) {
                            break;
                        }
                    }
                }
            }

            return new int[] { minEval, bestX, bestY };
        }
    }

    public int[] fallbackHC(Button[][] board, int roundsLeft) {
        int[] selection = new int[2];
        int eval = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j].getText().equals("")) {
                    int res = 0;
                    if (i != 0 && board[i - 1][j].getText().equals(enemySymbol)) {
                        res++;
                    }
                    if (j != 0 && board[i][j - 1].getText().equals(enemySymbol)) {
                        res++;
                    }
                    if (i != 7 && board[i + 1][j].getText().equals(enemySymbol)) {
                        res++;
                    }
                    if (j != 7 && board[i][j + 1].getText().equals(enemySymbol)) {
                        res++;
                    }
                    if (res > eval) {
                        eval = res;
                        selection[0] = i;
                        selection[1] = j;
                    }
                }
            }
        }
        return selection;
    }
}
