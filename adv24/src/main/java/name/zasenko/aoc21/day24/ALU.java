package name.zasenko.aoc21.day24;

import name.zasenko.aoc21.day24.operation.Input;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ALU {
    private final static String variablesNames = "wxyz";

    int []variables = new int[variablesNames.length()];
    private final LinkedList<Integer> input = new LinkedList<>();

    public int getVariable(char v) {
        int index = getVariableIndex(v);

        return variables[index];
    }

    private int getVariableIndex(char v) {
        int index = v - 'w';
        if (index < 0) {
            throw new IllegalArgumentException("Unexpected variable: " + v);
        }
        return index;
    }

    public void setVariable(char variable, int value) {
        int index = getVariableIndex(variable);

        variables[index] = value;
    }

    public void setInput(List<Integer> values) {
        input.addAll(values);
    }

    public int getInput() {
        return input.pop();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < variablesNames.length(); i++) {
            if (i > 0)
                sb.append(", ");

            final char v = variablesNames.charAt(i);
            sb.append(v + ": " + getVariable(v));
        }

        return sb.toString();
    }
}
