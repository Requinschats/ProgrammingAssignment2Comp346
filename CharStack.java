import CharStackExceptions.CharStackEmptyException;
import CharStackExceptions.CharStackFullException;
import CharStackExceptions.CharStackInvalidAceessException;
import CharStackExceptions.CharStackInvalidSizeException;

class CharStack {
    public static final int MIN_SIZE = 7;
    public static final int MAX_SIZE = 32;
    public static final int DEFAULT_SIZE = 10;

    private static int stackSize = DEFAULT_SIZE;
    private static int stackTopIndex = 3;
    private static char charStack[] = new char[]{'a', 'b', 'c', 'd', '$', '$', '$', '$', '$', '$'};

    public CharStack() {}

    public CharStack(int size) throws CharStackInvalidSizeException {
        if (size < MIN_SIZE || size > MAX_SIZE) {
            throw new CharStackInvalidSizeException(size);
        }
        if (size != DEFAULT_SIZE) {
            this.charStack = new char[size];
            for (int i = 0; i < size - 6; i++) {
                this.charStack[i] = (char) ('a' + i);
            }
            for (int i = 1; i <= 6; i++) {
                this.charStack[size - i] = '$';
            }
            this.stackTopIndex = size - 7;
            this.stackSize = size;
        }
    }

    public static char pick() throws CharStackEmptyException {
        if (stackTopIndex == -1) {
            throw new CharStackEmptyException();
        }
        return charStack[stackTopIndex];
    }

    public char getAt(int piPosition) throws CharStackInvalidAceessException {
        if (piPosition < 0 || piPosition >= stackSize) {
            throw new CharStackInvalidAceessException();
        }
        return this.charStack[piPosition];
    }

    public static void push(char pcChar) throws CharStackFullException {
        if (stackTopIndex == stackSize - 1) {
            throw new CharStackFullException();
        }
        charStack[++stackTopIndex] = pcChar;
    }

    public static char pop() throws CharStackEmptyException {
        if (stackTopIndex == -1) {
            throw new CharStackEmptyException();
        }
        char cChar = charStack[stackTopIndex];
        charStack[stackTopIndex--] = '$'; // Leave prev. value undefined
        return cChar;
    }

    public int getSize() {
        return this.stackSize;
    }

    public int getTop() {
        return this.stackTopIndex;
    }
}
