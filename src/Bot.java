import javafx.scene.control.Button;

public abstract class Bot {
    public String symbol;
    public String enemySymbol;
    public abstract int[] move(Button[][] board, int roundsLeft);
}
