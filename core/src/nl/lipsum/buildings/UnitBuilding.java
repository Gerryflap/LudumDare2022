package nl.lipsum.buildings;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import nl.lipsum.LudumDare2022;
import nl.lipsum.StaticUtils;
import nl.lipsum.controllers.CameraController;
import nl.lipsum.entities.AbstractEntity;
import nl.lipsum.entities.AttackType;
import nl.lipsum.entities.EntityType;
import nl.lipsum.gameLogic.GameController;
import nl.lipsum.gameLogic.playermodel.PlayerModel;

import static nl.lipsum.Config.TILE_SIZE;

public abstract class UnitBuilding extends Building {

    public static final Texture tileTexture = new Texture("greenTile.jpg");

    private int trainingProgress;
    private final int trainingTime;
    private final int unitCap;

    private final AbstractEntity[] units;
    private int unitPointer = 0;

    public UnitBuilding(int x, int y, PlayerModel owner, int cost, int trainingTime, int unitCap) {
        super(x, y, owner, cost);
        this.trainingTime = trainingTime;
        this.unitCap = unitCap;
        this.units = new AbstractEntity[unitCap];
    }

    @Override
    public void step() {
        trainingProgress += 1;
        if (trainingProgress > trainingTime) {
            if (unitPointer >= unitCap) {

            } else {
                trainingProgress = 0;
                AbstractEntity unit = new AbstractEntity(x*TILE_SIZE, y*TILE_SIZE, new Texture("greenTile.jpg"), GameController.playerController.getHumanPlayerModel().getBase(),
                        100, 100, 300, 10, 25, 100, AttackType.RANGED, EntityType.INFANTRY);
                //TODO: make sure the right army is has the added entity
                GameController.playerController.getHumanPlayerModel().armies.get(0).entities.add(unit);
                this.units[unitPointer] = unit;
                unitPointer += 1;
            }
        }
    }

    @Override
    public void draw(SpriteBatch batch, CameraController cameraController) {
        StaticUtils.smartDraw(batch, cameraController, tileTexture, TILE_SIZE * this.x - TILE_SIZE/2, TILE_SIZE * this.y- TILE_SIZE/2, TILE_SIZE, TILE_SIZE);
    }

    @Override
    public void dispose() {

    }
}
