package ma.ydev0.javajdbcgui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import ma.ydev0.javajdbcgui.entities.Gcharacter;
import ma.ydev0.javajdbcgui.entities.Gclass;
import ma.ydev0.javajdbcgui.service.ServiceGcharacter;
import ma.ydev0.javajdbcgui.service.ServiceGclass;

import java.net.URL;
import java.util.ResourceBundle;

public class WindowController implements Initializable {

    private ServiceGcharacter serviceGcharacter = new ServiceGcharacter();
    private ServiceGclass serviceGclass = new ServiceGclass();

    // GCharacter fields
    @FXML
    TextField tfGcharacterId;
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
    @FXML
    TextField tfGclassId;
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
        tvGcharacters.setItems(gcharacters);
        colGcharacterId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colGcharacterName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colGcharacterHealth.setCellValueFactory(new PropertyValueFactory<>("health"));
        colGcharacterDamage.setCellValueFactory(new PropertyValueFactory<>("damage"));
        colGcharacterClassId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getGClass().getId()).asObject());
    }

    public void showGclasses() {
        ObservableList<Gclass> gclasses = gclassesObservableList();
        tvGclasses.setItems(gclasses);
        colGclassId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colGclassLabel.setCellValueFactory(new PropertyValueFactory<>("label"));
        colGclassDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure ?");
        ButtonType userResponse = alert.showAndWait().orElse(ButtonType.CANCEL);
        if(userResponse == ButtonType.OK) tvGcharacters.getSelectionModel().getSelectedItems().forEach(item -> {
            if(item != null) {
                serviceGcharacter.remove(item);
                tvGcharacters.getItems().remove(item);
            }
        });
        clearGcharacterField();
    }

    @FXML
    public void updateCharacterAction() {
        Gcharacter gcharacter = getGcharacterFromField();
        if(gcharacter != null && !tfGcharacterId.getText().isEmpty()) {
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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure ?");
        ButtonType userResponse = alert.showAndWait().orElse(ButtonType.CANCEL);
        if(userResponse == ButtonType.OK) tvGclasses.getSelectionModel().getSelectedItems().forEach(item -> {
            if(item != null) {
                serviceGclass.remove(item);
                tvGclasses.getItems().remove(item);
            }
        });
        clearGclassField();
    }

    @FXML
    public void updateGclassAction() {
        Gclass gclass = getGclassFromField();
        if(gclass != null && !tfGclassId.getText().isEmpty()) {
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

    private Gclass getGclassFromField() {
        boolean validator = tflabel.getText().isEmpty() || tadescription.getText().isEmpty();
        if(validator) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields.");
            alert.show();
            return null;
        }
        Integer id = tfGclassId.getText().isEmpty() ? null : Integer.parseInt(tfGclassId.getText());
        String label = tflabel.getText();
        String description = tadescription.getText();
        return new Gclass(id, label, description);
    }
    private Gcharacter getGcharacterFromField() {
        boolean validator = tfname.getText().isEmpty() || tfhealth.getText().isEmpty() || tfdamage.getText().isEmpty() || cbgclass.getValue() == null;
        if(validator) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields.");
            alert.show();
            return null;
        }
        Integer id = tfGcharacterId.getText().isEmpty() ? null : Integer.parseInt(tfGcharacterId.getText());
        String name = tfname.getText();
        int health = Integer.parseInt(tfhealth.getText());
        float damage = Float.parseFloat(tfdamage.getText());
        Gclass gclass = cbgclass.getValue();

        return new Gcharacter(id, name, health, damage, gclass);
    }

    @FXML
    public void updateSelectedGcharacter() {
        Gcharacter gcharacter = tvGcharacters.getSelectionModel().getSelectedItem();
        if(gcharacter != null) {
            tfGcharacterId.setText(String.valueOf(gcharacter.getId()));
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
            tfGclassId.setText(String.valueOf(gclass.getId()));
            tflabel.setText(gclass.getLabel());
            tadescription.setText(gclass.getDescription());
        }
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
        tfGclassId.setText("");
        tflabel.setText("");
        tadescription.setText("");
    }

    @FXML
    public void clearGcharacterField() {
        tfGcharacterId.setText("");
        tfname.setText("");
        tfhealth.setText("");
        tfdamage.setText("");
        cbgclass.setValue(null);
    }
}