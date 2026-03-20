package simcity.WorldEngine.Editor2D;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Inspector extends JPanel implements ActionListener {

    View2D newView = new View2D();
    JLabel TxtInfo = new JLabel("Selcect a build...");
    JButton BtnConstruir = new JButton("Build");
    JButton BtnDesruir = new JButton("Destroy");
    
    public Inspector(){
        this.setLayout(new BorderLayout());
        
        this.add(TopBar(),BorderLayout.NORTH);        
        this.add(newView,BorderLayout.CENTER);      
        this.add(BottomBar(),BorderLayout.SOUTH);
    }
    
    JMenuBar TopBar(){
        JMenuBar myMenu = new JMenuBar();
        JMenu RCI = new JMenu("RCI");        
        JMenu Electricity = new JMenu("Electricity");         
        JMenu Services = new JMenu("Services");        
        JMenu Others = new JMenu("Others");        
        JMenu Turism = new JMenu("Turism"); 
        

        
        JMenuItem Residence = new JMenuItem("Residence");
        Residence.addActionListener(this);
        JMenuItem Comercial = new JMenuItem("Comercial");
        Comercial.addActionListener(this);
        JMenuItem Industrial = new JMenuItem("Industrial");
        Industrial.addActionListener(this);   
        
        JMenuItem CoalPowerPlant = new JMenuItem("Coal Power Plant");
        CoalPowerPlant.addActionListener(this);
        JMenuItem NuclearPowerPlant = new JMenuItem("Nuclear Power Plant");
        NuclearPowerPlant.addActionListener(this);  
        JMenuItem Wire = new JMenuItem("Wire");
        Wire.addActionListener(this);        


        JMenuItem PoliceStation = new JMenuItem("Police Station");
        PoliceStation.addActionListener(this);        
        JMenuItem FireStation = new JMenuItem("Fire Station");
        FireStation.addActionListener(this);  
        
        JMenuItem Stadium = new JMenuItem("Stadium");
        Stadium.addActionListener(this); 
        JMenuItem Airport = new JMenuItem("Airport");
        Airport.addActionListener(this);  
        JMenuItem Seaport = new JMenuItem("Seaport");
        Seaport.addActionListener(this);  
 
        JMenuItem Road = new JMenuItem("Road");
        Road.addActionListener(this);
        JMenuItem Rail = new JMenuItem("Rail");
        Rail.addActionListener(this);
        JMenuItem Park = new JMenuItem("Park");
        Park.addActionListener(this);        


        JMenuItem Demolish = new JMenuItem("Demolish");        
        Demolish.addActionListener(this);
        
        
        myMenu.add(RCI);
            RCI.add(Residence);
            RCI.add(Comercial);
            RCI.add(Industrial);        
        myMenu.add(Electricity);         
            Electricity.add(CoalPowerPlant);
            Electricity.add(NuclearPowerPlant);    
        myMenu.add(Services);         
            Services.add(PoliceStation);
            Services.add(FireStation);           
        myMenu.add(Turism);
            Turism.add(Stadium);
            Turism.add(Airport);
            Turism.add(Seaport);  
        myMenu.add(Others);
            Others.add(Road);
            Others.add(Rail);
            Others.add(Park);
            Others.add(Wire);
            Others.add(Demolish);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == BtnConstruir){
            newView.Build();
            newView.requestFocusInWindow();
            return;
        }

        if(e.getSource() == BtnDesruir){
            newView.Destroy();
            newView.requestFocusInWindow();
            return;
        }
        
        String CMD = ((JMenuItem)e.getSource()).getText(); 
        if(CMD == "Demolish"){
            BtnConstruir.setVisible(false);
            BtnDesruir.setVisible(true);
            newView.setBuildGrid("X",1,Color.red);
            return;
        }
           BtnConstruir.setVisible(true);
            BtnDesruir.setVisible(false);      
        
        switch(CMD){
            case "Residence":newView.setBuildGrid("R",3,Color.red); break;
            case "Comercial":newView.setBuildGrid("C",3,Color.BLUE); break;
            case "Industrial":newView.setBuildGrid("I",3,Color.yellow); break;
            case "Coal Power Plant":newView.setBuildGrid("CPP",4,Color.white); break;
            case "Nuclear Power Plant":newView.setBuildGrid("NPP",4,Color.white); break;
            case "Wire":newView.setBuildGrid("W",1,Color.white); break;
            case "Police Station":newView.setBuildGrid("PS",3,Color.cyan); break;            
            case "Fire Station":newView.setBuildGrid("FS",3,Color.orange); break;            
            case "Stadium":newView.setBuildGrid("S",4,Color.magenta); break;            
            case "Airport":newView.setBuildGrid("AP",4,Color.magenta); break;
            case "Seaport":newView.setBuildGrid("SP",4,Color.magenta); break;            
            case "Road":newView.setBuildGrid("R",1,Color.black); break; 
            case "Rail":newView.setBuildGrid("R",1,Color.darkGray); break; 
            case "Park":newView.setBuildGrid("P",1,Color.GREEN); break; 
        }
    }
}
