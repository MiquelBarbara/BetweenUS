package business.game.map;


import business.exceptions.BusinessException;
import business.exceptions.ResourcesNotAccessedException;
import persistence.exceptions.PersistenceException;
import persistence.map.MapLoader;
import persistence.map.RoomLoader;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a manager for the tiles used in the game, including their images and collision status.
 */
public class TileManager {
    /**
     * Loader of the map to get the map
     */
    private final MapLoader mapLoader;
    /**
     * Loader class of the rooms to get the rooms
     */
    private final RoomLoader roomLoader;

    /**
     * Constructor of the TileManager class
     */
    public TileManager(){
        mapLoader = new MapLoader();
        roomLoader = new RoomLoader();
    }

    /**
     * Function that given the mapName if returns and Instance of GameMap (the map)
     * @param mapName name of the map to get
     * @return Instance of GameMap representing the selected chosen map
     * @throws BusinessException occurs when the map can not be loaded correctly
     */
    public GameMap getMap(String mapName) throws BusinessException {

        try {
            Map<Point, Room> map = new HashMap<>();
            Map<Point, String> mapTemp = mapLoader.loadMap(mapName);

            int maxColumns = 0;
            int maxRows = 0;
            for (Map.Entry<Point, String> entry : mapTemp.entrySet()) {
                Point point = entry.getKey();
                if (point.getX() > maxRows) {
                    maxRows = (int) point.getX();
                }

                if (point.getY() > maxColumns) {
                    maxColumns = (int) point.getY();
                }
                String roomName = entry.getValue();

                Room room = roomLoader.loadRoom(roomName);

                map.put(point, room);
            }

            GameMap gameMap = new GameMap(maxColumns, maxRows, mapName, map, mapLoader.getInitialRoom(mapName), mapLoader.getScuttles(mapName));

            return gameMap;
        } catch (PersistenceException e) {
            throw new ResourcesNotAccessedException();
        }
    }

    /**
     * Function used to get the initial room of the map given its name
     * @param mapName Name of the map
     * @return Point according to the map initial room
     * @throws BusinessException Occuers when the resources (map) cannot be loaded
     */
    public Point getMapInitialPosition(String mapName) throws BusinessException {
        GameMap gameMap = getMap(mapName);
        return gameMap.getInitialRoom();
    }

}
