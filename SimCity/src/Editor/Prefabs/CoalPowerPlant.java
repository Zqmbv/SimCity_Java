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
        
        
        Transform3D Pared_Move = new Transform3D();
        
        
        Box Suelo = new Box(4,0.1f,4,app);
        this.addChild(Suelo);    
    
    }
}
