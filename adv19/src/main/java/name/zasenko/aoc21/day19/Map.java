package name.zasenko.aoc21.day19;

import java.util.*;

public class Map {
    final int MagicValue = 12 - 1;
    private final Scanner scanner;
    private final List<Coord> scannersOffset = new ArrayList<>();

    public Map(Scanner scanner) {
        this.scanner = new Scanner("Map rebuild", scanner.getCoords());
        scannersOffset.add(new Coord(0, 0, 0));
    }

    public Map(List<Scanner> scanners) {
        this(scanners.get(0));

        this.apply(scanners.subList(1, scanners.size()));
    }

    public void apply(List<Scanner> inputScanners) {
        List<Scanner> scanners = new ArrayList<>(inputScanners);

        while (! scanners.isEmpty()) {
            int size = scanners.size();

            scanners.removeIf(this::apply);

            if (scanners.size() == size) {
                throw new Error("No overlaps detected");
            }
        }
    }

    public boolean apply(Scanner scannerB) {
        ScannerOffset offset = findOffset(scannerB);
        if (offset == null)
            return false;

        Scanner transformed = offset.getRotatedShiftedScanner();

        HashSet<Coord> coords = new HashSet<>(getCoords());
        for (Coord coord : transformed.getCoords()) {
            if (!coords.contains(coord))
                this.scanner.addCoord(coord);
        }

        scannersOffset.add(offset.getOffset());

        return true;
    }

    public List<Coord> getCoords() {
        return scanner.getCoords();
    }

    public static class ScannerOffset {
        final Scanner rotatedScanner;
        final Coord offset;

        public ScannerOffset(Scanner scanner, Coord offset) {
            this.rotatedScanner = scanner;
            this.offset = offset;
        }

        public Scanner getRotatedShiftedScanner() {
            return rotatedScanner.vector(offset);
        }

        public Coord getOffset() {
            return offset;
        }
    }

    public ScannerOffset findOffset(Scanner destination) {
        var distancesOrig = scanner.buildDistancesMap();
        var distancesDst = destination.buildDistancesMap();

        HashMap<Integer, Integer> beaconsAssociations = new HashMap<>();
        for (int i = 0; i < distancesDst.size(); i++) {
            Set<Integer> dst = distancesDst.get(i);

            for (int j = 0; j < distancesOrig.size(); j++) {
                var orig = distancesOrig.get(j);

                var distancesOverlap = new HashSet<>(dst);
                distancesOverlap.retainAll(orig);
                if (distancesOverlap.size() >= MagicValue) {
                    beaconsAssociations.put(j, i);
                    break;
                }
            }
        }

        return calculateOffset(this.scanner, destination, beaconsAssociations);
    }

    private ScannerOffset calculateOffset(Scanner reference, Scanner dst, HashMap<Integer, Integer> associations) {
        if (associations.size() < 3)
            return null;

        ScannerRotationIterator it = new ScannerRotationIterator(dst);
        while (it.hasNext()) {
            Scanner rotatedDst = it.next();
            var associationIt = associations.entrySet().iterator();
            java.util.Map.Entry<Integer, Integer> first = associationIt.next();
            Coord difference = rotatedDst.getCoords().get(first.getValue()).vector(reference.getCoords().get(first.getKey()));

            boolean isCorrect = true;
            while (associationIt.hasNext()) {
                var nextAssociatedBeacon = associationIt.next();
                Coord checkedBeacon = rotatedDst.getCoords().get(nextAssociatedBeacon.getValue()).vector(difference);
                if (! checkedBeacon.equals(reference.getCoords().get(nextAssociatedBeacon.getKey()))) {
                    isCorrect = false;
                    break;
                }
            }

            if (isCorrect) {
                return new ScannerOffset(rotatedDst, difference);
            }
        }

        return null;
    }

    public List<Coord> getScannersOffset() {
        return scannersOffset;
    }
}
