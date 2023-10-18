import javafx.scene.control.Button;

public class HCSidewaysBot extends Bot {

    public HCSidewaysBot(String symbol) {
        this.symbol = symbol;
        this.enemySymbol = symbol.equals("X") ? "O" : "X";
    }

    public int[] move(Button[][] board, int roundsLeft) {
        int[] selection = new int[2];
        int eval = Integer.MIN_VALUE;
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
                    if (res < eval) {
                        return selection;
                    }
                    eval = res;
                    selection[0] = i;
                    selection[1] = j;
                }
            }
        }
        return selection;
    }
}
