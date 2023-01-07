//package inputOutput;
//
//import org.junit.jupiter.api.Test;
//
//import java.io.*;
//import java.nio.file.Path;
//import java.util.stream.Collectors;
//
///**
// *          outputstream - stream of byte
// * application--->file
// *
// *      input stream - stream of byte
// * application<----file
// */
//private class FileRunner {
//
//    @Test
//    private void workWithFile() throws IOException {
//        File file = new File("resources/test.txt");
//        System.out.println("does success create?: " + file.createNewFile());
//        System.out.println("does file exist?: " + file.exists());
//        System.out.println("is it file?: " + file.isFile());
//        System.out.println("is it directory?: " + file.isDirectory());
//        System.out.println("Name file is :" + file.getName());
//        System.out.println("What is parent?: " + file.getParent());
//        System.out.println("What is absolute path?: " + file.getAbsolutePath());
//        System.out.println("What is path " + file.getCanonicalPath());
//
//        File dir = new File(("/resources/test/dir"));
//        dir.mkdirs();
////        dir.list();
//    }
//
//
//    @Test
//    private void inputStreamTest() throws IOException {
////        File file = new File(String.join(File.separator,"resources" ,  "text.txt");
//        File file = Path.of("resources", "test.txt").toFile();
//        try(FileInputStream inputStream = new FileInputStream(file)){
//            byte[] bytes = new byte[inputStream.available()];
//            int counter = 0;
//            byte currentByte;
//            while ((currentByte = (byte) inputStream.read()) != -1) {
//                bytes[counter++] = currentByte;
//            }
//            String stringValue = new String(bytes);
//            System.out.println(stringValue);
////            byte[] bytes = inputStream.readAllBytes();
////            String stringValue = new String(bytes);
////            System.out.println(stringValue);
//
//        };
//
//
//
//    }
//
//    @Test
//    private void fileReaderTest_ReadSmallFiles() throws IOException {
//        File file = Path.of("resources", "test.txt").toFile();
//        try (FileReader fileReader = new FileReader(file)) {
//
//            char[] chars = new char[2048];
//
//            fileReader.read(chars);
//            String stringValue = new String(chars);
//            System.out.println(stringValue);
//        }
//    }
//
//    @Test
//    private void readingFileOneCharacterAtAtime() throws IOException {
//        File file = Path.of("resources", "test.txt").toFile();
//        try (FileReader fileReader = new FileReader(file)) {
//            int i;
//            while ((i=fileReader.read())!=-1){
//                System.out.println((char) i);
//            }
//        }
//    }
//
//    @Test
//    private void readingAFileLineUsingFileReader() throws IOException {
//        File file = Path.of("resources", "test.txt").toFile();
//        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
//            String line;
//            while ((line = bufferedReader.readLine()) != null) {
//                System.out.println(line);
//            }
//        }
//    }
//
//    @Test
//    private void readerTest() throws IOException {
//        File file = Path.of("resources", "test.txt").toFile();
//        try (BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
//
//            String collect = fileReader.lines()
//                    .collect(Collectors.joining("\n"));
//
//            System.out.println(collect);
//        }
//    }
//}
