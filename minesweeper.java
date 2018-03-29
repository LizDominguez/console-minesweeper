class Minesweeper {
  private int[][] map;
  
  public void Minesweeper() {
    this.map = map;
  }
  
  public void generateMap(int size) {
    map = new int[size][size];
  }

  public void printMap() {
    for(int i = 0; i < map.length; i++) {
      System.out.print("| ");
      for (int j = 0; j < map[i].length; j++) {
        if (map[i][j] == -1) System.out.print("* | ");
        else System.out.print(map[i][j] + " | ");
      }
      System.out.println();
    }
  }
  
  public void placeBombs(int number) {
    
    int max = map.length;
		int min = 0;
		int range = max - min + 1;		
		
    while (number > 0) {
      int i = (int)(Math.random() * range + min);
      int j = (int)(Math.random() * range + min);
      
      if (i < max && j < max && map[i][j] == 0) {
        map[i][j] = -1; // Bombs are represented by -1
        number--;
      }
    }
  }
  
  public void numberOfAdjacentBombs() {
    int numberOfBombs = 0;
    for (int i = 0; i < map.length; i++) {
      for (int j = 0; j < map[i].length; j++) {
        if (map[i][j] != -1) {
          if (i < map.length - 1 && map[i + 1][j] == -1) numberOfBombs++; // down
          if (i > 0 && map[i - 1][j] == 8) numberOfBombs++; // up
          if (j < map[i].length - 1 && map[i][j + 1] == -1) numberOfBombs++; // right
          if (j > 0 && map[i][j - 1] == -1) numberOfBombs++; // left
          map[i][j] = numberOfBombs;
          numberOfBombs = 0;
        }
      }
    }
  }
  
}


class Main {
  
  public static void main(String[] args) {
    Minesweeper board = new Minesweeper();
    board.generateMap(5);
    System.out.println("With Bombs:");
    board.placeBombs(5);
    board.printMap();
    System.out.println("Clues:");
    board.numberOfAdjacentBombs();
    board.printMap();
  }
}




