package name.zasenko.aoc21.day24;

import name.zasenko.aoc21.day24.operation.Input;
import name.zasenko.aoc21.day24.operation.Operation;
import name.zasenko.aoc21.day24.operation.VariableValueOperation;
import name.zasenko.aoc21.day24.operation.VariableVariableOperation;
import name.zasenko.aoc21.day24.operation.twoargumentsoperation.*;

public class OperationFactory {
    Operation buildOperation(String line) {
        String[] words = line.split("\\s+");
        final String opName = words[0];
        char a = words[1].charAt(0);

        if (opName.equals("inp")) {
            return new Input(a);
        }

        TwoArgumentsOperation op = null;
        switch (opName) {
            case "add":
                op = new Add();
                break;
            case "mul":
                op = new Mul();
                break;
            case "div":
                op = new Div();
                break;
            case "mod":
                op = new Mod();
                break;
            case "eql":
                op = new Eql();
                break;
        }

        if (op == null) {
            throw new IllegalArgumentException("Unknown operation " + opName);
        }

        char b = words[2].charAt(0);
        if (! Character.isAlphabetic(b)) {
            int bValue = Integer.parseInt(words[2]);

            if (op instanceof Mul && bValue == 0) {
                return new Clear(a);
            }

            return new VariableValueOperation(op, a, bValue);
        }

        return new VariableVariableOperation(op, a, b);
    }
}
