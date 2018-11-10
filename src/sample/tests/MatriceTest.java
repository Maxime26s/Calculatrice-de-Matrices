package sample.tests;

import sample.Matrice;

import java.text.DecimalFormat;

import static org.junit.jupiter.api.Assertions.*;

class MatriceTest {
    Matrice a, b, c, d, e, f;
    DecimalFormat decimalFormat = new DecimalFormat("0.0");

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        a = new Matrice("3x3", 3, 3, "");
        double[][] aDouble = {{4, 3, 7}, {3, 1, 7}, {5, 9, 9}};
        a.setMatrice(aDouble);

        b = new Matrice("1x3", 1, 3, "");
        double[][] bDouble = {{4, 3, 7}};
        b.setMatrice(bDouble);

        c = new Matrice("1x3", 1, 3, "");
        double[][] cDouble = {{3, 1, 7}};
        c.setMatrice(cDouble);

        d = new Matrice("2x2", 2, 2, "");
        double[][] dDouble = {{4, 3}, {3, 1}};
        d.setMatrice(dDouble);

        e = new Matrice("2x2", 2, 2, "");
        double[][] eDouble = {{3, 1}, {5, 9}};
        e.setMatrice(eDouble);

        f = new Matrice("1x1", 1, 1, "");
        double[][] fDouble = {{4}};
        f.setMatrice(fDouble);
    }

    @org.junit.jupiter.api.Test
    void transposer() {
        double[][] reponse = {{4, 3, 5}, {3, 1, 9}, {7, 7, 9}};
        Matrice temp = a.transposer();
        for (int i = 0; i < reponse.length; i++)
            for (int j = 0; j < reponse[i].length; j++)
                assertEquals(reponse[i][j], temp.getMatrice()[i][j]);
    }

    @org.junit.jupiter.api.Test
    void addition() {
        double[][] reponse = {{8, 6, 14}, {6, 2, 14}, {10, 18, 18}};
        Matrice temp = a.addition(a);
        for (int i = 0; i < reponse.length; i++)
            for (int j = 0; j < reponse[i].length; j++)
                assertEquals(reponse[i][j], temp.getMatrice()[i][j]);
    }

    @org.junit.jupiter.api.Test
    void soustraction() {
        double[][] reponse = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
        Matrice temp = a.soustraction(a);
        for (int i = 0; i < reponse.length; i++)
            for (int j = 0; j < reponse[i].length; j++)
                assertEquals(reponse[i][j], temp.getMatrice()[i][j]);
    }

    @org.junit.jupiter.api.Test
    void multiplication() {
        double[][] reponse = {{12, 9, 21}, {9, 3, 21}, {15, 27, 27}};
        Matrice temp = a.multiplication(3);
        for (int i = 0; i < reponse.length; i++)
            for (int j = 0; j < reponse[i].length; j++)
                assertEquals(reponse[i][j], temp.getMatrice()[i][j]);
    }

    @org.junit.jupiter.api.Test
    void produit() {
        double[][] reponse = {{60, 78, 112}, {50, 73, 91}, {92, 105, 179}};
        Matrice temp = a.produit(a);
        for (int i = 0; i < reponse.length; i++)
            for (int j = 0; j < reponse[i].length; j++)
                assertEquals(reponse[i][j], temp.getMatrice()[i][j]);
    }

    @org.junit.jupiter.api.Test
    void vectoriel() {
        double[][] reponse = {{14, -7, -5}};
        Matrice temp = b.vectoriel(c);
        for (int i = 0; i < reponse.length; i++)
            for (int j = 0; j < reponse[i].length; j++)
                assertEquals(reponse[i][j], temp.getMatrice()[i][j]);
    }

    @org.junit.jupiter.api.Test
    void hadamard() {
        double[][] reponse = {{12, 3}, {15, 9}};
        Matrice temp = d.hadamard(e);
        for (int i = 0; i < reponse.length; i++)
            for (int j = 0; j < reponse[i].length; j++)
                assertEquals(reponse[i][j], temp.getMatrice()[i][j]);
    }

    @org.junit.jupiter.api.Test
    void tensoriel() {
        double[][] reponse = {{12, 4, 9, 3}, {20, 36, 15, 27}, {9, 3, 3, 1}, {15, 27, 5, 9}};
        Matrice temp = d.tensoriel(e);
        for (int i = 0; i < reponse.length; i++)
            for (int j = 0; j < reponse[i].length; j++)
                assertEquals(reponse[i][j], temp.getMatrice()[i][j]);
    }

    @org.junit.jupiter.api.Test
    void determinant() {
        double reponse1 = 4;
        double reponse2 = -5;
        double reponse3 = -38;
        assertEquals(reponse1, f.determinant().getDeterminant()); //if 1x1
        assertEquals(reponse2, d.determinant().getDeterminant()); //if 2x2
        assertEquals(reponse3, a.determinant().getDeterminant()); //if 3x3 et +
    }

    @org.junit.jupiter.api.Test
    void inverser() {
        double[][] reponse = {{-0.2, 0.6}, {0.6, -0.8}};
        Matrice temp = d.inverser();
        for (int i = 0; i < reponse.length; i++)
            for (int j = 0; j < reponse[i].length; j++)
                assertEquals(reponse[i][j], (double) Math.round(temp.getMatrice()[i][j] * 100) / 100);
    }
}