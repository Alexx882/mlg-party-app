package at.aau.ase.mlg_party_app.quiz;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertNull;

@RunWith(Parameterized.class)
public class QuizLogicTestParametrized {
    private QuizLogic logic;
    private int currentQuestion;
    private int answer;
    private boolean correct;

    @Before // setup()
    public void before() {
        logic = new QuizLogic("");
        logic.createQuestions();
    }

    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][] {
                {0, 1, true},
                {1, 2, true},
                {2, 3, true},
                {3, 4, true},
                {0, 2, false},
                {1, 4, false},
                {2, 1, false},
                {3, 3, false}

        });
    }
    public QuizLogicTestParametrized(int currentQuestion, int answer, boolean correct) {
        this.currentQuestion = currentQuestion;
        this.answer = answer;
        this.correct = correct;
    }
    @Test
    public void checkExpectedResult() {
        logic.changeQuestion(currentQuestion);
        boolean res = logic.checkAnswer(answer);
        Assert.assertEquals(res, correct);
    }
    @After // tearDown
    public void after() {
        logic = null;
        assertNull(logic);

    }
}
