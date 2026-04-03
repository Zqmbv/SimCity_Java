package Menu;

import BDD.ConexionPostgres;
import java.sql.ResultSet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

        
class JAlcalde{
    
    JButton bAcceder;
    JLabel lAlcalde;
    JButton bIniciar;
    JButton bEditar;
    JButton bEliminar;
    
    JButton bAgregar;
    JLabel lMenuTitulo ;
    String titulo;
 

    public JAlcalde(String info){
        this.bAcceder = new JButton("Acceder");
        this.bIniciar = new JButton("Iniciar");
        this.bEditar= new JButton("Editar");
        this.bEliminar= new JButton("Eliminar");
        this.lAlcalde = new JLabel(info);
 
        this.bAgregar= new JButton("Agregar");
        this.lMenuTitulo= new JLabel("MENÚ PARTIDAS GUARDADAS");
        this.titulo="MENÚ PARTIDAS GUARDADAS";
        
     
    }
}


public class Menu extends JPanel implements ActionListener{
    public static int idalcalde;
    public static int idciudad;
    
  
    
    Font fTitulo = new Font("Segoe UI",Font.BOLD,18);
    Font fSubTitulo = new Font("Segoe UI",Font.BOLD,13);
    Font fTexto = new Font("Segoe UI",Font.PLAIN,13);
    
    Color cBarra = new Color(230,230,230); 
    Color cLabel = new Color(240,240,240); 
    Color cFondo = new Color(215,215,215); 
    Color cBoton1 = new Color(0,112,192); 
    Color cBoton2 = new Color(0,176,80); 
    Color cBoton3 = new Color(255,0,0); 
    Color cText2 = new Color(255,255,255); 

    JPanel pTitulo = new JPanel();
    JLabel lMenuTitulo = new JLabel("MENU DE PARTIDAS GUARDADAS");
    JLabel lMenuTitulo2 = new JLabel("MENU DE CIUDADES GUARDADAS");
    
    JPanel pAlcaldes = new JPanel();
    JPanel pCiudades = new JPanel();
    JScrollPane spAlcaldes = new JScrollPane(pAlcaldes);
    JScrollPane spCiudades = new JScrollPane(pCiudades);
    ArrayList<JAlcalde> JAlcaldes;

    JLabel lNoData = new JLabel("No hay alcaldes registrados.");
    JLabel lNoData2 = new JLabel("No hay ciudades creadas.");
    
    JPanel pBoton = new JPanel();
    JButton bAgregar = new JButton("Agregar Alcalde");
    JButton bCrear = new JButton("Crear Ciudad");
    JButton bRegresar = new JButton("<-Inicio");
    
   
    
    GridBagLayout GBL = new GridBagLayout();
    GridBagConstraints GBC = new GridBagConstraints();
    
    ArrayList<ArrayList<Object>> TUPLAS;
    
    public Menu() throws SQLException{
        setPosition1();   
        setTema();
        setConfigComponente();
    }
    
    public void setTema(){
        pTitulo.setBackground(cBarra);
        pAlcaldes.setBackground(cFondo);
        pCiudades.setBackground(cFondo);
        spAlcaldes.setOpaque(false);
        spCiudades.setOpaque(false);
        pBoton.setBackground(cBarra);
        
        spAlcaldes.setBorder(BorderFactory.createEmptyBorder());
        spCiudades.setBorder(BorderFactory.createEmptyBorder());
        
        bAgregar.setBackground(cBoton1); 
        bAgregar.setForeground(cText2); 
        bAgregar.setFont(fSubTitulo);
        bCrear.setBackground(cBoton1); 
        bCrear.setForeground(cText2); 
        bCrear.setFont(fSubTitulo);
        
   
        
        lMenuTitulo.setFont(fTitulo);
        lMenuTitulo2.setFont(fTitulo);
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
        
        // AGREGAR CADA ALCALDE AL PANEL\
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
    public void actualizarAPanelCiudades() throws SQLException{
        // CONSULTAR LOS ALCALDES EN LA BDD
        ConexionPostgres BDD = new ConexionPostgres();
        ResultSet RS = BDD.consultar("Select id,nombreciudad,poblacion,x,y \n" +
"From ciudades\n" +
"Where idAlcalde='"+Menu.idalcalde+"'\n" +
"",null);
        TUPLAS = new ArrayList<>();
        
        // OBTENER RESULTADO Y ALMACENARLO EN UNA ARRAYLIST TIPO OBJECT
        while(RS != null && RS.next()){
            ArrayList<Object> T = new ArrayList<>();
            
            T.add(RS.getString("id"));
            T.add(RS.getString("nombreciudad"));
            T.add(RS.getString("poblacion"));
            T.add(RS.getString("x"));
            T.add(RS.getString("y"));
            TUPLAS.add(T);
        }
        
        // LIMPIAMOS TODO EL PANEL
        JAlcaldes = new ArrayList<>();
        pAlcaldes.removeAll();
        
        // SI NO HAY CIUDADES REGISTRADAS TODAVIA
        if(TUPLAS.isEmpty()){
            GBC.gridx = 0; GBC.gridy = 0; GBC.weightx = 0; GBC.gridwidth = 1; GBC.insets = new Insets(10,10,10,10); pAlcaldes.add(lNoData2,GBC);
            return;
        }
        
        // SI EXISTEN, MOSTRAMOS NOMBRE DE LA CIUDAD + POBLACION + TAMAÑO()
        for(ArrayList<Object> T: TUPLAS){
            JAlcaldes.add(new JAlcalde("  "+(String) T.get(1)+"  Poblacion:"+(String) T.get(2)+" - "+(String) T.get(3)+"X"+(String) T.get(4)));
        }
        
        GBC.anchor = GridBagConstraints.NORTH; GBC.fill = GridBagConstraints.HORIZONTAL;
        GBC.ipadx = 0; GBC.gridwidth = 1; GBC.weighty = 0;
        
        
        
        // AGREGAR CADA CIUDAD DEL ALCALDE AL PANEL
        for(int i=0; i<JAlcaldes.size(); i++){
            JAlcalde actualJAlcalde = JAlcaldes.get(i);
            
            int top = (i==0) ? 20 : 10;
            
           


            GBC.gridx = 0; GBC.gridy = i; GBC.weightx = 0; GBC.insets = new Insets(top,70,10,5); pAlcaldes.add(actualJAlcalde.bIniciar,GBC);
            GBC.gridx = 1; GBC.gridy = i; GBC.weightx = 1; GBC.insets = new Insets(top,5,10,5); pAlcaldes.add(actualJAlcalde.lAlcalde,GBC);
            GBC.gridx = 3; GBC.gridy = i; GBC.weightx = 0; GBC.insets = new Insets(top,5,10,5); pAlcaldes.add(actualJAlcalde.bEditar,GBC);
            GBC.gridx = 4; GBC.gridy = i; GBC.weightx = 0; GBC.insets = new Insets(top,5,10,5); pAlcaldes.add(actualJAlcalde.bEliminar,GBC);
            
            actualJAlcalde.bIniciar.setPreferredSize(new Dimension(100,50));
            actualJAlcalde.bEditar.setPreferredSize(new Dimension(100,50));
            actualJAlcalde.bEliminar.setPreferredSize(new Dimension(100,50));
            actualJAlcalde.lAlcalde.setPreferredSize(new Dimension(100,50));
            
            actualJAlcalde.lAlcalde.setHorizontalTextPosition(JLabel.LEFT);
            actualJAlcalde.lAlcalde.setVerticalTextPosition(JLabel.CENTER);
            
            actualJAlcalde.bIniciar.addActionListener(this);
            actualJAlcalde.bEditar.addActionListener(this);
            actualJAlcalde.bEliminar.addActionListener(this);
            
            actualJAlcalde.lAlcalde.setBackground(cLabel); 
            actualJAlcalde.lAlcalde.setOpaque(true); 
            actualJAlcalde.lAlcalde.setFont(fTexto); 
            
            actualJAlcalde.bIniciar.setBackground(cBoton2); 
            actualJAlcalde.bIniciar.setForeground(cText2); 
            actualJAlcalde.bIniciar.setFont(fSubTitulo); 
            actualJAlcalde.bEditar.setBackground(cBoton1); 
            actualJAlcalde.bEditar.setForeground(cText2); 
            actualJAlcalde.bEditar.setFont(fSubTitulo); 
            actualJAlcalde.bEliminar.setBackground(cBoton3); 
            actualJAlcalde.bEliminar.setForeground(cText2); 
            actualJAlcalde.bEliminar.setFont(fSubTitulo); 
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
        bCrear.addActionListener(this);
        bRegresar.addActionListener(this);
    }
    
    
    public void setPosition1() throws SQLException{
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
    public void setPosition2() throws SQLException{
        setLayout(new BorderLayout());
        pTitulo.removeAll();
        pTitulo.setBackground(cBarra);
        pTitulo.setBackground(cBarra);
        
        pBoton.removeAll();
        pBoton.setBackground(cBarra);
        add(pTitulo,BorderLayout.NORTH);
        add(spAlcaldes,BorderLayout.CENTER);
        add(pBoton,BorderLayout.SOUTH);
        
        
        pTitulo.setLayout(GBL);
        GBC.anchor = GridBagConstraints.CENTER;

        GBC.gridx = 0; GBC.gridy = 0; pTitulo.add(lMenuTitulo2,GBC); 
        GBC.gridx = -1; GBC.gridy = 0; pTitulo.add(bRegresar,GBC); 
        bRegresar.setPreferredSize(new Dimension(5,20));
        
        pBoton.setLayout(GBL);
        GBC.anchor = GridBagConstraints.CENTER;
        GBC.gridx = 0; GBC.gridy = 0; pBoton.add(bCrear,GBC); 

        pAlcaldes.setLayout(GBL);  
        actualizarAPanelCiudades();
        
  
 
    } 
    
    private void botonAccesoPresionado(ActionEvent ae) throws SQLException{
        for(int i=0; i<JAlcaldes.size(); i++){
            JAlcalde actualJAlcalde = JAlcaldes.get(i);
            if(ae.getSource() == actualJAlcalde.bAcceder){
                setEnableButtons(false);
                new MenuIniciarSesion(this,Integer.parseInt(TUPLAS.get(i).get(3).toString()));
            }
             if(ae.getSource() == actualJAlcalde.bIniciar){
                setEnableButtons(false);
                /*ModuloJose*/
            }
              if(ae.getSource() == actualJAlcalde.bEditar){
                setEnableButtons(false);
                Menu.idciudad=Integer.parseInt(TUPLAS.get(i).get(0).toString());
                new ModificarCiudad(this);
            }
               if(ae.getSource() == actualJAlcalde.bEliminar){
                  Menu.idciudad=Integer.parseInt(TUPLAS.get(i).get(0).toString());
                  
               
                   System.out.print("");
                   String query="Delete from ciudades where id=?";
                   Object values[] = {Menu.idciudad};
                   try {
                ConexionPostgres BDD = new ConexionPostgres();
                
                setEnableButtons(false);
                           
                setEnableButtons(false);
                Object[] opciones = { "Si", "No" };  
               int seleccion=JOptionPane.showOptionDialog(this,"¿Seguro? Se perdera toda informacion de la ciudad",  "Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, opciones, opciones[1]);  
                if (seleccion == 0) {
                    BDD.comandoDML(query,values);
            JOptionPane.showMessageDialog(this, "Se ha eliminado la Ciudad.");
                    actualizarAPanelCiudades();
            
        } else if (seleccion == 1) {
            
        }  
            } catch (SQLException ex) {}

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
        try {
            botonAccesoPresionado(ae);
        } catch (SQLException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(ae.getSource()==bAgregar){
            try {
                setEnableButtons(false);
                new MenuRegistrarAlcalde(this);   
            } catch (SQLException ex) {}
        } else if(ae.getSource()==bCrear){
            try {
                setEnableButtons(false);
                new MenuCrearCiudad(this);   
            } catch (SQLException ex) {}
        }else if(ae.getSource()==bRegresar){
            try {
                setEnableButtons(false);
                actualizarPanelAlcaldes();   
            } catch (SQLException ex) {}
        }
    }
}
