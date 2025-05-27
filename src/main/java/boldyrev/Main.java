package boldyrev;

public class Main {
    public static void main(String[] args) {
        System.out.println("start");
        System.out.println();
        RBTreeMap<Integer, String> tree = new RBTreeMap<>();
        tree.put(5, "Artem");
        tree.put(3, "Gleb");
        tree.put(7, "Egor");
        tree.put(1, "Huesos");
//        String d = tree.get(3);
//        System.out.println(d);
        tree.printTree();
        tree.rotateRight(tree.root);
        System.out.println();
        System.out.println();
        tree.printTree();
    }
}
