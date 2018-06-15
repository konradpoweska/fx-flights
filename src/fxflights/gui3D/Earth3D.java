package fxflights.gui3D;

import java.net.URL;

import com.interactivemesh.jfx.importer.ImportException;
import com.interactivemesh.jfx.importer.obj.ObjModelImporter;

import javafx.geometry.Point3D;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class Earth3D{

    private static final double TEXTURE_LAT_OFFSET = -0.2f;
    private static final double TEXTURE_LON_OFFSET = 2.8f;
    private Group root3D;

    public Earth3D(Pane parent) {

        //Create a Pane et graph scene root for the 3D content
        root3D = new Group();

        // Load geometry
        ObjModelImporter objImporter = new ObjModelImporter();
        try {
        	URL modelURL = this.getClass().getResource("Earth/earth.obj");
        	objImporter.read(modelURL);
        }
        catch(ImportException e) {
        	//handle exception 
        	System.out.println(e.getMessage());
        }
        MeshView[] meshViews = objImporter.getImport();
        Group earth = new Group(meshViews);
        root3D.getChildren().add(earth);
        
        // Draw a line
        Point3D point1 = new Point3D(3, 0, 0);
        Point3D point2 = new Point3D(2, 1, 0);
        Cylinder line = createLine(point1, point2);
        root3D.getChildren().add(line);
        
        // Draw an helix
        Group helix = new Group();
        Point3D oldVect = new Point3D(1, 0, 0);
        for (int i =0; i < 100; i++) {
        	float t = i / 5.0f;
        	Point3D newVect = new Point3D(Math.cos(t), t / 5.0f, Math.sin(t));
        	//draw one small line
        	Cylinder smallLine = createLine(oldVect, newVect);
        	helix.getChildren().add(smallLine);
        	oldVect = newVect;
        }
        helix.setTranslateY(2);
        root3D.getChildren().add(helix);
        
        // Draw city on the earth
//        displayAirport("Brest", 48.447911, -4.418539, true);
//        displayAirport("Marseille", 43.435555, 5.213611, false);

        // Add a camera group
        PerspectiveCamera camera = new PerspectiveCamera(true);
        new CameraManager(camera, parent, root3D);

        // Add point light
        PointLight light = new PointLight(Color.WHITE);
        light.setTranslateX(-180);	
        light.setTranslateY(-90);
        light.setTranslateZ(-120);
        light.getScope().addAll(root3D);
        root3D.getChildren().add(light);

        // Add ambient light
        AmbientLight ambientLight = new AmbientLight(Color.WHITE);
        ambientLight.getScope().addAll(root3D);
        root3D.getChildren().add(ambientLight);
        
        System.out.println("Avant creation subscene");
        // Create scene
    	SubScene subscene = new SubScene(root3D, 600, 600, true, SceneAntialiasing.BALANCED);
		subscene.setCamera(camera);
		subscene.setFill(Color.GREY);
		parent.getChildren().addAll(subscene);

      
    }

    
    //Display Airport function
    public void displayAirport(String name, double latitude, double longitude, boolean isDeparture) {
    	Group airport = new Group();
    	Point3D point = geoCoordTo3dCoord(latitude, longitude);
    	Sphere sphere = new Sphere(0.01);
    	
    	//Create material
    	final PhongMaterial material = new PhongMaterial();
    	
    	if (isDeparture) {
    		material.setDiffuseColor(Color.GREEN);
        	material.setSpecularColor(Color.GREEN);
    	}
    	else {
    		material.setDiffuseColor(Color.RED);
        	material.setSpecularColor(Color.RED);
    	}
    	
    	//apply it to the airport
    	sphere.setMaterial(material);
    	
    	//add the sphere to the airport group
    	airport.getChildren().add(sphere);
    	airport.setId(name);
    	
    	//translate city to the right place
    	airport.setTranslateX(point.getX());
    	airport.setTranslateY(point.getY());
    	airport.setTranslateZ(point.getZ());
    	root3D.getChildren().add(airport);
    }

    // From Rahel Lüthy : https://netzwerg.ch/blog/2015/03/22/javafx-3d-line/
    public Cylinder createLine(Point3D origin, Point3D target) {
        Point3D yAxis = new Point3D(0, 1, 0);
        Point3D diff = target.subtract(origin);
        double height = diff.magnitude();

        Point3D mid = target.midpoint(origin);
        Translate moveToMidpoint = new Translate(mid.getX(), mid.getY(), mid.getZ());

        Point3D axisOfRotation = diff.crossProduct(yAxis);
        double angle = Math.acos(diff.normalize().dotProduct(yAxis));
        Rotate rotateAroundCenter = new Rotate(-Math.toDegrees(angle), axisOfRotation);

        Cylinder line = new Cylinder(0.01f, height);

        line.getTransforms().addAll(moveToMidpoint, rotateAroundCenter);

        return line;
    }

    public static Point3D geoCoordTo3dCoord(double lat, double lon) {
        double lat_cor = lat + TEXTURE_LAT_OFFSET;
        double lon_cor = lon + TEXTURE_LON_OFFSET;
        return new Point3D(
                -java.lang.Math.sin(java.lang.Math.toRadians(lon_cor))
                        * java.lang.Math.cos(java.lang.Math.toRadians(lat_cor)),
                -java.lang.Math.sin(java.lang.Math.toRadians(lat_cor)),
                java.lang.Math.cos(java.lang.Math.toRadians(lon_cor))
                        * java.lang.Math.cos(java.lang.Math.toRadians(lat_cor)));
    }

}
