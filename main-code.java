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
  public static void main(String[] args){
    int x=0;
  }
  public void actionPerformed(ActionEvent e) {
    Object sor=e.getSource();
    int wid=width.getValue()*2+1;
    int hei=height.getValue()*2+1;
    boolean gen=false;
    if (sor==mazebutton) {
      maze.generateDivision(wid,hei);
      gen=true;
    }
    if (sor==solvebutton) {
      maze.deadEndSolve();
    }
    if (gen) {
      maze.mazeArray[1][1]="+";
      maze.mazeArray[wid-2][hei-2]="-";
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
    //Adding stuff to the bottom panel (all but the maze)
    JPanel bottom=new JPanel();
    bottom.setLayout(new GridLayout(2,5,10,10));
    bottom.add(new JLabel("Width:"));
    bottom.add(width);
    bottom.add(new JLabel("Generation Algorithm"));
    bottom.add(generatemethod);
    bottom.add(mazebutton);
    bottom.add(new JLabel("Height:"));
    bottom.add(height);
    bottom.add(new JLabel("Solving Algorithm"));
    bottom.add(solvemethod);
    bottom.add(solvebutton);
    bigpanel.add(maze,BorderLayout.CENTER);
    bigpanel.add(bottom,BorderLayout.SOUTH);
    add(bigpanel);
    setSize(800,800);
    maze.generateDivision(11,11);
    setVisible(true);
  }
}
class MazePanel extends JPanel {
  String[][] mazeArray;
  int cellWidth;
  int cellHeight;
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
              repaint();
              }
            }
          }
        }
      }
  }
}
