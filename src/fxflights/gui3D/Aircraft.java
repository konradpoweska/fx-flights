package fxflights.gui3D;

import com.interactivemesh.jfx.importer.ImportException;
import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import fxflights.model.Flight;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;

import java.net.URL;


public class Aircraft {
//	private Group root3D;
	private MeshView[] planeMeshViews;
	private final Double scale = 0.025;
	private Flight flight;
	
	public Aircraft(Flight flight, Color color) {
		
		//Create a scene root for the 3D content
//		root3D = new Group();
		this.flight = flight;
		// Load geometry
		ObjModelImporter planeImporter = new ObjModelImporter();
		try {
			URL modelURL = this.getClass().getResource("Plane/plane.obj");
			planeImporter.read(modelURL);
		}
		catch(ImportException e) {
			//handle exception 
			System.out.println(e.getMessage());
		}
		MeshView[] meshViews = planeImporter.getImport();
		//...
		//TODO parcourir liste pour changer material mais attention le faire une seule fois
		
		//Create material
    	final PhongMaterial material = new PhongMaterial();
    	material.setDiffuseColor(color);
    	material.setSpecularColor(color);
		for (MeshView mesh : meshViews) {
	        	//apply it to the aircraft mesh
	        	mesh.setMaterial(material);
		}
		
		planeMeshViews = meshViews;

	}
	
//	public void setSelected() {
//		Fx3DGroup.planeScale.set3DScale(2*scale);
//	}
	
	public void displayAircraft(Group planesGroup) {
		
		double latitude = flight.getLatitude();
		double longitude = flight.getLongitude();
		double angle = flight.getAngle();
		
		Fx3DGroup planeScale = new Fx3DGroup(planeMeshViews);
		Fx3DGroup planeOffset = new Fx3DGroup(planeScale);
		Fx3DGroup plane = new Fx3DGroup(planeOffset);

		Point3D position = Earth3D.geoCoordTo3dCoord(latitude, longitude);
			
			
		planeScale.set3DScale(scale);
		
		planeOffset.set3DTranslate(0, -0.01, 0);
		planeOffset.set3DRotate(0, 180 + angle, 0);
		plane.set3DTranslate(position.getX(),position.getY(),position.getZ());
		plane.set3DRotate(
				-java.lang.Math.toDegrees(java.lang.Math.asin(position.getY())) - 90,
				java.lang.Math.toDegrees(java.lang.Math.atan2(position.getX(), position.getZ())),
				0);
		
//		plane.setOnMouseClicked(new EventHandler<MouseEvent>() {
//			 @Override
//		    	public void handle(MouseEvent event) {
//				 planeScale.set3DScale(scale*2);
//		    	}
//		 });
		
		planesGroup.getChildren().addAll(plane);
	}
	
	
}
