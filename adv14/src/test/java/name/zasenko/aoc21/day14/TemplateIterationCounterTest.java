package name.zasenko.aoc21.day14;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TemplateIterationCounterTest {
    PolymerTemplate template;

    @BeforeEach
    void init() {
        HashMap<String, Character> templates = new HashMap<>();
        PolymerTemplate.parseLine("AA -> B", templates);
        PolymerTemplate.parseLine("AB -> B", templates);
        PolymerTemplate.parseLine("BB -> A", templates);
        PolymerTemplate.parseLine("BA -> A", templates);

        template = new PolymerTemplate(templates);
    }

    @Test
    void basicTest() {
        CompressedData polymer = template.compress("AB");

        TemplateIterationCounter c = new TemplateIterationCounter(polymer.iterator(), template);

        assertEquals(0, c.score());
    }

    @Test
    void oneIterationTest() {
        CompressedData polymer = template.compress("AB");

        TemplateIterationCounter c = new TemplateIterationCounter(polymer.iterator(), template);
        c.iterate();

        assertEquals(1, c.score());
    }

    @Test
    void threeIterations() {
        CompressedData polymer = template.compress("AB");

        TemplateIterationCounter c = new TemplateIterationCounter(polymer.iterator(), template);
        c.iterate();
        c.iterate();
        c.iterate();

        CompressedData p = template.iterate(polymer);
        p = template.iterate(p);
        p = template.iterate(p);
        long expected = PolymerTemplate.polymerScore(p.iterator());
        System.out.println(template.expand(p));
        assertEquals(expected, c.score());
    }
}