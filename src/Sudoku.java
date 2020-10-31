import java.util.Scanner;

public class Sudoku {

  public enum Difficulty {
    EASY(20),
    MEDIUM(30),
    HARD(40),
    EXPERT(50),
    IMPOSSIBLE(60);

    private int numberToRemove;
    Difficulty(int numberToRemove) {
      this.numberToRemove = numberToRemove;
    }

    private int getNumberToRemove(){
      return numberToRemove;
    }
  }

  private static final int SIZE = 9;
  private static final int SQRT = (int) Math.sqrt(SIZE);
  private final int[][] board = new int[SIZE][SIZE];
  private final Difficulty difficulty;

  // Constructor
  public Sudoku() {
    for (int i = 0; i < SIZE; i++) {
      for (int j = 0; j < SIZE; j++) {
        board[i][j] = 0;
      }
    }
    Scanner scanner = new Scanner(System.in);

    System.out.println("Pick a difficulty");
    String input = scanner.next().toUpperCase();
    while (!isValidDifficulty(input)) {
      System.out.println("That isn't a difficulty, try again");
      input = scanner.next().toUpperCase();
    }
    difficulty = Difficulty.valueOf(input);

  }

  private boolean isValidDifficulty(String input) {
    return (input.equals("EASY") || input.equals("MEDIUM") ||
        input.equals("HARD") || input.equals("EXPERT") ||
        input.equals("IMPOSSIBLE"));
  }

  public void print() {

    for (int i = 0; i < SIZE; i++) {
      System.out.println();
      if (i % Math.sqrt(SIZE) == 0) {
        System.out.println(" - - - - - - - - - - - - -");
      }

      for (int j = 0; j < SIZE; j++) {
        if (j % Math.sqrt(SIZE) == 0) {
          System.out.print(" |");
        }
        System.out.print(" " + board[i][j]);
        if (j == SIZE - 1) {
          System.out.print(" |");
        }

      }
      if (i == SIZE - 1) {
        System.out.println();
        System.out.println(" - - - - - - - - - - - - -");
      }
    }
  }

  // Sudoku Generator
  public void generate() {
    // Fill the boxes on the diagonal
    fillBoxDiagonal();

    // Fill remaining blocks
    fillRemainingBox(0, SQRT);

    // Randomly remove digits to make game
    removeDigits();
  }

  // Fill the diagonals numbers of a box
  void fillBoxDiagonal() {

    for (int i = 0; i < SIZE; i = i + SQRT) {
      fillBox(i, i);
    }
  }

  // Returns false if given 3 x 3 block contains num.
  boolean unusedInBox(int rowStart, int colStart, int num) {
    for (int i = 0; i < SQRT; i++) {
      for (int j = 0; j < SQRT; j++) {
        if (board[rowStart + i][colStart + j] == num) {
          return false;
        }
      }
    }

    return true;
  }

  // Fill a 3 x 3 box.
  void fillBox(int row, int col) {
    int num;
    for (int i = 0; i < SQRT; i++) {
      for (int j = 0; j < SQRT; j++) {
        do {
          num = randomGenerator(SIZE);
        } while (!unusedInBox(row, col, num));

        board[row + i][col + j] = num;
      }
    }
  }

  // Random generator
  int randomGenerator(int num) {
    return (int) Math.floor((Math.random() * num + 1));
  }

  // Check if safe to put in cell
  boolean CheckIfSafe(int i, int j, int num) {
    return (unUsedInRow(i, num) &&
        unUsedInCol(j, num) &&
        unusedInBox(i - i % SQRT, j - j % SQRT, num));
  }

  // check in the row for existence
  boolean unUsedInRow(int i, int num) {
    for (int j = 0; j < SIZE; j++) {
      if (board[i][j] == num) {
        return false;
      }
    }
    return true;
  }

  // check in the row for existence
  boolean unUsedInCol(int j, int num) {
    for (int i = 0; i < SIZE; i++) {
      if (board[i][j] == num) {
        return false;
      }
    }
    return true;
  }

  // A recursive function to fill remaining box
  boolean fillRemainingBox(int i, int j) {
    if (j >= SIZE && i < SIZE - 1) {
      i++;
      j = 0;
    }
    if (i >= SIZE && j >= SIZE) {
      return true;
    }

    if (i < SQRT) {
      if (j < SQRT) {
        j = SQRT;
      }
    } else if (i < SIZE - SQRT) {
      if (j == (i / SQRT) * SQRT) {
        j = j + SQRT;
      }
    } else {
      if (j == SIZE - SQRT) {
        i = i + 1;
        j = 0;
        if (i >= SIZE) {
          return true;
        }
      }
    }
    for (int num = 1; num <= SIZE; num++) {
      if (CheckIfSafe(i, j, num)) {
        board[i][j] = num;
        if (fillRemainingBox(i, j + 1)) {
          return true;
        }

        board[i][j] = 0;
      }
    }
    return false;
  }

  // Remove the K no. of digits to
  // complete game
  public void removeDigits() {
    int count = difficulty.getNumberToRemove();
    while (count != 0) {
      int cellId = randomGenerator(SIZE * SIZE);

      // System.out.println(cellId);
      // extract coordinates i  and j
      int i = (cellId / SIZE);
      int j = cellId % 9;
      if (j != 0) {
        j = j - 1;
      }

      // System.out.println(i+" "+j);
      if (board[i][j] != 0) {
        count--;
        board[i][j] = 0;
      }
    }
  }


  // Driver code
  public static void main(String[] args) {
    Sudoku sudoku = new Sudoku();
    sudoku.generate();
    sudoku.print();

  }

}

