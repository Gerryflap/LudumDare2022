package nl.lipsum.main_menu.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import nl.lipsum.Coordinate;
import nl.lipsum.GameState;
import nl.lipsum.LudumDare2022;
import nl.lipsum.main_menu.MainMenuController;

public class PlayButton extends MainMenuButton {

    private BitmapFont font;

    public PlayButton(MainMenuController mainMenuController, int x, int y, int height, int width, String buttonText) {
        super(mainMenuController, x, y, height, width, buttonText);
         font = new BitmapFont(); //or use alex answer to use custom font
    }

    @Override
    public void step(int x, int y) {
        super.step(x, y);

        Coordinate locationCursor = convertCringeTopLeftCoordinateToNormalBottomLeftCoordinateForButtonPressed(x, y);

        // Check if button is pressed
        if (isCoordinateInButtonBox(locationCursor) && Gdx.input.isTouched()) {
            LudumDare2022.setGameState(GameState.PLAYING);
        }
    }

}
