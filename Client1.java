package chapter33;



import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client1 extends Application {
    private Label resultLabel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Is it a prime number..");

        Label inputLabel = new Label("Enter a number:");
        TextField inputField = new TextField();
        Button checkButton = new Button("Check Prime Status");
        resultLabel = new Label();

        checkButton.setOnAction(event -> {
            String userInput = inputField.getText().trim();
            if (!userInput.isEmpty()) {
                try {
                    int number = Integer.parseInt(userInput);
                    checkPrime(number);
                } catch (NumberFormatException e) {
                    resultLabel.setText("Invalid input! Please enter a valid number.");
                }
            } else {
                resultLabel.setText("Please enter a number.");
            }
        });

        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(inputLabel, inputField, checkButton, resultLabel);
        Scene scene = new Scene(vBox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void checkPrime(int number) {
        String hostName = "localhost";
        int portNumber = 4444;

        try (Socket socket = new Socket(hostName, portNumber);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println(number);
            String serverResponse = in.readLine();
            resultLabel.setText(serverResponse + "is prime!");
        } catch (UnknownHostException e) {
            resultLabel.setText("Unknown host: " + hostName);
        } catch (IOException e) {
            resultLabel.setText("Error: " + e.getMessage());
        }
    }
}

