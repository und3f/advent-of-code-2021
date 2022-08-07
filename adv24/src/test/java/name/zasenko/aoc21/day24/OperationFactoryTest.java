package name.zasenko.aoc21.day24;

import name.zasenko.aoc21.day24.operation.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class OperationFactoryTest {
    public static final int VALUE1 = 4;
    public static final int VALUE2 = 2;

    OperationFactory factory;
    ALU alu;
    @BeforeEach
    public void setup() {
        factory = new OperationFactory();
        alu = new ALU();
    }

    @Test
    public void inputOperationTest() {
        alu.setInput(Arrays.asList(VALUE1));

        Operation op = factory.buildOperation("inp w");
        op.execute(alu);

        assertEquals(VALUE1, alu.getVariable('w'));
    }

    @Test
    public void addVariableVariableOperationTest() {
        alu.setVariable('w', VALUE1);
        alu.setVariable('x', VALUE2);

        Operation op = factory.buildOperation("add w x");
        op.execute(alu);

        assertEquals(VALUE1 + VALUE2, alu.getVariable('w'));
    }

    @Test
    public void addVariableValueOperationTest() {
        alu.setVariable('w', VALUE1);

        Operation op = factory.buildOperation("add w " + VALUE2);
        op.execute(alu);

        assertEquals(VALUE1 + VALUE2, alu.getVariable('w'));
    }

    @Test
    public void addVariableNegativeValueOperationTest() {
        alu.setVariable('w', VALUE1);

        Operation op = factory.buildOperation("add w " + -VALUE2);
        op.execute(alu);

        assertEquals(VALUE1 - VALUE2, alu.getVariable('w'));
    }

    @Test
    public void otherOperationsTest() {
        factory.buildOperation("mul w z");
        factory.buildOperation("div w z");
        factory.buildOperation("mod w z");
        factory.buildOperation("eql w z");
    }
}