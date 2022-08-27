package name.zasenko.aoc21.day14;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PolymerTemplateTest {
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
    void iterateTest() {
        CompressedData polymer = template.compress("ABA");

        assertEquals("ABBAA", template.expand(template.iterate(polymer)));
    }

    @Test
    void iterationIterator() {
        CompressedData polymer = template.compress("ABA");

        PolymerTransformationIterator it = template.iterationIterator(polymer.iterator());
        it = template.iterationIterator(it);

        StringBuilder sb = new StringBuilder();
        while (it.hasNext()) {
            sb.append(template.toChar(it.next()));
        }
        /*
           ABA
          ABBAA
         ABBABAA
        ABBAABBAA
         */
        assertEquals(template.expand(template.iterate(template.iterate(polymer))), sb.toString());
    }

    @Test
    void leapIteration() {
        CompressedData polymer = template.compress("AB");

        PolymerLeapTemplate leapTemplate = template.leapTemplate(3);
        StringBuilder sb = new StringBuilder();
        var it = leapTemplate.iterationIterator(polymer.iterator());
        while (it.hasNext()) {
            sb.append(template.toChar(it.next()));
        }

        String expected = template.expand(template.iterate(template.iterate(template.iterate(polymer))));
        assertEquals(expected, sb.toString());
    }
}
