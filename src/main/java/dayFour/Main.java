package dayFour;

import utils.FileReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static final Pattern regex = Pattern.compile("(?:Card +(\\d*):)|(?:( *\\d* *))|(?:( *\\| *))");

    public static void main(String[] args) {
        var lines = FileReader.readFile("day4.txt");

        var part1 = 0;
        for (var line : lines) {
            Matcher matcher = regex.matcher(line);
            matcher.find();

            var numbersWin = new ArrayList<Integer>();
            var numbersGet = new ArrayList<Integer>();

            boolean swap = false;
            while (matcher.find()) {
                var element = matcher.group().trim();
                if (element.isEmpty()) {
                    swap = !swap;
                    continue;
                }

                if (swap) {
                    numbersGet.add(Integer.parseInt(element));
                } else {
                    numbersWin.add(Integer.parseInt(element));
                }
            }

            var value = numbersGet.stream()
                    .filter(numbersWin::contains)
                    .mapToInt(e -> 1).sum(); // Count to int
            if ( value > 0 ) {
                part1 += (int) Math.pow(2, value-1);
            }
        }
        System.out.println(part1);


        var scratchcard = new HashMap<Integer, Long>();
        for (int i = 0; i < lines.size(); i++) {
            var line = lines.get(i);

            scratchcard.merge(i, 1L, Long::sum);

            Matcher matcher = regex.matcher(line);
            matcher.find();

            var numbersWin = new ArrayList<Integer>();
            var numbersGet = new ArrayList<Integer>();

            boolean swap = false;
            while (matcher.find()) {
                var element = matcher.group().trim();
                if (element.isEmpty()) {
                    swap = !swap;
                    continue;
                }

                if (swap) {
                    numbersGet.add(Integer.parseInt(element));
                } else {
                    numbersWin.add(Integer.parseInt(element));
                }
            }

            var value = numbersGet.stream()
                    .filter(numbersWin::contains)
                    .mapToInt(e -> 1).sum(); // Count to int

            var max = i;
            var add = 1;
            while (max < lines.size() && add <= value) {
                scratchcard.merge(i+add, scratchcard.get(i), Long::sum);

                max++;
                add++;
            }
        }

        var part2 = scratchcard.values().stream().mapToLong(Long::longValue).sum();
        System.out.println(part2);
    }
}
