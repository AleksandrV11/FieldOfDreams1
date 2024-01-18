package org.example;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;


import java.util.HashMap;
import java.util.List;


public class TestGame {

    private static String kluch ;
    private static String id = Long.toString(-1002091888728L);
    private static MessageManager messageManager;

    public static void main(String[] args) {

        TelegramBot telegramBot = new TelegramBot(kluch);

        telegramBot.execute(new SendMessage(id, " Якщо хочеш пограти тицни /game якщо треба загальна статистика  то /statistics " +
                "або якщо треба персональна статистика /personalStatistics"));   //вивод сообщ праця з ним!!

        telegramBot.setUpdatesListener(a -> {
            List<Update> updates = a;
            Update update = updates.get(0);
            Message message = update.message();
            String name = update.message().from().firstName();
            Long idUsers = update.message().from().id(); //иd я от кого текст та зміни
            String text = message.text();
            // telegramBot.execute(new SendMessage(id, "rr"));

            if (text != null) {
                messageManager = new MessageManager();
                if (!messageManager.isGameRunning && !text.equals("/game@FieldOfDreams_bot") &&
                        !text.equals("/statistics@FieldOfDreams_bot") &&
                        !text.equals("/personalStatistics@FieldOfDreams_bot")) {
                    return UpdatesListener.CONFIRMED_UPDATES_ALL;
                }
                telegramBot.execute(new SendMessage(id, messageManager.message(idUsers, text, name)));
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });

    }
}
