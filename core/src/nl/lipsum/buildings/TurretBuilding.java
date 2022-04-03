package nl.lipsum.buildings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import nl.lipsum.LudumDare2022;
import nl.lipsum.StaticUtils;
import nl.lipsum.controllers.CameraController;
import nl.lipsum.entities.Targetable;
import nl.lipsum.gameLogic.playermodel.PlayerModel;

import static nl.lipsum.Config.RESOURCES_PER_BUILDING_PER_SECOND;
import static nl.lipsum.Config.TILE_SIZE;

public class TurretBuilding extends Building{
    public final Texture tileTexture;
    public int vision = 300;
    public int attackRange = 300;
    public int bulletDamage = 100;
    public float bulletReloadSpeed = 100;
    public float bulletReloadProgress = bulletReloadSpeed;

    public TurretBuilding(int x, int y, PlayerModel owner) {
        super(x, y, owner);
        this.tileTexture = new Texture(String.format("whiteTile.jpg", owner.getId()));
//        this.tileTexture = new Texture(String.format("player%d/turret_building.png", owner.getId()));
    }

    public void step(){
        if(!isDead()){
            if(bulletReloadProgress >=0 ){
                bulletReloadProgress -= Gdx.graphics.getDeltaTime();
            }
            Targetable target = (LudumDare2022.positionalEntityResolver.getClosestHostileTarget(this, this.vision));
            double dist = StaticUtils.distance(this, target);
            if (dist < this.attackRange) {
                if (bulletReloadProgress <= 0) {
                    target.damage(this.bulletDamage);
                    bulletReloadProgress = bulletReloadSpeed;
                }
            }
        }
    }
    @Override
    public void draw(SpriteBatch batch, CameraController cameraController) {
        if(!isDead()){
            StaticUtils.smartDraw(batch, cameraController, tileTexture, TILE_SIZE * this.x - TILE_SIZE/2, TILE_SIZE * this.y- TILE_SIZE/2, TILE_SIZE, TILE_SIZE);
        }
    }

    public void dispose(){

    }

    public void click(){}
}
