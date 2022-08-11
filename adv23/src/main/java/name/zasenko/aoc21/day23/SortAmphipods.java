package name.zasenko.aoc21.day23;

import java.util.*;
import java.util.stream.Collectors;

public class SortAmphipods {
    final private Burrow burrow;
    final private Set<Integer> hallways;
    int solutionEnergy = Integer.MAX_VALUE;

    public SortAmphipods(Burrow burrow) {
        this.burrow = burrow;
        this.hallways = burrow.findHallways();
    }

    int sort() {
        sortAmphipods(burrow.getAmphipods(), 0);

        return solutionEnergy;
    }

    private void sortAmphipods(Map<Integer, Integer> amphipods, int energy) {
        var misplaced = findMisplacedAmphipodsPositions(amphipods);

        if (misplaced.isEmpty()) {
//            System.out.println("Found solution: " + energy);
            if (energy < solutionEnergy) solutionEnergy = energy;

            return;
        }

        for (var amphipod : misplaced) {
            HashMap<Integer, Integer> newAmphipods = new HashMap<>(amphipods);

            newAmphipods.remove(amphipod);

//        var paths = new DFS(amphipods, amphipod).getPaths().stream().sorted(Comparator.comparingInt(AmphipodPath::pathSize).reversed()).collect(Collectors.toList());
            var paths = new DFS(amphipods, amphipod).getPaths();
            for (AmphipodPath path : paths) {
                int newEnergy = energy + path.calculateEnergy();
                if (newEnergy > solutionEnergy) continue;

                newAmphipods.put(path.getDestination(), path.amphipod);

                sortAmphipods(newAmphipods, newEnergy);

                newAmphipods.remove(path.getDestination());
            }

        }
    }

    private Set<Integer> findMisplacedAmphipodsPositions(Map<Integer, Integer> amphipods) {
        Set<Integer> misplaced = new HashSet<>();

        List<Integer> positions = amphipods.keySet().stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());

        for (var p : positions) {
            if (misplaced.contains(p)) continue;

            if (burrow.isRoom(p)) {
                if (!burrow.getSideRooms().get(p).equals(amphipods.get(p))) {
                    misplaced.add(p);

                    LinkedList<Integer> adj = burrow.adj(p).stream().filter(v -> v < p && burrow.isRoom(v)).collect(Collectors.toCollection(LinkedList::new));
                    while (!adj.isEmpty()) {
                        int w = adj.pop();

                        if (amphipods.containsKey(w)) {
                            misplaced.add(w);
                        }

                        adj.addAll(burrow.adj(w).stream().filter(v -> v < w && burrow.isRoom(v)).collect(Collectors.toList()));
                    }
                }
            } else {
                misplaced.add(p);
            }
        }

        return misplaced;
    }

    private Map<Integer, Integer> findDestinationHomes(Map<Integer, Integer> amphipods) {
        Map<Integer, Integer> homes = new HashMap<>();

        for (var entry : burrow.getSideRooms().entrySet()) {
            var value = entry.getValue();
            var position = entry.getKey();

            Integer prev = homes.get(value);
            if (!value.equals(amphipods.get(position))) if (prev == null || prev < position) homes.put(value, position);
        }
        return homes;
    }

    private int findAmphipodHome(Map<Integer, Integer> amphipods, int amphipod) {
        Integer home = null;
        var value = amphipods.get(amphipod);

        for (var entry : burrow.getSideRooms().entrySet()) {
            var roomValue = entry.getValue();
            if (!roomValue.equals(value)) continue;

            var position = entry.getKey();

            if (!roomValue.equals(amphipods.get(position))) if (home == null || home < position) home = position;
        }
        return home;
    }

    public class BreadthFirstPaths {
        private final Set<Integer> marked = new HashSet<>();
        private final Map<Integer, Integer> edgeTo = new HashMap<>();
        private final int s;

        public BreadthFirstPaths(int v) {
            this.s = v;
            bfs(v);
        }

        private void bfs(int s) {
            marked.add(s);

            LinkedList<Integer> queue = new LinkedList<>();
            queue.add(s);

            while (!queue.isEmpty()) {
                int v = queue.pop();
                for (int w : burrow.adj(v)) {
                    if (!marked.contains(w)) {
                        edgeTo.put(w, v);
                        marked.add(w);
                        queue.add(w);
                    }
                }
            }
        }

        public List<Integer> getPathTo(int v) {
            LinkedList<Integer> path = new LinkedList<>();
            for (int x = v; x != s; x = edgeTo.get(x)) {
                path.add(0, x);
            }

            return path;
        }
    }

    // Some idea for faster searching, not used
    public class DepGraph {
        Map<Integer, Set<Integer>> adj = new HashMap<>();
        Map<Integer, List<Integer>> paths = new HashMap<>();

        public DepGraph(Map<Integer, Integer> amphipods) {
            var misplaced = findMisplacedAmphipodsPositions(amphipods);
            if (misplaced.isEmpty()) return;

            Map<Integer, Integer> homes = findDestinationHomes(amphipods);

            for (var v : misplaced) {
                var path = new BreadthFirstPaths(v).getPathTo(homes.get(amphipods.get(v)));
                paths.put(v, path);

                Set<Integer> dependencies = new HashSet<>();
                for (var w : path) {
                    if (amphipods.containsKey(w)) {
                        dependencies.add(w);
                    }
                }
                adj.put(v, dependencies);
            }
        }

        public Set<Integer> getV() {
            return adj.keySet();
        }

        public Set<Integer> getDependencies(int v) {
            return adj.get(v);
        }

        public List<Integer> getLowerDepV() {
            return getV().stream().sorted((Integer v, Integer w) -> adj.get(v).size() - adj.get(w).size()).collect(Collectors.toList());
        }

        public List<Integer> getPath(int v) {
            return paths.get(v);
        }

        @Override
        public String toString() {
            return adj.toString();
        }
    }

    public class DFS {
        final int value;
        final Set<Integer> possiblePositions = new HashSet<>();
        Set<Integer> marked = new HashSet<>();
        List<AmphipodPath> paths = new LinkedList<>();

        public DFS(Map<Integer, Integer> amphipods, int v) {
            value = amphipods.get(v);

            possiblePositions.add(findAmphipodHome(amphipods, v));

            if (burrow.isRoom(v)) possiblePositions.addAll(hallways);

            dfs(amphipods, v, new LinkedList<>());
        }

        private void dfs(Map<Integer, Integer> amphipods, int v, List<Integer> pathBefore) {
            marked.add(v);

            List<Integer> path = new LinkedList<>(pathBefore);
            path.add(v);

            if (!pathBefore.isEmpty()) {
                if (amphipods.containsKey(v)) return;

                if (possiblePositions.contains(v)) paths.add(new AmphipodPath(value, path));
            }

            for (int w : burrow.adj(v)) {
                if (!marked.contains(w)) dfs(amphipods, w, path);
            }
        }

        public List<AmphipodPath> getPaths() {
            return paths;
        }
    }

}