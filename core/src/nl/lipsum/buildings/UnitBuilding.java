package nl.lipsum.buildings;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import nl.lipsum.StaticUtils;
import nl.lipsum.controllers.CameraController;
import nl.lipsum.entities.AbstractEntity;
import nl.lipsum.entities.combat_units.Infantry;
import nl.lipsum.gameLogic.GameController;
import nl.lipsum.gameLogic.playermodel.PlayerModel;

import java.util.HashSet;
import java.util.Set;

import static nl.lipsum.Config.TILE_SIZE;

public abstract class UnitBuilding extends Building {

    private final Texture tileTexture;

    private int trainingProgress;
    private final int trainingTime;
    private final int unitCap;

    private final Set<AbstractEntity> units;
    private int unitPointer = 0;

    private int selectedArmy;

    public UnitBuilding(int x, int y, PlayerModel owner, int trainingTime, int unitCap) {
        super(x, y, owner);
        this.trainingTime = trainingTime;
        this.unitCap = unitCap;
        this.units = new HashSet<>(unitCap);
        this.selectedArmy = owner.getSelectedArmy();
        this.tileTexture = new Texture("greenTile.jpg");
    }

    @Override
    public void step() {
        trainingProgress += 1;
        if (trainingProgress > trainingTime) {
            if (this.units.size() >= unitCap) {

            } else {
                trainingProgress = 0;
                AbstractEntity unit = new Infantry(x*TILE_SIZE, y*TILE_SIZE, owner);
                unit.setBuilding(this);
                //TODO: make sure the right army is has the added entity
                owner.armies.get(selectedArmy).entities.add(unit);
                unit.goTo(owner.armies.get(selectedArmy).getDestBase());
                this.units.add(unit);
            }
        }
    }

    @Override
    public void draw(SpriteBatch batch, CameraController cameraController) {
        StaticUtils.smartDraw(batch, cameraController, this.getTileTexture(), TILE_SIZE * this.x - TILE_SIZE/2, TILE_SIZE * this.y- TILE_SIZE/2, TILE_SIZE, TILE_SIZE);
    }

    @Override
    public void dispose() {

    }

    public Texture getTileTexture() {
        return tileTexture;
    }

    public void click(){
        selectedArmy = (selectedArmy+1)%3;
    }

    public void reportKilled(AbstractEntity entity) {
        units.remove(entity);
    }
}
