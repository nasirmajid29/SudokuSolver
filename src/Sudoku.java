public class Sudoku {

  private static final int SIZE = 9;
  private int sudoku[][] = new int[SIZE][SIZE];

  // generate a completely solved sudoku board

  public int[][] initialise() {

    // set all values of puzzle to 0
    // 0 denotes an empty square
    for (int i = 0; i < SIZE; i++) {
      for (int j = 0; j < SIZE; j++) {
        sudoku[i][j] = 0;
      }
    }
    return sudoku;
  }

  public void print(int [][] board){

    for (int i = 0; i < SIZE; i++) {
      System.out.println();
      if(i % Math.sqrt(SIZE) == 0) {
        System.out.println(" - - - - - - - - - - - - -");
      }

      for (int j = 0; j < SIZE; j++) {
        if(j % Math.sqrt(SIZE) == 0){
          System.out.print(" |");
        }
        System.out.print(" " + board[i][j]);
        if(j == SIZE - 1){
          System.out.print(" |");
        }

      }
      if(i == SIZE - 1){
        System.out.println();
        System.out.println(" - - - - - - - - - - - - -");
      }
    }
  }


}
