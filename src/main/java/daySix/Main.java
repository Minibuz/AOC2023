package daySix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Why read the file and parse it when it's 2 lines
        // var lines = FileReader.readFile("day6.txt");

        var times = new ArrayList<>(List.of(/* Entry */));
        var distances =  new ArrayList<>(List.of(/* Entry */));

        var bestDistancePerTimes = new HashMap<Integer, Integer>();
        for (var i = 0; i < times.size(); i++) {
            bestDistancePerTimes.put(times.get(i), distances.get(i));
        }

        var solutionsPerTimes = new HashMap<Integer, List<Integer>>();
        for (var time : times) {
            var results = new ArrayList<Integer>();
            for (int timeToWait = 0; timeToWait < time; timeToWait++) {
                var timeToRun = time - timeToWait;
                var result = timeToRun * timeToWait;
                results.add(result);
            }
            solutionsPerTimes.put(time, results);
        }

        var part1 = solutionsPerTimes.entrySet().stream()
                .map(entry -> {
                    var key = entry.getKey();
                    var distanceToBeat = bestDistancePerTimes.get(key);
                    return entry.getValue().stream().filter(distance -> distance >= distanceToBeat).mapToInt(i -> 1).sum();
                }).reduce(1, (a,b) -> a * b);
        System.out.println(part1);

        // PART 2
        var time = /* Entry */;
        var distance =  /* Entry */;

        var part2 = 0;
        for (int timeToWait = 0; timeToWait < time; timeToWait++) {
            var timeToRun = time - timeToWait;
            var result = timeToRun * timeToWait;
            if (result > distance) {
                part2++;
            }
        }
        System.out.println(part2);
    }
}
