package org.example;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Game {

    private char[] word;         //масив з літерами  для перевірки та пошука літер заповнений у методі mystery присвоенням
    private char[] starsRiddles;     // майбутній масив зірок ,заповнений у методі mystery присвоєнням
    @Getter
    private WordGenerator.WordDTO wordDTO;
    HashMap<Long, Player> playerMap = new HashMap<>();
    private WorkingDatabase workingDatabase;


    public Game() {
        this.wordDTO = WordGenerator.generate();
        hideWithStars(this.wordDTO.getWord());
        System.out.println(this.wordDTO.getWord());
    }

    //1
    public String getInitMessage() {
        return " Вітаю це Поле Чудес \n Вгадай назву столиці країни " + this.wordDTO.getDescription() +
                " \n\n" + String.valueOf(this.starsRiddles);
    }

    //2
    public String guessingProcess(String letter, Long idUsers, String name) {               //вгадування
        workingDatabase = new WorkingDatabase();
        playerMap = (HashMap<Long, Player>) workingDatabase.readFromDatabase();

        if (!playerMap.containsKey(idUsers)) {                      //якщо по ключу в мапе немає нічого
            Player playerStatistics = new Player();
            playerStatistics.setName(name);
            playerMap.put(idUsers, playerStatistics);                     //додаєм обьект
        }
        if (letter.length() > 1) {                         //перевірка це буква чи слово
            return testWord(letter, idUsers);
        } else {
            return testLetter(letter, idUsers);
        }
    }
    public String testWord(String letter, Long idUsers) {

        if (letter.equalsIgnoreCase(String.valueOf(word))) {                     //якщо слово вгадане
            MessageManager.isGameRunning = false;
            //  guessedWord = true;
            // запис у мап після вгаданого слова
            recordingAfterTheGuessedWord(idUsers);
            // запис у бд з мап
            dataBaseEntry();

            return " Слово вгадал \n" +
                    " Якщо грати то тицяй /game \n" +
                    " якщо треба загальна статистика то /statistics \n" +
                    " або якщо треба персональна статистика то /personalStatistics ";
        } else {
            // запис у мап після не вгаданого слова
            entryAfterTheWrongWord(idUsers);
            // запис у бд з мап
            dataBaseEntry();
            return " Слово не вгадал спробуй ще ";     //слово не вгадане
        }
    }
    public String testLetter(String letter, Long idUsers) {
        char[] temporary = wordСhange(letter);      //якщо літер таких Не було то він рівен  starsRiddles
        if (Arrays.equals(temporary, starsRiddles)) {//зрівнювання якщо літер таких не було
            //літеру не вгадав
            entryAfterAnUnguessedLetter(idUsers);
            // запис у бд з мап
            dataBaseEntry();
            return " Цієї букви немає ";
        } else {
            starsRiddles = temporary;       //перезапис в масив зірок якщо літери були вгадани
            char[] tempor = Arrays.copyOf(starsRiddles, starsRiddles.length);
            if (Arrays.equals(word, tempor)) {
                MessageManager.isGameRunning = false;
                //  guessedWord = true;
                //вгадав останню літеру а з нею і слово
                entryAfterTheGuessedLetter(idUsers);
                recordingAfterTheGuessedWord(idUsers);
                // запис у бд з мап
                dataBaseEntry();
                return " Вгадав усі букви \n" +
                        " Якщо грати то тицяй /game \n" +
                        " якщо треба загальна статистика то /statistics \n" +
                        " або якщо треба персональна статистика /personalStatistics ";          //инфа на перезапуск якщо були вгадані усі букви
            }
            // вгадав букву
            entryAfterTheGuessedLetter(idUsers);
            // запис у бд з мап
            dataBaseEntry();
            return String.valueOf(temporary);
        }
    }
    // слово вгадав
    public void recordingAfterTheGuessedWord(Long idUsers) {
        Player playerStatistics;
        playerStatistics = playerMap.get(idUsers);      //створюю нову змінну по кей
        playerStatistics.increaseWord();             //змінюю поле спроб вгадати слово
        playerStatistics.increaseWorPos();     //змінюю поле вгаданих слів
        playerStatistics.percWordPositive();      //підрахунок відсотків
        playerMap.put(idUsers, playerStatistics);           //записую змінений обьєкт у мапу
    }
    // слово не вгадав
    public void entryAfterTheWrongWord(Long idUsers) {
        Player playerStatistics;
        playerStatistics = playerMap.get(idUsers);      //створюю нову змінну по кей
        playerStatistics.increaseWord();             //змінюю поле попиток вгадати слово
        playerMap.put(idUsers, playerStatistics);           //записую змінений обьєкт у мапу
    }
    //букву вгадав
    public void entryAfterTheGuessedLetter(Long idUsers) {
        Player playerStatistics;
        playerStatistics = playerMap.get(idUsers);      //створюю нову змінну по кей
        playerStatistics.increaseLet();             //змінюю поле попиток вгадати літер
        playerStatistics.increaseLetPos();     //змінюю поле вгаданих літер
        playerStatistics.percLetPositive();    //підрахунок відсотків
        playerMap.put(idUsers, playerStatistics);           //записую змінений обьєкт у мапу
    }
    //букву не вгадав
    public void entryAfterAnUnguessedLetter(Long idUsers) {
        Player playerStatistics;
        playerStatistics = playerMap.get(idUsers);      //створюю нову змінну по кей
        playerStatistics.increaseLet();             //змінюю поле попиток вгадати слово
        playerMap.put(idUsers, playerStatistics);           //записую змінений обьєкт у мапу
    }
    //запис у бд
    public void dataBaseEntry() {
        //   workingDatabase = new WorkingDatabase();
        workingDatabase.clearDatabase();             //очистка бд
        workingDatabase.entryDatabase(playerMap);  //     записуємо мапу у БД
    }
    private void hideWithStars(String data) {          // заповнення зірками та перший вивод зірок
        char[] coverWithStars = data.toCharArray();
        for (int i = 0; i < coverWithStars.length; i++) {
            if (coverWithStars[i] != ' ') {
                coverWithStars[i] = '*';
            }
        }
        word = data.toCharArray();                   //заповнення масива літерами
        starsRiddles = coverWithStars;                   // заповнення масива зірками для порівнення
    }
    private char[] wordСhange(String letter) {            //перезапис після вгадування
        String ignorCase = letter.toLowerCase();
        char enteredLetter = ignorCase.charAt(0);
        char[] temporary = Arrays.copyOf(starsRiddles, starsRiddles.length);
        for (int i = 0; i < word.length; i++) {
            if (word[i] == (enteredLetter)) {                    //запис у масив якщо таки букви були
                temporary[i] = enteredLetter;
            }
        }
        return temporary;
    }
}
