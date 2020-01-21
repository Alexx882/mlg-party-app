package at.aau.ase.mlg_party_app.tictactoe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import at.aau.ase.mlg_party_app.Game;
import at.aau.ase.mlg_party_app.MainActivity;
import at.aau.ase.mlg_party_app.R;
import at.aau.ase.mlg_party_app.game_setup.networking.HelloGameRequest;
import at.aau.ase.mlg_party_app.networking.websocket.WebSocketClient;
import at.aau.ase.mlg_party_app.tictactoe.networking.TicTacToeEndGameResponse;
import at.aau.ase.mlg_party_app.tictactoe.networking.TicTacToeErrorResponse;
import at.aau.ase.mlg_party_app.tictactoe.networking.TicTacToeMoveRequest;
import at.aau.ase.mlg_party_app.tictactoe.networking.TicTacToeMoveResponse;
import at.aau.ase.mlg_party_app.networking.MessageType;


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
        socketHandling();
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
    private void socketHandling(){
        Intent intent = getIntent();
        String wsEndpoint = intent.getStringExtra("WS");
        WebSocketClient.getInstance().connectToServer(wsEndpoint);
        HelloGameRequest helloReq = new HelloGameRequest(Game.getInstance().getLobbyId(), Game.getInstance().getPlayerId());
        WebSocketClient.getInstance().sendMessage(helloReq);

        WebSocketClient.getInstance().registerCallback(MessageType.TicTacToeEndGame,this::showEndOfGameDialog);
        WebSocketClient.getInstance().registerCallback(MessageType.TicTacToeMove,this::addMove);
        WebSocketClient.getInstance().registerCallback(MessageType.TicTacToeError,this::displayErrorMessage);
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
            WebSocketClient.getInstance().sendMessage(req);
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
        if(!response.playerId.equals(playerId)){
            if(timer!=null)timer.cancel();
            timerTV.setText(R.string.tictactoe_notyourturn);
        }else{
            startCountdown();
        }

    }


    /*
    Start a Timer to show how much time remains
    This is visual only (cut-off+ random move happens @server)
     */
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
       builder.setTitle(response.endGameScenario);
        builder.setMessage(R.string.backtolobby);
        builder.setPositiveButton(android.R.string.ok, (dialog, id) -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
        AlertDialog endGameDialog=builder.create();
        endGameDialog.show();
    }


}
