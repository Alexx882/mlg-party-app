package at.aau.ase.mlg_party_app.quiz;


import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.security.SecureRandom;
import java.util.Random;

public class QuizLogic {
    private static final int NUM_QUESTIONS = 6;

    private static Random random;
    int currentQuestion;
    Question[] questions = new Question[NUM_QUESTIONS];

    public QuizLogic(String lobby) {

        if (random == null) {
            int seed;
            try {
                seed = Integer.valueOf(lobby);
            } catch (NumberFormatException e) {
                seed = 420;
            }

            random = new Random(seed);
        }

        currentQuestion = random.nextInt(NUM_QUESTIONS);
    }

    void createQuestions() {
        Question q1 = new Question("What is the name of the network of computers from which the Internet has emerged?", "Arpanet", "Google", "Cyclades", "Milnet", 1);
        questions[0] = q1;
        Question q2 = new Question("In what year was Google founded?", "1996", "1998", "1999", "2000", 2);
        questions[1] = q2;
        Question q3 = new Question("What is the county top-level domain of Belgium?", ".bl", "bg", "be", "bu", 3);
        questions[2] = q3;
        Question q4 = new Question("What colour is cobalt?", "Red", "Yellow", "Green", "Blue", 4);
        questions[3] = q4;

        questions[4] = new Question("What is the average penis size in the Kongo?", "12", "22", "18", "40", 3);
        questions[5] = new Question("What is the average number of individuals in orgies?", "7", "1", "5", "4", 3);
    }

    boolean checkAnswer(int ans) {
        //Checking answers
        if (questions[currentQuestion].getCorrectAnswer() == ans) return true;
        return false;
    }

    void setQuestion(TextView textViewQuestion) {
        textViewQuestion.setText(questions[currentQuestion].getQuestionText());
    }

    void setAnswers(Button[] allButtons) {
        allButtons[0].setText(questions[currentQuestion].getAnswer1());
        allButtons[1].setText(questions[currentQuestion].getAnswer2());
        allButtons[2].setText(questions[currentQuestion].getAnswer3());
        allButtons[3].setText(questions[currentQuestion].getAnswer4());
    }

    void changeQuestion(int newQuestion) {
        this.currentQuestion = newQuestion;
    }
}
