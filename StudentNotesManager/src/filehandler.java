import java.io.FileWriter;
import java.io.IOException;

public class FileHandler {

    public static void saveToFile(String title, String content, boolean important) {
        try (FileWriter fw = new FileWriter("notes_backup.txt", true)) {

            fw.write(title + "\n");
            fw.write(content + "\n");

            if (important) {
                fw.write("IMPORTANT\n");
            }

            fw.write("------------------\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}