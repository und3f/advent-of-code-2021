package name.zasenko.aoc21.day24.operation.twoargumentsoperation;

public class Mod implements TwoArgumentsOperation {
    @Override
    public int execute(int a, int b) {
        return a % b;
    }

    @Override
    public String toString() {
        return "%=";
    }
}
