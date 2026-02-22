import java.util.*;

public class LegacyRoomTypes {
    public static final int SINGLE = 1;
    public static final int DOUBLE = 2;
    public static final int TRIPLE = 3;
    public static final int DELUXE = 4;

    private static final Map<Integer, Double> PRICES = Map.of(
        SINGLE, 14000.0,
        DOUBLE, 15000.0,
        TRIPLE, 12000.0,
        DELUXE, 16000.0
    );

    public static double priceOf(int type) {
        return PRICES.getOrDefault(type, 16000.0);
    }

    public static String nameOf(int t) {
        return switch (t) {
            case SINGLE -> "SINGLE";
            case DOUBLE -> "DOUBLE";
            case TRIPLE -> "TRIPLE";
            default -> "DELUXE";
        };
    }
}
