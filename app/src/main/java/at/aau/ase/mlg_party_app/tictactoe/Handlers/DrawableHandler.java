package at.aau.ase.mlg_party_app.tictactoe.Handlers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import at.aau.ase.mlg_party_app.R;

public class DrawableHandler {
    final private int scaleSize= 200;

    private int [] gameCellImageRecource;
    private Context context;
    public DrawableHandler(Context context) {
        this.context=context;
        gameCellImageRecource=new int[]{
                R.drawable.tictactoe_doritos,
                R.drawable.tictactoe_mntdew,
                R.drawable.tictactoe_mlg,
                R.drawable.tictactoe_illuminati,
                R.drawable.tictactoe_obey,
                R.drawable.tictactoe_grill,
                R.drawable.tictactoe_wow
        };
    }

    //To paint a border around the cells of the game board (gets replaced after a move)
    public Bitmap emptyField(){
        return scaleDrawables(R.drawable.tictactoe_emptyfield);
    }


    public Bitmap drawPlayedCell(int picID){
        //TMP while pid gets used
        picID-=1;
        if(picID>=0 && picID<gameCellImageRecource.length){
            return scaleDrawables(gameCellImageRecource[picID]);
        }else{
            return scaleDrawables(gameCellImageRecource[0]);
        }
    }


    private Bitmap scaleDrawables(int resourceID){
        Drawable picDrawable=context.getResources().getDrawable(resourceID);
        Bitmap pic = ((BitmapDrawable)picDrawable).getBitmap();
        return Bitmap.createScaledBitmap(pic, scaleSize,scaleSize,true);
    }
}
