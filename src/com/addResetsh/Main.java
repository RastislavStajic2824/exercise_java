/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.addResetsh;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author student
 */
public class Main extends Application {
    
    private final TextTransfer textTransfer = new TextTransfer();
    
    @Override
    public void start(Stage primaryStage) {
        
        CheckBox alwaysOnTop = new CheckBox("Always on top");
        alwaysOnTop.setSelected(true);
        alwaysOnTop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                onTopOrNot(primaryStage, alwaysOnTop);
            }
        });
        
        Button prefix = new Button();
        prefix.setText("Add 'resetsh' PREFIX");
        prefix.setMinSize(200, 45);
        prefix.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                textTransfer.addPrefixResetshToClipboardString();
            }
        });
        
        Button resetsh = new Button();
        resetsh.setText("Only 'resetsh'");
        resetsh.setMinSize(200, 45);
        resetsh.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                textTransfer.addResetshToClipboard();
            }
        });
        
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.getChildren().add(prefix);
        root.getChildren().add(resetsh);
        root.getChildren().add(alwaysOnTop);
        
        Scene scene = new Scene(root, 280, 200);
        
        primaryStage.setResizable(false);
        primaryStage.setTitle("Make 'resetsh' easier!");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        onTopOrNot(primaryStage, alwaysOnTop);
        styleRootPane(root);
        stylePrimaryStage(primaryStage);
        focusedOrNot(primaryStage);
    }
    
    private void onTopOrNot(Stage stage, CheckBox checkbox) {
        if (checkbox.isSelected()) {
            stage.setAlwaysOnTop(true);
        }
        else {
            stage.setAlwaysOnTop(false);
        }
    }
    
    private void focusedOrNot(Stage stage) {
        stage.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                stylePrimaryStage(stage);
            }
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private class TextTransfer implements ClipboardOwner{

        public TextTransfer() {
        }

        @Override
        public void lostOwnership(Clipboard clipboard, Transferable contents) {
            
        }
        
        void addPrefixResetshToClipboardString() {
            String aString = getClipboardContents();
            aString = "resetsh" + "\n" + aString;
            setClipboardContents(aString);
        }
        
        void addResetshToClipboard() {
            String aString = "resetsh" + "\n";
            setClipboardContents(aString);
        }
        
        void setClipboardContents(String aString) {
            StringSelection stringSelection = new StringSelection(aString);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, this);
        }
        
        String getClipboardContents() {
            String result = "";
            
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            
            Transferable contents = clipboard.getContents(null);
            boolean hasTransferableText = (contents != null) && contents.isDataFlavorSupported(DataFlavor.stringFlavor);
            
            if (hasTransferableText) {
                try {
                    result = (String) contents.getTransferData(DataFlavor.stringFlavor);
                } catch (UnsupportedFlavorException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            return result;
        }
    }
    
    // styling root pane
    private void styleRootPane(VBox root) {
        root.setStyle("-fx-background-color: rgba(233, 150, 122, 0.7);");
//        root.setStyle("-fx-background-color: rgba(216, 191, 216, 0.7);");
//        root.setStyle("-fx-background-color: rgba(70, 130, 180, 0.7);");
//        Color.DARKSALMON;
//        Color.THISTLE;
//        Color.STEELBLUE;
    }
    
    // styling root pane
    private void stylePrimaryStage(Stage stage) {
        if (stage.isFocused()) {
            stage.setOpacity(.85);
        }
        else {
            stage.setOpacity(.7);
        }
    }
}
