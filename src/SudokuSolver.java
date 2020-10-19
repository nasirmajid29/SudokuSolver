public class SudokuSolver {

  public static void main(String[] args) {

    Sudoku sudoku = new Sudoku();
    int[][] board = sudoku.initialise();

    sudoku.print(board);

  }


}
