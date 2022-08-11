package name.zasenko.aoc21.day23;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BurrowTest {

    public static final int AMPHIPODS_COUNT = 8;
    public static final int AMPHIPODS_TYPES = 4;
    public static final int ROOMS_PER_AMPHIPOD = AMPHIPODS_COUNT / AMPHIPODS_TYPES;

    @Test
    public void TestRead() throws Exception {
        String input = Files.readString(Paths.get(getClass().getClassLoader().getResource("sample.txt").toURI()), StandardCharsets.UTF_8);

        Burrow burrow = new Burrow(input);

        assertEquals(burrow.getAmphipods().size(), AMPHIPODS_COUNT);

        assertEquals(burrow.getSideRooms().size(), AMPHIPODS_COUNT);
        assertEquals(burrow.getSideRooms().entrySet().stream().filter(entry -> entry.getValue().equals(1)).count(), ROOMS_PER_AMPHIPOD);
        assertEquals(burrow.getSideRooms().entrySet().stream().filter(entry -> entry.getValue().equals(10)).count(), ROOMS_PER_AMPHIPOD);
        assertEquals(burrow.getSideRooms().entrySet().stream().filter(entry -> entry.getValue().equals(100)).count(), ROOMS_PER_AMPHIPOD);
        assertEquals(burrow.getSideRooms().entrySet().stream().filter(entry -> entry.getValue().equals(1000)).count(), ROOMS_PER_AMPHIPOD);

        for (Integer v : burrow.getSideRooms().keySet()) {
            assertTrue(burrow.isRoom(v));
        }
    }
}