package Editor.Prefabs;
import javax.media.j3d.*;
import com.sun.j3d.utils.geometry.*;
import java.awt.Color;
import javax.vecmath.*;

public class Stadium extends TransformGroup  {
    public Stadium(){
        Transform3D Pivot = new  Transform3D();
        //Pivot.setTranslation(new Vector3f(4f,0,4f));
        this.setTransform(Pivot);
        
        Appearance app = new Appearance();
        Material mat = new Material();
        mat.setDiffuseColor(new Color3f(Color.magenta));
        app.setMaterial(mat);
    
        Appearance Aapp = new Appearance();
        Material Amat = new Material();
        Amat.setDiffuseColor(new Color3f(Color.gray));
        Aapp.setMaterial(Amat);        

        Appearance Bapp = new Appearance();
        Material Bmat = new Material();
        Bmat.setDiffuseColor(new Color3f(Color.blue));
        Bapp.setMaterial(Bmat);  
        
         Appearance Capp = new Appearance();
        Material Cmat = new Material();
        Cmat.setDiffuseColor(new Color3f(Color.white));
        Capp.setMaterial(Cmat);  
        
        Transform3D Base_Move = new Transform3D();
        Base_Move.setTranslation(new Vector3d(0,0.25f,0));
        Base_Move.setScale(new Vector3d(3,0.75,2));
        TransformGroup Base_T = new TransformGroup(Base_Move);       
        Cylinder Base = new Cylinder(1,1,Aapp);

        Transform3D Piso1_Move = new Transform3D();
        Piso1_Move.setTranslation(new Vector3d(0,0.75f,0));
        Piso1_Move.setScale(new Vector3d(2.9,0.5,1.9));
        TransformGroup Piso1_T = new TransformGroup(Piso1_Move);       
        Cylinder Piso1 = new Cylinder(1,1,Bapp);
        
        Transform3D Top_Move = new Transform3D();
        Top_Move.setTranslation(new Vector3d(0,1.25f,0));
        Top_Move.setScale(new Vector3d(3,0.75,2));
        TransformGroup Top_T = new TransformGroup(Top_Move);       
        Cylinder Top = new Cylinder(1,1,Aapp);

        Transform3D Vidrio_Move = new Transform3D();
        Vidrio_Move.setTranslation(new Vector3d(0,1.5f,0));
        Vidrio_Move.setScale(new Vector3d(2.9,1.5f,1.9));
        TransformGroup Vidrio_T = new TransformGroup(Vidrio_Move);       
        Sphere Vidrio = new Sphere(1,1,Capp);        
        
        
        this.addChild(Base_T);Base_T.addChild(Base);
        this.addChild(Piso1_T);Piso1_T.addChild(Piso1);
        this.addChild(Top_T);Top_T.addChild(Top);
        this.addChild(Vidrio_T);Vidrio_T.addChild(Vidrio);
        
        CuboT(new Vector3f(3f,0,3f),new Vector3d(0.5,1,0.5), 1, Color.CYAN);
        CuboT(new Vector3f(1f,0,3f),new Vector3d(0.5,1,0.5), 2, Color.ORANGE);
        CuboT(new Vector3f(3f,0,1f),new Vector3d(0.5,1,0.5), 3, Color.green);
                
        CuboT(new Vector3f(-3f,0,-3f),new Vector3d(0.5,1,0.5), 4, Color.red);
        CuboT(new Vector3f(-1f,0,-3f),new Vector3d(0.5,1,0.5), 5, Color.blue);
        CuboT(new Vector3f(-3f,0,-1f),new Vector3d(0.5,1,0.5), 6, Color.yellow);
         
        
        Box Road = new Box(4,0.1f,4,app);
        this.addChild(Road);
    }
    
    public void CuboT(Vector3f POS,Vector3d SCA, int N, Color C){
        Appearance TechoApp = new Appearance();
        Material TechoMat = new Material();
        TechoMat.setDiffuseColor(new Color3f(C));
        TechoApp.setMaterial(TechoMat); 
        
        for(int i = 0; i < N; i++){
            Transform3D T_Move = new Transform3D();
            T_Move.setTranslation(new Vector3f(POS.x,i,POS.z));
            T_Move.setScale(SCA);
            TransformGroup T = new TransformGroup(T_Move);

            Transform3D Pared_Move = new Transform3D();
            Pared_Move.setTranslation(new Vector3f(0,0.55f,0));
            TransformGroup Pared_T = new TransformGroup(Pared_Move);
            Box Pared = new Box(0.9f,0.5f,0.9f,TechoApp);
       

            this.addChild(T);
                T.addChild(Pared_T);Pared_T.addChild(Pared);
        }
    }
}
