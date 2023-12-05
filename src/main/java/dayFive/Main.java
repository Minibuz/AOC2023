package dayFive;

import utils.FileReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    private record Range(Long start, Long end) {}

    public static void main(String[] args) {
        var lines = FileReader.readFile("day5.txt");

        var seeds = Arrays.stream(
                lines.removeFirst().replace("seeds: ", "").split(" "))
                .map(Long::parseLong).toList();
        var lines1 = new ArrayList<>(lines);
        var lines2 = new ArrayList<>(lines);

        // Part 1
        var ranges = new ArrayList<Range>();
        for (var seed : seeds) {
            ranges.add(new Range(seed, seed));
        }
        ranges.addAll(getRangesLocation(lines1, ranges));
        var part1 = ranges.stream().mapToLong(Range::start).min();
        System.out.println(part1);

        // Part 2
        var ranges2 = new ArrayList<Range>();
        for (var i = 0; i < seeds.size(); i+=2) {
            ranges2.add(new Range(seeds.get(i), seeds.get(i)+seeds.get(i+1)));
        }
        ranges2.addAll(getRangesLocation(lines2, ranges2));
        var part2 = ranges2.stream().mapToLong(Range::start).min();
        System.out.println(part2);
    }

    private static List<Range> getRangesLocation(List<String> lines, List<Range> ranges) {
        var rangeUpdated = new ArrayList<Range>();
        while(!lines.isEmpty()) {
            var line = lines.removeFirst();
            if (line.contains("map")) {
                ranges.addAll(rangeUpdated);
                rangeUpdated.clear();
            } else if (!line.isEmpty()) {
                var mapping = Arrays.stream(line.split(" ")).map(Long::parseLong).toList();

                var from = new Range(mapping.get(1), mapping.get(1) + mapping.get(2));
                var updateValue = mapping.get(0) - mapping.get(1);

                for (var rangeId = 0; rangeId < ranges.size(); rangeId++) {
                    var current = ranges.get(rangeId);

                    if (from.start <= current.end && from.end > current.start) {
                        ranges.remove(rangeId--);

                        var rangeToMap =
                                new Range(Math.max(current.start, from.start), Math.min(current.end, from.end));
                        var rangeMapped =
                                new Range(rangeToMap.start + updateValue, rangeToMap.end + updateValue);

                        rangeUpdated.add(rangeMapped);

                        if (current.start < rangeToMap.start) {
                            ranges.add(new Range(current.start, rangeToMap.start - 1));
                        }
                        if (current.end > rangeToMap.end) {
                            ranges.add(new Range(rangeToMap.end, current.end - 1));
                        }
                    }
                }
            }
        }
        return rangeUpdated;
    }
}
