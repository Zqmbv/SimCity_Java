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
        
class JCiudad{
    
    JButton bIniciar;
    JLabel lCiudad;
    JButton bEditar;
    JButton bEliminar;

    public JCiudad(String info){
        this.bIniciar = new JButton("Acceder");
        this.lCiudad = new JLabel(info);
        this.bEditar = new JButton("Editar");
        this.bEliminar = new JButton("Eliminar");
    }
}

public class MenuCiudades extends JPanel implements ActionListener{
    public static int idAlcalde;
    public static int idCiudad;
    
    Font fTitulo = new Font("Segoe UI",Font.BOLD,18);
    Font fSubTitulo = new Font("Segoe UI",Font.BOLD,13);
    Font fTexto = new Font("Segoe UI",Font.PLAIN,13);
    
    Color cBarra = new Color(230,230,230); 
    Color cLabel = new Color(240,240,240); 
    Color cFondo = new Color(215,215,215); 
    Color cBoton1 = new Color(0,112,192); 
    Color cBoton2 = new Color(0,176,80); 
    Color cBotonEditar = new Color(0,176,80); 
    Color cBotonEliminar = new Color(205,25,25); 
    Color cText1 = new Color(0,0,0); 
    Color cText2 = new Color(255,255,255); 

    JPanel pTitulo = new JPanel();
    JButton bVolver = new JButton("<- Volver");
    JButton bEditAlcalde = new JButton("Editar Alcalde");
    JButton bElimAlcalde = new JButton("Eliminar Alcalde");
    JLabel lMenuTitulo = new JLabel("MENÚ CIUDADES GUARDADAS");
    
    JPanel pCiuades = new JPanel();
    JScrollPane spCiuades = new JScrollPane(pCiuades);
    ArrayList<JCiudad> JCiudades;
    JLabel lNoData = new JLabel("No hay ciudades registradas.");
    
    JPanel pBoton = new JPanel();
    JButton bAgregar = new JButton("Agregar Ciudad");
    
    
    GridBagLayout GBL = new GridBagLayout();
    GridBagConstraints GBC = new GridBagConstraints();
    
    ArrayList<ArrayList<Object>> TUPLAS;
    
    public MenuCiudades(int idAlcalde) throws SQLException{
        this.idAlcalde = idAlcalde;
        setPosition();
        setTema();
        setConfigComponente();
    }
    
    public void setTema(){
        pTitulo.setBackground(cBarra);
        pCiuades.setBackground(cFondo);
        spCiuades.setBackground(cFondo);
        spCiuades.setOpaque(false);
        pBoton.setBackground(cBarra);
        
        lNoData.setFont(fTexto);
        
        spCiuades.setBorder(BorderFactory.createEmptyBorder());
        
        bAgregar.setBackground(cBoton1); 
        bAgregar.setForeground(cText2); 
        bAgregar.setFont(fSubTitulo);
        
        bEditAlcalde.setBackground(cBotonEditar); 
        bEditAlcalde.setForeground(cText2); 
        bEditAlcalde.setFont(fSubTitulo);
        
        bElimAlcalde.setBackground(cBotonEliminar); 
        bElimAlcalde.setForeground(cText2); 
        bElimAlcalde.setFont(fSubTitulo);
        
        bVolver.setBackground(cBoton1); 
        bVolver.setForeground(cText2); 
        bVolver.setFont(fSubTitulo);
        
        lMenuTitulo.setFont(fTitulo);
    }
    
    public void actualizarPanelCiudades() throws SQLException{
         // CONSULTAR LAS CIUDADES EN LA BDD
        ConexionPostgres BDD = new ConexionPostgres();
        Object VALUES[] = {MenuCiudades.idAlcalde};
        ResultSet RS = BDD.consultar("SELECT id,nombre,poblacion,x,y,descripcion,fechaCreado FROM ciudades WHERE idAlcalde = ?",VALUES);
        TUPLAS = new ArrayList<>();
        
        // OBTENER RESULTADO Y ALMACENARLO EN UNA ARRAYLIST TIPO OBJECT
        while(RS != null && RS.next()){
            ArrayList<Object> T = new ArrayList<>();
            T.add(RS.getString("id"));
            T.add(RS.getString("nombre"));
            T.add(RS.getString("poblacion"));
            T.add(RS.getString("x"));
            T.add(RS.getString("y"));
            T.add(RS.getString("descripcion"));
            T.add(RS.getString("fechaCreado"));
            TUPLAS.add(T);
        }
        
        // LIMPIAMOS TODO EL PANEL
        JCiudades = new ArrayList<>();
        pCiuades.removeAll();
        
        // SI NO HAY CIUDADES REGISTRADAS TODAVIA
        if(TUPLAS.isEmpty()){
            GBC.gridx = 0; GBC.gridy = 0; GBC.weightx = 0; GBC.gridwidth = 1; GBC.insets = new Insets(10,10,10,10); pCiuades.add(lNoData,GBC);
            return;
        }
        
        // SI EXISTEN, MOSTRAMOS NOMBRE DE LA CIUDAD + POBLACION + DIMENSION()
        for(ArrayList<Object> T: TUPLAS){
            String sNombre = (String) T.get(1);
            String sPoblacion = (String) T.get(2);
            String sDimension = (String) T.get(3) + "x" + (String) T.get(4);
            String sDescripcion = (String) T.get(5);
            String sFechaCreado = ((String) T.get(6)).substring(0,19);
            JCiudades.add(new JCiudad("<html>"
                + "<b>"+sNombre+" - Población: "+sPoblacion+" ("+sDimension+")</b>"
                + "<br>"+sDescripcion+""
                + "<br>Creado:  "+sFechaCreado+""
                + "</html>"));
        }
        
        GBC.anchor = GridBagConstraints.NORTH; GBC.fill = GridBagConstraints.HORIZONTAL;
        GBC.ipadx = 0; GBC.gridwidth = 1; GBC.weighty = 0;
        
        // AGREGAR CADA CIUDAD DEL ALCALDE AL PANEL
        for(int i=0; i<JCiudades.size(); i++){
            JCiudad actualJCiudad = JCiudades.get(i);
            
            int top = (i==0) ? 20 : 10;
       
            GBC.gridx = 0; GBC.gridy = i; GBC.weightx = 0; GBC.insets = new Insets(top,70,10,5); pCiuades.add(actualJCiudad.bIniciar,GBC);
            GBC.gridx = 1; GBC.gridy = i; GBC.weightx = 1; GBC.insets = new Insets(top,5,10,5); pCiuades.add(actualJCiudad.lCiudad,GBC);
            GBC.gridx = 3; GBC.gridy = i; GBC.weightx = 0; GBC.insets = new Insets(top,5,10,5); pCiuades.add(actualJCiudad.bEditar,GBC);
            GBC.gridx = 4; GBC.gridy = i; GBC.weightx = 0; GBC.insets = new Insets(top,5,10,70); pCiuades.add(actualJCiudad.bEliminar,GBC);
            
            actualJCiudad.bIniciar.setPreferredSize(new Dimension(100,60));
            actualJCiudad.bEditar.setPreferredSize(new Dimension(90,60));
            actualJCiudad.bEliminar.setPreferredSize(new Dimension(90,60));
            actualJCiudad.lCiudad.setPreferredSize(new Dimension(100,60));
            
            actualJCiudad.lCiudad.setHorizontalTextPosition(JLabel.LEFT);
            actualJCiudad.lCiudad.setVerticalTextPosition(JLabel.CENTER);
            
            actualJCiudad.bIniciar.addActionListener(this);
            actualJCiudad.bEditar.addActionListener(this);
            actualJCiudad.bEliminar.addActionListener(this);
            
            actualJCiudad.lCiudad.setBackground(cLabel); 
            actualJCiudad.lCiudad.setOpaque(true); 
            actualJCiudad.lCiudad.setFont(fTexto); 
            
            actualJCiudad.bIniciar.setBackground(cBoton2); 
            actualJCiudad.bIniciar.setForeground(cText2); 
            actualJCiudad.bIniciar.setFont(fSubTitulo); 
            
            actualJCiudad.bEditar.setBackground(cBoton1); 
            actualJCiudad.bEditar.setForeground(cText2); 
            actualJCiudad.bEditar.setFont(fSubTitulo); 
            
            actualJCiudad.bEliminar.setBackground(cBotonEliminar); 
            actualJCiudad.bEliminar.setForeground(cText2); 
            actualJCiudad.bEliminar.setFont(fSubTitulo); 
        }
        
        // ESTO HACE QUE LOS COMPONENTES SE QUEDEN PEGADOS EN EL NORTE Y SE EXTENDERÁ HASTA ABAJO.
        GBC.anchor = GridBagConstraints.NORTH; GBC.fill = GridBagConstraints.HORIZONTAL;
        GBC.weightx = 1; GBC.weighty = 1;
        GBC.gridx = 0; GBC.gridy = 9999; GBC.gridwidth = 2; pCiuades.add(new JLabel(""),GBC);
        
        pCiuades.revalidate();
        pCiuades.repaint();
    }
    
    public void setConfigComponente(){
        bAgregar.addActionListener(this);
        bVolver.addActionListener(this);
        bEditAlcalde.addActionListener(this);
        bElimAlcalde.addActionListener(this);
        spCiuades.getVerticalScrollBar().setUnitIncrement(12);
    }
    
    public void setPosition() throws SQLException{
        setLayout(new BorderLayout());
        
        add(pTitulo,BorderLayout.NORTH);
        add(spCiuades,BorderLayout.CENTER);
        add(pBoton,BorderLayout.SOUTH);
        
        pTitulo.setLayout(GBL);
        
        GBC.anchor = GridBagConstraints.CENTER;
        GBC.gridwidth = 3; GBC.weightx = 1.0; 
        GBC.insets = new Insets(10, 10, 10, 10); 
        GBC.gridx = 0; GBC.gridy = 0; pTitulo.add(lMenuTitulo,GBC); 
        GBC.gridwidth = 1;
        
        GBC.weightx = 0; 
        GBC.insets = new Insets(10, 20, 10, 20);
        GBC.gridx = 0; GBC.gridy = 0; pTitulo.add(bVolver, GBC);
        
        
        pBoton.setLayout(GBL);
        GBC.weightx = 0; 
        GBC.insets = new Insets(5, 10, 5, 5);
        GBC.gridx = 0; GBC.gridy = 0; pBoton.add(bEditAlcalde, GBC);
        
        GBC.weightx = 1; GBC.fill = GridBagConstraints.BOTH;
        GBC.insets = new Insets(5, 5, 5, 5);
        GBC.gridx = 1; GBC.gridy = 0; pBoton.add(bAgregar,GBC);
        
        GBC.weightx = 0; 
        GBC.insets = new Insets(5, 5, 5, 10);
        GBC.gridx = 2; GBC.gridy = 0; pBoton.add(bElimAlcalde, GBC);
     
        pCiuades.setLayout(GBL);  
        actualizarPanelCiudades();
    }
    
    private void setIdCiudad(int INDICE) throws SQLException{
        this.idCiudad = -1;
        
        ConexionPostgres BD = new ConexionPostgres();
        String QUERY = "SELECT id FROM ciudades "
                + "WHERE nombre = ? AND idAlcalde = ? "
                + "LIMIT 1;";
        Object VALUES[] = {
            (String)TUPLAS.get(INDICE).get(1),
            idAlcalde};

        ResultSet RS = BD.consultar(QUERY,VALUES);
        while(RS != null && RS.next()){
            idCiudad = Integer.parseInt(RS.getString("id"));
        }
    }
    
    private void botonCiudadPresionado(ActionEvent ae) throws SQLException{
        for(int i=0; i<JCiudades.size(); i++){
            JCiudad actualJCiudades = JCiudades.get(i);
            if(ae.getSource() == actualJCiudades.bIniciar){
                setIdCiudad(i);
                JOptionPane.showMessageDialog(this,"MODULO JOSE\nSRC > Menu > MenuCiudades.java > Linea 267","JJ",JOptionPane.INFORMATION_MESSAGE);
                // MODULO JOSE
            }
            if(ae.getSource() == actualJCiudades.bEditar){
                setIdCiudad(i);
                if(idCiudad == -1){
                    JOptionPane.showMessageDialog(this,"La ciudad no ha sido encontrada.","ERROR",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                setEnableButtons(false);
                new FrameModificarCiudad(this);
            }
            if(ae.getSource() == actualJCiudades.bEliminar){
                setIdCiudad(i);
                if(idCiudad == -1){
                    JOptionPane.showMessageDialog(this,"La ciudad no ha sido encontrada.","ERROR",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                ConexionPostgres BD = new ConexionPostgres();
                try {
                    Object[] opciones = {"SÍ","NO"};  
                    int seleccion = JOptionPane.showOptionDialog(this,"¿Seguro? Se borrará permanentemente toda informacion de la ciudad.",  "ADVERTENCIA", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, opciones, opciones[1]);  
                    if (seleccion == 0){
                        BD.comandoDML("DELETE FROM ciudades WHERE id = ? ;", new Object[]{idCiudad});
                        JOptionPane.showMessageDialog(this, "Se ha eliminado la Ciudad.");
                        actualizarPanelCiudades();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(MenuCiudades.class.getName()).log(Level.SEVERE, null, ex);
                }    
            }
        }
    }
    
    public void setEnableButtons(Boolean bool){
        bAgregar.setEnabled(bool);
        bVolver.setEnabled(bool);
        bEditAlcalde.setEnabled(bool);
        bElimAlcalde.setEnabled(bool);
        for(int i=0; i<JCiudades.size(); i++){
            JCiudad actualJCiudades = JCiudades.get(i);
            actualJCiudades.bIniciar.setEnabled(bool);
            actualJCiudades.bEditar.setEnabled(bool);
            actualJCiudades.bEliminar.setEnabled(bool);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            botonCiudadPresionado(ae);
        } catch (SQLException ex) {
            Logger.getLogger(MenuCiudades.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(ae.getSource()==bAgregar){
            setEnableButtons(false);
            try {   
                new FrameRegistrarCiudad(this);
            } catch (SQLException ex) {
                Logger.getLogger(MenuCiudades.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(ae.getSource() == bVolver){
            JFrame ventanaPadre = (JFrame) SwingUtilities.getWindowAncestor(this);
            
            if (ventanaPadre != null) {
                ventanaPadre.remove(this); 
                try {
                    ventanaPadre.add(new MenuAlcaldes());
                } catch (SQLException ex) {
                    Logger.getLogger(MenuCiudades.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                ventanaPadre.revalidate();
                ventanaPadre.repaint();
            }
        }
                if(ae.getSource() == bEditAlcalde){
            try {
                setEnableButtons(false);
                new FrameModificarAlcalde(this, idAlcalde);
            } catch (SQLException ex) {
                Logger.getLogger(MenuCiudades.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(ae.getSource() == bElimAlcalde){
            ConexionPostgres BD = new ConexionPostgres();
            try {
                Object[] opciones = {"SÍ","NO"};  
                int seleccion = JOptionPane.showOptionDialog(this,"¿Seguro? Se borrará permanentemente toda informacion del alcalde.",  "ADVERTENCIA", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, opciones, opciones[1]);  
                if (seleccion == 0){
                    BD.comandoDML("DELETE FROM alcaldes WHERE id = ? ;", new Object[]{idAlcalde});
                    JOptionPane.showMessageDialog(this, "Se ha eliminado el Alcalde.");
                    
                    JFrame ventanaPadre = (JFrame) SwingUtilities.getWindowAncestor(this);
                    if (ventanaPadre != null) {
                        ventanaPadre.remove(this); 
                        try {
                            ventanaPadre.add(new MenuAlcaldes());
                        } catch (SQLException ex) {
                            Logger.getLogger(MenuCiudades.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        ventanaPadre.revalidate();
                        ventanaPadre.repaint();
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(MenuCiudades.class.getName()).log(Level.SEVERE, null, ex);
            }        
        }
    }
}
