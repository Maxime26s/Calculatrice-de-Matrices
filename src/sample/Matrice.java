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

    public Matrice transposer() {
        Matrice transpose = new Matrice(nom, n, m);
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                transpose.matrice[j][i] = this.matrice[i][j];
        return transpose;
    }

    public double determinant() {
        if (m == 1) {
            return matrice[0][0];
        } else if (m == 2) {
            return (matrice[0][0] * matrice[1][1]) - (matrice[0][1] * matrice[1][0]);
        } else {
            double resultat = 1;
            for (int i = 1; i < m; i++) {
                if (matrice[i - 1][i - 1] == 0) {
                    int found = 0;
                    for (int j = i; j < m; j++) {
                        if (matrice[j][i - 1] != 0 && found == 0)
                            found = j;
                    }
                    if (found == 0)
                        return 0;
                    else {
                        double[] temp = matrice[i - 1];
                        matrice[i - 1] = matrice[found];
                        matrice[found] = temp;
                    }
                }
                for (int j = i; j < n; j++) {
                    double multiplicateur = matrice[j][i - 1] / matrice[i - 1][i - 1];
                    for (int k = i - 1; k < n; k++)
                        matrice[j][k] = matrice[j][k] - multiplicateur * matrice[i - 1][k];
                }
            }
            for (int i = 0; i < n; i++)
                resultat *= matrice[i][i];
            return resultat;

        }
    }

    public Matrice addition(Matrice matriceB) {
        Matrice matriceTemp = new Matrice("temp", m, n);
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                matriceTemp.getMatrice()[i][j]=matrice[i][j]+matriceB.getMatrice()[i][j];
        return matriceTemp;
    }

    public Matrice soustraction(Matrice matriceB) {
        Matrice matriceTemp = new Matrice("temp", m, n);
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                matriceTemp.getMatrice()[i][j]=matrice[i][j]-matriceB.getMatrice()[i][j];
        return matriceTemp;
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
