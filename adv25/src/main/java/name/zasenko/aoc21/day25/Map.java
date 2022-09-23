package name.zasenko.aoc21.day25;

import java.util.Arrays;
import java.util.function.Predicate;

public class Map {
    final static char EMPTY_CELL = '.';
    final static char CUCUMBER_EAST = '>';
    final static char CUCUMBER_SOUTH = 'v';

    final int height, width;
    final char[][] map;

    public Map(String mapStr) {
        String[] lines = mapStr.split("\\n");

        height = lines.length;
        width = lines[0].length();

        map = new char[height][];
        for (int i = 0; i < height; i++) {
            map[i] = lines[i].toCharArray();
        }
    }

    private Map(int height, int width) {
        this.height = height;
        this.width = width;
        map = new char[height][];
        for (int i = 0; i < height; i++) {
            map[i] = new char[width];
            Arrays.fill(map[i], '.');
        }
    }

    public Map step() {
        Map stepEast = new Map(height, width);
        stepSymbol(stepEast, s -> s == CUCUMBER_EAST);

        copy(stepEast, s -> s == CUCUMBER_SOUTH);

        Map stepSouth = new Map(height, width);
        stepEast.copy(stepSouth, s -> s == CUCUMBER_EAST);
        stepEast.stepSymbol(stepSouth, s -> s == CUCUMBER_SOUTH);

        return stepSouth;
    }

    private void copy(Map next, Predicate<Character> predicate) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                char symbol = getCucumber(i, j);
                if (predicate.test(symbol)) {
                    next.setCucumber(i, j, symbol);
                }
            }
        }
    }

    private void stepSymbol(Map next, Predicate<Character> predicate) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                char symbol = getCucumber(i, j);
                if (predicate.test(symbol))
                    moveCucumber(next, i, j, symbol);
            }
        }
    }

    public char getCucumber(int i, int j) {
        return map[i][j];
    }

    public void setCucumber(int i, int j, char symbol) {
        map[i][j] = symbol;
    }

    private void moveCucumber(Map next, int i, int j, char symbol) {
        int nextI = i, nextJ = j;
        switch (symbol) {
            case CUCUMBER_SOUTH:
                nextI = (nextI + 1) % height;
                break;
            case CUCUMBER_EAST:
                nextJ = (nextJ + 1) % width;
                break;
            default:
                throw new RuntimeException("Unexpected symbol");
        }

        if (getCucumber(nextI, nextJ) != EMPTY_CELL) {
            nextI = i;
            nextJ = j;
        }
        next.setCucumber(nextI, nextJ, symbol);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < height; i++) {
            sb.append(map[i]);
            if (i != height - 1)
                sb.append("\n");
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object mapBObj) {
        if (!(mapBObj instanceof Map))
            return false;

        Map mapB = (Map) mapBObj;

        if (height != mapB.height || width != mapB.width)
            return false;

        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                if (getCucumber(i, j) != ((Map) mapBObj).getCucumber(i, j))
                    return false;

        return true;
    }
}
