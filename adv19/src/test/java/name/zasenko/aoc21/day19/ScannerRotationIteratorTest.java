package name.zasenko.aoc21.day19;

import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ScannerRotationIteratorTest {
    @Test
    public void rotationsTest() {
        InputStream in = getClass().getClassLoader().getResourceAsStream("scanner0.txt");
        List<Scanner> scanners = ScannerFactory.fromStream(in);

        var scanner0 = scanners.get(0);
        List<HashSet<Coord>> rotations = new ArrayList<>(scanner0.getCoords().size());
        for (Coord ignored : scanner0.getCoords()) {
            rotations.add(new HashSet<>());
        }

        for (ScannerRotationIterator it = new ScannerRotationIterator(scanners.get(0)); it.hasNext();) {
            var rotatedCoords = it.next().getCoords();
            for (int i = 0; i < rotatedCoords.size(); i++) {
                rotations.get(i).add(rotatedCoords.get(i));
            }
        }

        for (Scanner scanner : scanners) {
            if (scanner.equals(scanner0))
                continue;

            for (int j = 0; j < scanner.getCoords().size(); j++) {
                assertTrue(rotations.get(j).contains(scanner.getCoords().get(j)));
            }
        }
    }

}