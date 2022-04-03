package nl.lipsum.buildings;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import nl.lipsum.StaticUtils;
import nl.lipsum.controllers.CameraController;
import nl.lipsum.entities.AbstractEntity;
import nl.lipsum.entities.combat_units.Infantry;
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

    private int selectedArmy = 0;

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
                AbstractEntity unit = new Infantry(x*TILE_SIZE, y*TILE_SIZE, GameController.playerController.getHumanPlayerModel().getBase(), owner);
                //TODO: make sure the right army is has the added entity
                owner.armies.get(selectedArmy).entities.add(unit);
                unit.goTo(owner.armies.get(selectedArmy).getDestBase());
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

    public void click(){
        selectedArmy = (selectedArmy+1)%3;
    }
}
