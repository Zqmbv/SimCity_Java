package Menu;

import com.sun.j3d.utils.universe.SimpleUniverse;
import java.awt.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.*;

public class MenuPrincipal extends JPanel {

    public MenuPrincipal() {
        setLayout(new BorderLayout());
        
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        Canvas3D canvas = new Canvas3D(config);
        add(canvas, BorderLayout.CENTER);
        
        JPanel pSur = new JPanel(new BorderLayout());
        pSur.setOpaque(true);
        pSur.setBackground(new Color(15, 45, 75)); 
        pSur.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel lIntegrantes = new JLabel("<html><b>Integrantes:</b><br>Escola, Sebastian C.I. 31.884.003<br>Pedrique, Jose C.I. 31.333.931<br>Zambuchini, Maurizio C.I. 31.544.986</html>");
        lIntegrantes.setForeground(Color.WHITE);
        lIntegrantes.setFont(new Font("Arial", Font.PLAIN, 12));
        pSur.add(lIntegrantes, BorderLayout.WEST);
        
        JPanel pBotones = new JPanel();
        pBotones.setOpaque(false);
        
        JButton bJugar = crearBoton("JUGAR");
        JButton bSalir = crearBoton("SALIR");
        
        bJugar.addActionListener(e -> {
            JFrame ventanaPadre = (JFrame) SwingUtilities.getWindowAncestor(this);
            
            if (ventanaPadre != null) {
                ventanaPadre.remove(this); 

                try {
                    ventanaPadre.add(new MenuAlcaldes());
                } catch (SQLException ex) {
                    Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
                ventanaPadre.revalidate();
                ventanaPadre.repaint();
            }
        });
        
        bSalir.addActionListener(e -> {
            System.exit(0);
        });
        
        pBotones.add(bJugar);
        pBotones.add(bSalir);
        pSur.add(pBotones, BorderLayout.EAST);
        
        this.add(pSur, BorderLayout.SOUTH);

        BranchGroup scena = render();
        scena.compile();
        
        SimpleUniverse SU = new SimpleUniverse(canvas);
        SU.getViewingPlatform().setNominalViewingTransform();   
        SU.addBranchGraph(scena);
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        boton.setForeground(Color.WHITE);
        boton.setBackground(new Color(80, 125, 175));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        // CAMBIAR COLO CUANDO PASAMOS EL MOUSE POR ENCIMA DEL BOTON
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(80, 95, 205));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(80, 125, 175));
            }
        });
        return boton;
    }

    private BranchGroup render() {
        BranchGroup objRoot = new BranchGroup();
        
        BoundingSphere BS = new BoundingSphere(new Point3d(0,0,0), 100.0f);
        
        Background BG = new Background();
        BG.setApplicationBounds(BS);
        
        // DEGRADADO - CELESTE OSCURO A CLARO
        QuadArray gradientQuad = new QuadArray(4, QuadArray.COORDINATES | QuadArray.COLOR_3);
        gradientQuad.setCoordinate(0, new Point3d(-1.0, -1.0, -1.0));
        gradientQuad.setCoordinate(1, new Point3d( 1.0, -1.0, -1.0));
        gradientQuad.setCoordinate(2, new Point3d( 1.0,  1.0, -1.0));
        gradientQuad.setCoordinate(3, new Point3d(-1.0,  1.0, -1.0));

        Color3f celesteClaro = new Color3f(0.5f, 0.8f, 1.0f);  
        Color3f celesteOscuro = new Color3f(0.1f, 0.4f, 0.6f); 
        
        gradientQuad.setColor(0, celesteClaro);  
        gradientQuad.setColor(1, celesteClaro);  
        gradientQuad.setColor(2, celesteOscuro); 
        gradientQuad.setColor(3, celesteOscuro);
        
        Shape3D backgroundShape = new Shape3D(gradientQuad);
        BranchGroup bgGeometry = new BranchGroup();
        bgGeometry.addChild(backgroundShape);
        
        objRoot.addChild(BG);
        BG.setGeometry(bgGeometry);
            
        // TEXTO 3D "SimCity"
        TransformGroup tgTextoSimCity = new TransformGroup();
        tgTextoSimCity.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        objRoot.addChild(tgTextoSimCity);

        Transform3D posSimCity = new Transform3D();
        posSimCity.setTranslation(new Vector3f(0f, 0.35f, 0f));
        tgTextoSimCity.setTransform(posSimCity);

        Font3D font3DSimCity = new Font3D(new Font("Arial", Font.BOLD, 1), new FontExtrusion());
        Text3D textSimCityGeom = new Text3D(font3DSimCity, "SimCity", new Point3f(0,0,0), Text3D.ALIGN_CENTER, Text3D.PATH_RIGHT);

        Appearance appSimCity = new Appearance();
        Material matSimCity = new Material();
        matSimCity.setAmbientColor(new Color3f(1f,1f,1f));
        matSimCity.setDiffuseColor(new Color3f(1f,1f,1f));
        matSimCity.setSpecularColor(new Color3f(1f,1f,1f));
        appSimCity.setMaterial(matSimCity);

        Shape3D shapeSimCity = new Shape3D(textSimCityGeom, appSimCity);
        
        TransformGroup tgScaleSimCity = new TransformGroup();
        Transform3D t3dScaleSimCity = new Transform3D();
        t3dScaleSimCity.setScale(0.25);
        tgScaleSimCity.setTransform(t3dScaleSimCity);
        
        tgTextoSimCity.addChild(tgScaleSimCity); 
        tgScaleSimCity.addChild(shapeSimCity); 

        float aInicio = (float) Math.toRadians(0);
        float aFinal = (float) Math.toRadians(360);
        
        RotationInterpolator RT = new RotationInterpolator(new Alpha(-1, 8000), tgTextoSimCity, new Transform3D(), aInicio, aFinal);
        tgTextoSimCity.addChild(RT);
        RT.setSchedulingBounds(BS);

        // TEXTO 3D "JAVA"
        TransformGroup tgTextoJava = new TransformGroup();
        objRoot.addChild(tgTextoJava);

        Transform3D posJava = new Transform3D();
        posJava.setTranslation(new Vector3f(0.35f, -0.1f, 0.1f)); 
        
        Transform3D rotJava = new Transform3D();
        rotJava.rotZ(Math.toRadians(20));
        posJava.mul(rotJava);
        tgTextoJava.setTransform(posJava);

        Font fontJava = new Font("Arial", Font.BOLD, 1);
        
        FontExtrusion extrusionJava = new FontExtrusion(); 
        Font3D font3DJava = new Font3D(fontJava, extrusionJava);
        Text3D textJavaGeom = new Text3D(font3DJava, "JAVA", new Point3f(0,0,0), Text3D.ALIGN_CENTER, Text3D.PATH_RIGHT);

        Appearance appJava = new Appearance();
        Material matJava = new Material();

        matJava.setAmbientColor(new Color3f(1.0f, 1.0f, 0.0f));
        matJava.setDiffuseColor(new Color3f(1.0f, 1.0f, 0.0f));
        matJava.setSpecularColor(new Color3f(1f, 1f, 1f));
        appJava.setMaterial(matJava);

        Shape3D shapeJava = new Shape3D(textJavaGeom, appJava);
        
        TransformGroup tgScaleJava = new TransformGroup();
        Transform3D t3dScaleJava = new Transform3D();
        t3dScaleJava.setScale(0.15); 
        tgScaleJava.setTransform(t3dScaleJava);
        
        tgTextoJava.addChild(tgScaleJava);
        tgScaleJava.addChild(shapeJava);

        AmbientLight AL = new AmbientLight(new Color3f(Color.GRAY));
        AL.setInfluencingBounds(BS);
        objRoot.addChild(AL);
        
        DirectionalLight DL = new DirectionalLight(new Color3f(Color.WHITE), new Vector3f(-1f,-0.5f,-1f));
        DL.setInfluencingBounds(BS);
        objRoot.addChild(DL);
        
        return objRoot;
    }
}