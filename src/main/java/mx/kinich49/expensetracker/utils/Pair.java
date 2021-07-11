package mx.kinich49.expensetracker.utils;

import lombok.Getter;
import lombok.NonNull;

@Getter
public final class Pair<L, R> {

    private final @NonNull L left;
    private final @NonNull R right;

    private Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public static <L, R> Pair<L, R> of(@NonNull L left, @NonNull R right) {
        return new Pair<>(left, right);
    }

}
