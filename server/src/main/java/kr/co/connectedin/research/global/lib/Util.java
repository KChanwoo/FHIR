package kr.co.connectedin.research.global.lib;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Util {
    public static int getChildDirCount(String path) {
        File basePath = new File(path);

        if (basePath.exists()) {
            File[] child = basePath.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return pathname.isDirectory();
                }
            });

            return child == null ? 0 : child.length;
        }

        return 0;
    }

    public static String removeDuplicatedSlash(String file) {
        return file.replaceAll("/+", "/");
    }

    public static String removeExtension(String file) {
        String[] split = file.split("\\.");
        return String.join(".", Arrays.asList(split).subList(0, split.length - 1));
    }

    public static void unzipFile(File sourceZip, File targetDir) throws Exception {
        ZipInputStream zis = new ZipInputStream(new FileInputStream(sourceZip));

        // list files in zip
        ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null) {

            boolean isDirectory = false;
            if (zipEntry.getName().endsWith(File.separator)) {
                isDirectory = true;
            }

            Path newPath = zipSlipProtect(zipEntry, targetDir.toPath());

            if (isDirectory) {
                Files.createDirectories(newPath);
            } else {
                if (newPath.getParent() != null) {
                    if (Files.notExists(newPath.getParent())) {
                        Files.createDirectories(newPath.getParent());
                    }
                }
                // copy files
                Files.copy(zis, newPath, StandardCopyOption.REPLACE_EXISTING);
            }

            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
    }

    public static Path zipSlipProtect(ZipEntry zipEntry, Path targetDir)
            throws IOException {

        // test zip slip vulnerability
        Path targetDirResolved = targetDir.resolve(zipEntry.getName());

        // make sure normalized file still has targetDir as its prefix
        // else throws exception
        Path normalizePath = targetDirResolved.normalize();
        if (!normalizePath.startsWith(targetDir)) {
            throw new IOException("Bad zip entry: " + zipEntry.getName());
        }
        return normalizePath;
    }

    public static List<String> getFilesByExtension(File root, String extension) throws Exception {
        Stream<Path> walk = Files.walk(root.toPath());
        return walk
                .filter(p -> !Files.isDirectory(p))
                .map(p -> p.toString().toLowerCase())
                .filter(f -> f.endsWith(extension))
                .collect(Collectors.toList());
    }
}
