package ru.job4j.concurrent.pools;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Sums sums = (Sums) o;
            return rowSum == sums.rowSum && colSum == sums.colSum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rowSum, colSum);
        }
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] result = new Sums[matrix.length];
        IntStream.range(0, matrix.length).forEach(x -> result[x] = new Sums());
        for (int i = 0; i < matrix.length; i++) {
            for (int b = 0; b < matrix[i].length; b++) {
                result[i].setRowSum(result[i].getRowSum() + matrix[i][b]);
                result[b].setColSum(result[b].getColSum() + matrix[i][b]);
            }
        }
        return result;
    }

    private static CompletableFuture<Sums> getSums(int[][] data, int position) {
        return CompletableFuture.supplyAsync(() -> {
            Sums result = new Sums();
            for (int row : data[position]) {
                result.setRowSum(result.getRowSum() + row);
            }
            for (int[] column : data) {
                result.setColSum(result.getColSum() + column[position]);
            }
            return result;
        });
    }

    public static Sums[] asyncSum(int[][] matrix) {
        CompletableFuture<Sums>[] array = new CompletableFuture[matrix.length];
        IntStream.range(0, matrix.length).forEach(x -> array[x] = getSums(matrix, x));
        Sums[] result = new Sums[matrix.length];
        IntStream.range(0, matrix.length).forEach(x -> {
            try {
                result[x] = array[x].get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });
        return result;
    }

    public static void main(String[] args) {
        Sums[] sums = RolColSum.sum(new int[][] {{1, 2, 3}, {3, 4, 5}, {6, 7, 8}});
        Arrays.stream(sums).forEach(x -> System.out.println("RowSum" + x.rowSum
                + System.lineSeparator() + "ColSum" + x.colSum));

        System.out.println(System.lineSeparator());

        Sums[] sums1 = RolColSum.asyncSum(new int[][] {{1, 2, 3}, {3, 4, 5}, {6, 7, 8}});
        Arrays.stream(sums1).forEach(x -> System.out.println("RowSum" + x.rowSum
                + System.lineSeparator() + "ColSum" + x.colSum));
    }
}
