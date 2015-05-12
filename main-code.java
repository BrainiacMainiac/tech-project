// Import javax.swing.*
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
class TechProject extends JFrame implements ActionListener{
  //Instance variables/objects which all methods must be able to see
  MazePanel maze=new MazePanel();
  JButton mazebutton=new JButton("Generate a new maze");
  JButton solvebutton=new JButton("Solve the Maze");
  JSlider width=new JSlider(2,100,10);
  JSlider heigth=new JSlider(2,100,10);
  JComboBox generatemethod=new JComboBox();
  JComboBox solvemethod=new JComboBox();
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
    generatemethod.add({"Depth-First", "Prim Algorithm", "Recursive Division"});
    solvemethod.add({"Tramaux Algorithm", "A# Search", "Dead end filling"});
    JPanel bottom=new JPanel();
    bottom.add(width);
    bottom.add(heigth);
    bottom.add(generatemethod);
    bottom.add(solvemethod);
    bottom.add(mazebutton);
    bottom.add(solvebutton);
    bigpanel.add(maze);
    bigpanel.add(bottom);
    setVisible(true);
  }
}
class MazePanel extends JPanel {
  public MazePanel() {
    super();
  }
}
