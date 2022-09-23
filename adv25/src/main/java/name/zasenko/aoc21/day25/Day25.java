package name.zasenko.aoc21.day25;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Day25 {
    public static void main(String[] args) throws IOException {
        String mapStr = new String(System.in.readAllBytes(), StandardCharsets.UTF_8);
        Map map = new Map(mapStr);

        System.out.println("Part1: " + part1(map));
    }

    private static int part1(Map map) {
        int i = 1;
        Map step = map.step();
        while (!step.equals(map)) {
            map = step;
            step = map.step();
            i++;
        }
        return i;
    }
}
