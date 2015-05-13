// Import javax.swing.*
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
class TechProject extends JFrame// implements ActionListener
{
  //Instance variables/objects which all methods must be able to see
  MazePanel maze=new MazePanel();
  JButton mazebutton=new JButton("Generate a new maze");
  JButton solvebutton=new JButton("Solve the Maze");
  JSlider width=new JSlider(2,100,10);
  JSlider height=new JSlider(2,100,10);
  JComboBox generatemethod;
  JComboBox solvemethod;
  public static void main(String[] args){
    TechProject proj= new TechProject();
    thred=new Maze();
    thred.start();
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
    width.setMajorTickSpacing(20);
    height.setMajorTickSpacing(20);
    height.setPaintLabels(true);
    height.setPaintTicks(true);
    String[] generates={"Depth-First", "Prim Algorithm", "Recursive Division"};
    String[] solves={"Tramaux Algorithm", "A# Search", "Dead end filling"};
    generatemethod=new JComboBox(generates);
    solvemethod=new JComboBox(solves);
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
    setVisible(true);
  }
}
class Maze implements Runnable {
  public void run() {
    while (true) {
      new Maze().run();
    }
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
  //My dad thought the big, upper right red x only closed on tab, so he closed all my tabs and I lost about 1 hour of work.
  //The entire maze rendering code was almost finished, but then he closed it.
  //Now commiting every 2 minutes after losing 1 HOUR of work
  public MazePanel() {
    super();
    //this is for testing
    mazeArray= new String[][]{
      {"/","+","."},
      {"-",":","x"},
      {"f4","/","t18"}
    };
  }
  public void paintComponent(Graphics g) {
    Graphics2D graf=(Graphics2D) g;
    Dimension rect=getSize();
    cellHeight=rect.height/mazeArray.length;
    cellWidth=rect.width/mazeArray[0].length;
    for (int row=0; row<mazeArray.length; row++) {
  }
}
}
