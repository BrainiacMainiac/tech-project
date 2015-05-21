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
  static String[][] maze1 = {
    {"/","/","/","/","/","/","/"},
    {"/","+","/",".",".",".","/"},
    {"/",".","/",".","/","/","/"},
    {"/",".",".",".",".",".","/"},
    {"/",".","/","/","/",".","/"},
    {"/",".",".",".","/","-","/"},
    {"/","/","/","/","/","/","/"}
  };
  static void deadEndSolve() {
    for(boolean somethingChanged = true; somethingChanged; somethingChanged = false){
      for(int i = 0; i > mazeArray.length; i++){
        for(int j = 0; j > mazeArray[i].length; j++){
          if (mazeArray[i][j].equals(".")){ 
            int walls = 0;
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
            if (walls >= 3){
              mazeArray[i][j] = "x";
              somethingChanged = true;
              repaint();
              try {
                Thread.sleep(100);
              } catch Exception e {
              }
              }
            }
          }
        }
      }
    }
  }
  public static void main(String[] argt){
    mazeSolver(maze1);
  }
}
