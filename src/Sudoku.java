public class Sudoku {

  private static final int SIZE = 9;
  private static final int SQRT = (int) Math.sqrt(SIZE);
  private int board[][] = new int[SIZE][SIZE];


  int K; // SIZEo. Of missing digits

  // Constructor
  Sudoku() {
    for (int i = 0; i < SIZE; i++) {
      for (int j = 0; j < SIZE; j++) {
        board[i][j] = 0;
      }
    }
//    // Compute square root of SIZE
//    Double SQRTd = Math.sqrt(SIZE);
//    SQRT = SQRTd.intValue();
//
//    board = new int[SIZE][SIZE];
  }
  // generate a completely solved sudoku board


//  public void initialise() {
//
//    // set all values of puzzle to 0
//    // 0 denotes an empty square
//    for (int i = 0; i < SIZE; i++) {
//      for (int j = 0; j < SIZE; j++) {
//        board[i][j] = 0;
//      }
//    }
//  }

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
    // Fill the diagonal of SQRT x SQRT boardrices
    fillBoxDiagonal();

    // Fill remaining blocks
    fillRemainingBox(0, SQRT);

    // Remove Randomly K digits to make game
    removeKDigits();
  }

  // Fill the diagonals numbers of a box
  void fillBoxDiagonal() {

    for (int i = 0; i < SIZE; i = i + SQRT){
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

  // Fill a 3 x 3 boardrix.
  void fillBox(int row, int col) {
    int num;
    for (int i = 0; i < SQRT; i++) {
      for (int j = 0; j < SQRT; j++) {
        do {
          num = randomGenerator(SIZE);
        }while (!unusedInBox(row, col, num));

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

  // A recursive function to fill remaining
  // boardrix
  boolean fillRemainingBox(int i, int j) {
    //  System.out.println(i+" "+j);
    if (j >= SIZE && i < SIZE - 1) {
      i = i + 1;
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
      if (j == (int) (i / SQRT) * SQRT) {
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
  public void removeKDigits() {
    int count = K;
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
    int SIZE = 9, K = 20;
    Sudoku sudoku = new Sudoku();
    sudoku.generate();
    sudoku.print();

  }

}
