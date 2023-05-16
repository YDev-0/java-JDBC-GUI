package ma.ydev0.javajdbcgui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import ma.ydev0.javajdbcgui.entities.Gcharacter;
import ma.ydev0.javajdbcgui.entities.Gclass;

public class WindowController {

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
    TextField tfdescription;
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


    @FXML
    protected void onCloseItemClick() {

    }
}