package name.zasenko.aoc21.day19;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapTest {
    List<Scanner> scanners;

    @BeforeEach
    void init() {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream in = classLoader.getResourceAsStream("scanner1.txt");
        scanners = ScannerFactory.fromStream(in);
    }

    @Test
    public void findScannerOffset() {
        Map map = new Map(scanners.get(0));
        Scanner transformedScanner = map.findOffset(scanners.get(1)).getRotatedShiftedScanner();
        assertArrayEquals(new int[]{-618, -824, -621}, transformedScanner.getCoords().get(0).getCoord());
    }

    @Test
    public void revealWholeMapTest() {
        Map map = new Map(scanners);

        assertEquals(79, map.getCoords().size());
    }
}
