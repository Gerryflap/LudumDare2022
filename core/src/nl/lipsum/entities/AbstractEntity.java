package nl.lipsum.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import nl.lipsum.Drawable;
import nl.lipsum.gameLogic.Base;
import nl.lipsum.gameLogic.BaseGraph;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

import static nl.lipsum.Config.TILE_SIZE;

public class AbstractEntity implements Drawable {
    float xPosition;
    float yPosition;
    float xSize;
    float ySize;
    Texture texture;
    Base previousBase;
    Base nextBase;
    List<Base> path;

    public AbstractEntity(float xPosition, float yPosition, Texture texture, Base base) {
        this(xPosition, yPosition, 10, 10, texture, base);
    }

    public AbstractEntity(float xPosition, float yPosition, float xSize, float ySize, Texture texture, Base base) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.xSize = xSize;
        this.ySize = ySize;
        this.texture = texture;
        this.previousBase = base;
        this.nextBase = base;
        this.path = new ArrayList<>();
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, this.xPosition - (this.xSize/2), this.yPosition - (this.ySize/2), this.xSize, this.ySize);
    }

    public void step(){
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
            if (nextBase != previousBase){
                float diffX = nextBase.getX()*TILE_SIZE - xPosition;
                float diffY = nextBase.getY()*TILE_SIZE - yPosition;
                double factor = Gdx.graphics.getDeltaTime()*100/(Math.sqrt(diffX*diffX + diffY*diffY));
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
