public class Sudoku {

  private int sudoku[][] = new int[9][9];

  // generate a completely solved sudoku board

  public int[][] generate() {

    // set all values of puzzle to 0
    // 0 denotes an empty square
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        sudoku[i][j] = 0;
      }
    }
    return  sudoku;
  }
  

}
