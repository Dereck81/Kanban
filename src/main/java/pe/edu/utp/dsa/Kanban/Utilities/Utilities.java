package pe.edu.utp.dsa.Kanban.Utilities;

import javafx.scene.control.*;
import pe.edu.utp.dsa.Kanban.Kanban;

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

    public static boolean isValidString(String str){
        return (str != null) && ((!str.isEmpty() && !str.isBlank()));
    }


    public static void exportAsPDF(){

    }





}
