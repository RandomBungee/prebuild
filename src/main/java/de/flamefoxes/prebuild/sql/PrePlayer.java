package de.flamefoxes.prebuild.sql;

public interface PrePlayer {
    void create(String name, String theme, String email, String discord, int submitted, int status);

    void change(String name, String theme, String email, String discord, int submitted, int status);

    int status(String name);

    int submitted(String name);

    String theme(String name);

    String discord(String name);

    String email(String name);
}
