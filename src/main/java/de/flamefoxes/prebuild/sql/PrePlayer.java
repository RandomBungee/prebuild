package de.flamefoxes.prebuild.sql;

import java.util.Hashtable;
import java.util.List;

public interface PrePlayer {
    void create(
      String name,
      String theme,
      String email,
      String discord,
      int submitted,
      int status,
      String checkKey,
      String structure,
      String style,
      String plugin
    );

    void change(
      String name,
      String theme,
      String email,
      String discord,
      int submitted,
      int status,
      String checkKey,
      String structure,
      String style,
      String plugin
    );

    int status(String name);

    int submitted(String name);

    String theme(String name);

    String discord(String name);

    String email(String name);

    List<String> players();

    boolean exist(String name);

    String checkKey(String name);

    String structure(String name);

    String style(String name);

    String plugin(String name);
}
