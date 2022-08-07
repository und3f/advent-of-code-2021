package name.zasenko.aoc21.day24.operation;

import name.zasenko.aoc21.day24.ALU;
import name.zasenko.aoc21.day24.operation.twoargumentsoperation.TwoArgumentsOperation;

public class VariableVariableOperation implements Operation {
    private final char a, b;
    private final TwoArgumentsOperation operation;

    public VariableVariableOperation(TwoArgumentsOperation operation, char a, char b) {
        this.operation = operation;
        this.a = a;
        this.b = b;
    }

    @Override
    public void execute(ALU alu) {
        alu.setVariable(a, operation.execute(alu.getVariable(a), alu.getVariable(b)));
    }

    @Override
    public String toString() {
        return this.a + " " + operation.toString() + " " + this.b;
    }
}
