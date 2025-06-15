import java.util.Scanner;

public class TicTacToe {
    private static final char EMPTY = ' ';
    private static final int SIZE = 3;
    private static char[][] board = new char[SIZE][SIZE];
    private static final char HUMAN = 'X';
    private static final char AI = 'O';
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initBoard();
        printBoard();
        while (true) {
            if (currentPlayer() == HUMAN) {
                playerMove();
            } else {
                System.out.println("AI is making a move...");
                int[] bestMove = findBestMove();
                board[bestMove[0]][bestMove[1]] = AI;
            }
            printBoard();
            if (checkWin(currentPlayer() == HUMAN ? AI : HUMAN)) {
                System.out.println("Player " + (currentPlayer() == HUMAN ? AI : HUMAN) + " wins!");
                break;
            } else if (checkDraw()) {
                System.out.println("It's a draw!");
                break;
            }
        }
    }

    private static void initBoard() {
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                board[i][j] = EMPTY;
    }

    private static void printBoard() {
        System.out.println("\n  0 1 2");
        for (int i = 0; i < SIZE; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(board[i][j]);
                if (j < SIZE - 1) System.out.print("|");
            }
            System.out.println();
            if (i < SIZE - 1) System.out.println("  -----");
        }
    }

    private static void playerMove() {
        int row, col;
        while (true) {
            System.out.print("Your move (row and column): ");
            try {
                row = scanner.nextInt();
                col = scanner.nextInt();
                if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
                    System.out.println("Out of bounds. Try again.");
                } else if (board[row][col] != EMPTY) {
                    System.out.println("Cell already taken. Try again.");
                } else {
                    board[row][col] = HUMAN;
                    break;
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Enter numbers.");
                scanner.nextLine();
            }
        }
    }

    private static boolean checkWin(char player) {
        for (int i = 0; i < SIZE; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) return true;
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) return true;
        }
        return (board[0][0] == player && board[1][1] == player && board[2][2] == player)
            || (board[0][2] == player && board[1][1] == player && board[2][0] == player);
    }

    private static boolean checkDraw() {
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                if (board[i][j] == EMPTY)
                    return false;
        return true;
    }

    private static char currentPlayer() {
        int count = 0;
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                if (board[i][j] != EMPTY)
                    count++;
        return count % 2 == 0 ? HUMAN : AI;
    }

    private static int[] findBestMove() {
        int bestVal = Integer.MIN_VALUE;
        int[] bestMove = new int[] { -1, -1 };

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == EMPTY) {
                    board[i][j] = AI;
                    int moveVal = minimax(0, false);
                    board[i][j] = EMPTY;
                    if (moveVal > bestVal) {
                        bestMove[0] = i;
                        bestMove[1] = j;
                        bestVal = moveVal;
                    }
                }
            }
        }
        return bestMove;
    }

    private static int minimax(int depth, boolean isMax) {
        if (checkWin(AI)) return 10 - depth;
        if (checkWin(HUMAN)) return depth - 10;
        if (checkDraw()) return 0;

        int best = isMax ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == EMPTY) {
                    board[i][j] = isMax ? AI : HUMAN;
                    int value = minimax(depth + 1, !isMax);
                    board[i][j] = EMPTY;
                    best = isMax ? Math.max(best, value) : Math.min(best, value);
                }
            }
        }
        return best;
    }
}
