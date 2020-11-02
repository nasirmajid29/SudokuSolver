import java.util.Scanner;
import java.util.SimpleTimeZone;

public class Sudoku {

  public enum Difficulty {
    EASY(20),
    MEDIUM(30),
    HARD(40),
    EXPERT(50),
    IMPOSSIBLE(60);

    private final int numberToRemove;
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
        if(board[i][j] != 0) {
          System.out.print(" " + board[i][j]);
        }else{
          System.out.print("  ");
        }
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
    return (unusedInRow(i, num) &&
        unusedInCol(j, num) &&
        unusedInBox(i - i % SQRT, j - j % SQRT, num));
  }

  // check in the row for existence
  boolean unusedInRow(int i, int num) {
    for (int j = 0; j < SIZE; j++) {
      if (board[i][j] == num) {
        return false;
      }
    }
    return true;
  }

  // check in the row for existence
  boolean unusedInCol(int j, int num) {
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
      if(i == SIZE){
        i--;
      }
      int j = cellId % 9;


      // System.out.println(i+" "+j);
      if (board[i][j] != 0) {
        count--;
        board[i][j] = 0;
      }
    }
  }

  public boolean solve() {
    for (int row = 0; row < SIZE; row++) {
      for (int col = 0; col < SIZE; col++) {
        // we search an empty cell
        if (board[row][col] == 0) {
          // we try possible numbers
          for (int number = 1; number <= SIZE; number++) {
            if (CheckIfSafe(row, col, number)) {
              // number ok. it respects sudoku constraints
              board[row][col] = number;
                if (solve()) { // we start backtracking recursively
                  return true;
                } else { // if not a solution, we empty the cell and we continue
                  board[row][col] = 0;
                }
              }
            }

            return false; // we return false
          }
        }
      }

      return true; // sudoku solved
    }





  // Driver code
  public static void main(String[] args) {
    Sudoku sudoku = new Sudoku();
    sudoku.generate();
    sudoku.print();

    System.out.println("Going to solve");
    long startTime = System.nanoTime();
    sudoku.solve();
    long endTime = System.nanoTime();

    long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
    sudoku.print();
    System.out.println("That took " + duration);
  }

}

