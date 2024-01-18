package org.example;

import java.util.HashMap;
import java.util.Map;

public class GeneralStatistics {

    private WorkingDatabase workingDatabase = new WorkingDatabase();
    public String getStatisticGeneralMessage() {
        HashMap<Long, Player> playerMap = (HashMap<Long, Player>) workingDatabase.readFromDatabase();
        if (playerMap.isEmpty()) {                 //як що бд  пуста
            return " Щє не одної гри не було , \n статистика буде після гри. ";
        }
        return "Ща підготую \n Ось статистика : \n  " + getGeneralStatistics(playerMap);            //спроба виводу статистики
    }
    private String getGeneralStatistics(HashMap<Long, Player> map) {
        StringBuilder playerField = new StringBuilder();
        for (Map.Entry<Long, Player> entry : map.entrySet()) {
            String name = entry.getValue().getName();
            int persentLeters = (int) entry.getValue().getPercentageOfLettersGuessed();
            int persentWords = (int) entry.getValue().getPercentageOfWordsGuessed();
            playerField.append("   " + name + "\n" + " Процент вгаданих літер - " + persentLeters + " % \n"
                    + " Процент вгаданих слів - " + persentWords + " %\n");
        }
        return playerField.toString();
    }
}
