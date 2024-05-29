package pe.edu.utp.dsa.Kanban.Utilities;

import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import pe.edu.utp.dsa.Kanban.Kanban;
import javafx.embed.swing.SwingFXUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class Utilities {

    private static final String _FILELOG_ =
            Objects.requireNonNull(Kanban.class.getResource("logRecentFiles.log"))
                    .getFile()
                    .replaceAll("%20", " ").substring(1);

    private static final int _CANTRECENTFILES_ = 5;

    /**
     * Reads recent file records from the log file.
     * @return a list of strings representing the names of recent files
     */
    public static ArrayList<String> readRecordRecentFiles(){
        ArrayList<String> data;
        try(BufferedReader fi = new BufferedReader(new FileReader(_FILELOG_))){
            data = new ArrayList<>();
            String line;
            while((line = fi.readLine()) != null){
                data.add(line);
            }
        }catch (IOException e){
            return new ArrayList<>();
        }
        return data;
    }

    /**
     * Writes a new recent file record to the log file.
     * @param path path of the recent file to log
     */
    public static void writeRecordRecentFiles(String path){
        File filelog = new File(_FILELOG_);
        ArrayList<String> content = Utilities.readRecordRecentFiles();
        if (content.size() > _CANTRECENTFILES_) {
            content.remove(content.size()-1);
        }

        try(FileWriter fileW = new FileWriter(filelog)){
            fileW.write(path+"\n");
            content.removeIf(path_i -> path_i.equals(path));
            fileW.write(String.join("\n", content.toArray(new String[0])));
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    /**
     * Deletes a recent file record from the log file.
     * @param path path of the recent file to delete from the log
     */
    public static void deleteRecentFilesRecord(String path){
        File filelog = new File(_FILELOG_);
        ArrayList<String> content = Utilities.readRecordRecentFiles();
        content.removeIf(path_i -> path_i.equals(path));
        try(FileWriter fileW = new FileWriter(filelog)){
            fileW.write(String.join("\n", content.toArray(new String[0])));
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    /**
     * Capitalizes each word in the given string. The first letter of each word
     * will be converted to uppercase and the remaining letters will be in lowercase.
     *
     * @param str the string to be capitalized
     * @return the capitalized string or the original string if it is null or empty
     */
    public static String capitalize(String str){
        if(str == null || str.isEmpty())
            return str;
        StringBuilder result = new StringBuilder();
        String[] splitString = str.split(" ");
        for (String str_: splitString){
            if(!(str_.isBlank() || str_.isEmpty())){
                char firstLetter = str_.charAt(0);
                result.append(Character.toUpperCase(firstLetter));
                result.append(str_.substring(1).toLowerCase())
                        .append(" ");
            }
        }
        return result.toString();
    }

    /**
     * Truncates the given string to the specified length and appends ellipsis ("...")
     * if the string is longer than the specified length. If the last line break
     * in the string is within the truncation limit, only the substring after the
     * last line break will be truncated.
     *
     * @param str the string to be truncated
     * @param until the length to truncate the string to
     * @return the truncated string with ellipsis if needed, or the original string
     */
    public static String truncateString(String str, int until){
        // until - 1
        // add ellipsis
        String str_ = str;
        int length = str.length();
        int lastNewLineIndex = str.lastIndexOf("\n");

        if(lastNewLineIndex >= 0 && length - lastNewLineIndex > until) {
            str = str.substring(lastNewLineIndex + 1);
            return str_.substring(0, lastNewLineIndex+1)+str.substring(0, until)+"\n...";
        }
        if(str.length() <= until)
            return str_;
        else
            return str.substring(0, until)+"...";
    }

    /**
     * Checks if the given string is valid. A valid string is not null, not empty,
     * and not blank (does not consist solely of whitespace).
     *
     * @param str the string to be validated
     * @return true if the string is valid, false otherwise
     */
    public static boolean isValidString(String str){
        return (str != null) && ((!str.isEmpty() && !str.isBlank()));
    }

    /**
     * Export the content of the specified HBox as a PDF file.
     *
     * @param HBox     The HBox whose content will be exported.
     * @param filePath The file path where the PDF will be saved.
     * @throws IOException If an I/O error occurs while exporting the PDF.
     */
    public static void exportAsPDF(HBox HBox, String filePath) throws IOException {

        SnapshotParameters snapshotParams = new SnapshotParameters();
        snapshotParams.setFill(Color.TRANSPARENT);
        WritableImage writableImage = HBox.snapshot(snapshotParams, null);

        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(writableImage, null);

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", baos);
            byte[] imageBytes = baos.toByteArray();

            PDImageXObject pdImage = PDImageXObject.createFromByteArray(document, imageBytes, "PDFBox Image");

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                float scale = Math.min(page.getMediaBox().getWidth() / pdImage.getWidth(),
                        page.getMediaBox().getHeight() / pdImage.getHeight());
                float imageWidth = pdImage.getWidth() * scale;
                float imageHeight = pdImage.getHeight() * scale;
                float xOffset = (page.getMediaBox().getWidth() - imageWidth) / 2;
                float yOffset = (page.getMediaBox().getHeight() - imageHeight) / 2;

                contentStream.drawImage(pdImage, xOffset, yOffset, imageWidth, imageHeight);
            }

            document.save(filePath);
        }
    }
}
