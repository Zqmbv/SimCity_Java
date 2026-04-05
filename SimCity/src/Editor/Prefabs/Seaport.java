package Editor.Prefabs;
import javax.media.j3d.*;
import com.sun.j3d.utils.geometry.*;
import java.awt.Color;
import javax.vecmath.*;

public class Seaport  extends TransformGroup{
    public Seaport(){
        Transform3D Pivot = new  Transform3D();
       // Pivot.setTranslation(new Vector3f(4f,0,4f));
        this.setTransform(Pivot);
        
        Appearance app = new Appearance();
        Material mat = new Material();
        mat.setDiffuseColor(new Color3f(Color.magenta));
        app.setMaterial(mat);
    
        CuboT(new Vector3f(2,0,0),new Vector3d(2,2,2),2);
        CuboT(new Vector3f(0,0,0),new Vector3d(3,2,3),3);
        
        Appearance Starapp = new Appearance();
        Material Starmat = new Material();
        Starmat.setDiffuseColor(new Color3f(Color.yellow));
        Starapp.setMaterial(Starmat); 
        
        Transform3D Pivot_Move = new Transform3D();
        Pivot_Move.setTranslation(new Vector3f(0,6,0));
        Pivot_Move.setScale(new Vector3d(0.5,0.5,0.5));
        TransformGroup Pivot_T = new TransformGroup(Pivot_Move);       
 
        TransformGroup ROT = new TransformGroup(); 
        ROT.setCapability(ALLOW_TRANSFORM_WRITE);

        
        Transform3D  TuboA_Move = new Transform3D();
        TuboA_Move.setTranslation(new Vector3f(0,-0.25f,0));
        TransformGroup TuboA_T = new TransformGroup(TuboA_Move);
        Box TuboA = new Box(0.5f,3f,0.5f,Starapp);        
        

        Transform3D  TuboD_Move = new Transform3D();
        TuboD_Move.setTranslation(new Vector3f(0,-2,0));
        TransformGroup TuboD_T = new TransformGroup(TuboD_Move);
        Box TuboD = new Box(2,0.5f,0.5f,Starapp);

        Transform3D  TuboE_Move = new Transform3D();
        TuboE_Move.setTranslation(new Vector3f(-1.75f,-1,0));
        TransformGroup TuboE_T = new TransformGroup(TuboE_Move);
        Box TuboE = new Box(0.5f,0.75f,0.5f,Starapp);

        Transform3D  TuboF_Move = new Transform3D();
        TuboF_Move.setTranslation(new Vector3f(1.75f,-1,0));
        TransformGroup TuboF_T = new TransformGroup(TuboF_Move);
        Box TuboF = new Box(0.5f,0.75f,0.5f,Starapp);
        
        Alpha timer = new Alpha(-1,10000);
        RotationInterpolator RI = new RotationInterpolator(timer,ROT);
        RI.setSchedulingBounds(new BoundingSphere());
        
        this.addChild(Pivot_T);
            Pivot_T.addChild(ROT);ROT.addChild(RI);
                ROT.addChild(TuboA_T);TuboA_T.addChild(TuboA);    
                ROT.addChild(TuboD_T);TuboD_T.addChild(TuboD);
                ROT.addChild(TuboE_T);TuboE_T.addChild(TuboE);
                ROT.addChild(TuboF_T);TuboF_T.addChild(TuboF);
        
        Box Road = new Box(4,0.1f,4,app);
        this.addChild(Road);
    
    }
    
    public void CuboT(Vector3f POS,Vector3d SCA, int N){
        Appearance TechoApp = new Appearance();
        Material TechoMat = new Material();
        TechoMat.setDiffuseColor(new Color3f(Color.cyan));
        TechoApp.setMaterial(TechoMat); 
        
        Appearance ParedApp = new Appearance();
        Material ParedMat = new Material();
        ParedMat.setDiffuseColor(new Color3f(Color.white));
        ParedApp.setMaterial(ParedMat); 
        
        for(int i = 0; i < N; i++){
            Transform3D T_Move = new Transform3D();
            T_Move.setTranslation(new Vector3f(POS.x,i,POS.z));
            T_Move.setScale(SCA);
            TransformGroup T = new TransformGroup(T_Move);

            Transform3D Pared_Move = new Transform3D();
            Pared_Move.setTranslation(new Vector3f(0,0.55f,0));
            TransformGroup Pared_T = new TransformGroup(Pared_Move);
            Box Pared = new Box(0.9f,0.5f,0.9f,ParedApp);

            Transform3D Techo_Move = new Transform3D();
            Techo_Move.setTranslation(new Vector3f(0,1,0));
            TransformGroup Techo_T = new TransformGroup(Techo_Move);
            Box Techo = new Box(1f,0.1f,1f,TechoApp);        

            this.addChild(T);
                T.addChild(Pared_T);Pared_T.addChild(Pared);
                T.addChild(Techo_T);Techo_T.addChild(Techo);
        }
    }
}
