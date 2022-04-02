package nl.lipsum.buildings;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import nl.lipsum.Drawable;
import nl.lipsum.controllers.CameraController;

public class BuildingGrid implements Drawable{
    public final int SIZE_X;
    public final int SIZE_Y;

    private final Building[][] buildings;

    public BuildingGrid(int sizeX, int sizeY) {
        this.SIZE_X = sizeX;
        this.SIZE_Y = sizeY;
        this.buildings = new Building[this.SIZE_X][this.SIZE_Y];
    }

    public Building getBuilding(int x, int y) {
        return buildings[x][y];
    }

    public void setBuilding(int x, int y, Building b) {
        this.buildings[x][y] = b;
    }


    @Override
    public void draw(SpriteBatch batch, CameraController cameraController) {
        for (int x = 0; x < SIZE_X; x++) {
            for (int y = 0; y < SIZE_Y; y++) {
                if(this.buildings[x][y] != null){
                    this.buildings[x][y].draw(batch, cameraController);
                }
            }
        }
    }

    public void step(){
        for (int x = 0; x < SIZE_X; x++) {
            for (int y = 0; y < SIZE_Y; y++) {
                if(this.buildings[x][y] != null) {
                    this.buildings[x][y].step();
                }
            }
        }
    }

    public void dispose() {
        for (int x = 0; x < SIZE_X; x++) {
            for (int y = 0; y < SIZE_Y; y++) {
                if(this.buildings[x][y] != null) {
                    this.buildings[x][y].dispose();
                }
            }
        }
    }
}
