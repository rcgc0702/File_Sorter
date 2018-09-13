package sample;

import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class References {

    private static Stage stage;
    private static String username;
    private static String folders;

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stg) {
        stage = stg;
    }

    public static boolean checkDirectory() {

        // This method checks the existence of the folder
        // If the folder does not exist, this method will create.

        Path path = Paths.get(folders);
        String[] typesOfFiles = {"_doc","_excel","_text","_img","_pdf","_video","_ppt","_misc"};

        if(Files.notExists(path)) {
            for (String s: typesOfFiles) {
                new File(folders + s).mkdirs();
            }
        }

        return true;
    }

    public static String getFolders() {
        return folders;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        References.username = username;
        folders = "C:/" +username+ "/"+username;
    }
}
