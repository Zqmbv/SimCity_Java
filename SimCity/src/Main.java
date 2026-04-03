import javax.swing.JFrame;
import Editor.Inspector;

public class Main {

    public static void main(String[] args) {
        JFrame window = new JFrame("SimCity");
        window.setSize(600, 400);
        window.setDefaultCloseOperation(window.EXIT_ON_CLOSE);
        window.add(new Inspector());
        window.setVisible(true);
    }
    
}
