package com.simtechdata.macicns;

import com.simtechdata.easyfxcontrols.containers.CHBox;
import com.simtechdata.easyfxcontrols.containers.CVBox;
import com.simtechdata.easyfxcontrols.controls.Button;
import com.simtechdata.easyfxcontrols.controls.CLabel;
import com.simtechdata.sceneonefx.SceneOne;
import javafx.geometry.Pos;
import javafx.scene.text.Text;
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
		SceneOne.set(sceneId, vbox, width, height).centered().newStage().show();
	}

	private final String sceneId = SceneOne.randomSceneId();
	private final double width   = 700;
	private final double height  = 400;

	private CVBox  vbox;
	private CLabel   text;
	private CLabel lblFilePath;
	private Button btnGo;

	private Path mainPath;

	private void makeControls() {
		CLabel lblTitle = new CLabel.Builder("PNG To ICNS").alignment(Pos.CENTER).build();
		CLabel lblInput = new CLabel.Builder("Select a 1024 x 1024 png file to get started").alignment(Pos.CENTER).build();
		lblFilePath = new CLabel.Builder().size(width * .9,55).wordWrap(true).alignment(Pos.CENTER).build();
		text        = new CLabel.Builder().size(width * .95, height * .4).wordWrap(true).build();
		text.setText("\n\n\n\n\n\n");
		text.setWrapText(true);
		Button btnLoad = new Button.Builder("Load Image").width(100).onAction(e -> loadFile()).build();
		btnGo = new Button.Builder("Make ICNS File").width(155).onAction(e -> createFile()).disabled().build();
		Button btnClose   = new Button.Builder("Close").width(85).onAction(e -> close()).build();
		CHBox  boxButtons = new CHBox.Builder(25, btnLoad, btnGo, btnClose).alignment(Pos.CENTER).padding(5).build();
		vbox = new CVBox.Builder(20,lblTitle, lblInput, lblFilePath, text, boxButtons).size(width * .95, height * .9).alignment(Pos.CENTER).padding(8).build();
	}

	private void createFile() {
		new ProcessFile(mainPath, true).run();
		String msg = text.getText();
		msg += "\n\nDone!";
		text.setText(msg);
	}

	private void loadFile() {
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File(System.getProperty("user.home")));
		File file = fc.showOpenDialog(null);
		if (file != null) {
			mainPath = file.toPath();
			if (imageOK()){
				lblFilePath.setText(file.getAbsolutePath());
				btnGo.setDisable(false);
				String baseName     = FilenameUtils.getBaseName(file.getAbsolutePath());
				String iconFileName = baseName + ".icns";
				String rootPath     = file.getParent();
				Path destinationFile = Paths.get(rootPath,iconFileName);
				if(destinationFile.toFile().exists()) {
					text.setText("The file " + iconFileName + " Already exists in folder " + rootPath + "\n\nClicking on Make ICNS WILL OVERWRITE THE FILE.");
					return;
				}
				String msg          = "Icon file name: " + iconFileName;
				msg += "\nWill be created in: " + rootPath;
				text.setText(msg);
			}
		}
	}

	private boolean imageOK() {
		try {
			BufferedImage image = ImageIO.read(mainPath.toFile());
			int imgWidth = image.getWidth();
			int imgHeight = image.getHeight();
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
