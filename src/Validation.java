public class Validation
{
    public static String validateFileName(String fileName) {
        if (fileName.endsWith(".txt")) {
            return fileName;
        }
        else {
            return fileName + ".txt";
        }
    }
}
