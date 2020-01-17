package at.aau.ase.mlg_party_app.tictactoe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import at.aau.ase.mlg_party_app.MainActivity;
import at.aau.ase.mlg_party_app.R;
import at.aau.ase.mlg_party_app.networking.dtos.TicTacToeEndGameResponse;
import at.aau.ase.mlg_party_app.networking.dtos.TicTacToeErrorResponse;
import at.aau.ase.mlg_party_app.networking.dtos.TicTacToeMoveRequest;
import at.aau.ase.mlg_party_app.networking.dtos.TicTacToeMoveResponse;
import at.aau.ase.mlg_party_app.networking.websocket.MessageType;
import at.aau.ase.mlg_party_app.networking.websocket.TicTacToeSocketClient;


import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class TicTacToeActivity extends AppCompatActivity {
    //Variable Setup
    TicTacToeLogic gameLogic;
    CountDownTimer timer;
    String playerId; //TODO: Get this from server
    TextView gameMessageTV;
    TextView timerTV;
    TableLayout gameTable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);
        initComponents();
        createGameBoard();
        registerTicTacToeCallbacks();
    }


    /*
            initialize all required parts of the game
         */
    private void initComponents() {

        gameLogic=new TicTacToeLogic();
        gameTable=findViewById(R.id.gameTable);
        gameMessageTV=findViewById(R.id.tVGameMessage);
        timerTV=findViewById(R.id.tVTimer);
    }

    /*
          Register the callbacks for this game
     */
    private void registerTicTacToeCallbacks(){
        TicTacToeSocketClient.getInstance().registerCallback(MessageType.TicTacToe_EndGame,this::showEndOfGameDialog);
        TicTacToeSocketClient.getInstance().registerCallback(MessageType.TicTacToe_Move,this::addMove);
        TicTacToeSocketClient.getInstance().registerCallback(MessageType.TicTacToe_Error,this::displayErrorMessage);
    }
    /*
        Create a table for the game
     */
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
    /*
        If a Cell is clicked send a move request to the server
     */
    private void cellClickedHandler(int x , int y){
        gameMessageTV.setText(""); //New attempt = no current messages to display
        if(gameLogic.validMove(x,y, playerId)) {
            TicTacToeMoveRequest req = new TicTacToeMoveRequest(playerId, x, y);
            req.type = "TicTacToeMove";
            TicTacToeSocketClient.getInstance().sendMessage(req);
            timer.cancel();
        }else{
            gameMessageTV.setText(R.string.tictactoe_invalidmove);
        }
    }

    /*
        Display the game related error-messages
     */
    private void displayErrorMessage(TicTacToeErrorResponse response){
        gameMessageTV.setText(response.errorMessage);
    }
    /*
        Add a move to the gameboard
        By checking if the move is valid and then updating the cell accordingly
     */
    void addMove(TicTacToeMoveResponse response){
        TableRow row= (TableRow)gameTable.getChildAt(response.x);
        ImageView cell =(ImageView)row.getChildAt(response.y);
        setGameCell(cell,response.playerId);
        gameLogic.setMove(response.x,response.y,response.playerId);
        //Start the timer if its your turn
        if(response.playerId.equals(playerId))startCountdown();

    }

    void startCountdown(){
        timer=new CountDownTimer(5000, 100) {

            public void onTick(long millisUntilFinished) {
               timerTV.setText(getString(R.string.tictactoe_timertext) + millisUntilFinished / 1000);
            }

            public void onFinish() {
                timerTV.setText(getString(R.string.tictactoe_timerover));

            }
        }.start();
    }

    /*
        Update the cell to show the proper image for the player
     */
    private void setGameCell(ImageView gameCell, String pId){
        if(pId.equals(playerId)){
            gameCell.setImageResource(R.drawable.tictactoe_mlg);
           // playerId=2; //For Singleplayer
        }else {
            gameCell.setImageResource(R.drawable.tictactoe_doritos);
           // playerId=1;//For Singleplayer
        }
    }

    /*
        Create game cells with onclick-listener
        Use x and y parameters to identify the position of cell in the listener
     */
    private ImageView createCell(int x, int y){
        ImageView cell = new ImageView(this);
        cell.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        // Use id and Tag to identify the clicked cell!
       cell.setPadding(0,0,0,0);
        cell.setX(4);
        cell.setY(4);
        cell.setImageResource(R.drawable.tictactoe_emptyfield);
        cell.setId(x);
        cell.setTag(y);
        cell.setOnClickListener(v -> cellClickedHandler(v.getId(),(int)v.getTag()));
        return cell;
    }

    /*
    Visual popup-shown at the end of the game, using predefined text for the textviews

    Parameter (response.endgameScenarioId)
        1 = Player won
        2 = Player lost
        Everything else = Tie
     */
    private void showEndOfGameDialog(TicTacToeEndGameResponse response){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        if(response.endgameScenarioId==1) {
            builder.setTitle(getString(R.string.tictactoe_gamewon));
        }else if(response.endgameScenarioId==2){
            builder.setTitle(getString(R.string.tictactoe_gamelost));
        }else{
            builder.setTitle(getString(R.string.tictactoe_gametied));
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
