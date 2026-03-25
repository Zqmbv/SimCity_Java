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
    }
    static Tile Cursor = new Tile();
    static ArrayList<Tile> Tiles = new ArrayList<>();
    
    View2D newView2D = new View2D();
    View3D newView3D = new View3D();
    
    JLabel TxtInfo = new JLabel("Selcect a build...");
    JButton BtnConstruir = new JButton("Build");
    JButton BtnDesruir = new JButton("Demolish");
    
    String Category[] = {"RCI","Electricity","Services","Turism","Others"};
    String Options[][] = {{"Residence","Comercial","Industrial"},
                          {"Coal Power Plant","Nuclear Power Plant"},
                          {"Police Station","Fire Station"},
                          {"Stadium","Airport","Seaport"},
                          {"Road","Rail","Park","Wire"}};
    
    public Inspector(){
        this.setLayout(new BorderLayout());
        
        JPanel newPanel = new JPanel(new BorderLayout());
            newPanel.add(TopBar(),BorderLayout.NORTH);        
            newPanel.add(newView2D,BorderLayout.CENTER);      
            newPanel.add(BottomBar(),BorderLayout.SOUTH);
        this.add(newPanel,BorderLayout.WEST);
        
        this.add(newView3D,BorderLayout.CENTER);            
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
        
        JMenuItem Demolish = new JMenuItem("Demolish");        
        Demolish.addActionListener(this);
        myMenu.add(Demolish); 
        
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
        
        return panel;
    }
   
    public void Build(){
        if(Cursor.size == 0){
            JOptionPane.showMessageDialog(this, "ERROR - NO SELECCIONÓ UNA ESTRUCTURA");
            newView2D.repaint(); return;}
        
        Rectangle newTileCollider = new Rectangle(Cursor.x,Cursor.y,Cursor.size, Cursor.size);
        for(Tile T : Tiles){
            //Wire sobre road y rail
            //revisar road sobre rail
            if(Cursor.tag == "W" && (T.tag == "R" || T.tag == "P")){continue;}
            
            Rectangle oldTileCollider = new Rectangle(T.x,T.y, T.size, T.size);
            if(newTileCollider.intersects(oldTileCollider)){
                JOptionPane.showMessageDialog(this, "ERROR - LA ESTRUCTURA COLISIONO CON OTRA");
                newView2D.repaint();return;
            }
        }         
        
        //OJO - VALORES NO ESCALADOS
        Tile  newTile  = new Tile();
        newTile.x = Cursor.x;
        newTile.y = Cursor.y;
        newTile.tag = Cursor.tag;
        newTile.size = Cursor.size;
        newTile.color = Cursor.color;                                  
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
    
     public void Change(String tag, int size, Color color){
        Cursor.tag = tag;
        Cursor.size = size;
        Cursor.color = color;
        repaint(); 
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == BtnConstruir){Build();return;}
        if(e.getSource() == BtnDesruir){Demolish();return;}
        
        String CMD = ((JMenuItem)e.getSource()).getText(); 
        
        if(CMD == "Demolish"){
            BtnConstruir.setVisible(false);
            BtnDesruir.setVisible(true);
            TxtInfo.setText("What do you want to demolish?");
        
            Change("F",1,Color.red);   
            newView2D.repaint();
            return;
        }else{
            BtnConstruir.setVisible(true);
            BtnDesruir.setVisible(false);      
            TxtInfo.setText("You Selected: " + CMD);
        }
        
        switch(CMD){
            case "Residence":Change("R",3,Color.red); break;
            case "Comercial":Change("C",3,Color.blue); break;
            case "Industrial":Change("I",3,Color.yellow); break;
            case "Coal Power Plant":Change("CPP",4,Color.white); break;
            case "Nuclear Power Plant":Change("NPP",6,Color.white); break;
            case "Wire":Change("W",1,Color.white); break;
            case "Police Station":Change("PS",3,Color.cyan); break;            
            case "Fire Station":Change("FS",3,Color.orange); break;            
            case "Stadium":Change("S",4,Color.magenta); break;            
            case "Airport":Change("AP",4,Color.magenta); break;
            case "Seaport":Change("SP",4,Color.magenta); break;            
            case "Road":Change("R",1,Color.black); break; 
            case "Rail":Change("R",1,Color.darkGray); break; 
            case "Park":Change("P",1,Color.GREEN); break; 
        }
    }
}
