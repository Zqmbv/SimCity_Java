package Editor.Prefabs;
import javax.media.j3d.*;
import com.sun.j3d.utils.geometry.*;
import java.awt.Color;
import javax.vecmath.*;

public class Industrial extends TransformGroup  {
    public Industrial(){
        Transform3D Pivot = new  Transform3D();
        //Pivot.setTranslation(new Vector3f(3f,0,3f));
        this.setTransform(Pivot); 

        Appearance app = new Appearance();
        Material mat = new Material();
        mat.setDiffuseColor(new Color3f(Color.yellow));
        app.setMaterial(mat);
        
        Pereza(new Vector3f (-1.5f,0,0),new Vector3d (1,1,1), 0);
        Pereza(new Vector3f (-1.5f,0f,-1),new Vector3d (1,2,1.5), 0);
        Pereza(new Vector3f (0,0,1),new Vector3d (2,1,1), 90);
        
        Pereza2(new Vector3f (0,0,0),new Vector3d (1,1,1), 0);
        Pereza2(new Vector3f (0.75f,0,0),new Vector3d (1,1,1), 0);        
        Pereza2(new Vector3f (1.5f,0,0),new Vector3d (1,1,1), 0);
        
        Appearance Paredapp = new Appearance();
        Material ParedMat = new Material();
        ParedMat.setDiffuseColor(new Color3f(Color.darkGray));
        Paredapp.setMaterial(ParedMat);

           
        
        
        Box Suelo = new Box(3f,0.1f,3f,app);
        this.addChild(Suelo);        
    }
    
    void Pereza(Vector3f Pos, Vector3d Sca, float Rot){
        Appearance Paredapp = new Appearance();
        Material ParedMat = new Material();
        ParedMat.setDiffuseColor(new Color3f(Color.darkGray));
        Paredapp.setMaterial(ParedMat);

        Transform3D T_RotY = new Transform3D();
        Transform3D T_Move = new Transform3D();
        T_Move.setScale(new Vector3d(Sca));
        T_RotY.rotY((float)Math.toRadians(Rot));
        T_Move.mul(T_RotY);
        T_Move.setTranslation(new Vector3f(Pos));
        TransformGroup T = new TransformGroup(T_Move);

        Transform3D Pare_Move = new Transform3D();
        Pare_Move.setTranslation(new Vector3f(0,0.5f,0));
        TransformGroup Pared_T = new TransformGroup(Pare_Move);        
        Box Pared = new Box(1f,0.5f,1f,Paredapp);

        Transform3D Techo_Move = new Transform3D();
        Transform3D Techo_RotX = new Transform3D();
        Techo_RotX.rotX((float)Math.toRadians(90));
        Techo_Move.mul(Techo_RotX);
        Techo_Move.setTranslation(new Vector3f(0,0.75f,0));
        Techo_Move.setScale(new Vector3d(1,1,0.5));
        TransformGroup Techo_T = new TransformGroup(Techo_Move);        
        Cylinder Techo = new Cylinder(1f,2f,Paredapp);

        this.addChild(T);        
            T.addChild(Pared_T);Pared_T.addChild(Pared);
            T.addChild(Techo_T);Techo_T.addChild(Techo);
    }
    
    
    void Pereza2(Vector3f Pos, Vector3d Sca, float Rot){
        Appearance Paredapp = new Appearance();
        Material ParedMat = new Material();
        ParedMat.setDiffuseColor(new Color3f(Color.darkGray));
        Paredapp.setMaterial(ParedMat);

        Transform3D T_RotY = new Transform3D();
        Transform3D T_Move = new Transform3D();
        T_Move.setScale(new Vector3d(Sca));
        T_RotY.rotY((float)Math.toRadians(Rot));
        T_Move.mul(T_RotY);
        T_Move.setTranslation(new Vector3f(Pos));
        TransformGroup T = new TransformGroup(T_Move);

        
        
        Transform3D Base_Move = new Transform3D();
        Base_Move.setTranslation(new Vector3f(0,0.5f,0));
        TransformGroup Base_T = new TransformGroup(Base_Move);        
        Cylinder Base = new Cylinder(0.25f,1f,Paredapp);        
        
        Transform3D Tubo_Move = new Transform3D();
        Tubo_Move.setTranslation(new Vector3f(0,2f,0));
        TransformGroup Tubo_T = new TransformGroup(Tubo_Move);        
        Cylinder Tubo = new Cylinder(0.15f,2f,Paredapp); 
        
        this.addChild(T);        
            T.addChild(Base_T);Base_T.addChild(Base);
            T.addChild(Tubo_T);Tubo_T.addChild(Tubo);     
    }   
    
}
