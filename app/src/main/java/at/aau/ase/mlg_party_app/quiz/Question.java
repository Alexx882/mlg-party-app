package at.aau.ase.mlg_party_app.quiz;
// Creating a question with necessary information
class Question {
    private String questionText;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private int correctAnswer;

    Question(String questionText, String answer1, String answer2, String answer3, String answer4, int correctAnswer) {
        this.questionText = questionText;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.correctAnswer = correctAnswer;
    }

    String getQuestionText() {
        return questionText;
    }

    String getAnswer1() {
        return answer1;
    }

    String getAnswer2() {
        return answer2;
    }

    String getAnswer3() {
        return answer3;
    }

    String getAnswer4() {
        return answer4;
    }

    int getCorrectAnswer() {
        return correctAnswer;
    }
}
