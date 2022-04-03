package nl.lipsum.gameLogic;

import nl.lipsum.gameLogic.playermodel.PlayerModel;

/**
 * Interface for things that can be owned
 */
public interface Ownable {
    PlayerModel getOwner();
}
