package ax.ha.pp1.wordgame;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EvaluatorTest {

    // Ett exempel att utgå från. Skriv resten av testerna i del A.
    @Test
    void allLettersCorrectGivesACorrectResult() {
        GuessResult result = Evaluator.evaluate("KALLA", "KALLA");

        assertTrue(result.isCorrect());
        assertEquals("[K][A][L][L][A]", result.toString());
    }

    @Test
    void partialCorrectAndAbsentLetters() {
        GuessResult result = Evaluator.evaluate("KARMA", "KALLA");
        assertFalse(result.isCorrect());
        assertEquals("[K][A]-R--M-[A]", result.toString());
        assertArrayEquals(new int[]{2, 2, 0, 0, 2}, result.getMarks());
    }

    @Test
    void allLettersMisplaced(){
        GuessResult result = Evaluator.evaluate("STONE", "NOTES");
        assertFalse(result.isCorrect());
        assertEquals("(S)(T)(O)(N)(E)", result.toString());
        assertArrayEquals(new int[]{1, 1, 1, 1, 1}, result.getMarks());
    }

    @Test
    void noLettersInAnswer(){
        GuessResult result = Evaluator.evaluate("NOTER", "KALLA");
        assertFalse(result.isCorrect());
        assertEquals("-N--O--T--E--R-", result.toString());
        assertArrayEquals(new int[]{0, 0, 0, 0, 0}, result.getMarks());
    }

    @Test
    void repeadedLetters(){
        GuessResult result = Evaluator.evaluate("ALLAS", "KALLA");
        assertFalse(result.isCorrect());
        assertEquals("(A)(L)[L](A)-S-", result.toString());
        assertArrayEquals(new int[]{1, 1, 2, 1, 0}, result.getMarks());
    }

    @Test
    void repeatedLettersCorrectBeforeMisplaced(){
        GuessResult result = Evaluator.evaluate("KANAL", "KALLA");
        assertFalse(result.isCorrect());
        assertEquals("[K][A]-N-(A)(L)", result.toString());
        assertArrayEquals(new int[]{2, 2, 0, 1, 1}, result.getMarks());
    }

    @Test
    void repeatedLettersCorrectConsumesSingleMatchBeforeDuplicate(){
        GuessResult result = Evaluator.evaluate("KLARA", "STARK");
        assertFalse(result.isCorrect());
        assertEquals("(K)-L-[A][R]-A-", result.toString());
        assertArrayEquals(new int[]{1, 0, 2, 2, 0}, result.getMarks());
    }

    @Test
    void lowercaseGuessIsConvertedToUppercaseInResult(){
        GuessResult result = Evaluator.evaluate("kalla", "KALLA");
        assertTrue(result.isCorrect());
        assertEquals("KALLA", result.getGuess());
        assertEquals("[K][A][L][L][A]", result.toString());

        GuessResult mixedResult = Evaluator.evaluate("aLlAS", "KALLA");
        assertEquals("ALLAS", mixedResult.getGuess());
        assertEquals("(A)(L)[L](A)-S-", mixedResult.toString());
    }

    // Å, Ä och Ö stöds och skiljs från A och O
    @Test
    void swedishLettersAreSupportedAndDistinct() {
        GuessResult exactSwedish = Evaluator.evaluate("åäöåä", "ÅÄÖÅÄ");
        assertTrue(exactSwedish.isCorrect());
        assertEquals("ÅÄÖÅÄ", exactSwedish.getGuess());
        assertEquals("[Å][Ä][Ö][Å][Ä]", exactSwedish.toString());

        GuessResult aVsRingA = Evaluator.evaluate("ÅÅÅÅÅ", "AAAAA");
        assertFalse(aVsRingA.isCorrect());
        assertEquals("-Å--Å--Å--Å--Å-", aVsRingA.toString());

        GuessResult oVsDotsO = Evaluator.evaluate("ÖÖÖÖÖ", "OOOOO");
        assertFalse(oVsDotsO.isCorrect());
        assertEquals("-Ö--Ö--Ö--Ö--Ö-", oVsDotsO.toString());
    }

    @Test
    void invalidGuessOrAnswerThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Evaluator.evaluate("KATT", "KALLA"));
        assertThrows(IllegalArgumentException.class, () -> Evaluator.evaluate("KALLAR", "KALLA"));
        assertThrows(IllegalArgumentException.class, () -> Evaluator.evaluate("", "KALLA"));
        assertThrows(IllegalArgumentException.class, () -> Evaluator.evaluate("KALL1", "KALLA"));
        assertThrows(IllegalArgumentException.class, () -> Evaluator.evaluate("KALL!", "KALLA"));
        assertThrows(IllegalArgumentException.class, () -> Evaluator.evaluate("KA LA", "KALLA"));
        assertThrows(IllegalArgumentException.class, () -> Evaluator.evaluate(null, "KALLA"));
        assertThrows(IllegalArgumentException.class, () -> Evaluator.evaluate("KALLA", null));
        assertThrows(IllegalArgumentException.class, () -> Evaluator.evaluate("KALLA", "12345"));
    }

    @Test
    void isValidWordReturnsTrueForValidFiveLetterWords() {
        assertTrue(Evaluator.isValidWord("KALLA"));
        assertTrue(Evaluator.isValidWord("kalla"));
        assertTrue(Evaluator.isValidWord("ÅÄÖÅÄ"));
        assertTrue(Evaluator.isValidWord("åäöåä"));
        assertTrue(Evaluator.isValidWord("Fjärd"));
    }

    @Test
    void isValidWordReturnsFalseForInvalidInputs() {
        assertFalse(Evaluator.isValidWord(null));
        assertFalse(Evaluator.isValidWord(""));
        assertFalse(Evaluator.isValidWord("KATT"));
        assertFalse(Evaluator.isValidWord("KALLAR"));
        assertFalse(Evaluator.isValidWord("KALL1"));
        assertFalse(Evaluator.isValidWord("KA  LA"));
        assertFalse(Evaluator.isValidWord("KALL!"));
    }
}
