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

public final class MenuCrearCiudad extends JFrame implements ActionListener,WindowListener{
    
    private final Font fTitulo = new Font("Segoe UI",Font.BOLD,18);
    private final Font fSubTitulo = new Font("Segoe UI",Font.BOLD,13);
    private final Font fTexto = new Font("Segoe UI",Font.PLAIN,13);
    
    private final Color cBarra = new Color(230,230,230); 
    private final Color cBorde = new Color(80,80,80); 
    private final Color cFondo = new Color(215,215,215); 
    private final Color cBoton = new Color(0,112,192); 
    private final Color cText2 = new Color(255,255,255); 
    
    private final int MAX_LOGITUD_NOMBRE = 64;
    private final int MAX_LOGITUD_APELLIDO = 64;
    private final String charEspeciales = "!#$%&'()*+,-./:;<=>?@[]^_`{|}~";

    private final Menu MenuPadre;
    
    JPanel pTitulo = new JPanel();
    JLabel lMenuTitulo = new JLabel("CREAR NUEVA CIUDAD");
    
    JPanel pDatos = new JPanel();
    JLabel NombreCiudad = new JLabel("Nombre de la ciudad:");
    JLabel Tamaño = new JLabel("Tamaño:");
    JLabel Descripcion = new JLabel("Descripcion de la ciudad:");
    
    JTextField tfNombreCiudad = new JTextField();
    String[]opciones={"32*32","64*64","128*128","256*256"};
    JComboBox CbTamaño = new JComboBox(opciones);
    JTextField tfDescripcion = new JTextField();
    
    
    

    
    JPanel pBoton = new JPanel();
    JButton bAgregar = new JButton("Crear Ciudad");
    
    GridBagLayout GBL = new GridBagLayout();
    GridBagConstraints GBC = new GridBagConstraints();
    
    public MenuCrearCiudad(Menu MenuPadre) throws SQLException{
        this.MenuPadre = MenuPadre;
        setTitle("SimCity Java - Crear Ciudad");
        
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
        
        lMenuTitulo.setFont(fTitulo);
        NombreCiudad.setFont(fSubTitulo);
        Tamaño.setFont(fSubTitulo);
        Descripcion.setFont(fSubTitulo);
       
        
        tfNombreCiudad.setFont(fTexto);
        tfNombreCiudad.setBorder(BorderFactory.createLineBorder(cBorde));
        
        CbTamaño.setFont(fTexto);
        CbTamaño.setBorder(BorderFactory.createLineBorder(cBorde));
        
        tfDescripcion.setFont(fTexto);
        tfDescripcion.setBorder(BorderFactory.createLineBorder(cBorde));
  
        
        bAgregar.setFont(fSubTitulo);
        
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
        GBC.anchor = GridBagConstraints.CENTER;
        GBC.gridx = 0; GBC.gridy = 0; pTitulo.add(lMenuTitulo,GBC); 
        
        pBoton.setLayout(GBL);
        GBC.anchor = GridBagConstraints.CENTER;
        GBC.gridx = 0; GBC.gridy = 0; pBoton.add(bAgregar,GBC); GBC.ipady = 5;
     
        pDatos.setLayout(GBL);  
        GBC.anchor = GridBagConstraints.CENTER; GBC.fill = GridBagConstraints.BOTH;
        GBC.gridx = 0; GBC.gridy = 0; GBC.weightx = 0; GBC.insets = new Insets(20,20,10,10); pDatos.add(NombreCiudad,GBC); 
        GBC.gridx = 1; GBC.gridy = 0; GBC.weightx = 1; GBC.insets = new Insets(20,10,10,20); pDatos.add(tfNombreCiudad,GBC); 
        GBC.gridx = 0; GBC.gridy = 1; GBC.weightx = 0; GBC.insets = new Insets(10,20,10,10); pDatos.add(Tamaño,GBC); 
        GBC.gridx = 1; GBC.gridy = 1; GBC.weightx = 1; GBC.insets = new Insets(10,10,10,20); pDatos.add(CbTamaño,GBC); 
        GBC.gridx = 0; GBC.gridy = 2; GBC.weightx = 0; GBC.insets = new Insets(10,20,10,10); pDatos.add(Descripcion,GBC); 
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
    

    

    
    @Override
    public void actionPerformed(ActionEvent e) {
        String sNombreCiudad = tfNombreCiudad.getText().trim();
        String sTamaño = CbTamaño.getSelectedItem().toString().trim();
        String[] partes = sTamaño.split("\\*");
        int x=Integer.parseInt(partes[0]);
        int y=Integer.parseInt(partes[1]);
        
        
        String sDescripcion = tfDescripcion.getText().trim();
       
        
        // VERIFICAR DATOS NO INGRESADOS 
        String sCamposFaltantes = "";
        sCamposFaltantes += getCamposFaltantes(sNombreCiudad,     "* Nombre de la ciudad\n");
        sCamposFaltantes += getCamposFaltantes(sTamaño,   "* Tamaño\n");
        sCamposFaltantes += getCamposFaltantes(sDescripcion,        "* Descripcion\n");
 

        if(!sCamposFaltantes.equals("")){
            JOptionPane.showMessageDialog(this,"CAMPOS FALTANTES:\n"+sCamposFaltantes,"ERROR",JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // VERIFICAR NOMBRE
        if(sNombreCiudad.length() > MAX_LOGITUD_NOMBRE){
            JOptionPane.showMessageDialog(this,"Nombre no más de "+MAX_LOGITUD_NOMBRE+" caracteres.","ERROR",JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if(!espacioCorrecto(sNombreCiudad)){
            JOptionPane.showMessageDialog(this,"Separe correctamente los nombres con 1 solo espacio.","ERROR",JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // VERIFICAR Descripcion
        if(sDescripcion.length() > MAX_LOGITUD_APELLIDO){
            JOptionPane.showMessageDialog(this,"Apellido no más de "+MAX_LOGITUD_NOMBRE+" caracteres.","ERROR",JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        

  

        
        // INSERTAR LOS DATOS UNA VEZ VERIFICADOS
        ConexionPostgres BDD = new ConexionPostgres();
        String Query = "INSERT INTO ciudades (nombreciudad,x,y,Descripcion,idalcalde) VALUES (?,?,?,?,?)";
        
        Object values[] = {sNombreCiudad,x,y,sDescripcion,Menu.idalcalde};
        try {
            BDD.comandoDML(Query,values);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // MENSAJE DE EXITO
        JOptionPane.showMessageDialog(this,"¡Ciudad Creada Correctamente!","ÉXITO",JOptionPane.INFORMATION_MESSAGE);
        try {
            MenuPadre.actualizarAPanelCiudades();
            MenuPadre.setEnableButtons(true);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
            return;
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