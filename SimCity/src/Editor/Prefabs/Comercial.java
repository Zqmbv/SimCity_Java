package Editor.Prefabs;
import javax.media.j3d.*;
import com.sun.j3d.utils.geometry.*;
import java.awt.Color;
import javax.vecmath.*;

public class Comercial extends TransformGroup  {
    public Comercial(){
          Transform3D Pivot = new  Transform3D();
        Pivot.setTranslation(new Vector3f(3f,0,3f));
        this.setTransform(Pivot);
        
        Appearance app = new Appearance();
        Material mat = new Material();
        mat.setDiffuseColor(new Color3f(Color.blue));
        app.setMaterial(mat);

        Appearance LadrilloApp = new Appearance();
        Material LadrilloMat = new Material();
        LadrilloMat.setDiffuseColor(new Color3f(Color.white));
        LadrilloApp.setMaterial(LadrilloMat);
  
        
        for(int i = 0; i < 1; i++){
            Transform3D T_Move = new Transform3D();
            T_Move.setTranslation(new Vector3f(1.5f,i,0));
            T_Move.setScale(new Vector3d(0.75,1,2.9f));
            TransformGroup T = new TransformGroup(T_Move);

            Transform3D Pared_Move = new Transform3D();
            Pared_Move.setTranslation(new Vector3f(0,0.55f,0));
            TransformGroup Pared_T = new TransformGroup(Pared_Move);
            Box Pared = new Box(0.9f,0.5f,0.9f,LadrilloApp);

            Transform3D Techo_Move = new Transform3D();
            Techo_Move.setTranslation(new Vector3f(0,1,0));
            TransformGroup Techo_T = new TransformGroup(Techo_Move);
            Box Techo = new Box(1f,0.1f,1f,app);        

            this.addChild(T);
                T.addChild(Pared_T);Pared_T.addChild(Pared);
                T.addChild(Techo_T);Techo_T.addChild(Techo);
        }

        for(int i = 0; i < 1; i++){
            Transform3D T_Move = new Transform3D();
            T_Move.setTranslation(new Vector3f(-1.5f,i,0));
            T_Move.setScale(new Vector3d(0.75,1,2));
            TransformGroup T = new TransformGroup(T_Move);

            Transform3D Pared_Move = new Transform3D();
            Pared_Move.setTranslation(new Vector3f(0,0.55f,0));
            TransformGroup Pared_T = new TransformGroup(Pared_Move);
            Box Pared = new Box(0.9f,0.5f,0.9f,LadrilloApp);

            Transform3D Techo_Move = new Transform3D();
            Techo_Move.setTranslation(new Vector3f(0,1,0));
            TransformGroup Techo_T = new TransformGroup(Techo_Move);
            Box Techo = new Box(1f,0.1f,1f,app);        

            this.addChild(T);
                T.addChild(Pared_T);Pared_T.addChild(Pared);
                T.addChild(Techo_T);Techo_T.addChild(Techo);
        }           

        for(int i = 0; i < 3; i++){
            Transform3D T_Move = new Transform3D();
            T_Move.setTranslation(new Vector3f(0,i,-1.5f));
            T_Move.setScale(new Vector3d(1,1,1));
            TransformGroup T = new TransformGroup(T_Move);

            Transform3D Pared_Move = new Transform3D();
            Pared_Move.setTranslation(new Vector3f(0,0.55f,0));
            TransformGroup Pared_T = new TransformGroup(Pared_Move);
            Box Pared = new Box(0.9f,0.5f,0.9f,LadrilloApp);

            Transform3D Techo_Move = new Transform3D();
            Techo_Move.setTranslation(new Vector3f(0,1,0));
            TransformGroup Techo_T = new TransformGroup(Techo_Move);
            Box Techo = new Box(1f,0.1f,1f,app);        

            this.addChild(T);
                T.addChild(Pared_T);Pared_T.addChild(Pared);
                T.addChild(Techo_T);Techo_T.addChild(Techo);
        }  


        for(int i = 0; i < 2; i++){
            Transform3D T_Move = new Transform3D();
            T_Move.setTranslation(new Vector3f(1,i,-1f));
            T_Move.setScale(new Vector3d(1,1,1));
            TransformGroup T = new TransformGroup(T_Move);

            Transform3D Pared_Move = new Transform3D();
            Pared_Move.setTranslation(new Vector3f(0,0.55f,0));
            TransformGroup Pared_T = new TransformGroup(Pared_Move);
            Box Pared = new Box(0.9f,0.5f,0.9f,LadrilloApp);

            Transform3D Techo_Move = new Transform3D();
            Techo_Move.setTranslation(new Vector3f(0,1,0));
            TransformGroup Techo_T = new TransformGroup(Techo_Move);
            Box Techo = new Box(1f,0.1f,1f,app);        

            this.addChild(T);
                T.addChild(Pared_T);Pared_T.addChild(Pared);
                T.addChild(Techo_T);Techo_T.addChild(Techo);
        } 
        
        Box Suelo = new Box(3f,0.1f,3f,app);
        this.addChild(Suelo);
        
        
        
        
    }
}
