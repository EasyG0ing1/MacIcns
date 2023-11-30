package com.simtechdata.macicns;

import com.simtechdata.easyfxcontrols.containers.AnchorPane;
import com.simtechdata.easyfxcontrols.containers.CHBox;
import com.simtechdata.easyfxcontrols.containers.CVBox;
import com.simtechdata.easyfxcontrols.controls.CImageView;
import com.simtechdata.easyfxcontrols.controls.CText;
import com.simtechdata.easyfxcontrols.fonts.Fonts;
import com.simtechdata.macicns.settings.AppSettings;
import com.simtechdata.sceneonefx.SceneOne;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.StageStyle;
import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Window {

    public Window() {
        makeControls();
        setControlProperties();
        SceneOne.set(sceneId, ap, width, height).initStyle(StageStyle.TRANSPARENT).centered().build();
        SceneOne.getScene(sceneId).setFill(Color.rgb(0, 0, 125));
        SceneOne.show(sceneId);
    }

    private final String sceneId = SceneOne.getRandomSceneId();
    private final double width = 750;
    private final double height = 650;
    private AnchorPane ap;
    private static final URL frame = Window.class.getResource("/Frame.png");
    private static final URL makeFileUp = Window.class.getResource("/buttons/MakeFileUP.png");
    private static final URL makeFileDown = Window.class.getResource("/buttons/MakeFileDOWN.png");
    private static final URL loadImageUp = Window.class.getResource("/buttons/LoadImageUP.png");
    private static final URL loadImageDown = Window.class.getResource("/buttons/LoadImageDOWN.png");
    private static final URL closeUp = Window.class.getResource("/buttons/CloseUP.png");
    private static final URL closeDown = Window.class.getResource("/buttons/CloseDOWN.png");
    private static final URL viewUp = Window.class.getResource("/buttons/ViewUP.png");
    private static final URL viewDown = Window.class.getResource("/buttons/ViewDOWN.png");
    private static final URL logo = Window.class.getResource("/icons/Logo.png");

    private static final Image imgFrame = new Image(frame.toExternalForm());
    private static final Image imgMakeFileUp = new Image(makeFileUp.toExternalForm());
    private static final Image imgMakeFileDown = new Image(makeFileDown.toExternalForm());
    private static final Image imgLoadImageUp = new Image(loadImageUp.toExternalForm());
    private static final Image imgLoadImageDown = new Image(loadImageDown.toExternalForm());
    private static final Image imgCloseUp = new Image(closeUp.toExternalForm());
    private static final Image imgCloseDown = new Image(closeDown.toExternalForm());
    private static final Image imgViewDown = new Image(viewDown.toExternalForm());
    private static final Image imgViewUp = new Image(viewUp.toExternalForm());
    private static final Image imgLogo = new Image(logo.toExternalForm());

    private CImageView ivMakeFile;
    private CImageView ivLoadImage;
    private CImageView ivClose;
    private CImageView ivView;
    private CVBox vbox;

    private CText textDirections;
    private CText textResults;
    private CText textFileLabel;
    private CText textFilename;
    private CText textPathLabel;
    private CText textICNSLabel;
    private CText textICNSName;
    private CText textFinalPath;
    private CText textPath;
    private CText text3;
    private CText text4;
    private Path fileChosen;
    private VBox finalText;

    private void makeControls() {
        ap = new AnchorPane.Builder(width, height).backImage(imgFrame).build();

        textResults = new CText.Builder("").font(Fonts.Lato_Black(20), Color.GREENYELLOW).bold().build();
        textFinalPath = new CText.Builder("/Users/michael/temp/icons").font(Fonts.Lato_Black(20), Color.YELLOW).bold().visible(false).build();
        textFileLabel = new CText.Builder("File: ").font(Fonts.Fira_Code_Regular(20), Color.WHITE).lineSpacing(1.0).bold().build();
        textPathLabel = new CText.Builder("Path: ").font(Fonts.Fira_Code_Regular(20), Color.WHITE).lineSpacing(1.0).bold().build();
        textICNSLabel = new CText.Builder("ICNS: ").font(Fonts.Fira_Code_Regular(20), Color.WHITE).bold().lineSpacing(1.0).build();
        textFilename = new CText.Builder("").font(Fonts.Fira_Code_Bold(20), Color.WHITE).bold().lineSpacing(1.0).build();
        textICNSName = new CText.Builder("").font(Fonts.Fira_Code_Bold(20), Color.WHITE).bold().lineSpacing(1.0).build();
        textPath = new CText.Builder("").font(Fonts.Fira_Code_Regular(13), Color.WHITE).lineSpacing(1.0).build();
        text3 = new CText.Builder("\n").font(Fonts.Fira_Code_Regular(22), Color.WHITE).lineSpacing(1.0).build();
        text4 = new CText.Builder("\n").font(Fonts.Fira_Code_Regular(18), Color.WHITE).lineSpacing(1.0).build();
        ivMakeFile = new CImageView.Builder(imgMakeFileUp).downImage(imgMakeFileDown).preserveRatio(true).fitWidth(180).build();
        ivLoadImage = new CImageView.Builder(imgLoadImageUp).downImage(imgLoadImageDown).preserveRatio(true).fitWidth(180).build();
        ivClose = new CImageView.Builder(imgCloseUp).downImage(imgCloseDown).preserveRatio(true).fitWidth(180).build();
        ivView = new CImageView.Builder(imgViewUp).downImage(imgViewDown).preserveRatio(true).fitWidth(250).hidden().build();
        CImageView ivTitle = new CImageView.Builder(imgLogo).preserveRatio(true).fitWidth(200).build();
        textDirections = new CText.Builder("Click Load Image and select a 1024 x 1024 image file\n").font(Fonts.Lato_Heavy(24), Color.WHITE).bold().build();
        CHBox boxButtons = new CHBox.Builder(25, ivLoadImage, ivMakeFile, ivClose).alignment(Pos.CENTER).padding(5).build();
        finalText = showFinalText();
        vbox = new CVBox.Builder(10, ivTitle, textResults, textDirections, finalText).size(width * .95, height * .9).alignment(Pos.CENTER).padding(new Insets(10, 10, 45, 10)).build();
        finalText.setVisible(false);
        vbox.setFillWidth(true);
        vbox.setPrefWidth(width);
        CVBox boxTest = new CVBox.Builder(textFinalPath).alignment(Pos.CENTER).build();
        ap.getChildren().add(vbox);
        ap.addNode(boxButtons, 0, 0, -1, 20);
        ap.addNode(ivView, -1, ((width / 2) - 125), ((height / 2) + 35), -1);
        ap.addNode(boxTest, 0, 0, ((height / 2) + 140), -1);
    }

    private CVBox showFinalText() {
        TextFlow box1 = new TextFlow(textFileLabel, textFilename);
        TextFlow box2 = new TextFlow(textICNSLabel, textICNSName);
        TextFlow box3 = new TextFlow(textPathLabel, textPath);
        return new CVBox.Builder(5, box1, box2, box3, text3, text4).alignment(Pos.CENTER).padding(new Insets(0, 0, 0, 30)).build();
    }

    private void setControlProperties() {
        ivMakeFile.setOnMouseClicked(e -> Platform.runLater(this::createFile));
        ivLoadImage.setOnMouseClicked(e -> Platform.runLater(this::loadFile));
        ivClose.setOnMouseClicked(e -> Platform.runLater(this::close));
        ivView.setOnMouseClicked(e -> Platform.runLater(this::openPreview));
    }

    private void setLabels() {
        textFileLabel.setText("File: ");
        textICNSLabel.setText("ICNS: ");
        textPathLabel.setText("Path: ");
    }

    private void clearLabels() {
        textFileLabel.setText("");
        textICNSLabel.setText("");
        textPathLabel.setText("");
    }

    private void createFile() {
        new Thread(() -> {
            if (new ProcessFile(fileChosen, true).run()) {
                final String msg = "File created successfully!";
                Platform.runLater(() -> {
                    clearText();
                    clearLabels();
                    textFinalPath.setText(ProcessFile.getIcnsFilePath());
                    textResults.setText(msg);
                    ivView.show();
                    textFinalPath.show();
                });
            }
        }).start();
    }

    private void loadFile() {
        clearText();
        Platform.runLater(() -> {
            ivView.hide();
            textFinalPath.hide();
        });
        FileChooser fc = new FileChooser();
        File startFolder = getFolder();
        if (!startFolder.exists()) {
            startFolder = new File(System.getProperty("user.home"));
        }
        fc.setInitialDirectory(startFolder);
        File file = fc.showOpenDialog(null);
        if (file != null) {
            setFolder(file.getParentFile());
            if (ImageChecker.isValid(file)) {
                fileChosen = file.toPath();
                if (imageOK()) {
                    String rootPath = file.getParent();
                    text3.setColor(Color.WHITE);
                    text4.setColor(Color.WHITE);
                    textFilename.setText(file.getName());
                    textPath.setText(rootPath);
                    String baseName = FilenameUtils.getBaseName(file.getAbsolutePath());
                    String iconFileName = baseName + ".icns";
                    textICNSName.setText(iconFileName);
                    Path destinationFile = Paths.get(rootPath, iconFileName);
                    if (destinationFile.toFile().exists()) {
                        setLabels();
                        text3.setText("ICNS file already exists");
                        text3.setFill(Color.YELLOW);
                        text3.setFont(Fonts.Fira_Code_Regular(20));
                        text3.setFont(Fonts.Fira_Code_Regular(18));
                        text4.setText("Clicking on Make File will overwrite the file");
                        text4.setFill(Color.YELLOW);
                    }
                    else {
                        setLabels();
                        text3.setText("Click Make File to create the ICNS file");
                        text3.setFill(Color.YELLOW);
                        text3.setFont(Fonts.Fira_Code_Regular(22));
                        text4.setText("");
                    }
                }
                else {
                    clearLabels();
                    text3.setText("Selected file is not 1024 x 1024 pixels");
                    vbox.setPadding(new Insets(15, 10, 20, 10));
                    text3.setFont(Fonts.Fira_Code_Regular(22));
                    text3.setFill(Color.ORANGERED);
                    text4.setText("");
                }
            }
            else {
                clearLabels();
                text3.setText("Selected file is not a valid image type");
                text3.setFont(Fonts.Fira_Code_Bold(20));
                text3.setFill(Color.YELLOW);
                text4.setText("Must be: PNG, JPEG, GIF, TIFF, BMP or SVG");
                text4.setFill(Color.YELLOW);
                text4.setFont(Fonts.Fira_Code_Bold(20));
            }
            finalText.setVisible(true);
        }
    }

    private void clearText() {
        textResults.setText("");
        text3.setText("");
        text4.setText("");
        textPath.setText("");
        textFilename.setText("");
        textICNSName.setText("");
    }

    private void openPreview() {
        try {
            File file = new File(ProcessFile.getIcnsFilePath());
            if(file.exists())
                Desktop.getDesktop().open(file);
            else {
                Platform.runLater(() -> textFinalPath.setText(getNoFileMessage()));
            }
        } catch (IOException e) {
            Platform.runLater(() -> textFinalPath.setText("Problem with finding or opening Preview"));
        }
    }

    @NotNull
    private static String getNoFileMessage() {
        String filePath = ProcessFile.getIcnsFilePath();
        String exist = "File does not exist!";
        int lenPath = filePath.length();
        int lenExist = exist.length();
        int delta = Math.abs(lenExist - lenPath);
        int count = delta + (delta / 4);
        if((lenPath > lenExist) || lenPath == lenExist) {
            exist = " ".repeat(count) + exist;
        }
        else {
            filePath = " ".repeat(count) + filePath;
        }
        return filePath + "\n" + exist;
    }

    private boolean imageOK() {
        try {
            BufferedImage image = ImageIO.read(fileChosen.toFile());
            int imgWidth = image.getWidth();
            int imgHeight = image.getHeight();
            if (imgWidth != 1024 || imgHeight != 1024) {
                return false;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    private void setFolder(File folder) {
        AppSettings.set.folder(folder.getAbsolutePath());
    }

    private File getFolder() {
        return new File(AppSettings.get.folder());
    }


    private void close() {
        SceneOne.close(sceneId);
        System.exit(0);
    }
}
