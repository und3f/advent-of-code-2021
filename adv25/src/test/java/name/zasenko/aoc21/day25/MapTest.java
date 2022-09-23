package name.zasenko.aoc21.day25;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class MapTest {

    public static final String MAP_STRING = "...>>>>>...";

    @Test
    void stringify() {
        Map map = new Map(MAP_STRING);
        assertEquals(MAP_STRING, map.toString());
    }

    @Test
    void step() {
        Map map = new Map(MAP_STRING);
        Map mapIteration = map.step();

        assertEquals("...>>>>.>..", mapIteration.toString());
    }

    @Test
    void multilineStep() {
        Map map = new Map("..........\n" +
                ".>v....v..\n" +
                ".......>..\n" +
                "..........");
        Map mapIteration = map.step();

        assertEquals("..........\n" +
                ".>........\n" +
                "..v....v>.\n" +
                "..........", mapIteration.toString());
    }

    @Test
    void multilineEdgeStep() {
        Map map = new Map("...>...\n" +
                ".......\n" +
                "......>\n" +
                "v.....>\n" +
                "......>\n" +
                ".......\n" +
                "..vvv..");
        Map mapIteration = map.step();

        assertEquals("..vv>..\n" +
                ".......\n" +
                ">......\n" +
                "v.....>\n" +
                ">......\n" +
                ".......\n" +
                "....v..", mapIteration.toString());
    }

    @Test
    void notEquals() {
        Map map = new Map(MAP_STRING);
        Map mapIteration = map.step();

        assertNotEquals(mapIteration, map);
    }

    @Test
    void equals() {
        Map map = new Map(MAP_STRING);

        assertEquals(new Map(MAP_STRING), map);
    }

    @Test
    void staleStep() {
        Map map = new Map("..>>v>vv..\n" +
                "..v.>>vv..\n" +
                "..>>v>>vv.\n" +
                "..>>>>>vv.\n" +
                "v......>vv\n" +
                "v>v....>>v\n" +
                "vvv.....>>\n" +
                ">vv......>\n" +
                ".>v.vv.v..");

        assertEquals(map, map.step());
    }

}