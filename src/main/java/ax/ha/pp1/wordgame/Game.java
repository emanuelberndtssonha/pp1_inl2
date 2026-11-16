package ax.ha.pp1.wordgame;

/**
 * En spelomgång: ett hemligt ord och högst sex gissningar.
 * Klassen vet ingenting om dialogrutor.
 *
 * Kodskelettet kompilerar men är inte implementerat, och det har inga fält:
 * vilka fält klassen behöver bestämmer du själv i del B. Signaturerna får inte ändras.
 */
public class Game {
    public static final int MAX_GUESSES = 6;

    private final String answer;
    private final GuessResult[] guesses;
    private int guessCount;
    /** Startar en omgång. Svaret hämtas med source.next(). */
    public Game(WordSource source) {
        if (source == null) {
            throw new IllegalArgumentException("wordsource får inte vara null");
        }
        this.answer = source.next();
        this.guesses = new GuessResult[MAX_GUESSES];
        this.guessCount = 0;
    }

    /**
     * Gör en gissning och returnerar resultatet. Kastar IllegalArgumentException om
     * ordet är ogiltigt (gissningen räknas då inte), och IllegalStateException om
     * spelet redan är slut.
     */
    public GuessResult guess(String word) {
        if (isOver()) {
            throw new IllegalStateException("Spelet är redan slut");
        }
        GuessResult result = Evaluator.evaluate(word, answer);
        guesses[guessCount] = result;
        guessCount++;
        return result;
    }

    /** Sant om senaste gissningen var rätt. */
    public boolean isWon() {
        return guessCount > 0 && guesses[guessCount -1].isCorrect();
    }

    /** Sant om spelet är slut: vunnet eller sex gissningar gjorda. */
    public boolean isOver() {
        return isWon() || guessCount >= MAX_GUESSES;
    }

    /** Antal gjorda gissningar. */
    public int getGuessCount() {
        return  guessCount;
    }

    /** Antal gissningar kvar. */
    public int getGuessesLeft() {
        return MAX_GUESSES - guessCount;
    }

    /**
     * Svaret. Kastar IllegalStateException om spelet inte är slut, så att svaret
     * inte kan avslöjas för tidigt.
     */
    public String getAnswer() {
        if (!isOver()) {
            throw new IllegalStateException("Svaret får inte hämtas innan spelet är över");
        }
        return answer;
    }

    /** Spelbrädet: en rad per gissning i formen från GuessResult, med radbrytning efter varje rad. */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < guessCount; i++) {
            sb.append(guesses[i]).append('\n');
        }
        return sb.toString();
    }
}
