package nl.lipsum.main_menu.buttons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import nl.lipsum.main_menu.MainMenuController;

public class PlayButton extends MainMenuButton {

    public PlayButton(MainMenuController mainMenuController, int x, int y, int height, int width, String buttonText) {
        super(mainMenuController, x, y, height, width, buttonText);
    }

    @Override
    public void render(ShapeRenderer shapeRenderer) {
        Color color = new Color(125, 0, 125, 125);
        shapeRenderer.setColor(color);
//        shapeRenderer.rect(0, 0, 50 ,50);
        shapeRenderer.rect(getX(), getY(), getWidth() ,getHeight());
//        System.out.println("render " + getX() + " " + getY() + " " + getHeight() + " " + getWidth());
    }

    @Override
    public void step(int x, int y) {
        // Check if button is pressed
        System.out.println(x + " " + y);
        if (getX() < x && x < getX() + getWidth() &&
            getY() < y && y < getY() + getHeight()) {
            System.out.println(getButtonText());
        }


    }
}
