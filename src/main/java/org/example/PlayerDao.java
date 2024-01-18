package org.example;

import java.util.HashMap;
import java.util.Map;

public interface PlayerDao {
    //запись в бд
    void entryDatabase(HashMap<Long, Player> map);

    // запись из бд в мап
    Map<Long, Player> readFromDatabase();

    // очистка данн в бд
    void clearDatabase();


}
