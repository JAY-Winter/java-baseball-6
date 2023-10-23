package baseball.game;

import baseball.validators.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GameLogic {
    private final List<Validator> validators;

    public GameLogic(List<Validator> validators) {
        this.validators = validators;
    }

    void validateUserInput(String userInput) {
        for (Validator validator : validators) {
            validator.validate(userInput);
        }
    }

    public int countStrike(String userNumber, ArrayList<Integer> computerNumber) {
        int result = 0;
        for (int i = 0; i < 3; i++) {
            if (userNumber.charAt(i) - 48 == computerNumber.get(i)) {
                result++;
            }
        }
        return result;
    }

    public int countBall(String userNumber, ArrayList<Integer> computerNumber) {
        int result = 0;
        for (int i = 0; i < 3; i++) {
            if (userNumber.charAt(i) - 48 != computerNumber.get(i) && computerNumber.contains(userNumber.charAt(i) - 48)) {
                result++;
            }
        }
        return result;
    }

    boolean isNothing(int strike, int ball) {
        return strike == 0 && ball == 0;
    }

    boolean isAnswer(String hint) {
        return hint.equals("3스트라이크");
    }


    String generateHintFromResult(int strike, int ball) {
        if (isNothing(strike, ball)) {
            return GameHintType.NOTHING.format(0);
        }

        return combineStrikeAndBallHints(strike, ball);
    }

    private String combineStrikeAndBallHints(int strike, int ball) {
        return Stream.of(GameHintType.BALL.format(ball), GameHintType.STRIKE.format(strike))
                .filter(hint -> !hint.isEmpty())
                .collect(Collectors.joining(" "));
    }
}
