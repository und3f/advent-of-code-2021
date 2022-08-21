package name.zasenko.aoc21.day19;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapTest {
    ClassLoader classLoader;

    @BeforeEach
    public void init() {
        classLoader = getClass().getClassLoader();
    }

    @Test
    public void findScannerOffset() {
        InputStream in = classLoader.getResourceAsStream("scanner1.txt");
        List<Scanner> scanners = ScannerFactory.fromStream(in);

        Map map = new Map(scanners.get(0));
        Scanner transformedScanner = map.findOffset(scanners.get(1)).getRotatedShiftedScanner();
        assertArrayEquals(new int[]{-618, -824, -621}, transformedScanner.getCoords().get(0).getCoord());
    }

    @Test
    public void revealWholeMapTest() {
        InputStream in = classLoader.getResourceAsStream("scanner1.txt");
        List<Scanner> scanners = ScannerFactory.fromStream(in);
        Map map = new Map(scanners);

        assertEquals(79, map.getCoords().size());
    }
}
