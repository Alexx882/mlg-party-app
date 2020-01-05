package at.aau.ase.mlg_party_app.tictactoe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import at.aau.ase.mlg_party_app.MainActivity;
import at.aau.ase.mlg_party_app.R;


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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);
        initComponents();
        createGameBoard();
    }

    private void initComponents() {

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
                setGameField(cell,pId);
                showEndOfGameDialog(true);
                break;
            case 1:
                gameMessage.setText(R.string.tictactoe_ongoing);
                setGameField(cell,pId);
                break;
            case 2:
                gameMessage.setText(R.string.tictactoe_invalidmove);
                break;
            case 3:
                setGameField(cell,pId);
                showEndOfGameDialog(false);
                break;
        }
    }

    private void setGameField(ImageView gameCell, int pId){
        if(pId==1){
            gameCell.setImageResource(R.drawable.tictactoe_mlg);
            playerId=2; //For Singleplayer
        }else {
            gameCell.setImageResource(R.drawable.tictactoe_doritos);
            playerId=1;//For Singleplayer
        }
    }

    private ImageView createCell(int x, int y){
        ImageView cell = new ImageView(this);
        cell.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        //cell.setImageBitmap(imgHandler.emptyField());
        // Use id and Tag to identify the clicked cell!
       cell.setPadding(0,0,0,0);
        cell.setX(5);
        cell.setY(5);
        cell.setImageResource(R.drawable.tictactoe_emptyfield);


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
