package name.zasenko.aoc21.day19;

import java.util.ArrayList;
import java.util.List;

public class Day19 {
    public static void main(String[] args) {
        Map map = new Map(ScannerFactory.fromStream(System.in));
        System.out.println("Part1: " + part1(map));
        System.out.println("Part2: " + part2(map));
    }

    static int part1(Map map) {
        return map.getCoords().size();
    }

    static int part2(Map map) {
        List<Coord> offsets = map.getScannersOffset();
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < offsets.size() - 1; i++) {
            for (int j = i + 1; j < offsets.size(); j++) {
                int distance = offsets.get(i).distanceTo(offsets.get(j));
                if (distance > max)
                    max = distance;
            }
        }

        return max;
    }
}
