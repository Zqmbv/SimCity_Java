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

public class Menu extends JPanel implements ActionListener{
    
    Font fTitulo = new Font("Segoe UI",Font.BOLD,18);
    Font fSubTitulo = new Font("Segoe UI",Font.BOLD,13);
    Font fTexto = new Font("Segoe UI",Font.PLAIN,13);
    
    Color cBarra = new Color(230,230,230); 
    Color cLabel = new Color(240,240,240); 
    Color cFondo = new Color(215,215,215); 
    Color cBoton1 = new Color(0,112,192); 
    Color cBoton2 = new Color(0,176,80); 
    Color cText1 = new Color(0,0,0); 
    Color cText2 = new Color(255,255,255); 

    JPanel pTitulo = new JPanel();
    JLabel lMenuTitulo = new JLabel("MENÚ PARTIDAS GUARDADAS");
    
    JPanel pAlcaldes = new JPanel();
    JScrollPane spAlcaldes = new JScrollPane(pAlcaldes);
    ArrayList<JAlcalde> JAlcaldes;
    JLabel lNoData = new JLabel("No hay alcaldes registrados.");
    
    JPanel pBoton = new JPanel();
    JButton bAgregar = new JButton("Agregar Alcalde");
    
    GridBagLayout GBL = new GridBagLayout();
    GridBagConstraints GBC = new GridBagConstraints();
    
    ArrayList<ArrayList<Object>> TUPLAS;
    
    public Menu() throws SQLException{
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
        
        spAlcaldes.setBorder(BorderFactory.createEmptyBorder());
        
        bAgregar.setBackground(cBoton1); 
        bAgregar.setForeground(cText2); 
        bAgregar.setFont(fSubTitulo);
        
        lMenuTitulo.setFont(fTitulo);
    }
    
    public void actualizarPanelAlcaldes() throws SQLException{
   
        // CONSULTAR LOS ALCALDES EN LA BDD
        ConexionPostgres BDD = new ConexionPostgres();
        ResultSet RS = BDD.consultar("SELECT id,nombre,apellido,dni FROM alcaldes",null);
        TUPLAS = new ArrayList<>();
        
        // OBTENER RESULTADO Y ALMACENARLO EN UNA ARRAYLIST TIPO OBJECT
        while(RS != null && RS.next()){
            ArrayList<Object> T = new ArrayList<>();
            T.add(Integer.valueOf(RS.getString("id")));
            T.add(RS.getString("nombre"));
            T.add(RS.getString("apellido"));
            T.add(RS.getString("dni"));
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
            JAlcaldes.add(new JAlcalde("  "+(String) T.get(1)+" "+(String) T.get(2)+" - "+(String) T.get(3)));
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
            
            actualJAlcalde.bAcceder.setBackground(cBoton2); 
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
    }
    
    public void setPosition() throws SQLException{
        setLayout(new BorderLayout());
        
        add(pTitulo,BorderLayout.NORTH);
        add(spAlcaldes,BorderLayout.CENTER);
        add(pBoton,BorderLayout.SOUTH);
        
        pTitulo.setLayout(GBL);
        GBC.anchor = GridBagConstraints.CENTER;
        GBC.gridx = 0; GBC.gridy = 0; pTitulo.add(lMenuTitulo,GBC); 
        
        pBoton.setLayout(GBL);
        GBC.anchor = GridBagConstraints.CENTER;
        GBC.gridx = 0; GBC.gridy = 0; pBoton.add(bAgregar,GBC); 
     
        pAlcaldes.setLayout(GBL);  
        actualizarPanelAlcaldes();
    }
    
    private void botonAccesoPresionado(ActionEvent ae){
        for(int i=0; i<JAlcaldes.size(); i++){
            JAlcalde actualJAlcalde = JAlcaldes.get(i);
            if(ae.getSource() == actualJAlcalde.bAcceder){
                setEnableButtons(false);
                new MenuIniciarSesion(this,Integer.parseInt(TUPLAS.get(i).get(3).toString()));
            }
        }
    }
    
    public void setEnableButtons(Boolean bool){
        bAgregar.setEnabled(bool);
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
                new MenuRegistrarAlcalde(this);   
            } catch (SQLException ex) {}
        }
    }
}
