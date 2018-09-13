package sample;

import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class References {

    private static Stage stage;
    private static String username;
    private static String folders;
    private static String rootDrive;

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stg) {
        stage = stg;
    }

    public static boolean checkDirectory() {

        Path path = Paths.get(folders);
        String[] typesOfFiles = {"_doc","_excel","_text","_img","_pdf","_video","_ppt","_misc"};

        if(Files.notExists(path)) {
            for (String s: typesOfFiles) {
                if (Files.notExists(Paths.get(folders + s))) {
                    new File(folders + s).mkdirs();
                }
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
        References.rootDrive = getRootDrive();
        createFolder();
    }

    private static void createFolder() {
        folders = rootDrive + ":/" + References.username + "/" + References.username;
    }

    public static String getRootDrive() {

        //[a-zA-Z]{1,1}(?=\:)
        File[] afile = File.listRoots();
        Pattern pattern = Pattern.compile("[a-zA-Z]{1,1}(?=\\:)");
        Matcher matcher = pattern.matcher(afile[0].toString());
        String match = "";

        if (matcher.find()) {
            match = matcher.group();
        }
        return match;
    }

}
