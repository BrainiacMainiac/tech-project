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
    width.setMajorTickSpacing(10);
    height.setMajorTickSpacing(10);
    height.setPaintLabels(true);
    height.setPaintTicks(true);
    String[] generates={"Depth-First", "Prim Algorithm", "Recursive Division"};
    String[] solves={"Tramaux Algorithm", "A# Search", "Dead end filling"};
    generatemethod=new JComboBox(generates);
    solvemethod=new JComboBox(solves);
    //Adding stuff to the bottom panel (all but the maze)
    JPanel bottom=new JPanel();
    bottom.add(new JLabel("Width:"));
    bottom.add(width);
    bottom.add(new JLabel("Height:"));
    bottom.add(height);
    bottom.add(new JLabel("Generation Algorithm"));
    bottom.add(generatemethod);
    bottom.add(new JLabel("Solving Algorithm"));
    bottom.add(solvemethod);
    bottom.add(mazebutton);
    bottom.add(solvebutton);
    bigpanel.add(maze,BorderLayout.CENTER);
    bigpanel.add(bottom,BorderLayout.SOUTH);
    add(bigpanel);
    setSize(400,400);
    setVisible(true);
  }
}
class MazePanel extends JPanel {
  public MazePanel() {
    super();
  }
}
