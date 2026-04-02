package Editor.Prefabs;
import javax.media.j3d.*;
import com.sun.j3d.utils.geometry.*;
import java.awt.Color;
import javax.vecmath.*;

public class NuclearPowerPlant extends TransformGroup  {
     
    public NuclearPowerPlant(){
        Transform3D Pivot = new  Transform3D();
        Pivot.setTranslation(new Vector3f(4f,0,4f));
        this.setTransform(Pivot);   
        
        Appearance app = new Appearance();
        Material mat = new Material();
        mat.setDiffuseColor(new Color3f(Color.white));
        app.setMaterial(mat);    
        
        Appearance TechoApp = new Appearance();
        Material TechoMat = new Material();
        TechoMat.setDiffuseColor(new Color3f(Color.DARK_GRAY));
        TechoApp.setMaterial(TechoMat); 
        
        Appearance ParedApp = new Appearance();
        Material ParedMat = new Material();
        ParedMat.setDiffuseColor(new Color3f(Color.gray));
        ParedApp.setMaterial(ParedMat);  
        
        Appearance TuboApp = new Appearance();
        Material TuboMat = new Material();
        TuboMat.setDiffuseColor(new Color3f(Color.red));
        TuboApp.setMaterial(TuboMat);   
        

        CuboT(new Vector3f(-2,0,2),new Vector3d(0.75f,1,0.75f) , 2);
        CuboT(new Vector3f(-2,0,0),new Vector3d(0.75f,1,0.75f) , 2);
        CuboT(new Vector3f(1.25f,0,1),new Vector3d(2f,1,2) ,3);
        Tub(new Vector3f(1,0,-2));
        Tub(new Vector3f(-2,0,-2));        
         
        Transform3D ParedN_Move = new Transform3D();
        ParedN_Move.setTranslation(new Vector3f(0,0.5f,3.5f));
        TransformGroup ParedN_T = new TransformGroup(ParedN_Move);
        Box ParedN = new Box(3.5f,0.5f,0.1f,TechoApp);
        
        Transform3D  ParedS_Move = new Transform3D();
        ParedS_Move.setTranslation(new Vector3f(0,0.5f,-3.5f));
        TransformGroup ParedS_T = new TransformGroup(ParedS_Move);
        Box ParedS = new Box(3.5f,0.5f,0.1f,TechoApp);        
        
        Transform3D ParedE_Move = new Transform3D();
        ParedE_Move.setTranslation(new Vector3f(3.5f,0.5f,0));
        TransformGroup ParedE_T = new TransformGroup(ParedE_Move);
        Box ParedE = new Box(0.1f,0.5f,3.5f,TechoApp);
        
        Transform3D  ParedW_Move = new Transform3D();
        ParedW_Move.setTranslation(new Vector3f(-3.5f,0.5f,0));
        TransformGroup ParedW_T = new TransformGroup(ParedW_Move);
        Box ParedW = new Box(0.1f,0.5f,3.5f,TechoApp);  
        
        Box Suelo = new Box(4,0.1f,4,app);
        this.addChild(Suelo);    
        this.addChild(ParedN_T);ParedN_T.addChild(ParedN);
        this.addChild(ParedS_T);ParedS_T.addChild(ParedS);   
        this.addChild(ParedW_T);ParedW_T.addChild(ParedW);
        this.addChild(ParedE_T);ParedE_T.addChild(ParedE);    
    
    }
    
    public void Tub(Vector3f POS){
        Appearance app = new Appearance();
        Material mat = new Material();
        mat.setDiffuseColor(new Color3f(Color.white));
        app.setMaterial(mat);  
        
        Transform3D  TuboA_Move = new Transform3D();
        TuboA_Move.setTranslation(new Vector3f(POS.x,2f,POS.z));
        TransformGroup TuboA_T = new TransformGroup(TuboA_Move);
        Cone TuboA = new Cone(1.5f,4f,app);        
        this.addChild(TuboA_T);TuboA_T.addChild(TuboA);    
       
        Transform3D  TuboB_Rot = new Transform3D();
        TuboB_Rot.rotX((float)Math.toRadians(180));
        Transform3D  TuboB_Move = new Transform3D();
        TuboB_Move.mul(TuboB_Rot);
        TuboB_Move.setTranslation(new Vector3f(POS.x,2f,POS.z));
        TransformGroup TuboB_T = new TransformGroup(TuboB_Move);
        Cone TuboB = new Cone(1.5f,4f,app);        
        this.addChild(TuboB_T);TuboB_T.addChild(TuboB);   
    }
    
    public void CuboT(Vector3f POS,Vector3d SCA, int N){
        Appearance TechoApp = new Appearance();
        Material TechoMat = new Material();
        TechoMat.setDiffuseColor(new Color3f(Color.DARK_GRAY));
        TechoApp.setMaterial(TechoMat); 
        
        Appearance ParedApp = new Appearance();
        Material ParedMat = new Material();
        ParedMat.setDiffuseColor(new Color3f(Color.gray));
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
