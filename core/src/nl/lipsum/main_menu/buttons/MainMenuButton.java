package nl.lipsum.main_menu.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import nl.lipsum.main_menu.MainMenuController;
import nl.lipsum.Coordinate;

public abstract class MainMenuButton {

    private MainMenuController mainMenuController;
    private int x;
    private int y;
    private int height;
    private int width;
    private String buttonText;
    private BitmapFont font;

    public MainMenuButton(MainMenuController mainMenuController, int x, int y, int height, int width, String buttonText) {
        this.mainMenuController = mainMenuController;
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.buttonText = buttonText;
        this.font = new BitmapFont(); //or use alex answer to use custom font
    }

    public void render(ShapeRenderer shapeRenderer, SpriteBatch spriteBatch) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        Color color = new Color(125, 0, 125, 125);
        shapeRenderer.setColor(color);
        shapeRenderer.rect(getX(), getY(), getWidth(), getHeight());
        shapeRenderer.end();

        font.draw(spriteBatch, getButtonText(), getX(), getY() + font.getLineHeight());
    }

    public abstract void step(int x, int y);

    public MainMenuController getMainMenuController() {
        return mainMenuController;
    }

    public void setMainMenuController(MainMenuController mainMenuController) {
        this.mainMenuController = mainMenuController;
    }

    public boolean isCoordinateInButtonBox(Coordinate coordinate) {
        return getX() < coordinate.x && coordinate.x < getX() + getWidth() && getY() < coordinate.y && coordinate.y < getY() + getHeight();
    }

    public static Coordinate convertCringeTopLeftCoordinateToNormalBottomLeftCoordinateForButtonPressed(int x, int y) {
        return new Coordinate(x, Gdx.graphics.getHeight() - y);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

}