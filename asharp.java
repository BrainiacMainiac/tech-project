public class ASharp {
  ArrayList children=new ArrayList();
  int row,col,dist;
  boolean valid=true;
  public ASharp(int x,int y, int fromStart) {
    row=x;
    col=y;
    dist=fromStart;
  }
  public boolean step() {
    boolean isValid=false;
    if (children.length==0) {
      if (row!=1 && TechProject.proj.maze.mazeArray[row][col-2].equals(".")) {
        children.add(new ASharp(row,col-2,dist+1));
        isValid=true;
        TechProject.proj.maze.mazeArray[row][col-2]="t" + (dist+2);
        TechProject.proj.maze.mazeArray[row][col-1]="t" + (dist+1);
      }
      if (row!=TechProject.proj.maze.mazeArray[0].length-2 && TechProject.proj.maze.mazeArray[row][col+2].equals(".")) {
        children.add(new ASharp(row,col+2,dist+1));
        isValid=true;
        TechProject.proj.maze.mazeArray[row][col+2]="t" + (dist+2);
        TechProject.proj.maze.mazeArray[row][col+1]="t" + (dist+1);
      }
      if (col!=1 && TechProject.proj.maze.mazeArray[row-2][col].equals(".")) {
        children.add(new ASharp(row-2,col,dist+1));
        isValid=true;
        TechProject.proj.maze.mazeArray[row-2][col]="t" + (dist+2);
        TechProject.proj.maze.mazeArray[row-1][col]="t" + (dist+1);
      }
      if (row!=TechProject.proj.maze.mazeArray.length && TechProject.proj.maze.mazeArray[row+2][col].equals(".")) {
        children.add(new ASharp(row+2,col,dist+1));
        isValid=true;
        TechProject.proj.maze.mazeArray[row+2][col]="t" + (dist+2);
        TechProject.proj.maze.mazeArray[row+1][col]="t" + (dist+1);
      }
    }
    for (int i=0; i<children.length;i++) {
      if (children[i].valid && children[i].step()) isValid=true;
    }
    if (isValid) {
      TechProject.proj.maze.mazeArray[row][col]="t" + dist;
    } else {
      TechProject.proj.maze.mazeArray[row][col]="f"+dist;
    }
    return isValid;
  }
}
