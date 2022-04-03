package nl.lipsum.entities;

import nl.lipsum.gameLogic.playermodel.PlayerModel;

public interface Targetable {
    float getxPosition();
    float getyPosition();
    PlayerModel getOwner();
    boolean isDead();

}
