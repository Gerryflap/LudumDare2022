package nl.lipsum.entities.combat_units;

import com.badlogic.gdx.graphics.Texture;
import nl.lipsum.entities.AbstractEntity;
import nl.lipsum.entities.AttackType;
import nl.lipsum.entities.EntityType;
import nl.lipsum.gameLogic.playermodel.PlayerModel;

public class Sniper extends AbstractEntity {
    public static float X_SIZE = 48;
    public static float Y_SIZE = 48;
    public Texture TEXTURE;
    public static EntityType ENTITY_TYPE = EntityType.SNIPER;
    public static int MAX_HEALTH = 50;
    public static AttackType ATTACK_TYPE = AttackType.RANGED;
    public static float BULLET_DAMAGE = 100;
    public static int BULLET_RELOAD_SPEED = 20;
    public static float MAX_SPEED = 200;
    public static float ATTACK_RANGE = 250;
    public static float VISION_RANGE = 250;


    public Sniper(float xPosition, float yPosition, PlayerModel owner) {
        super(xPosition, yPosition, owner);
        TEXTURE = new Texture(String.format("player%d/sniper.png", owner.getId()));
    }


    @Override
    public float getXSize() {
        return X_SIZE;
    }

    @Override
    public float getYSize() {
        return Y_SIZE;
    }

    @Override
    public Texture getTexture() {
        return TEXTURE;
    }

    @Override
    public EntityType getEntityType() {
        return ENTITY_TYPE;
    }

    @Override
    public int getMaxHealth() {
        return MAX_HEALTH;
    }

    @Override
    public AttackType getAttackType() {
        return ATTACK_TYPE;
    }

    @Override
    public float getBulletDamage() {
        return BULLET_DAMAGE;
    }

    @Override
    public int getBulletReloadSpeed() {
        return BULLET_RELOAD_SPEED;
    }

    @Override
    public float getMaxSpeed() {
        return MAX_SPEED;
    }

    @Override
    public float getAttackRange() {
        return ATTACK_RANGE;
    }

    @Override
    public float getVisionRange() {
        return VISION_RANGE;
    }
}
