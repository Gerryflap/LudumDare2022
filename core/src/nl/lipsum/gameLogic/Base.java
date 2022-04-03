package nl.lipsum.gameLogic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import nl.lipsum.StaticUtils;
import nl.lipsum.controllers.CameraController;
import nl.lipsum.controllers.GenericController;
import nl.lipsum.gameLogic.playermodel.PlayerModel;

import java.util.ArrayList;
import java.util.List;

import static nl.lipsum.Config.BASE_SIZE;
import static nl.lipsum.Config.TILE_SIZE;

public class Base implements GenericController {
    int x;
    int y;
    List<Base> connections;
    Texture texture;
    PlayerModel owner;
    int buildrange;

    public Base(int x, int y, PlayerModel initialOwner, int buildrange){
        this.owner = initialOwner;
        this.x = x;
        this.y = y;
        this.connections = new ArrayList<>();
        texture = new Texture("whiteTile.jpg");
        this.buildrange = buildrange;
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

    public String toString(){
        return "Base x:" + Integer.toString(x) + " y:" + Integer.toString(y);
    }

    @Override
    public void step() {
        //TODO: check if base has switched sides
    }

    @Override
    public void render(SpriteBatch batch, CameraController cameraController) {
        StaticUtils.smartDraw(batch, cameraController, texture, x*TILE_SIZE-BASE_SIZE/2, y*TILE_SIZE-BASE_SIZE/2, BASE_SIZE, BASE_SIZE);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}
