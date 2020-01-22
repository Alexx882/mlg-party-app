package at.aau.ase.mlg_party_app.quiz;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


public class QuizTest {
    private QuizLogic logic;

    @Before // setup()
    public void before() {
        System.out.println("Setting up!");
        logic = new QuizLogic();
    }
    @Test
    public void checkQuestions() {
        logic.createQuestions();
        Question testQuestion = new Question("What is the name of the network of computers from which the Internet has emerged?", "Arpanet", "Google", "Cyclades", "Milnet", 1);
        assertEquals(testQuestion.getQuestionText(), logic.questions[0].getQuestionText());
        assertEquals(testQuestion.getAnswer1(), logic.questions[0].getAnswer1());
        assertEquals(testQuestion.getAnswer2(), logic.questions[0].getAnswer2());
        assertEquals(testQuestion.getAnswer3(), logic.questions[0].getAnswer3());
        assertEquals(testQuestion.getAnswer4(), logic.questions[0].getAnswer4());
        assertEquals(testQuestion.getCorrectAnswer(), logic.questions[0].getCorrectAnswer());
    }
    @Test
    public void wrongAnswer() {
            logic.createQuestions();
            assertFalse(logic.checkAnswer(5));
    }
    @Test
    public void correctAnswer() {
        for (int i=0;i<100;i++) {
            logic.createQuestions();
            assertTrue(logic.checkAnswer(logic.questions[logic.currentQuestion].getCorrectAnswer()));
        }

    }
    @After // tearDown
    public void after() {
        System.out.println("Tearing down");
        logic = null;
        assertNull(logic);

    }
}