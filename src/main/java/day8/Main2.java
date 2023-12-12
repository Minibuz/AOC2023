package day8;

import utils.FileReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main2 {


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

    public static List<Node<String>> createStartingNodes(List<String> lines) {
        lines.removeFirst();

        List<Node<String>> origin = new ArrayList<>();
        Map<String, Node<String>> nodes = new HashMap<>();
        for (String s : lines) {
            var line = s.replace("(", "").replace(")", "");
            var elements = line.split(" = ");
            var nodeId = elements[0];
            var children = elements[1].split(", ");
            var leftNodeId = children[0];
            var rightNodeId = children[1];


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

            if (nodeId.endsWith("A")) {
                origin.add(currentNode);
            }
        }

        System.out.println(nodes.values());

        return origin;
    }

    public static void main(String[] args) {
        var lines = FileReader.readFile("day8.txt");

        String path = lines.removeFirst();
        var nodes = createStartingNodes(lines);

        var moves = path.toCharArray();

        List<Long> paths = new ArrayList<>();
        for (var current : nodes) {
            var movement = 0L;
            var moveId = 0;
            while (!current.data.endsWith("Z")) {
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
            paths.add(movement);
        }

        var leastCommonMultiplier = 1L;
        for (var element : paths) {
            leastCommonMultiplier = calculateLcm(leastCommonMultiplier, element);
        }

        System.out.println(leastCommonMultiplier);
    }

    private static long calculateLcm(long lcm, long each) {
        return lcm * each / gcd(lcm, each);
    }

    private static long gcd(long a, long b) {
        if (a < b) return gcd(b, a);
        if (a % b == 0) return b;
        else return gcd(b, a % b);
    }
}

