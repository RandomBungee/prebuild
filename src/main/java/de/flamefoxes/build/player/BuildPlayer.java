package de.flamefoxes.build.player;

import java.util.UUID;

public class BuildPlayer {

  private String name;
  private UUID uniqueId;
  private String theme;
  private String structureKind;
  private String buildStyle;
  private String pluginKind;
  private int submitted;
  private String applyKey;
  public static final Builder BUILDER = new Builder();

  public BuildPlayer() {}

  public String getName() {
    return name;
  }

  public UUID getUniqueId() {
    return uniqueId;
  }

  public String getTheme() {
    return theme;
  }

  public String getStructureKind() {
    return structureKind;
  }

  public String getBuildStyle() {
    return buildStyle;
  }

  public String getPluginKind() {
    return pluginKind;
  }

  public int getSubmitted() {
    return submitted;
  }

  public String getApplyKey() {
    return applyKey;
  }

  public static Builder newBuilder() {
    return BUILDER;
  }

  public static final class Builder {

    private String name;
    private UUID uniqueId;
    private String theme;
    private String structureKind;
    private String buildStyle;
    private String pluginKind;
    private int submitted;
    private String applyKey;

    public BuildPlayer build() {
      return buildPartial();
    }

    private BuildPlayer buildPartial() {
      BuildPlayer result = new BuildPlayer();
      result.name = name;
      result.uniqueId = uniqueId;
      result.theme = theme;
      result.structureKind = structureKind;
      result.buildStyle = buildStyle;
      result.pluginKind = pluginKind;
      result.submitted = submitted;
      result.applyKey = applyKey;
      return result;
    }

    public Builder setName(String name) {
      this.name = name;
      return this;
    }

    public Builder setUniqueId(UUID uniqueId) {
      this.uniqueId = uniqueId;
      return this;
    }

    public Builder setTheme(String theme) {
      this.theme = theme;
      return this;
    }

    public Builder setStructureKind(String structureKind) {
      this.structureKind = structureKind;
      return this;
    }

    public Builder setBuildStyle(String buildStyle) {
      this.buildStyle = buildStyle;
      return this;
    }

    public Builder setPluginKind(String pluginKind) {
      this.pluginKind = pluginKind;
      return this;
    }

    public Builder setSubmitted(int submitted) {
      this.submitted = submitted;
      return this;
    }

    public Builder setApplyKey(String applyKey) {
      this.applyKey = applyKey;
      return this;
    }
  }
}