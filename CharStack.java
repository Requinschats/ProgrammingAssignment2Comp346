import CharStackExceptions.CharStackEmptyException;
import CharStackExceptions.CharStackFullException;
import CharStackExceptions.CharStackInvalidAceessException;
import CharStackExceptions.CharStackInvalidSizeException;

// Source code for character stack:
class CharStack {
    public static final int MIN_SIZE = 7;
    public static final int MAX_SIZE = 32;
    public static final int DEFAULT_SIZE = 10;

    private static int iSize = DEFAULT_SIZE;
    private static int stackTopIndex = 3;
    private static char charStack[] = new char[]{'a', 'b', 'c', 'd', '$', '$', '$', '$', '$', '$'};

    public CharStack() {
    }

    public CharStack(int piSize) throws CharStackInvalidSizeException {
        if (piSize < MIN_SIZE || piSize > MAX_SIZE) {
            throw new CharStackInvalidSizeException(piSize);
        }
        if (piSize != DEFAULT_SIZE) {
            this.charStack = new char[piSize];
            for (int i = 0; i < piSize - 6; i++) {
                this.charStack[i] = (char) ('a' + i);
            }
            for (int i = 1; i <= 6; i++) {
                this.charStack[piSize - i] = '$';
            }
            this.stackTopIndex = piSize - 7;
            this.iSize = piSize;
        }
    }

    public static char pick() throws CharStackEmptyException {
        if (stackTopIndex == -1) {
            throw new CharStackEmptyException();
        }
        return charStack[stackTopIndex];
    }

    public char getAt(int piPosition) throws CharStackInvalidAceessException {
        if (piPosition < 0 || piPosition >= iSize) {
            throw new CharStackInvalidAceessException();
        }
        return this.charStack[piPosition];
    }

    public static void push(char pcChar) throws CharStackFullException {
        if (stackTopIndex == iSize - 1) {
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

    /* Getters */
    public int getSize() {
        return this.iSize;
    }

    public int getTop() {
        return this.stackTopIndex;
    }
}
