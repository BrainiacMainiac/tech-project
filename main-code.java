// Import javax.swing.*
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
class TechProject extends JFrame{
  MazePanel maze;
  JButton mazebutton=new JButton("Generate a new maze");
  JButton solvebutton=new JButton("Solve the Maze");
  public static void main(String[] args){
    TechProject proj= new TechProject();
  }
  public TechProject() {
    super("Maze Generator/Solver");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JPanel bigpanel=new JPanel();
    BoxLayout lay=new BoxLayout(bigpanel,BoxLayout.X_AXIS);
    bigpanel.setLayout(lay);
  }
}
