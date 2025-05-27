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
        tree.put(13, "Roma");
        tree.put(0, "Misha");
        tree.put(6, "Stas");
        tree.put(14, "Stas2");
        tree.put(15, "Stas3");
        tree.put(16, "Stas4");
        tree.put(17, "Stas5");
        System.out.println();
        System.out.println();
        tree.printTree();
    }
}
