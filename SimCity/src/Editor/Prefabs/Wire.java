package Editor.Prefabs;
import javax.media.j3d.*;
import com.sun.j3d.utils.geometry.*;
import java.awt.Color;
import javax.vecmath.*;

public class Wire extends TransformGroup {
    public  Wire(){
        Transform3D Pivot = new  Transform3D();
        Pivot.setTranslation(new Vector3f(1f,0,1f));
        this.setTransform(Pivot);
        
        Appearance app = new Appearance();
        Material mat = new Material();
        mat.setDiffuseColor(new Color3f(Color.white));
        app.setMaterial(mat);

        Appearance Capp = new Appearance();
        Material Cmat = new Material();
        Cmat.setDiffuseColor(new Color3f(Color.black));
        Capp.setMaterial(Cmat);
        
        Transform3D BaseMov = new Transform3D();
        BaseMov.setTranslation(new Vector3f(0,0.5f,0));
        TransformGroup BaseT = new TransformGroup(BaseMov);
        Cylinder Base = new Cylinder(0.15f,1f,app);
        
        Transform3D PaloMov = new Transform3D();
        PaloMov.setTranslation(new Vector3f(0,1.5f,0));
        TransformGroup PaloT = new TransformGroup(PaloMov);
        Cylinder Palo = new Cylinder(0.1f,3f,app);        
        
        Transform3D PosteMove = new Transform3D();
        PosteMove.setTranslation(new Vector3f(0,3f,0));
        TransformGroup PosteT = new TransformGroup(PosteMove);
        Box Poste = new Box(0.15f,0.15f,0.15f,app);          
        
        
        Transform3D CableARotX = new Transform3D();
        Transform3D CableARotY = new Transform3D();
        Transform3D CableAMove = new Transform3D();
        CableARotX.rotX((float)Math.toRadians(90));
        CableARotY.rotY((float)Math.toRadians(90));
        CableAMove.setTranslation(new Vector3f(0,3f,0));
        CableAMove.mul(CableARotX);CableAMove.mul(CableARotY);
        TransformGroup CableAT = new TransformGroup(CableAMove);
        Cylinder CableA = new Cylinder(0.1f,3f,Capp);          
            
        Box Road = new Box(1,0.1f,1,app);
        this.addChild(Road);
        
        this.addChild(BaseT);BaseT.addChild(Base);
        this.addChild(PaloT);PaloT.addChild(Palo);
        this.addChild(PosteT);PosteT.addChild(Poste);
        this.addChild(CableAT);CableAT.addChild(CableA);           
    }   
}
