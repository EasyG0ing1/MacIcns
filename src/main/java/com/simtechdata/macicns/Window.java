package com.simtechdata.macicns;

import com.simtechdata.easyfxcontrols.containers.CHBox;
import com.simtechdata.easyfxcontrols.containers.CVBox;
import com.simtechdata.easyfxcontrols.controls.Button;
import com.simtechdata.easyfxcontrols.controls.CCheckBox;
import com.simtechdata.easyfxcontrols.controls.CImageView;
import com.simtechdata.easyfxcontrols.controls.CLabel;
import com.simtechdata.sceneonefx.SceneOne;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Window {

	public Window() {
		makeControls();
		setControlProperties();
		SceneOne.set(sceneId, vbox, width, height).centered().show();
	}

	private final String  sceneId    = SceneOne.randomSceneId();
	private final double  width      = 700;
	private final double  height     = 475;
	private       boolean processImmediately;
	private       String  lastPath   = "";
	private final String  monacoPath = Paths.get(System.getProperty("user.dir"), "Contents", "Resources", "Monaco.ttf").toFile().getAbsolutePath();
	private final String  iconPath   = Paths.get(System.getProperty("user.dir"), "Contents", "Resources", "Logo.png").toFile().getAbsolutePath();
	private final Font    monacoFont = Font.loadFont("file:" + monacoPath, 11.5);
	private final Image   imgIcon    = new Image("file:" + iconPath);

	private CVBox     vbox;
	private CLabel    text;
	private Button    btnGo;
	private CCheckBox cbProcessImmediately;

	private Path mainPath;

	private void makeControls() {
		CImageView ivTitle  = new CImageView.Builder(imgIcon).preserveRatio(true).fitWidth(150).build();
		CLabel     lblInput = new CLabel.Builder("Select a 1024 x 1024 png file to get started").alignment(Pos.CENTER).build();
		cbProcessImmediately = new CCheckBox.Builder().toolTip("Checking this box will cause the png file to be processed immediately after loading it.\nYou won't have to press the Make ICNS File button").build();
		CLabel lblProcess = new CLabel.Builder("Process file immediately after opening and overwrite if it already exists").build();
		text = new CLabel.Builder().size(width * .9, 150).wordWrap(true).alignment(Pos.CENTER_LEFT).font(monacoFont).build();
		Button btnLoad = new Button.Builder("Load Image").width(100).onAction(e -> loadFile()).build();
		btnGo = new Button.Builder("Make ICNS File").width(155).onAction(e -> createFile()).disabled().build();
		Button btnClose   = new Button.Builder("Close").width(85).onAction(e -> close()).build();
		CHBox  boxProcess = new CHBox.Builder(15, cbProcessImmediately, lblProcess).build();
		CHBox  boxButtons = new CHBox.Builder(25, btnLoad, btnGo, btnClose).alignment(Pos.CENTER).padding(5).build();
		vbox = new CVBox.Builder(10, ivTitle, lblInput, boxProcess, text, boxButtons).size(width * .95, height * .9).alignment(Pos.CENTER).padding(25).build();
	}

	private void setControlProperties() {
		cbProcessImmediately.selectedProperty().addListener((ob, ov, nv) -> processImmediately = nv);
	}

	private void createFile() {
		new Thread(() -> {
			btnGo.setDisable(true);
			new ProcessFile(mainPath, true).run();
			final String msg = text.getText() + "Done!";
			Platform.runLater(() -> text.setText(msg));
		}).start();
	}

	private void loadFile() {
		text.setText("");
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File(System.getProperty("user.home")));
		if(!lastPath.isEmpty()) {
			fc.setInitialDirectory(new File(lastPath));
		}
		File file = fc.showOpenDialog(null);
		if (file != null) {
			mainPath = file.toPath();
			if (imageOK()) {
				lastPath = file.getParentFile().getAbsolutePath();
				text.setText("File: " + file.getName() + "\n\n");
				btnGo.setDisable(processImmediately);
				String baseName        = FilenameUtils.getBaseName(file.getAbsolutePath());
				String iconFileName    = baseName + ".icns";
				String rootPath        = file.getParent();
				Path   destinationFile = Paths.get(rootPath, iconFileName);
				String txt             = text.getText();
				if (destinationFile.toFile().exists() && !processImmediately) {
					text.setText(txt + "The file " + iconFileName + " Already exists in folder " + rootPath + "\nClicking on Make ICNS File WILL OVERWRITE THE FILE.\n");
					return;
				}
				String msg = txt + "Icon file name: " + iconFileName + "\n";
				if (processImmediately) {
					text.setText(msg);
					createFile();
				}
				else {msg += "Will be created in: " + rootPath + "\n";}
				text.setText(msg);
			}
		}
	}

	private boolean imageOK() {
		try {
			BufferedImage image     = ImageIO.read(mainPath.toFile());
			int           imgWidth  = image.getWidth();
			int           imgHeight = image.getHeight();
			if (!(imgWidth == 1024 && imgHeight == 1024)) {
				String msg = "The selected image must be a PNG file that is 1024 x 1024 pixels in size.";
				text.setText(msg);
				return false;
			}
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
		return true;
	}

	private void close() {
		SceneOne.close(sceneId);
		System.exit(0);
	}
}
