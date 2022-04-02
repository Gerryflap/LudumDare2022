package nl.lipsum.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import nl.lipsum.Drawable;
import nl.lipsum.StaticUtils;
import nl.lipsum.controllers.CameraController;
import nl.lipsum.gameLogic.Base;
import nl.lipsum.gameLogic.BaseGraph;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

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
    private EntityStatus entityStatus;

    public AbstractEntity(float xPosition, float yPosition, Texture texture, Base base, int health, int maxHealth, float bulletSpeed,
                          float bulletDamage, int bulletReloadSpeed, float maxSpeed, AttackType attackType) {
        this(xPosition, yPosition, 75, 75, texture, base, health, maxHealth, bulletSpeed, bulletDamage, bulletReloadSpeed, maxSpeed, attackType);
    }

    public AbstractEntity(float xPosition, float yPosition, float xSize, float ySize, Texture texture, Base base, int health, int maxHealth, float bulletSpeed,
                          float bulletDamage, int bulletReloadSpeed, float maxSpeed, AttackType attackType) {
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

        // TODO: IDLE By default, currently testing
        this.entityStatus = EntityStatus.COMBAT;
    }

    @Override
    public void draw(SpriteBatch batch, CameraController cameraController) {
        StaticUtils.smartDraw(batch, cameraController, texture, this.xPosition - (this.xSize/2), this.yPosition - (this.ySize/2), this.xSize, this.ySize);

        if (entityStatus == EntityStatus.COMBAT && attackType == AttackType.RANGED) {
            if (bulletReloadProgress <= 0) {
                bulletReloadProgress = bulletReloadSpeed;
                bullets.add(new Bullet(this.xPosition, this.yPosition, 100, 50, this.bulletSpeed));
                // fire bullet
            }
            bulletReloadProgress -= 1;
        }

        for (Bullet _bullet :
                bullets) {
            _bullet.draw(batch);

        }


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

        if (nextBase != previousBase || !path.isEmpty()){
            if (nextBase.getX()*TILE_SIZE == xPosition && nextBase.getY()*TILE_SIZE == yPosition){
                previousBase = nextBase;
//                System.out.println(nextBase);
                if (!path.isEmpty()){
                    nextBase = path.get(0);
                    path.remove(0);
                }
//                System.out.println(nextBase);
                System.out.println("hoi" + nextBase + previousBase);
            }
            if (nextBase != previousBase || !path.isEmpty()){
                float diffX = nextBase.getX()*TILE_SIZE - xPosition;
                float diffY = nextBase.getY()*TILE_SIZE - yPosition;
                double factor = Gdx.graphics.getDeltaTime()*maxSpeed/(Math.sqrt(diffX*diffX + diffY*diffY));
                float updateX = (float) (diffX*factor);
                float updateY = (float) (diffY*factor);

//                System.out.println("udpate pos");
//                System.out.println(xPosition);
//                System.out.println(yPosition);
//                System.out.println(diffX);
//                System.out.println(updateX);
//                System.out.println(diffY);
//                System.out.println(updateX);
                if (Math.abs(updateX) < Math.abs(diffX)){
                    this.xPosition += updateX;
                } else {
                    this.xPosition = nextBase.getX()*TILE_SIZE;
                }
                if (Math.abs(updateY) < Math.abs(diffY)){
                    this.yPosition += updateY;
                } else {
                    this.yPosition = nextBase.getY()*TILE_SIZE;
//                    System.out.println("At ypos " + this.yPosition);
                }
            }
        }

    }

    public void goTo(Base b, BaseGraph baseGraph){
        List<Base> startBases = new ArrayList<>();
        startBases.add(previousBase);
        if (nextBase != previousBase){
            startBases.add(nextBase);
        }
        path = baseGraph.findPath(startBases, b);
        System.out.println(path);
        nextBase = path.get(0);
        path.remove(0);
    }
}
