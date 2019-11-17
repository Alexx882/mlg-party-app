package at.aau.ase.mlg_party_app.rps;

class RpsLogic {
   enum option {ROCK, PAPER, SCISSOR}
   enum result {DRAW, WON, LOST, ERROR}

    result checkResult(option userInput, option enemyInput) {
        if (userInput == enemyInput) {
            // Draw
            return result.DRAW;
        } else if (userInput == option.ROCK && enemyInput == option.PAPER){
            // Lost
            return result.LOST;
        } else if (userInput == option.ROCK && enemyInput == option.SCISSOR) {
            // Won
            return result.WON;
        } else if (userInput == option.PAPER && enemyInput == option.SCISSOR) {
            // Lost
            return result.LOST;
        } else if (userInput == option.PAPER && enemyInput == option.ROCK) {
            // Won
            return result.WON;
        } else if (userInput == option.SCISSOR && enemyInput == option.ROCK) {
            // Lost
            return result.LOST;
        } else if (userInput == option.SCISSOR && enemyInput == option.PAPER) {
            // Won
            return result.WON;
        }
        return result.ERROR;
    }
}
