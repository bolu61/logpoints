package anana5.sense.logpoints;

import java.util.Arrays;

public class IteratorLoopEmpty {
    public static void main(String[] args) {
        for (String arg : Arrays.asList(args)) {
            arg.toString();
        }
    }
}