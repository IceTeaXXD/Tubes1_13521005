import javafx.scene.control.Button;

public class MinimaxBot extends Bot {
    public long endTime;

    public int[] move(Button[][] board, int roundsLeft) {
        int[] move = new int[2];
        this.endTime = System.currentTimeMillis() + 5000; // 5 seconds from now
        int[] selection = minimax(board, roundsLeft, roundsLeft, Integer.MAX_VALUE, Integer.MIN_VALUE, true);
        move[0] = selection[1];
        move[1] = selection[2];
        return move;
    }

    // Minimax evaluation based on the number of O and X in position
    public int minimaxEvaluation(Button[][] board) {
        int evaluation = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j].getText().equals("O")) {
                    evaluation++;
                }
                if (board[i][j].getText().equals("X")) {
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
            finalBoard[x][y].setText("O");
            if (x > 0 && finalBoard[x - 1][y].getText().equals("X")) {
                finalBoard[x - 1][y].setText("O");
            }
            if (x < 7 && finalBoard[x + 1][y].getText().equals("X")) {
                finalBoard[x + 1][y].setText("O");
            }
            if (y > 0 && finalBoard[x][y - 1].getText().equals("X")) {
                finalBoard[x][y - 1].setText("O");
            }
            if (y < 7 && finalBoard[x][y + 1].getText().equals("X")) {
                finalBoard[x][y + 1].setText("O");
            }
            // Player's turn
        } else {
            finalBoard[x][y].setText("X");
            if (x > 0 && finalBoard[x - 1][y].getText().equals("O")) {
                finalBoard[x - 1][y].setText("X");
            }
            if (x < 7 && finalBoard[x + 1][y].getText().equals("O")) {
                finalBoard[x + 1][y].setText("X");
            }
            if (y > 0 && finalBoard[x][y - 1].getText().equals("O")) {
                finalBoard[x][y - 1].setText("X");
            }
            if (y < 7 && finalBoard[x][y + 1].getText().equals("O")) {
                finalBoard[x][y + 1].setText("X");
            }
        }
        return finalBoard;
    }

    // Minimax algorithm
    public int[] minimax(Button[][] board, int depth, int roundsLeft, int alpha, int beta, boolean bot) {
        // basis
        if (System.currentTimeMillis() >= this.endTime) {
            System.out.println("Time's up!");
        }
        if (depth == 0 || roundsLeft == 0 || System.currentTimeMillis() >= endTime) {
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
                        int[] selection = minimax(newBoard, depth - 1, roundsLeft - 1, alpha, beta, false);
                        int eval = selection[0];

                        if (eval > maxEval) {
                            maxEval = eval;
                            bestX = i;
                            bestY = j;
                        }

                        // Pruning, ignore if beta is smaller than alpha
                        alpha = Math.max(alpha, eval);
                        if (beta <= alpha) {
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
                        int[] selection = minimax(newBoard, depth - 1, roundsLeft, alpha, beta, true);
                        int eval = selection[0];

                        if (eval < minEval) {
                            minEval = eval;
                            bestX = i;
                            bestY = j;
                        }

                        // Pruning, ignore if beta is smaller than alpha
                        beta = Math.min(beta, eval);
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
            }

            return new int[] { minEval, bestX, bestY };
        }
    }
}
