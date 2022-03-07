package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LottoNumber implements Comparable<LottoNumber> {

    private static final int MINIMUM_LOTTO_NUMBER = 1;
    private static final int MAXIMUM_LOTTO_NUMBER = 45;

    private static final Map<Integer, LottoNumber> LOTTO_NUMBERS;

    static {
        LOTTO_NUMBERS = IntStream.rangeClosed(MINIMUM_LOTTO_NUMBER, MAXIMUM_LOTTO_NUMBER)
            .boxed()
            .collect(Collectors.toMap(number -> number, LottoNumber::new));
    }

    private final int value;

    private LottoNumber(int value) {
        this.value = value;
    }

    public static LottoNumber valueOf(final int number) {
        validateInRange(number);
        return LOTTO_NUMBERS.get(number);
    }

    private static void validateInRange(int value) {
        if (value < MINIMUM_LOTTO_NUMBER || value > MAXIMUM_LOTTO_NUMBER) {
            throw new IllegalArgumentException(
                "로또 번호는 " + MINIMUM_LOTTO_NUMBER + "~" + MAXIMUM_LOTTO_NUMBER + "여야 합니다.");
        }
    }

    public int value() {
        return value;
    }

    public static List<LottoNumber> values() {
        return new ArrayList<>(LOTTO_NUMBERS.values());
    }

    @Override
    public int compareTo(LottoNumber other) {
        return Integer.compare(this.value, other.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        LottoNumber that = (LottoNumber)o;

        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
