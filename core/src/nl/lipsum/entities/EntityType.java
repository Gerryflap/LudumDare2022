package nl.lipsum.entities;

public enum EntityType {


    INFANTRY("audio/sfx/infantry/"),
    //TODO: update audio files
    TANK("audio/sfx/infantry/"),
    SNIPER("audio/sfx/infantry/");

    String path;

    EntityType(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
