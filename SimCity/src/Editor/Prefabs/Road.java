package Editor.Prefabs;
import javax.media.j3d.*;
import com.sun.j3d.utils.geometry.*;
import java.awt.Color;
import javax.vecmath.*;

public class Road extends TransformGroup  {
    public  Road(){
        Transform3D Pivot = new  Transform3D();
        //Pivot.setTranslation(new Vector3f(1f,0,1f));
        this.setTransform(Pivot);
        
        Appearance app = new Appearance();
        Material mat = new Material();
        mat.setDiffuseColor(new Color3f(Color.black));
        app.setMaterial(mat);
       
        
        Appearance appA = new Appearance();
        Material matA = new Material();
        matA.setDiffuseColor(new Color3f(Color.white));
        appA.setMaterial(matA);        
        
        Box Blanco = new Box(0.1f,0.15f,0.25f,appA);
        this.addChild(Blanco);
        Box Road = new Box(1,0.1f,1,app);
        this.addChild(Road);
    }
}
