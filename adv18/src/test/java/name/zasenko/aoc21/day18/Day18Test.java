package name.zasenko.aoc21.day18;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Day18Test {

    @Test
    void explodeTest() {
        assertEquals(null, Day18.explode(Day18.parseLine("[0,9]")));

        assertEquals(parse("[[[[0,9],2],3],4]"), Day18.explode(parse("[[[[[9,8],1],2],3],4]")));

        assertEquals(parse("[7,[6,[5,[7,0]]]]"), Day18.explode(parse("[7,[6,[5,[4,[3,2]]]]]")));

        assertEquals(parse("[[6,[5,[7,0]]],3]"), Day18.explode(parse("[[6,[5,[4,[3,2]]]],1]")));
    }

    @Test
    void splitTest() {
        assertEquals(null, Day18.split(parse("[1, 5]")));

        assertEquals(parse("[1, [5, 6]]"), Day18.split(parse("[1, 11]")));
    }

    @Test
    void reduceTest() {
        assertEquals(parse("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]"), Day18.reduce(parse("[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]")));
    }

    @Test
    void addTest() {
        assertEquals(parse("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]"), Day18.add(parse("[[[[4,3],4],4],[7,[[8,4],9]]]"), parse("[1,1]")));

        assertEquals(parse("[[[[5,0],[7,4]],[5,5]],[6,6]]"), Day18.add(Day18.add(Day18.add(Day18.add(Day18.add(parse("[1,1]"), parse("[2,2]")), parse("[3,3]")), parse("[4, 4]")), parse("[5, 5]")), parse("[6, 6]")));
    }

    @Test
    void magnitudeTest() {
        assertEquals(29, Day18.magnitude(parse("[9, 1]")));

        assertEquals(129, Day18.magnitude(parse("[[9,1],[1,9]]")));

        assertEquals(1384, Day18.magnitude(parse("[[[0,7],4],[[7,8],[6,0]]],[8,1]]")));
    }

    private List<Integer> parse(String s) {
        return Day18.parseLine(s);
    }

}
