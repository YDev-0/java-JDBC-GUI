package ma.ydev0.javajdbcgui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import ma.ydev0.javajdbcgui.entities.Gcharacter;
import ma.ydev0.javajdbcgui.entities.Gclass;
import ma.ydev0.javajdbcgui.service.ServiceGcharacter;
import ma.ydev0.javajdbcgui.service.ServiceGclass;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class WindowController implements Initializable {

    private ServiceGcharacter serviceGcharacter = new ServiceGcharacter();
    private ServiceGclass serviceGclass = new ServiceGclass();

    // GCharacter fields
    String m_gcharacterId;
    @FXML
    TextField tfname;
    @FXML
    TextField tfhealth;
    @FXML
    TextField tfdamage;
    @FXML
    ChoiceBox<Gclass> cbgclass;
    @FXML
    Button btnGcharacterAdd;
    @FXML
    Button btnGcharacterUpdate;
    @FXML
    Button btnGcharacterDelete;
    @FXML
    Button btnGcharacterExport;
    @FXML
    Button btnGcharacterImport;
    @FXML
    Button btnGcharacterSave;
    @FXML
    TableView<Gcharacter> tvGcharacters;
    @FXML
    TableColumn<Gcharacter, Integer> colGcharacterId;
    @FXML
    TableColumn<Gcharacter, String> colGcharacterName;
    @FXML
    TableColumn<Gcharacter, Integer> colGcharacterHealth;
    @FXML
    TableColumn<Gcharacter, Float> colGcharacterDamage;
    @FXML
    TableColumn<Gcharacter, Integer> colGcharacterClassId;

    // GClass fields
    String m_gclassId;
    @FXML
    TextField tflabel;
    @FXML
    private TextArea tadescription;
    @FXML
    Button btnGclassAdd;
    @FXML
    Button btnGclassUpdate;
    @FXML
    Button btnGclassDelete;
    @FXML
    Button btnGclassExport;
    @FXML
    Button btnGclassImport;
    @FXML
    Button btnGclassSave;
    @FXML
    TableView<Gclass> tvGclasses;
    @FXML
    TableColumn<Gcharacter, Integer> colGclassId;
    @FXML
    TableColumn<Gcharacter, String> colGclassLabel;
    @FXML
    TableColumn<Gcharacter, String> colGclassDescription;

    public ObservableList<Gcharacter> gcharacterObservableList() {
        ObservableList<Gcharacter> gcharacters = FXCollections.observableArrayList();

        serviceGcharacter.findAll().forEach(gcharacter -> {
            gcharacters.add(gcharacter);
        });

        return gcharacters;
    }

    public ObservableList<Gclass> gclassesObservableList() {
        ObservableList<Gclass> gclasses = FXCollections.observableArrayList();

        serviceGclass.findAll().forEach(gclass -> {
            gclasses.add(gclass);
        });

        return gclasses;
    }

    public void showGcharacters() {
        ObservableList<Gcharacter> gcharacters = gcharacterObservableList();
        tvGcharacters.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tvGcharacters.setItems(gcharacters);
        colGcharacterId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colGcharacterName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colGcharacterHealth.setCellValueFactory(new PropertyValueFactory<>("health"));
        colGcharacterDamage.setCellValueFactory(new PropertyValueFactory<>("damage"));
        colGcharacterClassId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getGClass().getId()).asObject());
        tvGcharacters.refresh();
    }

    public void showGclasses() {
        ObservableList<Gclass> gclasses = gclassesObservableList();
        tvGclasses.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tvGclasses.setItems(gclasses);
        colGclassId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colGclassLabel.setCellValueFactory(new PropertyValueFactory<>("label"));
        colGclassDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tvGclasses.refresh();
    }

    public void loadGclassesChoiceBox() {
        cbgclass.setConverter(new StringConverter<Gclass>() {
            @Override
            public String toString(Gclass gclass) {
                return gclass != null ? gclass.getLabel() : "";
            }

            @Override
            public Gclass fromString(String string) {
                // This method is not needed for the ChoiceBox display
                return null;
            }
        });
        cbgclass.setItems(gclassesObservableList());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showGcharacters();
        showGclasses();
        loadGclassesChoiceBox();
    }

    @FXML
    public void deleteCharacterAction() {
        List<Gcharacter> gcharacterList = tvGcharacters.getSelectionModel().getSelectedItems().stream().toList();
        if (showConfirmDialog("Are you sure ?"))
            if (gcharacterList.size() > 0) {
                gcharacterList.forEach(item -> {
                    if (item != null) {
                        serviceGcharacter.remove(item);
                    }
                });
                showGcharacters();
            } else showErrorDialog("Please select items to delete.");
        clearGcharacterField();
    }

    @FXML
    public void updateCharacterAction() {
        Gcharacter gcharacter = getGcharacterFromField();
        if(gcharacter != null && !m_gcharacterId.isEmpty()) {
            serviceGcharacter.update(gcharacter);
            clearGcharacterField();
            Gcharacter item = tvGcharacters.getSelectionModel().getSelectedItem();
            item.setName(gcharacter.getName());
            item.setHealth(gcharacter.getHealth());
            item.setDamage(gcharacter.getDamage());
            item.setGClass(gcharacter.getGClass());
            tvGcharacters.refresh();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Data has been updated.");
            alert.show();
        }
    }

    @FXML
    public void addCharacterAction() {
        Gcharacter gcharacter = getGcharacterFromField();
        if(gcharacter != null) {
            serviceGcharacter.save(gcharacter);
            tvGcharacters.getItems().add(gcharacter);
            clearGcharacterField();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Data has been added.");
            alert.show();
        }
    }

    @FXML
    public void deleteGclassAction() {
        if(showConfirmDialog("Are you sure ?")) {
            List<Gclass> gclassesList = tvGclasses.getSelectionModel().getSelectedItems();
            if(gclassesList.size() > 0) {
                gclassesList.forEach(item -> {
                    if (item != null) {
                        serviceGclass.remove(item);
                    }
                });
                showGclasses();
            } else showErrorDialog("Please select items to delete");
        }
        clearGclassField();
    }

    @FXML
    public void updateGclassAction() {
        Gclass gclass = getGclassFromField();
        if(gclass != null && !m_gclassId.isEmpty()) {
            serviceGclass.update(gclass);
            Gclass item = tvGclasses.getSelectionModel().getSelectedItem();
            clearGclassField();
            item.setLabel(gclass.getLabel());
            item.setDescription(gclass.getDescription());
            tvGclasses.refresh();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Data has been updated.");
            alert.show();
        }
    }

    @FXML
    public void addGclassAction() {
        Gclass gclass = getGclassFromField();
        if(gclass != null) {
            serviceGclass.save(gclass);
            tvGclasses.getItems().add(gclass);
            clearGclassField();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText("Data has been added.");
            alert.show();
        }
    }

    @FXML
    public void updateSelectedGcharacter() {
        Gcharacter gcharacter = tvGcharacters.getSelectionModel().getSelectedItem();
        if(gcharacter != null) {
            m_gcharacterId = String.valueOf(gcharacter.getId());
            tfname.setText(gcharacter.getName());
            tfhealth.setText(String.valueOf(gcharacter.getHealth()));
            tfdamage.setText(String.valueOf(gcharacter.getDamage()));
            cbgclass.setValue(gcharacter.getGClass());
        }
    }

    @FXML
    public void updateSelectedGclass() {
        Gclass gclass = tvGclasses.getSelectionModel().getSelectedItem();
        if(gclass != null) {
            m_gclassId = String.valueOf(gclass.getId());
            tflabel.setText(gclass.getLabel());
            tadescription.setText(gclass.getDescription());
        }
    }

    @FXML
    public void exportSelectedGcharacters() {
        List<Gcharacter> gcharactersList = new ArrayList<>();
        tvGcharacters.getSelectionModel().getSelectedItems().forEach(item -> {
            gcharactersList.add(item);
        });
        if(gcharactersList.size() > 0) {
            Optional<String> result = showInputDialog("Export", "Enter file name", "File name: ");
            boolean inputError;
            do {
                inputError = false;
                if(result.isPresent()) {
                    if (result.isEmpty()) {
                        showErrorDialog("Please enter file name.");
                        result = showInputDialog("Export", "Enter file name", "File name: ");
                        inputError = true;
                    }
                    if (!serviceGcharacter.exportAsExcel(gcharactersList, result.get(), false)) {
                        inputError = true;
                        if (showConfirmDialog("File already exist do you want to replace it ?")) {
                            serviceGcharacter.exportAsExcel(gcharactersList, result.get(), true);
                            break;
                        } else {
                            result = showInputDialog("Export", "Enter file name", "File name: ");
                        }
                    }
                } else break;
            } while(inputError);
        } else {
            showErrorDialog("Please select items in the table.");
        }
    }

    @FXML
    public void exportSelectedGclasses() {
        List<Gclass> gclassesList = new ArrayList<>();
        tvGclasses.getSelectionModel().getSelectedItems().forEach(item -> {
            gclassesList.add(item);
        });
        if(gclassesList.size() > 0) {
            Optional<String> result = showInputDialog("Export", "Enter file name", "File name: ");
            boolean inputError;
            do {
                inputError = false;
                if(result.isPresent()) {
                    if(result.isEmpty()) {
                        showErrorDialog("Please enter file name.");
                        result = showInputDialog("Export", "Enter file name", "File name: ");
                        inputError = true;
                    }
                    if(!serviceGclass.exportAsExcel(gclassesList, result.get(), false)) {
                        inputError = true;
                        if(showConfirmDialog("File already exist do you want to replace it ?")) {
                            serviceGclass.exportAsExcel(gclassesList, result.get(), true);
                            break;
                        } else {
                            result = showInputDialog("Export", "Enter file name", "File name: ");
                        }
                    }
                } else break;
            } while(inputError);
        } else {
            showErrorDialog("Please select items in the table.");
        }
    }

    @FXML
    public void importGcharactersFromExcel() {
        Stage primaryStage = (Stage) btnGcharacterImport.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        while(true) {
            if(selectedFile == null) {
                if(showConfirmDialog("Please choose a file.")) selectedFile = fileChooser.showOpenDialog(primaryStage);
                else break;
            }
            else if(!getFileExtension(selectedFile.getName()).get().equals("xlsx")) {
                showErrorDialog("Please choose the correct file.");
                selectedFile = fileChooser.showOpenDialog(primaryStage);
            } else break;
        }
        if(selectedFile != null) {
            List<Gcharacter> gcharactersList = serviceGcharacter.importFromExcel(selectedFile);
            tvGcharacters.getItems().clear();
            gcharactersList.forEach(gcharacter -> {
                tvGcharacters.getItems().add(gcharacter);
            });
            tvGcharacters.refresh();
            btnGcharacterSave.setDisable(false);
        }
    }

    @FXML
    public void importGclassesFromExcel() {

    }

    @FXML
    public void saveGcharacters() {
        List<Gcharacter> gcharactersList = new ArrayList<>();
        tvGcharacters.getSelectionModel().getSelectedItems().forEach(item -> {
            gcharactersList.add(item);
        });

        if(gcharactersList.size() > 0) {
            gcharactersList.forEach(gcharacter -> {
                serviceGcharacter.save(new Gcharacter(null, gcharacter.getName(), gcharacter.getHealth(), gcharacter.getDamage(), gcharacter.getGClass()));
            });
            showInfoDialog("Data has been saved in database.");
            showGcharacters();
            btnGcharacterSave.setDisable(true);
        } else {
            showErrorDialog("Please select items to save in the table.");
        }
    }

    @FXML
    public void saveGclasses() {

    }

    private Gclass getGclassFromField() {
        boolean validator = tflabel.getText().isEmpty() || tadescription.getText().isEmpty();
        if(validator) {
            showErrorDialog("Please fill all blank fields.");
            return null;
        }
        Integer id = m_gclassId.isEmpty() ? null : Integer.parseInt(m_gclassId);
        String label = tflabel.getText();
        String description = tadescription.getText();
        return new Gclass(id, label, description);
    }
    private Gcharacter getGcharacterFromField() {
        boolean validator = tfname.getText().isEmpty() || tfhealth.getText().isEmpty() || tfdamage.getText().isEmpty() || cbgclass.getValue() == null;
        if(validator) {
            showErrorDialog("Please fill all blank fields.");
            return null;
        }
        Integer id = m_gcharacterId.isEmpty() ? null : Integer.parseInt(m_gcharacterId);
        String name = tfname.getText();
        int health = Integer.parseInt(tfhealth.getText());
        float damage = Float.parseFloat(tfdamage.getText());
        Gclass gclass = cbgclass.getValue();

        return new Gcharacter(id, name, health, damage, gclass);
    }

    @FXML
    public void checkHealthInput() {
        String value = tfhealth.getText();
        try {
            Integer.parseInt(value);
        } catch (Exception e) {
            if(value.length() > 0)
                tfhealth.setText(value.substring(0, value.length() - 1));
        }
    }

    @FXML
    public void checkDamageInput() {
        String value = tfdamage.getText();
        try {
            Float.parseFloat(value);
        } catch (Exception e) {
            if(value.length() > 0)
                tfdamage.setText(value.substring(0, value.length() - 1));
        }
    }

    @FXML
    public void clearGclassField() {
        m_gclassId = null;
        tflabel.setText("");
        tadescription.setText("");
    }

    @FXML
    public void clearGcharacterField() {
        m_gcharacterId = null;
        tfname.setText("");
        tfhealth.setText("");
        tfdamage.setText("");
        cbgclass.setValue(null);
    }

    private Optional<String> showInputDialog(String title, String header, String content) {
        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setTitle(title);
        inputDialog.setHeaderText(header);
        inputDialog.setContentText(content);
        return inputDialog.showAndWait();
    }

    private boolean showConfirmDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setHeaderText(null);
        alert.setContentText(message);
        ButtonType userResponse = alert.showAndWait().orElse(ButtonType.CANCEL);
        return ButtonType.OK == userResponse;
    }

    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfoDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

    private Optional<String> getFileExtension(String filename) {
        return Optional.ofNullable(filename).filter(f -> f.contains("."))
            .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }
}