package day2;

import utils.FileReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        var lines = FileReader.readFile("day2.txt");

        List<String> validGames = new ArrayList<>();
        for (var line : lines) {
            var gameAndValues = line.split(":");
            var gameId = gameAndValues[0].replaceAll("Game ", "");
            var valuesPerRoll = gameAndValues[1].split(";");
            var map = new HashMap<String, Integer>();
            for (var roll : valuesPerRoll) {
                var valueKeyComplete = roll.split(",");

                for (var colorAndValue : valueKeyComplete) {
                    var valueKey = colorAndValue.trim().split(" ");
                    var value = Integer.parseInt(valueKey[0]);
                    var key = valueKey[1];

                    var alreadyIn = map.get(key);
                    if (alreadyIn == null || alreadyIn < value) {
                        map.put(key, value);
                    }
                }
            }

            var red = map.getOrDefault("red", 0); //12
            var green = map.getOrDefault("green", 0); //13
            var blue = map.getOrDefault("blue", 0); //14
            if (red <= 12 && green <= 13 && blue <= 14) {
                validGames.add(gameId);
            }
        }

        var part1 = validGames.stream().mapToInt(Integer::parseInt).sum();
        System.out.println(part1);

        List<Integer> powers = new ArrayList<>();
        for (var line : lines) {
            var gameAndValues = line.split(":");
            var gameId = gameAndValues[0].replaceAll("Game ", "");
            var valuesPerRoll = gameAndValues[1].split(";");
            var map = new HashMap<String, Integer>();
            for (var roll : valuesPerRoll) {
                var valueKeyComplete = roll.split(",");

                for (var colorAndValue : valueKeyComplete) {
                    var valueKey = colorAndValue.trim().split(" ");
                    var value = Integer.parseInt(valueKey[0]);
                    var key = valueKey[1];

                    var alreadyIn = map.get(key);
                    if (alreadyIn == null || alreadyIn < value) {
                        map.put(key, value);
                    }
                }
            }

            var red = map.getOrDefault("red", 0);
            var green = map.getOrDefault("green", 0);
            var blue = map.getOrDefault("blue", 0);
            var power = red * green * blue;
            powers.add(power);
        }

        var part2 = powers.stream().mapToInt(Integer::valueOf).sum();
        System.out.println(part2);
    }
}
