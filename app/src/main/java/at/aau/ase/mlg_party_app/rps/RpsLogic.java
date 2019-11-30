package at.aau.ase.mlg_party_app.rps;

import java.security.SecureRandom;
import java.util.Random;

class RpsLogic {
   enum OPTION {ROCK, PAPER, SCISSOR;

       public static OPTION random() {
           SecureRandom random = new SecureRandom();
           return values()[random.nextInt(values().length)];
       }
   }
   enum RESULT {DRAW, WON, LOST, ERROR}

    RESULT checkResult(OPTION userInput, OPTION enemyInput) {
        if (userInput == enemyInput) {
            // Draw
            return RESULT.DRAW;
        } else if (userInput == OPTION.ROCK && enemyInput == OPTION.PAPER){
            // Lost
            return RESULT.LOST;
        } else if (userInput == OPTION.ROCK && enemyInput == OPTION.SCISSOR) {
            // Won
            return RESULT.WON;
        } else if (userInput == OPTION.PAPER && enemyInput == OPTION.SCISSOR) {
            // Lost
            return RESULT.LOST;
        } else if (userInput == OPTION.PAPER && enemyInput == OPTION.ROCK) {
            // Won
            return RESULT.WON;
        } else if (userInput == OPTION.SCISSOR && enemyInput == OPTION.ROCK) {
            // Lost
            return RESULT.LOST;
        } else if (userInput == OPTION.SCISSOR && enemyInput == OPTION.PAPER) {
            // Won
            return RESULT.WON;
        }
        return RESULT.ERROR;
    }
}
