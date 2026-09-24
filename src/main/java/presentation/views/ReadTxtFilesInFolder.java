package presentation.views;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
/**
 * This class reads the files in the resources folder
 */
public class ReadTxtFilesInFolder {
    /**
     * private variable that stores the path we have to enter
     */
    private final String folderPath = "resources/map";

    /**
     * private variable that creates the file we have to access
     */
    File folder;

    /**
     * private variable that stores the filter we want to apply
     */
    FilenameFilter txtFileFilter;

    /**
     * function that creates the class
     */
    public ReadTxtFilesInFolder () {
        folder = new File(folderPath);
        txtFileFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".txt");
            }
        };
    }

    /**
     * function that gets all the names of the files
     * @return an ArrayList of strings with all the names
     */
    public ArrayList<String> getNameFiles () {
        // Get the list of files in the folder matching the filter
        File[] txtFiles = folder.listFiles(txtFileFilter);
        ArrayList<String> txtNames = new ArrayList<>();
        if (txtFiles != null) {
            for (File file : txtFiles) {
                if (!file.getName().contains("data") && !file.getName().contains("_scuttles")) {
                    txtNames.add(file.getName().replace(".txt", ""));
                }
            }
        }
        return txtNames;
    }
}

