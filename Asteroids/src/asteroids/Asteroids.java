/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import myutils.MyArrayList;
import myutils.MyFile;

/* 
SPACE TO SHOOT
W TO GO FORWARD
A TO ROTATE LEFT
D TO ROTATE RIGHT


*/



public class Asteroids extends Application {
    private int numOfAsteroids;
    private final int WINDOW_WIDTH = 1280;
    private final int WINDOW_HEIGHT = 960;
    private boolean isRotatingLeft, isRotatingRight, isThrusting, isShooting, gameOver;
    private Shot shot1,shot2,shot3,shot4;
    private ArrayList<Shot> shotList = new ArrayList<>();
    private ArrayList<Asteroid> asteroidList = new ArrayList<>();
    private ArrayList<Asteroid> deadList = new ArrayList<>();
    private Ship ship;
    private Asteroid asteroid;
    private Asteroid asteroidChild;
    private Pane gamePane;
    private int score;
    private int lives;
    private int bonusLife;
    private Label scoreLabel, highScoreLabel, bonusLabel, gameOverLabel, listOfScoresLabel, congratsLabel, continueLabel, mainMenuLabel;
    private HBox livesBox, menuBox;
    private AnimationTimer gameTimer;
    private Scene gameWindow, menuWindow;
    private Stage stage;
    private Pane rootPane, menuPane;
    private TextArea area;
    private ImageView mainMenu;
    
    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        mainMenu();


    }
    public static void main(String[] args) {
        launch(args);
    }
    private void update() {
        //update ship variables based off of booleans
        if (isRotatingLeft){
            ship.rotateLeft();
        }
        if (isRotatingRight){
            ship.rotateRight();
        }
        if (isThrusting){
            ship.thrust();
            ship.setIsMoving(true);
        }
        if (!isThrusting){
            ship.drift();
            ship.setIsMoving(false);

        }
        if (isShooting){
             if (!shot1.isExists() && ship.canShoot()){
                ship.shoot(shot1);
            }
            if (!shot2.isExists() && ship.canShoot()){
                ship.shoot(shot2);
            }
            if (!shot3.isExists() && ship.canShoot()){
                ship.shoot(shot3);
            }
            if (!shot4.isExists() && ship.canShoot()){
                ship.shoot(shot4);
            }   
        }
        //update shot variables
        for (Shot shot : shotList) {
            if (shot.isExists()){
                if (!shot.checkTime()){
                    shot.setVisible(false);
                    shot.reset();
                }
                else{
                    shot.move();
                    shot.setVisible(true);
                }
            }
        }
        //update asteroid variables
        
        //update gui
        scoreLabel.setText(score + "");
        if (score >= bonusLife){
            bonusLife += 10000;
            bonusLabel.setText("Bonus at " + bonusLife);
            lives++;
            addLives(lives);
           
            
        }
    }
    private void draw() {
        //draw ship
        ship.getSprite();
        //draw shots
        for (Shot shot : shotList) {
            shot.setLayoutX(shot.getxLoc());
            shot.setLayoutY(shot.getyLoc());
        }
        //draw asteroid
        for (Asteroid ast : asteroidList) {
            if (ast.exists){
                ast.move();
                ast.rotate();
            }
        }
        
    }
    private void setup(){
        //variables to be reset when game is started
        score = 0;
        numOfAsteroids = 3;
        lives = 3;
        bonusLife = 10000;
        gameOver = false;
        //load fonts
        Font font16 = null;
        Font font32 = null;
        Font font48 = null;
        try {
            font32 = Font.loadFont(new FileInputStream(new File("./resources/font/HyperspaceBold-GM0g.ttf")), 30);
             font16 = Font.loadFont(new FileInputStream(new File("./resources/font/HyperspaceBold-GM0g.ttf")), 16);
             font48 = Font.loadFont(new FileInputStream(new File("./resources/font/HyperspaceBold-GM0g.ttf")), 48);
        } catch (Exception e) {
        }
        //setup text area
        area = new TextArea();
        area.setLayoutX(600);
        area.setLayoutY(700);
        area.setFont(font32);
        area.setVisible(false);
        area.setDisable(true);


        
        //init ship
        ship = new Ship();
        //init shot
        shot1 = new Shot();
        shot2 = new Shot();
        shot3 = new Shot();
        shot4 = new Shot();
        shot1.setVisible(false);
        shot2.setVisible(false);
        shot3.setVisible(false);
        shot4.setVisible(false);
        //init asteroids
        spawnAsteroids();
//        for (int i = 0; i < 3; i++){
//            asteroid = new Asteroid();
//            asteroidList.add(asteroid);
//        }
//        for (Asteroid asteroid : asteroidList) {
//            gamePane.getChildren().add(asteroid.getSprite());
//        }
        //setup lists
        shotList.add(shot1);
        shotList.add(shot2);
        shotList.add(shot3);
        shotList.add(shot4);
        
        //setup panes
        gamePane.getChildren().add(shot1);
        gamePane.getChildren().add(shot2);
        gamePane.getChildren().add(shot3);
        gamePane.getChildren().add(shot4);
        gamePane.getChildren().add(ship.getSprite());
        //setup/init current score label
        scoreLabel = new Label(score + "");
        scoreLabel.setTextFill(Color.WHITE);
        scoreLabel.setLayoutY(50);
        scoreLabel.setFont(font32);
        scoreLabel.setTextAlignment(TextAlignment.CENTER);
        //setup/init high score label
        //get first high score in presorted high score file
         MyArrayList highScores = new MyArrayList();
        highScores = getHighScores();
        if (highScores == null){
           highScoreLabel = new Label();
        }
        else{
             highScoreLabel = new Label(highScores.get(0).toString());
        }
        highScoreLabel.setLayoutY(50);
        highScoreLabel.setTextFill(Color.WHITE);
        highScoreLabel.setTextAlignment(TextAlignment.CENTER);
        highScoreLabel.setFont(font32);
        //setup/init lives label
        livesBox = new HBox();
        livesBox.setLayoutY(85);
        livesBox.setSpacing(5);
        ImageView lifeImage; 
        for (int i = 0; i < lives; i++) {
            lifeImage = new ImageView(new Image("file:resources/assets/shipv2.png"));
            livesBox.getChildren().add(lifeImage);
        }

        //setup/init bonus lives label
        bonusLabel = new Label("Bonus at " + bonusLife);

        
        bonusLabel.setLayoutY(85);
        bonusLabel.setTextFill(Color.WHITE);
        bonusLabel.setFont(font16);

        //setup/init game over label
        gameOverLabel = new Label("GAME OVER");

        gameOverLabel.setTextFill(Color.WHITE);
        gameOverLabel.setLayoutY(192);
        gameOverLabel.setVisible(false);
        gameOverLabel.setFont(font48);

        //setup/init list of high scores
         listOfScoresLabel = new Label("");

       
        
        listOfScoresLabel.setTextAlignment(TextAlignment.LEFT);
        listOfScoresLabel.setTextFill(Color.WHITE);
        listOfScoresLabel.setLayoutY(300);
        listOfScoresLabel.setFont(font48);

        listOfScoresLabel.setVisible(false);
        //setup/init high score congratulation
        congratsLabel = new Label("YOUR SCORE IS ONE OF THE 5 BEST \nPLEASE ENTER YOUR INITIALS\nPRESS ENTER WHEN FINISHED");
        congratsLabel.setFont(font32);
        congratsLabel.setTextFill(Color.WHITE);
        congratsLabel.setLayoutY(WINDOW_HEIGHT / 2);
        congratsLabel.setVisible(false);
        //label for continuing after game over
        continueLabel = new Label("PRESS ENTER TO CONTINUE");
        continueLabel.setFont(font32);
        continueLabel.setTextFill(Color.WHITE);
        continueLabel.setLayoutY(800);
        continueLabel.setVisible(false);

        
        
    }
    
    private void boundsCheck(){
        //SHIP CHECK
        if (!ship.getIsDead()){
            if (ship.getSprite().getBoundsInParent().getMinX() < 0 - ship.getSprite().getBoundsInParent().getWidth()){
              ship.setxLoc(ship.getxLoc() + WINDOW_WIDTH + ship.getSprite().getBoundsInParent().getWidth());
            }
            else if (ship.getSprite().getBoundsInParent().getMinX() > WINDOW_WIDTH + ship.getSprite().getBoundsInParent().getWidth()){
                ship.setxLoc(ship.getxLoc() - (WINDOW_WIDTH + ship.getSprite().getBoundsInParent().getWidth()) );
            }
            else if (ship.getSprite().getBoundsInParent().getMinY() < 0-  ship.getSprite().getBoundsInParent().getWidth()){
              ship.setyLoc(ship.getyLoc() + WINDOW_HEIGHT + ship.getSprite().getBoundsInParent().getWidth());
            }
            else if (ship.getSprite().getBoundsInParent().getMinY() > WINDOW_HEIGHT + ship.getSprite().getBoundsInParent().getWidth()){
                ship.setyLoc(ship.getyLoc() - (WINDOW_HEIGHT + ship.getSprite().getBoundsInParent().getWidth()) );
            }
        }
        
        //shot check
        for (Shot shot : shotList) {
            if (shot.isExists()){   
                if (shot.getBoundsInParent().getMinX() < 0 - shot.getBoundsInParent().getWidth()){
                    shot.setxLoc(shot.getxLoc() + WINDOW_WIDTH + shot.getBoundsInParent().getWidth());
                }
                else if (shot.getBoundsInParent().getMinX() > WINDOW_WIDTH + shot.getBoundsInParent().getWidth()){
                    shot.setxLoc(shot.getxLoc() - (WINDOW_WIDTH + shot.getBoundsInParent().getWidth()) );
                }
                else if (shot.getBoundsInParent().getMinY() < 0 -  shot.getBoundsInParent().getWidth()){
                    shot.setyLoc(shot.getyLoc() + WINDOW_HEIGHT + shot.getBoundsInParent().getWidth());
                }
                else if (shot.getBoundsInParent().getMinY() > WINDOW_HEIGHT + shot.getBoundsInParent().getWidth()){
                    shot.setyLoc(shot.getyLoc() - (WINDOW_HEIGHT + shot.getBoundsInParent().getWidth()) );
                }
            }
        }
        
        //asteroid check
        for (Asteroid asteroid : asteroidList){
             if (asteroid.isExists()){   
                if (asteroid.getSprite().getBoundsInParent().getMinX() < 0 - asteroid.getSprite().getBoundsInParent().getWidth()){
                    asteroid.setxLoc(asteroid.getxLoc() + WINDOW_WIDTH + asteroid.getSprite().getBoundsInParent().getWidth());
                }
                else if (asteroid.getSprite().getBoundsInParent().getMinX() > WINDOW_WIDTH + asteroid.getSprite().getBoundsInParent().getWidth()){
                    asteroid.setxLoc(asteroid.getxLoc() - (WINDOW_WIDTH + asteroid.getSprite().getBoundsInParent().getWidth()) );
                }
                else if (asteroid.getSprite().getBoundsInParent().getMinY() < 0 -  asteroid.getSprite().getBoundsInParent().getWidth()){
                    asteroid.setyLoc(asteroid.getyLoc() + WINDOW_HEIGHT + asteroid.getSprite().getBoundsInParent().getWidth());
                }
                else if (asteroid.getSprite().getBoundsInParent().getMinY() > WINDOW_HEIGHT + asteroid.getSprite().getBoundsInParent().getWidth()){
                    asteroid.setyLoc(asteroid.getyLoc() - (WINDOW_HEIGHT + asteroid.getSprite().getBoundsInParent().getWidth()) );
                }
            }
        }
        
    }
    
    private void collisionCheck(){
        deadList.clear();
        for (Asteroid ast : asteroidList){
            if (ast.isExists()){
                if (ast.getSprite().getBoundsInParent().intersects(ship.getSprite().getBoundsInParent())){
                    if (lives > 0 && !ship.getIsDead()){
                       ship.despawn();
                        lives--;
                        removeLives(lives);
                    }
                    else if (lives == 0){
                        gameOver();
                        gameOver = true;
                    }
                }
              
                

                for (Shot shot : shotList){
                    if (shot.isExists()){
                        if (ast.getSprite().getBoundsInParent().intersects(shot.getBoundsInParent())){
                            shot.reset();
                            shot.setVisible(false);
                            //HAVE TO ADD ASTEROID TO LIST OF DEAD BECAUSE IF REMOVE WHILE ITERATING COMPILER FREAKS OUT 
                           deadList.add(ast);

                        }
                    }
                }
            }
        }
        for (Asteroid ast : deadList){
            if (ast.getSize() > 1){
                
                asteroidChild = ast.spawnChild();
                asteroidList.add(asteroidChild);
                gamePane.getChildren().add(asteroidChild.getSprite());
                asteroidChild = ast.spawnChild();
                asteroidList.add(asteroidChild);
                gamePane.getChildren().add(asteroidChild.getSprite());
                
            }
            
            addPoints(ast);
            ast.setExists(false);
            gamePane.getChildren().remove(ast.getSprite());
            asteroidList.remove(ast);
        }
    }
    
    private void levelCheck(){
        boolean levelIsOver = true;
        for (Asteroid asteroid : asteroidList) {
            if (asteroid.isExists()){
                levelIsOver = false;
            }
        }
        if (levelIsOver){
            startNewLevel();
        }
    }

    private void startNewLevel() {
        numOfAsteroids++;
        asteroidList.clear();
        spawnAsteroids();
    }

    private void spawnAsteroids() {
        for (int i = 0; i < numOfAsteroids; i++){
            asteroid = new Asteroid();
            asteroidList.add(asteroid);
        }
        for (Asteroid ast : asteroidList) {
            while(!(ast.getxLoc() >= ship.getxLoc() + 100) && !(ast.getxLoc() <= ship.getxLoc() - 100) &&
                    !(ast.getyLoc() >= ship.getyLoc() + 100) && !(ast.getyLoc() <= ship.getyLoc() - 100)){
               ast.setxLoc(Math.random() * 1280);
               ast.setyLoc(Math.random() * 960);
            }
            gamePane.getChildren().add(ast.getSprite());
        }
    }

    private void addPoints(Asteroid ast) {
        switch (ast.getSize()){
            case (3): score+=20;break;
            case (2): score+=50;break;
            case (1): score+=100;break;
        }

    }

    private MyArrayList getHighScores() {
        MyArrayList list = null;
        try {
           list = MyFile.readFile("./resources/score/high score.txt");
        } catch (FileNotFoundException ex) {
        }
        if (list.isEmpty()){
            return null;
        }
        else{
            return list;
        }
    }

    private void addLives(int num) {
        livesBox.getChildren().remove(0, num - 1);
        ImageView lifeImage = null; 
        for (int i = 0; i < num; i++) {
            lifeImage = new ImageView(new Image("file:resources/assets/shipv2.png"));
            livesBox.getChildren().add(lifeImage);
        }
        livesBox.setLayoutX(livesBox.getLayoutX() - lifeImage.getImage().getWidth() - livesBox.getSpacing());
        
    }
    private void removeLives(int num) {
        livesBox.getChildren().remove(0, num + 1);
        if (num == 0){
            return;
        }
        ImageView lifeImage = null; 
        for (int i = 0; i < num; i++) {
            lifeImage = new ImageView(new Image("file:resources/assets/shipv2.png"));
            livesBox.getChildren().add(lifeImage);
        }
        livesBox.setLayoutX(livesBox.getLayoutX() + lifeImage.getImage().getWidth() + livesBox.getSpacing());
        
    }

    private void gameOver() {
        boolean isHighScore = false;
        gameTimer.stop();
        gameOverLabel.setVisible(true);
        highScoreLabel.setVisible(true);
        MyArrayList list = new MyArrayList();
        list = getHighScores();
        for (int i = 0; i < 5 && list != null && i < list.size(); i++){
            if (score > Integer.parseInt(list.get(i).toString().split(" ")[0])){
                isHighScore = true;
            }
        }
        if (list == null){
                isHighScore = true;
            }
        else if (list.size() < 5){
            isHighScore = true;
        }
        if (isHighScore){
            congratsLabel.setVisible(true);
            area.setVisible(true);
            area.setDisable(false);
        }
        else{
            showHighScores();
            listOfScoresLabel.setVisible(true);
            continueLabel.setVisible(true);
        }
        
        
        
    }
    private void shipStateCheck(){
        
        if (ship.getIsDead()){
            boolean waitToRespawn = false;
            for (Asteroid ast : asteroidList) {
                double d = Math.sqrt(Math.pow(ast.getxLoc() - ship.getxLoc(), 2) + Math.pow(ast.getyLoc() - ship.getyLoc(), 2));
                    if (d < 100){
                        System.out.println(d);
                        waitToRespawn = true;
                        System.out.println("Asteroid is too close");


                    }
                    else{
                        System.out.println("not waiting");
                    }
            }   
            if (!waitToRespawn){
                ship.respawn();
            }
        }
    }

    private void setHighScores(int num, String name) {
        MyArrayList list = new MyArrayList();
        try {
           list = MyFile.readFile("./resources/score/high score.txt");
        } catch (Exception e) {
        }
        list.add(num + " " + name);

        for (int i = 0; i < list.size(); i++) {
            Object key = list.get(i);
            int j = i - 1;
            while (j >= 0 && Integer.parseInt(list.get(j).toString().split(" ")[0]) < Integer.parseInt(key.toString().split(" ")[0])){
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, key);
                
        }
        MyFile.writeFile(list, "./resources/score/high score.txt");
        showHighScores();

        
    }

//    private String getPlayerName() {
//        char[] letters = "ABCDEFGHIJLKMNOPQRSTUVWXYZ".toCharArray();
//        ArrayList<Label> list = new ArrayList();


//        area.setLayoutX(WINDOW_WIDTH / 2 - area.getWidth());
//        area.setLayoutY(700);
//        area.setOnKeyPressed(new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent event) {
//                if (event.getCode() == KeyCode.ENTER){
//                   playerName = area.getText();
//                }
//            }
//        });
        
        
//    }
    private void showHighScores(){
        MyArrayList list = new MyArrayList();
        list = getHighScores();
        String scores = "High Scores \n";
        if (list != null){
            for (int i = 0; i < 5 && i < list.size();i++){

                if (list.get(i).toString() != null){
                   scores += i+1 + ". " + list.get(i).toString() + "\n";
                }
            }
        }
        listOfScoresLabel.setText(scores);
    }

    private void game() {

        gamePane = new Pane();
        setup();
        
        rootPane = new Pane();
        rootPane.getChildren().add(gamePane);
        rootPane.getChildren().add(scoreLabel);
        rootPane.getChildren().add(livesBox);
        rootPane.getChildren().add(highScoreLabel);
        rootPane.getChildren().add(bonusLabel);
        rootPane.getChildren().add(gameOverLabel);
        rootPane.getChildren().add(listOfScoresLabel);
        rootPane.getChildren().add(congratsLabel);       
        rootPane.getChildren().add(area);
        rootPane.getChildren().add(continueLabel);
        rootPane.getChildren().add(menuPane);

        gameWindow  = new Scene(rootPane, menuWindow.getWidth(), menuWindow.getHeight(), Color.BLACK);
        gameWindow.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        
        stage.setScene(gameWindow);
        stage.show();
        
        //has to be after the stage is shown or cannot get the width of the labels to properly center them based off of their text length
        highScoreLabel.setLayoutX(WINDOW_WIDTH / 2 - highScoreLabel.getWidth() / 2);
        bonusLabel.setLayoutX(WINDOW_WIDTH / 2 - bonusLabel.getWidth() / 2);
        livesBox.setLayoutX(WINDOW_WIDTH / 4 - livesBox.getWidth() / 2);
        gameOverLabel.setLayoutX(WINDOW_WIDTH / 2 - gameOverLabel.getWidth() / 2);
        congratsLabel.setLayoutX(WINDOW_WIDTH / 2 - congratsLabel.getWidth() / 2);
        listOfScoresLabel.setLayoutX(480);
        continueLabel.setLayoutX(WINDOW_WIDTH / 2 - continueLabel.getWidth() / 2);
        scoreLabel.setLayoutX(285);
        mainMenu.setLayoutX(WINDOW_WIDTH / 2 - mainMenu.getFitWidth() / 2);
        menuBox.setLayoutX(WINDOW_WIDTH / 2 - menuBox.getWidth() / 2);


        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                draw();
                boundsCheck();
                collisionCheck();
                levelCheck();
                shipStateCheck();
            }
        };
        gameTimer.start();
        
        
        gameWindow.setOnKeyPressed(e ->{
            switch (e.getCode()){
                case A: isRotatingLeft = true;break;
                case D: isRotatingRight = true;break;
                case W: isThrusting = true;break;
                case SPACE: isShooting = true;break;
                case ENTER: 
                    if (gameOver)    {               
                    continueLabel.setVisible(false);
                    asteroidList.clear();
                    game();
                    }
            }
        });
        gameWindow.setOnKeyReleased(e ->{
            switch (e.getCode()){
                case A: isRotatingLeft = false;break;
                case D: isRotatingRight = false;break;
                case W: isThrusting = false;break;
                case SPACE: isShooting = false;break;
            }
        });

        area.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER){
                    congratsLabel.setVisible(false);
                    setHighScores(score, area.getText());
                    area.setVisible(false);
                    listOfScoresLabel.setVisible(true);
                    continueLabel.setVisible(true);
                }
            }
        });
       
        


    }

    private void mainMenu() {
        Font menuFont = null;
        try {
            menuFont = Font.loadFont(new FileInputStream(new File("./resources/font/HyperspaceBold-GM0g.ttf")), 30);
        } catch (Exception e) {
        }
        //label for main menu
        Label mainLabel = new Label("Press ENTER to start");
        //image and pane for main menu
        mainMenu = new ImageView();
        mainMenu.setImage(new Image("file:resources/images/title.png"));
        menuBox = new HBox();
        menuBox.getChildren().add(mainMenu);
        menuPane = new Pane();
        menuPane.getChildren().add(menuBox);
        menuPane.getChildren().add(mainLabel);
        menuWindow = new Scene(menuPane, WINDOW_WIDTH, WINDOW_HEIGHT, Color.BLACK);
        stage.setScene(menuWindow);
        stage.setResizable(false);
        stage.show();
        menuBox.setLayoutY(480);
        menuBox.setLayoutX(WINDOW_WIDTH / 2 - menuBox.getWidth() / 2);
        mainLabel.setLayoutY(640);
        mainLabel.setFont(menuFont);
        mainLabel.setTextFill(Color.WHITE);
        menuWindow.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        mainLabel.setLayoutX(WINDOW_WIDTH / 2 - 200);
        menuWindow.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER){
                     menuBox.setVisible(false);
                     mainLabel.setVisible(false);
                     game();

                }
            }
        });
    }
}
