package at.aau.ase.mlg_party_app.spacepirate;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.security.SecureRandom;

import at.aau.ase.mlg_party_app.R;

public class Friend {

    private Bitmap bitmap;
    private int x;
    private int y;
    private int speed = 1;

    private int maxX;
    private int minX;

    private int maxY;
    private int minY;
    private SecureRandom generator;

    //creating a rect object for a friendly ship
    private Rect detectCollision;


    public Friend(Context context, int screenY, int screenX) {
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.voss);
        maxX = screenX;
        maxY = screenY;
        minX = 0;
        minY = 0;
        generator = new SecureRandom();
        speed = generator.nextInt(6) + 10;
        x = generator.nextInt(maxX) - bitmap.getHeight();
        y = screenY;

        //initializing rect object
        detectCollision = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }

    public void update(int playerSpeed) {
        y -= speed;
        if (y < minY - bitmap.getWidth()) {
            speed = generator.nextInt(10) + 10;
            y = maxY;
            x = generator.nextInt(maxX) - bitmap.getHeight();
        }

        //Adding the top, left, bottom and right to the rect object
        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x + bitmap.getWidth();
        detectCollision.bottom = y + bitmap.getHeight();
    }


    //one more getter for getting the rect object
    public Rect getDetectCollision() {
        return detectCollision;
    }

    //getters
    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
