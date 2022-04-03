package nl.lipsum.gameLogic;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import nl.lipsum.LudumDare2022;
import nl.lipsum.controllers.CameraController;
import nl.lipsum.controllers.GenericController;

import java.util.*;

import static nl.lipsum.Config.WIDTH_IN_TILES;
import static nl.lipsum.Config.HEIGHT_IN_TILES;
import static nl.lipsum.LudumDare2022.humanPlayerModel;

public class BaseGraph implements GenericController {
    List<Base> bases;

    public BaseGraph(){
        bases = new ArrayList<>();
        Base base00 = new Base(3,3, humanPlayerModel);
        Base base10 = new Base((WIDTH_IN_TILES-1)/2,3, null);
        Base base20 = new Base((WIDTH_IN_TILES-1)-3,3, null);
        Base base01 = new Base(3,(HEIGHT_IN_TILES-1)/2, null);
        Base base11 = new Base((WIDTH_IN_TILES-1)/2,(HEIGHT_IN_TILES-1)/2, null);
        Base base21 = new Base((WIDTH_IN_TILES-1)-3,(HEIGHT_IN_TILES-1)/2, null);
        Base base02 = new Base(3,(HEIGHT_IN_TILES-1)-3, null);
        Base base12 = new Base((WIDTH_IN_TILES-1)/2,(HEIGHT_IN_TILES-1)-3, null);
        Base base22 = new Base((WIDTH_IN_TILES-1)-3,(HEIGHT_IN_TILES-1)-3, null);
        bases.add(base00);
        bases.add(base01);
        bases.add(base02);
        bases.add(base10);
        bases.add(base11);
        bases.add(base12);
        bases.add(base20);
        bases.add(base21);
        bases.add(base22);

        base00.addConnection(base01);
        base00.addConnection(base10);
        //Center needs to be last otherwise breadth first search doesn't work as pathfinding
//        base00.addConnection(base11);

        base02.addConnection(base01);
        base02.addConnection(base12);
//        base02.addConnection(base11);

        base20.addConnection(base21);
        base20.addConnection(base10);
//        base20.addConnection(base11);

        base22.addConnection(base21);
        base22.addConnection(base12);
//        base22.addConnection(base11);

        base01.addConnection(base00);
        base01.addConnection(base02);
        base01.addConnection(base11);

        base10.addConnection(base00);
        base10.addConnection(base20);
        base10.addConnection(base11);

        base21.addConnection(base22);
        base21.addConnection(base20);
        base21.addConnection(base11);

        base12.addConnection(base22);
        base12.addConnection(base02);
        base12.addConnection(base11);

        base11.addConnection(base01);
        base11.addConnection(base12);
        base11.addConnection(base21);
        base11.addConnection(base10);
    }

    public List<Base> findPath(Base start, Base dest){
        Map<Base, ArrayList<Base>> paths = new HashMap<Base, ArrayList<Base>>();
        Set<Base> visited = new LinkedHashSet<Base>();
        Queue<Base> stack = new LinkedList<Base>();
        boolean continue_ = true;
        stack.add(start);
        ArrayList<Base> path = new ArrayList<>();
        path.add(start);
        paths.put(start, path);
        if (start == dest){
            continue_ = false;
        }
        while (continue_ && !stack.isEmpty()) {
            Base vertex = stack.remove();
            if (!visited.contains(vertex)) {
                visited.add(vertex);
                for (Base neighbour : vertex.getConnections()) {
                    stack.add(neighbour);
                    path = (ArrayList<Base>) paths.get(vertex).clone();
                    path.add(neighbour);
                    if(!paths.containsKey(neighbour)){
                        paths.put(neighbour, path);
                    }
                    if (neighbour == dest){
                        continue_ = false;
                        break;
                    }
                }
            }
        }
        return paths.get(dest);
    }

    public List<Base> getBases() {
        return bases;
    }


    @Override
    public void step() {
        for(Base b:bases){
            b.step();
        }
    }

    @Override
    public void render(SpriteBatch batch, CameraController cameraController) {
        for(Base b:bases){
            b.render(batch, cameraController);
        }
    }

    @Override
    public void dispose() {
        for(Base b:bases){
            b.dispose();
        }
    }
}
