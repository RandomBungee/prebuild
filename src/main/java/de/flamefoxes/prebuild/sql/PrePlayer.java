package de.flamefoxes.prebuild.sql;

import com.sun.istack.internal.Nullable;

public interface PrePlayer {
    void create(String name, String theme, @Nullable String email, @Nullable String discord, int submitted, int status);

    void change(String name, String theme, @Nullable String email, @Nullable String discord, int submitted, int status);

    int status(String name);

    int submitted(String name);

    String theme(String name);
}
