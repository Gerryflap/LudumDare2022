package nl.lipsum.gameLogic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import nl.lipsum.Config;
import nl.lipsum.LudumDare2022;
import nl.lipsum.StaticUtils;
import nl.lipsum.controllers.CameraController;
import nl.lipsum.controllers.GenericController;
import nl.lipsum.entities.AbstractEntity;
import nl.lipsum.entities.EntityController;
import nl.lipsum.entities.PositionalEntityResolver;
import nl.lipsum.entities.Targetable;
import nl.lipsum.gameLogic.playermodel.PlayerModel;

import java.util.*;
import java.util.stream.Collectors;

import static nl.lipsum.Config.BASE_SIZE;
import static nl.lipsum.Config.TILE_SIZE;

public class Base implements GenericController {

    int x;
    int y;
    List<Base> connections;
    Texture texture;
    PlayerModel owner;
    int buildrange;

    private int[] captureProgress;

    public BaseStatus baseStatus;

    private static final int CAPTURE_RANGE = 50;
    public int playerCapturing = -1;

    private static final int CAPTURE_REQUIREMENT = 500;

    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private BitmapFont font = new BitmapFont();

    public Set<AbstractEntity> entitiesInRange = new HashSet<>();


    public Base(int x, int y, PlayerModel initialOwner, int buildrange){
        this.owner = initialOwner;
        this.x = x;
        this.y = y;
        this.connections = new ArrayList<>();
        texture = new Texture("whiteTile.jpg");
        this.buildrange = buildrange;
        captureProgress = new int[4];

        if (initialOwner != null) {
            captureProgress[initialOwner.getId()] = CAPTURE_REQUIREMENT;
            baseStatus = BaseStatus.OWNED;
        } else {
            baseStatus = BaseStatus.NEUTRAL;
        }

    }

    public Base(int x, int y, PlayerModel initialOwner){
        this(x,y,initialOwner, 2);
    }


    public void addConnection(Base b){
        this.connections.add(b);
    }

    public List<Base> getConnections() {
        return connections;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getBuildrange(){
        return buildrange;
    }

    public PlayerModel getOwner(){
        return owner;
    }

    public void setOwner(PlayerModel owner) {
        this.owner = owner;
    }

    public String toString(){
        return "Base x:" + Integer.toString(x) + " y:" + Integer.toString(y);
    }

    public void updateInRangeUnits() {
        entitiesInRange = LudumDare2022.positionalEntityResolver.getTargetsInRange(x*TILE_SIZE-BASE_SIZE/2, y*TILE_SIZE-BASE_SIZE/2, BASE_SIZE/2).stream().filter(t -> t instanceof AbstractEntity).map(t -> (AbstractEntity) t).collect(Collectors.toSet());
    }

    @Override
    public void step() {
        if (entitiesInRange.size() > 0) {
            int[] entityCount = new int[Config.PLAYER_COUNT];

            int highestCount = 0;
            int armyOwnerWithHighestCount = 0;

            // find army owner with dominance in this base
            for (AbstractEntity abstractEntity : entitiesInRange) {
                int _ownerId = abstractEntity.getOwner().getId();
                entityCount[_ownerId] += 1;
                if (entityCount[_ownerId] > highestCount) {
                    highestCount = entityCount[_ownerId];
                    armyOwnerWithHighestCount = _ownerId;
                }
            }

            // is the dominant player also the owner of this base?
            if (this.owner != null && this.owner.getId() == armyOwnerWithHighestCount) {
                // if so we're good, ensure noone is capturing and reset any capture progress
                captureProgress[armyOwnerWithHighestCount] = CAPTURE_REQUIREMENT;
                playerCapturing = -1;
                baseStatus = BaseStatus.OWNED;
            } else {
                // if not, we need to update the capture progress, dependent on two scenarios

                // 1. There is an owner that we first need to dethrone
                if (this.owner != null) {
                    this.captureProgress[this.owner.getId()] -= 1;
                    baseStatus = BaseStatus.DETHRONING;
                    playerCapturing = armyOwnerWithHighestCount;

                    // If owner is kicked to 0 dominance, make the base neutral
                    if (this.captureProgress[this.owner.getId()] <= 0) {
                        this.owner = null;
                    }
                    // 2. There isn't another owner (possibly already dethroned) and we must simply capture the base
                } else {
                    // Add capture progress
                    this.captureProgress[armyOwnerWithHighestCount] += 1;
                    baseStatus = BaseStatus.CAPTURING;
                    playerCapturing = armyOwnerWithHighestCount;

                    // Check if we have sufficient progress to become dominant
                    if (captureProgress[armyOwnerWithHighestCount] >= CAPTURE_REQUIREMENT) {
                        this.owner = PlayerController.getPlayedModelByOwnerId(armyOwnerWithHighestCount);
                        playerCapturing = -1;
                        baseStatus = BaseStatus.OWNED;
                    }
                }
            }
        }
    }

    @Override
    public void render(SpriteBatch batch, CameraController cameraController) {
        StaticUtils.smartDraw(batch, cameraController, texture, x*TILE_SIZE-BASE_SIZE/2, y*TILE_SIZE-BASE_SIZE/2, BASE_SIZE, BASE_SIZE);

        int capStrLen = 10;

        int captIntermediates = CAPTURE_REQUIREMENT / capStrLen;

        int currentCapProg = -1;
        for (int counter = 0; counter < captureProgress.length; counter++)
        {
            if (captureProgress[counter] > currentCapProg)
            {
                currentCapProg = captureProgress[counter];
            }
        }

        StringBuilder stringBuilderBuilderBuilderBuilder = new StringBuilder();
        if (baseStatus == BaseStatus.CAPTURING) {
            stringBuilderBuilderBuilderBuilder.append("Capturing [");
        } else if (baseStatus == BaseStatus.DETHRONING) {
            stringBuilderBuilderBuilderBuilder.append("Dethroning [");
        }
        for (int i = 0; i < capStrLen; i++) {
            if (i*captIntermediates < currentCapProg) {
                stringBuilderBuilderBuilderBuilder.append("=");
            } else {
                stringBuilderBuilderBuilderBuilder.append("-");
            }
        }
        stringBuilderBuilderBuilderBuilder.append("]");

        if (baseStatus == BaseStatus.CAPTURING || baseStatus == BaseStatus.DETHRONING) {
            font.draw(batch, stringBuilderBuilderBuilderBuilder.toString(), x*TILE_SIZE-BASE_SIZE/2, y*TILE_SIZE-BASE_SIZE/2);
        }

    }

    @Override
    public void dispose() {
        texture.dispose();
        shapeRenderer.dispose();
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}
