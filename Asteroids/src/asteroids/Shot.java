/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids;

import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author peppe
 */
public class Shot extends Circle{
    private final static int SHOT_SPEED = 3;
    private long timeCreated = 0;
    private long timeAlive = 0;
    private boolean exists;
    double xLoc, yLoc;
    private double angle;
    public Shot(){
        super(0,0,5, Color.WHITE);
        
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

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void setTimeCreated(long timeCreated) {
        this.timeCreated = timeCreated;
    }
    
    
    public void move(){
        xLoc += SHOT_SPEED * Math.sin(Math.toRadians(angle));
        yLoc -= SHOT_SPEED * Math.cos(Math.toRadians(angle));

        
    }

    boolean checkTime() {
        long currentTime = System.currentTimeMillis();
        long timeAlive = currentTime - timeCreated;

        if (timeAlive > 2 * 1000){
            exists = false;
            return false;
        }
        return true;
        
         
    }

    void reset() {
        xLoc = 0;
        yLoc = 0;
        exists = false;
        
    }
    
    
    
}
