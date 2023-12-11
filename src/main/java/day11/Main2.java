package day11;

import utils.FileReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class Main2 {

    record Coordinate(long line, long column) {}

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
        var augmentsHorizontal = new TreeMap<Integer, Long>(); // Line id to augment
        augmentsHorizontal.put(0, 0L);
        var galaxySizeHorizontal = map.size();
        for (int i = 0; i < galaxySizeHorizontal; i++) {
            var line = map.get(i);
            if (!line.contains("#")) {
                var entry = augmentsHorizontal.floorEntry(i);
                augmentsHorizontal.put(i, entry.getValue() + 1000000L - 1L);
            }
        }
        // AGRANDIR LA GALAXIE 2
        var galaxySizeVertical = map.get(0).size();
        var augmentsVertical = new TreeMap<Integer, Long>(); // Line id to augment
        augmentsVertical.put(0, 0L);
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
                var entry = augmentsVertical.floorEntry(i);
                augmentsVertical.put(i, entry.getValue() + 1000000L - 1L);
            }
        }

        var galaxies = new ArrayList<Coordinate>();
        for (var i = 0; i < map.size(); i++) {
            var line = map.get(i);
            for (var j = 0; j < line.size(); j++) {
                var element = line.get(j);
                if ("#".equals(element)) {
                    // i = ligne : j = colomne
                    var horizontal = augmentsHorizontal.floorEntry(i).getValue();
                    var vertical = augmentsVertical.floorEntry(j).getValue();
                    galaxies.add(new Coordinate(i + horizontal, j + vertical));
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

    private static long[][] adjencyMatrix(List<Coordinate> points) {
        long[][] matrix = new long[points.size()][points.size()];

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
