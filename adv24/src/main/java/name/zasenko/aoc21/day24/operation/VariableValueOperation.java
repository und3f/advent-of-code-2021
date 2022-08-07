package name.zasenko.aoc21.day24.operation;

import name.zasenko.aoc21.day24.ALU;
import name.zasenko.aoc21.day24.operation.twoargumentsoperation.TwoArgumentsOperation;

public class VariableValueOperation implements Operation {
    private final char a;
    private final int b;
    private final TwoArgumentsOperation operation;

    public VariableValueOperation(TwoArgumentsOperation operation, char a, int b) {
        this.operation = operation;
        this.a = a;
        this.b = b;
    }

    @Override
    public void execute(ALU alu) {
        alu.setVariable(a, operation.execute(alu.getVariable(a), b));
    }

    @Override
    public String toString() {
        return this.a + " " + operation.toString() + " " + this.b;
    }

    public int getB() {
        return b;
    }
}
