package pe.edu.utp.dsa.Kanban.Utilities;

public class Utilities {

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

}
