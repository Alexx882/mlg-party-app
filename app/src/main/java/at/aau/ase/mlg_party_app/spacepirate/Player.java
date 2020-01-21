package at.aau.ase.mlg_party_app.spacepirate;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import at.aau.ase.mlg_party_app.R;

public class Player {
    private Bitmap bitmap;
    private int x;
    private int y;
    private int speed = 0;
    private int maxY;
    private int minY;
    private int maxX;
    private int minX;


    private Rect detectCollision;

    public Player(Context context, int screenX, int screenY) {

        speed = 0;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.player);
        maxY = screenY - bitmap.getHeight();
        minY = 0;
        maxX = screenX - bitmap.getHeight();
        minX = 0;
        x = maxX / 2;
        y = 0;

        //initializing rect object
        detectCollision = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }

    public void setRight() {
        speed = 10;
    }

    public void setLeft() {
        speed = -10;
    }

    public void dontmove() {
        speed = 0;
    }


    public void update() {


        x += speed;


        if (x < minX) {
            x = minX;
        }
        if (x > maxX) {
            x = maxX;
        }

        //adding top, left, bottom and right to the rect object
        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x + bitmap.getWidth();
        detectCollision.bottom = y + bitmap.getHeight();

    }

    //one more getter for getting the rect object
    public Rect getDetectCollision() {
        return detectCollision;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }
}
