package nl.lipsum.gameLogic;

import java.util.*;

public class BaseGraph {
    List<Base> bases;


    public BaseGraph(){
        bases = new ArrayList<>();

        Base base00 = new Base(0,0);
        Base base10 = new Base(10,0);
        Base base20 = new Base(20,0);
        Base base01 = new Base(0,10);
        Base base11 = new Base(10,10);
        Base base21 = new Base(20,10);
        Base base02 = new Base(0,20);
        Base base12 = new Base(10,20);
        Base base22 = new Base(20,20);
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
        base00.addConnection(base11);

        base02.addConnection(base01);
        base02.addConnection(base12);
        base02.addConnection(base11);

        base20.addConnection(base21);
        base20.addConnection(base10);
        base20.addConnection(base11);

        base22.addConnection(base21);
        base22.addConnection(base12);
        base22.addConnection(base11);

        base01.addConnection(base00);
        base01.addConnection(base02);

        base10.addConnection(base00);
        base10.addConnection(base20);

        base21.addConnection(base22);
        base21.addConnection(base20);

        base12.addConnection(base22);
        base12.addConnection(base02);

        base11.addConnection(base00);
        base11.addConnection(base02);
        base11.addConnection(base20);
        base11.addConnection(base22);
    }

    public List<Base> findPath(List<Base> start, Base dest){
        Map<Base, ArrayList<Base>> paths = new HashMap<Base, ArrayList<Base>>();
        Set<Base> visited = new LinkedHashSet<Base>();
        Stack<Base> stack = new Stack<Base>();
        for(Base b: start){
            stack.push(b);
            ArrayList<Base> path = new ArrayList<>();
            path.add(b);
            paths.put(b, path);
        }
        boolean continue_ = true;
        while (continue_ && !stack.isEmpty()) {
            Base vertex = stack.pop();
            if (!visited.contains(vertex)) {
                visited.add(vertex);
                for (Base neighbour : vertex.getConnections()) {
                    stack.push(neighbour);
                    ArrayList<Base> path = (ArrayList<Base>) paths.get(vertex).clone();
                    path.add(neighbour);
                    paths.put(neighbour, path);
                    if (neighbour == dest){
                        continue_ = false;
                        break;
                    }
                }
            }
        }
        return paths.get(dest);
    }
}
