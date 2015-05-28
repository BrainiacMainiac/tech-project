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
  String[][] mazeArray;
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
            graf.setColor(Color.RED);
            FontMetrics met=getFontMetrics(font);
            int a=x + (cellWidth - met.stringWidth(mazeArray[row][col].substring(1,mazeArray[row][col].length())))/2;
            int b=y + (cellHeight/2);
            graf.drawString(mazeArray[row][col].substring(1,mazeArray[row][col].length()),a,b);
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
            graf.setColor(Color.RED);
            FontMetrics mat=getFontMetrics(font);
            int c=x + (cellWidth - mat.stringWidth(mazeArray[row][col].substring(1,mazeArray[row][col].length())))/2;
            int d=y + (cellHeight/2);
            graf.drawString(mazeArray[row][col].substring(1,mazeArray[row][col].length()),c,d);
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
    String[][] ret=generate(rows-2,cols-2);
    for (int row=1; row<rows-1; row++) {
      for (int col=1; col<cols-1; col++) {
        mazeArray[row][col]=ret[row-1][col-1];
      }
    }
    repaint();
  }
public String[][] generate(int rows, int cols) {
    String[][] out=new String[rows][cols];
    for (int i=0; i<rows; i++) {
      for (int a=0; a<cols; a++) {
        out[i][a]=".";
      }
    }
    if (rows<=1 || cols<=1) {
      return out;
    }
    int vertLine=0;
    int horLine=0;
      horLine=1 + 2*(int)(Math.random()*((rows-3)/2+1));
      for (int i=0; i<cols; i++) {
        out[horLine][i]="/";
    }
      vertLine=1 + 2*(int)(Math.random()*((cols-3)/2+1));
      for (int i=0; i<rows; i++) {
        out[i][vertLine]="/";
    }
    boolean up=false;
    boolean down=false;
    boolean left=false;
    boolean right=false;
    int hGap=2*(int)(Math.random()*(cols+1)/2);
    out[horLine][hGap]=".";
    if (hGap<vertLine) {
      left=true;
    } else {
      right=true;
    }
    int vGap=2*(int)(Math.random()*((rows+1)/2));
    out[vGap][vertLine]=".";
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
      out[2*((int)Math.random()*((horLine+1)/2))][vertLine]=".";
    }
    if (li.get(0).equals("down")) {
      out[horLine +1 + 2*((int)Math.random()*((rows-horLine)/2))][vertLine]=".";
    }
    if (li.get(0).equals("left")) {
      out[horLine][2*((int)Math.random()*((vertLine+1)/2))]=".";
    }
    if (li.get(0).equals("right")) {
      out[horLine][vertLine  +1 +2*((int)Math.random()*((rows-vertLine)/2))]=".";
    }
    String[][] ul=generate(horLine,vertLine);
    for (int i=0; i<horLine; i++) {
      for (int a=0; a<vertLine; a++) {
        out[i][a]=ul[i][a];
      }
    }
    String[][] ur=generate(horLine,cols-vertLine-1);
    for (int i=0; i<horLine; i++) {
      for (int a=0; a<cols-vertLine-1; a++) {
        out[i][a+vertLine+1]=ur[i][a];
      }
    }
    String[][] dl=generate(rows-horLine-1,vertLine);
    for (int i=0; i<rows-horLine-1; i++) {
      for (int a=0; a<vertLine; a++) {
        out[i+horLine+1][a]=dl[i][a];
      }
    }
     String[][] dr=generate(rows-horLine-1,cols-vertLine-1);
    for (int i=0; i<rows-horLine-1; i++) {
      for (int a=0; a<cols-vertLine-1; a++) {
        out[i+horLine+1][a+vertLine+1]=dr[i][a];
      }
    }
    if (rows==mazeArray.length-2) {
      if (li.get(1).equals("up")) {
        out[0][cols-1]="-";
        out[0][0]="+";
      }else if (li.get(1).equals("left")) {
         out[0][0]="+";
         out[rows-1][0]="-";
      } else if (li.get(1).equals("right")){
        out[0][cols-1]="+";
        out[rows-1][cols-1]="-";
      } else {
        out[rows-1][0]="+";
        out[rows-1][cols-1]="-";
      }
    }
    return out;
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
