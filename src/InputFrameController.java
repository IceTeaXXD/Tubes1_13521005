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
 * The InputFrameController class.  It controls input from the users and validates it.
 * If validation is successful, the Adjacency game screen will pop up in a different window.
 *
 * @author Jedid Ahn
 *
 */
public class InputFrameController{

    public CheckBox isBotFirst;
    @FXML
    private TextField player1;

    @FXML
    private TextField player2;

    @FXML
    private ComboBox<String> numberOfRounds;

    @FXML
    private ComboBox<String> botAlgorithm;
    @FXML
    private ComboBox<String> gameMode;
    @FXML
    private Label player2text;
    @FXML
    private Label player1text;
    @FXML
    private Label botAlgorithmtext;
    @FXML
    private Label botAlgorithmtext2;
    @FXML
    private ComboBox<String> botAlgorithm2;

    /**
     * Initialize the dropdown ComboBox with a list of items that are allowed to be selected.
     * Select the first item in the list as the default value of the dropdown.
     *
     */
    @FXML
    private void initialize(){
        ObservableList<String> numberOfRoundsDropdown = FXCollections.observableArrayList(
                "", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
                "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28");
        this.numberOfRounds.setItems(numberOfRoundsDropdown);
        this.numberOfRounds.getSelectionModel().select(0);
        this.botAlgorithm.setItems(FXCollections.observableArrayList("Random Move", "Minimax Alpha Beta Pruning", "Hill Climb"));
        this.botAlgorithm2.setItems(FXCollections.observableArrayList("Random Move", "Minimax Alpha Beta Pruning", "Hill Climb"));
        this.gameMode.setItems(FXCollections.observableArrayList("Human vs Human", "Human vs Bot", "Bot vs Bot"));
        this.botAlgorithmtext2.setVisible(false);
        this.botAlgorithm2.setVisible(false);
        this.gameMode.getSelectionModel().select(1);
        this.gameMode.setOnAction(event -> {
            // Call your function here
            if (this.gameMode.getValue().equals("Human vs Human")){
                this.humanselection();
            }
            if (this.gameMode.getValue().equals("Human vs Bot")){
                this.humanbotselection();
            }
            if (this.gameMode.getValue().equals("Bot vs Bot")){
                this.botselection();
            }
        });
    }
    @FXML
    private void humanselection(){
        this.player1text.setText("Player Name (X):");
        this.player2text.setText("Player Name (O):");
        this.botAlgorithm.setVisible(false);
        this.botAlgorithmtext.setVisible(false);
        this.botAlgorithmtext.setText("Bot Algorithm: ");
        this.botAlgorithm2.setVisible(false);
        this.botAlgorithmtext2.setVisible(false);
    }
    private void botselection(){
        this.player1text.setText("Bot Name (X):");
        this.player2text.setText("Bot Name (O):");
        this.botAlgorithm.setVisible(true);
        this.botAlgorithmtext.setVisible(true);
        this.botAlgorithmtext.setText("Bot (O) Algorithm: ");
        this.botAlgorithm2.setVisible(true);
        this.botAlgorithmtext2.setVisible(true);
    }
    private void humanbotselection(){
        this.player1text.setText("Player Name (X):");
        this.player2text.setText("Bot Name (O):");
        this.botAlgorithm.setVisible(true);
        this.botAlgorithmtext.setVisible(true);
        this.botAlgorithmtext.setText("Bot Algorithm: ");
        this.botAlgorithm2.setVisible(false);
        this.botAlgorithmtext2.setVisible(false);
    }

    /**
     * Reset player1 and player2 text fields and reset numberOfRounds dropdown to default value
     * if reset button is clicked.
     *
     */
    @FXML
    private void reset(){
        this.player1.setText("");
        this.player2.setText("");
        this.numberOfRounds.getSelectionModel().select(0);
    }


    /**
     * Open OutputFrame controlled by OutputFrameController if play button is clicked and
     * all input have been successfully validated.
     *
     * @exception IOException To load the FXMLLoader to open the Adjacency game screen (output screen).
     *
     */
    @FXML
    private void play() throws IOException{
        if (this.isInputFieldValidated()){
            // Close primary stage/input frame.
            Stage primaryStage = (Stage) this.player1.getScene().getWindow();
            primaryStage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("OutputFrame.fxml"));
            Parent root = loader.load();

            // Get controller of output frame and pass input including player names and number of rounds chosen.
            OutputFrameController outputFC = loader.getController();
            
            Stage secondaryStage = new Stage();
            secondaryStage.setTitle("Game Board Display");
            secondaryStage.setScene(new Scene(root));
            secondaryStage.setResizable(true);
            secondaryStage.show();
            
            // Bot Algorithm
            // 1. Random Move
            // 2. ...
            // 3. ...
            if (this.gameMode.getValue().equals("Human vs Human")){
                System.out.println("Human vs Human");
                outputFC.getInput(this.gameMode.getValue(),this.player1.getText(), this.player2.getText(), this.numberOfRounds.getValue(), this.isBotFirst.isSelected(), 0, 0);
            }
            
            else if (this.gameMode.getValue().equals("Human vs Bot")){
                int botAlgorithm = 0;
                if (this.botAlgorithm.getValue().equals("Random Move")) {
                    botAlgorithm = 1;
                } else if (this.botAlgorithm.getValue().equals("Minimax Alpha Beta Pruning")) {
                    botAlgorithm = 2;
                } else if (this.botAlgorithm.getValue().equals("Hill Climb")){
                    botAlgorithm = 3;
                }
                outputFC.getInput(this.gameMode.getValue(),this.player1.getText(), this.player2.getText(), this.numberOfRounds.getValue(), this.isBotFirst.isSelected(), botAlgorithm, 0);
            }
            
            else if (this.gameMode.getValue().equals("Bot vs Bot")){
                int botAlgorithm = 0;
                if (this.botAlgorithm.getValue().equals("Random Move")) {
                    botAlgorithm = 1;
                } else if (this.botAlgorithm.getValue().equals("Minimax Alpha Beta Pruning")) {
                    botAlgorithm = 2;
                } else if (this.botAlgorithm.getValue().equals("Hill Climb")){
                    botAlgorithm = 3;
                }
                
                int botAlgorithm2 = 0;
                if (this.botAlgorithm2.getValue().equals("Random Move")) {
                    botAlgorithm2 = 1;
                } else if (this.botAlgorithm2.getValue().equals("Minimax Alpha Beta Pruning")) {
                    botAlgorithm2 = 2;
                } else if (this.botAlgorithm2.getValue().equals("Hill Climb")){
                    botAlgorithm2 = 3;
                }
                outputFC.getInput(this.gameMode.getValue(),this.player1.getText(), this.player2.getText(), this.numberOfRounds.getValue(), false, botAlgorithm, botAlgorithm2);
            }

            // outputFC.getInput(this.player1.getText(), this.player2.getText(), this.numberOfRounds.getValue(), this.isBotFirst.isSelected(), botAlgorithm);

            // Open the new frame.
        }
    }


    /**
     * Return whether all input fields have been successfully validated or not.
     *
     * @return boolean
     *
     */
    private boolean isInputFieldValidated() {
        String playerX = this.player1.getText();
        String playerO = this.player2.getText();
        String roundNumber = this.numberOfRounds.getValue();

        if (playerX.length() == 0) {
            new Alert(Alert.AlertType.ERROR, "Player 1 name is blank.").showAndWait();
            return false;
        }

        if (playerO.length() == 0) {
            new Alert(Alert.AlertType.ERROR, "Player 2 name is blank.").showAndWait();
            return false;
        }

        if (playerX.equals(playerO)){
            new Alert(Alert.AlertType.ERROR, "Player 1 and Player 2 cannot have the same name.").showAndWait();
            return false;
        }

        if (roundNumber.length() == 0) {
            new Alert(Alert.AlertType.ERROR, "Number of rounds dropdown menu is blank.").showAndWait();
            return false;
        }

        if (this.gameMode.getValue().equals("Human vs Bot") && this.botAlgorithm.getValue() == null) {
            new Alert(Alert.AlertType.ERROR, "Algorithm dropdown menu is blank.").showAndWait();
            return false;
        }

        return true;
    }
}