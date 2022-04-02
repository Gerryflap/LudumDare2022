package nl.lipsum;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

public class TextureStore {
    private final HashMap<String, Texture> textures;

    public TextureStore() {
        this.textures = new HashMap<>();
        this.textures.put("background", new Texture("favorieteAfbeelding.jpg"));
        this.textures.put("orange", new Texture("orangeTile.jpg"));
        this.textures.put("white", new Texture("whiteTile.jpg"));
    }

    public Texture getTileTextureByName(String name) throws Exception {
        if (textures.containsKey(name)) {
            return textures.get(name);
        }
        throw new Exception(String.format("Texture met naam %s bestaat niet", name));
    }
}
