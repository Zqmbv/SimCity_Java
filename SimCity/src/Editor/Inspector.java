package Editor;
import BDD.ConexionPostgres;
import Editor.Prefabs.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import static java.util.Map.entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.j3d.*;
import javax.vecmath.*;

public class Inspector extends JPanel implements ActionListener {
    
    //MAP
    public static int ID_CIUDAD = -1;
    public static int MAP_WIDTH = 32;
    public static int MAP_HEIGHT = 32; 
    
    static class DATA{
        String tag,ref;
        int size;
        Color color;
       
        public DATA(String t, int s, Color c,String r){
        tag=t;size=s;color=c;ref=r;}
    }
    
    Map<String,DATA> Memory = Map.ofEntries(
        entry("Zona Residencial",new DATA("R",3,Color.red, "Zona Residencial")),
        entry("Zona Comercial",new DATA("C",3,Color.blue, "Zona Comercial")),
        entry("Zona Industrial",new DATA("I",3,Color.yellow, "Zona Industrial")),

        entry("Planta de Carbón",new DATA("PC",4,Color.white, "Planta de Carbón")),
        entry("Planta Nuclear",new DATA("PN",4,Color.white, "Planta Nuclear")),

        entry("Estación de Policía",new DATA("EP",3,Color.cyan,"Estación de Policía")),
        entry("Estación de Bomberos",new DATA("EB",3,Color.orange,"Estación de Bomberos")),

        entry("Puerto Marítimo",new DATA("PM",4,Color.magenta,"Puerto Marítimo")),
        entry("Aeropuerto",new DATA("A",6,Color.magenta,"Aeropuerto")),
        entry("Estadio",new DATA("E",4,Color.magenta,"Estadio")),
        
        entry("Carretera",new DATA("C",1,Color.black,"Carretera")),
        entry("Vía de Tren",new DATA("T",1,Color.gray,"Vía de Tren")),            
        entry("Líneas Eléctricas",new DATA("L",1,Color.white,"Líneas Eléctricas")),
        entry("Parque",new DATA("P",1,Color.green,"Parque")),   
        
        entry("Demoler",new DATA("x",1,Color.red,null)),
        entry("Rotar",new DATA("+",1,Color.green,null))                
    );

    //DATA UNITARIO(1,1,1)
    static class Tile{
        int x,y;
        DATA struct;
        Tile(){};
        Tile(Tile T){x = T.x; y = T.y; struct = T.struct;}
    }
    
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
    
    JLabel TxtInfo = new JLabel("Se Eligio: Zona Residencial");
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
            newRow.add(Double.valueOf(RS.getString("rotacion"))); 
            Rows.add(newRow);
        }       
        
        for(ArrayList<Object> R: Rows){
            Change(Memory.get(R.get(0)));
            Cursor.x = (int) R.get(1);
            Cursor.y = (int) R.get(2);
            
            //OJO - VALORES NO ESCALADOS
            Tile  newTile  = new Tile(Cursor);                              
            Tiles2D.add(newTile);
            newView2D.repaint();

            TransformGroup newStruct = null;
            switch(Cursor.struct.ref){
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

            Tiles3D.add(newView3D.AddModel(newStruct,Cursor.x+((float)Cursor.struct.size)/2, Cursor.y+((float)Cursor.struct.size)/2,(float) (double) R.get(3)));
        }
        
        this.setLayout(new BorderLayout());
        
        JPanel newPanel = new JPanel(new BorderLayout());
            newPanel.add(TopBar(),BorderLayout.NORTH);   
            newPanel.add(newView2D,BorderLayout.CENTER);
            newPanel.add(BottomBar(),BorderLayout.SOUTH);
        this.add(newPanel,BorderLayout.WEST);
        this.add(newView3D,BorderLayout.CENTER);
        
        Cursor.x=0;Cursor.y=0;
        Change(Memory.get("Zona Residencial"));
    }
    
    JMenuBar TopBar(){
        JMenuBar myMenu = new JMenuBar();
               
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
                JOptionPane.showMessageDialog(this, "ERROR\nSE SALIO DE LA ZONA DE CONTRUCCIÓN");
                newView2D.repaint();return;
        }
        
        for(Tile T : Tiles2D){            
            Rectangle oldTileCollider = new Rectangle(T.x,T.y, T.struct.size, T.struct.size);
            if(newTileCollider.intersects(oldTileCollider)){
                JOptionPane.showMessageDialog(this, "ERROR\nLA ESTRUCTURA COLISIONÓ CON OTRA");
                newView2D.repaint();return;
            }
        }         
        
        String ins = "INSERT INTO tiles (idCiudad,tipo,posx,posy,rotacion) VALUES (?,?,?,?,?)";
        Object[] values = {ID_CIUDAD,Cursor.struct.ref,Cursor.x,Cursor.y,0};
        BD.comandoDML(ins,values);
        
        //OJO - VALORES NO ESCALADOS
        Tile  newTile  = new Tile(Cursor);                              
        Tiles2D.add(newTile);
        newView2D.repaint();
        
        TransformGroup newStruct = null;
        switch(Cursor.struct.ref){
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
        
        Tiles3D.add(newView3D.AddModel(newStruct,Cursor.x+((float)Cursor.struct.size)/2, Cursor.y+((float)Cursor.struct.size)/2,0));
        newView3D.CamUpdate(new Vector3f(2*Cursor.x+((float)Cursor.struct.size),20,2*Cursor.y+(float)Cursor.struct.size), new Vector2f((float)Math.toRadians(-90),0));
    }
    
    public void Demolish() throws SQLException{
        Rectangle DestroyCollider = new Rectangle(Cursor.x,Cursor.y,Cursor.struct.size, Cursor.struct.size);
        for(int i = 0 ; i < Tiles2D.size(); i++){
            Rectangle BuildCollider = new Rectangle(Tiles2D.get(i).x,Tiles2D.get(i).y, Tiles2D.get(i).struct.size, Tiles2D.get(i).struct.size);
            if(DestroyCollider.intersects(BuildCollider)){
                
                
                String ins = "DELETE FROM tiles WHERE idCiudad = ? AND posx = ? AND posy = ?";
           
                Object[] values = {ID_CIUDAD,Tiles2D.get(i).x,Tiles2D.get(i).y};
                BD.comandoDML(ins,values);
                
                Tiles2D.remove(Tiles2D.get(i));
                newView3D.RemoveModel(Tiles3D.get(i));
                Tiles3D.remove(Tiles3D.get(i));
                newView2D.repaint();
                newView3D.CamUpdate(new Vector3f(2*Cursor.x+((float)Cursor.struct.size),20,2*Cursor.y+(float)Cursor.struct.size), new Vector2f((float)Math.toRadians(-90),0));
                return;
            }
        }         
        JOptionPane.showMessageDialog(this, "ERROR\nNO HAY NADA QUE DEMOLER");
        newView2D.repaint();
    }

    public void Rotate() throws SQLException{
        Rectangle DestroyCollider = new Rectangle(Cursor.x,Cursor.y,Cursor.struct.size, Cursor.struct.size);
        for(int i = 0 ; i < Tiles2D.size(); i++){
            Rectangle BuildCollider = new Rectangle(Tiles2D.get(i).x,Tiles2D.get(i).y, Tiles2D.get(i).struct.size, Tiles2D.get(i).struct.size);
            if(DestroyCollider.intersects(BuildCollider)){
               
                int ROT = 0;
                
                String QUERY = "SELECT rotacion FROM tiles WHERE idCiudad = ? AND posx = ? AND posy = ? LIMIT 1";
                ResultSet RS = BD.consultar(QUERY,new Object[]{ID_CIUDAD,Tiles2D.get(i).x,Tiles2D.get(i).y});
                while(RS != null && RS.next()){           
                    ROT = Integer.valueOf(RS.getString("rotacion"));
                }  
                
                ROT+=90;
                if((int)ROT==360){ROT=0;}
                
                String ins = "UPDATE tiles SET rotacion = ? WHERE idCiudad = ? AND posx = ? AND posy = ?";
                Object[] values = {ROT,ID_CIUDAD,Tiles2D.get(i).x,Tiles2D.get(i).y};
                BD.comandoDML(ins,values);
                
                
                newView3D.CamUpdate(new Vector3f(2*Cursor.x+((float)Cursor.struct.size),20,2*Cursor.y+(float)Cursor.struct.size), new Vector2f((float)Math.toRadians(-90),0));
                newView3D.rotarHijoInterno(Tiles3D.get(i),90);
                newView2D.repaint();  
                return;
            }
        }         
        
        JOptionPane.showMessageDialog(this, "ERROR\nNO HAY NADA QUE ROTAR");
        newView2D.repaint();     
    }
    
     public void Change(DATA D){
        Cursor.struct = D;
        newView2D.repaint(); 
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

        
        String CMD = ((JMenuItem)e.getSource()).getText(); 
        
        if(CMD == "Demoler"){
            BtnConstruir.setVisible(false);
            BtnDesruir.setVisible(true);
            BtnRotar.setVisible(false);
            
            TxtInfo.setText("Que desea demoler?");
            Change(Memory.get(CMD)); 
            return;
        }
            
        if(CMD == "Rotar"){
            BtnConstruir.setVisible(false);
            BtnDesruir.setVisible(false);
            BtnRotar.setVisible(true);
            
            TxtInfo.setText("Que desea rotar?");
            Change(Memory.get(CMD));  
            return;
        }            
            
        BtnConstruir.setVisible(true);
        BtnDesruir.setVisible(false);
        BtnRotar.setVisible(false); 
        TxtInfo.setText("Se Eligio: " + CMD);        
        Change(Memory.get(CMD));
    }
}
