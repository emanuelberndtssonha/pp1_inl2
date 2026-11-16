package ax.ha.pp1.wordgame;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LlmWordsTest {

    // Ett exempel att utgå från. Skriv resten av testerna i del D.
    // Testerna ska inte anropa språkmodellen, eftersom dess svar varierar.
    @Test
    void parseFindsOneWordPerLine() {
        String answer = "KALLA\ntårta";

        assertArrayEquals(new String[] {"KALLA", "TÅRTA"}, LlmWords.parse(answer));
    }

    @Test
    void parseExtractsValidWordsFromNumberedListAndConvertsToUppercase() {
        String text = """
                1. kalla
                2. storm
                3. fjärd
                4. äpple
                5. båten
                """;

        assertArrayEquals(
                new String[]{"KALLA", "STORM", "FJÄRD", "ÄPPLE", "BÅTEN"},
                LlmWords.parse(text)
        );
    }

    @Test
    void parseHandlesPunctuationQuotesAndBullets() {
        String text = "Här är någraa ord: \"kalla\", (storm), [fjärd]! - öppna; • åker.";

        assertArrayEquals(
                new String[]{"KALLA", "STORM", "FJÄRD", "ÖPPNA"},
                LlmWords.parse(text)
        );
    }

    @Test
    void parseSkipsWordsWithWrongLengthOrInvalidCharacters() {
        String text = "katt kalla kallar kalla1 café! storm överst åäöåä";

        assertArrayEquals(
                new String[]{"KALLA", "STORM", "ÅÄÖÅÄ"},
                LlmWords.parse(text)
        );
    }

    @Test
    void parseReturnsEmptyArrayWhenNoValidWordsExist() {
        String text = "Tyvärr kan jag inte ge dig någraa fembokstavsord just nu (12345).";

        assertArrayEquals(new String[0], LlmWords.parse(text));
        assertArrayEquals(new String[0], LlmWords.parse(""));
        assertArrayEquals(new String[0], LlmWords.parse("   \n\t  "));
        assertArrayEquals(new String[0], LlmWords.parse(null));
    }
}
