import java.util.Scanner;

class Minesweeper {
  private int[][] map;
  
  public void Minesweeper() {
    this.map = map;
  }
  
  public void generateMap(int m, int n) {
    map = new int[m][n];
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
  
  public boolean gameOver(int m, int n) {
    if (map[m][n] == -1) return true;
    return false;
  }
  
}


class Main {
  

  public static void startGame(Minesweeper game) {
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
    
    switch (difficulty) {
      case 1: 
        game.generateMap(8, 8);
        game.placeBombs(10);
        break;
              
      case 2: 
        game.generateMap(16, 16);
        game.placeBombs(40);
        break;
              
      case 3: 
        game.generateMap(16, 30);
        game.placeBombs(99);
        break;
        
      default: break;
    }
    
    game.numberOfAdjacentBombs();
    game.printMap();
  }
  
  public static boolean gamePlay(Minesweeper game) {
    Scanner reader = new Scanner(System.in);
    System.out.println("Enter row:");
    int m = reader.nextInt();
    System.out.println("Enter column:");
    int n = reader.nextInt();

    if (game.gameOver(m, n)) {
      return false;
    }
    
    return true;
  }
  
  public static void main(String[] args) {
    Minesweeper newGame = new Minesweeper();
    startGame(newGame);
    
    while (gamePlay(newGame)) {
      newGame.printMap();
    }
    
    System.out.println("GAME OVER!");
  }
}

