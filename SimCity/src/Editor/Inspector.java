package Editor;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import static java.util.Map.entry;

public class Inspector extends JPanel implements ActionListener {
    
    //MAP
    static int MAP_WIDTH = 10;
    static int MAP_HEIGHT = 10; 
    
    //DATA UNITARIO(1,1,1)
    static class Tile{
        String tag;
        int x,y, size = 0;
        Color color;
        
        Tile(){};
        Tile(String t, int s,Color c){
            this.tag = t; this.size = s; this.color = c;};
        Tile(Tile T){this.x = T.x; this.y = T.y;
            this.tag = T.tag; this.size = T.size; this.color = T.color;};
    }
    
    static Tile Cursor = new Tile();
    static ArrayList<Tile> Tiles = new ArrayList<>();
    
    View2D newView2D = new View2D();
    View3D newView3D = new View3D();
    
    JLabel TxtInfo = new JLabel("Se Eligio: Zona Residencial");
    JButton BtnConstruir = new JButton("Construir");
    JButton BtnDesruir = new JButton("Demoler");
    JButton BtnMejorar = new JButton("Mejorar");    
    
    Map<String,Tile> Datos = Map.ofEntries(
        entry("Zona Residencial",new Tile("R",3,Color.red)),
        entry("Zona Comercial",new Tile("C",3,Color.blue)),
        entry("Zona Industrial",new Tile("I",3,Color.yellow)),

        entry("Planta de Carbón",new Tile("PC",4,Color.white)),
        entry("Planta Nuclear",new Tile("PN",4,Color.white)),

        entry("Estación de Policía",new Tile("EP",3,Color.cyan)),
        entry("Estación de Bomberos",new Tile("EB",3,Color.orange)),

        entry("Carretera",new Tile("C",1,Color.black)),
        entry("Vía de Tren",new Tile("T",1,Color.gray)),            
        entry("Líneas Eléctricas",new Tile("L",1,Color.white)),
        entry("Parque",new Tile("P",1,Color.green)),               

        entry("Puerto Marítimo",new Tile("PM",4,Color.magenta)),
        entry("Aeropuerto",new Tile("A",6,Color.magenta)),
        entry("Estadio",new Tile("E",4,Color.magenta))
    );
    
    String Category[] = {"RCI","Energia","Servicios","Turismo","Otros"};
    String Options[][] = {{"Zona Residencial","Zona Comercial","Zona Industrial"},
                          {"Planta de Carbón","Planta Nuclear"},
                          {"Estación de Policía","Estación de Bomberos"},
                          {"Estadio","Aeropuerto","Puerto Marítimo"},
                          {"Carretera","Vía de Tren","Parque","Líneas Eléctricas"}};
    
    public Inspector(){
        this.setLayout(new BorderLayout());
        
        JPanel newPanel = new JPanel(new BorderLayout());
            newPanel.add(TopBar(),BorderLayout.NORTH);        
            newPanel.add(newView2D,BorderLayout.CENTER);      
            newPanel.add(BottomBar(),BorderLayout.SOUTH);
        this.add(newPanel,BorderLayout.WEST);
        
        this.add(newView3D,BorderLayout.CENTER);
        Change(Datos.get("Zona Residencial"));
    }
    
    JMenuBar TopBar(){
        JMenuBar myMenu = new JMenuBar();
                
        for(int i = 0; i < Category.length; i++) {
            JMenu newMenu = new JMenu(Category[i]);
            for(String O : Options[i]){
                JMenuItem newOption = new JMenuItem(O);
                newOption.addActionListener(this);
                newMenu.add(newOption);
            }
            
            myMenu.add(newMenu);
        }
        
        JMenuItem Demolish = new JMenuItem("Demoler");        
        Demolish.addActionListener(this);
        myMenu.add(Demolish); 
        
        JMenuItem Update = new JMenuItem("Mejorar");        
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
        
        gbc.gridy=2;
        panel.add(BtnDesruir,gbc);        
        BtnDesruir.addActionListener(this);
        BtnDesruir.setVisible(false);

        gbc.gridy=3;
        panel.add(BtnMejorar,gbc);        
        BtnMejorar.addActionListener(this);
        BtnMejorar.setVisible(false);
        
        return panel;
    }
   
    public void Build(){
        Rectangle newTileCollider = new Rectangle(Cursor.x,Cursor.y,Cursor.size, Cursor.size);
        Rectangle Border = new Rectangle(0, 0, Inspector.MAP_WIDTH-Inspector.Cursor.size+1, Inspector.MAP_HEIGHT-Inspector.Cursor.size+1);
        
        if(!Border.intersects(newTileCollider)){
                JOptionPane.showMessageDialog(this, "ERROR\nSE SALIO DE LA ZONA DE CONTRUCCIÓN");
                newView2D.repaint();return;
        }
        
        
        for(Tile T : Tiles){            
            Rectangle oldTileCollider = new Rectangle(T.x,T.y, T.size, T.size);
            if(newTileCollider.intersects(oldTileCollider)){
                JOptionPane.showMessageDialog(this, "ERROR\nLA ESTRUCTURA COLISIONÓ CON OTRA");
                newView2D.repaint();return;
            }
        }         
        
        //OJO - VALORES NO ESCALADOS
        Tile  newTile  = new Tile(Cursor);                              
        Tiles.add(newTile);
        newView2D.repaint();
    }
    
    public void Demolish(){
        Rectangle DestroyCollider = new Rectangle(Cursor.x,Cursor.y,Cursor.size, Cursor.size);
        for(int i = 0 ; i < Tiles.size(); i++){
            Rectangle BuildCollider = new Rectangle(Tiles.get(i).x,Tiles.get(i).y, Tiles.get(i).size, Tiles.get(i).size);
            if(DestroyCollider.intersects(BuildCollider)){
                Tiles.remove(Tiles.get(i));
            }
        }         

        newView2D.repaint();      
    }

    public void Update(){ //Coming Soon
        newView2D.repaint();      
    }
    
     public void Change(Tile Dat){
        Cursor.tag = Dat.tag;
        Cursor.size = Dat.size;
        Cursor.color = Dat.color;
        repaint(); 
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == BtnConstruir){Build();return;}
        if(e.getSource() == BtnDesruir){Demolish();return;}
        
        String CMD = ((JMenuItem)e.getSource()).getText(); 
        
        if(CMD == "Demoler"){
            BtnConstruir.setVisible(false);
            BtnDesruir.setVisible(true);
            BtnMejorar.setVisible(false);
            
            TxtInfo.setText("Que desea demoler?");
            Change(new Tile(" - ",1,Color.red));   
            newView2D.repaint();
            return;
        }
            
        if(CMD == "Mejorar"){
            BtnConstruir.setVisible(false);
            BtnDesruir.setVisible(false);
            BtnMejorar.setVisible(true);
            
            TxtInfo.setText("Que desea mejorar?");
            Change(new Tile(" + ",1,Color.green));   
            newView2D.repaint();
            return;
        }            
            
        BtnConstruir.setVisible(true);
        BtnDesruir.setVisible(false);
        BtnMejorar.setVisible(false); 
        TxtInfo.setText("Se Eligio: " + CMD);        
        Change(Datos.get(CMD));
    }
}
