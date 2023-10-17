import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;

/**
 * The InputFrameController class. It controls input from the users and
 * validates it.
 * If validation is successful, the Adjacency game screen will pop up in a
 * different window.
 *
 * @author Jedid Ahn
 *
 */
public class InputFrameController {

    // GENERAL
    @FXML
    private ComboBox<String> gameMode;

    @FXML
    private ComboBox<String> numberOfRounds;

    // HUMAN VS HUMAN
    @FXML
    private TextField playerX;

    @FXML
    private TextField playerO;

    @FXML
    private Label playerXtext;

    @FXML
    private Label playerOtext;

    public CheckBox isPlayerOFirst;

    @FXML
    private Label isPlayerOFirsttext;

    // HUMAN VS BOT
    @FXML
    private TextField player;

    @FXML
    private TextField bot;

    @FXML
    private ComboBox<String> botAlgorithm;

    public CheckBox isBotFirst;

    @FXML
    private Label playertext;

    @FXML
    private Label bottext;

    @FXML
    private Label botAlgorithmtext;

    @FXML
    private Label botgoesfirsttext;

    // BOT VS BOT
    @FXML
    private TextField botX;

    @FXML
    private TextField botO;

    @FXML
    private ComboBox<String> botAlgorithmX;

    @FXML
    private ComboBox<String> botAlgorithmO;

    @FXML
    private Label botXtext;

    @FXML
    private Label botOtext;

    @FXML
    private Label botAlgorithmtextX;

    @FXML
    private Label botAlgorithmtextO;

    /**
     * Initialize the dropdown ComboBox with a list of items that are allowed to be
     * selected.
     * Select the first item in the list as the default value of the dropdown.
     *
     */
    @FXML
    private void initialize() {
        this.hideAll();
        ObservableList<String> numberOfRoundsDropdown = FXCollections.observableArrayList(
                "", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
                "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28");
        this.numberOfRounds.setItems(numberOfRoundsDropdown);
        this.numberOfRounds.getSelectionModel().select(0);
        this.gameMode.setItems(FXCollections.observableArrayList("Human vs Human", "Human vs Bot", "Bot vs Bot"));
        this.gameMode.getSelectionModel().select(1);
        this.humanbotselection();
        this.gameMode.setOnAction(event -> {
            if (this.gameMode.getValue().equals("Human vs Human")) {
                this.humanselection();
            }
            if (this.gameMode.getValue().equals("Human vs Bot")) {
                this.humanbotselection();
            }
            if (this.gameMode.getValue().equals("Bot vs Bot")) {
                this.botselection();
            }
        });
        this.botAlgorithm.setItems(FXCollections.observableArrayList("Random Move", "Minimax Alpha Beta Pruning",
                "Hill Climb", "Genetic-Minimax"));
        this.botAlgorithmX.setItems(FXCollections.observableArrayList("Random Move", "Minimax Alpha Beta Pruning",
                "Hill Climb", "Genetic-Minimax"));
        this.botAlgorithmO.setItems(FXCollections.observableArrayList("Random Move", "Minimax Alpha Beta Pruning",
                "Hill Climb", "Genetic-Minimax"));
    }

    private void hideAll() {
        this.playerX.setVisible(false);
        this.playerO.setVisible(false);
        this.isPlayerOFirst.setVisible(false);
        this.player.setVisible(false);
        this.bot.setVisible(false);
        this.botAlgorithm.setVisible(false);
        this.isBotFirst.setVisible(false);
        this.botX.setVisible(false);
        this.botO.setVisible(false);
        this.botAlgorithmX.setVisible(false);
        this.botAlgorithmO.setVisible(false);
        this.playerXtext.setVisible(false);
        this.playerOtext.setVisible(false);
        this.playertext.setVisible(false);
        this.bottext.setVisible(false);
        this.botXtext.setVisible(false);
        this.botOtext.setVisible(false);
        this.botAlgorithmtext.setVisible(false);
        this.botAlgorithmtextX.setVisible(false);
        this.botAlgorithmtextO.setVisible(false);
        this.isPlayerOFirst.setVisible(false);
        this.isPlayerOFirsttext.setVisible(false);
        this.botgoesfirsttext.setVisible(false);
    }

    private void humanselection() {
        // SHOW MENU FOR HUMAN VS HUMAN AND HIDE OTHERS
        this.hideAll();
        this.playerX.setVisible(true);
        this.playerO.setVisible(true);
        this.isPlayerOFirst.setVisible(true);
        this.playerXtext.setVisible(true);
        this.playerOtext.setVisible(true);
        this.isPlayerOFirsttext.setVisible(true);
    }

    private void botselection() {
        // SHOW MENU FOR BOT VS BOT AND HIDE OTHERS
        this.hideAll();
        this.botX.setVisible(true);
        this.botO.setVisible(true);
        this.botAlgorithmX.setVisible(true);
        this.botAlgorithmO.setVisible(true);
        this.botXtext.setVisible(true);
        this.botOtext.setVisible(true);
        this.botAlgorithmtextX.setVisible(true);
        this.botAlgorithmtextO.setVisible(true);
    }

    private void humanbotselection() {
        // SHOW MENU FOR HUMAN VS BOT AND HIDE OTHERS
        this.hideAll();
        this.player.setVisible(true);
        this.bot.setVisible(true);
        this.botAlgorithm.setVisible(true);
        this.botAlgorithmtext.setVisible(true);
        this.isBotFirst.setVisible(true);
        this.playertext.setVisible(true);
        this.bottext.setVisible(true);
        this.botgoesfirsttext.setVisible(true);
    }

    /**
     * Reset player1 and player2 text fields and reset numberOfRounds dropdown to
     * default value
     * if reset button is clicked.
     *
     */
    @FXML
    private void reset() {
        // reset all input fields
        this.playerX.setText("");
        this.playerO.setText("");
        this.numberOfRounds.getSelectionModel().select(0);
        this.player.setText("");
        this.bot.setText("");
        this.botAlgorithm.getSelectionModel().select(0);
        this.isBotFirst.setSelected(false);
        this.botX.setText("");
        this.botO.setText("");
        this.botAlgorithmX.getSelectionModel().select(0);
        this.botAlgorithmO.getSelectionModel().select(0);
    }

    /**
     * Open OutputFrame controlled by OutputFrameController if play button is
     * clicked and
     * all input have been successfully validated.
     *
     * @exception IOException To load the FXMLLoader to open the Adjacency game
     *                        screen (output screen).
     *
     */
    @FXML
    private void play() throws IOException {
        if (this.isInputFieldValidated()) {
            // Close primary stage/input frame.
            Stage primaryStage = (Stage) this.playerX.getScene().getWindow();
            primaryStage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("OutputFrame.fxml"));
            Parent root = loader.load();

            // Get controller of output frame and pass input including player names and
            // number of rounds chosen.
            OutputFrameController outputFC = loader.getController();

            Stage secondaryStage = new Stage();
            secondaryStage.setTitle("Game Board Display");
            secondaryStage.setScene(new Scene(root));
            secondaryStage.setResizable(true);
            secondaryStage.show();

            // Bot Algorithm
            // 1. Random Move
            // 2. Minimax Alpha Beta Pruning
            // 3. Hill Climb
            // 4. Genetic-Minimax
            if (this.gameMode.getValue().equals("Human vs Human")) {
                System.out.println("Human vs Human");
                outputFC.getInput(this.gameMode.getValue(), this.playerX.getText(), this.playerO.getText(),
                        this.numberOfRounds.getValue(), this.isPlayerOFirst.isSelected(), 0, 0);
            }

            else if (this.gameMode.getValue().equals("Human vs Bot")) {
                int botAlgorithm = 0;
                if (this.botAlgorithm.getValue().equals("Random Move")) {
                    botAlgorithm = 1;
                } else if (this.botAlgorithm.getValue().equals("Minimax Alpha Beta Pruning")) {
                    botAlgorithm = 2;
                } else if (this.botAlgorithm.getValue().equals("Hill Climb")) {
                    botAlgorithm = 3;
                } else if (this.botAlgorithm.getValue().equals("Genetic-Minimax")) {
                    botAlgorithm = 4;
                }
                outputFC.getInput(this.gameMode.getValue(), this.player.getText(), this.bot.getText(),
                        this.numberOfRounds.getValue(), this.isBotFirst.isSelected(), botAlgorithm, 0);
            }

            else if (this.gameMode.getValue().equals("Bot vs Bot")) {
                int botAlgorithmX = 0;
                if (this.botAlgorithmX.getValue().equals("Random Move")) {
                    botAlgorithmX = 1;
                } else if (this.botAlgorithmX.getValue().equals("Minimax Alpha Beta Pruning")) {
                    botAlgorithmX = 2;
                } else if (this.botAlgorithmX.getValue().equals("Hill Climb")) {
                    botAlgorithmX = 3;
                } else if (this.botAlgorithmX.getValue().equals("Genetic-Minimax")) {
                    botAlgorithmX = 4;
                }

                int botAlgorithmO = 0;
                if (this.botAlgorithmO.getValue().equals("Random Move")) {
                    botAlgorithmO = 1;
                } else if (this.botAlgorithmO.getValue().equals("Minimax Alpha Beta Pruning")) {
                    botAlgorithmO = 2;
                } else if (this.botAlgorithmO.getValue().equals("Hill Climb")) {
                    botAlgorithmO = 3;
                } else if (this.botAlgorithmO.getValue().equals("Genetic-Minimax")) {
                    botAlgorithmO = 4;
                }
                outputFC.getInput(this.gameMode.getValue(), this.botX.getText(), this.botO.getText(),
                        this.numberOfRounds.getValue(), false, botAlgorithmX, botAlgorithmO);
            }
        }
    }

    /**
     * Return whether all input fields have been successfully validated or not.
     *
     * @return boolean
     *
     */
    private boolean isInputFieldValidated() {
        if (this.numberOfRounds.getValue().length() == 0) {
            new Alert(Alert.AlertType.ERROR, "Number of rounds dropdown menu is blank.").showAndWait();
            return false;
        }

        if (this.gameMode.getValue().equals("Human vs Human")) {
            if (this.playerX.getText().length() == 0) {
                new Alert(Alert.AlertType.ERROR, "Player X text field is blank.").showAndWait();
                return false;
            }
            if (this.playerO.getText().length() == 0) {
                new Alert(Alert.AlertType.ERROR, "Player O text field is blank.").showAndWait();
                return false;
            }
            if (this.playerX.getText().equals(this.playerO.getText())) {
                new Alert(Alert.AlertType.ERROR, "Player X and Player O cannot have the same name.").showAndWait();
                return false;
            }
        }

        else if (this.gameMode.getValue().equals("Human vs Bot")) {
            if (this.player.getText().length() == 0) {
                new Alert(Alert.AlertType.ERROR, "Player text field is blank.").showAndWait();
                return false;
            }
            if (this.bot.getText().length() == 0) {
                new Alert(Alert.AlertType.ERROR, "Bot text field is blank.").showAndWait();
                return false;
            }
            if (this.botAlgorithm.getValue().length() == 0) {
                new Alert(Alert.AlertType.ERROR, "Bot Algorithm dropdown menu is blank.").showAndWait();
                return false;
            }
            if (this.player.getText().equals(this.bot.getText())) {
                new Alert(Alert.AlertType.ERROR, "Player and Bot cannot have the same name.").showAndWait();
                return false;
            }
        }

        else if (this.gameMode.getValue().equals("Bot vs Bot")) {
            if (this.botX.getText().length() == 0) {
                new Alert(Alert.AlertType.ERROR, "Bot X text field is blank.").showAndWait();
                return false;
            }
            if (this.botO.getText().length() == 0) {
                new Alert(Alert.AlertType.ERROR, "Bot O text field is blank.").showAndWait();
                return false;
            }
            if (this.botX.getText().equals(this.botO.getText())) {
                new Alert(Alert.AlertType.ERROR, "Bot X and Bot O cannot have the same name.").showAndWait();
                return false;
            }
            if (this.botAlgorithmX.getValue().length() == 0) {
                new Alert(Alert.AlertType.ERROR, "Bot X Algorithm dropdown menu is blank.").showAndWait();
                return false;
            }
            if (this.botAlgorithmO.getValue().length() == 0) {
                new Alert(Alert.AlertType.ERROR, "Bot O Algorithm dropdown menu is blank.").showAndWait();
                return false;
            }
        }
        return true;
    }
}