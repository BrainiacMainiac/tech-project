// Import javax.swing.*
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
class TechProject extends JFrame implements ActionListener
{
  //Instance variables/objects which all methods must be able to see
  public MazePanel maze=new MazePanel();
  static TechProject proj=new TechProject();
  JButton mazebutton=new JButton("Generate a new maze");
  JButton solvebutton=new JButton("Solve the Maze");
  JSlider width=new JSlider(2,200);
  JSlider height=new JSlider(2,200);
  JComboBox generatemethod;
  JComboBox solvemethod;
  JCheckBox slow=new JCheckBox("Slow Mode");
  JButton clear=new JButton("Restore the maze");
  public static void main(String[] args){
    int x=0;
  }
  public void actionPerformed(ActionEvent e) {
    if (e.getSource()==clear) {
      for (int i=0; i<maze.mazeArray.length; i++) {
      for (int a=0; a<maze.mazeArray[0].length; a++) {
        String s=maze.mazeArray[i][a];
        if (s.equals("x") || s.charAt(0)=='t' || s.charAt(0)=='f' || s.equals("*") || s.equals(":")) maze.mazeArray[i][a]=".";
      }
    }
    repaint();
    } else {
    mazebutton.setEnabled(false);
    solvebutton.setEnabled(false);
    clear.setEnabled(false);
    for (int i=0; i<maze.mazeArray.length; i++) {
      for (int a=0; a<maze.mazeArray[0].length; a++) {
        String s=maze.mazeArray[i][a];
        if (s.equals("x") || s.charAt(0)=='t' || s.charAt(0)=='f' || s.equals("*") || s.equals(":")) maze.mazeArray[i][a]=".";
      }
    }
    Object sor=e.getSource();
    if (sor==mazebutton) {
      String item=(String) generatemethod.getSelectedItem();
      if (item.equals("Recursive Division")) maze.mode=MazePanel.GENDIV;
      if (item.equals("Depth-First")) maze.mode=MazePanel.GEN_DFS;
      if (item.equals("Prim Algorithm")) maze.mode=MazePanel.GEN_PRIM;
    }
    if (sor==solvebutton) {
      String item=(String) solvemethod.getSelectedItem();
      if (item.equals("Dead end filling"))  maze.mode=MazePanel.DEAD_END;
      if (item.equals("Tramaux Algorithm")) maze.mode=MazePanel.TREM;
      if (item.equals("A# Search")) maze.mode=MazePanel.A_SHARP;
    }
    maze.current=new Thread(maze);
    maze.current.start();
    }
  }
  public TechProject() {
    //Setting up the GUI
    super("Maze Generator/Solver");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JPanel bigpanel=new JPanel();
    BorderLayout lay=new BorderLayout();
    bigpanel.setLayout(lay);
    width.setPaintLabels(true);
    width.setPaintTicks(true);
    width.setMajorTickSpacing(66);
    height.setMajorTickSpacing(66);
    height.setPaintLabels(true);
    height.setPaintTicks(true);
    String[] generates={"Depth-First", "Prim Algorithm", "Recursive Division"};
    String[] solves={"Tramaux Algorithm", "A# Search", "Dead end filling"};
    generatemethod=new JComboBox(generates);
    solvemethod=new JComboBox(solves);
    mazebutton.addActionListener(this);
    solvebutton.addActionListener(this);
    clear.addActionListener(this);
    //Adding stuff to the bottom panel (all but the maze)
    JPanel bottom=new JPanel();
    bottom.setLayout(new GridLayout(2,6,10,10));
    bottom.add(new JLabel("Width:"));
    bottom.add(width);
    bottom.add(new JLabel("Generation Algorithm:"));
    bottom.add(generatemethod);
    bottom.add(mazebutton);
    bottom.add(clear);
    bottom.add(new JLabel("Height:"));
    bottom.add(height);
    bottom.add(new JLabel("Solving Algorithm:"));
    bottom.add(solvemethod);
    bottom.add(solvebutton);
    bottom.add(slow);
    bigpanel.add(maze,BorderLayout.CENTER);
    bigpanel.add(bottom,BorderLayout.SOUTH);
    add(bigpanel);
    setSize(600,600);
    maze.generateDivision(11,11);
    setVisible(true);
  }
}
class MazePanel extends JPanel implements Runnable{
  public String[][] mazeArray;
  int cellWidth;
  int cellHeight;
  Thread current;
  final static byte GENDIV=0;
  final static byte DEAD_END=1;
  final static byte GEN_DFS=2;
  final static byte GEN_PRIM=3;
  final static byte TREM=4;
  final static byte A_SHARP=5;
  byte mode;
  boolean slowmo=false;
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
  public void run() {
    slowmo=TechProject.proj.slow.isSelected();
    if (mode==GENDIV) {
      int wid=TechProject.proj.width.getValue()*2+1;
      int hei=TechProject.proj.height.getValue()*2+1;
      generateDivision(hei,wid);
    }
    if (mode==DEAD_END) {
      deadEndSolve();
    }
    if (mode==GEN_DFS) {
      int wid=TechProject.proj.width.getValue()*2+1;
      int hei=TechProject.proj.height.getValue()*2+1;
      generateDFS(hei,wid);
    }
    if (mode==A_SHARP) {
      ASharp.ASharpSolve();
    }
    TechProject.proj.mazebutton.setEnabled(true);
    TechProject.proj.solvebutton.setEnabled(true);
    TechProject.proj.clear.setEnabled(true);
  }
  public MazePanel() {
    super();
    //this is for testing
    mazeArray= new String[][]{
    };
  }
  public void paintComponent(Graphics g) {
    Graphics2D graf=(Graphics2D) g;
    graf.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
    Dimension rect=getSize();
    Font font=new Font("dfg",Font.PLAIN, 12);
    graf.setFont(font);
    cellHeight=rect.height/mazeArray.length;
    cellWidth=rect.width/mazeArray[0].length;

    for (int row=0; row<mazeArray.length; row++) {
      for (int col=0; col<mazeArray[0].length; col++) {
        int x=col*cellWidth;
        int y=row*cellHeight;
        char p=mazeArray[row][col].charAt(0);
        switch (p) {
          case '.':
            graf.setColor(Color.WHITE);
            graf.fillRect(x,y,cellWidth,cellHeight);
            break;
          case '/':
            //wall
            graf.setColor(Color.BLACK);
            graf.fillRect(x,y,cellWidth,cellHeight);
            break;
          case 'x':
            //dead end
            graf.setColor(Color.RED);
            graf.fillRect(x,y,cellWidth,cellHeight);
            break;
            case 'f':
            //bad a# space
            graf.setColor(new Color(129,129,129));
            graf.fillRect(x,y,cellWidth,cellHeight);
            /* Text is ugly
            graf.setColor(Color.RED);
            FontMetrics met=getFontMetrics(font);
            int a=x + (cellWidth - met.stringWidth(mazeArray[row][col].substring(1,mazeArray[row][col].length())))/2;
            int b=y + (cellHeight/2);
            graf.drawString(mazeArray[row][col].substring(1,mazeArray[row][col].length()),a,b);
            */
            //show distance
            break;
            case ':':
            //wrong path
            graf.setColor(new Color(129,129,129));
            graf.fillRect(x,y,cellWidth,cellHeight);
            break;
            case 't':
            //possible A#
            graf.setColor(Color.BLUE);
            graf.fillRect(x,y,cellWidth,cellHeight);
            /*
            graf.setColor(Color.RED);
            FontMetrics mat=getFontMetrics(font);
            int c=x + (cellWidth - mat.stringWidth(mazeArray[row][col].substring(1,mazeArray[row][col].length())))/2;
            int d=y + (cellHeight/2);
            graf.drawString(mazeArray[row][col].substring(1,mazeArray[row][col].length()),c,d);
            */
            break;
            case '*':
            //possible path
            graf.setColor(Color.BLUE);
            graf.fillRect(x,y,cellWidth,cellHeight);
            break;
            case '+':
            //start
            graf.setColor(Color.GREEN);
            graf.fillRect(x,y,cellWidth,cellHeight);
            break;
            case '-':
            //end
            graf.setColor(Color.GREEN);
            graf.fillRect(x,y,cellWidth,cellHeight);
            break;
        }
      }
  }
}
  void generateDivision(int rows,int cols) {
    mazeArray=new String[rows][cols];
    for (int row=0; row<rows; row++) {
      for (int col=0; col<cols; col++) {
        if (row==0 || col==0 || row==rows-1 || col==cols-1) {
          mazeArray[row][col]="/";
        } else {
          mazeArray[row][col]=".";
        }
      }
    }
    repaint();
    generate(rows-2,cols-2,1,1);
    repaint();
  }
public void generate(int rows, int cols,int rowDif, int colDif) {
    if (rows<=1 || cols<=1) {
      return;
    }
    int vertLine=0;
    int horLine=0;
      horLine=1 + 2*(int)(Math.random()*((rows-3)/2+1));
      for (int i=0; i<cols; i++) {
        mazeArray[horLine+rowDif][i+colDif]="/";
    }
      vertLine=1 + 2*(int)(Math.random()*((cols-3)/2+1));
      for (int i=0; i<rows; i++) {
        mazeArray[i+rowDif][vertLine+colDif]="/";
    }
    repaint();
    try {
    Thread.sleep((slowmo ? 30000/(mazeArray.length+mazeArray[0].length) : 20000/(mazeArray.length*mazeArray[0].length)));
    } catch (Exception e) {
    }
    boolean up=false;
    boolean down=false;
    boolean left=false;
    boolean right=false;
    int hGap=2*(int)(Math.random()*(cols+1)/2);
    mazeArray[horLine+rowDif][hGap+colDif]=".";
    if (hGap<vertLine) {
      left=true;
    } else {
      right=true;
    }
    int vGap=2*(int)(Math.random()*((rows+1)/2));
    mazeArray[vGap+rowDif][vertLine+colDif]=".";
    if (vGap<horLine) {
      up=true;
    } else {
      down=true;
    }
    ArrayList li=new ArrayList();
    if (!up) li.add("up");
    if (!down) li.add("down");
    if (!left) li.add("left");
    if (!right) li.add("right");
    Collections.shuffle(li);
    if (li.get(0).equals("up")) {
      mazeArray[(2*((int)Math.random()*((horLine+1)/2)))+rowDif][vertLine+colDif]=".";
    }
    if (li.get(0).equals("down")) {
      mazeArray[(horLine +1 + 2*((int)Math.random()*((rows-horLine)/2)))+rowDif][vertLine+colDif]=".";
    }
    if (li.get(0).equals("left")) {
      mazeArray[horLine+rowDif][(2*((int)Math.random()*((vertLine+1)/2)))+colDif]=".";
    }
    if (li.get(0).equals("right")) {
      mazeArray[horLine+rowDif][(vertLine  +1 +2*((int)Math.random()*((rows-vertLine)/2)))+colDif]=".";
    }
    repaint();
    try {
    Thread.sleep((slowmo ? 30000/(mazeArray.length+mazeArray[0].length) : 20000/(mazeArray.length*mazeArray[0].length)));
    } catch (Exception e) {
    }
    generate(horLine,vertLine,rowDif,colDif);
    repaint();
    generate(horLine,cols-vertLine-1,rowDif,colDif+vertLine+1);
    repaint();
    generate(rows-horLine-1,vertLine,rowDif+horLine+1,colDif);
    repaint();
    generate(rows-horLine-1,cols-vertLine-1,rowDif+horLine+1,colDif+vertLine+1);
    repaint();
    if (rows==mazeArray.length-2) {
      if (li.get(1).equals("up")) {
        mazeArray[1][cols]="-";
        mazeArray[1][1]="+";
      }else if (li.get(1).equals("left")) {
         mazeArray[1][1]="+";
         mazeArray[rows][1]="-";
      } else if (li.get(1).equals("right")){
        mazeArray[1][cols]="+";
        mazeArray[rows][cols]="-";
      } else {
        mazeArray[rows][1]="+";
        mazeArray[rows][cols]="-";
      }
    }
  }
  public void deadEndSolve() {
    boolean somethingChanged = true;
    while(somethingChanged) {
      somethingChanged = false;
      for(int i = 0; i < mazeArray.length; i++){
        for(int j = 0; j < mazeArray[i].length; j++){
          if (mazeArray[i][j].equals(".")){
            int walls = 0;
            if (mazeArray[i][j + 1] == "/" || mazeArray[i][j+1] == "x") {
              walls++;
            }
            if (mazeArray[i][j - 1] == "/" || mazeArray[i][j-1] == "x") {
              walls++;
            }
            if (mazeArray[i + 1][j] == "/" || mazeArray[i + 1][j] == "x") {
              walls++;
            }
            if (mazeArray[i - 1][j] == "/" || mazeArray[i - 1][j] == "x") {
              walls++;
            }
            if (walls >= 3){
              mazeArray[i][j] = "x";
              somethingChanged = true;
              try {
              Thread.sleep(slowmo ? 5000/(mazeArray.length+mazeArray[0].length):50000/(mazeArray.length*mazeArray[0].length));
              } catch (Exception e) {
              }
              repaint();
              }
            }
          }
        }
      }
  }
  public void generateDFS(int rows, int cols) {
    mazeArray = new String[rows][cols];
    for(int i = 0; i < mazeArray.length; i++){
        for(int j = 0; j < mazeArray[i].length; j++){
            mazeArray[i][j] = "/";
        }
    }
    mazeArray[1][1]=".";
    DFS(1,1);
    mazeArray[1][1]="+";
    mazeArray[rows-2][cols-2]="-";
    repaint();
}
public void DFS(int row, int col) {
    repaint();
    try {
    Thread.sleep(slowmo ? 5000/(mazeArray.length+mazeArray[0].length):50000/(mazeArray.length*mazeArray[0].length));
    } catch (Exception e) {
    }
    ArrayList li = new ArrayList();
    li.add((byte)2);
    li.add((byte)3);
    li.add((byte)0);
    li.add((byte)1);
    Collections.shuffle(li);
    for (int i=0; i<4; i++) {
    byte test =(byte) li.get(i);
    if (test==2) {
        if (col!=1 && mazeArray[row][col-2].equals("/")) {
            mazeArray[row][col-2]=".";
            mazeArray[row][col-1]=".";
            DFS(row,col-2);
        }
    }
    if (test==3) {
        if (col!=mazeArray[0].length-2 && mazeArray[row][col+2].equals("/")) {
            mazeArray[row][col+2]=".";
            mazeArray[row][col+1]=".";
            DFS(row,col+2);
        }
    }
    if (test==0) {
        if (row!=1 && mazeArray[row-2][col].equals("/")) {
            mazeArray[row-2][col]=".";
            mazeArray[row-1][col]=".";
            DFS(row-2,col);
        }
    }
    if (test==1) {
        if (row!=mazeArray.length-2 && mazeArray[row+2][col].equals("/")) {
            mazeArray[row+2][col]=".";
            mazeArray[row+1][col]=".";
            DFS(row+2,col);
        }
    }
}
}

}
class ASharp {
  ArrayList children=new ArrayList();
  int row,col,dist;
  boolean valid=true;
  static boolean changed=true;
  static int distToStart=0;
  public ASharp(int x,int y, int fromStart) {
    row=x;
    col=y;
    dist=fromStart;
    valid=true;
  }
  public boolean step() {
    boolean isValid=false;
    if (children.size()==0) {
      if (col!=1 && TechProject.proj.maze.mazeArray[row][col-2].equals(".") && TechProject.proj.maze.mazeArray[row][col-1].equals(".")) {
        children.add(new ASharp(row,col-2,dist+2));
        isValid=true;
        changed=true;
        TechProject.proj.maze.mazeArray[row][col-2]="t" + (dist+2);
        TechProject.proj.maze.mazeArray[row][col-1]="t" + (dist+1);
      }
      if (col!=TechProject.proj.maze.mazeArray[0].length-2 && TechProject.proj.maze.mazeArray[row][col+2].equals(".") && TechProject.proj.maze.mazeArray[row][col+1].equals(".")) {
        children.add(new ASharp(row,col+2,dist+2));
        isValid=true;
        changed=true;
        TechProject.proj.maze.mazeArray[row][col+2]="t" + (dist+2);
        TechProject.proj.maze.mazeArray[row][col+1]="t" + (dist+1);
      }
      if (row!=1 && TechProject.proj.maze.mazeArray[row-2][col].equals(".") && TechProject.proj.maze.mazeArray[row-1][col].equals(".")) {
        children.add(new ASharp(row-2,col,dist+2));
        isValid=true;
        changed=true;
        TechProject.proj.maze.mazeArray[row-2][col]="t" + (dist+2);
        TechProject.proj.maze.mazeArray[row-1][col]="t" + (dist+1);
      }
      if (row!=TechProject.proj.maze.mazeArray.length-2 && TechProject.proj.maze.mazeArray[row+2][col].equals(".") && TechProject.proj.maze.mazeArray[row+1][col].equals(".")) {
        children.add(new ASharp(row+2,col,dist+2));
        isValid=true;
        changed=true;
        TechProject.proj.maze.mazeArray[row+2][col]="t" + (dist+2);
        TechProject.proj.maze.mazeArray[row+1][col]="t" + (dist+1);
      }
    } else {
    for (int i=0; i<children.size();i++) {
      if (((ASharp)children.get(i)).valid && ((ASharp)children.get(i)).step()) isValid=true;
    }
    }
    if ((col!=1 && TechProject.proj.maze.mazeArray[row][col-2].equals("-")) && (TechProject.proj.maze.mazeArray[row][col-1].equals(".") || TechProject.proj.maze.mazeArray[row][col-1].charAt(0)=='t')) {
      isValid=true;
      distToStart=Math.max(dist+2,distToStart);
      TechProject.proj.maze.mazeArray[row][col-1]="t" + dist+1;
    }
    if ((col!=TechProject.proj.maze.mazeArray[0].length-2 && TechProject.proj.maze.mazeArray[row][col+2].equals("-"))&& (TechProject.proj.maze.mazeArray[row][col+1].equals(".")  || TechProject.proj.maze.mazeArray[row][col+1].charAt(0)=='t')) {
      isValid=true;
      distToStart=Math.max(dist+2,distToStart);
      TechProject.proj.maze.mazeArray[row][col+1]="t" + dist+1;
    }
    if ((row!=1 && TechProject.proj.maze.mazeArray[row-2][col].equals("-"))&& (TechProject.proj.maze.mazeArray[row-1][col].equals(".") || TechProject.proj.maze.mazeArray[row-1][col].charAt(0)=='t')) {
      isValid=true;
      distToStart=Math.max(dist+2,distToStart);
      TechProject.proj.maze.mazeArray[row-1][col]="t" + dist+1;
    }
    if ((row!=TechProject.proj.maze.mazeArray.length-2 && TechProject.proj.maze.mazeArray[row+2][col].equals("-")) && (TechProject.proj.maze.mazeArray[row+1][col].equals(".") || TechProject.proj.maze.mazeArray[row+1][col].charAt(0)=='t')) {
      isValid=true;
      distToStart=Math.max(dist+2,distToStart);
      TechProject.proj.maze.mazeArray[row+1][col]="t" + dist+1;
    }
    if (isValid) {
      TechProject.proj.maze.mazeArray[row][col]="t" + dist;
    } else {
      TechProject.proj.maze.mazeArray[row][col]="f"+dist;
    }
    for (int i=0; i<children.size(); i++) {
      ASharp a=(ASharp)children.get(i);
      if (!a.valid) TechProject.proj.maze.mazeArray[(row+a.row)/2][(col+a.col)/2]="f" + dist+1;
    }
    TechProject.proj.maze.repaint();
    valid=isValid;
    return isValid;
  }
  static void ASharpSolve() {
    ASharp start=new ASharp(0,0,0);
    distToStart=0;
    changed=true;
    for (int i=0; i<TechProject.proj.maze.mazeArray.length; i++) {
      for (int j=0; j<TechProject.proj.maze.mazeArray[0].length; j++) {
        if (TechProject.proj.maze.mazeArray[i][j].equals("+")) {
          start=new ASharp(i,j,0);
        }
      }
    }
      while (ASharp.changed) {
        changed=false;
        start.step();
        TechProject.proj.maze.repaint();
        try {
        Thread.sleep(TechProject.proj.maze.slowmo ? 30000/(TechProject.proj.maze.mazeArray.length+TechProject.proj.maze.mazeArray[0].length):200000/(TechProject.proj.maze.mazeArray.length*TechProject.proj.maze.mazeArray[0].length));
      } catch (Exception e) {
      }
      }
      TechProject.proj.maze.mazeArray[start.row][start.col]="+";
      JOptionPane.showMessageDialog(null,"The end was reached in " + distToStart + " spaces.");
      TechProject.proj.maze.repaint();
    }
}
