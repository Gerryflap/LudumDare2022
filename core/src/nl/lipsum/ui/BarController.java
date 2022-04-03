package nl.lipsum.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import nl.lipsum.LudumDare2022;
import nl.lipsum.gameLogic.GameController;
import nl.lipsum.TextureStore;
import nl.lipsum.gameLogic.playermodel.HumanPlayerModel;

import java.util.function.Function;

import static nl.lipsum.ui.UiConstants.*;

/**
 * Controls the main UI bar
 */
public class BarController {
    private final GameController gameController;
    private TextureStore textureStore;
    private UiItem[] uiItems = new UiItem[]{null, null, null, null, null, null, null, null, null, null};
    private HumanPlayerModel humanPlayerModel;
    private BitmapFont font = new BitmapFont();
    private SpriteBatch fontSpriteBatch = new SpriteBatch();

    public BarController(GameController gameController, HumanPlayerModel humanPlayerModel) {
        this.gameController = gameController;
        this.humanPlayerModel = humanPlayerModel;
        this.textureStore = new TextureStore();

        try {
            UiItem exampleHaveEnough = new UiItem(textureStore.getTileTextureByName("blue"), ICON_WIDTH, ICON_HEIGHT);
            exampleHaveEnough.setRequiredResources(100);
            UiItem exampleDontHaveEnough = new UiItem(textureStore.getTileTextureByName("orange"), ICON_WIDTH, ICON_HEIGHT);

            this.uiItems[0] = exampleHaveEnough;
            this.uiItems[1] = exampleDontHaveEnough;

            UiArmySelect uiArmySelect7 = new UiArmySelect(textureStore.getTileTextureByName("blue"), textureStore.getTileTextureByName("orange"), ICON_WIDTH, ICON_HEIGHT,
                    new Function<UiItem, Object>() {
                        @Override
                        public Object apply(UiItem uiItem) {
                            LudumDare2022.gameController.setSelectedArmy(0, (UiArmySelect) uiItem);
                            return null;
                        }
                    });
            this.uiItems[7] = uiArmySelect7;
            gameController.setSelectedArmy(0, uiArmySelect7);
            this.uiItems[8] = new UiArmySelect(textureStore.getTileTextureByName("blue"), textureStore.getTileTextureByName("orange"), ICON_WIDTH, ICON_HEIGHT,
                    new Function<UiItem, Object>() {
                        @Override
                        public Object apply(UiItem uiItem) {
                            LudumDare2022.gameController.setSelectedArmy(1, (UiArmySelect) uiItem);
                            return null;
                        }
                    });
            this.uiItems[9] = new UiArmySelect(textureStore.getTileTextureByName("blue"), textureStore.getTileTextureByName("orange"), ICON_WIDTH, ICON_HEIGHT,
                    new Function<UiItem, Object>() {
                        @Override
                        public Object apply(UiItem uiItem) {
                            LudumDare2022.gameController.setSelectedArmy(2, (UiArmySelect) uiItem);
                            return null;
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void step() {
        if (Gdx.input.justTouched()){
            if (Gdx.graphics.getHeight() - BAR_HEIGHT < Gdx.input.getY() && Gdx.input.getY() < Gdx.graphics.getHeight()){
                float uiItemY = 3;
                if (Gdx.graphics.getHeight() - uiItemY > Gdx.input.getY() && Gdx.graphics.getHeight() - uiItemY - ICON_HEIGHT < Gdx.input.getY()) {
                    float uiItemX = 5;
                    for (UiItem uiItem : uiItems) {
                        if (uiItem != null && uiItem.getRequiredResources() < humanPlayerModel.getAmountResources()) {
                            if (uiItemX < Gdx.input.getX() && uiItemX + ICON_WIDTH > Gdx.input.getX()) {
                                uiItem.getFunction().apply(uiItem);
                            }
                        }
                        uiItemX = uiItemX + 5 + ICON_WIDTH;
                    }
                }
            }
        }
    }

    public void render(ShapeRenderer shapeRenderer, Camera camera) {
        // draw ui backgroundz
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(BAR_COLOR);
        shapeRenderer.rect(0, 0, camera.viewportWidth, BAR_HEIGHT);
        shapeRenderer.rect(0, BAR_HEIGHT, RESOURCE_COUNTER_WIDTH, RESOURCE_COUNTER_HEIGHT);
        shapeRenderer.circle(RESOURCE_COUNTER_WIDTH, BAR_HEIGHT, RESOURCE_COUNTER_HEIGHT);
        shapeRenderer.rect(camera.viewportWidth - MINIMAP_WIDTH, 0, camera.viewportWidth, MINIMAP_HEIGHT);
        shapeRenderer.end();

        // draw resource counter
        font.setColor(UI_FONT_COLOR);

        fontSpriteBatch.begin();
        font.draw(fontSpriteBatch, String.format("Resources: %s", humanPlayerModel.getAmountResources()), 5, BAR_HEIGHT + font.getLineHeight() - 3);

        // draw global temperature
        font.draw(fontSpriteBatch, String.format("Global temperature: %.1f C", gameController.globalTemperature), 5, BAR_HEIGHT + 2 * font.getLineHeight() - 3);

        // draw player health
        font.draw(fontSpriteBatch, String.format("Player health: %s", humanPlayerModel.getHealth()), 5, BAR_HEIGHT + 3 * font.getLineHeight() - 3);
        fontSpriteBatch.end();

        //draw item icons
        SpriteBatch spriteBatch = new SpriteBatch();
        spriteBatch.begin();
        float uiItemX = 5;
        float uiItemY = 3;
        for (UiItem uiItem : uiItems) {
            if (uiItem != null) {
                if (uiItem instanceof UiArmySelect){
                    if (gameController.getUiArmySelect() == uiItem){
                        spriteBatch.draw(((UiArmySelect) uiItem).getTextureSelected(), uiItemX, uiItemY, ICON_WIDTH, ICON_HEIGHT);
                    } else {
                        spriteBatch.draw(uiItem.getTexture(), uiItemX, uiItemY, ICON_WIDTH, ICON_HEIGHT);
                    }
                } else {
                    spriteBatch.draw(uiItem.getTexture(), uiItemX, uiItemY, ICON_WIDTH, ICON_HEIGHT);
                }
            }
            uiItemX = uiItemX + 5 + ICON_WIDTH;
        }
        spriteBatch.end();


        // draw icon borders
        ShapeRenderer borderShapeRenderer = new ShapeRenderer();
        borderShapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        borderShapeRenderer.setColor(ICON_BORDER_COLOR);
        uiItemX = 5;
        uiItemY = 3;
        for (UiItem uiItem : uiItems) {
            borderShapeRenderer.rect(uiItemX, uiItemY, ICON_WIDTH, ICON_HEIGHT);
            uiItemX = uiItemX + 5 + ICON_WIDTH;
        }
        borderShapeRenderer.end();

        // draw not enough resources greyed out effect
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(ICON_NOT_IN_STORE_COLOR);
        uiItemX = 5;
        uiItemY = 3;
        for (UiItem uiItem : uiItems) {
            if (uiItem != null && uiItem.getRequiredResources() < humanPlayerModel.getAmountResources()) {
                shapeRenderer.rect(uiItemX, uiItemY, ICON_WIDTH, ICON_HEIGHT);
            }
            uiItemX = uiItemX + 5 + ICON_WIDTH;
        }
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    public void dispose() {
        font.dispose();
        fontSpriteBatch.dispose();
    }
}
