package Editor.Prefabs;
import javax.media.j3d.*;
import com.sun.j3d.utils.geometry.*;
import java.awt.Color;
import javax.vecmath.*;

public class Airport extends TransformGroup {
    public Airport(){
        Transform3D Pivot = new  Transform3D();
        //Pivot.setTranslation(new Vector3f(6f,0,6f));
        this.setTransform(Pivot);

        Appearance app = new Appearance();
        Material mat = new Material();
        mat.setDiffuseColor(new Color3f(Color.magenta));
        app.setMaterial(mat);

        Appearance Aapp = new Appearance();
        Material Amat = new Material();
        Amat.setDiffuseColor(new Color3f(Color.BLACK));
        Aapp.setMaterial(Amat);

        Appearance Bapp = new Appearance();
        Material Bmat = new Material();
        Bmat.setDiffuseColor(new Color3f(Color.white));
        Bapp.setMaterial(Bmat);

        Appearance Capp = new Appearance();
        Material Cmat = new Material();
        Cmat.setDiffuseColor(new Color3f(Color.red));
        Capp.setMaterial(Cmat);
        
        Transform3D Linea_Move = new Transform3D();
        Linea_Move.setTranslation(new Vector3f(0,0,-3));
        TransformGroup Linea_T = new TransformGroup(Linea_Move);
        Box Linea = new Box(6f,0.2f,2.5f,Aapp);

        Transform3D LineaA_Move = new Transform3D();
        LineaA_Move.setTranslation(new Vector3f(0,0.05f,-3));
        TransformGroup LineaA_T = new TransformGroup(LineaA_Move);
        Box LineaA = new Box(5f,0.2f,0.1f,Bapp);        

        
        Transform3D Torre_Move = new Transform3D();
        Torre_Move.setTranslation(new Vector3f(-5,3,0.75f));
        TransformGroup Torre_T = new TransformGroup(Torre_Move);
        Box Torre = new Box(0.5f,3f,0.5f,Bapp);

        Transform3D TorreB_Move = new Transform3D();
        TorreB_Move.setTranslation(new Vector3f(-5,5,0.75f));
        TransformGroup TorreB_T = new TransformGroup(TorreB_Move);
        Box TorreB = new Box(1f,0.5f,1f,Capp);        

        Box Suelo = new Box(6,0.1f,6,app);
        this.addChild(Suelo);
        this.addChild(Linea_T);Linea_T.addChild(Linea);
        this.addChild(LineaA_T);LineaA_T.addChild(LineaA);
        this.addChild(Torre_T);Torre_T.addChild(Torre);
        this.addChild(TorreB_T);TorreB_T.addChild(TorreB);
                
        CuboT(new Vector3f(0,0,3.5f),new Vector3d(4.5f,1,2), 1);
        CuboT(new Vector3f(3.5f,0,3f),new Vector3d(1,1,3), 3);    
    }
    
    public void CuboT(Vector3f POS,Vector3d SCA, int N){
        Appearance TechoApp = new Appearance();
        Material TechoMat = new Material();
        TechoMat.setDiffuseColor(new Color3f(Color.red));
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
