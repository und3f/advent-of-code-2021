package name.zasenko.aoc21.day24;

import name.zasenko.aoc21.day24.operation.Input;
import name.zasenko.aoc21.day24.operation.Operation;
import name.zasenko.aoc21.day24.operation.VariableValueOperation;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day24 {
    final static int NUMBERS = 14;
    final static int BASE = 9;
    final static int MIN = 1;

    final static long MAX = (long) Math.pow(BASE, NUMBERS) - 1;

    public static void main(String[] args) {
        List<Operation> program = parseProgram();

        LinkedList<SubprogramDiff> diffs = getSubprogramDiffs(splitSubprograms(program));

        System.out.println("Part1: " + tryInput(program, getMaxInput(diffs)));
        System.out.println("Part2: " + tryInput(program, getMinInput(diffs)));
    }

    private static String tryInput(List<Operation> program, List<Integer> inputData) {
        if (executeProgram(program, inputData)) {
                return inputData.stream().map(Object::toString).collect(Collectors.joining(""));
            }

        return null;
    }

    private static List<Integer> getMaxInput(LinkedList<SubprogramDiff> diffs) {
        List<Integer> inputData = IntStream.range(0, NUMBERS).boxed().collect(Collectors.toList());

        for (SubprogramDiff d : diffs) {
            List<Integer> indices = Arrays.asList(d.src, d.dst);
            int offset = d.offset;

            if (offset < 0) {
                offset = -offset;
                Collections.reverse(indices);
            }

            inputData.set(indices.get(0), BASE);
            inputData.set(indices.get(1), BASE - offset);
        }
        return inputData;
    }

    private static List<Integer> getMinInput(LinkedList<SubprogramDiff> diffs) {
        List<Integer> inputData = IntStream.range(0, NUMBERS).boxed().collect(Collectors.toList());

        for (SubprogramDiff d : diffs) {
            List<Integer> indices = Arrays.asList(d.src, d.dst);
            int offset = d.offset;

            if (offset < 0) {
                offset = -offset;
                Collections.reverse(indices);
            }

            inputData.set(indices.get(1), MIN);
            inputData.set(indices.get(0), MIN + offset);
        }
        return inputData;
    }

    private static List<List<Operation>> splitSubprograms(List<Operation> program) {
        List<List<Operation>> subprograms = new ArrayList<>(NUMBERS);
        int offset = 0;
        for (int i = 0; i < NUMBERS; i++) {
            int j = offset + 1;
            for (; j < program.size(); j++) {
                if (program.get(j) instanceof Input) {
                    break;
                }
            }

            subprograms.add(program.subList(offset, j));
            offset = j;
        }
        return subprograms;
    }

    private static class SubprogramDiff {
        public int src, dst, offset;
        public SubprogramDiff(int src, int dst, int offset) {
            this.src = src;
            this.dst = dst;
            this.offset = offset;
        }

        @Override
        public String toString() {
            return src + " - " + dst + " = " + offset;
        }
    }

    private static LinkedList<SubprogramDiff> getSubprogramDiffs(List<List<Operation>> subprograms) {
        LinkedList<SubprogramDiff> diffs = new LinkedList<>();
        LinkedList<SubprogramDiff> pushes = new LinkedList<>();

        for (int i = 0; i < subprograms.size(); i++) {
            List<Operation> subprogram = subprograms.get(i);

            Operation subprogramTypeOperation = subprogram.get(4);
            if (!(subprogramTypeOperation instanceof VariableValueOperation)) {
                throw new Error("Invalid opcode");
            }

            int subprogramType = ((VariableValueOperation) subprogramTypeOperation).getB();
            switch (subprogramType) {
                case 1:
                    pushes.add(new SubprogramDiff(i, 0, getSubprogramType1Offset(subprogram)));
                    break;
                case 26:
                    SubprogramDiff push = pushes.removeLast();
                    push.dst = i;
                    push.offset = - (push.offset + getSubprogramType26Offset(subprogram));
                    diffs.add(push);
                    break;
                default:
                    throw new Error("Invalid opcode");
            }
        }
        return diffs;
    }

    static final int DEFAULT_INPUT = 1;
    private static int getSubprogramType26Offset(List<Operation> subprogram) {
        Operation op = subprogram.get(5);
        if (! (op instanceof VariableValueOperation)) {
            throw new Error("Invalid operation");
        }

        return ((VariableValueOperation) op).getB();
    }

    private static int getSubprogramType1Offset(List<Operation> subprogram) {
        int y = executeSubprogram(subprogram, Collections.singletonList(DEFAULT_INPUT)).getVariable('y');
        return y - DEFAULT_INPUT;
    }

    private static List<Operation> parseProgram() {
        List<Operation> program = new LinkedList<>();
        OperationFactory factory = new OperationFactory();

        Scanner s = new Scanner(System.in);
        while (s.hasNextLine()) {
            program.add(factory.buildOperation(s.nextLine()));
        }

        return program;
    }

    private static void printProgram(List<Operation> program) {
        int line = 0;
        for (Operation operation : program) {
            System.out.println(++line + ": " + operation);
        }
    }


    private static boolean executeProgram(List<Operation> program, List<Integer> input) {
        return executeSubprogram(program, input).getVariable('z') == 0;
    }

    private static ALU executeSubprogram(List<Operation> subprogram, List<Integer> input) {
        ALU alu = new ALU();
        alu.setInput(input);

        for (Operation operation : subprogram) {
            operation.execute(alu);
        }

        return alu;
    }

}
