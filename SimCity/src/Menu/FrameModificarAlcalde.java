
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

public final class FrameModificarAlcalde extends JFrame implements ActionListener,WindowListener{
    
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
    private final int MAX_LOGITUD_APELLIDO = 64;
    private final int MAX_NUMERO_DNI = 99999999;
    private final int MAX_LOGITUD_CLAVE = 64;
    private final int MIN_LOGITUD_CLAVE = 8;
    private final String charEspeciales = "!#$%&'()*+,-./:;<=>?@[]^_`{|}~";
    private final String REQUISITOS_CLAVE = "La clave debe contener:\n"
            + "* Mínimo "+MIN_LOGITUD_CLAVE+" carácteres.\n"
            + "* Máximo "+MAX_LOGITUD_CLAVE+" carácteres.\n"
            + "* Al menos 1 Caracter Minúscula y Mayúscula.\n"
            + "* Al menos 1 Caracter numérico.\n"
            + "* Al menos 1 Caracter especial.\n"
            + "* Sin espacios entre carácteres.";

    private MenuCiudades MenuPadre;
    private int idAlcalde;
    
    JPanel pTitulo = new JPanel();
    JLabel lMenuTitulo = new JLabel("MODIFICAR ALCALDE");
    
    JPanel pDatos = new JPanel();
    JLabel lNombre = new JLabel("Nombre:");
    JLabel lApellido = new JLabel("Apellido:");
    JLabel lDNI = new JLabel("DNI:");
    JLabel lGenero = new JLabel("Género:");
    JLabel lClave = new JLabel("Clave:");
    JTextField tfNombre = new JTextField();
    JTextField tfApellido = new JTextField();
    JTextField tfDNI = new JTextField();
    JPasswordField pfClave = new JPasswordField();
    JToggleButton bMostrarClave = new JToggleButton("Mostrar");
    char echoCharPorDefecto;
    
    JPanel pGenero = new JPanel();
    ButtonGroup BG = new ButtonGroup();
    JRadioButton rbMasculino = new JRadioButton("Masculino");
    JRadioButton rbFemenino = new JRadioButton("Femenino");
    JRadioButton rbOtro = new JRadioButton("Otro");
    
    JPanel pBoton = new JPanel();
    JButton bActualizar = new JButton("Actualizar Alcalde");
    
    GridBagLayout GBL = new GridBagLayout();
    GridBagConstraints GBC = new GridBagConstraints();
    
    public FrameModificarAlcalde(MenuCiudades MenuPadre,int idAlcalde) throws SQLException{
        this.MenuPadre = MenuPadre;
        this.idAlcalde = idAlcalde;
        setTitle("SimCity JAVA - Modificar Alcalde");
        
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
        pGenero.setOpaque(false);
        pDatos.setBackground(cFondo);
        
        bActualizar.setFont(fSubTitulo);
        bActualizar.setBackground(cBoton);
        bActualizar.setForeground(cText2);
        
        bMostrarClave.setBackground(cBoton);
        bMostrarClave.setForeground(cText2);
        bMostrarClave.setFont(fSubTitulo);
        
        lMenuTitulo.setFont(fTitulo);
        lNombre.setFont(fSubTitulo);
        lApellido.setFont(fSubTitulo);
        lDNI.setFont(fSubTitulo);
        lGenero.setFont(fSubTitulo);
        lClave.setFont(fSubTitulo);
        
        tfNombre.setFont(fTexto);
        tfNombre.setBorder(BorderFactory.createLineBorder(cBorde));
        
        tfApellido.setFont(fTexto);
        tfApellido.setBorder(BorderFactory.createLineBorder(cBorde));
        
        tfDNI.setFont(fTexto);
        tfDNI.setBorder(BorderFactory.createLineBorder(cBorde));
       
        pfClave.setFont(fTexto);
        pfClave.setBorder(BorderFactory.createLineBorder(cBorde));
        
        rbMasculino.setFont(fSubTitulo);
        rbMasculino.setOpaque(false);
        
        rbFemenino.setFont(fSubTitulo);
        rbFemenino.setOpaque(false);
        
        rbOtro.setFont(fSubTitulo);
        rbOtro.setOpaque(false);
        
        // PARA PONERLE COLOR A LOS JOPTIONPANE
        UIManager.put("OptionPane.background", cFondo);
        UIManager.put("OptionPane.messageForeground",cText1);
        UIManager.put("OptionPane.messageFont",fTexto);
        UIManager.put("Button.background",cBoton);
        UIManager.put("Button.foreground",cText2);
        UIManager.put("Button.focus", new Color(0, 0, 0, 0));               
        UIManager.put("Panel.background", cFondo);
    }
    
    public void setConfigComponente() throws SQLException{
        bActualizar.addActionListener(this);
        echoCharPorDefecto = pfClave.getEchoChar();
        bMostrarClave.addActionListener(this);
        
        ConexionPostgres BD = new ConexionPostgres();
        ResultSet RS = BD.consultar("SELECT nombre,apellido,dni,genero,clave FROM alcaldes WHERE id = ?",new Object[]{this.idAlcalde});
        
        Object T[] = null;
        while(RS != null && RS.next()){
            T = new Object[]{
                RS.getString("nombre"),
                RS.getString("apellido"),
                RS.getString("dni"),
                RS.getString("genero"),
                RS.getString("clave")
            };
        }
        
        if(T == null){
            JOptionPane.showMessageDialog(this,"La ciudad no existe.","ERROR",JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        tfNombre.setText((String) T[0]);
        tfApellido.setText((String) T[1]);
        tfDNI.setText((String) T[2]);
        if(((String) T[3]).equals(rbMasculino.getText())){
            rbMasculino.setSelected(true);
            rbFemenino.setSelected(false);
            rbOtro.setSelected(false);
        }
        if(((String) T[3]).equals(rbFemenino.getText())){
            rbMasculino.setSelected(false);
            rbFemenino.setSelected(true);
            rbOtro.setSelected(false);
        }
        if(((String) T[3]).equals(rbOtro.getText())){
            rbMasculino.setSelected(false);
            rbFemenino.setSelected(false);
            rbOtro.setSelected(true);
        }
        pfClave.setText((String) T[4]);
        
    }
    
    public void setPosition() throws SQLException{
        setLayout(new BorderLayout());
        
        add(pTitulo,BorderLayout.NORTH);
        add(pDatos,BorderLayout.CENTER);
        add(pBoton,BorderLayout.SOUTH);
        
        pTitulo.setLayout(GBL);
        GBC.anchor = GridBagConstraints.CENTER;
        GBC.insets = new Insets(10, 10, 10, 10);
        GBC.gridx = 0; GBC.gridy = 0; pTitulo.add(lMenuTitulo,GBC); 
        
        pBoton.setLayout(GBL);
        GBC.anchor = GridBagConstraints.CENTER;
        GBC.gridx = 0; GBC.gridy = 0; pBoton.add(bActualizar,GBC); GBC.ipady = 5;
     
        pDatos.setLayout(GBL);  
        GBC.anchor = GridBagConstraints.CENTER; GBC.fill = GridBagConstraints.BOTH;
        GBC.gridx = 0; GBC.gridy = 0; GBC.weightx = 0; GBC.gridwidth=1; GBC.insets = new Insets(20,20,10,10); pDatos.add(lNombre,GBC); 
        GBC.gridx = 1; GBC.gridy = 0; GBC.weightx = 1; GBC.gridwidth=2; GBC.insets = new Insets(20,10,10,20); pDatos.add(tfNombre,GBC); 
        GBC.gridx = 0; GBC.gridy = 1; GBC.weightx = 0; GBC.gridwidth=1; GBC.insets = new Insets(10,20,10,10); pDatos.add(lApellido,GBC); 
        GBC.gridx = 1; GBC.gridy = 1; GBC.weightx = 1; GBC.gridwidth=2; GBC.insets = new Insets(10,10,10,20); pDatos.add(tfApellido,GBC); 
        GBC.gridx = 0; GBC.gridy = 2; GBC.weightx = 0; GBC.gridwidth=1; GBC.insets = new Insets(10,20,10,10); pDatos.add(lDNI,GBC); 
        GBC.gridx = 1; GBC.gridy = 2; GBC.weightx = 1; GBC.gridwidth=2; GBC.insets = new Insets(10,10,10,20); pDatos.add(tfDNI,GBC); 
        GBC.gridx = 0; GBC.gridy = 3; GBC.weightx = 0; GBC.gridwidth=1; GBC.insets = new Insets(10,20,10,10); pDatos.add(lGenero,GBC); 
        GBC.gridx = 1; GBC.gridy = 3; GBC.weightx = 1; GBC.gridwidth=2; GBC.insets = new Insets(10,10,10,20); pDatos.add(pGenero,GBC); 
        GBC.gridwidth=1;
        
        pGenero.setLayout(GBL);
        GBC.ipady = 0;
        GBC.insets = new Insets(0,0,0,0);
        GBC.gridx = 0; GBC.gridy = 0; pGenero.add(rbMasculino,GBC);  BG.add(rbMasculino);
        GBC.gridx = 1; GBC.gridy = 0; pGenero.add(rbFemenino,GBC);   BG.add(rbFemenino);
        GBC.gridx = 2; GBC.gridy = 0; pGenero.add(rbOtro,GBC);       BG.add(rbOtro);
        
        GBC.ipady = 5;
        GBC.gridx = 0; GBC.gridy = 4; GBC.weightx = 0; GBC.insets = new Insets(10,20,20,10); pDatos.add(lClave,GBC);
        GBC.anchor = GridBagConstraints.CENTER; GBC.fill = GridBagConstraints.BOTH;
        GBC.gridx = 1; GBC.gridy = 4; GBC.weightx = 1; GBC.insets = new Insets(10,10,20,0);pDatos.add(pfClave,GBC);
        GBC.anchor = GridBagConstraints.EAST; GBC.fill = GridBagConstraints.NONE;
        GBC.gridx = 2; GBC.gridy = 4; GBC.weightx = 0; GBC.insets = new Insets(10,0,20,20);pDatos.add(bMostrarClave,GBC);
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
    
    private Boolean existeDNI(int DNI) throws SQLException{
        ConexionPostgres BDD = new ConexionPostgres();
        ResultSet RS = BDD.consultar("SELECT dni FROM alcaldes WHERE dni = ? AND id != ?", new Object[]{DNI,this.idAlcalde});

        ArrayList<Object> TUPLA = new ArrayList<>();
        while(RS != null && RS.next()){
             TUPLA.add(RS.getString("dni"));
        }
        
        return !TUPLA.isEmpty(); // VACIO -> NO ENCONTRÓ DNI
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
        if(ae.getSource()==bMostrarClave){
            if(bMostrarClave.isSelected()){
                pfClave.setEchoChar((char)0);
                bMostrarClave.setText("Ocultar");
            }
            else{
                pfClave.setEchoChar(echoCharPorDefecto);
                bMostrarClave.setText("Mostrar");
            }
        }
        if(ae.getSource() == bActualizar){
            String sNombre = tfNombre.getText().trim();
            String sApellido = tfApellido.getText().trim();
            String sDNI = tfDNI.getText().trim();
            String sGenero = (rbMasculino.isSelected()) ? "Masculino" : (rbFemenino.isSelected()) ? "Femenino" : (rbOtro.isSelected()) ? "Otro" : "";   
            String sClave = new String(pfClave.getPassword()).trim();

            // VERIFICAR DATOS NO INGRESADOS 
            String sCamposFaltantes = "";
            sCamposFaltantes += getCamposFaltantes(sNombre,     "* Nombre\n");
            sCamposFaltantes += getCamposFaltantes(sApellido,   "* Apellido\n");
            sCamposFaltantes += getCamposFaltantes(sDNI,        "* DNI\n");
            sCamposFaltantes += getCamposFaltantes(sGenero,     "* Género\n");
            sCamposFaltantes += getCamposFaltantes(sClave,      "* Clave\n");

            if(!sCamposFaltantes.equals("")){
                JOptionPane.showMessageDialog(this,"CAMPOS FALTANTES:\n"+sCamposFaltantes,"ERROR",JOptionPane.ERROR_MESSAGE);
                return;
            }

            // VERIFICAR NOMBRE
            if(sNombre.length() > MAX_LOGITUD_NOMBRE){
                JOptionPane.showMessageDialog(this,"Nombre no más de "+MAX_LOGITUD_NOMBRE+" caracteres.","ERROR",JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(!espacioCorrecto(sNombre)){
                JOptionPane.showMessageDialog(this,"Separe correctamente los nombres con 1 solo espacio.","ERROR",JOptionPane.ERROR_MESSAGE);
                return;
            }

            // VERIFICAR APELLIDO
            if(sApellido.length() > MAX_LOGITUD_APELLIDO){
                JOptionPane.showMessageDialog(this,"Apellido no más de "+MAX_LOGITUD_NOMBRE+" caracteres.","ERROR",JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(!espacioCorrecto(sApellido)){
                JOptionPane.showMessageDialog(this,"Separe correctamente los apellidos con 1 solo espacio.","ERROR",JOptionPane.ERROR_MESSAGE);
                return;
            }

            // VERIFICAR DNI
            int nDNI=0;
            try{
                nDNI = Integer.parseInt(sDNI);
                if(nDNI <= 0 || nDNI > MAX_NUMERO_DNI){
                    JOptionPane.showMessageDialog(this,"DNI negativo, nulo o superior a "+MAX_NUMERO_DNI+".","ERROR",JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if(existeDNI(nDNI)){
                    JOptionPane.showMessageDialog(this,"El DNI ya se encuentra registrado.","ERROR",JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch(HeadlessException | NumberFormatException EX){
                JOptionPane.showMessageDialog(this,"DNI invalido.\nEjemplo DNI: 12345678","ERROR",JOptionPane.ERROR_MESSAGE);
                return;
            } catch (SQLException ex) {
                Logger.getLogger(FrameModificarAlcalde.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }

            // VERIFICAR CLAVE   
            if(!claveVerificado(sClave)){
                JOptionPane.showMessageDialog(this,REQUISITOS_CLAVE,"REQUISITOS",JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // ACTUALIZAR LOS DATOS UNA VEZ VERIFICADOS
            ConexionPostgres BDD = new ConexionPostgres();
            String Query = "UPDATE alcaldes SET nombre = ?, apellido = ?, dni = ?, genero = ?, clave = ? WHERE id = ? ;";
            Object values[] = {sNombre,sApellido,nDNI,sGenero,sClave,this.idAlcalde};
            try {
                BDD.comandoDML(Query,values);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this,ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                return;
            }

            // MENSAJE DE EXITO
            JOptionPane.showMessageDialog(this,"¡Alcalde Actualizado Correctamente!\nLos cambios se mostrarán en el Menú de Alcaldes.","ÉXITO",JOptionPane.INFORMATION_MESSAGE);
            MenuPadre.setEnableButtons(true);
            this.dispose();
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