package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Player {
    private String name;
    private double guessLetters = 0;
    private double guessLetterPositive = 0;
    private double guessWords = 0;
    private double guessWordsPositive = 0;

    private double percentageOfLettersGuessed;
    private double percentageOfWordsGuessed;

    public void increaseLet() {
        guessLetters++;
    }

    public void increaseLetPos() {
        guessLetterPositive++;
    }

    public void increaseWord() {
        guessWords++;
    }

    public void increaseWorPos() {
        guessWordsPositive++;
    }

    public void percLetPositive() {
        percentageOfLettersGuessed = (guessLetterPositive / guessLetters) * 100;
    }

    public void percWordPositive() {
        percentageOfWordsGuessed = (guessWordsPositive / guessWords) * 100;
    }
}

