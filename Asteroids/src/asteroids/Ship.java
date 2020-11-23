/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids;

import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;

/**
 *
 * @author peppe
 */
public class Ship {

    ImageView shipImage = new ImageView(new Image("file:resources/assets/shipv2.png"));
    private double xLoc, yLoc;
     double speed, xSpeed, ySpeed;
    private double rotation;
    private int shots;
    double timeLastShot;
    boolean isMoving;
    private boolean dead;

    public Ship() {
        xLoc = 640;
        yLoc = 480;
        
    }

    public boolean getIsDead() {
        return dead;
    }

    public void setIsDead(boolean isDead) {
        this.dead = isDead;
    }
    
    public boolean isIsMoving() {
        return isMoving;
    }

    public void setIsMoving(boolean isMoving) {
        this.isMoving = isMoving;
    }
    
    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
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
        shipImage.setLayoutX(xLoc);
        shipImage.setLayoutY(yLoc);
        return shipImage;
    }

    void rotateLeft() {
        if (isMoving) {
            rotation = (shipImage.getRotate() + 359) % 360;
            shipImage.setRotate(rotation);
        } else {
            rotation = (shipImage.getRotate() + 358) % 360;
            shipImage.setRotate(rotation);
        }
    }

    void rotateRight() {
        if (isMoving) {
            rotation = (shipImage.getRotate() + 1) % 360;
            shipImage.setRotate(rotation);
        } else {
            rotation = (shipImage.getRotate() + 2) % 360;
            shipImage.setRotate(rotation);
        }
    }

    void thrust() {
        double yTempSpeed = ySpeed * Math.sin(Math.toRadians(rotation));
        double xTempSpeed = xSpeed * Math.sin(Math.toRadians(rotation));
        double currSpeed = Math.sqrt(xTempSpeed * xTempSpeed + yTempSpeed * yTempSpeed);
        xSpeed += (0.01 * Math.sin(Math.toRadians(rotation)));
        ySpeed += (0.01 * Math.cos(Math.toRadians(rotation)));
        xLoc += xSpeed;
        yLoc -=  ySpeed;
        if (currSpeed > 2) {
            ySpeed /= currSpeed / 2;
            xSpeed /= currSpeed / 2;
        }

    }

    void shoot(Shot shot) {
        shots++;
        //OFFSETS THE LOCATION OF THE SHOT SO IT WILL START FROM MIDDLE OF SHIP
        shot.setxLoc(shipImage.getLayoutX() + 9); // displace shot by half of the size of the ship
        shot.setyLoc(shipImage.getLayoutY() + 10);
        //SETS ANGLE OF SHOT TO BE USED IN THE SHOT MOVE METHOD
        shot.setAngle(shipImage.getRotate());
        //
        shot.setExists(true);
        shot.setTimeCreated(System.currentTimeMillis());
        timeLastShot = System.currentTimeMillis();
    }

    boolean canShoot() {
        if (System.currentTimeMillis() - timeLastShot > 100 && !dead) {
            return true;
        } else {
            return false;
        }
    }

    void drift() {
 
        xLoc += xSpeed;
        yLoc -= ySpeed;
        if (xSpeed > 0){
            xSpeed -= 0.005;
        }
        if (xSpeed < 0){
            xSpeed += 0.005;
        }
        if (ySpeed > 0){
            ySpeed -= 0.005;
        }
        if (ySpeed < 0){
            ySpeed += 0.005;
        }


        
    }
    void respawn(){
        dead = false;
        xLoc = 640;
        yLoc = 480;
        rotation = 0;
        this.getSprite().setVisible(true);
        shipImage.setRotate(0);
        xSpeed = 0;
        ySpeed = 0;
    }
    void despawn(){
        dead = true;
        this.getSprite().setVisible(false);
    }

}
