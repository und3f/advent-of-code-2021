package name.zasenko.aoc21.day14;

import java.nio.ByteBuffer;

public class CompressedData {
    private final ByteBuffer bb;
    private final int base;
    private final int symbolsPerByte;
    private final byte mask;
    private int position = 0;
    private int buffer = 0;
    private int size;

    public CompressedData(long capacity, int base) {
        this.base = base;

        int c = 1;
        int s = base;
        while (s < 0x100) {
            s *= base;
            c++;
        }
        symbolsPerByte = c;

        mask = (byte) (base - 1);

        final long capacityBytes = (int) Math.ceil((float) capacity / symbolsPerByte);
        this.bb = ByteBuffer.allocate((int) capacityBytes);

        assert(this.bb.capacity() == capacityBytes);
    }

    public CompressedData(CompressedData prototype) {
        this.base = prototype.base;
        this.symbolsPerByte = prototype.symbolsPerByte;
        this.mask = prototype.mask;

        this.bb = prototype.bb.asReadOnlyBuffer();
        this.size = prototype.size();

        this.position = 0;
        this.buffer = 0;
    }

    CompressedData duplicate() {
        return new CompressedData(this);
    }

    JustPolymerIterator iterator() {
        return new JustPolymerIterator(this);
    }

    int size() {
        return size;
    }

    void put(byte value) {
        assert size == 0;

        buffer = (buffer * base) | value;

        if ((position + 1) % symbolsPerByte == 0) {
            bb.put((byte) buffer);
            buffer = 0;
        }

        position++;
    }

    void closeWrite() {
        int appendBytes = (symbolsPerByte - position % symbolsPerByte) % symbolsPerByte;

        if (appendBytes > 0) {
            buffer *= Math.pow(base, appendBytes);
            bb.put((byte) buffer);
        }

        size = position;
        rewind();
    }

    void rewind() {
        position = 0;
        bb.rewind();
    }

    byte get() {
        assert hasRemaining();

        int offset = position % symbolsPerByte;
        if (offset == 0) {
            buffer = bb.get() & 0xff;
        }
        position++;

        int value = buffer;
        int bitsOffset = symbolsPerByte - 1 - offset;
        value /= Math.pow(base, bitsOffset);

        return (byte) (value & mask);
    }

    boolean hasRemaining() {
        return position < size;
    }

    int getBase() {
        return base;
    }
}
