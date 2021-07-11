package mx.kinich49.expensetracker.utils;

import lombok.Data;
import lombok.NonNull;

@Data
public class Triad<L, M, R> {

    private final @NonNull L left;
    private final @NonNull M middle;
    private final @NonNull R right;

    private Triad(L left, M middle, R right) {
        this.left = left;
        this.middle = middle;
        this.right = right;
    }

    public static <L, M, R> Triad<L, M, R> of(@NonNull L left,
                                                 @NonNull M middle,
                                                 @NonNull R right) {
        return new Triad<>(left, middle, right);
    }
}
