package sample;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.print.PageLayout;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Controller {
    @FXML
    private Spinner matriceX, matriceY;
    @FXML
    private HBox hBoxManipulation, hBoxResultat;
    @FXML
    private ToggleGroup toggleGroup;
    @FXML
    private RadioButton addition, soustraction, multiplication, puissance, transposition, inversion, matriciel, vectoriel, hadamard, tensoriel, determinant;
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab tabSize, tabResultat;
    private ArrayList<Matrice> matriceArrayList = new ArrayList<>();
    private DecimalFormat numberFormat = new DecimalFormat("0.00");

    public void ajouter() {
        matriceArrayList.add(new Matrice(Character.toString((char) (65 + matriceArrayList.size())), (int) matriceX.getValueFactory().getValue(), (int) matriceY.getValueFactory().getValue()));
        creerMatrice(matriceArrayList.get(matriceArrayList.size() - 1));
        afficherMatrice(matriceArrayList.get(matriceArrayList.size() - 1));
        test();
    }

    private void creerMatrice(Matrice matrice) {
        for (int i = 0; i < matrice.getM(); i++)
            for (int j = 0; j < matrice.getN(); j++) {
                TextInputDialog alerteValeur = new TextInputDialog("Entrez ici");
                alerteValeur.setTitle("Valeur");
                alerteValeur.setHeaderText("Entrez la valeur à la position " + (i + 1) + "-" + (j + 1) + " de la matrice " + matrice.getNom());
                String valeur = alerteValeur.showAndWait().get();
                try {
                    matrice.getMatrice()[i][j] = Double.parseDouble(valeur);
                } catch (Exception e) {
                    Alert alerte = new Alert(Alert.AlertType.INFORMATION);
                    alerte.setTitle("ERREUR");
                    alerte.setHeaderText("La valeur entrée est invalide, veuillez réessayer");
                    alerte.showAndWait();
                    j--;
                }
            }
    }

    private void afficherMatrice(Matrice matrice) {
        HBox hBox = new HBox();
        hBoxManipulation.getChildren().add(hBox);
        ArrayList<VBox> vBoxArrayList = new ArrayList<>();
        for (int i = 0; i < matrice.getN(); i++) {
            vBoxArrayList.add(new VBox());
            vBoxArrayList.get(i).setAlignment(Pos.CENTER);
            hBox.getChildren().add(vBoxArrayList.get(i));
        }
        for (int i = 0; i < matrice.getM(); i++) {
            for (int j = 0; j < matrice.getN(); j++) {
                BetterButton button = new BetterButton(i, j);
                button.setMinHeight(50);
                button.setMinWidth(50);
                button.setMaxHeight(50);
                button.setMaxWidth(50);
                button.setAlignment(Pos.CENTER);
                button.setText(Double.toString(matrice.getMatrice()[i][j]));
                button.setOnAction(event -> changerValeur(button, matrice));
                vBoxArrayList.get(j).getChildren().add(button);
            }
        }
    }

    private void test() {
        boolean error = false;
        RadioButton[] test1 = {multiplication, transposition};
        RadioButton[] test2 = {puissance, determinant, inversion};
        RadioButton[] test3 = {addition, soustraction, hadamard};
        if (matriceArrayList.size() > 1)
            for (int i = 0; i < test1.length; i++) {
                test1[i].setDisable(true);
                if (test1[i].isSelected())
                    test1[i].setSelected(false);
            }
        else
            for (int i = 0; i < test1.length; i++)
                test1[i].setDisable(false);
        if (matriceArrayList.size() > 1 || matriceArrayList.get(0).getM() != matriceArrayList.get(0).getN())
            for (int i = 0; i < test2.length; i++) {
                test2[i].setDisable(true);
                if (test2[i].isSelected())
                    test2[i].setSelected(false);
            }
        else
            for (int i = 0; i < test2.length; i++)
                test2[i].setDisable(false);
        for (int i = 0; (i < matriceArrayList.size() - 1 || matriceArrayList.size() < 2) && !error; i++)
            if (matriceArrayList.size() < 2 || matriceArrayList.get(i).getM() != matriceArrayList.get(i + 1).getM() || matriceArrayList.get(i).getN() != matriceArrayList.get(i + 1).getN())
                error = true;
        if (error) {
            for (int i = 0; i < test3.length; i++) {
                test3[i].setDisable(true);
                if (test3[i].isSelected())
                    test3[i].setSelected(false);
            }
            error = false;
        } else
            for (int i = 0; i < test3.length; i++)
                test3[i].setDisable(false);
        if (matriceArrayList.size() < 2) {
            tensoriel.setDisable(true);
            if (tensoriel.isSelected())
                tensoriel.setSelected(false);
        } else
            tensoriel.setDisable(false);
        for (int i = 0; (i < matriceArrayList.size() - 1 || matriceArrayList.size() < 2) && !error; i++)
            if (matriceArrayList.size() < 2 || matriceArrayList.get(i).getN() != matriceArrayList.get(i + 1).getM())
                error = true;
        if (error) {
            matriciel.setDisable(true);
            if (matriciel.isSelected())
                matriciel.setSelected(false);
            error = false;
        } else
            matriciel.setDisable(false);
        for (int i = 0; i < matriceArrayList.size() && !error; i++)
            if (matriceArrayList.size() != 2 || matriceArrayList.get(0).getM() != matriceArrayList.get(1).getM() && matriceArrayList.get(0).getM() != 1 && matriceArrayList.get(0).getN() != matriceArrayList.get(2).getN() && matriceArrayList.get(0).getN() != 3)
                error = true;
        if (error) {
            vectoriel.setDisable(true);
            if (vectoriel.isSelected())
                vectoriel.setSelected(false);
        } else
            vectoriel.setDisable(false);
    }

    public void changerValeur(BetterButton button, Matrice matrice) {
        TextInputDialog alerteValeur = new TextInputDialog("Entrez ici");
        alerteValeur.setTitle("Valeur");
        alerteValeur.setHeaderText("Entrez la valeur à la position " + (button.getX() + 1) + "-" + (button.getY() + 1) + " de la matrice " + matrice.getNom());
        String valeur = alerteValeur.showAndWait().get();
        try {
            matrice.getMatrice()[button.getX()][button.getY()] = Integer.parseInt(valeur);
            button.setText(valeur);
        } catch (Exception e) {
            Alert alerte = new Alert(Alert.AlertType.INFORMATION);
            alerte.setTitle("ERREUR");
            alerte.setHeaderText("La valeur entrée est invalide, veuillez réessayer");
            alerte.showAndWait();
            changerValeur(button, matrice);
        }
    }

    public void calculer() {
        boolean error = false;
        Matrice matriceTemp;
        if (toggleGroup.getSelectedToggle() != null && matriceArrayList.size() > 0) {
            switch (toggleGroup.getSelectedToggle().getUserData().toString()) {
                case "addition":
                    matriceTemp = matriceArrayList.get(0);
                    for (int i = 0; i < matriceArrayList.size() - 1; i++)
                        matriceTemp = matriceTemp.addition(matriceArrayList.get(i));
                    resultatsMatrice(matriceTemp);
                    break;
                case "soustraction":
                    matriceTemp = matriceArrayList.get(0);
                    for (int i = 0; i < matriceArrayList.size() - 1; i++)
                        matriceTemp = matriceTemp.soustraction(matriceArrayList.get(i + 1));
                    resultatsMatrice(matriceTemp);
                    break;
                case "multiplication":
                    multiplication();
                    break;
                case "puissance":
                    puissance();
                    break;
                case "transposition":
                    resultatsMatrice(matriceArrayList.get(0).transposer());
                    break;
                case "inversion":
                    resultatsMatrice(matriceArrayList.get(0).inverser());
                    break;
                case "matriciel":
                    matriceTemp = matriceArrayList.get(0);
                    for (int i = 0; i < matriceArrayList.size() - 1; i++)
                        matriceTemp = matriceTemp.produit(matriceArrayList.get(i + 1));
                    resultatsMatrice(matriceTemp);
                    break;
                case "vectoriel":
                    resultatsMatrice(matriceArrayList.get(0).vectoriel(matriceArrayList.get(1)));
                    break;
                case "hadamard":
                    matriceTemp = matriceArrayList.get(0);
                    for (int i = 0; i < matriceArrayList.size() - 1; i++)
                        matriceTemp = matriceTemp.hadamard(matriceArrayList.get(i + 1));
                    resultatsMatrice(matriceTemp);
                    break;
                case "tensoriel":
                    matriceTemp = matriceArrayList.get(0);
                    for (int i = 0; i < matriceArrayList.size() - 1; i++)
                        matriceTemp = matriceTemp.tensoriel(matriceArrayList.get(i + 1));
                    resultatsMatrice(matriceTemp);
                    break;
                case "determinant":
                    resultatsNombre(matriceArrayList.get(0).determinant());
                    break;
            }
            if (error) {
                Alert alerte = new Alert(Alert.AlertType.INFORMATION);
                alerte.setTitle("ERREUR");
                alerte.setHeaderText("Opération impossible");
                alerte.showAndWait();
            } else
                tabPane.getSelectionModel().select(tabResultat);
        } else {
            String message = "";
            if (toggleGroup.getSelectedToggle() == null)
                message += "Aucune opération séléctionnée\n";
            if (matriceArrayList.size() == 0)
                message += "Aucune matrice séléctionnée";
            Alert alerte = new Alert(Alert.AlertType.INFORMATION);
            alerte.setTitle("ERREUR");
            alerte.setHeaderText(message);
            alerte.showAndWait();
        }
    }

    private void puissance() {
        TextInputDialog alerteExposant = new TextInputDialog("Entrez ici");
        alerteExposant.setTitle("Exposant");
        alerteExposant.setHeaderText("Entrez l'exposant");
        String exposant = alerteExposant.showAndWait().get();
        try {
            Matrice matriceTemp = matriceArrayList.get(0);
            for (int i = 1; i < Double.parseDouble(exposant); i++)
                matriceTemp = matriceTemp.produit(matriceArrayList.get(0));
            resultatsMatrice(matriceTemp);
        } catch (Exception e) {
            Alert alerte = new Alert(Alert.AlertType.INFORMATION);
            alerte.setTitle("ERREUR");
            alerte.setHeaderText("L'exposant entré est invalide, veuillez réessayer");
            alerte.showAndWait();
        }
    }

    private void multiplication() {
        TextInputDialog alerteCoefficient = new TextInputDialog("Entrez ici");
        alerteCoefficient.setTitle("Coefficient");
        alerteCoefficient.setHeaderText("Entrez le coefficient");
        String coefficient = alerteCoefficient.showAndWait().get();
        try {
            resultatsMatrice(matriceArrayList.get(0).multiplication(Double.parseDouble(coefficient)));
        } catch (Exception e) {
            Alert alerte = new Alert(Alert.AlertType.INFORMATION);
            alerte.setTitle("ERREUR");
            alerte.setHeaderText("Le coefficient entré est invalide, veuillez réessayer");
            alerte.showAndWait();
        }
    }

    private void resultatsMatrice(Matrice matrice) {
        hBoxResultat.getChildren().clear();
        ArrayList<VBox> vBoxArrayList = new ArrayList<>();
        for (int i = 0; i < matrice.getN(); i++) {
            vBoxArrayList.add(new VBox());
            vBoxArrayList.get(i).setAlignment(Pos.CENTER);
            hBoxResultat.getChildren().add(vBoxArrayList.get(i));
        }
        for (int i = 0; i < matrice.getM(); i++) {
            for (int j = 0; j < matrice.getN(); j++) {
                Button button = new Button();
                button.setMinHeight(50);
                button.setMinWidth(50);
                button.setMaxHeight(50);
                button.setMaxWidth(50);
                button.setAlignment(Pos.CENTER);
                button.setText(numberFormat.format(matrice.getMatrice()[i][j]));
                button.setTooltip(new Tooltip(Double.toString(matrice.getMatrice()[i][j])));
                vBoxArrayList.get(j).getChildren().add(button);
            }
        }
    }

    private void resultatsNombre(double nombre) {
        hBoxResultat.getChildren().clear();
        Button button = new Button();
        button.setMinHeight(50);
        button.setMinWidth(50);
        button.setMaxHeight(50);
        button.setMaxWidth(50);
        button.setAlignment(Pos.CENTER);
        button.setText(numberFormat.format(nombre));
        button.setTooltip(new Tooltip(Double.toString(nombre)));
        hBoxResultat.getChildren().add(button);
    }

    public void clearManipulation() {
        hBoxManipulation.getChildren().clear();
        matriceArrayList = new ArrayList<>();
        tabPane.getSelectionModel().select(tabSize);
    }

    // Resize : https://stackoverflow.com/questions/34815660/javafx-image-getting-scaled-to-25-and-then-getting-printed
    public void printImage() {
        Printer printer = Printer.getDefaultPrinter();
        PrinterJob job = PrinterJob.createPrinterJob(printer);
        PageLayout pageLayout = job.getJobSettings().getPageLayout();
        ImageView imageView = new ImageView(hBoxResultat.snapshot(null,null));
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(pageLayout.getPrintableHeight());
        imageView.setFitWidth(pageLayout.getPrintableWidth());

        if (job != null) {
            boolean success = job.printPage(imageView);
            if (success) {
                System.out.println("PRINTING FINISHED");
                job.endJob();
            }
        }
    }
}
