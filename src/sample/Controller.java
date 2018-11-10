package sample;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.print.PageLayout;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

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
    private Tab tabSize, tabResultat, tabDemarches;
    @FXML
    private Button demarches;
    private ArrayList<Matrice> matriceArrayList = new ArrayList<>();
    private DecimalFormat numberFormat = new DecimalFormat("0.00");

    public void ajouter() {
        matriceArrayList.add(new Matrice(Character.toString((char) (65 + matriceArrayList.size())), (int) matriceX.getValueFactory().getValue(), (int) matriceY.getValueFactory().getValue(), ""));
        creerMatrice(matriceArrayList.get(matriceArrayList.size() - 1));
        afficherMatrice(matriceArrayList.get(matriceArrayList.size() - 1));
        test();
    }

    public void ajouterFromFile(int m, int n, double[][] matrice) {
        matriceArrayList.add(new Matrice(Character.toString((char) (65 + matriceArrayList.size())), m, n, ""));
        matriceArrayList.get(matriceArrayList.size() - 1).setMatrice(matrice);
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
        if (matriceArrayList.size() != 2 || matriceArrayList.get(0).getM() != matriceArrayList.get(1).getM() && matriceArrayList.get(0).getM() != 1 && matriceArrayList.get(0).getN() != matriceArrayList.get(1).getN() && matriceArrayList.get(0).getN() != 3)
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

    public void actionCalculer() {
        calculer(null, false, 0, null);
    }

    public Matrice calculer(String string, boolean mixte, int nb, Matrice matriceMixte) {
        Matrice matriceTemp;
        if ((toggleGroup.getSelectedToggle() != null || mixte) && matriceArrayList.size() > 0) {
            if (!mixte)
                string = toggleGroup.getSelectedToggle().getUserData().toString();
            switch (string) {
                case "addition":
                    if (!mixte) {
                        matriceTemp = matriceArrayList.get(0);
                        for (int i = 0; i < matriceArrayList.size() - 1; i++)
                            matriceTemp = matriceTemp.addition(matriceArrayList.get(i));
                        resultatsMatrice(matriceTemp);
                    } else
                        return matriceMixte.addition(matriceArrayList.get(nb + 1));
                    break;
                case "soustraction":
                    if (!mixte) {
                        matriceTemp = matriceArrayList.get(0);
                        for (int i = 0; i < matriceArrayList.size() - 1; i++)
                            matriceTemp = matriceTemp.soustraction(matriceArrayList.get(i + 1));
                        resultatsMatrice(matriceTemp);
                    } else
                        return matriceMixte.soustraction(matriceArrayList.get(nb + 1));
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
                    resultatsDeterminant(matriceArrayList.get(0).determinant());
                    break;
            }
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
        return null;
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
        demarches.setText(matrice.getDemarche());
    }

    private void resultatsDeterminant(Matrice matrice) {
        hBoxResultat.getChildren().clear();
        Button button = new Button();
        button.setMinHeight(50);
        button.setMinWidth(50);
        button.setMaxHeight(50);
        button.setMaxWidth(50);
        button.setAlignment(Pos.CENTER);
        button.setText(numberFormat.format(matrice.getDeterminant()));
        button.setTooltip(new Tooltip(Double.toString(matrice.getDeterminant())));
        hBoxResultat.getChildren().add(button);
        demarches.setText(matrice.getDemarche());
    }

    public void clear() {
        hBoxManipulation.getChildren().clear();
        hBoxResultat.getChildren().clear();
        demarches.setText("");
        matriceArrayList = new ArrayList<>();
        tabPane.getSelectionModel().select(tabSize);
    }

    // Resize : https://stackoverflow.com/questions/34815660/javafx-image-getting-scaled-to-25-and-then-getting-printed
    public void printImage() {
        Printer printer = Printer.getDefaultPrinter();
        PrinterJob job = PrinterJob.createPrinterJob(printer);
        PageLayout pageLayout = job.getJobSettings().getPageLayout();
        ImageView imageView1 = new ImageView(hBoxResultat.snapshot(null, null));
        imageView1.setPreserveRatio(true);
        ImageView imageView2 = new ImageView(demarches.snapshot(null, null));
        imageView2.setPreserveRatio(true);
        VBox vBox = new VBox(imageView1, imageView2);
        vBox.setMaxHeight(pageLayout.getPrintableHeight());
        vBox.setMaxWidth(pageLayout.getPrintableWidth());
        imageView1.setFitHeight(vBox.getHeight());
        imageView1.setFitWidth(vBox.getWidth());
        imageView2.setFitHeight(vBox.getHeight());
        imageView2.setFitWidth(vBox.getWidth());
        if (job != null) {
            boolean success = job.printPage(vBox);
            if (success) {
                System.out.println("PRINTING FINISHED");
                job.endJob();
            }
        }
    }

    public void operationsMixtes() {
        boolean error = false;
        for (int i = 0; (i < matriceArrayList.size() - 1 || matriceArrayList.size() < 2) && !error; i++)
            if (matriceArrayList.size() < 2 || matriceArrayList.get(i).getM() != matriceArrayList.get(i + 1).getM() || matriceArrayList.get(i).getN() != matriceArrayList.get(i + 1).getN())
                error = true;
        if (matriceArrayList.size() > 1 && !error) {
            Matrice matriceTemp = new Matrice(matriceArrayList.get(0).getNom(), matriceArrayList.get(0).getM(), matriceArrayList.get(0).getN(), matriceArrayList.get(0).getDemarche());
            for (int i = 0; i < matriceTemp.getM(); i++)
                for (int j = 0; j < matriceTemp.getN(); j++)
                    matriceTemp.getMatrice()[i][j] = matriceArrayList.get(0).getMatrice()[i][j];
            for (int i = 0; i < matriceArrayList.size() - 1; i++) {
                ChoiceDialog<String> alerte = new ChoiceDialog<String>("Addition", "Addition", "Soustraction");
                alerte.setTitle("Opération mixtes");
                alerte.setHeaderText("Veuillez choisir");
                alerte.setContentText("Votre choix: ");
                matriceTemp = calculer(alerte.showAndWait().get().toLowerCase(), true, i, matriceTemp);
                if (i == matriceArrayList.size() - 2)
                    tabPane.getSelectionModel().select(tabResultat);
            }
            resultatsMatrice(matriceTemp);
        } else {
            String message = "";
            if (matriceArrayList.size() < 1)
                message += "Nombre insufisant de matrice\n";
            if (error)
                message += "Matrices invalides pour les opérations";
            Alert alerte = new Alert(Alert.AlertType.INFORMATION);
            alerte.setTitle("ERREUR");
            alerte.setHeaderText(message);
            alerte.showAndWait();
        }
    }

    public void actionDemarches() {
        tabPane.getSelectionModel().select(tabDemarches);
    }

    public void actionResultats() {
        tabPane.getSelectionModel().select(tabResultat);
    }

    public void loadCSV() {
        clear();
        try {
            FileChooser fc = new FileChooser();
            fc.setTitle("Veuillez sélectionner un fichier");
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers CSV", "*.csv"));
            File fichier = fc.showOpenDialog(Main.stage);
            List<String> allLines = Files.readAllLines(fichier.toPath());
            String string;
            for (int i = 0; i < allLines.size(); i++) {
                string = allLines.get(i);
                String[] parts = string.split(",");
                double[][] matrice = new double[Integer.parseInt(parts[0])][Integer.parseInt(parts[1])];
                for (int j = 2; j < 2 + Integer.parseInt(parts[0]) && j < parts.length; j++)
                    for (int k = j; k < 2 + Integer.parseInt(parts[1]) && k < parts.length; k++)
                        matrice[j - 2][k - 2] = Double.parseDouble(parts[k]);
                ajouterFromFile(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), matrice);
            }

        } catch (Exception e) {
        }
    }

    public void loadXML() {
        clear();
        try {
            File file = new File("Data.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbf.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            Node root = doc.getDocumentElement();
            clean(root);
            NodeList data = root.getChildNodes();
            for (int i = 1; i < data.getLength(); i++) {
                double[][] matrice = new double[Integer.parseInt(data.item(i).getChildNodes().item(0).getTextContent())][Integer.parseInt(data.item(i).getChildNodes().item(1).getTextContent())];
                for (int j = 0; j < Integer.parseInt(data.item(i).getChildNodes().item(0).getTextContent()); j++) {
                    String[] parts = data.item(i).getChildNodes().item(2).getChildNodes().item(j).getTextContent().split(",");
                    for (int k = 0; k < Integer.parseInt(data.item(i).getChildNodes().item(1).getTextContent()); k++)
                        matrice[j][k] = Double.parseDouble(parts[k]);
                }
                ajouterFromFile(Integer.parseInt(data.item(i).getChildNodes().item(0).getTextContent()), Integer.parseInt(data.item(i).getChildNodes().item(1).getTextContent()), matrice);
            }
            String[] parts = data.item(0).getTextContent().split(",");
            if (parts.length > 1) {
                Matrice matriceTemp = new Matrice(matriceArrayList.get(0).getNom(), matriceArrayList.get(0).getM(), matriceArrayList.get(0).getN(), matriceArrayList.get(0).getDemarche());
                for (int i = 0; i < matriceTemp.getM(); i++)
                    for (int j = 0; j < matriceTemp.getN(); j++)
                        matriceTemp.getMatrice()[i][j] = matriceArrayList.get(0).getMatrice()[i][j];
                for (int i = 0; i < matriceArrayList.size() - 1; i++) {
                    matriceTemp = calculer(parts[i], true, i, matriceTemp);
                    if (i == matriceArrayList.size() - 2)
                        tabPane.getSelectionModel().select(tabResultat);
                }
                resultatsMatrice(matriceTemp);
            } else
                calculer(parts[0], false, 0, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clean(Node node) {
        NodeList childNodes = node.getChildNodes();
        for (int n = childNodes.getLength() - 1; n >= 0; n--) {
            Node child = childNodes.item(n);
            short nodeType = child.getNodeType();

            if (nodeType == Node.ELEMENT_NODE)
                clean(child);
            else if (nodeType == Node.TEXT_NODE) {
                String trimmedNodeVal = child.getNodeValue().trim();
                if (trimmedNodeVal.length() == 0)
                    node.removeChild(child);
                else
                    child.setNodeValue(trimmedNodeVal);
            } else if (nodeType == Node.COMMENT_NODE)
                node.removeChild(child);
        }
    }
}
