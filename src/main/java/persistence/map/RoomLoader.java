package persistence.map;

import business.game.Event;
import business.game.map.Room;
import business.game.map.Tile;
import business.game.map.attributes.RoomType;
import persistence.exceptions.PersistenceException;
import persistence.exceptions.mapLoadingException;


import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class RoomLoader {

    /**
     * Constructs a RoomLoader object with the specified game panel.
     * Reads tile data and map data from external files, initializes tile images and collision status,
     * and sets up the mapTileNum array.
     * @param room name of the room to get
     * @return Instance of Room class according to the room selected
     * @throws PersistenceException occurs if the map can not be loaded
     */
    public Room loadRoom(String room) throws PersistenceException {

        ArrayList<String> fileNames = new ArrayList<>();
        ArrayList<String> collisionStatus = new ArrayList<>();
        String generalRoom = room.replaceAll("_.+", "");
        //Read the data file
        InputStream inputStream = getClass().getResourceAsStream("/rooms/tile_data/tile_"+generalRoom+".txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        //Get tile names and collision info from files
        String tileDataLine;
        try{
            while((tileDataLine = bufferedReader.readLine()) != null){
                fileNames.add(tileDataLine);
                collisionStatus.add(bufferedReader.readLine());
            }
            bufferedReader.close();
        } catch (IOException e) {
            throw new mapLoadingException();
        }
        //Initialize the array based on the size of the fileName variable
        Tile[] tiles = new Tile[fileNames.size()];
        Tile [] tilesFilled = getTileImage(tiles, fileNames, collisionStatus, generalRoom);

        //Get maxWordCol & Row
        inputStream = getClass().getResourceAsStream("/rooms/"+generalRoom+"/"+room+".txt");
        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        try{
            String roomDataLine = bufferedReader.readLine();
            String maxTile[] = roomDataLine.split(" ");
            //A room is a square
            int[][] mapTileNum = new int[maxTile.length][maxTile.length];
            int[][] mapTileNumFilled = loadRoom("/rooms/"+generalRoom+"/"+room+".txt", mapTileNum, maxTile.length);
            bufferedReader.close();
            ArrayList<Event> events = getEventFromRoom(generalRoom, room);
            generalRoom = generalRoom.replaceAll("\\d","");
            Room roomInstance = new Room(room, RoomType.fromString(generalRoom) , tiles, mapTileNumFilled, events);
            return roomInstance;
        } catch (IOException e){
            throw new mapLoadingException();
        }

    }

    /**
     * Populates the Tile[] array with Tile objects containing their respective image and collision status.
     * @param tiles array of tiles to be filled
     * @param fileNames Arraylist of names of the files of each tile
     * @param collisionStatus arraylist that contains the status of each tile
     * @param room Name of the room ar
     * @return Array of tiles filled
     * @throws PersistenceException occurs if the tiles cannot be filled due to map loading exception
     */
    public Tile[] getTileImage(Tile[] tiles, ArrayList<String> fileNames, ArrayList<String> collisionStatus, String room) throws PersistenceException {

        for(int i = 0; i < fileNames.size(); i++){
            String fileName;
            boolean collision;
            //Get a file name
            fileName = fileNames.get(i);
            //Get collision statement
            if(collisionStatus.get(i).equals("true")){
                collision = true;
            }
            else {
                collision = false;
            }
            setUpTile(tiles, i, fileName, collision, room);
        }
        return tiles;
    }

    /**
     * Helper function for getTileImage that loads and scales the image, and sets the collision status.
     * @param index The index of the Tile in the Tile[] array.
     * @param imageName The name of the Tile's image file.
     * @param collision The Tile's collision status.
     * @param tiles Array of tiles to be set up
     * @param room name of the room to set up tiles
     * @return Arrays of tiles set up
     * @throws PersistenceException occurs if the tiles cannot be filled due to map loading exception
     */
    public Tile[] setUpTile(Tile[] tiles, int index, String imageName, boolean collision, String room) throws PersistenceException{
        ImageLoader imageModifier = new ImageLoader();
        try{
            tiles[index] = new Tile(imageModifier.scaleImage(ImageIO.read(getClass().getClassLoader().getResourceAsStream("tile/"+room+"/"+imageName))), collision);
            return tiles;
        } catch (IOException e){
            throw new mapLoadingException();
        }
    }

    /**
     * Reads and loads the room file to the mapTileNum array. Each element of the array
     * represents a single tile in the game world.
     * @param path the path of the room file to be loaded
     * @param mapTileNum matrix of int[][] that indicates the room tile num
     * @param maxTile integer that indicates the max amount of tiles
     * @return the filled room tile num matrix
     * @throws PersistenceException occurs if the tiles cannot be filled due to map loading exception
     */
    public int[][] loadRoom(String path, int[][] mapTileNum, int maxTile) throws PersistenceException{
        try{
            InputStream inputStream = getClass().getResourceAsStream(path);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            int col = 0;
            int row = 0;
            //We are going to read the .txt map file
            while (col < maxTile && row < maxTile){
                //Read a line by line the .txt
                String line = bufferedReader.readLine();

                while (col < maxTile){
                    //Split the line by " " (spaces)
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if(col == maxTile){
                    col = 0;
                    row++;
                }
            }
            bufferedReader.close();
            return mapTileNum;
        } catch (Exception e){
            throw new mapLoadingException();
        }
    }

    /**
     * Reads and loads the events file to the room.
     * @param generalRoom is the name of the room without the connections
     * @param room is the name of the room with the connections
     * @return an ArrayList of events objects
     */
    public ArrayList<Event> getEventFromRoom(String generalRoom, String room){
        try{
            InputStream inputStream = getClass().getResourceAsStream("/events/"+generalRoom+"/"+room+".txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            ArrayList<Event> events = new ArrayList<>();
            String tileDataLine;

            while((tileDataLine = bufferedReader.readLine()) != null){
                Scanner scanner = new Scanner(tileDataLine);
                int x  = scanner.nextInt();
                int y = scanner.nextInt();
                int width = scanner.nextInt();
                int height = scanner.nextInt();
                String Event = scanner.next();
                //Falta constructor
                events.add(new Event(new Rectangle(x,y,width,height)));
            }
            bufferedReader.close();
            return events;
        } catch (Exception e){
            return new ArrayList<>();
        }
    }


}