package day10;

import utils.FileReader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Main {

    enum Direction {
        NORTH_SOUTH {
            @Override
            List<Tile> traverse(Tile current, List<List<Tile>> map) {
                var possible = new ArrayList<Tile>();

                if (current.alreadyVisited) {
                    return List.of();
                }

                current.alreadyVisited = true;

                var col = current.column;
                var line = current.line;

                // UP / NORTH
                if (line - 1 >= 0) {
                    var aboveTile = map.get(line-1).get(col);
                    if (aboveTile.direction == NORTH_SOUTH || aboveTile.direction == SOUTH_EAST || aboveTile.direction == SOUTH_WEST) {
                        possible.add(aboveTile);
                    }
                }
                // DOWN / SOUTH
                if (line + 1 < map.size()) {
                    var aboveTile = map.get(line+1).get(col);
                    if (aboveTile.direction == NORTH_SOUTH || aboveTile.direction == NORTH_EAST || aboveTile.direction == NORTH_WEST) {
                        possible.add(aboveTile);
                    }
                }

                return possible;
            }
        },
        EAST_WEST {
            @Override
            List<Tile> traverse(Tile current, List<List<Tile>> map) {
                var possible = new ArrayList<Tile>();

                if (current.alreadyVisited) {
                    return List.of();
                }

                current.alreadyVisited = true;

                var col = current.column;
                var line = current.line;

                // LEFT / WEST
                if (col - 1 >= 0) {
                    var aboveTile = map.get(line).get(col - 1);
                    if (aboveTile.direction == EAST_WEST || aboveTile.direction == NORTH_EAST || aboveTile.direction == SOUTH_EAST) {
                        possible.add(aboveTile);
                    }
                }
                // RIGHT / EAST
                if (col + 1 < map.get(line).size()) {
                    var aboveTile = map.get(line).get(col + 1);
                    if (aboveTile.direction == EAST_WEST || aboveTile.direction == NORTH_WEST || aboveTile.direction == SOUTH_WEST) {
                        possible.add(aboveTile);
                    }
                }

                return possible;
            }
        },
        NORTH_EAST {
            @Override
            List<Tile> traverse(Tile current, List<List<Tile>> map) {
                var possible = new ArrayList<Tile>();

                if (current.alreadyVisited) {
                    return List.of();
                }

                current.alreadyVisited = true;

                var col = current.column;
                var line = current.line;

                // UP / NORTH
                if (line - 1 >= 0) {
                    var aboveTile = map.get(line-1).get(col);
                    if (aboveTile.direction == NORTH_SOUTH || aboveTile.direction == SOUTH_EAST || aboveTile.direction == SOUTH_WEST) {
                        possible.add(aboveTile);
                    }
                }
                // RIGHT / EAST
                if (col + 1 < map.get(line).size()) {
                    var aboveTile = map.get(line).get(col + 1);
                    if (aboveTile.direction == EAST_WEST || aboveTile.direction == NORTH_WEST || aboveTile.direction == SOUTH_WEST) {
                        possible.add(aboveTile);
                    }
                }

                return possible;
            }
        },
        NORTH_WEST {
            @Override
            List<Tile> traverse(Tile current, List<List<Tile>> map) {
                var possible = new ArrayList<Tile>();

                if (current.alreadyVisited) {
                    return List.of();
                }

                current.alreadyVisited = true;

                var col = current.column;
                var line = current.line;

                // UP / NORTH
                if (line - 1 >= 0) {
                    var aboveTile = map.get(line-1).get(col);
                    if (aboveTile.direction == NORTH_SOUTH || aboveTile.direction == SOUTH_EAST || aboveTile.direction == SOUTH_WEST) {
                        possible.add(aboveTile);
                    }
                }
                // LEFT / WEST
                if (col - 1 >= 0) {
                    var aboveTile = map.get(line).get(col - 1);
                    if (aboveTile.direction == EAST_WEST || aboveTile.direction == NORTH_EAST || aboveTile.direction == SOUTH_EAST) {
                        possible.add(aboveTile);
                    }
                }

                return possible;
            }
        },
        SOUTH_WEST {
            @Override
            List<Tile> traverse(Tile current, List<List<Tile>> map) {
                var possible = new ArrayList<Tile>();

                if (current.alreadyVisited) {
                    return List.of();
                }

                current.alreadyVisited = true;

                var col = current.column;
                var line = current.line;

                // DOWN / SOUTH
                if (line + 1 < map.size()) {
                    var aboveTile = map.get(line+1).get(col);
                    if (aboveTile.direction == NORTH_SOUTH || aboveTile.direction == NORTH_EAST || aboveTile.direction == NORTH_WEST) {
                        possible.add(aboveTile);
                    }
                }
                // LEFT / WEST
                if (col - 1 >= 0) {
                    var aboveTile = map.get(line).get(col - 1);
                    if (aboveTile.direction == EAST_WEST || aboveTile.direction == NORTH_EAST || aboveTile.direction == SOUTH_EAST) {
                        possible.add(aboveTile);
                    }
                }

                return possible;
            }
        },
        SOUTH_EAST {
            @Override
            List<Tile> traverse(Tile current, List<List<Tile>> map) {
                var possible = new ArrayList<Tile>();

                if (current.alreadyVisited) {
                    return List.of();
                }

                current.alreadyVisited = true;

                var col = current.column;
                var line = current.line;

                // DOWN / SOUTH
                if (line + 1 < map.size()) {
                    var aboveTile = map.get(line+1).get(col);
                    if (aboveTile.direction == NORTH_SOUTH || aboveTile.direction == NORTH_EAST || aboveTile.direction == NORTH_WEST) {
                        possible.add(aboveTile);
                    }
                }
                // RIGHT / EAST
                if (col + 1 < map.get(line).size()) {
                    var aboveTile = map.get(line).get(col + 1);
                    if (aboveTile.direction == EAST_WEST || aboveTile.direction == NORTH_WEST || aboveTile.direction == SOUTH_WEST) {
                        possible.add(aboveTile);
                    }
                }

                return possible;
            }
        },
        GROUND {
            @Override
            List<Tile> traverse(Tile current, List<List<Tile>> map) {
                throw new RuntimeException("WHY ARE WE HERE ! HOW !");
            }
        },
        START {
            @Override
            List<Tile> traverse(Tile current, List<List<Tile>> map) {
                var possible = new ArrayList<Tile>();
                current.alreadyVisited = true;

                var col = current.column;
                var line = current.line;

                // UP
                if (line - 1 >= 0) {
                    var aboveTile = map.get(line-1).get(col);
                    if (aboveTile.direction == NORTH_SOUTH || aboveTile.direction == SOUTH_EAST || aboveTile.direction == SOUTH_WEST) {
                        possible.add(aboveTile);
                    }
                }
                // DOWN
                if (line + 1 < map.size()) {
                    var aboveTile = map.get(line+1).get(col);
                    if (aboveTile.direction == NORTH_SOUTH || aboveTile.direction == NORTH_EAST || aboveTile.direction == NORTH_WEST) {
                        possible.add(aboveTile);
                    }
                }
                // LEFT
                if (col - 1 >= 0) {
                    var aboveTile = map.get(line).get(col - 1);
                    if (aboveTile.direction == EAST_WEST || aboveTile.direction == NORTH_EAST || aboveTile.direction == SOUTH_EAST) {
                        possible.add(aboveTile);
                    }
                }
                // RIGHT
                if (col + 1 < map.get(line).size()) {
                    var aboveTile = map.get(line).get(col + 1);
                    if (aboveTile.direction == EAST_WEST || aboveTile.direction == NORTH_WEST || aboveTile.direction == SOUTH_WEST) {
                        possible.add(aboveTile);
                    }
                }

                return possible;
            }
        };

        abstract List<Tile> traverse(Tile current, List<List<Tile>> map);

        public static Direction getDirection(String string) {
            return switch (string) {
                case "|" -> NORTH_SOUTH;
                case "-" -> EAST_WEST;
                case "L" -> NORTH_EAST;
                case "J" -> NORTH_WEST;
                case "7" -> SOUTH_WEST;
                case "F" -> SOUTH_EAST;
                case "." -> GROUND;
                case "S" -> START;
                default -> throw new RuntimeException();
            };
        }
    }

    static class Tile {
        final int line;
        final int column;
        final Direction direction;
        boolean alreadyVisited = false;

        public Tile(int line, int column, Direction direction) {
            this.line = line;
            this.column = column;
            this.direction = direction;
        }

        @Override
        public String toString() {
            return "Tile{" +
                    "line=" + line +
                    ", column=" + column +
                    ", direction=" + direction +
                    ", alreadyVisited=" + alreadyVisited +
                    '}';
        }
    }

    public static void main(String[] args) {
        var lines = FileReader.readFile("day10.txt");

        var tab = new ArrayList<List<Tile>>();
        IntStream.range(0, lines.size()).forEach(lineId -> {
            String currentLine = lines.removeFirst();
            AtomicInteger value = new AtomicInteger(0);
            List<Tile> directions =
                    currentLine.chars()
                            .mapToObj(element -> Direction.getDirection((char) (element) + ""))
                            .map(direction -> new Tile(lineId, value.getAndAdd(1), direction))
                            .toList();
            tab.add(directions);
        });

        System.out.println(tab.size() * tab.get(0).size());

        Tile start = findStart(tab);

        var value = goThroughPipes(start, tab);
        System.out.println(value / 2);
    }

    private static Tile findStart(List<List<Tile>> map) {
        for (var line : map) {
            for (var element : line) {
                if (element.direction == Direction.START) {
                    return element;
                }
            }
        }
        return null;
    }

    private static long goThroughPipes(Tile start, List<List<Tile>> map) {

        var tilesToVisitV2 = new ArrayList<>(List.of(start));
        while (!tilesToVisitV2.isEmpty()) {
            var current = tilesToVisitV2.removeFirst();

            var nextPossibleTiles = current.direction.traverse(current, map);
            nextPossibleTiles.reversed().stream().filter(t -> !t.alreadyVisited).forEach(tilesToVisitV2::addFirst);
        }

        var visitedTiles = map.stream().flatMap(lineTiles -> lineTiles.stream().filter(tile -> tile.alreadyVisited)).toList();
        System.out.println(visitedTiles.size());

        return 0L;
    }
}
