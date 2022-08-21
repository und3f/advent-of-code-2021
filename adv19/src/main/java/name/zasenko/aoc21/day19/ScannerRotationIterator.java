package name.zasenko.aoc21.day19;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class ScannerRotationIterator implements Iterator<Scanner> {
    private final Scanner scanner;
    private final List<CoordRotationsIterator> coordIterators;

    public ScannerRotationIterator(Scanner scanner) {
        this.scanner = scanner;
        this.coordIterators = scanner.getCoords().stream().map(CoordRotationsIterator::new).collect(Collectors.toList());
    }

    @Override
    public boolean hasNext() {
        return coordIterators.get(0).hasNext();
    }

    @Override
    public Scanner next() {
        List<Coord> coords = coordIterators.stream().map(CoordRotationsIterator::next).collect(Collectors.toList());
        return new Scanner(scanner.name, coords);
    }
}
