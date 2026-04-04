package Menu;

import BDD.ConexionPostgres;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;

public class FrameIniciarSesion extends JFrame implements ActionListener,WindowListener{

    private final static Font fTitulo = new Font("Segoe UI",Font.BOLD,18);
    private final static Font fSubTitulo = new Font("Segoe UI",Font.BOLD,13);
    private final static Font fTexto = new Font("Segoe UI",Font.PLAIN,13);
    
    private final static Color cBarra = new Color(230,230,230); 
    private final static Color cBorde = new Color(80,80,80); 
    private final static Color cFondo = new Color(215,215,215); 
    private final static Color cBoton = new Color(0,112,192); 
    private final static Color cText1 = new Color(0,0,0); 
    private final static Color cText2 = new Color(255,255,255); 
    
    private static final int MAX_LOGITUD_CLAVE = 64;
    private static final int MIN_LOGITUD_CLAVE = 8;
    private static final String charEspeciales = "!#$%&'()*+,-./:;<=>?@[]^_`{|}~";
    private final String REQUISITOS_CLAVE = "La clave debe contener:\n"
            + "* Mínimo "+MIN_LOGITUD_CLAVE+" carácteres.\n"
            + "* Máximo "+MAX_LOGITUD_CLAVE+" carácteres.\n"
            + "* Al menos 1 Caracter Minúscula y Mayúscula.\n"
            + "* Al menos 1 Caracter numérico.\n"
            + "* Al menos 1 Caracter especial.\n"
            + "* Sin espacios entre carácteres.";
    
    private MenuAlcaldes MenuPadre;
    
    int dniAlcalde;

    JPanel pTitulo = new JPanel();
    JLabel lMenuTitulo = new JLabel("INICIAR SESIÓN");
    
    JPanel pDatos = new JPanel();
    JLabel lDNI = new JLabel("DNI");
    JLabel lClave = new JLabel("Clave");
    JTextField tfDNI = new JTextField();
    JPasswordField pfClave = new JPasswordField();
    
    JPanel pBoton = new JPanel();
    JButton bAcceder = new JButton("Iniciar Sesión");
    
    GridBagLayout GBL = new GridBagLayout();
    GridBagConstraints GBC = new GridBagConstraints();
  
    public FrameIniciarSesion(MenuAlcaldes MenuPadre, int dniAlcalde){
        this.MenuPadre = MenuPadre;
        this.dniAlcalde = dniAlcalde;
        
        setTitle("SimCity Java - Iniciar Sesión");
        
        setPosition();
        setConfigComponente();
        setTema();
        
        this.addWindowListener(this);
        
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public void setTema(){
        lMenuTitulo.setFont(fTitulo);
        
        pTitulo.setBackground(cBarra);
        pBoton.setBackground(cBarra);
        pDatos.setBackground(cFondo);
        
        lDNI.setFont(fSubTitulo);
        lClave.setFont(fSubTitulo);
        tfDNI.setFont(fTexto);
        pfClave.setFont(fTexto);
        
        tfDNI.setBorder(BorderFactory.createLineBorder(cBorde));
        pfClave.setBorder(BorderFactory.createLineBorder(cBorde));
        
        bAcceder.setBackground(cBoton);
        bAcceder.setForeground(cText2);
        bAcceder.setFont(fSubTitulo);
        
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
        tfDNI.setText(String.valueOf(this.dniAlcalde));
        tfDNI.setEnabled(false);
        bAcceder.addActionListener(this);
    }
    
    public void setPosition(){
        setLayout(new BorderLayout());
        
        add(pTitulo,BorderLayout.NORTH);
        add(pDatos,BorderLayout.CENTER);
        add(pBoton,BorderLayout.SOUTH);
        
        pTitulo.setLayout(GBL);
        GBC.insets = new Insets(10, 10, 10, 10);
        GBC.anchor = GridBagConstraints.CENTER;
        GBC.gridx = 0; GBC.gridy = 0; pTitulo.add(lMenuTitulo,GBC); 
        
        pBoton.setLayout(GBL);
        GBC.anchor = GridBagConstraints.CENTER;
        GBC.gridx = 0; GBC.gridy = 0; pBoton.add(bAcceder,GBC); 
     
        pDatos.setLayout(GBL);  
        GBC.anchor = GridBagConstraints.CENTER; GBC.fill = GridBagConstraints.BOTH;
        GBC.gridx = 0; GBC.gridy = 0; GBC.weightx = 0; GBC.insets = new Insets(10,20,10,10); pDatos.add(lDNI,GBC); 
        GBC.gridx = 1; GBC.gridy = 0; GBC.weightx = 1; GBC.insets = new Insets(10,10,10,20); pDatos.add(tfDNI,GBC); 
        GBC.gridx = 0; GBC.gridy = 1; GBC.weightx = 0; GBC.insets = new Insets(10,20,20,10); pDatos.add(lClave,GBC);
        GBC.gridx = 1; GBC.gridy = 1; GBC.weightx = 1; GBC.insets = new Insets(10,10,20,20);pDatos.add(pfClave,GBC);
    }
    
    public String getCamposFaltantes(String TextoObtenido, String Mensaje){
        if(TextoObtenido.equals("")) return Mensaje;
        else return "";
    }
    
    private Boolean claveVerificado(String STR){
        int longitudClave = STR.length();
        if(longitudClave < MIN_LOGITUD_CLAVE || longitudClave > MAX_LOGITUD_CLAVE){
            return false;
        }
        
        Boolean contieneMayus =     false;
        Boolean contieneMinus =     false; 
        Boolean contieneNumero =    false; 
        Boolean contieneEspecial =  false; 
        Boolean sinEspacio =        true;
       
        for(int c=0; c<longitudClave; c++){
            if(Character.isUpperCase(STR.charAt(c)))        contieneMayus=true;
            if(Character.isLowerCase(STR.charAt(c)))        contieneMinus=true;
            if(Character.isDigit(STR.charAt(c)))            contieneNumero=true;
            if(charEspeciales.indexOf(STR.charAt(c)) != -1) contieneEspecial=true;
            if(STR.charAt(c) == ' ')                        sinEspacio=false;
        }
  
        return (contieneMayus && contieneMinus && contieneNumero && contieneEspecial && sinEspacio && true);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {

        String sClave = pfClave.getText().trim();
        
        // VERIFICAR DATOS NO INGRESADOS 
        String sCamposFaltantes = "";
        sCamposFaltantes += getCamposFaltantes(sClave," Clave");

        if(!sCamposFaltantes.equals("")){
            JOptionPane.showMessageDialog(this,"CAMPOS FALTANTES:"+sCamposFaltantes,"ERROR",JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // VERIFICAR CLAVE   
        if(!claveVerificado(sClave)){
            JOptionPane.showMessageDialog(this,REQUISITOS_CLAVE);
            return;
        }
        
        // INSERTAR LOS DATOS UNA VEZ VERIFICADOS
        String Query = "SELECT id,nombre,apellido,dni FROM alcaldes WHERE dni = ? and clave = ? LIMIT 1";
        Object values[] = {dniAlcalde,sClave};
        try {
            ConexionPostgres BDD = new ConexionPostgres();
            ResultSet RS = BDD.consultar(Query,values);

            ArrayList<Object> TUPLA = new ArrayList<>();
            while(RS != null && RS.next()){
                 TUPLA.add(Integer.valueOf(RS.getString("id")));
                 TUPLA.add(RS.getString("nombre"));
                 TUPLA.add(RS.getString("apellido"));
                 TUPLA.add(Integer.valueOf(RS.getString("dni")));
            }

            if(TUPLA.isEmpty()){
                JOptionPane.showMessageDialog(this,"Clave o Usuario incorrecto.","ERROR",JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // MENSAJE DE EXITO!
            JOptionPane.showMessageDialog(this,"¡Bienvenido a SimCity Java, "+TUPLA.get(1)+"!","ÉXITO",JOptionPane.INFORMATION_MESSAGE);
            
            JFrame framePrincipal = (JFrame) SwingUtilities.getWindowAncestor(MenuPadre);
            if (framePrincipal != null) {
                framePrincipal.remove(MenuPadre);
                framePrincipal.add(new MenuCiudades((int) TUPLA.get(0)));

                framePrincipal.revalidate();
                framePrincipal.repaint();
            }
            this.dispose();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,ex.getMessage());
            return;
        }

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
