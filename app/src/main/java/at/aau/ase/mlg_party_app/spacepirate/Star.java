package at.aau.ase.mlg_party_app.spacepirate;

import java.security.SecureRandom;

public class Star {
    private int x;
    private int y;
    private int speed;

    private int maxX;
    private int maxY;
    private int minX;
    private int minY;
    private SecureRandom generator;


    public Star(int screenX, int screenY) {
        maxX = screenX * 2;
        maxY = screenY;
        minX = 0;
        minY = 0;
        generator = new SecureRandom();
        speed = generator.nextInt(10);

        //generating a random coordinate
        //but keeping the coordinate inside the screen size
        x = generator.nextInt(maxX);
        y = generator.nextInt(maxY);
    }

    public void update(int playerSpeed) {
        //animating the star horizontally left side
        //by decreasing x coordinate with player speed
        x -= playerSpeed;
        y -= speed;
        //if the star reached the left edge of the screen
        if (y < 0) {
            //again starting the star from right edge
            //this will give a infinite scrolling background effect
            y = maxY;
            x = generator.nextInt(maxX);
            speed = generator.nextInt(15);
        }
    }

    public float getStarWidth() {
        //Making the star width random so that
        //it will give a real look
        float minX = 1.0f;
        float maxX = 4.0f;
        return generator.nextFloat() * (maxX - minX) + minX;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
