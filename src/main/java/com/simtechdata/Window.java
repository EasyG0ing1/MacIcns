package com.simtechdata;

import com.simtechdata.build.Selections;
import com.simtechdata.fonts.Fonts;
import com.simtechdata.settings.AppSettings;
import com.simtechdata.utils.Colors;
import com.simtechdata.utils.Shell;
import com.simtechdata.build.CheckBox;
import com.simtechdata.build.CheckBoxs;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;


import static com.simtechdata.enums.OverlayColor.*;

public class Window {

    public Window(Stage primaryStage) {
        makeControls();
        setControlProperties();
        this.stage = primaryStage;
        this.scene = new Scene(ap);
        this.stage.initStyle(StageStyle.TRANSPARENT);
        this.stage.centerOnScreen();
        this.scene.setFill(Color.rgb(0, 0, 125, .01));
        this.stage.setScene(this.scene);
        this.stage.show();
        fadeThread();
    }

    private final Stage stage;
    private final Scene scene;
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
    private static final URL dragMoveRight = Window.class.getResource("/misc/DragToMoveRight.png");
    private static final URL dragMoveLeft = Window.class.getResource("/misc/DragToMoveLeft.png");
    private static final URL startHere = Window.class.getResource("/misc/StartHere.png");
    private static final URL lastAccessed = Window.class.getResource("/misc/LastFolderAccessed.png");

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
    private static final Image imgDragRight = new Image(dragMoveRight.toExternalForm());
    private static final Image imgDragLeft = new Image(dragMoveLeft.toExternalForm());
    private static final Image imgStartHere = new Image(startHere.toExternalForm());
    private static final Image imgLastAccessed = new Image(lastAccessed.toExternalForm());

    private ImageView ivDragLeft;
    private ImageView ivDragRight;
    private ImageView ivStartHere;
    private ImageView ivLastAccessed;
    private ImageView ivMakeFile;
    private ImageView ivLoadImage;
    private ImageView ivClose;
    private ImageView ivView;
    private VBox vbox;

    private Text textDirections;
    private Text textResults;
    private Text textFileLabel;
    private Text textFilename;
    private Text textPathLabel;
    private Text textICNSLabel;
    private Text textICNSName;
    private Text textFinalPath;
    private Text textPath;
    private Text text3;
    private Text text4;
    private Path fileChosen;
    private VBox finalText;
    private HBox checkBoxes;
    private boolean fileLoaded = false;
    private Selections selections;
    private       double       xx    , yy;

    private void mouseDragged(MouseEvent event, double topAreaRatio) {
        if(yy <= height * topAreaRatio) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setX(event.getScreenX() - xx);
            stage.setY(event.getScreenY() - yy);
        }
    }

    private void mousePressed(MouseEvent event) {
        xx = event.getSceneX();
        yy = event.getSceneY();
    }




    private void makeControls() {

        ap = newAnchorPane(imgFrame);
        textResults   = newText("", Fonts.Lato_Black(20), Color.GREENYELLOW, 1.0, true);
        textFinalPath = newText(AppSettings.get.folder(),Fonts.Lato_Black(20), Color.YELLOW, 1.0, true);
        textFileLabel = newText("File: ",Fonts.Fira_Code_Regular(20), Color.WHITE, 1.0, true);
        textPathLabel = newText("Path: ",Fonts.Fira_Code_Regular(20), Color.WHITE, 1.0, true);
        textICNSLabel = newText("ICNS: ",Fonts.Fira_Code_Regular(20), Color.WHITE, 1.0, true);
        textFilename  = newText("",Fonts.Fira_Code_Bold(20), Color.WHITE,1.0, false);
        textICNSName  = newText("",Fonts.Fira_Code_Bold(20), Color.WHITE,1.0, false);
        textPath      = newText("",Fonts.Fira_Code_Regular(13), Color.WHITE, 1.0, false);
        text3         = newText("\n",Fonts.Fira_Code_Regular(22), Color.WHITE, 1.0, false);
        text4         = newText("\n",Fonts.Fira_Code_Regular(18), Color.WHITE, 1.0, false);
        ivDragLeft    = newImageView(imgDragLeft, null, 180, null);
        ivDragRight   = newImageView(imgDragRight,null,180,null);
        ivStartHere   = newImageView(imgStartHere,null,180,null);
        ivLastAccessed= newImageView(imgLastAccessed,null,325,null);
        ivMakeFile    = newImageView(imgMakeFileUp, imgMakeFileDown, 180,e-> Platform.runLater(this::createFile));
        ivLoadImage   = newImageView(Colors.overlay(imgLoadImageUp, GREEN), Colors.overlay(imgLoadImageDown, GREEN),180, e -> Platform.runLater(this::loadFile));
        ivClose       = newImageView(imgCloseUp, imgCloseDown, 180, e-> Platform.runLater(this::close));
        ivView        = newImageView(Colors.overlay(imgViewUp, GREEN), Colors.overlay(imgViewDown, GREEN), 250, e-> Platform.runLater(this::openPreview));
        ivView.setVisible(false);
        
        ImageView ivTitle = newImageView(imgLogo,null,200, null);
        textDirections = newText("Click Load Image and select a 1024 x 1024 image file\n", Fonts.Lato_Heavy(24), Color.WHITE, 1.0, true);
        HBox boxButtons = newHBox(25, Pos.CENTER, new Insets(5), ivLoadImage, ivMakeFile, ivClose);
        finalText = showFinalText();
        vbox = newVBox(10, Pos.CENTER, new Insets(10,10,45,10), textResults, textDirections, finalText);
        vbox.setPrefSize(width * .95, height * .9);
        finalText.setVisible(false);
        vbox.setFillWidth(true);
        vbox.setPrefWidth(width);
        checkBoxes = getCheckBoxes();
        checkBoxes.setVisible(false);
        VBox boxTest = newVBox(0, Pos.CENTER, new Insets(0), textFinalPath);
        addNode(vbox, 10,350,10,150);
        addNode(ivTitle, (width / 2) -108, 40, (width/2),-1);
        addNode(boxButtons, 0, -1, 0, 20);
        addNode(ivView, -1, ((height / 2) + 15), ((width / 2) - 125), -1);
        addNode(boxTest, 0, ((height / 2) + 140), 0, -1);
        addNode(ivDragLeft, 25, 65, -1, -1);
        addNode(ivDragRight, -1, 65, 25, -1);
        addNode(ivStartHere, 20, -1, -1, 100);
        addNode(ivLastAccessed, -1, -1, 155, 195);
        addNode(checkBoxes, 20, 285, 20, -1);
        ap.setOnMouseDragged(e -> mouseDragged(e, .15));
        ap.setOnMousePressed(this::mousePressed);
   }

    private AnchorPane newAnchorPane(Image backImage){
        ImageView iv = new ImageView(backImage);
        AnchorPane ap = new AnchorPane(iv);
        AnchorPane.setLeftAnchor(iv,0.0);
        AnchorPane.setRightAnchor(iv,0.0);
        AnchorPane.setTopAnchor(iv,0.0);
        AnchorPane.setBottomAnchor(iv,0.0);
        ap.setPrefWidth(width);
        ap.setMinWidth(width);
        ap.setMaxWidth(width);
        ap.setPrefHeight(height);
        ap.setMinHeight(height);
        ap.setMaxHeight(height);
        return ap;
    }

    private void addNode(Node node, double left, double top, double right, double bottom) {
        ap.getChildren().add(node);
        if(left != -1)
            AnchorPane.setLeftAnchor(node, left);
        if(top != -1)
            AnchorPane.setTopAnchor(node, top);
        if(right != -1)
            AnchorPane.setRightAnchor(node, right);
        if(bottom != -1)
            AnchorPane.setBottomAnchor(node, bottom);
    }

    private void addNode(HBox box, double left, double top, double right, double bottom) {
        ap.getChildren().add(box);
        if(left != -1)
            AnchorPane.setLeftAnchor(box, left);
        if(top != -1)
            AnchorPane.setTopAnchor(box, top);
        if(right != -1)
            AnchorPane.setRightAnchor(box, right);
        if(bottom != -1)
            AnchorPane.setBottomAnchor(box, bottom);
    }

    private void addNode(VBox box, double left, double top, double right, double bottom) {
        ap.getChildren().add(box);
        if(left != -1)
            AnchorPane.setLeftAnchor(box, left);
        if(top != -1)
            AnchorPane.setTopAnchor(box, top);
        if(right != -1)
            AnchorPane.setRightAnchor(box, right);
        if(bottom != -1)
            AnchorPane.setBottomAnchor(box, bottom);
    }

    private Text newText(String msg, Font font, Color color, double lineSpacing, boolean bold) {
        Text text = new Text(msg);
        text.setFont(font);
        text.setLineSpacing(lineSpacing);
        text.setStyle(bold ? "-fx-font-weight: bold;" : "");
        text.setFill(color);
        return text;
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

    private HBox newHBox(double spacing, Pos alignment, Insets padding, Node... nodes) {
        HBox box = new HBox(spacing, nodes);
        box.setAlignment(alignment);
        box.setPadding(padding);
        return box;
    }

    private VBox newVBox(double spacing, Pos alignment, Insets padding, Node... nodes) {
        VBox box = new VBox(spacing, nodes);
        box.setAlignment(alignment);
        box.setPadding(padding);
        return box;
    }

    private void setImages(ImageView iv, Image up, Image down) {
        iv.setImage(up);
        iv.setOnMousePressed(e -> iv.setImage(down));
        iv.setOnMouseReleased(e -> iv.setImage(up));
    }

    private VBox showFinalText() {
        TextFlow box1 = new TextFlow(textFileLabel, textFilename);
        TextFlow box2 = new TextFlow(textICNSLabel, textICNSName);
        TextFlow box3 = new TextFlow(textPathLabel, textPath);
        return newVBox(5, Pos.CENTER, new Insets(0, 0, 0, 30), box1, box2, box3, text3, text4);
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
        if(!fileLoaded) {
            Platform.runLater(() -> new Dialog("Problem: " + "No png file loaded"));
            return;
        }
        new Thread(() -> {
            File file = selections.getIcnsFilePath().toFile();
            if(file.exists()) {
                file.delete();
            }
            Response response = new ProcessFile(fileChosen, selections).run();
            if (response.isSuccess()) {
                final String msg = "File created successfully!";
                Platform.runLater(() -> {
                    clearText();
                    clearLabels();
                    textFinalPath.setText(ProcessFile.getIcnsFilePath());
                    textResults.setText(msg);
                    ivView.setVisible(true);
                    textFinalPath.setVisible(true);
                    checkBoxes.setVisible(false);
                });
                fileLoaded = false;
                setImages(ivMakeFile, imgMakeFileUp, imgMakeFileDown);
            }
            else {
                String message = response.getMessage().isEmpty() ? "No png file selected" : response.getMessage();
                Platform.runLater(() -> new Dialog("Problem: " + message));
            }
        }).start();
    }

    private String getNewStyle(String color, boolean bold) {
        StringBuilder style = new StringBuilder();
        style.append(bold ? "-fx-font-weight: bold;"   : "");
        style.append((color != null && !color.isEmpty()) ? "-fx-text-fill: " + color.replace("0x","#") + ";" : "");
        return style.toString();
    }

    public void setColor(Text text, Color color, boolean bold) {
        text.setStyle(getNewStyle(color.toString(), bold));
    }

    private void loadFile() {
        FileChooser fc = new FileChooser();
        File startFolder = getFolder();
        if (!startFolder.exists()) {
            startFolder = new File(System.getProperty("user.home"));
        }
        fc.setInitialDirectory(startFolder);
        File file = fc.showOpenDialog(null);
        if (file != null && ImageChecker.isValid(file)) {
            Platform.runLater(() -> {
                ivView.setVisible(false);
                textFinalPath.setVisible(false);
            });
            setImages(ivMakeFile, imgMakeFileUp, imgMakeFileDown);
            setFolder(file.getParentFile());
            if (ImageChecker.isValid(file)) {
                fileChosen = file.toPath();
                selections = new Selections(fileChosen);
                CheckBoxs.reset();
                clearText();
                textDirections.setVisible(false);
                checkBoxes.setVisible(true);
                checkBoxes.toFront();
                String rootPath = file.getParent();
                setColor(text3, Color.WHITE, false);
                setColor(text4, Color.WHITE, false);
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
                    text4.setFont(Fonts.Fira_Code_Bold(18));
                    text4.setText("Clicking on Make File will overwrite the file");
                    text4.setFill(Color.rgb(255, 255, 0));
                    setImages(ivMakeFile, Colors.overlay(imgMakeFileUp, RED), Colors.overlay(imgMakeFileDown, RED));
                }
                else {
                    setLabels();
                    text3.setText("Click Make File to create the ICNS file");
                    text3.setFill(Color.YELLOW);
                    text3.setFont(Fonts.Fira_Code_Regular(22));
                    text4.setText("");
                    setImages(ivMakeFile, Colors.overlay(imgMakeFileUp, GREEN), Colors.overlay(imgMakeFileDown, GREEN));
                }
                fileLoaded = true;
                setImages(ivLoadImage, imgLoadImageUp, imgLoadImageDown);
            }
            finalText.setVisible(true);
        }
        else {
            String message = "Selected file is either null," + "\n";
            message += "not 1024 x 1024 in size OR is not one of these\n";
            message += "formats: PNG, JPEG, GIF, TIFF, BMP or SVG";
            new Dialog(message);
        }
    }

    private HBox getCheckBoxes() {
        CheckBox cb512 = CheckBoxs.getCheckbox(512);
        CheckBox cb256 = CheckBoxs.getCheckbox(256);
        CheckBox cb128 = CheckBoxs.getCheckbox(128);
        CheckBox cb64  = CheckBoxs.getCheckbox(64);
        CheckBox cb32  = CheckBoxs.getCheckbox(32);
        CheckBox cb16  = CheckBoxs.getCheckbox(16);
        cb512.setOnAction((observable, oldValue, newValue) -> selections.setI512(newValue));
        cb256.setOnAction((observable, oldValue, newValue) -> selections.setI256(newValue));
        cb128.setOnAction((observable, oldValue, newValue) -> selections.setI128(newValue));
        cb64.setOnAction((observable, oldValue, newValue) -> selections.setI64(newValue));
        cb32.setOnAction((observable, oldValue, newValue) -> selections.setI32(newValue));
        cb16.setOnAction((observable, oldValue, newValue) -> selections.setI16(newValue));
        HBox top = new HBox(0, cb512.getIv(), cb256.getIv(), cb128.getIv(), cb64.getIv(), cb32.getIv(), cb16.getIv());

        top.setAlignment(Pos.CENTER);
        top.setPadding(new Insets(5));
        return top;
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
        File file = selections.getIcnsFilePath().toFile();
        if (file.exists()) {
            String openPath = file.getAbsolutePath();
            String[] args = new String[]{"-a", "Preview", openPath};
            int response = Shell.run("open", args);
            if (response != 0)
                new Dialog("There was a problem finding or launching the Preview application");
        }
        else {
            String filePath = ProcessFile.getIcnsFilePath();
            String message = "File does not exist: " + filePath;
            new Dialog(message);
        }
    }

    private void setFolder(File folder) {
        AppSettings.set.folder(folder.getAbsolutePath());
    }

    private File getFolder() {
        return new File(AppSettings.get.folder());
    }

    private void close() {
        this.stage.close();
        System.exit(0);
    }

    private void fadeThread() {
        new Thread(() -> {
            sleep(2500);
            for (double x = 1.0; x >= 0.0; x -= .01) {
                final double opacity = x;
               Platform.runLater(() -> {
                    ivDragLeft.setOpacity(opacity);
                    ivDragRight.setOpacity(opacity);
                    ivStartHere.setOpacity(opacity);
                    ivLastAccessed.setOpacity(opacity);
                });
                sleep(30);
            }
        }).start();
    }

    private void sleep(long time) {
        try {
            TimeUnit.MILLISECONDS.sleep(time);
        } catch (InterruptedException ignored) {
        }
    }
}
