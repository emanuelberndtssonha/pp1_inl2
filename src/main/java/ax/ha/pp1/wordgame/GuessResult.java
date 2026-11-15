package ax.ha.pp1.wordgame;

import java.util.Locale;

/**
 * Resultatet av en gissning: gissningens bokstäver och en markering per bokstav.
 * Klassen ska vara oföränderlig.
 *
 * Kodskelettet kompilerar men är inte implementerat. Skriv testerna i del A först,
 * och implementera sedan klassen i del B. Signaturerna får inte ändras.
 */
public final class GuessResult {
    public static final int ABSENT = 0;     // finns inte (fler gånger) i ordet
    public static final int MISPLACED = 1;  // finns, men på en annan plats
    public static final int CORRECT = 2;    // rätt bokstav på rätt plats

    private final String guess;
    private final int[] marks;

    public GuessResult(String guess, int[] marks) {
        if (guess == null || marks == null || guess.length() != marks.length) {
            throw new IllegalArgumentException("Gissning och markering måste ha samma längd");
        }
        this.guess = guess.toUpperCase(Locale.ROOT);
        this.marks = marks.clone();

        for (int mark : this.marks) {
            if (mark != ABSENT && mark != MISPLACED && mark != CORRECT) {
                throw new IllegalArgumentException("Ogiltig markering: " + mark);
            }
        }
    }

    /** Gissningen med versaler, t.ex. "ALLAS". */
    public String getGuess() {
        return guess;
    }

    /** Antal bokstäver. */
    public int length() {
        return guess.length();
    }

    /** Bokstaven på plats index (0–4). */
    public char letterAt(int index) {
        return guess.charAt(index);
    }

    /** Markeringen på plats index: ABSENT, MISPLACED eller CORRECT. */
    public int markAt(int index) {
        return marks[index];
    }

    /** Alla markeringar, t.ex. {1, 1, 2, 1, 0}. */
    public int[] getMarks() {
        return marks.clone();
    }

    /** Sant om alla bokstäver är CORRECT. */
    public boolean isCorrect() {
        for (int mark : marks) {
            if (mark != CORRECT) {
                return false;
            }
        }
        return true;
    }

    /** Till exempel (A)(L)[L](A)-S- för gissningen ALLAS mot svaret KALLA. */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(guess.length() * 3);
        for (int i= 0; i < guess.length(); i++) {
            char letter = guess.charAt(i);
            switch (marks[i]) {
                case CORRECT -> sb.append('[').append(letter).append(']');
                case MISPLACED -> sb.append('(').append(letter).append(')');
                case ABSENT -> sb.append('-').append(letter).append('-');
            }
        }
        return sb.toString();
    }
}
