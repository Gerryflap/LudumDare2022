package nl.lipsum.main_menu.buttons;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import nl.lipsum.main_menu.MainMenuController;

public abstract class MainMenuButton {

    private MainMenuController mainMenuController;
    private int x;
    private int y;
    private int height;
    private int width;
    private String buttonText;

    public MainMenuButton(MainMenuController mainMenuController, int x, int y, int height, int width, String buttonText) {
        this.mainMenuController = mainMenuController;
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.buttonText = buttonText;
    }

    public abstract void render(ShapeRenderer shapeRenderer);

    public abstract void step(int x, int y);

    public MainMenuController getMainMenuController() {
        return mainMenuController;
    }

    public void setMainMenuController(MainMenuController mainMenuController) {
        this.mainMenuController = mainMenuController;
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
