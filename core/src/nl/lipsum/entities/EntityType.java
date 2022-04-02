package nl.lipsum.entities;

public enum EntityType {


    INFANTRY("audio/sfx/infantry/");

    String path;

    EntityType(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
