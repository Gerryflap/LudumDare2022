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

    protected int trainingProgress;
    protected final int trainingTime;
    protected final int unitCap;

    protected final Set<AbstractEntity> units;
    protected int unitPointer = 0;

    protected int selectedArmy;

    public UnitBuilding(int x, int y, PlayerModel owner, int trainingTime, int unitCap) {
        super(x, y, owner);
        this.trainingTime = trainingTime;
        this.unitCap = unitCap;
        this.units = new HashSet<>(unitCap);
        this.selectedArmy = owner.getSelectedArmy();
        this.tileTexture = new Texture("greenTile.jpg");
    }

    @Override
    public void draw(SpriteBatch batch, CameraController cameraController) {
        if(!isDead()){
            StaticUtils.smartDraw(batch, cameraController, this.getTileTexture(), TILE_SIZE * this.x - TILE_SIZE/2, TILE_SIZE * this.y- TILE_SIZE/2, TILE_SIZE, TILE_SIZE);
        }
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
