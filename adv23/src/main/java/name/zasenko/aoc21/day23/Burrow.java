package name.zasenko.aoc21.day23;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Burrow {
    String[] lines;

    Set<Integer> V;
    Map<Integer, Set<Integer>> adj;
    Map<Integer, Integer> amphipods;

    Map<Integer, Integer> sideRooms;

    public Burrow(String input) {
        V = new HashSet<>();
        adj = new HashMap<>();
        amphipods = new HashMap<>();
        sideRooms = new HashMap<>();

        lines = input.split("\n");

        for (int i = 0; i < lines.length; i++) {
            for (int j = 0; j < lines[i].length(); j++) {
                char c = lines[i].charAt(j);
                switch (c) {
                    case '#':
                    case ' ':
                        break;
                    case 'A':
                    case 'B':
                    case 'C':
                    case 'D':
                        addAmphipod(c, i, j);
                        addRoom(i, j);
                    case '.':
                        addVertex(i, j);
                        break;
                    default:
                        throw (new RuntimeException("Unexpected character " + c));
                }
            }
        }

    }

    private void addRoom(int y, int x) {
        // Determine room value by previous room
        int value = 1;
        if (x > 1) {
            Integer prevValue = sideRooms.get(coordToV(y, x - 2));
            if (prevValue != null)
                value = prevValue * 10;
        }

        sideRooms.put(coordToV(y, x), value);
    }

    private void addAmphipod(char amphipod, int i, int j) {
        int value = (int) Math.pow(10, (amphipod - 'A'));
        amphipods.put(coordToV(i, j), value);
    }

    private void addVertex(int i, int j) {
        int w = coordToV(i, j);
        V.add(w);
        adj.put(w, new HashSet<>());

        if (j > 0) {
            int v = coordToV(i, j - 1);
            if (V.contains(v)) {
                connect(w, v);
            }
        }

        if (i > 0) {
            int v = coordToV(i - 1, j);
            if (V.contains(v)) {
                connect(w, v);
            }
        }
    }

    private void connect(int v1, int v2) {
        adj.get(v1).add(v2);
        adj.get(v2).add(v1);
    }

    private int coordToV(int y, int x) {
        return y * lines[0].length() + x;
    }

    public Set<Integer> getV() {
        return V;
    }

    public Set<Integer> adj(int v) {
        return adj.get(v);
    }

    public Map<Integer, Integer> getAmphipods() {
        return amphipods;
    }

    public Map<Integer, Integer> getSideRooms() {
        return sideRooms;
    }

    public boolean isRoom(Integer v) {
        return sideRooms.containsKey(v);
    }

    public Set<Integer> findHallways() {
        final Set<Integer> hallways;
        hallways = getV().stream()
                .filter(v -> !isRoom(v) && adj(v).size() < 3)
                .collect(Collectors.toSet());
        return hallways;
    }

}
