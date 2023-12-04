package com.simtechdata;

import com.simtechdata.easyfxcontrols.containers.AnchorPane;
import com.simtechdata.easyfxcontrols.containers.CVBox;
import com.simtechdata.easyfxcontrols.controls.CImageView;
import com.simtechdata.easyfxcontrols.controls.CText;
import com.simtechdata.easyfxcontrols.fonts.Fonts;
import com.simtechdata.sceneonefx.SceneOne;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;

import java.net.URL;

public class Dialog {


    public Dialog(String message) {
        this.message = message;
        makeControls();
        setControlProperties();
        Platform.runLater(() -> {
            SceneOne.set(sceneId, ap, width, height).newStage().initStyle(StageStyle.TRANSPARENT).centered().alwaysOnTop().build();
            SceneOne.getScene(sceneId).setFill(Color.rgb(0, 0, 125, .01));
            SceneOne.showAndWait(sceneId);
        });
    }

    private final String message;
    private final String sceneId = SceneOne.getRandomSceneId();
    private final double width = 471;
    private final double height = 231;
    private AnchorPane ap;
    private static final URL frame = Dialog.class.getResource("/Dialog.png");
    private static final URL OKUp = Window.class.getResource("/buttons/OkUP.png");
    private static final URL OKDown = Window.class.getResource("/buttons/OkDOWN.png");
    private static final Image imgFrame = new Image(frame.toExternalForm());
    private static final Image imgOKUp = new Image(OKUp.toExternalForm());
    private static final Image imgOKDown = new Image(OKDown.toExternalForm());

    private CImageView ivOK;
    private CText textMessage;


    private void makeControls() {
        ap = new AnchorPane.Builder(width, height).backImage(imgFrame).build();
        textMessage = new CText.Builder(message).font(Fonts.Lato_Black(19), Color.YELLOW).lineSpacing(1.0).bold().build();
        textMessage.setWrappingWidth(395);
        ivOK = new CImageView.Builder(imgOKUp).downImage(imgOKDown).preserveRatio(true).fitWidth(180).build();
        CVBox vbox = new CVBox.Builder(55,textMessage,ivOK).alignment(Pos.CENTER).build();
        ap.addNode(vbox,10,10,10, 10);
    }

    private void setControlProperties() {
        ivOK.setOnMouseClicked(e -> Platform.runLater(this::close));

    }

    private void close() {
        SceneOne.close(sceneId);
    }

}
