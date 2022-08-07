package name.zasenko.aoc21.day24;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ALUTest {
    public static final int INPUT = 4;
    ALU alu;
    @BeforeEach
    void setup() {
        alu = new ALU();
    }

    public static final int value = 3;
    public static final char variable = 'w';

    @Test
    public void shouldInitializeVariableToZero() {
        assertEquals(0, alu.getVariable('w'));
        assertEquals(0, alu.getVariable('x'));
        assertEquals(0, alu.getVariable('y'));
        assertEquals(0, alu.getVariable('z'));
    }

    @Test
    public void shouldRetrieveSettedVariable() {
        alu.setVariable(variable, value);

        assertEquals(value, alu.getVariable(variable));
    }

    @Test
    public void shouldReturnInput() {
        alu.setInput(Arrays.asList(INPUT, INPUT+1));

        assertEquals(alu.getInput(), INPUT);
        assertEquals(alu.getInput(), INPUT + 1);
    }
}
