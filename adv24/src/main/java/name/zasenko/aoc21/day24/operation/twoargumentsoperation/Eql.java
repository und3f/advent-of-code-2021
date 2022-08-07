package name.zasenko.aoc21.day24.operation.twoargumentsoperation;

public class Eql implements TwoArgumentsOperation {
    @Override
    public int execute(int a, int b) {
        return a == b ? 1 : 0;
    }

    @Override
    public String toString() {
        return "==";
    }
}
