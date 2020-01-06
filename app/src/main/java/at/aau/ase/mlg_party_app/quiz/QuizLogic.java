package at.aau.ase.mlg_party_app.quiz;


import java.security.SecureRandom;

public class QuizLogic {
        SecureRandom random = new SecureRandom();
        int question = random.nextInt(4)+1;

    boolean checkAnswer(int ans) {
        switch (question) {
            case 1:
                if (ans == 1) {
                    return true;
                } else {
                    return false;
                }
            case 2:
                if (ans == 2) {
                    return true;
                } else {
                    return false;
                }
            case 3:
                if (ans == 3) {
                    return true;
                } else {
                    return false;
                }
            case 4:
                if (ans == 4) {
                    return true;
                } else {
                    return false;
                }
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
}
