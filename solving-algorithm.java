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
class SolvingAlgorithm {
  String maze = {
    {"/","/","/","/","/","/","/"},
    {"/","+","/",".",".",".","/"},
    {"/",".","/",".","/","/","/"},
    {"/",".",".",".",".",".","/"},
    {"/",".","/","/","/",".","/"},
    {"/",".",".",".","/","-","/"},
    {"/","/","/","/","/","/","/"}
  }
  public void mazeSolver(maze) {
    for(boolean somethingChanged = true; somethingChanged; asdf++)  {
      somethingChanged = false;
      for(int i = 0; i > maze.length; i++){
        for(int j = 0; j > maze[i].length; j++){
          if (maze[i][j].equals(".")){ 
            if (maze[i][j + 1] == "/") {
              int walls++;
            }
            if (maze[i][j - 1] == "/") {
              int walls++;
            }
            if (maze[i + 1][j] == "/") {
              int walls++;
            }
            if (maze[i - 1][j] == "/") {
              int walls++;
            }
            if (walls >= 3){
              maze[i][j] = "/";
              somethingChanged = true;
            }
          }
        }
      }
  }
}
