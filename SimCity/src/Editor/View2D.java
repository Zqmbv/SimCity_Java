package Editor;
import Editor.*;
import Editor.Prefabs.*;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import javax.vecmath.Vector2f;

public class View2D extends JPanel implements KeyListener {
    
    int Zdepth = 16;
    
    Vector2f Move = new Vector2f(0,0);
    Vector2f Center= new Vector2f(0,0);
    
    public View2D(){
        this.setFocusable(true);
        this.setBackground(new Color(0.522f, 0.333f, 0.184f));
        this.addKeyListener(this);
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D)g;
        
        Center = new Vector2f(this.getWidth()/2,this.getHeight()/2);        
        g2D.translate(Center.x-Move.x*Zdepth,Center.y-Move.y*Zdepth);
        
        //LIMITS
        g2D.setColor(Color.white);
        g2D.drawRect(0, 0, Inspector.MAP_WIDTH*Zdepth, Inspector.MAP_HEIGHT*Zdepth);

        //BUILD HECHAS
        for(Inspector.Tile T : Inspector.Tiles2D){
            g2D.setColor(T.struct.color);
            g2D.setStroke(new BasicStroke(2f));
            g2D.drawRect(T.x*Zdepth,T.y*Zdepth, T.struct.size*Zdepth, T.struct.size*Zdepth);
            g2D.drawString(T.struct.tag, T.x*Zdepth + 3,(T.y+T.struct.size)*Zdepth -3);
        }  
        
        //CURSO
        if(Inspector.Cursor.struct.size != 0){
            g2D.setColor(Inspector.Cursor.struct.color);
            g2D.drawRect(Inspector.Cursor.x*Zdepth,Inspector.Cursor.y*Zdepth, Inspector.Cursor.struct.size*Zdepth, Inspector.Cursor.struct.size*Zdepth);
            g2D.drawString(Inspector.Cursor.struct.tag, Move.x*Zdepth + 3,(Move.y+Inspector.Cursor.struct.size)*Zdepth-3);
        }  
        this.requestFocus();
    }
 
    @Override
    public void keyPressed(KeyEvent e) { 
        int code = e.getKeyCode();
        
        Vector2f temp = new Vector2f(Move.x,Move.y);
        if(code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A){Move.x--;}    
        if(code == KeyEvent.VK_UP   || code == KeyEvent.VK_W){Move.y--;}
        if(code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S){Move.y++;}
        if(code == KeyEvent.VK_RIGHT|| code == KeyEvent.VK_D){Move.x++;} 
        
        Rectangle Border = new Rectangle(0, 0, Inspector.MAP_WIDTH-Inspector.Cursor.struct.size+1, Inspector.MAP_HEIGHT-Inspector.Cursor.struct.size+1);
        Rectangle Cursor = new Rectangle((int)Move.x,(int)Move.y, 1,1);
        
        if(!Border.intersects(Cursor)){Move.x= temp.x; Move.y=temp.y;}
        Inspector.Cursor.x = (int) Move.x;Inspector.Cursor.y = (int) Move.y;
        
        System.out.println(Move.toString());
        repaint();       
    }

    @Override
    public void keyTyped(KeyEvent e) {}    
    
    @Override
    public void keyReleased(KeyEvent e) {}
}
