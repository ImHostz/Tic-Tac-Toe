import java.util.Scanner;

public class TicTacToe {
    private static final char EMPTY = ' ';
    private static final int SIZE = 3;
    private static char[][] board = new char[SIZE][SIZE];
    private static char currentPlayer = 'X';
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initBoard();
        while (true) {
            printBoard();
            playerMove();
            if (checkWin()) {
                printBoard();
                System.out.println("Player " + currentPlayer + " wins!");
                break;
            } else if (checkDraw()) {
                printBoard();
                System.out.println("It's a draw!");
                break;
            }
            switchPlayer();
        }
    }

    private static void initBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = EMPTY;
            }
        }
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
            System.out.print("Player " + currentPlayer + ", enter row and column (e.g. 1 2): ");
            try {
                row = scanner.nextInt();
                col = scanner.nextInt();
                if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
                    System.out.println("Out of bounds. Try again.");
                } else if (board[row][col] != EMPTY) {
                    System.out.println("Cell already taken. Try again.");
                } else {
                    board[row][col] = currentPlayer;
                    break;
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Enter numbers.");
                scanner.nextLine();
            }
        }
    }

    private static boolean checkWin() {
        for (int i = 0; i < SIZE; i++) {
            if (board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) return true;
            if (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer) return true;
        }
        return (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer)
            || (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer);
    }

    private static boolean checkDraw() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == EMPTY) return false;
            }
        }
        return true;
    }

    private static void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }
}
