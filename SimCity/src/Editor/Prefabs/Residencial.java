package Editor.Prefabs;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.SimpleUniverse;
import java.awt.Color;
import javax.vecmath.*;

public class Residencial extends TransformGroup  {
    
    float Size = 3;
    
    public Residencial(){
        Appearance BaseApp = new  Appearance();
        Material BaseMat = new Material();
        BaseMat.setDiffuseColor(new Color3f(Color.red));
        BaseMat.setLightingEnable(true);
        BaseApp.setMaterial(BaseMat);
        
        Transform3D mov = new Transform3D();
        mov.setTranslation(new Vector3f(Size,0,Size));
        TransformGroup Pivot = new TransformGroup(mov);
        
        //Box Base = new Box(Size,0.05f,Size,BaseApp);
        ColorCube Base = new  ColorCube(3);
        this.addChild(Pivot);
            Pivot.addChild(Base);  

    }
}
