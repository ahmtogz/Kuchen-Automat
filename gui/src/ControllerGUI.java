
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import kuchen.Allergen;
import kuchen.Kuchentyp;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

public class ControllerGUI {
    private static final int AUTOMAT_KAPAZITAET = 100;
    Automat automat = new Automat(AUTOMAT_KAPAZITAET);

    @FXML
    private TextField kremField;
    @FXML
    private TextField obstField;
    @FXML
    private TextField preisField;
    @FXML
    private TextField naehrwertField;
    @FXML
    private TextField haltbarkeitField;
    @FXML
    private TextField herstellerNameField;
    @FXML
    private TextField entfernenUndAendernFachnrField;
    @FXML
    private RadioButton rad_obstkuchen;
    @FXML
    private RadioButton rad_kremkuchen;
    @FXML
    private RadioButton rad_obsttorte;
    @FXML
    private RadioButton rad_jos;
    @FXML
    private RadioButton rad_jbp;
    @FXML
    private CheckBox check_gluten;
    @FXML
    private CheckBox check_sesamsamen;
    @FXML
    private CheckBox check_erdnuss;
    @FXML
    private CheckBox check_haselnuss;


    ObservableList<String> kuchen = FXCollections.observableArrayList();
    @FXML
    ListView<String> kuchenList = new ListView<>(kuchen);
    ObservableList<String> hersteller = FXCollections.observableArrayList();
    @FXML
    ListView<String> herstellerList = new ListView<>(hersteller);

    ObservableList<String> herstellerMitAnzahl = FXCollections.observableArrayList();
    @FXML
    ListView<String> herstellerListMitAnzahl = new ListView<>(herstellerMitAnzahl);


    Alert allert = new Alert(Alert.AlertType.NONE);

    public void einfuegenHerstellerClicked(MouseEvent mouseEvent) {
        String name = herstellerNameField.getText();
        if (automat.einfuegenHersteller(name)) {
            System.out.println("Hersteller zur Automat hinzugefügt");
            herstellerList.getItems().clear();
            herstellerList.getItems().addAll(getHerstellerListItems());
        } else if (name.length() == 0) {
            System.out.println("Herstellername darf nicht leer sein");
            allert.setAlertType(Alert.AlertType.WARNING);
            allert.setContentText("Herstellername darf nicht leer sein!");
            allert.show();
        } else {
            System.out.println("Hersteller bereits verfügbar");
            allert.setAlertType(Alert.AlertType.WARNING);
            allert.setContentText("Hersteller bereits verfügbar!");
            allert.show();
        }
    }


    public void entfernenHerstellerClicked(MouseEvent mouseEvent) throws Exception {
        //String name = herstellerNameField.getText();
        String name = herstellerList.getSelectionModel().getSelectedItem();
        if (name == null || name.isEmpty()) {
            name = herstellerNameField.getText();
        }

        if (name.length() == 0) {
            System.out.println("Zu entfernender Name des Herstellers darf nicht leer sein");
            allert.setAlertType(Alert.AlertType.WARNING);
            allert.setContentText("Zu entfernender Name des Herstellers darf nicht leer sein!");
            allert.show();
        } else {
            int gesuchtIndex = herstellerList.getItems().indexOf(name);

            if (gesuchtIndex != -1) {
                if (automat.entfernenHersteller(name)) {
                    System.out.println("Hersteller von Automat entfernt");
                    herstellerList.getItems().clear();
                    herstellerList.getItems().addAll(getHerstellerListItems());
                } else {
                    System.out.println("Hersteller konnte nicht entfernt werden; Kuchen dieses Herstellers ist bereits verfügbar");
                    allert.setAlertType(Alert.AlertType.WARNING);
                    allert.setContentText("Hersteller konnte nicht entfernt werden; Kuchen dieses Herstellers ist bereits verfügbar");
                    allert.show();
                }
            } else {
                throw new Exception("Hersteller nicht gefunden");
            }
        }
    }


    public void einfuegenKuchenClicked(MouseEvent mouseEvent) throws Exception {

        String herstellerName = herstellerList.getSelectionModel().getSelectedItem();
        HerstellerImp hersteller = new HerstellerImp(herstellerName);

        String naehrwertText = naehrwertField.getText();
        String preisText = preisField.getText();
        String haltbarkeitText = haltbarkeitField.getText();
        String kremsorte = kremField.getText();
        String obstsorte = obstField.getText();
        int naehrwert;
        BigDecimal preis;
        Duration haltbarkeit;

        if (naehrwertText.isEmpty()) {
            allert.setAlertType(Alert.AlertType.WARNING);
            allert.setContentText("Naehrwert-Feld muss ausgefüllt sein!");
            allert.show();
            return;
        }

        try {
            naehrwert = Integer.parseInt(naehrwertText);
        } catch (NumberFormatException e) {
            allert.setAlertType(Alert.AlertType.ERROR);
            allert.setContentText("Ungültige Zahl im Naehrwert-Feld!");
            allert.show();
            return;
        }

        if (preisText.isEmpty()) {
            allert.setAlertType(Alert.AlertType.WARNING);
            allert.setContentText("Preis-Feld muss ausgefüllt sein!");
            allert.show();
            return;
        }
        try {
            preis = BigDecimal.valueOf(Double.parseDouble(preisText));
        } catch (NumberFormatException e) {
            allert.setAlertType(Alert.AlertType.ERROR);
            allert.setContentText("Ungültige Zahl im Preis-Feld!");
            allert.show();
            return;
        }

        if (haltbarkeitText.isEmpty()) {
            allert.setAlertType(Alert.AlertType.WARNING);
            allert.setContentText("Haltbarkeit-Feld muss ausgefüllt sein!");
            allert.show();
            return;
        }
        try {
            haltbarkeit = Duration.ofHours(Long.parseLong(haltbarkeitText));
        } catch (NumberFormatException e) {
            allert.setAlertType(Alert.AlertType.ERROR);
            allert.setContentText("Ungültige Zahl im Haltbarkeit-Feld!");
            allert.show();
            return;
        }

        Collection<Allergen> allergene = new LinkedList<>();
        if (check_gluten.isSelected()) {
            allergene.add(Allergen.Gluten);
        }
        if (check_sesamsamen.isSelected()) {
            allergene.add(Allergen.Sesamsamen);
        }
        if (check_erdnuss.isSelected()) {
            allergene.add(Allergen.Erdnuss);
        }
        if (check_haselnuss.isSelected()) {
            allergene.add(Allergen.Haselnuss);
        }


        if (herstellerName == null) {
            allert.setAlertType(Alert.AlertType.WARNING);
            allert.setContentText("Wählen Sie zuerst den Herstellernamen von Hersteller Liste!");
            allert.show();
        } else {
            if (rad_obsttorte.isSelected()) {
                if (kremField.getText().length() == 0 || obstField.getText().length() == 0) {
                    allert.setAlertType(Alert.AlertType.WARNING);
                    allert.setContentText("Obstsorte und Kremsorte müssen eingetragen werden!");
                    allert.show();
                } else {
                    if (automat.einfuegenKuchen(Kuchentyp.valueOf("Obsttorte"), hersteller, preis, naehrwert, haltbarkeit, allergene, obstsorte, kremsorte)) {
                        System.out.println("Obsttorte zur Automat hinzugefügt");
                    }
                }

            } else if (rad_kremkuchen.isSelected()) {
                if (kremField.getText().length() == 0) {
                    allert.setAlertType(Alert.AlertType.WARNING);
                    allert.setContentText("Kremsorte muss eingetragen werden!");
                    allert.show();
                } else {
                    if (automat.einfuegenKuchen(Kuchentyp.valueOf("Kremkuchen"), hersteller, preis, naehrwert, haltbarkeit, allergene, kremsorte)) {
                        System.out.println("Kremkuchen zur Automat hinzugefügt");
                    }
                }


            } else if (rad_obstkuchen.isSelected()) {
                if (obstField.getText().length() == 0) {
                    allert.setAlertType(Alert.AlertType.WARNING);
                    allert.setContentText("Obstsorte muss eingetragen werden!");
                    allert.show();
                } else {
                    if (automat.einfuegenKuchen(Kuchentyp.valueOf("Obstkuchen"), hersteller, preis, naehrwert, haltbarkeit, allergene, obstsorte)) {
                        System.out.println("Obstkuchen zur Automat hinzugefügt");
                    }
                }
            }
            kuchenList.getItems().clear();
            kuchenList.getItems().addAll(getKuchenListItems());

            herstellerListMitAnzahl.getItems().clear();
            herstellerListMitAnzahl.getItems().addAll(getHerstellerMitKuchenAnzahlListItems());
        }

    }

    public void entfernenKuchenClicked(MouseEvent mouseEvent) {
        String fachnummerStr = entfernenUndAendernFachnrField.getText();

        try {
            if (fachnummerStr.isEmpty()) {
                System.out.println("EntfernenFachnummerField darf nicht leer sein");
                allert.setAlertType(Alert.AlertType.WARNING);
                allert.setContentText("EntfernenFachnummerField darf nicht leer sein!");
                allert.show();
            } else {
                int fachnummer = Integer.parseInt(fachnummerStr);
                automat.entfernenKuchen(fachnummer);
                System.out.println("Kuchen von der Automat entfernt");
            }
        } catch (NumberFormatException e) {
            System.out.println("Fehler beim Parsen der Fachnummer: " + e.getMessage());
            allert.setAlertType(Alert.AlertType.ERROR);
            allert.setContentText("Ungültige Fachnummer! Geben Sie bitte eine gültige Zahl ein.");
            allert.show();
        }

        kuchenList.getItems().clear();
        kuchenList.getItems().addAll(getKuchenListItems());

        herstellerListMitAnzahl.getItems().clear();
        herstellerListMitAnzahl.getItems().addAll(getHerstellerMitKuchenAnzahlListItems());
    }


    public void aendernKuchenClicked(MouseEvent mouseEvent) {
        String fachnummerStr = entfernenUndAendernFachnrField.getText();

        try {
            if (fachnummerStr.isEmpty()) {
                System.out.println("AendernFachnummerField darf nicht leer sein");
                allert.setAlertType(Alert.AlertType.WARNING);
                allert.setContentText("AendernFachnummerField darf nicht leer sein!");
                allert.show();
            } else {
                int fachnummer = Integer.parseInt(fachnummerStr);
                automat.aendern(fachnummer);
                System.out.println("Inspektionsdatum wurde aktualisiert");
            }
        } catch (NumberFormatException e) {
            System.out.println("Fehler beim Parsen der Fachnummer: " + e.getMessage());
            allert.setAlertType(Alert.AlertType.ERROR);
            allert.setContentText("Ungültige Fachnummer! Geben Sie bitte eine gültige Zahl ein.");
            allert.show();
        }

        kuchenList.getItems().clear();
        kuchenList.getItems().addAll(getKuchenListItems());
    }

        private List<String> getKuchenListItems() {
            List<String> itemList = new ArrayList<>();
            for (KuchenImp kuchen : automat.auflistenKuchen()) {
                itemList.add("Fach " + kuchen.getFachnummer() + ": " + kuchen.getKuchentyp() + ", Hersteller: " + kuchen.getHersteller().getName() +
                        ", Inspektionsdatum: " + kuchen.getInspektionsdatum() +
                        ", Haltbarkeit: " + automat.verbleibendeHaltbarkeit(kuchen) +
                        ", Allergene: " + kuchen.getAllergene() + System.lineSeparator());
            }
            return itemList;
        }


    private List<String> getHerstellerListItems() {
        List<String> itemList = new ArrayList<>();
        for (HerstellerImp hersteller : automat.auflistenHersteller()) {
            itemList.add(hersteller.getName());
        }
        return itemList;
    }



    private List<String> getHerstellerMitKuchenAnzahlListItems() {
        List<String> itemList = new ArrayList<>();
        Map<String, Integer> herstellerMitKuchenAnzahl = automat.getHerstellerMitKuchenAnzahl();

        for (Map.Entry<String, Integer> entry : herstellerMitKuchenAnzahl.entrySet()) {
            String herstellerName = entry.getKey();
            Integer kuchenAnzahl = entry.getValue();
            itemList.add(herstellerName + " - " + kuchenAnzahl);
        }
        return itemList;
    }



    private void sortAndDisplay(List<KuchenImp> kuchenListe, Comparator<KuchenImp> comparator) {
        kuchenListe.sort(comparator);

        kuchenList.getItems().clear();
        for (KuchenImp kuchen : kuchenListe) {
            kuchenList.getItems().add("Fach " + kuchen.getFachnummer() + ": " + kuchen.getKuchentyp() + ", Hersteller: " +
                    kuchen.getHersteller().getName() +
                    ", Inspektionsdatum: " + kuchen.getInspektionsdatum() +
                    ", Haltbarkeit: " + automat.verbleibendeHaltbarkeit(kuchen) +
                    ", Allergene: " + kuchen.getAllergene() + System.lineSeparator());
        }
    }

    public void mitFachnrSortClicked(MouseEvent mouseEvent) {
        List<KuchenImp> kuchenListe = new ArrayList<>(automat.auflistenKuchen());
        sortAndDisplay(kuchenListe, Comparator.comparingInt(KuchenImp::getFachnummer));
    }

    public void mitHerstellerSortClicked(MouseEvent mouseEvent) {
        List<KuchenImp> kuchenListe = new ArrayList<>(automat.auflistenKuchen());
        sortAndDisplay(kuchenListe, Comparator.comparing(k -> k.getHersteller().getName()));
    }

    public void mitInspektSortClicked(MouseEvent mouseEvent) {
        List<KuchenImp> kuchenListe = new ArrayList<>(automat.auflistenKuchen());
        sortAndDisplay(kuchenListe, Comparator.comparing(KuchenImp::getInspektionsdatum));
    }

    public void mitHaltbarkeitSortClicked(MouseEvent mouseEvent) {
        List<KuchenImp> kuchenListe = new ArrayList<>(automat.auflistenKuchen());
        sortAndDisplay(kuchenListe, Comparator.comparing(KuchenImp::getHaltbarkeit));
    }


    public void speichernClicked(MouseEvent mouseEvent) throws IOException {
        if (rad_jos.isSelected()) {
            JOS jos = new JOS();
            jos.save(automat);
            System.out.println("Automat im JOS-Format gespeichert.");
            allert.setAlertType(Alert.AlertType.INFORMATION);
            allert.setContentText("Automat im JOS-Format gespeichert!");
            allert.show();
        } else if (rad_jbp.isSelected()) {
            JBP jbp = new JBP();
            jbp.save(automat);
            System.out.println("Automat im JBP-Format gespeichert.");
            allert.setAlertType(Alert.AlertType.INFORMATION);
            allert.setContentText("Automat im JBP-Format gespeichert!");
            allert.show();
        } else {
            System.out.println("Bitte wählen Sie ein Format (JOS oder JBP)");
        }
    }

    public void aufladenClicked(MouseEvent mouseEvent) throws IOException, ClassNotFoundException {

        if (rad_jbp.isSelected()) {
            JBP jbp = new JBP();
            automat = jbp.load();
        } else {
            JOS jos = new JOS();
            automat = jos.load();
        }
        kuchenList.getItems().clear();
        kuchenList.getItems().addAll(getKuchenListItems());

        herstellerList.getItems().clear();
        herstellerList.getItems().addAll(getHerstellerListItems());

        herstellerListMitAnzahl.getItems().clear();
        herstellerListMitAnzahl.getItems().addAll(getHerstellerMitKuchenAnzahlListItems());

    }
}