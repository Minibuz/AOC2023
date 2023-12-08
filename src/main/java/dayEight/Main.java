package dayEight;

import utils.FileReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static class Tree<T> {
        private final Node<T> root;

        public Tree(Node<T> root) {
            this.root = root;
        }

        public void addLeftChild(Node<T> left) {
            this.root.leftChild = left;
        }

        public void addRightChild(Node<T> right) {
            this.root.rightChild = right;
        }

        public int move(String line) {
            var moves = line.toCharArray();

            var movement = 0;
            var moveId = 0;
            var current = root;
            while (!"ZZZ".equals(current.data)) {
                var nextMove = moveId % moves.length;
                moveId++;
                movement++;

                var moveTodo = moves[nextMove] + "";
                if ("L".equals(moveTodo)) {
                    current = current.leftChild;
                }
                // "R".equals(moveTodo)
                else {
                    current = current.rightChild;
                }
            }
            return movement;
        }

        public static class Node<T> {
            private final T data;
            private Node<T> leftChild;
            private Node<T> rightChild;

            public Node(T data) {
                this.data = data;
                leftChild = this;
                rightChild = this;
            }

            @Override
            public String toString() {
                return data + " = (" + leftChild.data + "," + rightChild.data + ")";
            }
        }

        public static Tree<String> createTree(List<String> lines) {
            lines.removeFirst();

            Node<String> origin = null;
            Map<String, Node<String>> nodes = new HashMap<>();
            for (String s : lines) {
                var line = s.replace("(", "").replace(")", "");
                var elements = line.split(" = ");
                var nodeId = elements[0];
                var children = elements[1].split(", ");
                var leftNodeId = children[0];
                var rightNodeId = children[1];

                // First line
                if (origin == null) {
                    origin = new Node<>(nodeId);
                    nodes.put(nodeId, origin);

                    Node<String> leftChild = new Node<>(leftNodeId);
                    origin.leftChild = leftChild;
                    nodes.put(leftNodeId, leftChild);

                    Node<String> rightChild = new Node<>(rightNodeId);
                    origin.rightChild = rightChild;
                    nodes.put(rightNodeId, rightChild);
                }
                // Others
                else {
                    var currentNode = nodes.getOrDefault(nodeId, new Node<>(nodeId));
                    var leftChild = nodes.getOrDefault(leftNodeId, new Node<>(leftNodeId));
                    if (leftNodeId.equals(rightNodeId)) {
                        nodes.put(leftNodeId, leftChild);
                    }
                    var rightChild = nodes.getOrDefault(rightNodeId, new Node<>(rightNodeId));

                    currentNode.leftChild = leftChild;
                    currentNode.rightChild = rightChild;

                    nodes.put(nodeId, currentNode);
                    nodes.put(leftNodeId, leftChild);
                    nodes.put(rightNodeId, rightChild);

                    if ("AAA".equals(nodeId)) {
                        origin = currentNode;
                    }
                }
            }

            System.out.println(nodes.values());

            return new Tree<>(origin);
        }
    }

    public static void main(String[] args) {
        var lines = FileReader.readFile("day8.txt");

        String path = lines.removeFirst();
        var tree = Tree.createTree(lines);

        var part1 = tree.move(path);
        System.out.println(part1);
    }
}
