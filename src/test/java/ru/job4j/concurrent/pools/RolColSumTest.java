package ru.job4j.concurrent.pools;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class RolColSumTest {

    private final static RolColSum.Sums FIRST = new RolColSum.Sums();
    private final static RolColSum.Sums SECOND = new RolColSum.Sums();
    private final static RolColSum.Sums THIRD = new RolColSum.Sums();

    private final static RolColSum.Sums[] EXPECTED = new RolColSum.Sums[] {FIRST, SECOND, THIRD};

    static {
        FIRST.setRowSum(6);
        FIRST.setColSum(10);
        SECOND.setRowSum(12);
        SECOND.setColSum(13);
        THIRD.setRowSum(21);
        THIRD.setColSum(16);
    }

    @Test
    void whenLinearSum() {
        RolColSum.Sums[] result = RolColSum.sum(new int[][] {{1, 2, 3}, {3, 4, 5}, {6, 7, 8}});
        Assertions.assertThat(result).isEqualTo(EXPECTED);
    }

    @Test
    void whenAsyncSum() {
        RolColSum.Sums[] result = RolColSum.asyncSum(new int[][] {{1, 2, 3}, {3, 4, 5}, {6, 7, 8}});
        Assertions.assertThat(result).isEqualTo(EXPECTED);
    }
}