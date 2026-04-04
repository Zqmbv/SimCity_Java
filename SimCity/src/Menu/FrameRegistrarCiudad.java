package Menu;

import BDD.ConexionPostgres;
import java.sql.ResultSet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public final class FrameRegistrarCiudad extends JFrame implements ActionListener,WindowListener{
    
    private final Font fTitulo = new Font("Segoe UI",Font.BOLD,18);
    private final Font fSubTitulo = new Font("Segoe UI",Font.BOLD,13);
    private final Font fTexto = new Font("Segoe UI",Font.PLAIN,13);
    
    private final Color cBarra = new Color(230,230,230); 
    private final Color cBorde = new Color(80,80,80); 
    private final Color cFondo = new Color(215,215,215); 
    private final Color cBoton = new Color(0,112,192); 
    private final Color cText1 = new Color(0,0,0); 
    private final Color cText2 = new Color(255,255,255); 
    
    private final int MAX_LOGITUD_NOMBRE = 64;
    private final int MAX_LOGITUD_DESCRIPCION = 100;

    private final MenuCiudades MenuPadre;
    
    JPanel pTitulo = new JPanel();
    JLabel lTitulo = new JLabel("CREAR NUEVA CIUDAD");
    
    JPanel pDatos = new JPanel();
    JLabel lNombre = new JLabel("Nombre:");
    JLabel lDimension = new JLabel("Dimensión:");
    JLabel lDescripcion = new JLabel("Descripción:");
    
    JTextField tfNombreCiudad = new JTextField();
    String[] opciones = {"32*32","64*64","128*128","256*256"};
    JComboBox cbDimension = new JComboBox(opciones);
    JTextField tfDescripcion = new JTextField();
    
    JPanel pBoton = new JPanel();
    JButton bAgregar = new JButton("Crear Ciudad");
    
    GridBagLayout GBL = new GridBagLayout();
    GridBagConstraints GBC = new GridBagConstraints();
    
    public FrameRegistrarCiudad(MenuCiudades MenuPadre) throws SQLException{
        this.MenuPadre = MenuPadre;
        
        setTitle("SimCity JAVA - Crear Ciudad");
        
        setPosition();
        setTema();
        setConfigComponente();
        
        this.addWindowListener(this);
        
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public void setTema(){
        pTitulo.setBackground(cBarra);
        pBoton.setBackground(cBarra);
        pDatos.setBackground(cFondo);

        bAgregar.setBackground(cBoton);
        bAgregar.setForeground(cText2);
        bAgregar.setFont(fSubTitulo);
        
        lTitulo.setFont(fTitulo);
        lNombre.setFont(fSubTitulo);
        lDimension.setFont(fSubTitulo);
        lDescripcion.setFont(fSubTitulo);

        tfNombreCiudad.setFont(fTexto);
        tfNombreCiudad.setBorder(BorderFactory.createLineBorder(cBorde));
        
        cbDimension.setFont(fTexto);
        cbDimension.setBorder(BorderFactory.createLineBorder(cBorde));
        
        tfDescripcion.setFont(fTexto);
        tfDescripcion.setBorder(BorderFactory.createLineBorder(cBorde));
        
        // PARA PONERLE COLOR A LOS JOPTIONPANE
        UIManager.put("OptionPane.background", cFondo);
        UIManager.put("OptionPane.messageForeground",cText1);
        UIManager.put("OptionPane.messageFont",fTexto);
        UIManager.put("Button.background",cBoton);
        UIManager.put("Button.foreground",cText2);
        UIManager.put("Button.focus", new Color(0, 0, 0, 0));               
        UIManager.put("Panel.background", cFondo);
    }
    
    public void setConfigComponente(){
        bAgregar.addActionListener(this);
    }
    
    public void setPosition() throws SQLException{
        setLayout(new BorderLayout());
        
        add(pTitulo,BorderLayout.NORTH);
        add(pDatos,BorderLayout.CENTER);
        add(pBoton,BorderLayout.SOUTH);
        
        pTitulo.setLayout(GBL);
        GBC.insets = new Insets(10, 10, 10, 10);
        GBC.anchor = GridBagConstraints.CENTER;
        GBC.gridx = 0; GBC.gridy = 0; pTitulo.add(lTitulo,GBC); 
        
        pBoton.setLayout(GBL);
        GBC.anchor = GridBagConstraints.CENTER;
        GBC.gridx = 0; GBC.gridy = 0; pBoton.add(bAgregar,GBC); GBC.ipady = 5;
     
        pDatos.setLayout(GBL);  
        GBC.anchor = GridBagConstraints.CENTER; GBC.fill = GridBagConstraints.BOTH;
        GBC.gridx = 0; GBC.gridy = 0; GBC.weightx = 0; GBC.insets = new Insets(20,20,10,10); pDatos.add(lNombre,GBC); 
        GBC.gridx = 1; GBC.gridy = 0; GBC.weightx = 1; GBC.insets = new Insets(20,10,10,20); pDatos.add(tfNombreCiudad,GBC); 
        GBC.gridx = 0; GBC.gridy = 1; GBC.weightx = 0; GBC.insets = new Insets(10,20,10,10); pDatos.add(lDimension,GBC); 
        GBC.gridx = 1; GBC.gridy = 1; GBC.weightx = 1; GBC.insets = new Insets(10,10,10,20); pDatos.add(cbDimension,GBC); 
        GBC.gridx = 0; GBC.gridy = 2; GBC.weightx = 0; GBC.insets = new Insets(10,20,10,10); pDatos.add(lDescripcion,GBC); 
        GBC.gridx = 1; GBC.gridy = 2; GBC.weightx = 1; GBC.insets = new Insets(10,10,10,20); pDatos.add(tfDescripcion,GBC); 
    }
    
    public String getCamposFaltantes(String TextoObtenido, String Mensaje){
        if(TextoObtenido.equals("")) return Mensaje;
        else return "";
    }
   
    private int contarCaracteres(String STR, char CHAR){
        int contador = 0;
        for(int c=0; c<STR.length(); c++){
            if(STR.charAt(c) == CHAR) contador++;
        }
        return contador;
    }
    
    private int contarPalabras(String STR){
        int contador = 0;
        String palabras[] = STR.split(" ");
        for(String palabra: palabras){
            if(!palabra.equals("")) contador++;
        }
        return contador;
    }
    
    private Boolean espacioCorrecto(String STR){
        return (contarPalabras(STR)>contarCaracteres(STR,' '));
    }

    private Boolean ciudadUnicaPorAlcalde(String STR, int ID) throws SQLException{
        ConexionPostgres BD = new ConexionPostgres();
        String QUERY = "SELECT nombre FROM ciudades WHERE nombre = ? AND idAlcalde = ? ;";
        Object VALUES[] = {STR,MenuCiudades.idAlcalde};
        
        ResultSet RS = BD.consultar(QUERY,VALUES);
        ArrayList<Object> T = new ArrayList<>();
        while(RS != null && RS.next()){
            T.add(RS.getString("nombre"));
        }
        
        return T.isEmpty();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String sNombreCiudad = tfNombreCiudad.getText().trim();
        String sDimension = cbDimension.getSelectedItem().toString().trim();
        String[] partes = sDimension.split("\\*");
        int x = Integer.parseInt(partes[0]);
        int y = Integer.parseInt(partes[1]);
        String sDescripcion = tfDescripcion.getText().trim();

        // VERIFICAR DATOS NO INGRESADOS 
        String sCamposFaltantes = "";
        sCamposFaltantes += getCamposFaltantes(sNombreCiudad,"* Nombre Ciudad\n");
 
        if(!sCamposFaltantes.equals("")){
            JOptionPane.showMessageDialog(this,"CAMPOS FALTANTES:\n"+sCamposFaltantes,"ERROR",JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // VERIFICAR NOMBRE CIUDAD
        if(sNombreCiudad.length() > MAX_LOGITUD_NOMBRE){
            JOptionPane.showMessageDialog(this,"Nombre no más de "+MAX_LOGITUD_NOMBRE+" caracteres.","ERROR",JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if(!espacioCorrecto(sNombreCiudad)){
            JOptionPane.showMessageDialog(this,"Separe correctamente los nombres con 1 solo espacio.","ERROR",JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            if(!ciudadUnicaPorAlcalde(sNombreCiudad,MenuCiudades.idAlcalde)){
                JOptionPane.showMessageDialog(this,"Nombre de Ciudad duplicada por Alcalde.","ERROR",JOptionPane.ERROR_MESSAGE);
                return; 
            }
        } catch (SQLException ex) {
            Logger.getLogger(FrameRegistrarCiudad.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // VERIFICAR DESCRIPCIÓN
        if(sDescripcion.length() > MAX_LOGITUD_DESCRIPCION){
            JOptionPane.showMessageDialog(this,"Descripción no más de "+MAX_LOGITUD_DESCRIPCION+" caracteres.","ERROR",JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if(sDescripcion.equals("")){
            sDescripcion = "Una próspera ciudad en crecimiento.";
        }

        // INSERTAR LOS DATOS UNA VEZ VERIFICADOS
        ConexionPostgres BDD = new ConexionPostgres();
        String Query = "INSERT INTO ciudades (nombre,x,y,descripcion,idAlcalde) VALUES (?,?,?,?,?)";
        Object values[] = {sNombreCiudad,x,y,sDescripcion,MenuCiudades.idAlcalde};
        
        try {
            BDD.comandoDML(Query,values);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        JOptionPane.showMessageDialog(this,"¡Ciudad Creada Correctamente!","ÉXITO",JOptionPane.INFORMATION_MESSAGE);
        try {
            MenuPadre.actualizarPanelCiudades();
            MenuPadre.setEnableButtons(true);
        } catch (SQLException ex) {
            Logger.getLogger(FrameRegistrarCiudad.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dispose();
    }
    
    @Override
    public void windowClosing(WindowEvent e) {
        MenuPadre.setEnableButtons(true);
    }

    @Override
    public void windowClosed(WindowEvent e) {}
    @Override
    public void windowOpened(WindowEvent e) {}
    @Override
    public void windowIconified(WindowEvent e) {}
    @Override
    public void windowDeiconified(WindowEvent e) {}
    @Override
    public void windowActivated(WindowEvent e) {}
    @Override
    public void windowDeactivated(WindowEvent e) {}
}