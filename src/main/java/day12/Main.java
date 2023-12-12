package day12;

import utils.FileReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    record Spring(String line, List<Integer> conditions) {

        public long possibleCombinaison() {
            var amount = 0L;

            var currentIndex = 0;
            List<String> possibleCombinaison = new ArrayList<>(List.of(line));
            List<String> tempCombinaison = new ArrayList<>();
            while (currentIndex < line.length()) {
                for (var combinaison : possibleCombinaison) {
                    var letters = combinaison.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
                    if ('?' == letters.get(currentIndex)) {
                        letters.set(currentIndex, '.');
                        StringBuilder sb = new StringBuilder();
                        // Appends characters one by one
                        for (Character ch : letters) {
                            sb.append(ch);
                        }
                        // convert in string
                        tempCombinaison.add(sb.toString());

                        letters.set(currentIndex, '#');
                        sb = new StringBuilder();
                        // Appends characters one by one
                        for (Character ch : letters) {
                            sb.append(ch);
                        }
                        // convert in string
                        tempCombinaison.add(sb.toString());
                    } else {
                        tempCombinaison.add(combinaison);
                    }
                }
                if (!line.substring(currentIndex).contains("?")) {
                    currentIndex = line.length();
                }
                possibleCombinaison.clear();
                possibleCombinaison.addAll(tempCombinaison);
                tempCombinaison.clear();
                currentIndex++;
            }

            possibleCombinaison = possibleCombinaison.stream().filter(str -> !str.contains("?")).toList();

            for (var combinaison : possibleCombinaison) {
                var parts = Arrays.stream(combinaison.replaceAll("\\.+", " ").split(" "))
                        .collect(Collectors.toList());
                parts.removeAll(List.of(""));

                if (parts.size() == conditions.size()) {
                    var toAdd = true;
                    for (var i = 0; i < conditions.size(); i++) {
                        if (parts.get(i).length() != conditions.get(i)) {
                            toAdd = false;
                            break;
                        }
                    }

                    if (toAdd) {
                        amount++;
                    }
                }
            }

            return amount;
        }
    }

    public static void main(String[] args) {
        var lines = FileReader.readFile("day12.txt");

        var springs = lines.stream().map(line -> {
            var parts = line.split(" ");
            return new Spring(
                    parts[0],
                    Arrays.stream(parts[1].split(",")).mapToInt(Integer::parseInt).boxed().toList());
        }).toList();

        var result = springs.stream().mapToLong(Spring::possibleCombinaison).sum();
        System.out.println(result);
    }
}
