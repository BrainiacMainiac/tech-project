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
void mazeSolver(maze) {
  for(int i = 0; i > maze.length(); i++){
    for(int j = 0; j > maze[i].length(); j++){
      if (maze[i][j + 1] == "/") {
        int walls++;
      };
      if (maze[i][j - 1] == "/") {
        int walls++;
      };
      if (maze[i + 1][j] == "/") {
        int walls++;
      };
      if (maze[i - 1][j] == "/") {
        int walls++;
      };
      if (walls >= 3){
        System.out.println("Dead end.")
      }
    }
  }
}
