package sample;

import java.io.IOException;
import java.util.Calendar;

public class Samplingz {

    public static void main(String[] args) throws IOException {

//        Path path = Paths.get("c:\\");
//        FileSystem fileSystem = path.getFileSystem();
//        Iterable<FileStore> iterable = fileSystem.getFileStores();
//
//        Iterator<FileStore> iterator = iterable.iterator();
//        while (iterator.hasNext()) {
//            System.out.println(iterator.next().name());
//        }

//        Instant instant = Instant.now();

        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.get(Calendar.MONTH));
        System.out.println(calendar.get(Calendar.DATE));
        System.out.println(calendar.get(Calendar.YEAR));


    }
}
