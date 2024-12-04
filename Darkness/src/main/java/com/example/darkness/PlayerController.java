// Referenced https://gist.github.com/Da9el00/7f46d7bc4fd87feb134199cb0ba6a157

package com.example.darkness;
import javafx.animation.*;
import javafx.application.Application;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PlayerController {
    @FXML
    private Group collisionGroup;

    @FXML
    private Group successGroup;

    private ImageView sprite;
    private AnchorPane scene;

    private Bounds spriteBounds;
    private Bounds targetBounds;

    private Rectangle targetObj;

    private double gravity = 1;

    private boolean wallJumping = false;

    private double velocityY = 0; // Vertical velocity
    private final double GRAVITY = 0.5; // Gravity acceleration
    private  double JUMP_FORCE = -15; // Jump force (negative for upward movement)
    private int jumpCounter = 0; // Tracks current jumps
    private final int MAX_JUMPS = 1;
    private double TIME_SINCE_LAST_JUMP = 0;




    // Player Inputs
    private BooleanProperty spacePressed = new SimpleBooleanProperty();
    private BooleanProperty aPressed = new SimpleBooleanProperty();
    private BooleanProperty dPressed = new SimpleBooleanProperty();

    private BooleanBinding keyPressed = spacePressed.or(aPressed).or(dPressed);

    public void init(ImageView sprite, AnchorPane scene) {
        // Set values
        this.scene = scene;
        this.sprite = sprite;

        // Check for collision groups \\
//--------------------------------------------------------------------------\\
        if (collisionGroup != null) {
            for (Node node : collisionGroup.getChildren()) {
                if (node instanceof Rectangle) {
                    Rectangle rect = (Rectangle) node;
                    System.out.println(rect);
                }
            }
        } else {
            System.out.println("Is currently null..");
        }

        if (successGroup != null) {
            for (Node node : successGroup.getChildren()) {
                if (node instanceof Rectangle) {
                    Rectangle rect = (Rectangle) node;
                    System.out.println(rect);
                }
            }
        }
//--------------------------------------------------------------------------\\

        // Initialize player movement
        movePlayer();
        startGameLoop();
    }

    private void movePlayer() {
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE) {
                spacePressed.set(true);
            }

            if (e.getCode() == KeyCode.A) {
                aPressed.set(true);
            }

            if (e.getCode() == KeyCode.D) {
                dPressed.set(true);
            }
        });

        scene.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.SPACE) {
                spacePressed.set(false);
            }

            if (e.getCode() == KeyCode.A) {
                aPressed.set(false);
            }

            if (e.getCode() == KeyCode.D) {
                dPressed.set(false);
            }
        });
    }

    private AnimationTimer updateFunction;

    public void startGameLoop(){
        updateFunction = new AnimationTimer() {
            @Override
            public void handle(long timer) {
                update();
            }
        };

        updateFunction.start();
    }
    private boolean grounded;
    private boolean hasWallClimbed = false;
    private boolean VALIDATE_WALL_CLIMB = false;

    private double MOVE_SPEED = 1;

    private void update() {
        double lastPosX = sprite.getLayoutX();
        double lastPosY = sprite.getLayoutY();

        if (grounded) {
            MOVE_SPEED = 1;
        } else {
            MOVE_SPEED = 2;
        }

        if (aPressed.get()) {
            sprite.setLayoutX(sprite.getLayoutX() - MOVE_SPEED);
        }

        if (dPressed.get()) {
            sprite.setLayoutX(sprite.getLayoutX() + MOVE_SPEED);
        }


        // X Collision
        if (checkCollision(sprite, collisionGroup)) {
            sprite.setLayoutX(lastPosX);

            if (!grounded) {
                System.out.println("Wall jump");
                wallJumping = true;

                jumpCounter = 0;
                velocityY = 0;

                velocityY += GRAVITY / 2;
                // Wall jump
            } else { wallJumping = false; }
        }

        if (spacePressed.get() && jumpCounter < MAX_JUMPS) {

            velocityY = JUMP_FORCE; // Apply jump force
            jumpCounter++; // Increment jump counter
            System.out.println(jumpCounter);

        }

        velocityY += GRAVITY; // Apply gravity
        sprite.setLayoutY(sprite.getLayoutY() + velocityY);


        if (checkCollision(sprite, collisionGroup)) {
            sprite.setLayoutY(lastPosY);
            grounded = true;

            velocityY = 0; // Stop downward movement
            jumpCounter = 0;


            TIME_SINCE_LAST_JUMP = System.currentTimeMillis();

            System.out.println("Grounded");
        } else {
            grounded = false;
        }

    }

    private boolean checkCollision(ImageView sprite, Group targetGroup) {
            if (collisionGroup != null) {
                for (Node node : collisionGroup.getChildren()) {
                    if (node instanceof Rectangle) {
                        Rectangle rect = (Rectangle) node;

                        Bounds rectBounds = rect.localToScene(rect.getBoundsInLocal());
                        Bounds spriteBounds = sprite.localToScene(sprite.getBoundsInLocal());

                        if (rectBounds.intersects(spriteBounds)) {
                            return true;
                        }
                    }
                }
            }

        return false;
    }


    private double velocity = 0;
    private double boost = 0.5;
    private double jumpStrength = -10;
    private boolean isJumping = false;

    private void jump() {
    }
}

