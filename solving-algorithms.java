// Reference to maze:
/* 
  .=open
  /=wall
  +=start
  -=end
  *=rigth path
  :=wrong path
  x=dead end filled
  f[int]=wrong A#
  t[int]=right/possible A#
  */
class DeadEndBlockerAlgorithm {
  // Test maze please delete
  static String[][] maze1 = {
    {"/","/","/","/","/","/","/"},
    {"/","+","/",".",".",".","/"},
    {"/",".","/",".","/","/","/"},
    {"/",".",".",".",".",".","/"},
    {"/",".","/","/","/",".","/"},
    {"/",".",".",".","/","-","/"},
    {"/","/","/","/","/","/","/"}
  };
  static void deadEndSolve(String mazeArray[][]) {
    // Make sure something changes every time
    for(boolean somethingChanged = true; somethingChanged; somethingChanged = false){
      // Outer loop for x
      for(int i = 0; i > mazeArray.length; i++){
        // Inner loop for y
        for(int j = 0; j > mazeArray[i].length; j++){
          // Make sure you're marking an open spot
          if (mazeArray[i][j].equals(".")){ 
            // Surrounding walls check
            int walls = 0;
            // Note: / = wall, x = filled dead end
            if (mazeArray[i][j + 1] == "/" || mazeArray[i - 1][j] == "x") {
              walls++;
            }
            if (mazeArray[i][j - 1] == "/" || mazeArray[i - 1][j] == "x") {
              walls++;
            }
            if (mazeArray[i + 1][j] == "/" || mazeArray[i - 1][j] == "x") {
              walls++;
            }
            if (mazeArray[i - 1][j] == "/" || mazeArray[i - 1][j] == "x") {
              walls++;
            }
            // If surrounded by 3 or more filled dead ends or walls
            if (walls >= 3){
              // Fill in space
              mazeArray[i][j] = "x";
              // Mention something was changed
              somethingChanged = true;
              // Update the window
              repaint();
              }
            }
          }
        }
      }
    }
  }
  public static void main(String[] argt){
    // Run a test
    mazeSolver(maze1);
      }
    }
  }
}
