package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Controller {

    @FXML
    Button btn_openfolder;
    @FXML
    ListView<String> listView;
    private File selected;
    private List<File> fromDrag;
    private StringProperty stringProperty = new SimpleStringProperty();
    private ObservableList<String> myfiles = FXCollections.observableArrayList();
    Dragboard dragboard;


    public void initialize() {

        FileChooser fileChooser = new FileChooser();
        File file = new File("C:\\Users\\rica\\Desktop");
        fileChooser.setInitialDirectory(file);


//        btn_selected.setOnAction(e->{
//            selected = fileChooser.showOpenDialog(References.getStage());
//            if (selected != null) {
//                setFileToProcess(selected.toString());
//            } else {
//                return;
//            }
//        });
//
//        tf_file.setOnDragOver(e->{
//            e.acceptTransferModes(TransferMode.ANY);
//            Dragboard dragboard = e.getDragboard();
//            fromDrag = dragboard.getFiles();
//            for (File f: fromDrag) {
//                setFileToProcess(f.toString());
//            }
//            e.consume();
//        });

        stringProperty.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println(newValue);
                routeToFolder();
            }
        });

        btn_openfolder.setOnAction(e-> {

            File file1 = new File("c:/" + References.getUsername());
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.open(file1);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        listView.setOnDragOver(event -> {
            event.acceptTransferModes(TransferMode.ANY);
            dragboard = event.getDragboard();
            fromDrag = dragboard.getFiles();
        });

        listView.setOnDragExited(event -> {
            for (File f: fromDrag) {
                setFileToProcess(f.toString());
                myfiles.add(f.toString());
            }
            event.consume();
        });

        listView.setItems(myfiles);
    }

    private void setFileToProcess(String file) {
//        tf_file.setText(file);
        stringProperty.set(file);
    }

    private void routeToFolder() {

        //\.[a-zA-Z]{3,4}
        Pattern pattern = Pattern.compile("\\.[a-zA-Z0-9 _]{2,4}");
        Matcher matcher = pattern.matcher(stringProperty.getValue());

        if (matcher.find()) {
            try {
                moveFile(matcher.group());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private String getNameOfFile() {

        //[a-zA-Z0-9 \-]*(?=\.)
        //[a-zA-Z0-9]*(?=\.)
        Pattern pattern = Pattern.compile("[a-zA-Z0-9 \\-]*(?=\\.)");
        Matcher matcher = pattern.matcher(stringProperty.getValue());
        String groupCase = "";

        if (matcher.find()) {
            groupCase = matcher.group();
        }
        return groupCase;
    }

    private String saveString() {

        // Creates the date stamp for every saved file
        Calendar calendar = Calendar.getInstance();
        String year = processDatesWithZero(calendar.get(Calendar.YEAR));
        String month = processDatesWithZero(calendar.get(Calendar.MONTH)+1);
        String date = processDatesWithZero(calendar.get(Calendar.DATE));

        return year + month + date + "_";
    }

    private String processDatesWithZero(int number) {

        String withZero = "0";
        if (number < 10) {
            withZero += String.valueOf(number);
        } else {
            withZero = String.valueOf(number);
        }
        return withZero;
    }

    private void moveFile(String fileExtension) throws IOException {

        String toAssign = "";
        String saveName = saveString();
        String fileName = getNameOfFile();
        boolean process = false;
        switch (fileExtension.toLowerCase()) {
            case ".docx":
            case ".doc":
            case ".rtf":
            case ".wpd":
                toAssign = References.getFolders() + "_doc/";
                process = true;
                break;
            case ".xlsm":
            case ".xlsx":
                toAssign = References.getFolders() + "_excel/";
                process = true;
                break;
            case ".jpg":
            case ".png":
            case ".gif":
            case ".jpeg":
                toAssign = References.getFolders() + "_img/";
                process = true;
                break;
            case ".txt":
                toAssign = References.getFolders() + "_text/";
                process = true;
                break;
            case ".pdf":
                toAssign = References.getFolders() + "_pdf/";
                process = true;
                break;
            case ".pptx":
            case ".pps":
            case ".ppt":
                toAssign = References.getFolders() + "_ppt/";
                process = true;
                break;
            case ".mov":
            case ".mp4":
            case ".mpg":
            case ".mpeg":
                toAssign = References.getFolders() + "_video/";
                process = true;
                break;
            default:
                toAssign = References.getFolders() + "_misc/";
                process = true;
        }
        if (process) {
            Path target = Paths.get(toAssign + saveName + fileName + fileExtension);
            Path source = Paths.get(stringProperty.getValue());

            if (Files.notExists(target)) {
                Files.move(source,target, StandardCopyOption.REPLACE_EXISTING);
            } else {
                System.out.println("Existing");
            }
        }
    }
}
