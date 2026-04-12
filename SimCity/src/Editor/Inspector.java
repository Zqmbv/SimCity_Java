package Editor;
import BDD.ConexionPostgres;
import Editor.Prefabs.*;
import Menu.MenuCiudades;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import java.util.logging.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import static java.util.Map.entry;

public class Inspector extends JPanel implements ActionListener {
    
    //MAP
    public static int ID_CIUDAD = -1;
    public static int MAP_WIDTH = 32;
    public static int MAP_HEIGHT = 32; 
    
    static class DATA{
        String tag;
        int size;
        Color color;
       
        public DATA(String t, int s, Color c){
        tag=t;size=s;color=c;}
    }
    
    Map<String,DATA> Memory = Map.ofEntries(
        entry("Zona Residencial",new DATA("R",3,Color.red)),
        entry("Zona Comercial",new DATA("C",3,Color.blue)),
        entry("Zona Industrial",new DATA("I",3,Color.yellow)),
        entry("Planta de Carbón",new DATA("PC",4,Color.white)),
        entry("Planta Nuclear",new DATA("PN",4,Color.white)),
        entry("Estación de Policía",new DATA("EP",3,Color.cyan)),
        entry("Estación de Bomberos",new DATA("EB",3,Color.orange)),
        entry("Puerto Marítimo",new DATA("PM",4,Color.magenta)),
        entry("Aeropuerto",new DATA("A",6,Color.magenta)),
        entry("Estadio",new DATA("E",4,Color.magenta)),
        entry("Carretera",new DATA("C",1,Color.black)),
        entry("Vía de Tren",new DATA("T",1,Color.gray)),            
        entry("Líneas Eléctricas",new DATA("L",1,Color.white)),
        entry("Parque",new DATA("P",1,Color.green)),   
        entry("Demoler",new DATA("x",1,Color.red)),
        entry("Rotar",new DATA("+",1,Color.green))                
    );

    static class Tile{
        int x,y;
        DATA struct;
        Tile(){};
        Tile(Tile T){x = T.x; y = T.y; struct = T.struct;}
    }
    
    String CMD; 
    static Tile Cursor = new Tile();
    static ArrayList<Tile> Tiles2D = new ArrayList<>();
    static ArrayList<BranchGroup> Tiles3D = new ArrayList<>();
    
    
    View2D newView2D = new View2D();
    View3D newView3D = new View3D();
   
    
    Map<String,String[]> TxtMenu = Map.ofEntries(
        entry("RCI",new String[]{"Zona Residencial","Zona Comercial","Zona Industrial"}),      
        entry("Energia",new String[]{"Planta de Carbón","Planta Nuclear"}), 
        entry("Servicios",new String[]{"Estación de Policía","Estación de Bomberos"}), 
        entry("Turismo",new String[]{"Estadio","Puerto Marítimo","Aeropuerto"}), 
        entry("Otros",new String[]{"Carretera","Vía de Tren","Parque","Líneas Eléctricas"}) 
    );    
    
    JLabel TxtInfo = new JLabel("Se Eligió: Zona Residencial");
    JButton BtnConstruir = new JButton("Construir");
    JButton BtnDesruir = new JButton("Demoler");
    JButton BtnRotar = new JButton("Rotar");   
    
    ConexionPostgres BD = new ConexionPostgres();
    
    public Inspector() throws SQLException{        
        String QUERY = "SELECT tipo,posx,posy,rotacion FROM tiles WHERE idCiudad = ?";

        ResultSet RS = BD.consultar(QUERY,new Object[]{ID_CIUDAD});
        
        ArrayList<ArrayList<Object>> Rows = new ArrayList<>();
        while(RS != null && RS.next()){
            ArrayList<Object> newRow = new ArrayList<>();
            newRow.add(RS.getString("Tipo"));
            newRow.add(Integer.valueOf(RS.getString("posx")));            
            newRow.add(Integer.valueOf(RS.getString("posy")));             
            newRow.add(Float.valueOf(RS.getString("rotacion"))); 
            Rows.add(newRow);
        }       
        
        for(ArrayList<Object> R: Rows){
            Change((String) R.get(0));
            Cursor.x = (int) R.get(1);
            Cursor.y = (int) R.get(2);
            AddToTheScene((float) R.get(3));
        }
        
        this.setLayout(new BorderLayout());
        
        JPanel newPanel = new JPanel(new BorderLayout());
            newPanel.add(TopBar(),BorderLayout.NORTH);   
            newPanel.add(newView2D,BorderLayout.CENTER);
            newPanel.add(BottomBar(),BorderLayout.SOUTH);
        this.add(newPanel,BorderLayout.WEST);
        this.add(newView3D,BorderLayout.CENTER);
        
        Cursor.x=0;Cursor.y=0;
        Change("Zona Residencial");
    }
    
    JMenuBar TopBar(){
        JMenuBar myMenu = new JMenuBar();

        JMenuItem miVolver = new JMenuItem("<- Volver");        
        miVolver.addActionListener(this);
        myMenu.add(miVolver); 
        
        for (String key : new String[]{"RCI","Energia","Servicios","Turismo","Otros"}) {
            JMenu newMenu = new JMenu(key);
            for (String Value : TxtMenu.get(key)) {
                JMenuItem newOption = new JMenuItem(Value);
                newOption.addActionListener(this);
                newMenu.add(newOption);
            }myMenu.add(newMenu);
        }       
            
        JMenuItem Demolish = new JMenuItem("Demoler");        
        Demolish.addActionListener(this);
        myMenu.add(Demolish); 
        
        JMenuItem Update = new JMenuItem("Rotar");        
        Update.addActionListener(this);
        myMenu.add(Update); 
        
        return myMenu;
    }
    
    JPanel BottomBar(){
        JPanel panel = new JPanel(new GridBagLayout()); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = gbc.BOTH;
        gbc.weightx = 1; gbc.weighty =1;
        gbc.insets = new Insets(5,5,5,5);
        
        panel.add(TxtInfo,gbc);
        
        gbc.gridy=1;
        panel.add(BtnConstruir,gbc);        
        BtnConstruir.addActionListener(this);
        panel.add(BtnDesruir,gbc);        
        BtnDesruir.addActionListener(this);
        BtnDesruir.setVisible(false);
        panel.add(BtnRotar,gbc);        
        BtnRotar.addActionListener(this);
        BtnRotar.setVisible(false);
        
        return panel;
    }
   
    public void Build() throws SQLException{
        Rectangle newTileCollider = new Rectangle(Cursor.x,Cursor.y,Cursor.struct.size, Cursor.struct.size);
        Rectangle Border = new Rectangle(0, 0, MAP_WIDTH-Cursor.struct.size+1, MAP_HEIGHT-Cursor.struct.size+1);
        
        if(!Border.intersects(newTileCollider)){
                JOptionPane.showMessageDialog(this, "ERROR\nSE SALIÓ DE LA ZONA DE CONTRUCCIÓN.","ERROR",JOptionPane.ERROR_MESSAGE);
                newView2D.repaint();return;
        }
        
        int Check = hasCollission();
        if(Check !=  -1){
            JOptionPane.showMessageDialog(this, "ERROR\nLA ESTRUCTURA COLISIONÓ CON OTRA.","ERROR",JOptionPane.ERROR_MESSAGE);
            newView2D.repaint();return;}
        
        String ins = "INSERT INTO tiles (idCiudad,tipo,posx,posy,rotacion) VALUES (?,?,?,?,?)";
        Object[] values = {ID_CIUDAD,CMD,Cursor.x,Cursor.y,0};
        BD.comandoDML(ins,values);
        
        if(CMD.equals("Zona Residencial")){
            String ins2 = "UPDATE ciudades SET poblacion = poblacion + 900 WHERE id = ?";
            Object[] values2 = {ID_CIUDAD};
            BD.comandoDML(ins2,values2);
        }
        
        AddToTheScene(0);
    }
    
    public void Demolish() throws SQLException{
        int Check = hasCollission();
        if(Check ==  -1){
            JOptionPane.showMessageDialog(this, "ERROR\nNO HAY NADA QUE DEMOLER.","ERROR",JOptionPane.ERROR_MESSAGE);
            newView2D.repaint();return;}        
        
        String ins = "DELETE FROM tiles WHERE idCiudad = ? AND posx = ? AND posy = ?";
        Object[] values = {ID_CIUDAD,Tiles2D.get(Check).x,Tiles2D.get(Check).y};
        BD.comandoDML(ins,values);

        if(Tiles2D.get(Check).struct.tag.equals("R")){
            String ins2 = "UPDATE ciudades SET poblacion = poblacion - 900 WHERE id = ?";
            Object[] values2 = {ID_CIUDAD};
            BD.comandoDML(ins2,values2);
        }
        
        Tiles2D.remove(Check);
        newView3D.RemoveModel(Tiles3D.get(Check));
        Tiles3D.remove(Check);
        newView3D.CamUpdate(new Vector3f(2*Cursor.x+((float)Cursor.struct.size),20,2*Cursor.y+(float)Cursor.struct.size), new Vector2f((float)Math.toRadians(-90),0));
        newView2D.repaint();
    }

    public void Rotate() throws SQLException{
        int Check = hasCollission();
        if(Check == -1){
            JOptionPane.showMessageDialog(this, "ERROR\nNO HAY NADA QUE ROTAR.","ERROR",JOptionPane.ERROR_MESSAGE);
            newView2D.repaint(); return;    
        }
        
        String ins = "UPDATE tiles SET rotacion = MOD(rotacion+90,360) WHERE idCiudad = ? AND posx = ? AND posy = ?";
        Object[] values = {ID_CIUDAD,Tiles2D.get(Check).x,Tiles2D.get(Check).y};
        BD.comandoDML(ins,values);

        newView3D.CamUpdate(new Vector3f(2*Cursor.x+((float)Cursor.struct.size),20,2*Cursor.y+(float)Cursor.struct.size), new Vector2f((float)Math.toRadians(-90),0));
        newView3D.rotarHijoInterno(Tiles3D.get(Check),90);
        newView2D.repaint();  
    }
    
     public void Change(String key){
        Cursor.struct = Memory.get(key);
        newView2D.repaint(); 
        CMD = key;
    }
   
    public int hasCollission(){
        Rectangle CursorHitbox = new Rectangle(Cursor.x,Cursor.y,Cursor.struct.size, Cursor.struct.size);
        for(Tile T : Tiles2D){
            Rectangle OtherHitBox = new Rectangle(T.x,T.y, T.struct.size, T.struct.size);
            if(CursorHitbox.intersects(OtherHitBox)){return Tiles2D.indexOf(T);}       
        }
         return -1;
    }
    
    public void AddToTheScene(float THETA){
        //2D
        Tile  newTile  = new Tile(Cursor);                              
        Tiles2D.add(newTile);
        newView2D.repaint();
        
        //3D
        TransformGroup newStruct = null;
        switch(CMD){
            case "Zona Residencial": newStruct = new Residencial(); break;
            case "Zona Comercial": newStruct = new Comercial(); break;
            case "Zona Industrial": newStruct = new Industrial(); break;
            case "Planta de Carbón": newStruct = new CoalPowerPlant(); break;
            case "Planta Nuclear": newStruct = new NuclearPowerPlant(); break;
            case "Estación de Policía": newStruct = new PoliceStation(); break;
            case "Estación de Bomberos": newStruct = new FireStation(); break;
            case "Puerto Marítimo": newStruct = new Seaport(); break;
            case "Aeropuerto": newStruct = new Airport(); break;
            case "Estadio": newStruct = new Stadium(); break;
            case "Carretera": newStruct = new Road(); break;
            case "Vía de Tren": newStruct = new Rail(); break;
            case "Líneas Eléctricas": newStruct = new Wire(); break;
            case "Parque": newStruct = new Park(); break;
        }
        Tiles3D.add(newView3D.AddModel(newStruct,Cursor.x+((float)Cursor.struct.size)/2, Cursor.y+((float)Cursor.struct.size)/2,THETA));
        newView3D.CamUpdate(new Vector3f(2*Cursor.x+((float)Cursor.struct.size),20,2*Cursor.y+(float)Cursor.struct.size), new Vector2f((float)Math.toRadians(-90),0));
    }
         
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if(e.getSource() == BtnConstruir){Build();return;}
            if(e.getSource() == BtnDesruir){Demolish();return;}
            if(e.getSource() == BtnRotar){Rotate();return;}
        } catch (SQLException ex) {
            Logger.getLogger(Inspector.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        CMD = ((JMenuItem)e.getSource()).getText(); 
        BtnConstruir.setVisible(false);
        BtnDesruir.setVisible(false);
        BtnRotar.setVisible(false);

        if("Demoler".equals(CMD)){
            BtnDesruir.setVisible(true);
            TxtInfo.setText("¿Qué desea demoler?"); 
        }else if("Rotar".equals(CMD)){
            BtnRotar.setVisible(true);
            TxtInfo.setText("¿Qué desea rotar?"); 
        }else if("<- Volver".equals(CMD)){
            JFrame framePrincipal = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (framePrincipal != null) {
                framePrincipal.remove(this);
                try {
                    framePrincipal.add(new MenuCiudades(MenuCiudades.idAlcalde));
                    Tiles2D.clear();
                    Tiles3D.clear();
                } catch (SQLException ex) {
                    Logger.getLogger(Inspector.class.getName()).log(Level.SEVERE, null, ex);
                }
                framePrincipal.revalidate();
                framePrincipal.repaint();
            }  
        }else{
            BtnConstruir.setVisible(true);
            TxtInfo.setText("Se Eligió: " + CMD);   
        }            
        Change(CMD);
    }
}
