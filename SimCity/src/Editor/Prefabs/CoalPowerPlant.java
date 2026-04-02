package Editor.Prefabs;
import javax.media.j3d.*;
import com.sun.j3d.utils.geometry.*;
import java.awt.Color;
import javax.vecmath.*;

public class CoalPowerPlant extends TransformGroup {
    
    public CoalPowerPlant(){
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
        
        
        for(int i = 0; i < 4; i++){
            Transform3D T_Move = new Transform3D();
            T_Move.setTranslation(new Vector3f(-2f,i,-2.5f));
            T_Move.setScale(new Vector3d(1.5f,1,0.75f));
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

        for(int i = 0; i < 2; i++){
            Transform3D T_Move = new Transform3D();
            T_Move.setTranslation(new Vector3f(2f,i,-2f));
            T_Move.setScale(new Vector3d(1.25,1,1.25f));
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
        

        for(int i = 0; i < 1; i++){
            Transform3D T_Move = new Transform3D();
            T_Move.setTranslation(new Vector3f(0f,i,1.5f));
            T_Move.setScale(new Vector3d(3f,1,1.5f));
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
        
  
        TuboT(new Vector3f(1.5f,0,1.5f));
        TuboT(new Vector3f(0.5f,0,1.5f));
        TuboT(new Vector3f(-0.5f,0,1.5f));
        TuboT(new Vector3f(-1.5f,0,1.5f));
        
        Transform3D ParedN_Move = new Transform3D();
        ParedN_Move.setTranslation(new Vector3f(0,0.5f,3.5f));
        TransformGroup ParedN_T = new TransformGroup(ParedN_Move);
        Box ParedN = new Box(3.5f,0.5f,0.1f,ParedApp);
        
        Transform3D  ParedS_Move = new Transform3D();
        ParedS_Move.setTranslation(new Vector3f(0,0.5f,-3.5f));
        TransformGroup ParedS_T = new TransformGroup(ParedS_Move);
        Box ParedS = new Box(3.5f,0.5f,0.1f,ParedApp);        
        
        Transform3D ParedE_Move = new Transform3D();
        ParedE_Move.setTranslation(new Vector3f(3.5f,0.5f,0));
        TransformGroup ParedE_T = new TransformGroup(ParedE_Move);
        Box ParedE = new Box(0.1f,0.5f,3.5f,ParedApp);
        
        Transform3D  ParedW_Move = new Transform3D();
        ParedW_Move.setTranslation(new Vector3f(-3.5f,0.5f,0));
        TransformGroup ParedW_T = new TransformGroup(ParedW_Move);
        Box ParedW = new Box(0.1f,0.5f,3.5f,ParedApp);  
        
        Box Suelo = new Box(4,0.1f,4,app);
        this.addChild(Suelo);    
        this.addChild(ParedN_T);ParedN_T.addChild(ParedN);
        this.addChild(ParedS_T);ParedS_T.addChild(ParedS);   
        this.addChild(ParedW_T);ParedW_T.addChild(ParedW);
        this.addChild(ParedE_T);ParedE_T.addChild(ParedE);   
    }
    
    public void TuboT(Vector3f POS){
        Appearance app = new Appearance();
        Material mat = new Material();
        mat.setDiffuseColor(new Color3f(Color.white));
        app.setMaterial(mat); 
        
        Appearance TuboApp = new Appearance();
        Material TuboMat = new Material();
        TuboMat.setDiffuseColor(new Color3f(Color.red));
        TuboApp.setMaterial(TuboMat);          

        Transform3D T_Move = new Transform3D();
        T_Move.setTranslation(POS);
        T_Move.setScale(new Vector3d(1f,1,1f));
        TransformGroup T = new TransformGroup(T_Move);

        Transform3D Tubo_Move = new Transform3D();
        Tubo_Move.setTranslation(new Vector3f(0,2f,0));
        TransformGroup Tubo_T = new TransformGroup(Tubo_Move);
        Cylinder Tubo = new Cylinder(0.2f,4f,TuboApp);

        this.addChild(T);
            T.addChild(Tubo_T);Tubo_T.addChild(Tubo);


        for(int j = -5; j < 5; j++){
            Transform3D T_Move2 = new Transform3D();
            T_Move2.setTranslation(new Vector3f(0f,j*0.5f,0));
            T_Move2.setScale(new Vector3d(1f,1,1f));
            TransformGroup T2 = new TransformGroup(T_Move2);

            Transform3D Tubo_Move2 = new Transform3D();
            Tubo_Move2.setTranslation(new Vector3f(0,2f,0));
            TransformGroup Tubo_T2 = new TransformGroup(Tubo_Move);
            Cylinder Tubo2 = new Cylinder(0.3f,0.1f,app);

            T.addChild(T2);
                T2.addChild(Tubo_T2);Tubo_T2.addChild(Tubo2);
        }    
    }
}
