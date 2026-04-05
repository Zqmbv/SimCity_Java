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
    Vector3f Pos = new Vector3f(1.5f,20f,1.5f);
    Vector2f LastAngle = new Vector2f();
    Vector2f Angle = new Vector2f((float)Math.toRadians(-90),0);    
    float sensibility = 0.005f;     
    float velocity = 1f;
    
    public View3D(){
        this.setLayout(new BorderLayout());
        this.add(canvas,BorderLayout.CENTER);
        canvas.addKeyListener(this);
        canvas.addMouseMotionListener(this);
        
        SU.getViewingPlatform().setNominalViewingTransform();
        BranchGroup newScene = Scene3D(); 
        newScene.compile();
        SU.addBranchGraph(newScene);
        
        CamUpdate(Pos, Angle);
    }
    
    public BranchGroup Scene3D(){
        BranchGroup root = new BranchGroup();
        root.setCapability(Group.ALLOW_CHILDREN_EXTEND);
        root.setCapability(Group.ALLOW_CHILDREN_WRITE);
        
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
    
    
    BranchGroup AddModel(TransformGroup Obj, float x, float y,float O){
        System.out.println(x+" "+y);
        
        BranchGroup wrapper = new BranchGroup();
        wrapper.setCapability(BranchGroup.ALLOW_DETACH);
        wrapper.setCapability(Group.ALLOW_CHILDREN_READ);
        wrapper.setCapability(Group.ALLOW_CHILDREN_WRITE);
        
        Transform3D Rot = new Transform3D();
        Transform3D Move = new Transform3D();
        Rot.rotY((float)Math.toRadians(O));
        Move.setTranslation(new Vector3f(x*2,0,y*2));
        Move.mul(Rot);
        
        TransformGroup T = new TransformGroup(Move);
        T.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        T.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);       
        
        wrapper.addChild(T);T.addChild(Obj);
        
        SU.addBranchGraph(wrapper);
        return wrapper;
    }
    
    public void RemoveModel(BranchGroup wrapper){
        if (wrapper != null) {
            wrapper.detach(); 
        }
    } 
    
    public void rotarHijoInterno(BranchGroup wrapper, double anguloRad) {
        TransformGroup tgInterno = (TransformGroup) wrapper.getChild(0);

        Transform3D t3d = new Transform3D();
        tgInterno.getTransform(t3d);

        Transform3D nuevaRot = new Transform3D();
        nuevaRot.rotY((float)Math.toRadians(anguloRad));

        t3d.mul(nuevaRot); 
        tgInterno.setTransform(t3d);
    }
     
   public void CamUpdate(Vector3f POS, Vector2f ROT){
        Transform3D Cam_T = new Transform3D();
        Transform3D rotX = new Transform3D();
        Transform3D rotY = new Transform3D();

        rotX.rotY(ROT.y);
        rotY.rotX(ROT.x);

        Cam_T.mul(rotX);
        Cam_T.mul(rotY);
    
        Cam_T.setTranslation(POS);
        Camera.setTransform(Cam_T);
        Pos = POS;
        Angle = ROT;
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
        
        CamUpdate(Pos,Angle);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        double dx = e.getX() - LastAngle.y;
        double dy = e.getY() - LastAngle.x;

        Angle.y -= dx * sensibility;
        Angle.x -= dy * sensibility;

        CamUpdate(Pos,Angle);
        
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
