package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * This project solves classic 0/1 knapsack problem with dynamic programming method.
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter number of items:\n");
        int itemCount = scanner.nextInt();

        int[][] weightAndProfit = new int[itemCount][2];
        System.out.println("Enter weight and profit of each item: \n");
        for (int i = 0; i < itemCount; i++) {
            for (int j = 0; j < 2; j++) {
                weightAndProfit[i][j] = scanner.nextInt();
            }
        }

        System.out.println("Your list: [Weight][Profit]: " + Arrays.deepToString(weightAndProfit));

        System.out.println("Enter knapsack weight:\n");
        int knapsackWeight = scanner.nextInt();

        // sort weightAndProfit
        sortOnWeight(weightAndProfit);

        // generate knapsack matrix
        int[][] knapsackMatrix = generateKnapsackMatrix(weightAndProfit, knapsackWeight);

//        System.out.println("knapsackMatrix: " + Arrays.deepToString(knapsackMatrix));

        System.out.println("Max profit is: " +
                knapsackMatrix[knapsackMatrix.length - 1][knapsackMatrix[knapsackMatrix.length - 1].length - 1]);

        // get list of selected items
        List<Integer> selectedWeightAndProfitIndex = getListOfSelectedItemsIndex(weightAndProfit, knapsackMatrix);

        System.out.println("Selected Items: ");
        selectedWeightAndProfitIndex.forEach(i -> System.out.println(Arrays.toString(weightAndProfit[i])));
    }

    private static void sortOnWeight(int[][] weightAndProfit) {
        int index;
        int[] buffer;
        for (int i = 0; i < weightAndProfit.length - 1; i++) {
            index = i;
            buffer = weightAndProfit[index];
            for (int j = i + 1; j < weightAndProfit.length; j++) {
                if (buffer[0] > weightAndProfit[j][0]) {
                    index = j;
                    buffer = weightAndProfit[j];
                }
            }
            if (index != i) {
                buffer = weightAndProfit[i];
                weightAndProfit[i] = weightAndProfit[index];
                weightAndProfit[index] = buffer;
            }
        }
    }

    private static int[][] generateKnapsackMatrix(int[][] weightAndProfit, int knapsackWeight) {
        int[][] knapsackMatrix = new int[weightAndProfit.length + 1][knapsackWeight + 1];

        // initialize all elements of matrix to 0
        for (int i = 0; i <= weightAndProfit.length; i++) {
            for (int j = 0; j <= knapsackWeight; j++) {
                knapsackMatrix[i][j] = 0;
            }
        }

        for (int i = 1; i <= weightAndProfit.length; i++) {
            for (int j = 1; j <= knapsackWeight; j++) {
                if (weightAndProfit[i - 1][0] > j) {
                    knapsackMatrix[i][j] = knapsackMatrix[i - 1][j];
                } else {
                    knapsackMatrix[i][j] = Math.max(knapsackMatrix[i - 1][j],
                            weightAndProfit[i - 1][1] + knapsackMatrix[i - 1][j - weightAndProfit[i - 1][0]]);
                }
            }
        }

        return knapsackMatrix;
    }

    private static List<Integer> getListOfSelectedItemsIndex(int[][] weightAndProfit, int[][] knapsackMatrix) {
        List<Integer> selectedWeightAndProfitIndex = new ArrayList<>();
        int iPointer = knapsackMatrix.length - 1;
        int jPointer = knapsackMatrix[knapsackMatrix.length - 1].length - 1;

        while (jPointer != 0) {
            if (knapsackMatrix[iPointer][jPointer] == knapsackMatrix[iPointer - 1][jPointer]) {
                iPointer = iPointer - 1;
            }
            if (knapsackMatrix[iPointer][jPointer] > knapsackMatrix[iPointer - 1][jPointer]) {
                selectedWeightAndProfitIndex.add(iPointer - 1);
                jPointer = jPointer - weightAndProfit[iPointer - 1][0];
                iPointer = iPointer - 1;
            }
        }

        return selectedWeightAndProfitIndex;
    }

}
