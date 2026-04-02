package Editor;
import Editor.*;
import Editor.Prefabs.*;

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
    
    TransformGroup Camera = SU.getViewingPlatform().getViewPlatformTransform();
    Vector3f Pos = new Vector3f();
    Vector2f LastAngle = new Vector2f();
    Vector2f Angle = new Vector2f(0,0);    
    float sensibility = 0.005f;     
    float velocity = 0.1f;
    
    public View3D(){
        this.setLayout(new BorderLayout());
        this.add(canvas,BorderLayout.CENTER);
        canvas.addKeyListener(this);
        canvas.addMouseMotionListener(this);
        
        SU.getViewingPlatform().setNominalViewingTransform();
        BranchGroup newScene = Scene3D(); 
        newScene.compile();
        SU.addBranchGraph(newScene);
        
        //Init Camera
        Transform3D Camera_T = new Transform3D();
        Camera.getTransform(Camera_T);
        Camera_T.get(Pos); 
    
        AddModel(new Residencial(),6,8); 
        AddModel(new Comercial(),9,8); 
        AddModel(new Industrial(),12,8);
        
        AddModel(new CoalPowerPlant(),6,4); 
        AddModel(new NuclearPowerPlant(),10,4);         
        
        AddModel(new PoliceStation(),0,6); 
        AddModel(new FireStation(),3,6);          

        AddModel(new Airport(),0,0); 
        AddModel(new Seaport(),6,0); 
        AddModel(new Stadium(),10,0);
        
        AddModel(new Road(),0,9); 
        AddModel(new Wire(),1,9); 
        AddModel(new Park(),2,9);
        AddModel(new Rail(),3,9);         
    }
    
    public BranchGroup Scene3D(){
        BranchGroup root = new BranchGroup();

        DirectionalLight Light = new DirectionalLight(new Color3f(Color.white),new Vector3f(0.5f,-0.5f,-0.5f));
        Light.setInfluencingBounds(new BoundingSphere(new Point3d(0,0,0), 1000.0));
        
        Background bg = new Background(new Color3f(0.255f, 0.812f, 0.969f));
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 1000.0);
        bg.setApplicationBounds(bounds);
        
        Appearance appFloor = new Appearance();
        Material matFloor = new Material();
        matFloor.setDiffuseColor(new Color3f(0.722f, 0.533f, 0.384f));
        appFloor.setMaterial(matFloor);
        
        Appearance appBase = new Appearance();
        Material matBase = new Material();
        matBase.setDiffuseColor(new Color3f(Color.white));
        appBase.setMaterial(matBase);        
        
        Transform3D Move  = new Transform3D();
        Move.setTranslation(new Vector3f(Inspector.MAP_WIDTH,0,Inspector.MAP_HEIGHT));
        TransformGroup T = new TransformGroup(Move);

        Transform3D BaseMove  = new Transform3D();
        BaseMove.setTranslation(new Vector3f(0,-0.1f,0));
        TransformGroup BaseT = new TransformGroup(BaseMove);        
        Box Floor  = new Box(Inspector.MAP_WIDTH,0.05f,Inspector.MAP_HEIGHT,appFloor);
        
        Box Base  = new Box(Inspector.MAP_WIDTH+0.1f,0.05f,Inspector.MAP_HEIGHT+0.1f,appBase);                
        
        root.addChild(bg);
        root.addChild(Light);
        root.addChild(T);
            T.addChild(BaseT);BaseT.addChild(Base);
            T.addChild(Floor);        

        return root;
    }
    
    
    public void AddModel(TransformGroup Obj, float x, float y){
        BranchGroup newBG = new BranchGroup();
        
        Transform3D Move = new Transform3D();
        Move.setTranslation(new Vector3f(x*2,0,y*2));
        TransformGroup T = new TransformGroup(Move);
            newBG.addChild(T);T.addChild(Obj);
        
        newBG.compile();
        SU.addBranchGraph(newBG);
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
