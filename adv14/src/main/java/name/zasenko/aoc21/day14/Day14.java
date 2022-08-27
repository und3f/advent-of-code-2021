package name.zasenko.aoc21.day14;

import java.util.HashMap;
import java.util.Scanner;

public class Day14 {
    private final CompressedData polymer;
    private final PolymerTemplate template;

    public Day14(CompressedData polymer, PolymerTemplate template) {
        this.polymer = polymer;
        this.template = template;
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String polymerString = s.nextLine();

        s.nextLine();
        HashMap<String, Character> templates = new HashMap<>();
        while (s.hasNextLine()) {
            PolymerTemplate.parseLine(s.nextLine(), templates);
        }

        PolymerTemplate template = new PolymerTemplate(templates);
        CompressedData data = template.compress(polymerString);
        Day14 solution = new Day14(data, template);
        System.out.println("Part1: " + solution.iteration(10));
        System.out.println("Part2: " + solution.iteration(40));
    }

    private long iteration(int n) {
        TemplateIterationCounter c = new TemplateIterationCounter(this.polymer.iterator(), template);

        for (int i = 0; i < n; i++)
            c.iterate();

        return c.score();
    }


}
