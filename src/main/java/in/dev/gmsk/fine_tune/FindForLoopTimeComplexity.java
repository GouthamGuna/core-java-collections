package in.dev.gmsk.fine_tune;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindForLoopTimeComplexity {

    public static void main(String[] args) {



    }

}

class Graph<T>{

    private Map<T, List<T>> map = new HashMap<>();

    public void addEdge(T node1, T node2){

        if(!map.containsKey(node1)){
            map.put(node1, new ArrayList<>());
        }
        map.get(node1).add(node2);

        if(!map.containsKey(node2)){
            map.put(node2, new ArrayList<>());
        }
        map.get(node2).add(node1);
    }
}
