import javax.sound.sampled.Line;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.lang.Math;

public class EkremDegirmenci {
    private static void drawEnvironment(double[][] obstacleArray, double[][] targetArray, double bulletAngle, double bulletVelocity, double x0, double y0, double theta) {  //drawing obstacles, targets and line
        StdDraw.clear();

        // Drawing obstacles
        StdDraw.setPenColor(StdDraw.DARK_GRAY);
        for (double[] obstacle : obstacleArray) {
            StdDraw.filledRectangle(obstacle[0] + obstacle[2] / 2, obstacle[1] + obstacle[3] / 2, obstacle[2] / 2, obstacle[3] / 2);
        }

        // Drawing targets
        StdDraw.setPenColor(StdDraw.PRINCETON_ORANGE);
        for (double[] target : targetArray) {
            StdDraw.filledRectangle(target[0] + target[2] / 2, target[1] + target[3] / 2, target[2] / 2, target[3] / 2);
        }

        // Drawing shooting platform
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.filledRectangle(60, 60, 60, 60);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(60, 60, "a: " + String.format("%.1f", bulletAngle));
        StdDraw.text(60, 40, "v: " + String.format("%.1f", bulletVelocity));
        drawShootingLine(x0, y0, theta, bulletVelocity);
        StdDraw.show();
    }

    private static void drawShootingLine(double x0, double y0, double theta, double bulletVelocity) { //drawing shooting line
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.009);
        double x1 = x0 + bulletVelocity * 0.40 * Math.cos(theta); //end points
        double y1 = y0 + bulletVelocity * 0.40 * Math.sin(theta);
        StdDraw.line(x0, y0, x1, y1);
    }

    public static boolean checkObstacleCollision(double x, double y, double[][] obstacleArray) { //checking whether it hit the obstacle or not
        for (double[] obstacle : obstacleArray) {
            double obsX = obstacle[0];
            double obsY = obstacle[1];
            double width = obstacle[2];
            double height = obstacle[3];
            if (x >= obsX && x <= obsX + width && y >= obsY && y <= obsY + height) {
                return true; // Collision detected
            }
        }
        return false; // No collision
    }

    public static boolean checkTargetCollision(double x, double y, double[][] targetArray) {//checking whether it hit the obstacle or not
        for (double[] obstacle : targetArray) {
            double obsX = obstacle[0];
            double obsY = obstacle[1];
            double width = obstacle[2];
            double height = obstacle[3];
            if (x >= obsX && x <= obsX + width && y >= obsY && y <= obsY + height) {
                return true; // Collision detected
            }
        }
        return false; // No collision
    }

    public static void main(String[] args) {
        StdDraw.enableDoubleBuffering();
        // Game Parameters
        int width = 1600; //screen width
        int height = 800; // screen height
        double gravity = 9.80665; // gravity
        double x0 = 120; // x and y coordinates of the bullet’s starting position on the platform
        double y0 = 120;
        double bulletVelocity = 180; // initial velocity
        double bulletAngle = 45.0; // initial angle
        StdDraw.setCanvasSize(width, height); //determinig canvas size
        StdDraw.setXscale(0, width); //x and y scales
        StdDraw.setYscale(0, height);

        double x00 = 120; // it will help to draw the line between two view of the ball.
        double y00 = 120;
        double theta = Math.toRadians(bulletAngle);
        double vx = bulletVelocity * Math.cos(theta);//x component of the velocity
        double vy = bulletVelocity * Math.sin(theta);//y component of the velocity
// Box coordinates for obstacles and targets
// Each row stores a box containing the following information:
// x and y coordinates of the lower left rectangle corner, width, and height
        double[][] obstacleArray = {
                {1200, 0, 60, 220},
                {1000, 0, 60, 160},
                {600, 0, 60, 80},
                {600, 180, 60, 160},
                {220, 0, 120, 180}
        };
        double[][] targetArray = {
                {1160, 0, 30, 30},
                {730, 0, 30, 30},
                {150, 0, 20, 20},
                {1480, 0, 60, 60},
                {340, 80, 60, 30},
                {1500, 600, 60, 60}
        };
        /*
        New target and obstacle arrays
        double[][] obstacleArray = {
                {1300, 0, 80, 180},
                {700, 200, 60, 100},
                {400, 0, 40, 120},
                {1000, 300, 100, 60},
                {1100, 500, 80, 80}
        };
        double[][] targetArray = {
                {1400, 0, 50, 50},
                {500, 0, 40, 40},
                {200, 100, 30, 30},
                {1000, 700, 70, 70},
                {200, 500, 60, 60}
        };
     */
        drawEnvironment(obstacleArray, targetArray, bulletAngle, bulletVelocity, x0, y0, theta); //firstly we draw the game environment.
        boolean shooting =true;
        while (shooting) {
            if (StdDraw.isKeyPressed(KeyEvent.VK_DOWN)) {
                bulletAngle -= 1;
                theta = Math.toRadians(bulletAngle);
                vx = bulletVelocity * Math.cos(theta);
                vy = bulletVelocity * Math.sin(theta);
                drawEnvironment(obstacleArray, targetArray, bulletAngle, bulletVelocity, x0, y0, theta); //it will draw the game environment again and the new shooting line.

            }
            if (StdDraw.isKeyPressed(KeyEvent.VK_UP)) {
                bulletAngle += 1;
                theta = Math.toRadians(bulletAngle);
                vx = bulletVelocity * Math.cos(theta);
                vy = bulletVelocity * Math.sin(theta);
                drawEnvironment(obstacleArray, targetArray, bulletAngle, bulletVelocity, x0, y0, theta);

            }
            if (StdDraw.isKeyPressed(KeyEvent.VK_RIGHT)) {
                bulletVelocity += 1;
                vx = bulletVelocity * Math.cos(theta);
                vy = bulletVelocity * Math.sin(theta);
                drawEnvironment(obstacleArray, targetArray, bulletAngle, bulletVelocity, x0, y0, theta);

            }
            if (StdDraw.isKeyPressed(KeyEvent.VK_LEFT)) {
                bulletVelocity -= 1;
                vx = bulletVelocity * Math.cos(theta);
                vy = bulletVelocity * Math.sin(theta);
                drawEnvironment(obstacleArray, targetArray, bulletAngle, bulletVelocity, x0, y0, theta);
            }
            StdDraw.pause(30);
            if (StdDraw.isKeyPressed(KeyEvent.VK_SPACE)) {
                double startTime = System.currentTimeMillis() / 1000.0;
                boolean spacePressed = true; // checking whether space button is pressed or not
                while (spacePressed) {
                    double currentTime = System.currentTimeMillis() / 1000.0;// Time step
                    double changeT = currentTime - startTime; //time change
                    StdDraw.setPenColor(Color.BLACK);
                    StdDraw.show();
                    double x = x0 + vx * changeT; //determining new x and y coordinate.
                    double y = y0 + vy * changeT - 0.5 * gravity * 3 * changeT * changeT; //determining new x and y coordinate.
                    StdDraw.setPenRadius(0.002);
                    StdDraw.filledCircle(x, y, 5);
                    StdDraw.line(x00, y00, x, y); //line between two balls
                    x00 = x;
                    y00 = y;
                    if (checkTargetCollision(x, y, targetArray)) {
                        StdDraw.textLeft(10, 780, "Congratulations: You hit the target!");
                        StdDraw.show();
                        spacePressed = false; //it finishes the ball movement.

                    }
                    if (checkObstacleCollision(x, y, obstacleArray)) {
                        StdDraw.textLeft(10, 780, "Hit an obstacle. Press 'r' to shoot again.");
                        StdDraw.show();
                        spacePressed = false;
                    }
                    if (y <= 0) {
                        StdDraw.textLeft(10, 780, "Hit the ground. Press 'r' to shoot again.");
                        StdDraw.show();
                        spacePressed = false;
                    }
                    if (x > width) {
                        StdDraw.textLeft(10, 780, "Max X reached. Press 'r' to shoot again.");
                        StdDraw.show();
                        spacePressed = false;

                    }
                    StdDraw.pause(60);


                }

            }
            if (StdDraw.isKeyPressed(KeyEvent.VK_R)){ //checking whether R button is pressed or not
                x00 = 120;
                x0 = 120;
                y00 = 120;
                y0 = 120;
                bulletAngle = 45.0;
                theta = Math.toRadians(bulletAngle);
                bulletVelocity = 180;
                vx = bulletVelocity * Math.cos(theta);
                vy = bulletVelocity * Math.sin(theta); //we restored the values.
                drawEnvironment(obstacleArray, targetArray, bulletAngle, bulletVelocity, x0, y0, theta); // new clean game environment.
            }
            StdDraw.pause(20);

        }
    }




}