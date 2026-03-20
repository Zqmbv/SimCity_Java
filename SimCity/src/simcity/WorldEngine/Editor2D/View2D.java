package simcity.WorldEngine.Editor2D;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class View2D extends JPanel implements KeyListener {
    
    final int MAP_WIDTH = 100, MAP_HEIGHT = 100, MAP_GRIDSIZE=16;
    
    int X=0, Y=0;   
    int CenterX = Math.round((this.getWidth()/2)/MAP_GRIDSIZE)*MAP_GRIDSIZE;
    int CenterY = Math.round((this.getHeight()/2)/MAP_GRIDSIZE)*MAP_GRIDSIZE;
        
    class Build{
        String tag;
        int x,y,size = 0;
        Color color;
    }
    
    Build GridBuild = new Build();
    ArrayList<Build> builds = new ArrayList<>();
  
    public View2D(){
        this.setFocusable(true);
        this.setBackground(Color.gray);
        this.addKeyListener(this);
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D)g;
        g2D.translate(X, Y);
        
        //LIMITS
        g2D.drawRect(0, 0, MAP_WIDTH*MAP_GRIDSIZE, MAP_HEIGHT*MAP_GRIDSIZE);
        
        //BUILD HECHAS
        for(Build b : builds){
            g2D.setColor(b.color);
            g2D.setStroke(new BasicStroke(2f));
            g2D.drawRect(b.x,b.y, b.size, b.size);
            g2D.drawString(b.tag, b.x + 3,b.y+b.size-3);
        }        
        
        //GRILLA CENTRAL
        g2D.translate(-X, -Y);
        if(GridBuild.size != 0){
            g2D.setStroke(new BasicStroke(1f));
            g2D.setColor(GridBuild.color);
            
            CenterX = Math.round((this.getWidth()/2)/MAP_GRIDSIZE)*MAP_GRIDSIZE;
            CenterY = Math.round((this.getHeight()/2)/MAP_GRIDSIZE)*MAP_GRIDSIZE;
        
            g2D.drawRect(CenterX, CenterY, GridBuild.size, GridBuild.size);  
            g2D.drawString(GridBuild.tag, CenterX + 3,CenterY+GridBuild.size-3);
        }
    }
    public void setBuildGrid(String tag, int size, Color color){
        GridBuild.tag = tag;
        GridBuild.size = size*MAP_GRIDSIZE;
        GridBuild.color = color;
        repaint(); 
    }
    
    public void Build(){
        if(GridBuild.size == 0){
            JOptionPane.showMessageDialog(this, "ERROR - NO SELECCIONÓ UNA ESTRUCTURA");
            return;}
        
        Rectangle newBuildCollider = new Rectangle(CenterX-X,CenterY-Y,GridBuild.size, GridBuild.size);
        for(Build b : builds){
            //Wire sobre road y rail
            //revisar road sobre rail
            if(GridBuild.tag == "W" && (b.tag == "R" || b.tag == "P")){continue;}
            
            Rectangle oldBuildCollider = new Rectangle(b.x,b.y, b.size, b.size);
            if(newBuildCollider.intersects(oldBuildCollider)){
                JOptionPane.showMessageDialog(this, "ERROR - LA ESTRUCTURA COLISIONO CON OTRA");
                return;
            }
        }         
        
        
        Build newBuild = new Build();
        newBuild.x = CenterX-X;
        newBuild.y = CenterY-Y;
        newBuild.tag = GridBuild.tag;
        newBuild.size = GridBuild.size;
        newBuild.color = GridBuild.color;
        
        builds.add(newBuild);
        repaint();
    }
 
    public void Destroy(){
        Rectangle DestroyCollider = new Rectangle(CenterX-X,CenterY-Y,GridBuild.size, GridBuild.size);
        for(int i = 0 ; i < builds.size(); i++){
            Rectangle BuildCollider = new Rectangle(builds.get(i).x,builds.get(i).y, builds.get(i).size, builds.get(i).size);
            if(DestroyCollider.intersects(BuildCollider)){
                builds.remove(builds.get(i));
            }
        }         

        repaint();
    }
    
    @Override
    public void keyPressed(KeyEvent e) { 
        int code = e.getKeyCode();
        
        int tempx = X, tempy = Y;
        if(code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A){X+=MAP_GRIDSIZE;}    
        if(code == KeyEvent.VK_UP   || code == KeyEvent.VK_W){Y+=MAP_GRIDSIZE;}
        if(code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S){Y-=MAP_GRIDSIZE;}
        if(code == KeyEvent.VK_RIGHT|| code == KeyEvent.VK_D){X-=MAP_GRIDSIZE;} 
        
        Rectangle Border = new Rectangle(0,0, MAP_WIDTH*MAP_GRIDSIZE, MAP_HEIGHT*MAP_GRIDSIZE);
        Rectangle Cursor = new Rectangle(CenterX-X,CenterY-Y, 1, 1);
        
        if(!Border.intersects(Cursor)){X= tempx; Y=tempy;}
        
        repaint();       
    }

    @Override
    public void keyTyped(KeyEvent e) {}    
    
    @Override
    public void keyReleased(KeyEvent e) {}
}
