package com.simtechdata;

import com.simtechdata.fonts.Fonts;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;

public class Dialog {


    public Dialog(String message) {
        this.message = message;
        makeControls();
        this.stage = new Stage();
        this.scene = new Scene(ap);
        this.stage.initStyle(StageStyle.TRANSPARENT);
        this.stage.centerOnScreen();
        this.stage.setAlwaysOnTop(true);
        this.stage.setWidth(width);
        this.stage.setHeight(height);
        this.scene.setFill(Color.rgb(0, 0, 125, .01));
        this.stage.setScene(this.scene);
        this.stage.showAndWait();
    }

    private final Stage stage;
    private final Scene scene;
    private final String message;
    private final double width = 471;
    private final double height = 231;
    private AnchorPane ap;
    private static final URL OKUp = Window.class.getResource("/buttons/OkUP.png");
    private static final URL OKDown = Window.class.getResource("/buttons/OkDOWN.png");
    private static final Image imgOKUp = new Image(OKUp.toExternalForm());
    private static final Image imgOKDown = new Image(OKDown.toExternalForm());

    private ImageView ivOK;
    private Text textMessage;


    private void makeControls() {
        ap = new AnchorPane();
        ap.setPrefWidth(width);
        ap.setPrefHeight(height);
        textMessage = newText(message, Fonts.Lato_Black(19), Color.YELLOW, 1.0, true);
        textMessage.setWrappingWidth(395);
        ivOK = newImageView(imgOKUp, imgOKDown, 180, e-> Platform.runLater(this::close));
        VBox vbox = newVBox(55, Pos.CENTER, new Insets(0), textMessage, ivOK);
        addNode(vbox, 10, 10, 10, 10);
    }

    private Text newText(String msg, Font font, Color color, double lineSpacing, boolean bold) {
        Text text = new Text(msg);
        text.setFont(font);
        text.setLineSpacing(lineSpacing);
        text.setStyle(bold ? "-fx-font-weight: bold;" : "");
        text.setFill(color);
        return text;
    }
    private VBox newVBox(double spacing, Pos alignment, Insets padding, Node... nodes) {
        VBox box = new VBox(spacing, nodes);
        box.setAlignment(alignment);
        box.setPadding(padding);
        return box;
    }
    private void addNode(Node node, double left, double top, double right, double bottom) {
        ap.getChildren().add(node);
        AnchorPane.setLeftAnchor(node, left);
        AnchorPane.setTopAnchor(node, top);
        AnchorPane.setRightAnchor(node, right);
        AnchorPane.setBottomAnchor(node, bottom);
    }

    private ImageView newImageView(Image up, Image down, double size, EventHandler<? super MouseEvent> mouseClicked) {
        ImageView iv = new ImageView(up);
        if (down != null) {
            iv.setOnMousePressed(e -> iv.setImage(down));
            iv.setOnMouseReleased(e -> iv.setImage(up));
        }
        if(mouseClicked != null) {
            iv.setOnMouseClicked(mouseClicked);
        }
        iv.setPreserveRatio(true);
        iv.setFitWidth(size);
        return iv;
    }

    private void close() {
        this.stage.close();
    }

}
