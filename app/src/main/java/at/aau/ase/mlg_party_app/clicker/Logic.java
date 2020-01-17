package at.aau.ase.mlg_party_app.clicker;

import android.content.Context;
import android.os.Vibrator;

import java.security.SecureRandom;

import at.aau.ase.mlg_party_app.R;

public class Logic {

    private int counter=0;
    private int fontsize=20;


    public String cookiecounter() {
        setCounter(getCounter()+1);
        if (counter == 69) {
            return "Nice";
        } else
            return String.valueOf(getCounter());

    }


    public int imagesetup() {
        int imageid=0;
        if (counter == 15) {
            imageid=R.drawable.lvl15;
        } else if (counter == 30) {
           imageid=R.drawable.lvl30;
        } else if (counter == 45) {
           imageid=R.drawable.lvl45;
        } else if (counter == 60)
           imageid=R.drawable.lvl60;
        else if (counter == 69)
            imageid=R.drawable.lvl69;
        else if (counter == 70)
            imageid=R.drawable.lvl60;
        else if (counter == 75) {
            imageid=R.drawable.endboss;
        }
        return imageid;

    }

    public int gifsetup() {
        int gifid=0;
        if (counter == 75)
            gifid=R.drawable.asci;

        return gifid;

    }


    public int rand (int x, int y){

        return new SecureRandom().nextInt(x) - y;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getFontsize() {
        return fontsize;
    }

    public void setFontsize(int fontsize) {
        this.fontsize = fontsize;
    }

    public int incsize() {
        if (fontsize >= 100)
            return fontsize;
        else {
            fontsize++;
            return fontsize;
        }
    }

    public void vibrate(Context context){

        Vibrator click = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        click.vibrate(50);

    }


}
