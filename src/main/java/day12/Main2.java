package day12;

import utils.FileReader;

import java.util.*;
import java.util.stream.Stream;

public class Main2 {

    record Key(int index, int indexCondition, int cursor) {}
    record Spring(String line, List<Integer> conditions) {}

    private static long getCombinaison(Map<Key, Long> memoisation, String map, List<Integer> condition, int index, int indexCondition, int curseur) {
        var key = new Key(index, indexCondition, curseur);

        // MEMOISATION
        var memoisationValue = memoisation.get(key);
        if (memoisationValue != null) {
            return memoisationValue;
        }

        // FINI
        if (index == map.length()) {
            // on a vu toutes les conditions et on est pas dans une nouvelle condition
            return (indexCondition == condition.size() && curseur == 0)
                    // il reste une condition et on regarde si elle est vrai
                    || (indexCondition == condition.size() - 1 && condition.get(indexCondition) == curseur)
                    // si un des deux cas, le cas est BON ( 1 ) sinon MAUVAIS ( 0 )
                    ? 1 : 0;
        }

        // PROCESS
        var total = 0L;
        var letter = map.charAt(index); // ? / # / .
        // On est au debut d'une condition (debut du mot possible) et on as soit un "." soit un "?"
        if (curseur == 0 && (letter == '.' || letter == '?')) {
            total += getCombinaison(memoisation, map, condition, index + 1, indexCondition, 0);
        }
        // On est dans une condition et on as soit un "." soit un "?"
        // On valide que toutes les conditions n'ont pas deja ete vu, et la condition actuelle
        else if (curseur > 0 && (letter == '.' || letter == '?') && indexCondition < condition.size() && condition.get(indexCondition) == curseur) {
            total += getCombinaison(memoisation, map, condition, index + 1, indexCondition + 1, 0);
        }

        // On rentre dans une condition si on as soit "#" soit "?"
        if (letter == '#' || letter == '?') {
            total += getCombinaison(memoisation, map, condition, index + 1, indexCondition, curseur + 1);
        }

        memoisation.put(key, total);
        return total;
    }

    public static void main(String[] args) {
        var lines = FileReader.readFile("day12.txt");

        var springs = lines.stream().map(line -> {
            var parts = line.split(" ");
            var p1 = parts[0] + "?" + parts[0] + "?" + parts[0] + "?" + parts[0] + "?" + parts[0];
            var p2 = Arrays.stream(parts[1].split(",")).mapToInt(Integer::parseInt).boxed().toList();
            var p2Possible = Stream.of(p2, p2, p2, p2, p2).flatMap(Collection::stream).toList();
            return new Spring(p1, p2Possible);
        }).toList();

        var result = springs.stream().mapToLong(element -> getCombinaison(new HashMap<>(), element.line, element.conditions, 0, 0, 0)).sum();
        System.out.println(result);
    }
}
