package name.zasenko.aoc21.day14;

import java.util.Iterator;

public abstract class PolymerIterator implements Iterator<Byte> {
    int base;
    public PolymerIterator(int base) {
        this.base = base;
    }

    int getBase() {
        return base;
    }
}
