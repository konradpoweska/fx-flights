package gui3D;
import java.util.*;
import java.net.*;

import javafx.animation.AnimationTimer;
import javafx.fxml.*;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class Controller implements Initializable {

	
	@FXML
	private Pane pane3D;
	
	@Override
	public void initialize(URL location, ResourceBundle resources){
		//Put the code of the 3D scene here
		//TODO create the earth 3D scene
		
		//Create scene root for the 3D content
				Group root3D = new Group();//TODO changer nom du group en fonction du nom du pane sur scene builder
				
				//Create cube shape
				final Box cubeBleu = new Box(1, 1, 1);
				final Box cubeVert = new Box(1, 1, 1);
				final Box cubeRouge = new Box(1, 1, 1);
				
				//Move cube shape
				cubeVert.setTranslateY(-1.5);
				cubeRouge.setTranslateX(1.5);
				cubeRouge.setTranslateZ(1.5);
				
				//Create Material
				final PhongMaterial blueMaterial = new PhongMaterial();
				blueMaterial.setDiffuseColor(Color.BLUE);
				blueMaterial.setDiffuseColor(Color.BLUE);
				
				final PhongMaterial greenMaterial = new PhongMaterial();
				greenMaterial.setDiffuseColor(Color.GREEN);
				greenMaterial.setDiffuseColor(Color.GREEN);
				
				final PhongMaterial redMaterial = new PhongMaterial();
				redMaterial.setDiffuseColor(Color.RED);
				redMaterial.setDiffuseColor(Color.RED);
				
				//Set it to the cube
				cubeBleu.setMaterial(blueMaterial);
				cubeVert.setMaterial(greenMaterial);
				cubeRouge.setMaterial(redMaterial);
				
				//Add the cube to this node
				root3D.getChildren().add(cubeBleu);
				root3D.getChildren().add(cubeVert);
				root3D.getChildren().add(cubeRouge);
				
				//Add a camera group
				PerspectiveCamera camera = new PerspectiveCamera(true);

				//Add point light
				PointLight light = new PointLight(Color.WHITE);
				light.setTranslateX(-180);
				light.setTranslateY(-90);
				light.setTranslateZ(-120);
				light.getScope().addAll(root3D);
				root3D.getChildren().add(light);
				
				//Create the subScene 
				SubScene subscene = new SubScene(root3D, 600, 600, true, SceneAntialiasing.BALANCED);
				subscene.setCamera(camera);
				subscene.setFill(Color.GREY);
				pane3D.getChildren().addAll(subscene);
				
				//Add an animation timer
				final long startNanoTime = System.nanoTime();
				new AnimationTimer(){
					public void handle(long currentNanoTime){
						double t = (currentNanoTime - startNanoTime) / 1000000000.0;
						//add your code here
						int rotationSpeed = 15; //15 is a bit slow
						cubeVert.setRotationAxis(new Point3D(0, 1, 0));
						cubeVert.setRotate(rotationSpeed * t);
						
					}
				}.start();
				
				//Build camera manager
						new CameraManager(camera, subscene.getRoot(), root3D);
				
		
		
	}
	
	public Controller() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
