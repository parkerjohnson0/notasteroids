/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids;

import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author peppe
 */
public class Asteroid {

    int size = 3;
    double xLoc = Math.random() * 1280;
    double yLoc = Math.random() * 960;
    double xSpeed, ySpeed;
    double rotation;
    boolean exists;
    ImageView asteroidImage = new ImageView();

    //constructor for medium and small asteroids
    public Asteroid(int size, double xLoc, double yLoc, double xSpeed, double ySpeed, double rotation) {


        this.rotation += rotation * Math.random();
        this.exists = true;
        this.size = size;
        if (this.size == 2) {
            this.asteroidImage.setImage(new Image("file:resources/assets/medium1.png"));
            this.xLoc = xLoc + asteroidImage.getImage().getWidth() / 2; // OFFSETS THE CENTER OF EACH STEROID TO BE THE CENTER OF THE IMAGE INSTEAD OF THE TOP LEFT
            this.yLoc = yLoc - asteroidImage.getImage().getHeight() / 2;
            this.xSpeed = (xSpeed * 1.1) + (Math.random() - 0.5);
            this.ySpeed = (ySpeed * 1.1)- (Math.random() - 0.5);
        }
        if (this.size == 1) {
            this.asteroidImage.setImage(new Image("file:resources/assets/small1.png"));
            this.xLoc = xLoc + asteroidImage.getImage().getWidth() / 2;
            this.yLoc = yLoc - asteroidImage.getImage().getHeight() / 2;
            this.xSpeed = (xSpeed * 1.1) + (Math.random() - 0.5);
            this.ySpeed = (ySpeed * 1.1) - (Math.random() - 0.5);
        }
    }

    //default constructor
    public Asteroid() {
        this.asteroidImage.setImage(new Image("file:resources/assets/big1.png"));
        this.xLoc = xLoc + asteroidImage.getImage().getWidth() / 2;
        this.yLoc = yLoc - asteroidImage.getImage().getHeight() / 2;
        this.xSpeed = Math.random() - 0.5;
        this.ySpeed = Math.random() - 0.5;
        this.rotation = Math.random() * 3 - 1;
        this.exists = true;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    public double getxLoc() {
        return xLoc;
    }

    public void setxLoc(double xLoc) {
        this.xLoc = xLoc;
    }

    public double getyLoc() {
        return yLoc;
    }

    public void setyLoc(double yLoc) {
        this.yLoc = yLoc;
    }

    public ImageView getSprite() {
        asteroidImage.setLayoutX(xLoc);
        asteroidImage.setLayoutY(yLoc);
        return asteroidImage;
    }

    void move() {
        xLoc += xSpeed;
        yLoc -= ySpeed;
    }

    void rotate() {
        asteroidImage.setRotate((asteroidImage.getRotate() + rotation) % 360);
    }

    Asteroid spawnChild() {
        
        return new Asteroid(this.size - 1, xLoc, yLoc, xSpeed, ySpeed, rotation);
    }

}
