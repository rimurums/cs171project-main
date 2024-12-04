package com.example.darkness;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Load the FXML so that we can access all groups.
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        AnchorPane root = fxmlLoader.load();

        // Initialize the Player Sprite
        Image image = new Image("newSprite.png");
        ImageView imageView = new ImageView(image);

        // Set sprite parameters \\
//--------------------------------------------------------------\\
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);

        Scale scale = new Scale(10, 10, 10, 10);

        imageView.getTransforms().add(scale);
        imageView.setPreserveRatio(true);
//--------------------------------------------------------------\\
        // Initialize the Player Controller
        PlayerController PC = fxmlLoader.getController();
        PC.init(imageView, root);

        // Add image view to the root so we can see it
        root.getChildren().add(imageView);

        // Set the scene
        Scene scene = new Scene(root, 800, 600);



        // Show the stage
        stage.setTitle("JavaFX Application");
        stage.setScene(scene);
        scene.setOnMouseClicked(e -> root.requestFocus());
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}