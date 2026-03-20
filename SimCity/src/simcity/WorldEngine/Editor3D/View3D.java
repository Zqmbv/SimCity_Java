package simcity.WorldEngine.Editor3D;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;

import javax.media.j3d.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.SimpleUniverse;
import javax.vecmath.*;

public class View3D extends JPanel implements KeyListener, MouseMotionListener {

    GraphicsConfiguration Config = SimpleUniverse.getPreferredConfiguration();
    Canvas3D canvas = new Canvas3D(Config);
    SimpleUniverse SU = new SimpleUniverse(canvas);
    BranchGroup newScene = Scene3D();    
    
    TransformGroup Camera = SU.getViewingPlatform().getViewPlatformTransform();
    Vector3f Pos = new Vector3f();
    Vector2f LastAngle = new Vector2f();
    Vector2f Angle = new Vector2f(0,0);    
    float sensibility = 0.005f;     
    float velocity = 0.01f;
    
    public View3D(){
        this.setLayout(new BorderLayout());
        this.add(canvas,BorderLayout.CENTER);
        canvas.addKeyListener(this);
        canvas.addMouseMotionListener(this);
        
        SU.getViewingPlatform().setNominalViewingTransform();

        newScene.compile();
        SU.addBranchGraph(newScene);
        
        //Init Camera
        Transform3D Camera_T = new Transform3D();
        Camera.getTransform(Camera_T);
        Camera_T.get(Pos);   
    }
    
    public BranchGroup Scene3D(){
        BranchGroup root = new BranchGroup();
        
        Color3f colorFondo = new Color3f(0.255f, 0.812f, 0.969f);
        Background bg = new Background(colorFondo);
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 1000.0);
        bg.setApplicationBounds(bounds);
        
        
        root.addChild(new ColorCube(0.1f));
        root.addChild(bg);
        
        return root;
    }
    
    public void AddModel(BranchGroup Obj,float x,float y){
        Transform3D Obj_Pos = new Transform3D();
        Obj_Pos.setTranslation(new Vector3f(x,0,y));
        TransformGroup Obj_T = new TransformGroup(Obj_Pos);
        
        Obj_T.addChild(Obj);
        newScene.addChild(Obj_T);
    }
    
   public void CamUpdate(){
        Transform3D Cam_T = new Transform3D();
        Transform3D rotX = new Transform3D();
        Transform3D rotY = new Transform3D();

        rotX.rotY(Angle.y);
        rotY.rotX(Angle.x);

        Cam_T.mul(rotX);
        Cam_T.mul(rotY);
    
        Cam_T.setTranslation(Pos);
        Camera.setTransform(Cam_T);   
   }

    @Override
    public void keyPressed(KeyEvent e) {    
        Transform3D Camera_T = new Transform3D();
        Camera.getTransform(Camera_T);
        Matrix4f matriz = new Matrix4f();
        Camera_T.get(matriz);    
        
        if(e.getKeyCode()==KeyEvent.VK_A || e.getKeyCode()==KeyEvent.VK_D){
            Vector3f right = new Vector3f(matriz.m00, matriz.m10, matriz.m20);
            right.normalize();        
            
            if(e.getKeyCode()==KeyEvent.VK_A){right = new Vector3f(right.x*-velocity,right.y*velocity,right.z*-velocity);}
            else{right = new Vector3f(right.x*velocity,right.y*-velocity,right.z*velocity);}
            
            Pos = new Vector3f(Pos.x + right.x, Pos.y + right.y, Pos.z + right.z);
        }
        
        if(e.getKeyCode()==KeyEvent.VK_W || e.getKeyCode()==KeyEvent.VK_S){
            Vector3f forward = new Vector3f(-matriz.m02, -matriz.m12, -matriz.m22);
            forward.normalize();      
            
            if(e.getKeyCode()==KeyEvent.VK_W){forward = new Vector3f(forward.x*velocity,forward.y*velocity,forward.z*velocity);}
            else{forward = new Vector3f(forward.x*-velocity,forward.y*-velocity,forward.z*-velocity);}
            
            Pos = new Vector3f(Pos.x + forward.x, Pos.y + forward.y, Pos.z + forward.z);
        }        
        
        CamUpdate();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        double dx = e.getX() - LastAngle.y;
        double dy = e.getY() - LastAngle.x;

        Angle.y -= dx * sensibility;
        Angle.x -= dy * sensibility;

        CamUpdate();
        
        LastAngle.y = e.getX();
        LastAngle.x = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        LastAngle.y = e.getX();
        LastAngle.x = e.getY();
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    
    @Override
    public void keyTyped(KeyEvent e) {}
}
