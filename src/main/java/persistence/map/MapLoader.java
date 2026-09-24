package persistence.map;

import business.game.map.Scuttle;
import persistence.exceptions.PersistenceException;
import persistence.exceptions.mapLoadingException;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Class used to load Map
 */
public class MapLoader {

    /**
     * Reads and loads the map file to the mapTileNum array. Each element of the array
     * represents a single tile in the game world.
     * @param mapName name of the map to load
     * @return HashMap of all points and tileName of the map
     * @exception mapLoadingException is thrown when the map can not be loaded
     */
    public Map<Point, String> loadMap(String mapName) throws PersistenceException {
        Map<Point, String> map = new HashMap<>();
        InputStream inputStream = getClass().getResourceAsStream("/map/"+mapName+".txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        try{
            int column = 0;
            int rows = 0;
            String line;
            //We are going to read the .txt map file
            while (((line = bufferedReader.readLine()) != null)) {
                //Read a line by line the .txt
                String [] divLine = line.split(" ");

                for ( int i = 0; i < divLine.length; i++){
                    //Split the line by " " (spaces)
                    map.put(new Point(column,rows), divLine[i]);
                    column++;
                }
                rows++;
                column = 0;
            }
            bufferedReader.close();
            return map;

        } catch (Exception e){
            throw new mapLoadingException();
        }

    }

    /**
     * Function that gets the initialRoom of a map given its name
     * @param mapName name of the map to look on
     * @return Point according to the initialRoom of the map
     * @exception PersistenceException is thrown when the map can not be loaded
     */
    public Point getInitialRoom(String mapName) throws PersistenceException {
        try{
            InputStream inputStream = getClass().getResourceAsStream("/map/"+mapName+"_data.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String tileDataLine = bufferedReader.readLine();
            Scanner scanner = new Scanner(tileDataLine);
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            bufferedReader.close();
            return new Point(x,y);

        } catch (Exception e){
            throw new mapLoadingException();
        }
    }

    public ArrayList<Scuttle> getScuttles(String mapName){
        try{
            InputStream inputStream = getClass().getResourceAsStream("/map/"+mapName+"_scuttles.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            ArrayList<Scuttle> scuttles = new ArrayList<>();
            String tileDataLine;

            while((tileDataLine = bufferedReader.readLine()) != null){
                Scanner scanner = new Scanner(tileDataLine);
                int room1X  = scanner.nextInt();
                int room1Y = scanner.nextInt();
                int room2X = scanner.nextInt();
                int room2Y = scanner.nextInt();

                scuttles.add(new Scuttle(new Point(room1X,room1Y), new Point(room2X,room2Y)));
            }
            bufferedReader.close();
            return scuttles;
        } catch (Exception e){
            return new ArrayList<>();
        }
    }
}
