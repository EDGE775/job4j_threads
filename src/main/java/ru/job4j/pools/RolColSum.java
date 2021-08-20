package ru.job4j.pools;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            Sums sum = new Sums();
            int rows = 0;
            int cols = 0;
            for (int j = 0; j < matrix[i].length; j++) {
                rows += matrix[i][j];
                cols += matrix[j][i];
            }
            sum.setRowSum(rows);
            sum.setColSum(cols);
            sums[i] = sum;
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        CompletableFuture<Sums>[] cf = new CompletableFuture[matrix.length];
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            cf[i] = getSums(matrix, i);
        }
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = cf[i].get();
        }
        return sums;
    }

    private static CompletableFuture<Sums> getSums(int[][] matrix, int index) {
        return CompletableFuture.supplyAsync(() -> {
            Sums sum = new Sums();
            int rows = 0;
            int cols = 0;
            for (int j = 0; j < matrix[index].length; j++) {
                rows += matrix[index][j];
                cols += matrix[j][index];
            }
            sum.setRowSum(rows);
            sum.setColSum(cols);
            return sum;
        });
    }

    public static class Sums {
        private int rowSum;
        private int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        public Sums() {
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }

        @Override
        public String toString() {
            return String.format("[%d, %d]", rowSum, colSum);
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
            return rowSum == sums.rowSum
                    && colSum == sums.colSum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rowSum, colSum);
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[][] array = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9},
        };
        System.out.println(Arrays.toString(RolColSum.sum(array)));
        System.out.println(Arrays.toString(RolColSum.asyncSum(array)));
    }
}