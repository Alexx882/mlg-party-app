package at.aau.ase.mlg_party_app.rps;

import java.security.SecureRandom;

class RpsLogic {
   enum Option {ROCK, PAPER, SCISSOR;

       public static Option random() {
           SecureRandom random = new SecureRandom();
           return values()[random.nextInt(values().length)];
       }
   }
   enum Result {DRAW, WON, LOST, ERROR}

    Result checkResult(Option userInput, Option enemyInput) {
        if (userInput == enemyInput) {
            // Draw
            return Result.DRAW;
        } else if (userInput == Option.ROCK && enemyInput == Option.PAPER){
            // Lost
            return Result.LOST;
        } else if (userInput == Option.ROCK && enemyInput == Option.SCISSOR) {
            // Won
            return Result.WON;
        } else if (userInput == Option.PAPER && enemyInput == Option.SCISSOR) {
            // Lost
            return Result.LOST;
        } else if (userInput == Option.PAPER && enemyInput == Option.ROCK) {
            // Won
            return Result.WON;
        } else if (userInput == Option.SCISSOR && enemyInput == Option.ROCK) {
            // Lost
            return Result.LOST;
        } else if (userInput == Option.SCISSOR && enemyInput == Option.PAPER) {
            // Won
            return Result.WON;
        }
        return Result.ERROR;
    }
}
