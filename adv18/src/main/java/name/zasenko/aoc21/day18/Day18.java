package name.zasenko.aoc21.day18;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day18 {
    final static Pattern entity = Pattern.compile("(\\d+|\\[)");
    public static final String PAIR_START = "[";

    public static List<Integer> parseLine(String line) {
        List<Integer> fishes = new ArrayList<>();

        Matcher matcher = entity.matcher(line);

        while (matcher.find()) {
            String group = matcher.group();
            if (group.equals(PAIR_START)) {
                fishes.add(null);
            } else {
                int value = Integer.parseInt(group);
                fishes.add(value);
            }
        }

        return fishes;
    }

    public static List<Integer> explode(List<Integer> fishes) {
        Deque<Integer> stack = new LinkedList();
        for (int i = 0; i < fishes.size(); i++) {
            Integer v = fishes.get(i);

            if (v == null) {
                stack.push(0);

                if (stack.size() > 4) {
                    if (fishes.get(i + 1) != null && fishes.get(i + 2) != null) {
                        for (int j = i - 1; j >= 0; j--) {
                            Integer left = fishes.get(j);
                            if (left != null) {
                                fishes.set(j, left + fishes.get(i + 1));
                                break;
                            }
                        }

                        for (int j = i + 3; j < fishes.size(); j++) {
                            Integer right = fishes.get(j);
                            if (right != null) {
                                fishes.set(j, right + fishes.get(i + 2));
                                break;
                            }
                        }

                        fishes.remove(i + 1);
                        fishes.remove(i + 1);

                        fishes.set(i, 0);

                        return fishes;
                    }
                }
            } else {
                Integer sv;
                for (sv = stack.pop(); sv >= 1; sv = stack.pop()) {
                    if (stack.size() == 0) {
                        return null;
                    }
                }
                stack.push(sv + 1);
            }

        }
        return null;
    }

    public static List<Integer> split(List<Integer> fishes) {
        for (int i = 1; i < fishes.size(); i++) {
            Integer value = fishes.get(i);
            if (value != null && value >= 10) {
                fishes.add(i + 1, (int) (Math.ceil((double) value / 2.)));
                fishes.add(i + 1, value / 2);
                fishes.set(i, null);

                return fishes;
            }
        }
        return null;
    }

    public static List<Integer> reduce(List<Integer> fishes) {
        for (boolean run = true; run; ) {
            List<Integer> result;
            result = explode(fishes);
            if (result == null) {
                result = split(fishes);
            }

            if (result == null) {
                run = false;
            } else {
                fishes = result;
            }
        }

        return fishes;
    }

    public static List<Integer> add(List<Integer> original, List<Integer> additional) {
        LinkedList<Integer> fishes = new LinkedList<>(original);
        fishes.add(0, null);
        fishes.addAll(additional);

        return reduce(fishes);
    }

    public static int magnitude(List<Integer> fishes) {
        Deque<Integer> stack = new LinkedList<>();

        for (Integer value : fishes) {
            if (value == null) {
                stack.push(null);
            } else {
                int right = value;
                while (stack.size() > 0) {
                    Integer c = stack.pop();

                    if (c == null) {
                        break;
                    }
                    right = 3 * c + 2 * right;
                }
                stack.push(right);
            }
        }

        return stack.pop();
    }

    private static int part1(List<List<Integer>> lines) {
        List<Integer> sum = lines.get(0);

        for (List<Integer> fish : lines.subList(1, lines.size())) {
            sum = add(sum, fish);
        }

        return magnitude(sum);
    }

    private static int part2(List<List<Integer>> lines) {
        int maxSum = Integer.MIN_VALUE;

        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.size(); j++) {
                if (i == j) {
                    continue;
                }

                int sum = magnitude(add(lines.get(i), lines.get(j)));
                if (sum > maxSum) {
                    maxSum = sum;
                }
            }
        }

        return maxSum;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        List<List<Integer>> lines = new ArrayList<>();
        while (sc.hasNext()) {
            lines.add(parseLine(sc.nextLine()));
        }

        System.out.println("Part 1: " + part1(lines));
        System.out.println("Part 2: " + part2(lines));
    }
}
