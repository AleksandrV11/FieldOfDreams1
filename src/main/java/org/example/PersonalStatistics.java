package org.example;

import java.util.HashMap;
import java.util.Map;

public class PersonalStatistics {
    private WorkingDatabase workingDatabase = new WorkingDatabase();

    public String getStatisticPersonalMessage(Long idUsers) {
        HashMap<Long, Player> playerMap = (HashMap<Long, Player>) workingDatabase.readFromDatabase();      //спроба запису мапы из бд
        if (playerMap.isEmpty()) {                 //як що бд  пуста
            return " Щє не одної гри не було , \n статистика буде після гри. ";
        }
        return "Ща підготую \n Ось твоя статистика : \n  " + getPersonalStatistics(idUsers, playerMap);  //спроба виводу перс статистики
    }
    private String getPersonalStatistics(Long id, HashMap<Long, Player> map) {
        StringBuilder playerField = new StringBuilder();
        for (Map.Entry<Long, Player> entry : map.entrySet()) {
            if (entry.getKey().equals(id)) {
                String name = entry.getValue().getName();
                int persLet = (int) entry.getValue().getPercentageOfLettersGuessed();
                int persWord = (int) entry.getValue().getPercentageOfWordsGuessed();
                playerField.append("   " + name + "\n" + " Процент вгаданих літер - "
                        + persLet + " % \n" + " Процент вгаданих слів - " + persWord + " % \n");
            }
        }
        return playerField.toString();
    }

}
