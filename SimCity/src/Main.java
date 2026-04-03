import javax.swing.JFrame;
import Menu.Menu;
import java.sql.SQLException;

public class Main {
   

    public static void main(String[] args) throws SQLException {
        JFrame window = new JFrame("SimCity");
        window.setSize(600, 400);
        window.setDefaultCloseOperation(window.EXIT_ON_CLOSE);
        window.add(new Menu());
        window.setVisible(true);
    }
    
}
