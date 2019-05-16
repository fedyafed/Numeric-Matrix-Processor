package processor;

import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {
        int choice;
        while (true) {
            System.out.println("" +
                    "1. Add matrices\n" +
                    "2. Multiply matrix to a constant\n" +
                    "3. Multiply matrices\n" +
                    "4. Transpose matrix\n" +
                    "5. Calculate a determinant\n" +
                    "6. Inverse matrix\n" +
                    "0. Exit\n" +
                    "Your choice:");
            choice = scanner.nextInt();

            int additionalChoice = 0;
            if (choice == 0) {
                break;
            } else if (choice == 4) {
                System.out.println("" +
                        "1. Main diagonal\n" +
                        "2. Side diagonal\n" +
                        "3. Vertical line\n" +
                        "4. Horizontal line\n" +
                        "Your chouce:");
                additionalChoice = scanner.nextInt();
            }

            try {
                double[][] a = inputMatrix("first");
                double[][] b;
                double[][] result = null;
                double determinant = 0;
                switch (choice) {
                    case 1:
                        b = inputMatrix("second");
                        result = addMatrix(a, b);
                        break;
                    case 2:
                        double k = scanner.nextDouble();
                        result = multiplyMatrix(a, k);
                        break;
                    case 3:
                        b = inputMatrix("second");
                        result = multiplyMatrix(a, b);
                        break;
                    case 4:
                        result = transposeMatrix(a, additionalChoice);
                        break;
                    case 5:
                        determinant = matrixDeterminant(a);
                        break;
                    case 6:
                        result = inverseMatrix(a);
                        break;
                    default:
                        continue;
                }

                if (result != null) {
                    printMatrix(result);
                } else {
                    System.out.println("The determinant is: \n          " + determinant);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }

    private static double[][] inverseMatrix(double[][] a) {
        if (a.length != a[0].length) {
            throw new RuntimeException("ERROR");
        }
        double[][] result = new double[a.length][];
        for (int i = 0; i < a.length; i++) {
            result[i] = new double[a[i].length];
            for (int j = 0; j < a[i].length; j++) {
                result[i][j] = matrixCofactor(a, i, j);
            }
        }

        result = mainTransposeMatrix(result);
        return multiplyMatrix(result, 1 / matrixDeterminant(a));
    }

    private static double matrixDeterminant(double[][] a) {
        if (a.length != a[0].length) {
            throw new RuntimeException("ERROR");
        }
        if (a.length == 1) {
            return a[0][0];
        }

        double result = 0;
        for (int i = 0; i < a.length; i++) {
            result += a[i][0] * matrixCofactor(a, i, 0);
        }
        return result;
    }

    private static double matrixCofactor(double[][] a, int i, int j) {
        int sign = (i + j) % 2 == 0 ? 1 : -1;
        return sign * minor(a, i, j);
    }

    private static double minor(double[][] a, int i, int j) {
        return matrixDeterminant(submatrix(a, i, j));
    }

    private static double[][] transposeMatrix(double[][] a, int choice) {
        if (a.length != a[0].length) {
            throw new RuntimeException("ERROR");
        }
        switch (choice) {
            case 1:
                return mainTransposeMatrix(a);
            case 2:
                return sideTransposeMatrix(a);
            case 3:
                return verticalTransposeMatrix(a);
            case 4:
                return horizontalTransposeMatrix(a);
            default:
                throw new RuntimeException("ERROR");
        }
    }

    private static double[][] horizontalTransposeMatrix(double[][] a) {
        double[][] result = new double[a.length][];
        for (int i = 0; i < a.length; i++) {
            result[i] = new double[a[i].length];
            for (int j = 0; j < a[0].length; j++) {
                result[i][j] = a[a.length - i - 1][j];
            }
        }
        return result;
    }

    private static double[][] verticalTransposeMatrix(double[][] a) {
        double[][] result = new double[a.length][];
        for (int i = 0; i < a.length; i++) {
            result[i] = new double[a[i].length];
            for (int j = 0; j < a[0].length; j++) {
                result[i][j] = a[i][a[0].length - j - 1];
            }
        }
        return result;
    }

    private static double[][] sideTransposeMatrix(double[][] a) {
        double[][] result = new double[a[0].length][];
        for (int i = 0; i < a.length; i++) {
            result[i] = new double[a.length];
            for (int j = 0; j < a[0].length; j++) {
                result[i][j] = a[a.length - j - 1][a[0].length - i - 1];
            }
        }
        return result;
    }

    private static double[][] mainTransposeMatrix(double[][] a) {
        double[][] result = new double[a[0].length][];
        for (int i = 0; i < a.length; i++) {
            result[i] = new double[a.length];
            for (int j = 0; j < a[0].length; j++) {
                result[i][j] = a[j][i];
            }
        }
        return result;
    }

    private static double[][] inputMatrix(String name) {
        System.out.println("Enter size of " + name + " matrix:");

        int n = scanner.nextInt();
        int m = scanner.nextInt();

        System.out.println("Enter " + name + " matrix:");

        double[][] matrix = new double[n][];
        for (int i = 0; i < n; i++) {
            matrix[i] = new double[m];
            for (int j = 0; j < m; j++) {
                matrix[i][j] = scanner.nextDouble();
            }
        }
        return matrix;
    }

    private static double[][] submatrix(double[][] matrix, int m, int n) {
        double[][] result = new double[matrix.length - 1][];
        for (int i = 0; i < matrix.length; i++) {
            if (i == m) {
                continue;
            }
            int row = i < m ? i : i - 1;
            result[row] = new double[matrix[i].length - 1];
            for (int j = 0; j < matrix[i].length; j++) {
                if (j == n) {
                    continue;
                }
                int column = j < n ? j : j - 1;
                result[row][column] = matrix[i][j];
            }
        }
        return result;
    }

    private static void printMatrix(double[][] matrix) {
        for (double[] row : matrix) {
            for (double value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }

    private static double[][] addMatrix(double[][] a, double[][] b) {
        if (a.length != b.length || a.length > 0 && a[0].length != b[0].length) {
            throw new RuntimeException("ERROR");
        }
        double[][] result = new double[a.length][];
        for (int i = 0; i < a.length; i++) {
            result[i] = new double[a[i].length];
            for (int j = 0; j < a[i].length; j++) {
                result[i][j] = a[i][j] + b[i][j];
            }
        }
        return result;
    }

    private static double[][] multiplyMatrix(double[][] a, double k) {
        double[][] result = new double[a.length][];
        for (int i = 0; i < a.length; i++) {
            result[i] = new double[a[i].length];
            for (int j = 0; j < a[i].length; j++) {
                result[i][j] = a[i][j] * k;
            }
        }
        return result;
    }

    private static double[][] multiplyMatrix(double[][] a, double[][] b) {
        if (a.length > 0 && a[0].length != b.length) {
            throw new RuntimeException("ERROR");
        }
        double[][] result = new double[a.length][];
        for (int i = 0; i < a.length; i++) {
            result[i] = new double[b[0].length];
            for (int k = 0; k < b[0].length; k++) {
                for (int j = 0; j < b.length; j++) {
                    result[i][k] += a[i][j] * b[j][k];
                }
            }
        }
        return result;
    }
}
