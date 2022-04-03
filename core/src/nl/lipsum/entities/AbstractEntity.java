package nl.lipsum.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import nl.lipsum.Drawable;
import nl.lipsum.LudumDare2022;
import nl.lipsum.StaticUtils;
import nl.lipsum.controllers.CameraController;
import nl.lipsum.LudumDare2022;
import nl.lipsum.gameLogic.Army;
import nl.lipsum.gameLogic.Base;
import nl.lipsum.gameLogic.BaseGraph;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;
import java.util.Optional;

import static nl.lipsum.Config.TILE_SIZE;

public class AbstractEntity implements Drawable {

    private float xPosition;
    private float yPosition;
    private float xSize;
    private float ySize;
    private Texture texture;
    private Base previousBase;
    private Base nextBase;
    private List<Base> path;
    private Army army;

    private EntityType entityType;

    // Movement information
    private float speed;
    private float maxSpeed;

    // Defense information
    private int health;
    private int maxHealth;

    // Offense information
    private AttackType attackType;
    private float bulletSpeed;
    private float bulletDamage;
    private int bulletReloadSpeed;

    private int bulletReloadProgress;

    private List<Bullet> bullets = new ArrayList<>();

    // Unit status
    private EntityStatus previousEntityStatus;
    private EntityStatus entityStatus;

    public AbstractEntity(float xPosition, float yPosition, Texture texture, Base base, int health, int maxHealth, float bulletSpeed,
                          float bulletDamage, int bulletReloadSpeed, float maxSpeed, AttackType attackType, EntityType entityType) {
        this(xPosition, yPosition, 75, 75, texture, base, health, maxHealth, bulletSpeed, bulletDamage, bulletReloadSpeed, maxSpeed, attackType, entityType);
    }

    public AbstractEntity(float xPosition, float yPosition, float xSize, float ySize, Texture texture, Base base, int health, int maxHealth, float bulletSpeed,
                          float bulletDamage, int bulletReloadSpeed, float maxSpeed, AttackType attackType, EntityType entityType) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.xSize = xSize;
        this.ySize = ySize;
        this.texture = texture;
        this.previousBase = base;
        this.nextBase = base;
        this.path = new ArrayList<>();
        this.health = health;
        this.maxHealth = maxHealth;
        this.bulletSpeed = bulletSpeed;
        this.bulletDamage = bulletDamage;
        this.bulletReloadSpeed = bulletReloadSpeed;
        this.speed = 0;
        this.maxSpeed = maxSpeed;
        this.attackType = attackType;

        this.entityType = entityType;

        // TODO: IDLE By default, currently testing
        this.entityStatus = EntityStatus.COMBAT;
        this.previousEntityStatus = EntityStatus.COMBAT;
        LudumDare2022.entityController.addEntity(this);

        emitSound(EntitySoundType.SPAWN);
    }

    @Override
    public void draw(SpriteBatch batch, CameraController cameraController) {
        StaticUtils.smartDraw(batch, cameraController, texture, this.xPosition - (this.xSize/2), this.yPosition - (this.ySize/2), this.xSize, this.ySize);

        if (entityStatus == EntityStatus.COMBAT && attackType == AttackType.RANGED) {
            if (bulletReloadProgress <= 0) {
                // fire bullet
                bulletReloadProgress = bulletReloadSpeed;
                bullets.add(new Bullet(this.xPosition, this.yPosition, 100, 50, this.bulletSpeed));
                emitSound(EntitySoundType.FIRE);
            }
            bulletReloadProgress -= 1;
        }

        for (Bullet _bullet :
                bullets) {
            _bullet.draw(batch);
        }
    }

//    private float calculateSortOfDistanceToCenterCamera() {
//        float distance = (float) Math.sqrt(Math.pow(Math.abs(this.xPosition - LudumDare2022.cameraController.getCamera().position.x), 2) + Math.pow(Math.abs(this.yPosition - LudumDare2022.cameraController.getCamera().position.y), 2));
//        distance = ((1 / distance) * 5000) - 5;
//        System.out.println(distance);
//        return (float) Math.pow(2, distance);
//    }

    public void emitSound(EntitySoundType entitySoundType) {
//        float distance = calculateSortOfDistanceToCenterCamera();
        float zoomDistance = (1/(LudumDare2022.cameraController.getCamera().zoom * 5));

        if (!StaticUtils.inRange(LudumDare2022.cameraController, xPosition, yPosition)) {
            return ;
        }

        float volume = 1 * zoomDistance * entitySoundType.getLoudness();
//        System.out.printf("%s %s\n", volume, zoomDistance);
        Sound sound = Gdx.audio.newSound(Gdx.files.internal(entityType.getPath() + entitySoundType.getPath()));
        long id = sound.play();
        sound.setVolume(id, volume);
    }

    public EntityStatus getEntityStatus() {
        return entityStatus;
    }

    public void setEntityStatus(EntityStatus entityStatus) {
        previousEntityStatus = this.entityStatus;
        this.entityStatus = entityStatus;
    }

    public void step() {
        List<Bullet> bulletsToRemove = new ArrayList<>();

        for (Bullet _bullet : bullets) {
            if (_bullet.step()) {
                bulletsToRemove.add(_bullet);
            }
        }

        for (Bullet _bullet : bulletsToRemove) {
            bullets.remove(_bullet);
        }
        if (health <= 0) {
            setEntityStatus(EntityStatus.DEAD);

            if (previousEntityStatus != EntityStatus.DEAD) {
                emitSound(EntitySoundType.DEATH);
            }

            return;
        }
        if (nextBase != null){
            if (nextBase.getX()*TILE_SIZE == xPosition && nextBase.getY()*TILE_SIZE == yPosition){
                previousBase = nextBase;
                if (!path.isEmpty()){
                    nextBase = path.get(0);
                    path.remove(0);
                }
            }
            if (nextBase.getX()*TILE_SIZE != xPosition || nextBase.getY()*TILE_SIZE != yPosition){
                float diffX = nextBase.getX()*TILE_SIZE - xPosition;
                float diffY = nextBase.getY()*TILE_SIZE - yPosition;
                double factor = Gdx.graphics.getDeltaTime()*maxSpeed/(Math.sqrt(diffX*diffX + diffY*diffY));
                float updateX = (float) (diffX*factor);
                float updateY = (float) (diffY*factor);
                if (Math.abs(updateX) < Math.abs(diffX)){
                    this.xPosition += updateX;
                } else {
                    this.xPosition = nextBase.getX()*TILE_SIZE;
                }
                if (Math.abs(updateY) < Math.abs(diffY)){
                    this.yPosition += updateY;
                } else {
                    this.yPosition = nextBase.getY()*TILE_SIZE;
                }
            }
        }
    }

    public void goTo(Base b){
        List<Base> startBases = new ArrayList<>();
        startBases.add(previousBase);
        if (nextBase != previousBase){
            startBases.add(nextBase);
        }
        path = LudumDare2022.gameController.getBaseGraph().findPath(startBases, b);
        nextBase = path.get(0);
        path.remove(0);
    }

    public void kill() {
        Optional.ofNullable(army).ifPresent(army -> army.removeEntity(this));

        LudumDare2022.entityController.removeEntity(this);
    }

    public Army getArmy() {
        return army;
    }

    public void setArmy(Army army) {
        Optional.ofNullable(this.army).ifPresent(a -> a.removeEntity(this));
        Optional.ofNullable(army).ifPresent(a -> a.addEntity(this));
        this.army = army;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}
