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
    BoxLayout lay=new BoxLayout(bigpanel,BoxLayout.X_AXIS);
    bigpanel.setLayout(lay);
    width.setPaintLabels(true);
    height.setPaintLabels(true);
    String[] generates={"Depth-First", "Prim Algorithm", "Recursive Division"};
    String[] solves={"Tramaux Algorithm", "A# Search", "Dead end filling"};
    generatemethod=new JComboBox(generates);
    solvemethod=new JComboBox(solves);
    JPanel bottom=new JPanel();
    bottom.add(width);
    bottom.add(height);
    bottom.add(generatemethod);
    bottom.add(solvemethod);
    bottom.add(mazebutton);
    bottom.add(solvebutton);
    bigpanel.add(maze);
    bigpanel.add(bottom);
    add(bigpanel);
    setVisible(true);
  }
}
class MazePanel extends JPanel {
  public MazePanel() {
    super();
  }
}
