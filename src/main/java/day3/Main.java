package day3;

import utils.FileReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Main {

    private final static Pattern patternPart1 = Pattern.compile("\\d+");
    private final static Pattern patternPart2 = Pattern.compile("\\*");

    public static void main(String[] args) {
        var lines = FileReader.readFile("day3.txt");

        String previousLine = null;
        String currentLine;
        String nextLine = null;

        List<Integer> validNumbers = new ArrayList<>();
        List<Integer> gearRatios = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            var line = lines.get(i);
            if (i > 0) {
                previousLine = lines.get(i - 1);
            }
            if (i < lines.size() - 1) {
                nextLine = lines.get(i + 1);
            }
            currentLine = line;

            Line parsedLine = new Line(currentLine, previousLine, nextLine);
            validNumbers.addAll(parsedLine.validNumbers);
            gearRatios.add(parsedLine.sumOfGearRatio);
        }

        int part1 = validNumbers.stream().mapToInt(Integer::intValue).sum();
        System.out.println(part1);

        int part2 = gearRatios.stream().mapToInt(Integer::intValue).sum();
        System.out.println(part2);

    }

    private static class Line {

        private final List<Integer> validNumbers = new ArrayList<>();
        private final int lineLength;
        private int sumOfGearRatio = 0;

        public Line(String currentLine, String previousLine, String nextLine) {
            lineLength = currentLine.length();

            Matcher matcher = patternPart1.matcher(currentLine);
            while (matcher.find()) {
                if (isValidPartNumber(matcher, currentLine, previousLine, nextLine))
                    validNumbers.add(Integer.parseInt(matcher.group()));
            }

            Matcher matcherPart2 = patternPart2.matcher(currentLine);
            while (matcherPart2.find()) {
                sumOfGearRatio += gearRatio(matcherPart2, currentLine, previousLine, nextLine);
            }
        }

        private boolean isSymbol(char c) {
            return !Character.isDigit(c) && c != '.';
        }

        private int gearRatio(Matcher matcher, String currentLine, String previousLine, String nextLine) {
            int startIndex = matcher.start();

            List<Integer> gearNumbers = new ArrayList<>();

            Stream.of(previousLine, currentLine, nextLine).filter(Objects::nonNull).forEach(line -> {
                Matcher matcherPart1 = patternPart1.matcher(line);

                while (matcherPart1.find()) {
                    int msindex = matcherPart1.start();
                    int meindex = matcherPart1.end();

                    if (startIndex >= msindex && startIndex <= meindex) {
                        gearNumbers.add(Integer.parseInt(matcherPart1.group()));
                    }

                    else if (startIndex == meindex || startIndex + 1 == msindex) {
                        gearNumbers.add(Integer.parseInt(matcherPart1.group()));
                    }
                }
            });

            return gearNumbers.size() != 2 ? 0 : gearNumbers.stream().reduce(1, Math::multiplyExact);
        }

        private boolean isValidPartNumber(Matcher matcher, String currentLine, String previousLine, String nextLine) {
            var startIndex = matcher.start();
            var endIndex = startIndex + matcher.group().length();

            if (startIndex > 0) {
                var character = currentLine.charAt(startIndex - 1);
                if (isSymbol(character)) {
                    return true;
                }
            }
            if (endIndex < currentLine.length()) {
                var character = currentLine.charAt(endIndex);
                if (isSymbol(character)) {
                    return true;
                }
            }

            int begin = Math.max(0, startIndex - 1);
            int end = Math.min(endIndex + 1, lineLength);
            for (int i = begin; i < end; i++) {
                if ((previousLine != null && isSymbol(previousLine.charAt(i)))
                        || (nextLine != null && isSymbol(nextLine.charAt(i)))) {
                    return true;
                }
            }
            return false;
        }

    }
}
