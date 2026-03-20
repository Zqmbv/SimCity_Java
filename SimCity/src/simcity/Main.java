package simcity;
import simcity.WorldEngine.Editor2D.*;
import simcity.WorldEngine.Editor3D.*;
import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {
        JFrame window = new JFrame("SimCity");
        window.setSize(600, 400);
        window.setDefaultCloseOperation(window.EXIT_ON_CLOSE);
        window.add(new View3D());
        window.setVisible(true);
    }
    
}
