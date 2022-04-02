package nl.lipsum.main_menu.buttons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import nl.lipsum.Coordinate;
import nl.lipsum.GameState;
import nl.lipsum.LudumDare2022;
import nl.lipsum.main_menu.MainMenuController;

public class ExitButton extends MainMenuButton {

    private BitmapFont font;

    public ExitButton(MainMenuController mainMenuController, int x, int y, int height, int width, String buttonText) {
        super(mainMenuController, x, y, height, width, buttonText);
    }

    @Override
    public void step(int x, int y) {
        Coordinate locationPressed = convertCringeTopLeftCoordinateToNormalBottomLeftCoordinateForButtonPressed(x, y);

        // Check if button is pressed
        if (isCoordinateInButtonBox(locationPressed)) {
            LudumDare2022.setGameState(GameState.EXITING);
        }
    }

}
