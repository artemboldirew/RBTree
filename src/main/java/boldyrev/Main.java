package boldyrev;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {
        RBTreeMap<String, Integer> tree = new RBTreeMap<>();
        tree.put("Abcdef", 10);
        tree.put("cefhgri", 10);
        tree.put("10AB", 10);
        tree.put("Cada", 10);
        tree.put("Avtyrf", 10);
        tree.put("B", 10);
        tree.put("Ba", 10);
        System.out.println();
        tree.printTree();
        System.out.println();
        tree.fromA2B();
        System.out.println();

        TreeMap<String, Integer> tree2 = new TreeMap<>();
        tree2.put("Abcdef", 10);
        tree2.put("cefhgri", 10);
        tree2.put("10AB", 10);
        tree2.put("Cada", 10);
        tree2.put("Avtyrf", 10);
        tree2.put("B", 10);
        tree2.put("Ba", 10);
        NavigableMap<String, Integer> subMap = tree2.subMap("A", true, "B", true);

        for (Map.Entry<String, Integer> entry : subMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

    }
}
