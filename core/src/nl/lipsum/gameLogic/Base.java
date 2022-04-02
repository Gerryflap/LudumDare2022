package nl.lipsum.gameLogic;

import java.util.ArrayList;
import java.util.List;

public class Base {
    int x;
    int y;
    List<Base> connections;

    public Base(int x, int y){
        this.x = x;
        this.y = y;
        this.connections = new ArrayList<>();
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
}
