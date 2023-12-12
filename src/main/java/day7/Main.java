package day7;

import utils.FileReader;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class Main {

    static Map<String, Integer> power = new HashMap<>();
    static {
        power.put("2", 0);
        power.put("3", 1);
        power.put("4", 2);
        power.put("5", 3);
        power.put("6", 4);
        power.put("7", 5);
        power.put("8", 6);
        power.put("9", 7);
        power.put("T", 8);
        power.put("J", 9);
        power.put("Q", 10);
        power.put("K", 11);
        power.put("A", 12);
    }

    enum Type {
        FIVE_OF_A_KIND,
        FOUR_OF_A_KIND,
        FULL_HOUSE,
        THREE_OF_A_KIND,
        TWO_PAIR,
        ONE_PAIR,
        HIGH_CARD;

        public static Type getType(String hand) {
            var values = hand.toCharArray();
            Arrays.sort(values);

            if (values[0] == values[1] && values[0] == values[2] && values[0] == values[3] && values[0] == values[4]) {
                return FIVE_OF_A_KIND;
            }

            for (int i = 0; i < values.length - 3; i++) {
                if (values[i] == values[i + 1] && values[i] == values[i + 2] && values[i] == values[i + 3]) {
                    return FOUR_OF_A_KIND;
                }
            }

            if ((values[0] == values[1] && values[0] == values[2] && values[3] == values[4])
             || (values[2] == values[3] && values[2] == values[4] && values[0] == values[1])) {
                return FULL_HOUSE;
            }

            for (int i = 0; i < values.length - 2; i++) {
                if (values[i] == values[i + 1] && values[i] == values[i + 2]) {
                    return THREE_OF_A_KIND;
                }
            }

            var counter = 0;
            for (int i = 0; i < values.length - 1; i++) {
                if (values[i] == values[i + 1]) {
                    counter++;
                }
            }

            if (counter == 2) {
                return TWO_PAIR;
            } else if (counter == 1) {
                return ONE_PAIR;
            }

            return HIGH_CARD;
        }
    }

    record Hand(String hand, Type type, Long bid) {

        static Hand fromLine(String line) {
            var elements = line.split(" ");
            var hand = elements[0];
            var type = Type.getType(hand);
            var bid = Long.parseLong(elements[1]);
            return new Hand(hand, type, bid);
        }
    };

    static class HandsComparator implements Comparator<Hand> {

        @Override
        public int compare(Hand firstHand, Hand secondHand) {
            var firstType = firstHand.type();
            var secondType = secondHand.type();

            if (firstType.ordinal() < secondType.ordinal()) {
                return 1;
            } else if (firstType.ordinal() > secondType.ordinal()) {
                return -1;
            } else {
                for(int i = 0; i < 5; i++) {
                    var p1 = power.get(firstHand.hand.charAt(i) + "");
                    var p2 = power.get(secondHand.hand.charAt(i) + "");
                    if (p1 > p2) {
                        return 1;
                    }
                    if (p1 < p2) {
                        return -1;
                    }
                }
            }
            return 0;
        }

    }

    public static void main(String[] args) {
        // A, K, Q, J, T, 9, 8, 7, 6, 5, 4, 3, or 2
        // Five of a kind, Four of a kind, Full house, Three of a kind, Two pair, One pair, High card

        var lines = FileReader.readFile("day7.txt");

        var comparator = new HandsComparator();
        var hands = lines.stream().map(Hand::fromLine).sorted(comparator).toList();
        hands.forEach(System.out::println);

        var result = IntStream.range(1, hands.size()+1).mapToLong(id -> hands.get(id-1).bid * id).sum();
        System.out.println(result);
    }
}
