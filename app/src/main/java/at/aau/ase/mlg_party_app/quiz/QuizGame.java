package at.aau.ase.mlg_party_app.quiz;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import at.aau.ase.mlg_party_app.R;

public class QuizGame extends AppCompatActivity implements View.OnClickListener{

    TextView questionText;
    TextView questionResult;
    Button buttonAnswer1;
    Button buttonAnswer2;
    Button buttonAnswer3;
    Button buttonAnswer4;
    Button[] allButtons = new Button[4];
    QuizLogic qLogic = new QuizLogic();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        qLogic.createQuestions();
        setContentView(R.layout.activity_quiz);
        questionText = findViewById(R.id.textViewQuestion);
        questionResult = findViewById(R.id.textViewResult);

        buttonAnswer1 = findViewById(R.id.buttonAnswer1);
        buttonAnswer1.setOnClickListener(this);

        buttonAnswer2 = findViewById(R.id.buttonAnswer2);
        buttonAnswer2.setOnClickListener(this);

        buttonAnswer3 = findViewById(R.id.buttonAnswer3);
        buttonAnswer3.setOnClickListener(this);

        buttonAnswer4 = findViewById(R.id.buttonAnswer4);
        buttonAnswer4.setOnClickListener(this);

        qLogic.setQuestion(questionText);
        allButtons[0] = buttonAnswer1;
        allButtons[1] = buttonAnswer2;
        allButtons[2] = buttonAnswer3;
        allButtons[3] = buttonAnswer4;
        qLogic.setAnswers(allButtons);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.buttonAnswer1:
                questionResult.setText(String.valueOf(qLogic.checkAnswer(1)));
                break;
            case R.id.buttonAnswer2:
                questionResult.setText(String.valueOf(qLogic.checkAnswer(2)));
                break;
            case R.id.buttonAnswer3:
                questionResult.setText(String.valueOf(qLogic.checkAnswer(3)));
                break;
            case R.id.buttonAnswer4:
                questionResult.setText(String.valueOf(qLogic.checkAnswer(4)));
                break;
            default:

        }
        // Disable all buttons so no option can be chosen anymore
        disableButtons();
    }
    private void disableButtons() {
        for (int i=0; i<=3; i++) {
            allButtons[i].setEnabled(false);
        }
    }
}
