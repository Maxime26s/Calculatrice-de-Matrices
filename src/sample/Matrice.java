package sample;

public class Matrice {

    private String nom;
    private int m, n;
    private double[][] matrice;

    public Matrice(String nom, int m, int n) {
        this.nom = nom;
        this.m = m;
        this.n = n;
        matrice = new double[m][n];
    }

    public Matrice(int m) {
        this.nom = "identité";
        this.m = m;
        this.n = m;
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
        Matrice matriceTemp = new Matrice(nom, n, m);
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                matriceTemp.matrice[j][i] = this.matrice[i][j];
        return matriceTemp;
    }

    public double determinant() {
        Matrice matriceTemp = new Matrice("temp", m, n);
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                matriceTemp.getMatrice()[i][j] = matrice[i][j];
        if (matriceTemp.getM() == 1) {
            return matriceTemp.getMatrice()[0][0];
        } else if (matriceTemp.getM() == 2) {
            return (matriceTemp.getMatrice()[0][0] * matriceTemp.getMatrice()[1][1]) - (matriceTemp.getMatrice()[0][1] * matriceTemp.getMatrice()[1][0]);
        } else {
            double resultat = 1;
            Matrice[] matrices = {matriceTemp};
            matriceTemp = triangleSuperieur(matrices)[0];
            for (int i = 0; i < matriceTemp.getN(); i++)
                resultat *= matriceTemp.getMatrice()[i][i];
            return resultat;

        }
    }

    public Matrice addition(Matrice matriceB) {
        Matrice matriceTemp = new Matrice("temp", m, n);
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                matriceTemp.getMatrice()[i][j] = matrice[i][j] + matriceB.getMatrice()[i][j];
        return matriceTemp;
    }

    public Matrice soustraction(Matrice matriceB) {
        Matrice matriceTemp = new Matrice("temp", m, n);
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                matriceTemp.getMatrice()[i][j] = matrice[i][j] - matriceB.getMatrice()[i][j];
        return matriceTemp;
    }

    public Matrice multiplication(double coefficient) {
        Matrice matriceTemp = new Matrice("temp", m, n);
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                matriceTemp.getMatrice()[i][j] = coefficient * matrice[i][j];
        return matriceTemp;
    }

    public Matrice produit(Matrice matriceB) {
        Matrice matriceTemp = new Matrice("temp", m, matriceB.getN());
        for (int i = 0; i < matriceTemp.getM(); i++)
            for (int j = 0; j < matriceTemp.getN(); j++)
                for (int k = 0; k < n; k++)
                    matriceTemp.matrice[i][j] += matrice[i][k] * matriceB.matrice[k][j];
        return matriceTemp;
    }

    public Matrice vectoriel(Matrice matriceB) {
        Matrice matriceTemp = new Matrice("temp", m, n);
        matriceTemp.getMatrice()[0][0]=matrice[0][1]*matriceB.getMatrice()[0][2]-matrice[0][2]*matriceB.getMatrice()[0][1];
        matriceTemp.getMatrice()[0][1]=matrice[0][2]*matriceB.getMatrice()[0][0]-matrice[0][0]*matriceB.getMatrice()[0][2];
        matriceTemp.getMatrice()[0][2]=matrice[0][0]*matriceB.getMatrice()[0][1]-matrice[0][1]*matriceB.getMatrice()[0][0];
        return matriceTemp;
    }

    public Matrice hadamard(Matrice matriceB) {
        Matrice matriceTemp = new Matrice("temp", m, n);
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                matriceTemp.getMatrice()[i][j] = matrice[i][j] * matriceB.getMatrice()[i][j];
        return matriceTemp;
    }

    public Matrice tensoriel(Matrice matriceB) {
        Matrice matriceTemp = new Matrice("temp", m * matriceB.getM(), n * matriceB.getN()); //3x2 1x2 = 3x4
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                for (int k = 0; k < matriceB.getM(); k++)
                    for (int l = 0; l < matriceB.getN(); l++)
                        matriceTemp.getMatrice()[i * matriceB.getM() + k][j * matriceB.getN() + l] = matrice[i][j] * matriceB.getMatrice()[k][l];
        return matriceTemp;
    }

    public Matrice inverser() {
        Matrice matriceTemp = new Matrice("temp", m, n);
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                matriceTemp.getMatrice()[i][j] = matrice[i][j];
        Matrice[] matrices = {matriceTemp, new Matrice(matriceTemp.getM())};
        matrices = triangleSuperieur(matrices);
        matrices = triangleInferieur(matrices);
        for (int i = 0; i < matriceTemp.getM(); i++) {
            double diviseur = matrices[0].getMatrice()[i][i];
            for (int j = 0; j < matrices[0].getN(); j++)
                for (int k = 0; k < matrices.length; k++)
                    matrices[k].getMatrice()[i][j] /= diviseur;
        }
        return matrices[1];
    }

    public double allo() {
        Matrice matriceTemp = new Matrice("temp", m, n);
        matriceTemp.setMatrice(matrice);
        if (matriceTemp.getM() == 1) {
            return matriceTemp.getMatrice()[0][0];
        } else if (matriceTemp.getM() == 2) {
            return (matriceTemp.getMatrice()[0][0] * matriceTemp.getMatrice()[1][1]) - (matriceTemp.getMatrice()[0][1] * matriceTemp.getMatrice()[1][0]);
        } else {
            double resultat = 1;
            for (int i = 1; i < matriceTemp.getM(); i++) {
                if (matriceTemp.getMatrice()[i - 1][i - 1] == 0) {
                    int found = 0;
                    for (int j = i; j < matriceTemp.getM(); j++) {
                        if (matriceTemp.getMatrice()[j][i - 1] != 0 && found == 0)
                            found = j;
                    }
                    if (found == 0)
                        return 0;
                    else {
                        double[] temp = matriceTemp.getMatrice()[i - 1];
                        matriceTemp.getMatrice()[i - 1] = matriceTemp.getMatrice()[found];
                        matriceTemp.getMatrice()[found] = temp;
                    }
                }
                for (int j = i; j < matriceTemp.getN(); j++) {
                    double multiplicateur = matriceTemp.getMatrice()[j][i - 1] / matriceTemp.getMatrice()[i - 1][i - 1];
                    for (int k = i - 1; k < matriceTemp.getN(); k++)
                        matriceTemp.getMatrice()[j][k] = matriceTemp.getMatrice()[j][k] - multiplicateur * matriceTemp.getMatrice()[i - 1][k];
                }
            }
            for (int i = 0; i < matriceTemp.getN(); i++)
                resultat *= matriceTemp.getMatrice()[i][i];
            return resultat;

        }
    }

    private Matrice[] triangleSuperieur(Matrice[] matrices) {
        for (int i = 1; i < matrices[0].getM(); i++) {
            if (matrices[0].getMatrice()[i - 1][i - 1] == 0) {
                int found = 0;
                for (int j = i; j < matrices[0].getM(); j++) {
                    if (matrices[0].getMatrice()[j][i - 1] != 0 && found == 0)
                        found = j;
                }
                if (found == 0) {
                    matrices[0].setNom("zero");
                    return matrices;
                } else {
                    for (int j = 0; j < matrices.length; j++) {
                        double[] temp = matrices[j].getMatrice()[i - 1];
                        matrices[j].getMatrice()[i - 1] = matrices[j].getMatrice()[found];
                        matrices[j].getMatrice()[found] = temp;
                    }
                }
            }
            for (int j = i; j < matrices[0].getN(); j++) {
                double multiplicateur = matrices[0].getMatrice()[j][i - 1] / matrices[0].getMatrice()[i - 1][i - 1];
                for (int k = i - 1; k < matrices[0].getN(); k++)
                    for (int l = 0; l < matrices.length; l++)
                        matrices[l].getMatrice()[j][k] = matrices[l].getMatrice()[j][k] - multiplicateur * matrices[l].getMatrice()[i - 1][k];
            }
        }
        return matrices;
    }

    private Matrice[] triangleInferieur(Matrice[] matrices) {
        for (int i = matrices[0].getM() - 2; i > -1; i--) {
            if (matrices[0].getMatrice()[i + 1][i + 1] == 0) {
                int found = 0;
                for (int j = i; j > -1; j--) {
                    if (matrices[0].getMatrice()[j][i + 1] != 0 && found == 0)
                        found = j;
                }
                if (found == 0) {
                    matrices[0].setNom("zero");
                    return matrices;
                } else {
                    for (int j = 0; j < matrices.length; j++) {
                        double[] temp = matrices[j].getMatrice()[i + 1];
                        matrices[j].getMatrice()[i + 1] = matrices[j].getMatrice()[found];
                        matrices[j].getMatrice()[found] = temp;
                    }

                }
            }
            for (int j = i; j > -1; j--) {
                double multiplicateur = matrices[0].getMatrice()[j][i + 1] / matrices[0].getMatrice()[i + 1][i + 1];
                for (int k = i + 1; k > -1; k--)
                    for (int l = 0; l < matrices.length; l++)
                        matrices[l].getMatrice()[j][k] = matrices[l].getMatrice()[j][k] - multiplicateur * matrices[l].getMatrice()[i + 1][k];
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

    public double[][] getMatrice() {
        return matrice;
    }

    public void setMatrice(double[][] matrice) {
        this.matrice = matrice;
    }

}
