package persistence.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import persistence.exceptions.PersistenceException;
import persistence.exceptions.ConfigurationFileNotAccessibleException;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Class that is used to read the Configuration File of the program
 */
public class ConfigurationDAO {
    /**
     * String that indicates the path of the ConfigurationFile
     */
    private final String PATH;
    /**
     * FileReader used to read the configuration file
     */
    private final FileReader fileReader;
    /**
     * Gson instance used to parse the configuration file
     */
    private final Gson gson;

    /**
     * Constructor of ConfigurationDAO
     */
    public ConfigurationDAO() throws PersistenceException {
        String PATH1;
        FileReader fileReader1;
        Gson gson1;

        try {
            PATH1 = "config/database.json";
            fileReader1 = new FileReader(PATH1);
            gson1 =  new GsonBuilder().setPrettyPrinting().create();
        } catch (FileNotFoundException e) {
            PATH1 = null;
            fileReader1 = null;
            gson1 = null;
            throw new ConfigurationFileNotAccessibleException();
        }

        this.PATH = PATH1;
        this.fileReader = fileReader1;
        this.gson = gson1;
    }

    /**
     * Function used to read the configuration file
     * @return Instance of the configuration file readed
     */
    public ConfigurationFile readFile() {
        return gson.fromJson(fileReader, ConfigurationFile.class);
    }
}
