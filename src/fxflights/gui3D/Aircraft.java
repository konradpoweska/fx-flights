package fxflights.gui3D;

import java.net.URL;
import java.util.List;

import com.interactivemesh.jfx.importer.ImportException;
import com.interactivemesh.jfx.importer.obj.ObjModelImporter;

//import lib.interactivemesh.jfx.importer.ImportException;
//import lib.interactivemesh.jfx.importer.obj.ObjModelImporter;

import fxflights.model.Flight;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;

public class Aircraft {
//	private Group root3D;
	private MeshView[] planeMeshViews;
	private final Double scale = 0.025;
	
	public Aircraft(Color color) {
		
		//Create a scene root for the 3D content
//		root3D = new Group();

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
	
	public void displayAircraft(Flight flight, Group planesGroup) {
		
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
		
		plane.setOnMouseClicked(new EventHandler<MouseEvent>() {
			 @Override
		    	public void handle(MouseEvent event) {
				 planeScale.set3DScale(scale*2);
		    	}
		 });
		
		planesGroup.getChildren().addAll(plane);
	}
	
	
}
