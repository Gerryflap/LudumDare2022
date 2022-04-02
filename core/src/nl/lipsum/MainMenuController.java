package nl.lipsum;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class MainMenuController implements GenericController {

    private Texture testTexture;
    private SpriteBatch batch;

    private ShapeRenderer shapeRenderer;

    private MainMenuState mainMenuState;

    public MainMenuController() {
        testTexture = new Texture("badlogic.jpg");
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        mainMenuState = MainMenuState.MAIN_SCREEN;
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

        switch (mainMenuState) {
            case MAIN_SCREEN:
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                Color color = new Color(240, 22, 150, 255);
                shapeRenderer.setColor(color);
                shapeRenderer.rect(0, 0, 50, 50);
                shapeRenderer.rect(200, 200, 20, 20);

                shapeRenderer.end();
        }

//        this.batch.draw(testTexture, 0, 0);
        this.batch.end();
    }

    @Override
    public void dispose() {
        this.batch.dispose();
    }
}
