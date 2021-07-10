package mx.kinich49.expensetracker.utils;

import lombok.Getter;
import lombok.NonNull;

@Getter
public final class Pair<S, T> {

    private final @NonNull S left;
    private final @NonNull T right;

    private Pair(S left, T right) {
        this.left = left;
        this.right = right;
    }

    public static <S, T> Pair<S, T> of(@NonNull S first, @NonNull T second) {
        return new Pair<>(first, second);
    }

}
