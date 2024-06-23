package messageProcessor;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileHandler {
    private static final String LECTURER_FOLDER_NAME = "exam/";
    private static final String STUDENT_FOLDER_NAME = "exam_student/";

    public static boolean saveFile(String filePath, byte[] fileData, String fileType,String filename) {
        String saveFolderPath;

        if (fileType.equals("lecturer")) {
            saveFolderPath = LECTURER_FOLDER_NAME;
        } else if (fileType.equals("student")) {
            saveFolderPath = STUDENT_FOLDER_NAME + filePath;
        } else {
            // Invalid file type
            return false;
        }

        String saveFilePath = saveFolderPath+filename;
        try {
            // Create the save folder if it doesn't exist
            Path saveFolder = Paths.get(saveFolderPath);
            if (!Files.exists(saveFolder)) {
                Files.createDirectories(saveFolder);
            }

            // Create BufferedOutputStream to improve performance
            FileOutputStream fileOutputStream = new FileOutputStream(saveFilePath);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

            // Write the file data to the output stream
            bufferedOutputStream.write(fileData);

            // Flush and close the streams
            bufferedOutputStream.flush();
            bufferedOutputStream.close();

            // File saved successfully
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            // Failed to save the file
            return false;
        }
    }

    public static byte[] getFileData(String fileName) {
        Path filePath = Paths.get(fileName);
        try {
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
