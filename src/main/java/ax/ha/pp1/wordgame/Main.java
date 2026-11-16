package ax.ha.pp1.wordgame;

import javax.swing.*;

/**
 * Programmet som spelar spelet med dialogrutor. Det skriver du i del C.
 */
public class Main {
    private static final int REQUESTED_WORDS = 10;
    private static final int MIN_VALID_WORDS = 5;


    void main() {
        String[] words = loadWords();
        Game game = new Game(new WordSource(words));

        while (!game.isOver()) {
            int currentGuess = game.getGuessCount() + 1;
            String board = game.toString();

            String message = (board.isEmpty() ? "" : board + "\n")
                    + "Gissning " + currentGuess + " av " + Game.MAX_GUESSES + "\n"
                    + "Skriv ett ord med " + Evaluator.WORD_LENGTH + " Bokstäver (A-Ö):";

            String input = JOptionPane.showInputDialog(null, message, "Ordspel",  JOptionPane.QUESTION_MESSAGE);

            if (input == null) {
                return;
            }

            try {
                game.guess(input);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, "Ogiltigt ord! Ordet måste bestå av " + Evaluator.WORD_LENGTH + " bokstäver (A-Ö)", "Ogiltig Gissning", JOptionPane.ERROR_MESSAGE);
            }
        }
        String endMessage = game.toString() + "\n";
        if (game.isWon()) {
            endMessage += "Grattis! Antal försök: " + game.getGuessCount() + "\n";
        } else {
            endMessage += "Du förlorade... Rätt svar var: " + game.getAnswer();
        }
        JOptionPane.showMessageDialog(null, endMessage, "Game Over", JOptionPane.INFORMATION_MESSAGE);
    }
    private String[] loadWords() {
        String url = System.getenv("PP1_LLM_URL");
        String model = System.getenv("PP1_LLM_MODEL");

        if (url == null || url.isBlank() || model == null || model.isBlank()) {
            return WordList.all();
        }
        try {
            LlmClient client = new LlmClient();
            String[] llmWords = LlmWords.fetch(client, REQUESTED_WORDS);

            if (llmWords.length < MIN_VALID_WORDS) {
                JOptionPane.showMessageDialog(
                        null,
                        "Språkmodellen gav för få giltiga ord (" + llmWords.length + "). Använder den inbyggda listan istället.",
                        "Reserv används",
                        JOptionPane.INFORMATION_MESSAGE
                );
                return WordList.all();
            }
            return llmWords;
        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "kunde inte hämta ord från språkmodellen: " + e.getMessage()
                    + "\nAnvänder den inbyggda listan istället.",
                    "Reserv används",
                    JOptionPane.WARNING_MESSAGE
            );
            return WordList.all();
        }
    }
}
