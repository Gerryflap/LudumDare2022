package nl.lipsum.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import nl.lipsum.*;
import nl.lipsum.controllers.CameraController;
import nl.lipsum.gameLogic.Army;
import nl.lipsum.gameLogic.Base;
import nl.lipsum.gameLogic.BaseGraph;
import nl.lipsum.gameLogic.Ownable;
import nl.lipsum.gameLogic.playermodel.PlayerModel;
import sun.awt.image.ImageWatched;

import java.util.*;
import java.lang.Math;

import static nl.lipsum.Config.TILE_SIZE;

public abstract class AbstractEntity implements Drawable, Ownable, Targetable {

    private final PlayerModel owner;
    private float xPosition;
    private float yPosition;
    private final float xSize;
    private final float ySize;
    private final Texture texture;
    private List<Base> path;
    private Army army;

    private static final int ARRIVAL_RANGE = 10;

    private EntityType entityType;

    // Movement information
    private float speed;
    private final float maxSpeed;

    // Defense information
    private int health;
    private final int maxHealth;

    // Offense information
    private final AttackType attackType;
    private final float bulletDamage;
    private final int bulletReloadSpeed;

    private int bulletReloadProgress;

    public static final Random random = new Random();

    // Unit status
    private EntityStatus previousEntityStatus;
    private EntityStatus entityStatus;
    private Targetable target = null;
    private boolean dead = false;

    public AbstractEntity(float xPosition, float yPosition, PlayerModel owner) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.xSize = getXSize();
        this.ySize = getYSize();
        this.texture = getTexture();
        this.path = new ArrayList<>();
        this.health = getMaxHealth();
        this.maxHealth = getMaxHealth();
        this.bulletDamage = getBulletDamage();
        this.bulletReloadSpeed = getBulletReloadSpeed();
        this.speed = 0;
        this.maxSpeed = getMaxSpeed();
        this.attackType = getAttackType();
        this.owner = owner;

        this.entityType = getEntityType();

        // TODO: IDLE By default, currently testing
        this.entityStatus = EntityStatus.COMBAT;
        this.previousEntityStatus = EntityStatus.COMBAT;
        LudumDare2022.entityController.addEntity(this);

        emitSound(EntitySoundType.SPAWN);
    }

    @Override
    public void draw(SpriteBatch batch, CameraController cameraController) {
        StaticUtils.smartDraw(batch, cameraController, this.getTexture(), this.xPosition - (this.xSize / 2), this.yPosition - (this.ySize / 2), this.xSize, this.ySize);

        if (entityStatus == EntityStatus.COMBAT && attackType == AttackType.RANGED) {
            if (bulletReloadProgress <= 0) {
                // fire bullet
                bulletReloadProgress = bulletReloadSpeed;
                //TODO: Fire bullet ofzo
            }
            bulletReloadProgress -= 1;
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
        float zoomDistance = (1 / (LudumDare2022.cameraController.getCamera().zoom * 5));

        if (!StaticUtils.inRange(LudumDare2022.cameraController, xPosition, yPosition)) {
            return;
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
        if (target != null && target.isDead()) {
            target = null;
        }

        if (health <= 0) {
            setEntityStatus(EntityStatus.DEAD);

            if (previousEntityStatus != EntityStatus.DEAD) {
                emitSound(EntitySoundType.DEATH);
            }

            return;
        }

        if (target != null) {
            attackTarget();
        } else if (!path.isEmpty()) {
            moveToBase();
        }
    }

    private void attackTarget() {
        double dist = StaticUtils.distance(this, target);
        if (dist < getAttackRange()) {
            speed = 0.0f;

            // TODO: Pew Pew
        } else {
            speed = getMaxSpeed();
            float cos = (float) ((target.getxPosition() - xPosition)/dist);
            float sin = (float) ((target.getyPosition() - yPosition)/dist);
            float newX = xPosition + cos * speed * Gdx.graphics.getDeltaTime();
            float newY = yPosition + sin * speed * Gdx.graphics.getDeltaTime();

            attemptToMove(newX, newY);
        }
    }

    private void moveToBase() {
        if (!path.isEmpty()){
            Base nextBase = path.get(0);
            if ((nextBase.getX() * TILE_SIZE <= xPosition + ARRIVAL_RANGE) && (nextBase.getX() * TILE_SIZE >= xPosition - ARRIVAL_RANGE) &&
                (nextBase.getY() * TILE_SIZE <= yPosition + ARRIVAL_RANGE) && (nextBase.getY() * TILE_SIZE >= yPosition - ARRIVAL_RANGE)) {
                path.remove(0);
            }
        }
        if (!path.isEmpty()){
            Base nextBase = path.get(0);
            float diffX = nextBase.getX() * TILE_SIZE - xPosition;
            float diffY = nextBase.getY() * TILE_SIZE - yPosition;
            double factor = Gdx.graphics.getDeltaTime() * maxSpeed / (Math.sqrt(diffX * diffX + diffY * diffY));
            float updateX = (float) (diffX * factor);
            float updateY = (float) (diffY * factor);

            float newX;
            float newY;


            if (Math.abs(updateX) < Math.abs(diffX)) {
                newX = this.xPosition + updateX;
            } else {
                newX = nextBase.getX() * TILE_SIZE;
            }
            if (Math.abs(updateY) < Math.abs(diffY)) {
                newY = this.yPosition + updateY;
            } else {
                newY = nextBase.getY() * TILE_SIZE;
            }

            attemptToMove(newX, newY);
        }
    }

    private void attemptToMove(float newX, float newY) {
        boolean allowedToMove = true;

        if (EntityController.collisionGrid[(int) newY][(int) newX]) {
            int signX = -1;
            int signY = -1;
            if (random.nextFloat() > 0.5) {
                signX = 1;
            }
            if (random.nextFloat() > 0.5) {
                signY = 1;
            }
            newX += signX * random.nextInt(5);
            newY += signY * random.nextInt(5);

            if (0 > newX) {
                newX = 0;
            }
            // this will break if collision grid != movement grid
            if (newX > Config.COLLISION_GRID_WIDTH) {
                newX = Config.COLLISION_GRID_WIDTH;
            }

            if (0 > newY) {
                newY = 0;
            }
            // this will break if collision grid != movement grid
            if (newY > Config.COLLISION_GRID_HEIGHT) {
                newY = Config.COLLISION_GRID_HEIGHT;
            }

            if (EntityController.collisionGrid[(int) newY][(int) newX]) {
                allowedToMove = false;
            }
        }

        if (allowedToMove) {
            EntityController.collisionGrid[(int) this.yPosition][(int) this.xPosition] = false;
            EntityController.collisionGrid[(int) newY][(int) newX] = true;
            this.xPosition = newX;
            this.yPosition = newY;
        }
    }

    @Override
    public boolean isDead() {
        return dead;
    }

    public void goTo(Base b) {
        if(b!=null){
            Base[] closest = getClosestBases();
            path = LudumDare2022.gameController.getBaseGraph().findPath(closest[0], b);
            if (path.size() > 1){
                if (path.get(1) == closest[1]){
                    path.remove(0);
                }
            }
        }
    }

    private Base[] getClosestBases(){
        Base[] output = new Base[2];
        double[] dists = new double[]{-1, -1};
        for(Base b:LudumDare2022.gameController.getBaseGraph().getBases()){
            double d = Math.pow(b.getX()*TILE_SIZE-xPosition, 2) + Math.pow(b.getY()*TILE_SIZE-yPosition, 2);
            if (dists[0] == -1 || dists[0]>d){
                output[1] = output[0];
                dists[1] = dists[0];
                output[0] = b;
                dists[0] = d;
            } else if (dists[1] == -1 || dists[1]>d){
                output[1] = b;
                dists[1] = d;
            }
        }
        return output;
    }

    public void kill() {
        Optional.ofNullable(army).ifPresent(army -> army.removeEntity(this));
        LudumDare2022.entityController.removeEntity(this);
        dead = true;
    }

    public Army getArmy() {
        return army;
    }

    public void setArmy(Army army) {
        Optional.ofNullable(this.army).ifPresent(a -> a.removeEntity(this));
        Optional.ofNullable(army).ifPresent(a -> a.addEntity(this));
        this.army = army;
    }

    /*
     * These constants should be defined for every entity type
     */
    public abstract float getXSize();

    public abstract float getYSize();

    public abstract Texture getTexture();

    public abstract EntityType getEntityType();

    public abstract int getMaxHealth();

    public abstract AttackType getAttackType();

    public abstract float getBulletDamage();

    public abstract int getBulletReloadSpeed();

    public abstract float getMaxSpeed();

    public abstract float getAttackRange();

    public abstract float getVisionRange();

    public float getxPosition() {
        return xPosition;
    }

    public float getyPosition() {
        return yPosition;
    }

    public PlayerModel getOwner() {
        return owner;
    }


    public void setTarget(Targetable target) {
        this.target = target;
    }
}
