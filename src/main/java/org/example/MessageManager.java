package org.example;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.Getter;

import java.util.HashMap;

public class MessageManager {

    public static boolean isGameRunning;

    private static Game game;
    private static GeneralStatistics generalStatistics;
    private static PersonalStatistics personalStatistics;

    public String message(Long id, String text, String name) {

        if (text.equals("/statistics@FieldOfDreams_bot")) {
            if (isGameRunning) {
                return " Гра вже їде статистика після гри ";
            }
            generalStatistics = new GeneralStatistics();
            return generalStatistics.getStatisticGeneralMessage();
        }
        if (text.equals("/personalStatistics@FieldOfDreams_bot")) {
            if (isGameRunning) {
                return " Гра вже їде особиста статистика після гри ";
            }
            personalStatistics = new PersonalStatistics();
            return personalStatistics.getStatisticPersonalMessage(id);
        }
        if (text.equals("/game@FieldOfDreams_bot")) {
            if (isGameRunning) {            // минулий иф та_________гра вже йде
                return "Гра вже запущена та триває";
            }
            isGameRunning = true;   // гра йде
            game = new Game();       //виділення памяті
            return game.getInitMessage();  //реакція на вибір та повертає текст всіх данних завдання
        }
//        if (!text.equals("/statistics@FieldOfDreams_bot") ||
//                !text.equals("/personalStatistics@FieldOfDreams_bot") ||
//                !text.equals("/game@FieldOfDreams_bot") && !isGameRunning) {
//
//        }
        else {
            if (isGameRunning) {//якщо гра вже йде то символи йдуть на вгадування
                return game.guessingProcess(text, id, name); //САМИЙ ОСНОВНИЙ МЕТОД ПО ВГАДУВАННЮ
            }
        }

        return null;
    }
}
