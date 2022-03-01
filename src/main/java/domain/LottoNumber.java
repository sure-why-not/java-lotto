package domain;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import constant.LottoConstant;

public class LottoNumber implements Comparable<LottoNumber> {

    private static final Map<Integer, LottoNumber> LOTTO_NUMBERS;

    static {
        LOTTO_NUMBERS = IntStream.rangeClosed(LottoConstant.MINIMUM_LOTTO_NUMBER, LottoConstant.MAXIMUM_LOTTO_NUMBER)
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
        if (value < LottoConstant.MINIMUM_LOTTO_NUMBER || value > LottoConstant.MAXIMUM_LOTTO_NUMBER) {
            throw new IllegalArgumentException("유효한 로또 번호가 아닙니다.");
        }
    }

    public int getValue() {
        return value;
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
