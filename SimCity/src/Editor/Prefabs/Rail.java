package Editor.Prefabs;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.*;
import javax.media.j3d.*;
import com.sun.j3d.utils.geometry.*;
import java.awt.Color;
import javax.vecmath.*;

public class Rail extends TransformGroup  {
    public  Rail(){
        Transform3D Pivot = new  Transform3D();
        //Pivot.setTranslation(new Vector3f(1f,0,1f));
        this.setTransform(Pivot);
        
        Appearance PiedraApp = new Appearance();
        Material PiedraMat = new Material();
        PiedraMat.setDiffuseColor(new Color3f(Color.gray));
        PiedraApp.setMaterial(PiedraMat);
       
        Appearance MaderaApp = new Appearance();
        Material MaderaMat = new Material();
        MaderaMat.setDiffuseColor(new Color3f(0.722f, 0.533f, 0.384f));
        MaderaApp.setMaterial(MaderaMat);        
        
        Appearance HierroApp = new Appearance();
        Material HierroMat = new Material();
        HierroMat.setDiffuseColor(new Color3f(Color.white));
        HierroApp.setMaterial(HierroMat);        
        
        
        Transform3D Hierro1Move = new Transform3D();
        Hierro1Move.setTranslation(new Vector3f(0.3f,0.1f,0));
        TransformGroup Hierro1T =  new TransformGroup(Hierro1Move);
        Box Hierro1 = new Box(0.1f,0.1f,1,HierroApp);
        
        Transform3D Hierro2Move = new Transform3D();
        Hierro2Move.setTranslation(new Vector3f(-0.3f,0.1f,0));
        TransformGroup Hierro2T =  new TransformGroup(Hierro2Move);
        Box Hierro2 = new Box(0.1f,0.1f,1,HierroApp);        
        
        Transform3D Madera1Move = new Transform3D();
        Madera1Move.setTranslation(new Vector3f(0f,0.1f,0));
        TransformGroup Madera1T =  new TransformGroup(Madera1Move);
        Box Madera1 = new Box(0.75f,0.05f,0.1f,MaderaApp);         
        
        Transform3D Madera2Move = new Transform3D();
        Madera2Move.setTranslation(new Vector3f(0f,0.1f,0.65f));
        TransformGroup Madera2T =  new TransformGroup(Madera2Move);
        Box Madera2 = new Box(0.75f,0.05f,0.1f,MaderaApp);         
        
        Transform3D Madera3Move = new Transform3D();
        Madera3Move.setTranslation(new Vector3f(0f,0.1f,-0.65f));
        TransformGroup Madera3T =  new TransformGroup(Madera3Move);
        Box Madera3 = new Box(0.75f,0.05f,0.1f,MaderaApp);         
        
        
        
        
        
        Box PiedraSuelo = new Box(1,0.1f,1,PiedraApp);
        this.addChild(PiedraSuelo);
        
        this.addChild(Hierro1T);Hierro1T.addChild(Hierro1);
        this.addChild(Hierro2T);Hierro2T.addChild(Hierro2);
        
        this.addChild(Madera1T);Madera1T.addChild(Madera1);
        this.addChild(Madera2T);Madera2T.addChild(Madera2);
        this.addChild(Madera3T);Madera3T.addChild(Madera3);
    }    
}
