package Menu;

import BDD.ConexionPostgres;
import java.sql.ResultSet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;
        
class JAlcalde{
    
    JButton bAcceder;
    JLabel lAlcalde;

    public JAlcalde(String info){
        this.bAcceder = new JButton("Acceder");
        this.lAlcalde = new JLabel(info);
    }
}

public class MenuAlcaldes extends JPanel implements ActionListener{
    
    private final Font fTitulo = new Font("Segoe UI",Font.BOLD,18);
    private final Font fSubTitulo = new Font("Segoe UI",Font.BOLD,13);
    private final Font fTexto = new Font("Segoe UI",Font.PLAIN,13);
    
    private final Color cBarra = new Color(230,230,230); 
    private final Color cLabel = new Color(240,240,240); 
    private final Color cFondo = new Color(215,215,215); 
    private final Color cBotonEstandar = new Color(0,112,192); 
    private final Color cBotonAcceder = new Color(0,176,80); 
    private final Color cText1 = new Color(0,0,0); 
    private final Color cText2 = new Color(255,255,255); 

    JPanel pTitulo = new JPanel();
    JLabel lMenuTitulo = new JLabel("MENÚ ALCALDES GUARDADOS");
    
    JPanel pAlcaldes = new JPanel();
    JScrollPane spAlcaldes = new JScrollPane(pAlcaldes);
    ArrayList<JAlcalde> JAlcaldes;
    JLabel lNoData = new JLabel("No hay alcaldes registrados.");
    
    JPanel pBoton = new JPanel();
    JButton bAgregar = new JButton("Agregar Alcalde");
    JButton bVolver = new JButton("<- Volver");
    
    GridBagLayout GBL = new GridBagLayout();
    GridBagConstraints GBC = new GridBagConstraints();
    
    ArrayList<ArrayList<Object>> TUPLAS;
    
    public MenuAlcaldes() throws SQLException{
        setPosition();
        setTema();
        setConfigComponente();
    }
    
    public void setTema(){
        pTitulo.setBackground(cBarra);
        pAlcaldes.setBackground(cFondo);
        spAlcaldes.setBackground(cFondo);
        spAlcaldes.setOpaque(false);
        pBoton.setBackground(cBarra);
        
        lNoData.setFont(fTexto);
        
        spAlcaldes.setBorder(BorderFactory.createEmptyBorder());
        
        bAgregar.setBackground(cBotonEstandar); 
        bAgregar.setForeground(cText2); 
        bAgregar.setFont(fSubTitulo);
        
        bVolver.setBackground(cBotonEstandar); 
        bVolver.setForeground(cText2); 
        bVolver.setFont(fSubTitulo);
        
        lMenuTitulo.setFont(fTitulo);
    }
    
    public void actualizarPanelAlcaldes() throws SQLException{
   
        // CONSULTAR LOS ALCALDES EN LA BDD
        ConexionPostgres BDD = new ConexionPostgres();
         ResultSet RS = BDD.consultar(
                "SELECT alcaldes.id,alcaldes.nombre,alcaldes.apellido,alcaldes.dni,COUNT(ciudades.id) AS numCiudades " +
                "FROM alcaldes LEFT JOIN ciudades ON alcaldes.id = ciudades.idAlcalde " +
                "GROUP BY alcaldes.id ORDER BY alcaldes.id ASC",null);
        TUPLAS = new ArrayList<>();
        
        // OBTENER RESULTADO Y ALMACENARLO EN UNA ARRAYLIST TIPO OBJECT
        while(RS != null && RS.next()){
            ArrayList<Object> T = new ArrayList<>();
            T.add(Integer.valueOf(RS.getString("id")));
            T.add(RS.getString("nombre"));
            T.add(RS.getString("apellido"));
            T.add(RS.getString("dni"));
            T.add(RS.getString("numCiudades"));
            TUPLAS.add(T);
        }
        
        // LIMPIAMOS TODO EL PANEL
        JAlcaldes = new ArrayList<>();
        pAlcaldes.removeAll();
        
        // SI NO HAY ALCALDES REGISTRADOS TODAVIA
        if(TUPLAS.isEmpty()){
            GBC.gridx = 0; GBC.gridy = 0; GBC.weightx = 0; GBC.gridwidth = 1; GBC.insets = new Insets(10,10,10,10); pAlcaldes.add(lNoData,GBC);
            return;
        }
        
        // SI EXISTEN, MOSTRAMOS NOMBRE + APELLIDO + DNI DE CADA ALCALDE
        for(ArrayList<Object> T: TUPLAS){
            String sNombre = (String) T.get(1);
            String sApellido = (String) T.get(2);
            String sDNI = (String) T.get(3);
            String sNumCiudades = (Integer.parseInt((String)T.get(4)) == 1) ? "("+(String)T.get(4)+" Ciudad)" : "("+(String)T.get(4)+" Ciudades)"; 
 
            JAlcaldes.add(new JAlcalde("<html>"
            + "<b>"+sNombre+" "+sApellido+" - "+sNumCiudades+"</b>"
            + "<br>"+sDNI
            + "</html>"));
        }
        
        GBC.anchor = GridBagConstraints.NORTH; GBC.fill = GridBagConstraints.HORIZONTAL;
        GBC.ipadx = 0; GBC.gridwidth = 1; GBC.weighty = 0;
        
        // AGREGAR CADA ALCALDE AL PANEL
        for(int i=0; i<JAlcaldes.size(); i++){
            JAlcalde actualJAlcalde = JAlcaldes.get(i);
            
            int top = (i==0) ? 20 : 10;

            GBC.gridx = 0; GBC.gridy = i; GBC.weightx = 0; GBC.insets = new Insets(top,70,10,5); pAlcaldes.add(actualJAlcalde.bAcceder,GBC);
            GBC.gridx = 1; GBC.gridy = i; GBC.weightx = 1; GBC.insets = new Insets(top,5,10,70); pAlcaldes.add(actualJAlcalde.lAlcalde,GBC);
            
            actualJAlcalde.bAcceder.setPreferredSize(new Dimension(100,50));
            actualJAlcalde.lAlcalde.setPreferredSize(new Dimension(100,50));
            
            actualJAlcalde.lAlcalde.setHorizontalTextPosition(JLabel.LEFT);
            actualJAlcalde.lAlcalde.setVerticalTextPosition(JLabel.CENTER);
            
            actualJAlcalde.bAcceder.addActionListener(this);
            
            actualJAlcalde.lAlcalde.setBackground(cLabel); 
            actualJAlcalde.lAlcalde.setOpaque(true); 
            actualJAlcalde.lAlcalde.setFont(fTexto); 
            
            actualJAlcalde.bAcceder.setBackground(cBotonAcceder); 
            actualJAlcalde.bAcceder.setForeground(cText2); 
            actualJAlcalde.bAcceder.setFont(fSubTitulo); 
        }
        
        // ESTO HACE QUE LOS COMPONENTES SE QUEDEN PEGADOS EN EL NORTE Y SE EXTENDERÁ HASTA ABAJO.
        GBC.anchor = GridBagConstraints.NORTH; GBC.fill = GridBagConstraints.HORIZONTAL;
        GBC.weightx = 1; GBC.weighty = 1;
        GBC.gridx = 0; GBC.gridy = 9999; GBC.gridwidth = 2; pAlcaldes.add(new JLabel(""),GBC);
        
        pAlcaldes.revalidate();
        pAlcaldes.repaint();
    }
    
    public void setConfigComponente(){
        bAgregar.addActionListener(this);
        bVolver.addActionListener(this);
        spAlcaldes.getVerticalScrollBar().setUnitIncrement(12); 
    }
    
    public void setPosition() throws SQLException{
        setLayout(new BorderLayout());
        
        add(pTitulo,BorderLayout.NORTH);
        add(spAlcaldes,BorderLayout.CENTER);
        add(pBoton,BorderLayout.SOUTH);
        
        pTitulo.setLayout(GBL);

        GBC.anchor = GridBagConstraints.WEST;
        GBC.gridx = 0; GBC.gridy = 0;
        GBC.insets = new Insets(10, 20, 10, 10);
        pTitulo.add(bVolver, GBC);
        

        GBC.anchor = GridBagConstraints.CENTER;
        GBC.gridx = 1; GBC.gridy = 0;
        GBC.weightx = 1.0; 
        GBC.insets = new Insets(10, 10, 10, 100); 
        pTitulo.add(lMenuTitulo,GBC); 
        
        pBoton.setLayout(GBL);
        GBC.insets = new Insets(10, 10, 10, 10);
        GBC.anchor = GridBagConstraints.CENTER;
        GBC.weightx = 0; 
        GBC.gridx = 0; GBC.gridy = 0; pBoton.add(bAgregar,GBC);
     
        pAlcaldes.setLayout(GBL);  
        actualizarPanelAlcaldes();
    }
    
    private void botonAccesoPresionado(ActionEvent ae){
        for(int i=0; i<JAlcaldes.size(); i++){
            JAlcalde actualJAlcalde = JAlcaldes.get(i);
            if(ae.getSource() == actualJAlcalde.bAcceder){
                setEnableButtons(false);
                new FrameIniciarSesion(this,Integer.parseInt(TUPLAS.get(i).get(3).toString()));
            }
        }
    }
    
    public void setEnableButtons(Boolean bool){
        bAgregar.setEnabled(bool);
        bVolver.setEnabled(bool);
        for(int i=0; i<JAlcaldes.size(); i++){
            JAlcalde actualJAlcalde = JAlcaldes.get(i);
            actualJAlcalde.bAcceder.setEnabled(bool);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        botonAccesoPresionado(ae);
        if(ae.getSource()==bAgregar){
            try {
                setEnableButtons(false);
                new FrameRegistrarAlcalde(this);   
            } catch (SQLException ex) {}
        }
        if(ae.getSource() == bVolver){
            JFrame ventanaPadre = (JFrame) SwingUtilities.getWindowAncestor(this);
            
            if (ventanaPadre != null) {
                ventanaPadre.remove(this); 
                ventanaPadre.add(new MenuPrincipal());
                
                ventanaPadre.revalidate();
                ventanaPadre.repaint();
            }
        }
    }
}
