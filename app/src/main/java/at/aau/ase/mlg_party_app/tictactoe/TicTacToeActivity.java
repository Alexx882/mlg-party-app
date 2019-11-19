package at.aau.ase.mlg_party_app.tictactoe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import at.aau.ase.mlg_party_app.MainActivity;
import at.aau.ase.mlg_party_app.R;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
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
        gameTable.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        gameTable.setShrinkAllColumns(true);
        gameTable.setStretchAllColumns(true);
        for(int x=0; x<gameLogic.getBoardSize(); x++) {
            TableRow row = new TableRow(this);
            for(int y=0;y<gameLogic.getBoardSize(); y++) {
                TextView cell = new TextView(this);
                cell.setText(" ");
                cell.setTextSize(50);
                cell.setGravity(Gravity.CENTER);
                cell.setHeight(200);
                cell.setBackground(borderHelper());

                // Use id and Tag to identify the clicked cell!
                cell.setId(x);
                cell.setTag(y);

                cell.setOnClickListener(v -> cellClickedHandler(v.getId(),(int)v.getTag()));

                row.addView(cell);
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
        TextView cell= (TextView)row.getChildAt(y);

        switch(gameLogic.checkMoveStatus(x,y,pId)){
            case 0:
                //SHOW ENDGAME DIALOG + Move to Main-Activity
                showEndOfGameDialog(pId);
                break;
            case 1:
                gameMessage.setText(R.string.tictactoe_ongoing);
                if(pId==1){
                    cell.setText("X");
                    cell.setBackgroundColor(Color.BLUE);
                    playerId=2; //For Singleplayer
                }else {
                    cell.setText("O");
                    cell.setBackgroundColor(Color.RED);
                    playerId=1;//For Singleplayer
                }

                break;
            case 2:
                gameMessage.setText(R.string.tictactoe_invalidmove);
                break;
        }
    }

    //To paint a border around the cells of the game board (gets replaced after a move)
    ShapeDrawable borderHelper(){
        ShapeDrawable border = new ShapeDrawable();
        border.setShape(new RectShape());
        border.getPaint().setColor(Color.BLACK);
        border.getPaint().setStrokeWidth(10f);
        border.getPaint().setStyle(Paint.Style.STROKE);
        return border;
    }

    private void showEndOfGameDialog( int pId){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle(getString(R.string.tictactoe_gamewon)+" "+pId);
        builder.setMessage(R.string.backtolobby);
        builder.setPositiveButton(android.R.string.ok, (dialog, id) -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
        AlertDialog endGameDialog=builder.create();
        endGameDialog.show();
    }


}
