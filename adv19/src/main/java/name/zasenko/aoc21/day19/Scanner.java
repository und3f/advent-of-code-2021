package name.zasenko.aoc21.day19;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Scanner {
    final String name;
     final List<Coord> coords;

    public Scanner(String name) {
        this.name = name;
        coords = new ArrayList<>();
    }

    public Scanner(String name, List<Coord> coords) {
        this.name = name;
        this.coords = new ArrayList<>(coords);
    }

    public List<Set<Integer>> buildDistancesMap() {
        List<Set<Integer>> coordDistances = new ArrayList<>(coords.size());
        for (Coord source : coords) {
            Set<Integer> distances = new HashSet<>(coords.size() - 1);
            for (Coord destination : coords) {
                if (destination.equals(source))
                    continue;

                distances.add(source.distanceTo(destination));
            }

            coordDistances.add(distances);
        }

        return coordDistances;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append("\n");

        for (Coord coord : coords) {
            sb.append(coord);
            sb.append("\n");
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object scanner2) {
        if (scanner2 == this)
            return true;

        if (!(scanner2 instanceof Scanner)) {
            return false;
        }

        return coords.equals(((Scanner) scanner2).coords);
    }

    public void addCoord(Coord coord) {
        coords.add(coord);
    }

    public String getName() { return name; }

    public List<Coord> getCoords() { return coords; }

    public Scanner vector(Coord vector) {
        List<Coord> vectCoords = new ArrayList<>(getCoords().size());
        for (Coord coord : getCoords()) {
            vectCoords.add(coord.vector(vector));
        }

        return new Scanner(this.name, vectCoords);
    }
}
