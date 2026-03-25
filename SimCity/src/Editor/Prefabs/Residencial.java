package Editor.Prefabs;
import javax.media.j3d.*;
import com.sun.j3d.utils.geometry.*;
import java.awt.Color;
import javax.vecmath.*;

public class Residencial extends TransformGroup  {
    
    public Residencial(){
        Transform3D Pivot = new  Transform3D();
        Pivot.setTranslation(new Vector3f(3f,0,3f));
        this.setTransform(Pivot);
        
        Appearance app = new Appearance();
        Material mat = new Material();
        mat.setDiffuseColor(new Color3f(Color.red));
        app.setMaterial(mat);
       
        Box Road = new Box(3,0.1f,3,app);
        this.addChild(Road);
    }
}
