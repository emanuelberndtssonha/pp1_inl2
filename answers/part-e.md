# Del E: Frågor

## 1. Om WordSource skapades inne i Game

Hur hade testerna av `Game` sett ut om `Game` själv skapade sin `WordSource` med hela ordlistan? Vad hade varit svårt eller omöjligt att testa?

Testerna hade blivit oförutsägbara eftersom svaret slumpas fram och hålls hemligt tills spelet är slut. Man hade egentligen bara kunna testat förlust.

Att testa isWon() hade varit en intressant lek då svaret alltid slumpas så då skulle man ha en 1/(hur många ord som finns med i listan) chans att vinna.

## 2. Om GuessResult var föränderlig

Anta att `GuessResult` hade en metod `setMark(int index, int mark)` och att `getMarks()` returnerade den interna räckan. Beskriv minst ett konkret fel som då kunde uppstå i spelet eller i testerna.

Eftersom Game sparar GuessResult objekten i sin historik delas samma interna data med koden som anropar game.guess.

Anroparen kan ändra en fel gissning i efterhand via result.setMark() ellre result.getMarks()[i] = GuessResult.CORRECT. Då tror game.isWon() att spelaren vunnit även fast den gissat fel ord.

## 3. Undantag

`Game.guess` kastar `IllegalArgumentException` för en ogiltig gissning och `IllegalStateException` när spelet är slut. Varför är det bättre än att returnera `null`? Var i ditt program fångas undantagen, och varför just där?

Null säger inte varför anropet misslyckas men `IllegalArgumentException` visar på användarfel och `IllegalStateException` visar på programmeringsfel

`IllegalArgumentException` fångas i main runt game.guess(input) så då visas felmeddelandet och låter spelaren skriva iun nytt ord

`IllegalStateException` fångas inte i main utan i game eftersom while (!game.isOver()) loppen redan hindrar gissningar efter game over skulle det ändå inträffas så är det en bugg i koden som skulle behöva synas direkt.

`IllegalStateException` används samt i Llmclient i loadwords för att kunna byta till reservordlistan.

## 4. Ord från språkmodellen

Ger språkmodellen alltid riktiga svenska ord? Ge exempel från dina körningar eller dina tester, och beskriv hur du skulle kunna kontrollera orden bättre.

Enligt mina tester samt mina exempelkörnignar så var faktiskt outputen bara svenska.

## 5. Svårast och tid

Vad var svårast i uppgiften, och vad gjorde du för att komma vidare? Ungefär hur många timmar lade du på varje del (A, B, C, D och E)?

Jag hade väldigt svårt att få Llm att fungera korrekt. Det vela först inte koppla alls sedan fick jag inte promten korrekt samt utskriften den gav fick jag inte att fungera. Jag använde lite ai för att få detta att fungera men skrev allt för hand då svaren inte löste något dock hjälpte de svaren mig med hur jag skulle tänka för att gå vidare.

| Del | Timmar |
|-----|--------|
| A   |    1    |
| B   |    2    |
| C   |    2    |
| D   |    3    |
| E   |    0.5    |
