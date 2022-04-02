package nl.lipsum.entities;

public enum EntitySoundType {

    FIRE("fire.wav"),
    DEATH("death.wav");

    String path;

    EntitySoundType(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
