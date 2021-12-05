package com.coderpwh.grammar_processor;

import com.coderpwh.grammar_processor.service.Service4Result;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private TextArea in_text;
    @FXML
    private TextArea result;
    @FXML
    public VBox vbox;
    public StringBuilder code;
    private Service4Result service = new Service4Result();
    @FXML
    protected void onHelloButtonClick() throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        Window stage = vbox.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(selectedFile));
            code = new StringBuilder();
            bufferedReader.lines().forEach(e->{
                code.append(e).append("\n");
            });
            in_text.setText(code.toString());
        }
    }
    @FXML
    protected void parse() {
        try {
            String r = service.create(in_text.getText());
            System.out.println("结果："+r);
            result.setText(r);
        } catch (Exception e) {
            result.setText("error msg:"+e.getMessage()+",error cause:"+e.getCause());
            e.printStackTrace();
        }
    }
}