package nl.lipsum.entities.combat_units;

import com.badlogic.gdx.graphics.Texture;
import nl.lipsum.entities.AbstractEntity;
import nl.lipsum.entities.AttackType;
import nl.lipsum.entities.EntityType;
import nl.lipsum.gameLogic.Base;
import nl.lipsum.gameLogic.playermodel.PlayerModel;

public class Infantry extends AbstractEntity {
    public static float X_SIZE = 16;
    public static float Y_SIZE = 16;
    public Texture TEXTURE;
    public static EntityType ENTITY_TYPE = EntityType.INFANTRY;
    public static int MAX_HEALTH = 100;
    public static AttackType ATTACK_TYPE = AttackType.RANGED;
    public static float BULLET_DAMAGE = 5;
    public static int BULLET_RELOAD_SPEED = 10;
    public static float MAX_SPEED = 250;

    public Infantry(float xPosition, float yPosition, Base base, PlayerModel owner) {
        super(xPosition, yPosition, base, owner);
        TEXTURE = new Texture(String.format("player%d/infantry.png", owner.getId()));
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
}
