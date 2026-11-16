package ax.ha.pp1.wordgame;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Hämtar ord till spelet från skolans språkmodell (del D).
 *
 * Kodskelettet kompilerar men är inte implementerat. Signaturerna får inte ändras.
 */
public class LlmWords {

    /**
     * Frågar modellen efter ungefär count ord och returnerar de giltiga orden i svaret.
     * Kastar IllegalStateException, via LlmClient, om modellen inte går att nå.
     */
    public static String[] fetch(LlmClient client, int count) {
        if  (client == null) {
            throw new NullPointerException("Llm client får inte vara null");
        }
        if (count <= 0) {
            return new String[0];
        }
        String prompt = "Ge mig " + count + "Vanliga svenska ord på exakt "
                + Evaluator.WORD_LENGTH + "bokstäver (A-Ö). "
                + "Skrivett ord per rad utan numrering, skiljetecken eller annan text.";

        String response = client.ask(prompt);
        return parse(response);
    }

    /** Alla giltiga ord i texten, med versaler, i den ordning de står. Ogiltiga hoppas över. Kan vara tom. */
    public static String[] parse(String text) {
        if (text == null || text.isBlank()) {
            return new String[0];
        }

        List<String> validWords = new ArrayList<>();
        String[] tokens = text.split("[\\s.,;:!?()\\[\\]{}\"'*•\\-–—/]+");

        for (String token : tokens) {
            if (Evaluator.isValidWord(token)) {
                validWords.add(token.toUpperCase(Locale.ROOT));
            }
        }
        return validWords.toArray(new String[0]);
    }
}
