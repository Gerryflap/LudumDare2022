package nl.lipsum.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import nl.lipsum.LudumDare2022;
import nl.lipsum.buildings.BuildingType;
import nl.lipsum.gameLogic.GameController;
import nl.lipsum.TextureStore;
import nl.lipsum.gameLogic.playermodel.HumanPlayerModel;
import nl.lipsum.gameLogic.playermodel.PlayerModel;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static nl.lipsum.Config.*;
import static nl.lipsum.ui.UiConstants.*;

/**
 * Controls the main UI bar
 */
public class BarController {
    private final static List<Integer> ARMY_SELECTED_KEYS = Arrays.asList(Input.Keys.NUM_0, Input.Keys.NUM_1, Input.Keys.NUM_2);

    private final GameController gameController;
    private TextureStore textureStore;
    private UiItem[] uiItems = new UiItem[]{null, null, null, null, null, null, null, null, null, null};
    private HumanPlayerModel humanPlayerModel;
    private BitmapFont font = new BitmapFont();
    private SpriteBatch fontSpriteBatch = new SpriteBatch();
    private ShapeRenderer borderShapeRenderer = new ShapeRenderer();
    private SpriteBatch spriteBatch = new SpriteBatch();
    private boolean displayDebugInfo = false;

    public BarController(GameController gameController, HumanPlayerModel humanPlayerModel) {
        this.gameController = gameController;
        this.humanPlayerModel = humanPlayerModel;
        this.textureStore = new TextureStore();
        Texture orangeTile = null;
        try {
            orangeTile = textureStore.getTileTextureByName("orange");
        } catch (Exception e){

        }

        try {
            UiItem infantryBuilding = new UiSelectedItem(LudumDare2022.buildingController.getBuildingBuilder().infantryTexture, orangeTile, ICON_WIDTH, ICON_HEIGHT, new Function<UiItem, Object>() {
                @Override
                public Object apply(UiItem uiItem) {
                    LudumDare2022.buildingController.startBuilder(BuildingType.INFANTRY);
                    LudumDare2022.humanPlayerModel.setUiBuildingSelect((UiSelectedItem) uiItem);
                    return null;
                }
            });
            infantryBuilding.setRequiredResources(INFANTRY_BUILDING_COST);
            UiItem tankBuilding = new UiSelectedItem(LudumDare2022.buildingController.getHumanBuildingBuilder().tankTexture, orangeTile, ICON_WIDTH, ICON_HEIGHT, new Function<UiItem, Object>() {
                @Override
                public Object apply(UiItem uiItem) {
//                    LudumDare2022.buildingController.setActive(true);
//                    LudumDare2022.buildingController.setBuildingType(BuildingType.UNIT);
                    LudumDare2022.buildingController.startBuilder(BuildingType.TANK);
                    LudumDare2022.humanPlayerModel.setUiBuildingSelect((UiSelectedItem) uiItem);
                    return null;
                }
            });
            tankBuilding.setRequiredResources(TANK_BUILDING_COST);
            UiItem sniperBuilding = new UiSelectedItem(LudumDare2022.buildingController.getBuildingBuilder().sniperTexture, orangeTile, ICON_WIDTH, ICON_HEIGHT, new Function<UiItem, Object>() {
                @Override
                public Object apply(UiItem uiItem) {
//                    LudumDare2022.buildingController.setActive(true);
//                    LudumDare2022.buildingController.setBuildingType(BuildingType.UNIT);
                    LudumDare2022.buildingController.startBuilder(BuildingType.SNIPER);
                    LudumDare2022.humanPlayerModel.setUiBuildingSelect((UiSelectedItem) uiItem);
                    return null;
                }
            });
            sniperBuilding.setRequiredResources(SNIPER_BUILDING_COST);
            UiItem resourceBuilding = new UiSelectedItem(LudumDare2022.buildingController.getHumanBuildingBuilder().resourceTexture, orangeTile, ICON_WIDTH, ICON_HEIGHT, new Function<UiItem, Object>() {
                @Override
                public Object apply(UiItem uiItem) {
//                    LudumDare2022.buildingController.setActive(true);
                    LudumDare2022.buildingController.startBuilder(BuildingType.RESOURCE);
                    LudumDare2022.humanPlayerModel.setUiBuildingSelect((UiSelectedItem) uiItem);
//                    buildingBuilder.start(BuildingType.UNIT);

                    return null;
                }
            });
            resourceBuilding.setRequiredResources(RESOURCE_BUILDING_COST);
            UiItem turretBuilding = new UiSelectedItem(LudumDare2022.buildingController.getBuildingBuilder().turretTexture, orangeTile, ICON_WIDTH, ICON_HEIGHT, new Function<UiItem, Object>() {
                @Override
                public Object apply(UiItem uiItem) {
//                    LudumDare2022.buildingController.setActive(true);
                    LudumDare2022.buildingController.startBuilder(BuildingType.TURRET);
                    LudumDare2022.humanPlayerModel.setUiBuildingSelect((UiSelectedItem) uiItem);
//                    buildingBuilder.start(BuildingType.UNIT);

                    return null;
                }
            });
            turretBuilding.setRequiredResources(TURRET_BUILDING_COST);
            UiItem heatBuilding = new UiSelectedItem(LudumDare2022.buildingController.getBuildingBuilder().heatTexture, orangeTile, ICON_WIDTH, ICON_HEIGHT, new Function<UiItem, Object>() {
                @Override
                public Object apply(UiItem uiItem) {
                    LudumDare2022.buildingController.startBuilder(BuildingType.HEAT);
                    LudumDare2022.humanPlayerModel.setUiBuildingSelect((UiSelectedItem) uiItem);
                    return null;
                }
            });
            heatBuilding.setRequiredResources(HEAT_BUILDING_COST);

            this.uiItems[3] = tankBuilding;
            this.uiItems[4] = sniperBuilding;
            this.uiItems[5] = infantryBuilding;
            this.uiItems[6] = resourceBuilding;
            this.uiItems[7] = turretBuilding;
            this.uiItems[8] = heatBuilding;

            UiSelectedItem uiSelectedItem1 = new UiSelectedItem(new Texture("army0.png"), textureStore.getTileTextureByName("orange"), ICON_WIDTH, ICON_HEIGHT,
                    new Function<UiItem, Object>() {
                        @Override
                        public Object apply(UiItem uiItem) {
//                            LudumDare2022.buildingController.stopBuilder();
                            LudumDare2022.gameController.setSelectedArmy(0, (UiSelectedItem) uiItem);
                            return null;
                        }
                    });
            this.uiItems[0] = uiSelectedItem1;
            gameController.setSelectedArmy(0, uiSelectedItem1);
            this.uiItems[1] = new UiSelectedItem(new Texture("army1.png"), textureStore.getTileTextureByName("orange"), ICON_WIDTH, ICON_HEIGHT,
                    new Function<UiItem, Object>() {
                        @Override
                        public Object apply(UiItem uiItem) {
//                            LudumDare2022.buildingController.stopBuilder();
                            LudumDare2022.gameController.setSelectedArmy(1, (UiSelectedItem) uiItem);
                            return null;
                        }
                    });
            this.uiItems[2] = new UiSelectedItem(new Texture("army2.png"), textureStore.getTileTextureByName("orange"), ICON_WIDTH, ICON_HEIGHT,
                    new Function<UiItem, Object>() {
                        @Override
                        public Object apply(UiItem uiItem) {
//                            LudumDare2022.buildingController.stopBuilder();
                            LudumDare2022.gameController.setSelectedArmy(2, (UiSelectedItem) uiItem);
                            return null;
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void step() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.GRAVE)) {
            displayDebugInfo = !displayDebugInfo;
        }
        if (Gdx.input.justTouched()){
            if (Gdx.graphics.getHeight() - BAR_HEIGHT < Gdx.input.getY() && Gdx.input.getY() < Gdx.graphics.getHeight()){
                float uiItemY = 3;
                LudumDare2022.buildingController.stopBuilder(); //maybe activate later again, but if clicked on bar, then builder deactivates
                if (Gdx.graphics.getHeight() - uiItemY > Gdx.input.getY() && Gdx.graphics.getHeight() - uiItemY - ICON_HEIGHT < Gdx.input.getY()) {
                    float uiItemX = 5;
                    for (UiItem uiItem : uiItems) {
                        if (uiItem != null/* && uiItem.getRequiredResources() <= humanPlayerModel.getAmountResources()*/) {
                            if (uiItemX < Gdx.input.getX() && uiItemX + ICON_WIDTH > Gdx.input.getX()) {
                                uiItem.getFunction().apply(uiItem);
                            }
                        }
                        uiItemX = uiItemX + 5 + ICON_WIDTH;
                    }
                }
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)){
            int i = 1;
            for (UiItem uiItem : uiItems) {
                if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_0 + i)) {
                    if (uiItem != null/* && uiItem.getRequiredResources() <= humanPlayerModel.getAmountResources()*/) {
                        uiItem.getFunction().apply(uiItem);
                    } else if (ARMY_SELECTED_KEYS.stream().noneMatch(key -> Gdx.input.isKeyJustPressed(key))) {
                        LudumDare2022.buildingController.stopBuilder();
                    }
                }
                i += 1;
                if (i == 10){
                    //0 is the last on the numbers on your keyboard
                    i = 0;
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
        font.draw(fontSpriteBatch, String.format("Resources: %s", (int) humanPlayerModel.getAmountResources()), 5, BAR_HEIGHT + font.getLineHeight() - 3);

        // draw global temperature
        font.draw(fontSpriteBatch, String.format("Global temperature: %.1f C", gameController.globalTemperature), 5, BAR_HEIGHT + 2 * font.getLineHeight() - 3);

        // draw player health
        font.draw(fontSpriteBatch, String.format("Player health: %s", humanPlayerModel.getHealth()), 5, BAR_HEIGHT + 3 * font.getLineHeight() - 3);

        // draw player health
        font.draw(fontSpriteBatch, String.format("Survivable temperature: %s C", PlayerModel.TEMPERATURE_MAX_CAN_SURVIVE_WITHOUT_DAMAGE - humanPlayerModel.getCoolingPower()), 5, BAR_HEIGHT + 4 * font.getLineHeight() - 3);

        // draw debug info
        if(displayDebugInfo){
            font.draw(fontSpriteBatch, String.format("FPS: %s", (int) (1/Gdx.graphics.getDeltaTime())), 0, camera.viewportHeight);
        }
        fontSpriteBatch.end();

        //draw item icons
        spriteBatch.begin();
        float uiItemX = 5;
        float uiItemY = 3;
        for (UiItem uiItem : uiItems) {
            if (uiItem != null) {
                if (uiItem instanceof UiSelectedItem){
                    if (gameController.getUiArmySelect() == uiItem || LudumDare2022.humanPlayerModel.getUiBuildingSelected() == uiItem){
                        spriteBatch.draw(((UiSelectedItem) uiItem).getTextureSelected(), uiItemX, uiItemY, ICON_WIDTH, ICON_HEIGHT);
                    }
                    spriteBatch.draw(uiItem.getTexture(), uiItemX, uiItemY, ICON_WIDTH, ICON_HEIGHT);
                } else {
                    spriteBatch.draw(uiItem.getTexture(), uiItemX, uiItemY, ICON_WIDTH, ICON_HEIGHT);
                }
            }
            uiItemX = uiItemX + 5 + ICON_WIDTH;
        }
        spriteBatch.end();


        // draw icon borders
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
            if (uiItem != null && uiItem.getRequiredResources() > humanPlayerModel.getAmountResources()) {
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
        borderShapeRenderer.dispose();
        spriteBatch.dispose();
    }
}
