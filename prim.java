public void genPrim(int rows, int cols) {
  mazeArray=new String[rows][cols];
  for (int i=0; i<rows; i++) {
    for (int j=0; j<cols; j++) {
      mazeArray[i][j]="/";
    }
  }
  ArrayList walls=new ArrayList(60);
  while (walls.size()>0) {
    int n=(int)Math.floor(Math.random()*walls.size());
    PrimWall w=(PrimWall) walls.get(n);
    int row=w.row+w.rowDif;
    int col=w.col+w.colDif;
    if (mazeArray[row][col].equals("/") && mazeArray[w.row][w.col].equals("/")) {
      mazeArray[row][col]=".";
      mazeArray[w.row][w.col]=".";
      if (mazeArray[row+1][col].equals("/") && row!=rows-2) walls.add(new PrimWall(row+1,col,PrimWall.DOWN));
      if (mazeArray[row][col+1].equals("/") && col!=cols-2) walls.add(new PrimWall(row,col+1,PrimWall.RIGHT));
      if (mazeArray[row-1][col].equals("/") && row!=1) walls.add(new PrimWall(row-1,col,PrimWall.UP));
      if (mazeArray[row][col-1].equals("/") && col!=1) walls.add(new PrimWall(row,col-1,PrimWall.LEFT));
      try {
    Thread.sleep(slowmo ? 5000/(mazeArray.length+mazeArray[0].length):0);
    } catch (Exception e) {
    }
    }
    walls.remove(w);
  }
}


class PrimWall {
  int row=0;
  int col=0;
  public static final int UP=0;
  public static final int DOWN=1;
  public static final int LEFT=2;
  public static final int RIGHT=3;
  int direction=0;
  byte rowDif=0;
  byte colDif=0;
  public PrimWall(int x, int y, int direc) {
    row=x;
    col=y;
    direction=direc;
    if (direction=UP) rowDif=-1;
    if (direction=DOWN) rowDif=1;
    if (direction=LEFT) colDif=-1;
    if (direction=RIGHT) colDif=1;
  }
}
