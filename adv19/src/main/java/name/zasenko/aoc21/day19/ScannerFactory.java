package name.zasenko.aoc21.day19;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ScannerFactory {
    public static List<Scanner> fromStream(InputStream in) {
        ArrayList<Scanner> scanners = new ArrayList<>();

        java.util.Scanner inputScanner = new java.util.Scanner(in);
        while (inputScanner.hasNextLine()) {
            String name = inputScanner.nextLine();

            Scanner scanner = new Scanner(name);
            while (inputScanner.hasNextLine()) {
                String line = inputScanner.nextLine();
                if (line.isBlank())
                    break;

                Coord coord = new Coord(Arrays.stream(line.split(",")).map(Integer::parseInt).collect(Collectors.toList()));
                scanner.addCoord(coord);
            }
            scanners.add(scanner);
        }

        return scanners;
    }
}
