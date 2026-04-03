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
        
        
        Appearance Techoapp = new Appearance();
        Material TechoMat = new Material();
        TechoMat.setDiffuseColor(new Color3f(Color.black));
        Techoapp.setMaterial(TechoMat);    
                
        
        
        
        Box ParedN = new Box(1,1,0.1f,Techoapp);
        Box ParedS = new Box(1,1,0.1f,Techoapp);
        Box ParedW = new Box(0.1f,1,1f,Techoapp);
        Box ParedE = new Box(0.1f,1,1f,Techoapp);
        
        Box Suelo = new Box(4,0.1f,4,app);
        this.addChild(Suelo);    
    
    }
}
