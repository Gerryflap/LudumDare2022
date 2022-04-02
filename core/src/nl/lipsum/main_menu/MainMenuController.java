package nl.lipsum.main_menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import nl.lipsum.CameraController;
import nl.lipsum.GameState;
import nl.lipsum.GenericController;
import nl.lipsum.LudumDare2022;
import nl.lipsum.main_menu.buttons.MainMenuButton;
import nl.lipsum.main_menu.buttons.PlayButton;

import java.util.ArrayList;
import java.util.List;

public class MainMenuController implements GenericController {

    private Texture testTexture;
    private SpriteBatch batch;

    private ShapeRenderer shapeRenderer;

    private MainMenuState mainMenuState;

    private List<MainMenuButton> mainMenuButtons = new ArrayList<>();

    public MainMenuController() {
        testTexture = new Texture("badlogic.jpg");
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        mainMenuState = MainMenuState.MAIN_SCREEN;

        mainMenuButtons.add(new PlayButton(
                this, 200, 200, 20, 100, "Play"
        ));

//        mainMenuButtons.add(new MainMenuButton(
//                400, 400, 20, 100, "Options"
//        ));
//
//        mainMenuButtons.add(new MainMenuButton(
//                300, 300, 20, 100, "Exit"
//        ));
    }

    @Override
    public void step() {
//        if(Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
//            LudumDare2022.setGameState(GameState.PLAYING);
//        }

        if (Gdx.input.isTouched()) {
            switch (mainMenuState) {
                case MAIN_SCREEN:

                    for (MainMenuButton _mainMenuButton :
                            mainMenuButtons) {
                        _mainMenuButton.step(Gdx.input.getX(), Gdx.input.getY());
                    }
            }
//            System.out.println(Gdx.input.getX() + " " + Gdx.input.getY());
        }



    }

    @Override
    public void render(SpriteBatch batch, CameraController camera) {
//        this.batch.begin();

        switch (mainMenuState) {
            case MAIN_SCREEN:
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

                for (MainMenuButton _mainMenuButton :
                        mainMenuButtons) {
                    _mainMenuButton.render(shapeRenderer);
                }


                shapeRenderer.end();
        }

//        this.batch.draw(testTexture, 0, 0);
//        this.batch.end();
    }

    @Override
    public void dispose() {
        this.batch.dispose();
    }
}
