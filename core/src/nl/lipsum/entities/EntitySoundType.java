package nl.lipsum.entities;

public enum EntitySoundType {

    FIRE("fire.wav", 0.2f),
    DEATH("death.wav", 0.7f),
    SPAWN("spawn.wav", 1f);

    String path;
    float loudness;

    EntitySoundType(String path) {
        this(path, 1);
    }

    EntitySoundType(String path, float loudness) {
        this.path = path;
        this.loudness = loudness;
    }

    public String getPath() {
        return path;
    }

    public float getLoudness() {
        return loudness;
    }
}
