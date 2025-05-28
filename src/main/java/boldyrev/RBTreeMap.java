package boldyrev;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RBTreeMap<K extends Comparable<K>, V> implements Map<K, V> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private static class Node<K, V> {
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
        Node<K, V> node = null;
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
                    node = next.left;
                    balanceTree(node);
                    //printTree();
                    //System.out.println();
                    size++;
                    return value;
                }
            }
            else {
                if (next.right != null) {
                    next = next.right;
                } else {
                    next.right = new Node<>(key, value, next);
                    node = next.right;
                    balanceTree(node);
                    //printTree();
                    //System.out.println();
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
        //System.out.println("Вызван для " + node.value);
        //printTree();
        if (node == null) {
            throw new RuntimeException("Вставляемый элемент равен нулю");
        }
        Node<K, V> parent = node.parent;

        if (parent != null && parent.isRed && parent.parent != null) {
            Node<K, V> grandParent = parent.parent;
            Node<K, V> uncle = (grandParent.left == parent) ? grandParent.right : grandParent.left;
            if (isNodeRed(uncle) && isNodeRed(parent)) { // case: 1 - дядя и отец красные
                parent.isRed = false;
                uncle.isRed = false;
                grandParent.isRed = grandParent != root;
                balanceTree(grandParent);
            }
            else if (!isNodeRed(uncle) && (node == parent.right ^ parent == grandParent.right)) {
                if (node == parent.left) {
                    rotateRight(parent);
                    rotateLeft(grandParent);
                    grandParent.isRed = true;
                    grandParent.parent.isRed = false;
                    balanceTree(grandParent);
                }
                else {
                    rotateLeft(parent);
                    rotateRight(grandParent);
                    grandParent.isRed = true;
                    grandParent.parent.isRed = false;
                    balanceTree(grandParent);
                }
            }

            else if (!isNodeRed(uncle) && (node == parent.right) == (parent == grandParent.right)) {
                if (node == parent.left) {
                    rotateRight(grandParent);
                    grandParent.isRed = true;
                    grandParent.parent.isRed = false;
                    balanceTree(grandParent);
                }
                else {
                    rotateLeft(grandParent);
                    grandParent.isRed = true;
                    grandParent.parent.isRed = false;
                    balanceTree(grandParent);
                }
            }

        }
    }

    private void printTreeRecursive(Node<K, V> node, String prefix, boolean isLeft) {
        if (node == null) return;
        String color = node.isRed ? "R" : "B";
        System.out.println(prefix + (isLeft ? "├── " : "└── ") + color + " " + node.key + ": " +  node.value);
        printTreeRecursive(node.left, prefix + (isLeft ? "│   " : "    "), true);
        printTreeRecursive(node.right, prefix + (isLeft ? "│   " : "    "), false);
    }

    public void printTree() {
        printTreeRecursive(root, "", false);
    }


    public void rotateRight(Node<K, V> h) {
        if (h == null || h.left == null) {
            return; // или выбросить исключение
        }

        Node<K, V> leftChild = h.left;
        Node<K, V> rightGrandSon = leftChild.right;

        leftChild.parent = h.parent;

        if (h == root) { //замена ссылки у родителя вершины
            root = leftChild;
        } else if (h == h.parent.right) {
            h.parent.right = leftChild;
        } else {
            h.parent.left = leftChild;
        }

        leftChild.right = h;//замена связи у h и leftChild
        h.parent = leftChild;

        h.left = rightGrandSon;
        if (rightGrandSon != null) {
            rightGrandSon.parent = h;
        }
    }

    private void rotateLeft(Node<K, V> h) {
        if (h == null || h.right == null) {
            return; // или выбросить исключение
        }

        Node<K, V> rightChild = h.right;
        Node<K, V> leftGrandSon = rightChild.left;

        rightChild.parent = h.parent;

        if (h == root) { //замена ссылки у родителя вершины
            root = rightChild;
        } else if (h == h.parent.right) {
            h.parent.right = rightChild;
        } else {
            h.parent.left = rightChild;
        }

        rightChild.left = h;//замена связи у h и leftChild
        h.parent = rightChild;

        h.right = leftGrandSon;
        if (leftGrandSon != null) {
            leftGrandSon.parent = h;
        }
    }

    private boolean isNodeRed(Node<K, V> h) {
        boolean isRed = h != null && h.isRed;
        return isRed;
    }

    public void fromA2B() {
        if (root.key instanceof String) {
            fromA2BHelper(root);
        }
    }

    public void fromA2BHelper(Node<K, V> node) {
        if (node == null) {
            return;
        }
        String key = (String) node.key;
        if (key.compareTo("B") > 0) {
            fromA2BHelper(node.left);
        }
        else if (key.compareTo("A") >= 0 && key.compareTo("B") <= 0) {
            System.out.println(node.key + ": " + node.value);
            fromA2BHelper(node.left);
            fromA2BHelper(node.right);
        }
        else {
            fromA2BHelper(node.right);
        }
    }
}
