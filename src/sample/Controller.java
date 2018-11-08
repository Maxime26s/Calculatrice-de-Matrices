package sample;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class Controller {
    @FXML
    private Spinner matriceAx, matriceAy, matriceBx, matriceBy; //Auto-create Spinner? Spinner de nombre de matrice?
    @FXML
    private HBox hBoxA1, hBoxB1, hBoxA2, hBoxB2; //VBox et auto-create HBox?
    @FXML
    private ToggleGroup toggleGroup;
    private Matrice matriceA, matriceB, matriceResultatA, matriceResultatB; //ArrayList?
    private boolean matriceExistante=false;

    public void continuer() {
        hBoxA1.getChildren().clear();
        hBoxB1.getChildren().clear();
        matriceExistante=false;
        matriceA = new Matrice("A", (int) matriceAx.getValueFactory().getValue(), (int) matriceAy.getValueFactory().getValue()); //nom = (char)(i+65)?
        matriceB = new Matrice("B", (int) matriceBx.getValueFactory().getValue(), (int) matriceBy.getValueFactory().getValue()); //nom = (char)(i+65)?
        creerMatrice(matriceA);
        creerMatrice(matriceB);
        matriceExistante=true;
        afficherMatrice(matriceA, hBoxA1);
        afficherMatrice(matriceB, hBoxB1);
    }

    private void creerMatrice(Matrice matrice) {
        for (int i = 0; i < matrice.getM(); i++)
            for (int j = 0; j < matrice.getN(); j++) {
                TextInputDialog alerteValeur = new TextInputDialog("Entrez ici");
                alerteValeur.setTitle("Valeur");
                alerteValeur.setHeaderText("Entrez la valeur à la position " + (i+1) + "-" + (j+1) + " de la matrice " + matrice.getNom());
                String valeur = alerteValeur.showAndWait().get();
                boolean number = true;
                for (int k = 0; k < valeur.length(); k++)
                    if (!Character.isDigit(valeur.charAt(k)))
                        number = false;
                if (number)
                    matrice.getMatrice()[i][j] = Integer.parseInt(valeur);
                else {
                    Alert alerte = new Alert(Alert.AlertType.INFORMATION);
                    alerte.setTitle("ERREUR");
                    alerte.setHeaderText("La valeur entrée est invalide, veuillez réessayer");
                    alerte.showAndWait();
                    j--;
                }
            }
    }

    private void afficherMatrice(Matrice matrice, HBox hBox) {
        ArrayList<VBox> vBoxArrayList = new ArrayList<>();
        for (int i = 0; i < matrice.getN(); i++) {
            vBoxArrayList.add(new VBox());
            vBoxArrayList.get(i).setAlignment(Pos.CENTER);
            hBox.getChildren().add(vBoxArrayList.get(i));
        }
        for (int i = 0; i < matrice.getM(); i++) {
            for (int j = 0; j < matrice.getN(); j++) {
                BetterButton button = new BetterButton(i, j);
                button.setPrefHeight(50);
                button.setPrefWidth(50);
                button.setAlignment(Pos.CENTER);
                button.setText(Double.toString(matrice.getMatrice()[i][j]));
                button.setOnAction(event -> changerValeur(button,matrice));
                vBoxArrayList.get(j).getChildren().add(button);
            }
        }
    }

    public void changerValeur(BetterButton button, Matrice matrice) {
        TextInputDialog alerteValeur = new TextInputDialog("Entrez ici");
        alerteValeur.setTitle("Valeur");
        alerteValeur.setHeaderText("Entrez la valeur à la position " + (button.getX()+1) + "-" + (button.getY()+1) + " de la matrice " + matrice.getNom());
        String valeur = alerteValeur.showAndWait().get();
        boolean number = true;
        for (int k = 0; k < valeur.length(); k++)
            if (!Character.isDigit(valeur.charAt(k)))
                number = false;
        if (number) {
            matrice.getMatrice()[button.getX()][button.getY()] = Integer.parseInt(valeur);
            button.setText(valeur);
        }
        else {
            Alert alerte = new Alert(Alert.AlertType.INFORMATION);
            alerte.setTitle("ERREUR");
            alerte.setHeaderText("La valeur entrée est invalide, veuillez réessayer");
            alerte.showAndWait();
            changerValeur(button,matrice);
        }
    }

    public void calculer(){
        if (toggleGroup.getSelectedToggle()!=null&&matriceExistante)
            switch (toggleGroup.getSelectedToggle().getUserData().toString()){
                case "addition":
                    if (matriceA.getM()==matriceB.getM()&&matriceA.getN()==matriceB.getN())
                        resultatsMatrice(matriceA.addition(matriceB), hBoxA2);
                    break;
                case "soustraction":
                    if (matriceA.getM()==matriceB.getM()&&matriceA.getN()==matriceB.getN())
                        resultatsMatrice(matriceA.soustraction(matriceB), hBoxA2);
                    break;
                case "multiplication":
                    break;
                case "puissance":
                    break;
                case "transposition":
                    matriceResultatA = matriceA.transposer();
                    matriceResultatB = matriceB.transposer();
                    resultatsMatrice(matriceResultatA, hBoxA2);
                    resultatsMatrice(matriceResultatB, hBoxB2);
                    break;
                case "inversion":
                    break;
                case "matriciel":
                    break;
                case "vectoriel":
                    break;
                case "hadamard":
                    break;
                case "tensoriel":
                    break;
                case "determinant":
                    if(matriceA.getM()==matriceA.getN())
                        resultatsnombre(matriceA.determinant(),hBoxA2);
                    break;
            }
        else{
            String message="";
            if (toggleGroup.getSelectedToggle()==null)
                message+="Aucune opération séléctionnée\n";
            if (!matriceExistante)
                message+="Aucune matrice séléctionnée";
            Alert alerte = new Alert(Alert.AlertType.INFORMATION);
            alerte.setTitle("ERREUR");
            alerte.setHeaderText(message);
            alerte.showAndWait();
        }
    }

    private void resultatsMatrice(Matrice matrice, HBox hBox){
        hBox.getChildren().clear();
        ArrayList<VBox> vBoxArrayList = new ArrayList<>();
        for (int i = 0; i < matrice.getN(); i++) {
            vBoxArrayList.add(new VBox());
            vBoxArrayList.get(i).setAlignment(Pos.CENTER);
            hBox.getChildren().add(vBoxArrayList.get(i));
        }
        for (int i = 0; i < matrice.getM(); i++) {
            for (int j = 0; j < matrice.getN(); j++) {
                Button button = new Button();
                button.setPrefHeight(50);
                button.setPrefWidth(50);
                button.setAlignment(Pos.CENTER);
                button.setText(Double.toString(matrice.getMatrice()[i][j]));
                vBoxArrayList.get(j).getChildren().add(button);
            }
        }
    }
    private void resultatsnombre(double nombre, HBox hBox){
        hBox.getChildren().clear();
        Button button = new Button();
        button.setPrefHeight(50);
        button.setPrefWidth(50);
        button.setAlignment(Pos.CENTER);
        button.setText(Double.toString(nombre));
        hBox.getChildren().add(button);
    }

    public void clearManipulation(){
        hBoxA1.getChildren().clear();
        hBoxB1.getChildren().clear();
        matriceA = null;
        matriceB = null;
    }

    public void clearResultat(){
        hBoxA2.getChildren().clear();
        hBoxB2.getChildren().clear();
        matriceResultatA = null;
        matriceResultatB = null;
    }
}
