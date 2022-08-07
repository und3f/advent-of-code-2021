package name.zasenko.aoc21.day24.operation;

import name.zasenko.aoc21.day24.ALU;

public class Input implements Operation {
    private char variable;
    public Input(char variable) {
        this.variable = variable;
    }

    @Override
    public void execute(ALU alu) {
        alu.setVariable(variable, alu.getInput());
    }

    @Override
    public String toString() {
        return "inp " + variable;
    }
}
