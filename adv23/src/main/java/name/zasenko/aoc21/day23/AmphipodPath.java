package name.zasenko.aoc21.day23;

import java.util.List;

public class AmphipodPath {
    public int amphipod;
    public List<Integer> path;

    public AmphipodPath(int amphipod, List<Integer> path) {
        this.amphipod = amphipod;
        this.path = path;
    }

    public int getDestination() {
        return path.get(path.size() - 1);
    }

    public int pathSize() {
        return path.size();
    }

    @Override
    public String toString() {
        return amphipod + ": " + path.toString();
    }

    public int calculateEnergy() {
        return (path.size() - 1) * amphipod;
    }
}
