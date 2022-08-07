package name.zasenko.aoc21.day24.operation.twoargumentsoperation;

import name.zasenko.aoc21.day24.ALU;
import name.zasenko.aoc21.day24.operation.Operation;

public class Clear implements Operation {
    private final char variable;
    public Clear(char variable) {
        this.variable = variable;
    }

    @Override
    public void execute(ALU alu) {
        alu.setVariable(variable, 0);
    }

    @Override
    public String toString() {
        return "clear " + variable;
    }
}
