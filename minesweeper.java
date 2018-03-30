import java.util.Scanner;

class Minesweeper {
  private int[][] map;
  private int[][] progressMap;
  public int rows;
  public int cols;
  
  public void Minesweeper() {
    this.map = map;
    this.progressMap = progressMap;
    this.rows = rows;
    this.cols = cols;
  }
  
  public void generateMap(int m, int n) {
    map = new int[m][n];
    progressMap = new int[m][n];
    rows = map.length;
    cols = map[0].length;
  }
  
  public void printEmptyMap() {
    for(int i = 0; i < map.length; i++) {
      System.out.print("| ");
      for (int j = 0; j < map[i].length; j++) {
        System.out.print("  | ");
        progressMap[i][j] = -1; // prepopulate progress map
      }
      System.out.println();
    }
  }

  public void revealMap(int m, int n, boolean isgameOver) {
    
    clearArea(m, n);
    
    for(int i = 0; i < map.length; i++) {
      System.out.print("| ");
      for (int j = 0; j < map[i].length; j++) {
        if (isgameOver) { // if game over, reaveal full map
          if (map[i][j] == -1) System.out.print("* | ");
          else System.out.print(map[i][j] + " | ");
        } else { // otherwise print progress map
          if (progressMap[i][j] != -1) System.out.print(progressMap[i][j] + " | ");
          else System.out.print("  | ");
        }
      }
      System.out.println();
    }
  }
  
  public void clearArea(int m, int n) {
    if (m < 0 || n < 0 || m > map.length - 1 || n > map[m].length - 1 || map[m][n] == -1 || progressMap[m][n] > -1) return;
    
    progressMap[m][n] = map[m][n];
    
    if (map[m][n] > 0) return; 
    
    /* Left & Right */
    clearArea(m, n - 1);
    clearArea(m, n + 1);
            
    /* Up & Down */
    clearArea(m + 1, n);
    clearArea(m - 1, n);
            
    /* Diagonal */
    clearArea(m - 1, n + 1); 
    clearArea(m - 1, n - 1);
    clearArea(m + 1, n + 1);
    clearArea(m + 1, n - 1);
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
          
          /* Left & Right */
          if (j < map[i].length - 1 && map[i][j + 1] == -1) numberOfBombs++;
          if (j > 0 && map[i][j - 1] == -1) numberOfBombs++;
          
          /* Up & Diagonal Right and Left */
          if (i > 0 && map[i - 1][j] == -1) numberOfBombs++;
          if (i > 0 && j < map[i].length - 1 && map[i - 1][j + 1] == -1) numberOfBombs++; 
          if (i > 0 && j > 0 && map[i - 1][j - 1] == -1) numberOfBombs++; 
          
          /* Down & Diagonal Right and Left */
          if (i < map.length - 1 && map[i + 1][j] == -1) numberOfBombs++;
          if (i < map.length - 1 && j < map[i].length - 1 && map[i + 1][j + 1] == -1) numberOfBombs++;
          if (i < map.length - 1 && j > 0 && map[i + 1][j - 1] == -1) numberOfBombs++; 
          
          map[i][j] = numberOfBombs;
          numberOfBombs = 0;
        }
      }
    }
  }
  
  public boolean gameOver(int m, int n, int numBombs) {
    if (map[m][n] == -1) {
      System.out.println("GAME OVER!");
      return true;
    } 
    
    for (int i = 0; i < progressMap.length; i++) {
      for (int j = 0; j < progressMap[i].length; j++) {
        if (progressMap[i][j] == -1) {
          numBombs--;
        }
      }
    }
    
    if (numBombs == 0) {
      System.out.println("YOU WIN!!");
      return true;
    } 
    
    return false;
  }
  
}


class Main {
  

  public static int startGame(Minesweeper game) {
    System.out.println("Welcome to Minesweeper!");
    System.out.println();
    Scanner level = new Scanner(System.in);
    
    System.out.println("Select difficulty:");
    System.out.println("1. Beginner (8x8 with 10 mines)");
    System.out.println("2. Intermediate (16x16 with 40 mines)");
    System.out.println("3. Expert (16x30 with 99 mines)");
    System.out.println();
    
    int difficulty = level.nextInt();
    
    while (difficulty < 1 || difficulty > 3) {
      System.out.println("Invalid option. Please type 1, 2, or 3.");
      difficulty = level.nextInt();
    }
    
    int bombs = 0;
    
    switch (difficulty) {
      case 1: 
        game.generateMap(8, 8);
        bombs = 10;
        break;
              
      case 2: 
        game.generateMap(16, 16);
        bombs = 40;
        break;
              
      case 3: 
        game.generateMap(16, 30);
        bombs = 99;
        break;
        
      default: break;
    }
    
    game.placeBombs(bombs);
    game.numberOfAdjacentBombs();
    game.printEmptyMap();
    return bombs;
  }
  
  public static boolean playGame(Minesweeper game, int bombs) {
    Scanner selection = new Scanner(System.in);
    System.out.println("Enter row:");
    int m = selection.nextInt();
    
    while (m < 0 || m > game.rows - 1) {
      System.out.println("Invalid row number. Try again.");
      m = selection.nextInt();
    }
    
    System.out.println("Enter column:");
    int n = selection.nextInt();
    
    while (n < 0 || n > game.cols - 1) {
      System.out.println("Invalid column number. Try again.");
      n = selection.nextInt();
    }

    if (!game.gameOver(m, n, bombs)) {
      game.revealMap(m, n, false);
      return true;
    }
    
    game.revealMap(m, n, true);
    return false;
  }
  
  public static void main(String[] args) {
    Minesweeper newGame = new Minesweeper();
    int bombs = startGame(newGame);
    
    while (playGame(newGame, bombs)) {
      // continue gameplay until bomb is selected or player wins
    }
  }
}
