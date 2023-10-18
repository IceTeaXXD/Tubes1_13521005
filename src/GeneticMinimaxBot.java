import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javafx.scene.control.Button;

public class GeneticMinimaxBot extends Bot {
    private String symbol;
    private String enemySymbol;
    private static final int POPULATION_SIZE = 10;
    private static final double MUTATION_RATE = 0.1;
    private static final int MAX_GENERATIONS = 100;
    private static final int MAX_DEPTH = 3;
    private Random random = new Random();
    private boolean isFallback = false;
    private long endTime;

    public GeneticMinimaxBot(String symbol) {
        this.symbol = symbol;
        this.enemySymbol = symbol.equals("X") ? "O" : "X";
    }

    public int[] move(Button[][] board, int roundsLeft) {
        // Create a copy of the board
        Button[][] copyBoard = new Button[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                copyBoard[i][j] = new Button(board[i][j].getText());
            }
        }

        // Create a population of random moves
        ArrayList<int[]> population = new ArrayList<>();
        for (int i = 0; i < POPULATION_SIZE; i++) {
            population.add(getRandomMove(copyBoard));
        }

        // Evolve the population using Genetic Algorithm
        for (int generation = 0; generation < MAX_GENERATIONS; generation++) {
            // Evaluate the fitness function
            ArrayList<Integer> fitness = new ArrayList<>();
            for (int[] move : population) {
                Button[][] newBoard = updateVirtualBoard(move[0], move[1], copyBoard, true);
                fitness.add(evaluate(newBoard));
            }

            // Select the best solutions to be parents for the next generation
            ArrayList<int[]> parents = new ArrayList<>();
            for (int i = 0; i < POPULATION_SIZE / 2; i++) {
                int index1 = rouletteWheelSelection(fitness);
                int index2 = rouletteWheelSelection(fitness);
                parents.add(population.get(index1));
                parents.add(population.get(index2));
            }

            // Generate new solutions by combining the genes of the parents (crossover)
            ArrayList<int[]> children = new ArrayList<>();
            for (int i = 0; i < POPULATION_SIZE; i++) {
                int[] parent1 = parents.get(random.nextInt(parents.size()));
                int[] parent2 = parents.get(random.nextInt(parents.size()));
                int[] child = new int[2];
                child[0] = random.nextBoolean() ? parent1[0] : parent2[0];
                child[1] = random.nextBoolean() ? parent1[1] : parent2[1];
                children.add(child);
            }

            // Mutate the children
            for (int[] move : children) {
                if (random.nextDouble() < MUTATION_RATE) {
                    move[0] = random.nextInt(8);
                    move[1] = random.nextInt(8);
                }
            }

            // Evaluate the fitness function using Minimax
            ArrayList<Integer> minimaxFitness = new ArrayList<>();
            for (int[] move : children) {
                this.endTime = System.currentTimeMillis() + 5000; // 5 seconds from now
                minimaxFitness.add(minimax(board, MAX_DEPTH, roundsLeft, Integer.MIN_VALUE, Integer.MAX_VALUE, true,
                        1)[0]);
            }

            // Select the best solution as the move to make
            int bestIndex = minimaxFitness.indexOf(Collections.max(minimaxFitness));
            int[] bestMove = children.get(bestIndex);
            if (copyBoard[bestMove[0]][bestMove[1]].getText().equals("")) {
                return bestMove;
            }

            // Repeat the process with the new population
            population = children;
        }

        // If no move is found, return a random move
        int [] move = getRandomMove(copyBoard);
        return move;
    }

    private int[] getRandomMove(Button[][] board) {
        int[] move = new int[2];
        do {
            move[0] = random.nextInt(8);
            move[1] = random.nextInt(8);
        } while (!board[move[0]][move[1]].getText().equals(""));
        return move;
    }

    private int rouletteWheelSelection(ArrayList<Integer> fitness) {
        int totalFitness = 0;
        for (int f : fitness) {
            totalFitness += f;
        }
        double randomFitness = random.nextDouble() * totalFitness;
        int index = 0;
        while (randomFitness > 0) {
            randomFitness -= fitness.get(index);
            index++;
        }
        return index == 0 ? 0 : index - 1; // index - 1 because index is incremented one more time after the last
                                           // iteration
    }

    // Minimax evaluation based on the number of O and X in position
    public int evaluate(Button[][] board) {
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
            int evaluation = evaluate(board);
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
                        if ((beta < Integer.MAX_VALUE || alpha > Integer.MIN_VALUE) && beta <= alpha) {
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
