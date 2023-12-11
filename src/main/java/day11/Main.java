package day11;

import utils.FileReader;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Main {

    record Coordinate(int line, int column) {}

    public static void main(String[] args) {
        var lines = FileReader.readFile("day11.txt");

        // DESSINER LA GALAXIE
        var map = new ArrayList<ArrayList<String>>();
        for (var line : lines) {
            var tabLine = new ArrayList<String>();
            for (var element : line.toCharArray()) {
                tabLine.add(element + "");
            }
            map.add(tabLine);
        }
        // AGRANDIR LA GALAXIE 1
        var galaxySizeHorizontal = map.size();
        for (int i = 0; i < galaxySizeHorizontal; i++) {
            var line = map.get(i);
            if (!line.contains("#")) {
                map.add(i, line);
                i++;
                galaxySizeHorizontal = map.size();
            }
        }
        // AGRANDIR LA GALAXIE 2
        var galaxySizeVertical = map.get(0).size();
        for (int i = 0; i < galaxySizeVertical; i++) {
            var addColumn = true;
            for (int j = 0; j < galaxySizeHorizontal; j++ ) {
                var element = map.get(j).get(i);
                if ("#".equals(element)) {
                    addColumn = false;
                    break;
                }
            }

            if (addColumn) {
                for (int j = 0; j < galaxySizeHorizontal; j++ ) {
                    map.get(j).add(i, ".");
                }
                i++;
                galaxySizeVertical = map.get(0).size();
            }
        }

        var galaxies = new ArrayList<Coordinate>();
        for (var i = 0; i < map.size(); i++) {
            var line = map.get(i);
            for (var j = 0; j < line.size(); j++) {
                var element = line.get(j);
                if ("#".equals(element)) {
                    galaxies.add(new Coordinate(i, j));
                }
            }
        }

        var matrix = adjencyMatrix(galaxies);

        long sum = 0L;
        for (int i = 0; i < galaxies.size(); i++) {
            for (int j = 0; j < i; j++) {
                sum += matrix[i][j];
            }
        }

        System.out.println(sum);
    }

    private static int[][] adjencyMatrix(List<Coordinate> points) {
        int[][] matrix = new int[points.size()][points.size()];

        var current = 0;
        for (var origin : points) {
            matrix[current][current] = 0;
            for (var j = current + 1; j < points.size(); j++) {
                var end = points.get(j);

                var lineDiff = Math.abs(origin.line - end.line);
                var columnDiff = Math.abs(origin.column - end.column);

                var path = lineDiff + columnDiff;
                matrix[current][j] = path;
                matrix[j][current] = path;
            }
            current++;
        }

        return matrix;
    }
}
