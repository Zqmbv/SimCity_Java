package Editor.Prefabs;
import javax.media.j3d.*;
import com.sun.j3d.utils.geometry.*;
import java.awt.Color;
import javax.vecmath.*;
import java.util.*;

public class Park extends TransformGroup  {

    Random rand = new Random();
    
    public  Park(){
        Transform3D Pivot = new  Transform3D();
        Pivot.setTranslation(new Vector3f(1f,0,1f));
        this.setTransform(Pivot);
        
        Appearance PastoApp = new Appearance();
        Material PastoMat = new Material();
        PastoMat.setDiffuseColor(new Color3f(Color.green));
        PastoApp.setMaterial(PastoMat);

                
        Appearance TroncoApp = new Appearance();
        Material TroncoMat = new Material();
        TroncoMat.setDiffuseColor(new Color3f(0.722f, 0.533f, 0.384f));
        TroncoApp.setMaterial(TroncoMat);
        
        Box PastoSuelo = new Box(1,0.1f,1,PastoApp);
        
        for(int i = 0; i < rand.nextInt(1, 5); i++){
            float X = rand.nextFloat(-0.9f, 0.9f);
            float Y = rand.nextFloat(-0.9f, 0.9f);
            Transform3D Mov = new  Transform3D();
            TransformGroup T = new TransformGroup(Mov);

            Transform3D TroncoMov = new  Transform3D();
            TroncoMov.setTranslation(new Vector3f(X,0.25f,Y));
            TransformGroup TroncoT = new TransformGroup(TroncoMov);               
            Cylinder Tronco = new Cylinder(0.1f,0.5f,TroncoApp); 

            Transform3D HojasMov = new  Transform3D();
            HojasMov.setTranslation(new Vector3f(X,0.75f,Y));
            TransformGroup HojasT = new TransformGroup(HojasMov);        
            Sphere Hojas = new Sphere(0.3f,PastoApp);        

            this.addChild(T);
                T.addChild(TroncoT);TroncoT.addChild(Tronco);
                T.addChild(HojasT);HojasT.addChild(Hojas);
        }

        for(int i = 0; i < rand.nextInt(1, 5); i++){
            float X = rand.nextFloat(-0.9f, 0.9f);
            float Y = rand.nextFloat(-0.9f, 0.9f);
            Transform3D Mov = new  Transform3D();
            TransformGroup T = new TransformGroup(Mov);

            Transform3D HojasMov = new  Transform3D();
            HojasMov.setTranslation(new Vector3f(X,0,Y));
            TransformGroup HojasT = new TransformGroup(HojasMov);        
            Sphere Hojas = new Sphere(0.3f,PastoApp);        

            this.addChild(T);
                T.addChild(HojasT);HojasT.addChild(Hojas);
        }
        
        this.addChild(PastoSuelo);
    }      
}
