package name.zasenko.aoc21.day23;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day23 {

    private final static String[] foldedLines = new String[]{"  #D#C#B#A#", "  #D#B#A#C#" };

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in).useDelimiter("\\Z");

        String content = sc.next();

        System.out.println("Part 1: " + part1(content));
        System.out.println("Part 2: " + part2(content));
    }

    private static String part1(String content) {
        return Integer.toString(new SortAmphipods(new Burrow(content)).sort());
    }

    private static String part2(String content) {
        List<String> lines = new ArrayList<>(List.of(content.split("\n")));
        lines.addAll(lines.size() - 2, List.of(foldedLines));
        final String fullContent = String.join("\n", lines);
//        System.out.println(fullContent);

        return Integer.toString(new SortAmphipods(new Burrow(fullContent)).sort());
    }
}
