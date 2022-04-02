package nl.lipsum;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainMenuController implements GenericController {

    private Texture testTexture;
    private SpriteBatch batch;

    public MainMenuController() {
        testTexture = new Texture("badlogic.jpg");
        batch = new SpriteBatch();
    }

    @Override
    public void step() {
        if(Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
            LudumDare2022.setGameState(GameState.PLAYING);
        }

        if (Gdx.input.isTouched()) {
            System.out.println(Gdx.input.getX() + " " + Gdx.input.getY());
        }
    }

    @Override
    public void render(SpriteBatch batch, Camera camera) {
        this.batch.begin();
        this.batch.draw(testTexture, 0, 0);
        this.batch.end();
    }

    @Override
    public void dispose() {
        this.batch.dispose();
    }
}
