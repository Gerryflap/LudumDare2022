package nl.lipsum.controllers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import nl.lipsum.LudumDare2022;
import nl.lipsum.main_menu.MainMenuController;

public class WinController extends MainMenuController {

    BitmapFont font;

    public WinController() {
        super();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
    }

    @Override
    public void step() {

    }

    @Override
    public void render(SpriteBatch batch, CameraController cameraController) {
        super.render(batch, cameraController);
//        batch.begin();
        if (LudumDare2022.winTimeString != null) {
            font.draw(batch, LudumDare2022.winTimeString, 50, 50);
        }
//        batch.end();
    }

    @Override
    public void dispose() {

    }
}
