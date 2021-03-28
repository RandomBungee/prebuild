package de.flamefoxes.prebuild.sql;

import java.util.List;

public interface PrePlayer {
    void create(String name, String theme, String email, String discord, int submitted, int status, String checkKey);

    void change(String name, String theme, String email, String discord, int submitted, int status, String checkKey);

    int status(String name);

    int submitted(String name);

    String theme(String name);

    String discord(String name);

    String email(String name);

    List<String> players();

    boolean exist(String name);

    String checkKey(String name);
}
