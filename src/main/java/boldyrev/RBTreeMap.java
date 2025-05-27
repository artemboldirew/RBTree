package boldyrev;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RBTreeMap<K extends Comparable<K>, V> implements Map<K, V> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    public static class Node<K, V> {
        K key;
        V value;
        boolean isRed;
        Node<K, V> parent;
        Node<K, V> left;
        Node<K, V> right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.isRed = true;
        }

        public Node(K key, V value, Node<K, V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
            this.isRed = true;
        }
    }

    private int size;
    public Node<K, V> root;


    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public V get(Object key2) {
        K key = (K) key2;
        Node<K, V> next = root;
        while (next.key != key) {
            if (key.compareTo(next.key) < 0) {
                if (next.left != null) {
                    next = next.left;
                }
                else {
                    throw new RuntimeException("Нет такого ключа");
                }
            }
            else {
                if (next.right != null) {
                    next = next.right;
                } else {
                    throw new RuntimeException("Нет такого ключа");
                }
            }
        }
        return next.value;
    }

    @Override
    public V put(K key, V value) {
        if (size == 0) {
            root = new Node<>(key, value);
            root.isRed = false;
            size++;
            return value;
        }
        Node<K, V> next = root;
        while (next != null) {
            if (key.compareTo(next.key) < 0) {
                if (next.left != null) {
                    next = next.left;
                } else {
                    next.left = new Node<>(key, value, next);
                    size++;
                    return value;
                }
            }
            else {
                if (next.right != null) {
                    next = next.right;
                } else {
                    next.right = new Node<>(key, value, next);
                    size++;
                    return value;
                }
            }
        }
        return value;
    }

    @Override
    public V remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<K> keySet() {
        return Set.of();
    }

    @Override
    public Collection<V> values() {
        return List.of();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return Set.of();
    }

    private void balanceTree(Node<K, V> node) {
        Node<K, V> parent = node.parent;

        if (parent != null && parent.isRed && parent.parent != null) {
            Node<K, V> grandParent = parent.parent;
            Node<K, V> uncle = (grandParent.left == parent) ? grandParent.right : grandParent.left;
            if (isNodeRed(uncle) && isNodeRed(parent)) { // case: 1 - дядя и отец красные
                parent.isRed = false;
                uncle.isRed = false;
                grandParent.isRed = grandParent != root;
            }
        }
    }

    private void printTreeRecursive(Node<K, V> node, String prefix, boolean isLeft) {
        if (node == null) return;

        System.out.println(prefix + (isLeft ? "├── " : "└── ") + node.value);
        printTreeRecursive(node.left, prefix + (isLeft ? "│   " : "    "), true);
        printTreeRecursive(node.right, prefix + (isLeft ? "│   " : "    "), false);
    }

    public void printTree() {
        printTreeRecursive(root, "", false);
    }


    public void rotateRight(Node<K, V> h) {
        Node<K, V> leftChild = h.left;
        Node<K, V> rightGrandSon = leftChild.right;
        if (h == root) {
            root = leftChild;
        }
        else if (h == h.parent.right) {
            h.parent.right = leftChild;
        }
        else {
            h.parent.left = leftChild;
        }
        leftChild.parent = h.parent;
        leftChild.right = h;
        h.left = rightGrandSon;
    }

    private void rotateLeft(Node<K, V> h) {

    }

    private boolean isNodeRed(Node<K, V> h) {
        boolean isRed = h != null && h.isRed;
        return isRed;
    }
}
