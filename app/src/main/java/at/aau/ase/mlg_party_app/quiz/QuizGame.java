package at.aau.ase.mlg_party_app.quiz;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import at.aau.ase.mlg_party_app.R;

public class QuizGame extends AppCompatActivity implements View.OnClickListener{

    TextView questionText;
    Button buttonAnswer1;
    Button buttonAnswer2;
    Button buttonAnswer3;
    Button buttonAnswer4;
    QuizLogic qlogic = new QuizLogic();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        questionText = findViewById(R.id.textViewQuestion);
        buttonAnswer1 = findViewById(R.id.buttonAnswer1);
        buttonAnswer2 = findViewById(R.id.buttonAnswer2);
        buttonAnswer3 = findViewById(R.id.buttonAnswer3);
        buttonAnswer4 = findViewById(R.id.buttonAnswer4);
        questionText.setText(qlogic.setQuestion());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.buttonAnswer1:
            questionText.append(""+qlogic.checkAnswer(1));
                break;
            case R.id.buttonAnswer2:
                questionText.append(""+qlogic.checkAnswer(2));
                break;
            case R.id.buttonAnswer3:
                questionText.append(""+qlogic.checkAnswer(3));
                break;
            case R.id.buttonAnswer4:
                questionText.append(""+qlogic.checkAnswer(4));
                break;
            default:

        }
    }
}
