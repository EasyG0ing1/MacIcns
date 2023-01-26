package com.simtechdata.macicns;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ProcessFile {

	public ProcessFile(Path mainPath, boolean inWindow) {
		this.mainPath = mainPath;
		this.inWindow = inWindow;
	}

	private final Path mainPath;
	private final boolean inWindow;

	public void run() {
		processFile();
	}

	private void processFile() {
		String folder = mainPath.toFile().getParentFile().getAbsolutePath();
		Path iconFolder = Paths.get(folder, "icon.iconset");
		String iconFilename = FilenameUtils.getBaseName(mainPath.toString()) + ".icns";
		Path iconFile = Paths.get(folder,iconFilename);
		Path Image1 = Paths.get(iconFolder.toString(),"icon_512x512@2x.png");
		Path Image2 = Paths.get(iconFolder.toString(),"icon_512x512.png");
		Path Image3 = Paths.get(iconFolder.toString(),"icon_256x256@2x.png");
		Path Image4 = Paths.get(iconFolder.toString(),"icon_256x256.png");
		Path Image5 = Paths.get(iconFolder.toString(),"icon_128x128@2x.png");
		Path Image6 = Paths.get(iconFolder.toString(),"icon_128x128.png");
		Path Image7 = Paths.get(iconFolder.toString(),"icon_32x32@2x.png");
		Path Image8 = Paths.get(iconFolder.toString(),"icon_32x32.png");
		Path Image9 = Paths.get(iconFolder.toString(),"icon_16x16@2x.png");
		Path Image10 = Paths.get(iconFolder.toString(),"icon_16x16.png");

		Map<Integer, Job> jobMap = new HashMap<>();

		try {
			BufferedImage original = ImageIO.read(mainPath.toFile());
			double        width    = original.getWidth();
			double height = original.getHeight();
			if(width != 1024 && height != 1024) {
				System.out.println("Specified image is not 1024 x 1024 in size");
				System.exit(0);
			}
			if(!iconFolder.toFile().exists()) {
				FileUtils.createParentDirectories(Image1.toFile());
			}
			jobMap.put(1,new Job(original,Image1,1024));
			jobMap.put(2,new Job(original,Image2,512));
			jobMap.put(3,new Job(original,Image3,512));
			jobMap.put(4,new Job(original,Image4,256));
			jobMap.put(5,new Job(original,Image5,256));
			jobMap.put(6,new Job(original,Image6,128));
			jobMap.put(7,new Job(original,Image7,64));
			jobMap.put(8,new Job(original,Image8,32));
			jobMap.put(9,new Job(original,Image9,32));
			jobMap.put(10,new Job(original,Image10,16));

			for(int index : jobMap.keySet()) {
				jobMap.get(index).saveFile();
			}
			Thread thread = new Thread(new Run(iconFolder,iconFile));
			thread.start();
			while(thread.getState().equals(Thread.State.RUNNABLE)) {

			}
			FileUtils.deleteDirectory(iconFolder.toFile());
			if (!inWindow) {
				System.out.println("Icon file created: " + iconFile);
				System.exit(0);
			}
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static class Job {

		public Job(BufferedImage original, Path path, int size) {
			this.original = original;
			this.path = path;
			this.size = size;
		}

		private final BufferedImage original;
		private final Path path;
		private final int  size;

		public void saveFile() {
			try {
				BufferedImage finalImage = getImage();
				ImageIO.write(finalImage,"PNG",path.toFile());
			}
			catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private BufferedImage getImage() {
			try {
				return resizeImage(original,size,size);
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws Exception {
			return Scalr.resize(originalImage, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.FIT_EXACT, targetWidth, targetHeight);
		}
	}


}
