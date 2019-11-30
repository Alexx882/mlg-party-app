package at.aau.ase.mlg_party_app.tictactoe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import at.aau.ase.mlg_party_app.MainActivity;
import at.aau.ase.mlg_party_app.R;
import at.aau.ase.mlg_party_app.tictactoe.Handlers.DrawableHandler;


import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class TicTacToeActivity extends AppCompatActivity {
    //Variable Setup
    TicTacToeLogic gameLogic;
    int playerId; // Should be FINAL -> and handled at serverside
    TextView gameMessage;
    TableLayout gameTable;
    DrawableHandler imgHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);
        initComponents();
        createGameBoard();
    }

    private void initComponents() {
        imgHandler=new DrawableHandler(this);
        gameLogic=new TicTacToeLogic();
        playerId=1;
        gameTable=findViewById(R.id.gameTable);
        gameMessage=findViewById(R.id.tVGameMessage);
    }

    private void createGameBoard(){
        gameTable.setLayoutParams(new TableLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        gameTable.setShrinkAllColumns(true);
        gameTable.setStretchAllColumns(true);
        gameTable.setBackgroundResource(R.drawable.borderdrawable);
        for(int x=0; x<gameLogic.getBoardSize(); x++) {
            TableRow row = new TableRow(this);
            for(int y=0;y<gameLogic.getBoardSize(); y++) {
                //Create the ImmageView for the cell and place it in the table
                row.addView(createCell(x,y));
            }
            gameTable.addView(row);
        }
    }

    private void cellClickedHandler(int x , int y){
        //Replace with CheckMove + Send move to Server
        addMove(x,y,playerId);
    }

    //should get called after respone from server
     void addMove(int x, int y, int pId){
        TableRow row = (TableRow)gameTable.getChildAt(x);
        ImageView cell= (ImageView)row.getChildAt(y);

        switch(gameLogic.checkMoveStatus(x,y,pId)){
            case 0:
                //SHOW ENDGAME DIALOG + Move to Main-Activity
                cell.setImageBitmap(imgHandler.drawPlayedCell(pId));
                showEndOfGameDialog(true);
                break;
            case 1:
                gameMessage.setText(R.string.tictactoe_ongoing);
                if(pId==1){
                    cell.setImageBitmap(imgHandler.drawPlayedCell(pId));
                    playerId=2; //For Singleplayer
                }else {
                    cell.setImageBitmap(imgHandler.drawPlayedCell(pId));
                    playerId=1;//For Singleplayer
                }

                break;
            case 2:
                gameMessage.setText(R.string.tictactoe_invalidmove);
                break;
            case 3:
                cell.setImageBitmap(imgHandler.drawPlayedCell(pId));
                showEndOfGameDialog(false);
                break;
        }
    }

    private ImageView createCell(int x, int y){
        ImageView cell = new ImageView(this);
        cell.setScaleType(ImageView.ScaleType.FIT_XY);
        cell.setImageBitmap(imgHandler.emptyField());
        // Use id and Tag to identify the clicked cell!
        cell.setId(x);
        cell.setTag(y);
        cell.setOnClickListener(v -> cellClickedHandler(v.getId(),(int)v.getTag()));
        return cell;
    }


    private void showEndOfGameDialog( boolean winner){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        if(winner) {
            builder.setTitle(getString(R.string.tictactoe_gamewon));
        }else{
            builder.setTitle(getString(R.string.tictactoe_gamelost));
        }
        builder.setMessage(R.string.backtolobby);
        builder.setPositiveButton(android.R.string.ok, (dialog, id) -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
        AlertDialog endGameDialog=builder.create();
        endGameDialog.show();
    }


}
