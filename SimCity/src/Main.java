import javax.swing.JFrame;
import Menu.MenuAlcaldes;
import Menu.MenuPrincipal;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        JFrame window = new JFrame("SimCity JAVA");
        window.setSize(600, 400);
        window.setDefaultCloseOperation(window.EXIT_ON_CLOSE);
        window.add(new MenuPrincipal());
        window.setVisible(true);
    }
    
}
