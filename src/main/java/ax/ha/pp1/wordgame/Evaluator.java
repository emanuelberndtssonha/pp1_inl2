package ax.ha.pp1.wordgame;

import java.util.Locale;
/**
 * Spelreglerna: kontrollerar ord och jämför en gissning med svaret.
 *
 * Kodskelettet kompilerar men är inte implementerat. Skriv testerna i del A först,
 * och implementera sedan metoderna i del B. Signaturerna får inte ändras.
 */
public class Evaluator {
    public static final int WORD_LENGTH = 5;
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZÅÄÖ";

    /** Sant om ordet har fem bokstäver ur alfabetet A–Ö, versaler eller gemener. */
    public static boolean isValidWord(String word) {
        if (word == null || word.length() != WORD_LENGTH) {
            return false;
        }
        String upper = word.toUpperCase(Locale.ROOT);
        for (int i = 0; i < upper.length(); i++) {
            if (ALPHABET.indexOf(upper.charAt(i)) < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Jämför gissningen med svaret. Versaler och gemener räknas lika.
     * Kastar IllegalArgumentException om gissningen eller svaret inte är ett giltigt ord.
     */
    public static GuessResult evaluate(String guess, String answer) {
        if (!isValidWord(guess) || !isValidWord(answer)) {
            throw new  IllegalArgumentException("Både gissning samt svar måste vara giltiga ord på 5 bokstäver");
        }

        String upperGuess = guess.toUpperCase(Locale.ROOT);
        String upperAnswer = answer.toUpperCase(Locale.ROOT);

        int[] marks = new int[WORD_LENGTH];
        int[] remainingCounts = new int[ALPHABET.length()];

        for (int i = 0; i < WORD_LENGTH; i++) {
            if (upperGuess.charAt(i) == upperAnswer.charAt(i)) {
                marks[i] = GuessResult.CORRECT;
            } else {
                int answerIndex = ALPHABET.indexOf(upperAnswer.charAt(i));
                remainingCounts[answerIndex]++;
            }
        }

        for (int i = 0; i < WORD_LENGTH; i++) {
            if (marks[i] != GuessResult.CORRECT) {
                int guessIndex = ALPHABET.indexOf(upperGuess.charAt(i));
                if (remainingCounts[guessIndex] > 0) {
                    marks[i] = GuessResult.MISPLACED;
                    remainingCounts[guessIndex]--;
                }
            }
        }
        return new GuessResult(upperGuess, marks);
    }
}
