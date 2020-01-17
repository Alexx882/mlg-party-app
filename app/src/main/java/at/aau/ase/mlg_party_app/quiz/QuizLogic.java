package at.aau.ase.mlg_party_app.quiz;


import android.widget.Button;
import android.widget.TextView;

import java.security.SecureRandom;

class QuizLogic {
        private SecureRandom random = new SecureRandom();
        int currentQuestion = random.nextInt(4);
        Question[] questions = new Question[4];

   void createQuestions() {
        Question q1 = new Question("What is the name of the network of computers from which the Internet has emerged?", "Arpanet", "Google", "Cyclades", "Milnet", 1);
        questions[0] = q1;
        Question q2 = new Question("In what year was Google founded?", "1996", "1998", "1999", "2000", 2);
        questions[1] = q2;
        Question q3 = new Question("What is the county top-level domain of Belgium?", ".bl", "bg", "be", "bu", 3);
        questions[2] = q3;
        Question q4 = new Question("What colour is cobalt?", "Red", "Yellow", "Green", "Blue", 4);
        questions[3] = q4;
    }
   boolean checkAnswer(int ans) {
       //Checking answers
        if(questions[currentQuestion].getCorrectAnswer() == ans ) return true;
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
}
