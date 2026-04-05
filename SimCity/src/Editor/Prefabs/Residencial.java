package Editor.Prefabs;
import javax.media.j3d.*;
import com.sun.j3d.utils.geometry.*;
import java.awt.Color;
import javax.vecmath.*;

public class Residencial extends TransformGroup  {
    
    public Residencial(){
        Transform3D Pivot = new  Transform3D();
        //Pivot.setTranslation(new Vector3f(0f,0,0f));
        this.setTransform(Pivot);
        
        
        Appearance app = new Appearance();
        Material mat = new Material();
        mat.setDiffuseColor(new Color3f(Color.red));
        app.setMaterial(mat);
       
        Appearance Paredapp = new Appearance();
        Material Paredmat = new Material();
        Paredmat.setDiffuseColor(new Color3f(Color.white));
        Paredapp.setMaterial(Paredmat);
        
        float Pos[] = {-2f, 0f, 2f};     
        for(int i = 0; i < Pos.length; i++){
            for(int j = 0; j < Pos.length; j++){
                Transform3D T_Move = new Transform3D();
                T_Move.setTranslation(new Vector3f(Pos[i],0,Pos[j]));
                TransformGroup T = new TransformGroup(T_Move);

                
                Transform3D Pared_Move = new Transform3D();
                Pared_Move.setTranslation(new Vector3f(0,0.3f,0));
                TransformGroup Pared_T = new TransformGroup(Pared_Move);
                Box Pared = new Box(0.6f,0.6f,0.6f,Paredapp);
                
                Transform3D Techo_Move = new Transform3D();
                Techo_Move.setTranslation(new Vector3f(0,1.25f,0));
                TransformGroup Techo_T = new TransformGroup(Techo_Move);
                Cone Techo = new Cone(1f,1f,app);

                this.addChild(T);
                    T.addChild(Pared_T);Pared_T.addChild(Pared);
                    T.addChild(Techo_T);Techo_T.addChild(Techo);
            }        
        }
        
        Box Suelo = new Box(3,0.1f,3,app);
        this.addChild(Suelo);
    }
}
