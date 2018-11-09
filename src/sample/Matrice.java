package sample;

import java.text.DecimalFormat;

public class Matrice {

    private String nom, demarche;
    private int m, n, coefficient;
    private double determinant;
    private double[][] matrice;

    public Matrice(String nom, int m, int n, String demarche) {
        this.nom = nom;
        this.demarche = demarche;
        this.m = m;
        this.n = n;
        this.coefficient = 1;
        this.determinant = 1;
        matrice = new double[m][n];
    }

    public Matrice(String nom, int m, String demarche) {
        this.nom = "identité";
        this.demarche = demarche;
        this.m = m;
        this.n = m;
        this.coefficient = 1;
        this.matrice = new double[m][m];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < m; j++) {
                if (i == j)
                    this.matrice[i][j] = 1;
                else
                    this.matrice[i][j] = 0;
            }
    }

    public Matrice transposer() {
        Matrice matriceTemp = new Matrice("Z", n, m, demarche);
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++) {
                matriceTemp.matrice[j][i] = this.matrice[i][j];
                matriceTemp.setDemarche(matriceTemp.getDemarche() + nom.toLowerCase() + (i + 1) + (j + 1) + " -> " + nom.toLowerCase() + (i + 1) + (j + 1) + "\n");
            }
        return matriceTemp;
    }

    public Matrice addition(Matrice matriceB) {
        Matrice matriceTemp = new Matrice("Z", m, n, demarche);
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++) {
                matriceTemp.getMatrice()[i][j] = matrice[i][j] + matriceB.getMatrice()[i][j];
                matriceTemp.setDemarche(matriceTemp.getDemarche() + matriceTemp.getNom().toLowerCase() + (i + 1) + (j + 1) + " = Addition de " + nom.toLowerCase() + (i + 1) + (j + 1) + " et " + matriceB.getNom().toLowerCase() + (i + 1) + (j + 1) + "\n");
            }
        return matriceTemp;
    }

    public Matrice soustraction(Matrice matriceB) {
        Matrice matriceTemp = new Matrice("Z", m, n, demarche);
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++) {
                matriceTemp.getMatrice()[i][j] = matrice[i][j] - matriceB.getMatrice()[i][j];
                matriceTemp.setDemarche(matriceTemp.getDemarche() + matriceTemp.getNom().toLowerCase() + (i + 1) + (j + 1) + " = Soustraction de " + nom.toLowerCase() + (i + 1) + (j + 1) + " et " + matriceB.getNom().toLowerCase() + (i + 1) + (j + 1) + "\n");
            }
        return matriceTemp;
    }

    public Matrice multiplication(double coefficient) {
        Matrice matriceTemp = new Matrice("Z", m, n, demarche);
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++) {
                matriceTemp.getMatrice()[i][j] = coefficient * matrice[i][j];
                matriceTemp.setDemarche(matriceTemp.getDemarche() + matriceTemp.getNom().toLowerCase() + (i + 1) + (j + 1) + " = Multiplication de " + nom.toLowerCase() + (i + 1) + (j + 1) + " par " + coefficient + "\n");
            }
        return matriceTemp;
    }

    public Matrice produit(Matrice matriceB) {
        Matrice matriceTemp = new Matrice("Z", m, matriceB.getN(), demarche);
        for (int i = 0; i < matriceTemp.getM(); i++)
            for (int j = 0; j < matriceTemp.getN(); j++)
                for (int k = 0; k < n; k++) {
                    matriceTemp.matrice[i][j] += matrice[i][k] * matriceB.matrice[k][j];
                    matriceTemp.setDemarche(matriceTemp.getDemarche() + matriceTemp.getNom().toLowerCase() + (i + 1) + (j + 1) + " = Addition de " + nom.toLowerCase() + (i + 1) + (j + 1) + " et (Multiplication de " + nom.toLowerCase() + (i + 1) + (k + 1) + " et " + matriceB.getNom().toLowerCase() + (k + 1) + (j + 1) + ")\n");
                }
        return matriceTemp;
    }

    public Matrice vectoriel(Matrice matriceB) {
        Matrice matriceTemp = new Matrice("Z", m, n, demarche);
        matriceTemp.getMatrice()[0][0] = matrice[0][1] * matriceB.getMatrice()[0][2] - matrice[0][2] * matriceB.getMatrice()[0][1];
        matriceTemp.setDemarche(matriceTemp.getDemarche() + matriceTemp.getNom().toLowerCase() + 1 + 1 + " = Calcul du cofacteur 2x2 c11\n");
        matriceTemp.getMatrice()[0][1] = matrice[0][2] * matriceB.getMatrice()[0][0] - matrice[0][0] * matriceB.getMatrice()[0][2];
        matriceTemp.setDemarche(matriceTemp.getDemarche() + matriceTemp.getNom().toLowerCase() + 1 + 2 + " = Calcul du cofacteur 2x2 c12\n");
        matriceTemp.getMatrice()[0][2] = matrice[0][0] * matriceB.getMatrice()[0][1] - matrice[0][1] * matriceB.getMatrice()[0][0];
        matriceTemp.setDemarche(matriceTemp.getDemarche() + matriceTemp.getNom().toLowerCase() + 1 + 3 + " = Calcul du cofacteur 2x2 c13\n");
        return matriceTemp;
    }

    public Matrice hadamard(Matrice matriceB) {
        Matrice matriceTemp = new Matrice("Z", m, n, demarche);
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++) {
                matriceTemp.getMatrice()[i][j] = matrice[i][j] * matriceB.getMatrice()[i][j];
                matriceTemp.setDemarche(matriceTemp.getDemarche() + matriceTemp.getNom().toLowerCase() + (i + 1) + (j + 1) + " = Multiplication de " + nom.toLowerCase() + (i + 1) + (j + 1) + " et " + matriceB.getNom().toLowerCase() + (i + 1) + (j + 1) + "\n");
            }
        return matriceTemp;
    }

    public Matrice tensoriel(Matrice matriceB) {
        Matrice matriceTemp = new Matrice("Z", m * matriceB.getM(), n * matriceB.getN(), demarche);
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                for (int k = 0; k < matriceB.getM(); k++)
                    for (int l = 0; l < matriceB.getN(); l++) {
                        matriceTemp.getMatrice()[i * matriceB.getM() + k][j * matriceB.getN() + l] = matrice[i][j] * matriceB.getMatrice()[k][l];
                        matriceTemp.setDemarche(matriceTemp.getDemarche() + matriceTemp.getNom().toLowerCase() + (i * matriceB.getM() + k + 1) + (j * matriceB.getN() + l + 1) + " = Multiplication de " + nom.toLowerCase() + (i + 1) + (k + 1) + " et " + matriceB.getNom().toLowerCase() + (k + 1) + (l + 1) + "\n");
                    }
        return matriceTemp;
    }

    public Matrice determinant() {
        Matrice matriceTemp = new Matrice(nom, m, n, demarche);
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                matriceTemp.getMatrice()[i][j] = matrice[i][j];
        if (matriceTemp.getM() == 1) {
            matriceTemp.setDeterminant(matriceTemp.getMatrice()[0][0]);
            matriceTemp.setDemarche(matriceTemp.getDemarche() + "det(" + nom + ") = " + nom.toLowerCase() + "11\n");
            return matriceTemp;
        } else if (matriceTemp.getM() == 2) {
            matriceTemp.setDeterminant((matriceTemp.getMatrice()[0][0] * matriceTemp.getMatrice()[1][1]) - (matriceTemp.getMatrice()[0][1] * matriceTemp.getMatrice()[1][0]));
            matriceTemp.setDemarche(matriceTemp.getDemarche() + "det(" + nom + ") = Soustraction de (Multiplication de " + nom.toLowerCase() + "11 par " + nom.toLowerCase() + "22) et (Multiplication de " + nom.toLowerCase() + "12 par " + nom.toLowerCase() + "21) \n");
            return matriceTemp;
        } else {
            double resultat = 1;
            Matrice[] matrices = {matriceTemp};
            matriceTemp = triangleSuperieur(matrices)[0];
            for (int i = 0; i < matriceTemp.getN(); i++)
                resultat *= matriceTemp.getMatrice()[i][i];
            matriceTemp.setDemarche(matriceTemp.getDemarche() + "det(" + nom + ") = Multiplication de la diagonale de la matrice " + nom);
            matriceTemp.setDeterminant(resultat * matrices[0].getCoefficient());
            return matriceTemp;
        }
    }

    public Matrice inverser() {
        DecimalFormat numberFormat = new DecimalFormat("0.00");
        Matrice matriceTemp = new Matrice("Z", m, n, demarche);
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                matriceTemp.getMatrice()[i][j] = matrice[i][j];
        Matrice[] matrices = {matriceTemp, new Matrice(nom, matriceTemp.getM(), demarche)};
        matrices = triangleSuperieur(matrices);
        matrices = triangleInferieur(matrices);
        for (int i = 0; i < matriceTemp.getM(); i++) {
            double diviseur = matrices[0].getMatrice()[i][i];
            for (int j = 0; j < matrices[0].getN(); j++)
                for (int k = 0; k < matrices.length; k++)
                    matrices[k].getMatrice()[i][j] /= diviseur;
            for (int l = 0; l < matrices.length; l++)
                matrices[l].setDemarche(matrices[l].getDemarche() + "Ligne " + (i + 1) + " -> (1/" + numberFormat.format(diviseur) + ") * Ligne " + (i + 1) + "\n");
        }
        return matrices[1];
    }

    private Matrice[] triangleSuperieur(Matrice[] matrices) {
        DecimalFormat numberFormat = new DecimalFormat("0.00");
        for (int i = 1; i < matrices[0].getM(); i++) {
            if (matrices[0].getMatrice()[i - 1][i - 1] == 0) {
                int found = 0;
                for (int j = i; j < matrices[0].getM(); j++) {
                    if (matrices[0].getMatrice()[j][i - 1] != 0 && found == 0)
                        found = j;
                }
                if (found == 0)
                    return matrices;
                else {
                    matrices[0].setCoefficient(matrices[0].getCoefficient() * -1);
                    for (int j = 0; j < matrices.length; j++) {
                        double[] temp = matrices[j].getMatrice()[i - 1];
                        matrices[j].getMatrice()[i - 1] = matrices[j].getMatrice()[found];
                        matrices[j].getMatrice()[found] = temp;
                    }
                    for (int l = 0; l < matrices.length; l++)
                        matrices[l].setDemarche(matrices[l].getDemarche() + "Ligne " + i + " <-> Ligne " + (found + 1) + "\n");
                }
            }
            for (int j = i; j < matrices[0].getN(); j++) {
                double multiplicateur = matrices[0].getMatrice()[j][i - 1] / matrices[0].getMatrice()[i - 1][i - 1];
                for (int k = i - 1; k < matrices[0].getN(); k++)
                    for (int l = 0; l < matrices.length; l++)
                        matrices[l].getMatrice()[j][k] = matrices[l].getMatrice()[j][k] - multiplicateur * matrices[l].getMatrice()[i - 1][k];
                for (int l = 0; l < matrices.length; l++)
                    matrices[l].setDemarche(matrices[l].getDemarche() + "Ligne " + (j + 1) + " -> Ligne " + (j + 1) + " - " + numberFormat.format(multiplicateur) + " * Ligne " + i + "\n");
            }
        }
        return matrices;
    }

    private Matrice[] triangleInferieur(Matrice[] matrices) {
        DecimalFormat numberFormat = new DecimalFormat("0.00");
        for (int i = matrices[0].getM() - 2; i > -1; i--) {
            if (matrices[0].getMatrice()[i + 1][i + 1] == 0) {
                int found = 0;
                for (int j = i; j > -1; j--) {
                    if (matrices[0].getMatrice()[j][i + 1] != 0 && found == 0)
                        found = j;
                }
                if (found == 0) {
                    return matrices;
                } else {
                    matrices[0].setCoefficient(matrices[0].getCoefficient() * -1);
                    for (int j = 0; j < matrices.length; j++) {
                        double[] temp = matrices[j].getMatrice()[i + 1];
                        matrices[j].getMatrice()[i + 1] = matrices[j].getMatrice()[found];
                        matrices[j].getMatrice()[found] = temp;
                    }
                    for (int l = 0; l < matrices.length; l++)
                        matrices[l].setDemarche(matrices[l].getDemarche() + "Ligne " + i + " <-> Ligne " + (found + 1) + "\n");
                }
            }
            for (int j = i; j > -1; j--) {
                double multiplicateur = matrices[0].getMatrice()[j][i + 1] / matrices[0].getMatrice()[i + 1][i + 1];
                for (int k = i + 1; k > -1; k--)
                    for (int l = 0; l < matrices.length; l++)
                        matrices[l].getMatrice()[j][k] = matrices[l].getMatrice()[j][k] - multiplicateur * matrices[l].getMatrice()[i + 1][k];
                for (int l = 0; l < matrices.length; l++)
                    matrices[l].setDemarche(matrices[l].getDemarche() + "Ligne " + (j + 1) + " -> Ligne " + (j + 1) + " - " + numberFormat.format(multiplicateur) + " * Ligne " + i + "\n");
            }
        }
        return matrices;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDemarche() {
        return demarche;
    }

    public void setDemarche(String demarche) {
        this.demarche = demarche;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        m = m;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        n = n;
    }

    public int getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }

    public double getDeterminant() {
        return determinant;
    }

    public void setDeterminant(double determinant) {
        this.determinant = determinant;
    }

    public double[][] getMatrice() {
        return matrice;
    }

    public void setMatrice(double[][] matrice) {
        this.matrice = matrice;
    }

}
