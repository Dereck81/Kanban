package pe.edu.utp.dsa.Kanban.Utilities;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import pe.edu.utp.dsa.Kanban.Kanban;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class IO {

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
        ArrayList<String> content = IO.readRecordRecentFiles();
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
        ArrayList<String> content = IO.readRecordRecentFiles();
        content.removeIf(path_i -> path_i.equals(path));
        try(FileWriter fileW = new FileWriter(filelog)){
            fileW.write(String.join("\n", content.toArray(new String[0])));
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
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

    public static void saveData(String content, String filePath) throws Exception{
        try(FileWriter fileW = new FileWriter(filePath)){
            fileW.write(content);
        }
        GlobalExceptionHandler.alertInformation(
                "Information",
                "Information about the saved file",
                "the file was exported successfully");
    }

}
