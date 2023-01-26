![Logo](./icns/logo.png)

MacICNS is a Java program that will convert any 1024 x 1024 png file into a proper MacOS .icns file.

### Using the program

It's fairly simply, click on the releases link at the right and download the program and install it.

You can use it in either of two ways. If you launch the program without passing in a path for your png file, it will launch the GUI where you can select the file then convert it.

OR, if you prefer, you can use it from Terminal by passing in the path of your png file and it will kick out a properly created icns file.

You must have `iconutil` available in your PATH or it won't work. You can check to see if it is in your path by opening Terminal and just type `iconutil` and hit enter. If it comes back with anything other than `command not found`, then you're in business.

`iconutil` comes with MacOS so you shouuld have it already.

There are a couple of ways you can pass the path of your png file into the program:
```bash
open /Applications/MacIcns.app --args /Users/username/Pictures/MyIcon.png
```
OR
```Bash
/Applications/MacIcns.app/Contents/MacOS/universalJavaApplicationStub /Users/username/Pictures/MyIcon.png
```

Java libraries are compiled into the app package so no need to install Java.

## How it works

The program takes you 1024 x 1024 png file and it creates a folder where it then converts your image into the different sizes that are needed for the final `.icns` file. Then it calls `iconutil` to do the conversion. It will use the origianl name of your file but it will have `.icns` as the extension name and it will drop it into the same folder that your PNG file is in.

Thats it!

If you have any issues, open an issue or if you would like to enhance the program, then open a pull request.

Thank you,

Mike
