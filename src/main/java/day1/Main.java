package day1;

import utils.FileReader;

public class Main {

    public static void main(String[] args) {
        var lines = FileReader.readFile("day1.txt");

        var part1 = lines.stream()
                .map(l -> l.replaceAll("[a-zA-Z]", ""))
                .map(l -> {
                    var length = l.length();
                    return switch (length) {
                        case 0 -> "0";
                        case 1 -> l + l;
                        default -> l.charAt(0) + "" + l.charAt(length - 1);
                    };
                })
                .mapToInt(Integer::parseInt)
                .sum();
        System.out.println(part1);

//        Tentative Partie 2

//        var pattern = Pattern.compile("(one|two|three|four|five|six|seven|eight|nine|[1-9])");
//
//        List<Integer> values = new ArrayList<>();
//        for(var line : lines) {
//
//            var matcher = pattern.matcher(line);
//            String first = null;
//            String last = "";
//            while (matcher.find()) {
//                if (first == null) {
//                    first = Main.transform(matcher.group(0));
//                }
//                last = Main.transform(matcher.group(0));
//            }
//            var value = first + last;
//            values.add(Integer.parseInt(value));
//        }
//
//        System.out.println(values.stream().mapToInt(Integer::intValue).sum());

        var part2 = lines.stream()
                .map(l -> l.replaceAll("one", "one1one")
                        .replaceAll("two", "two2two")
                        .replaceAll("three", "three3three")
                        .replaceAll("four", "four4four")
                        .replaceAll("five", "five5five")
                        .replaceAll("six", "six6six")
                        .replaceAll("seven", "seven7seven")
                        .replaceAll("eight", "eight8eight")
                        .replaceAll("nine", "nine9nine"))
                .map(l -> l.replaceAll("[a-zA-Z]", ""))
                .map(l -> {
                    var length = l.length();
                    return switch (length) {
                        case 0 -> "0";
                        case 1 -> l + l;
                        default -> l.charAt(0) + "" + l.charAt(length - 1);
                    };
                })
                .peek(System.out::println)
                .mapToInt(Integer::parseInt)
                .sum();
        System.out.println(part2);
    }

//    private static String transform(String value) {
//        return switch (value) {
//            case "one" -> "1";
//            case "two" -> "2";
//            case "three" -> "3";
//            case "four" -> "4";
//            case "five" -> "5";
//            case "six" -> "6";
//            case "seven" -> "7";
//            case "eight" -> "8";
//            case "nine" -> "9";
//            default -> value;
//        };
//    }
}
