![Logo](./img/Logo.png)

MacICNS is a Java program that will convert any valid 1024 x 1024 image file into a proper MacOS .icns file.

---
#### Program is now compiled with GraalVM into a NATIVE executable just like Swift - No JavaVM

---

Valid image types are:
* PNG
* JPG
* GIF
* BMP
* TIFF
* SVG

### Using the program

[Video Demo](https://youtu.be/ZJuk7e1mZ9I)

It's fairly simple, grab [the latest version](https://github.com/EasyG0ing1/MacIcns/releases/latest) from Releases at the right and download the program and install it.

You can use it in either of two ways. If you launch the program without passing in a path to your png file, it will launch the GUI where you can select the file then convert it.

OR, if you prefer, you can use it from Terminal by passing in the path of your png file and it will kick out a properly created icns file.

You must have `iconutil` available in your PATH or it won't work. You can check to see if it is in your path by opening Terminal and just type `iconutil` and hit enter. If it comes back with anything other than `command not found`, then you're in business.

`iconutil` comes with MacOS so this should not be a problem.

### Command Line Usage
By default, the program will run in GUI mode, but if you prefer to run it from terminal, there are a couple of ways you can pass the path of your image file into the program:
```bash
open /Applications/MacIcns.app --args /Users/username/Pictures/MyIcon.png
```
OR
```Bash
/Applications/MacIcns.app/Contents/MacOS/MacIcns /Users/username/Pictures/MyIcon.png
```

The program uses the filename of your image file but replaces the extension with `.icns`. If the `.icns` file already exists, the program will overwrite the file if it is being run from within Terminal. In the GUI, you will have the option of overwriting the file.

### Java libraries are compiled into the app package so no need to install Java.

### GUI Mode
If the program quits without showing that the file was created successfully (green text under the program logo), run it from within terminal using this command:
```Bash
/Applications/MacIcns.app/Contents/MacOS/MacIcns
```
Then, try to process your image again but this time when it exits, it should kick out an error message that will help you understand why it wasn't able to process the file.

## How it works

The program takes your 1024 x 1024 image file and it creates a folder where it then converts your image into the different sizes that are needed for the final `.icns` file. Then it calls `iconutil` to do the conversion. It will use the original name of your file but it will have `.icns` as the extension name and it will drop it into the same folder that your selected image file resides in.

## Updates
* 1.5.0
  * Added ability to chose which resolutions go into the icns file in the GUI.
* 1.4.0
  * Facelift - along with major functionality enhancements
* 1.3.2
  * Now compiling with GraalVM so that program no longer depends on a Java runtime environment. Much more efficient!
  * Fixed bug where spaces in path names were not generating the `.icns` file
  * Changed UI for aesthetics and moved the drag region to the top of the program window
  * Other minor code improvements.

* 1.3.0
  * Added button after icns creation which will open the new file in the MacOS Preview application.
  * Other minor code improvements.

* 1.2.0
  * Program got a massive face lift.
  * Can now use other valid images than just PNG

That's it!

If you have any issues, open an issue or if you would like to enhance the program, then open a pull request.

Thank you,

Mike
