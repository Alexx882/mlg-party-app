package at.aau.ase.mlg_party_app.rps;

import java.security.SecureRandom;

public class RpsLogic {
    public enum Option {
        ROCK, PAPER, SCISSOR;

        public static Option random() {
            SecureRandom random = new SecureRandom();
            return values()[random.nextInt(values().length)];
        }
    }

    enum Result {DRAW, WON, LOST, ERROR}

    Option checkEnemyOption(Option userInput, Result status) {

        if (status == Result.DRAW) {
            // Draw
            return userInput;
        } else if (status == Result.WON) {
            switch (userInput) {
                case ROCK:
                    return Option.SCISSOR;
                case PAPER:
                    return Option.ROCK;
                case SCISSOR:
                    return Option.PAPER;
                default:
                    return userInput;
            }
        } else if (status == Result.LOST) {
            switch (userInput) {
                case ROCK:
                    return Option.PAPER;
                case PAPER:
                    return Option.SCISSOR;
                case SCISSOR:
                    return Option.ROCK;
                default:
                    return userInput;
            }

        }
        return userInput;
    }
}

