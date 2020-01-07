package at.aau.ase.mlg_party_app.quiz;


import android.widget.Button;

import java.security.SecureRandom;

class QuizLogic {
        private SecureRandom random = new SecureRandom();
        private int question = random.nextInt(4)+1;

    boolean checkAnswer(int ans) {
        switch (question) {
            case 1:
                if (ans == 1) return true;
                return false;
            case 2:
                if (ans == 2) return true;
                return false;
            case 3:
                if (ans == 3) return true;
                return false;
            case 4:
                if (ans == 4) return true;
                return false;
            default:
                return false;
    }
    }
    String setQuestion() {
        switch (question) {

            case 1:
                return "Question number 1:\nPlease answer with the first answer!";
            case 2:
                return "Question number 2:\nPlease answer with the second answer!";
            case 3:
                return "Question number 3:\nPlease answer with the third answer!";
            case 4:
                return "Question number 4:\nPlease answer with the fourth answer!";
            default:
                return "Error";

        }
    }
    void setAnswers(Button b1, Button b2, Button b3, Button b4) {
        switch (question) {
            case 1:
                b1.setText("Placeholder 1");
                b2.setText("Placeholder 2");
                b3.setText("Placeholder 3");
                b4.setText("Placeholder 4");
            case 2:
                b1.setText("Placeholder 1");
                b2.setText("Placeholder 2");
                b3.setText("Placeholder 3");
                b4.setText("Placeholder 4");
            case 3:
                b1.setText("Placeholder 1");
                b2.setText("Placeholder 2");
                b3.setText("Placeholder 3");
                b4.setText("Placeholder 4");
            case 4:
                b1.setText("Placeholder 1");
                b2.setText("Placeholder 2");
                b3.setText("Placeholder 3");
                b4.setText("Placeholder 4");
            default:
                b1.setText("Placeholder 1");
                b2.setText("Placeholder 2");
                b3.setText("Placeholder 3");
                b4.setText("Placeholder 4");
        }
    }
}
