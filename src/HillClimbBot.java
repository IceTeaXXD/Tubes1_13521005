import javafx.scene.control.Button;

public class HillClimbBot extends Bot {

    public int[] move(Button[][] board, int roundsLeft) {
        int[] selection = new int[2];
        int eval = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j].getText().equals("")) {
                    int res = 0;
                    if (i != 0 && board[i - 1][j].getText().equals("X")) {
                        res++;
                    }
                    if (j != 0 && board[i][j - 1].getText().equals("X")) {
                        res++;
                    }
                    if (i != 7 && board[i + 1][j].getText().equals("X")) {
                        res++;
                    }
                    if (j != 7 && board[i][j + 1].getText().equals("X")) {
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