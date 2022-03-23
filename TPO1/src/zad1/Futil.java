package zad1;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

import static java.nio.file.StandardOpenOption.*;

public class Futil {
    public static void processDir(String dirName, String resultFileName){

        ArrayList<ByteBuffer> list = new ArrayList<>();

        FileVisitor<Path> fileVisitor = new FileVisitor<>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                System.out.println("Just visited: " + dir);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

                if (attrs.isRegularFile()) {
                    System.out.println("Processing: " + file);
                    if (Files.isRegularFile(file)){
                            try (FileChannel fileReaderChannel = FileChannel.open(file)){
                            ByteBuffer byteBuffer = ByteBuffer.allocateDirect((int) fileReaderChannel.size());
                            fileReaderChannel.read(byteBuffer);
                            byteBuffer.flip();
                            list.add(byteBuffer);
                        }
                    }
                }

                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                System.out.println("Can't access: " + file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                System.out.println("About to visit: " + dir);
                return FileVisitResult.CONTINUE;
            }
        };

        try {
            Files.walkFileTree(Path.of(dirName), fileVisitor);

            try(FileChannel  fileWriterChannel = FileChannel.open(Path.of(resultFileName), CREATE, TRUNCATE_EXISTING, WRITE)) {
                ;
                Charset csIn    = Charset.forName("Cp1250");
                Charset csOut   = StandardCharsets.UTF_8;

                for (ByteBuffer s : list) {
                    CharBuffer charBuffer = csIn.decode(s);
                    fileWriterChannel.write(csOut.encode(charBuffer));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}