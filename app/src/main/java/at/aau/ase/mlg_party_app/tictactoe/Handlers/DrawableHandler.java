package at.aau.ase.mlg_party_app.tictactoe.Handlers;

import android.content.Context;
import android.graphics.drawable.Drawable;

import at.aau.ase.mlg_party_app.R;

public class DrawableHandler {

    int [] gameCellImageRecource;
    Context context;
    public DrawableHandler(Context context) {
        this.context=context;
        gameCellImageRecource=new int[]{
                R.drawable.tictactoe_mlg,
                R.drawable.tictactoe_doritos,
                R.drawable.tictactoe_illuminati,
                R.drawable.tictactoe_obey,
                R.drawable.tictactoe_grill
        };
    }

    //To paint a border around the cells of the game board (gets replaced after a move)
    public Drawable borderHelper(){
        return context.getResources().getDrawable(R.drawable.borderdrawable);
    }
    public Drawable drawPlayedCell(int picID){
         if(picID>=0 && picID<gameCellImageRecource.length){
             return context.getResources().getDrawable(gameCellImageRecource[picID]);
         }else{
             return null;
        }

    }
}
