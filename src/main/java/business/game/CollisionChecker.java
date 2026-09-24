package business.game;

import business.characters.PlayableCharacter;
import business.game.map.Room;
import business.game.map.Tile;


import java.awt.*;

/** The CollisionChecker class is responsible for checking
 * collisions between entities and the game word represented by tiles.
 */
public class CollisionChecker {
    /**
     * Constructor of collision checker class
     */
    public CollisionChecker(){

    }

    /** The checkTile method checks if an Entity is colliding
     * @param playableCharacter is the player that is being controlled by the user that is being checked
     */
    public void checkTile(PlayableCharacter playableCharacter, Room room){
        Point location = playableCharacter.getRoom();
        int collisionAreaX = playableCharacter.getHitBox().x;
        int collisionAreaY = playableCharacter.getHitBox().y;
        //To be able to collide we are getting the position of the collider point by point, so that means the relative position of the player + the collisionArea
        int entityLeftX = playableCharacter.getLocation().x - (location.x * Room.ROOM_SIZE * Tile.TILE_SIZE)  + collisionAreaX;
        int entityRightX = playableCharacter.getLocation().x - (location.x * Room.ROOM_SIZE * Tile.TILE_SIZE) + collisionAreaX + playableCharacter.getHitBox().width;
        int entityTopY = playableCharacter.getLocation().y - (location.y * Room.ROOM_SIZE * Tile.TILE_SIZE) + collisionAreaY;
        int entityBottomY = playableCharacter.getLocation().y - (location.y * Room.ROOM_SIZE * Tile.TILE_SIZE) + collisionAreaY + playableCharacter.getHitBox().height;

        //Calculate the column and row of the tiles that entity can collide with
        int entityLeftCol = entityLeftX/Tile.TILE_SIZE;
        int entityRightCol = entityRightX/Tile.TILE_SIZE;
        int entityTopRow = entityTopY/Tile.TILE_SIZE;
        int entityBottomRow = entityBottomY/Tile.TILE_SIZE;

        int tileNum1, tileNum2;

        //To be able to collide we want to predict what tile the player is trying to get access
        switch (playableCharacter.getDirection()){
            //For example, when we are going in "up" direction, maybe the entityTopRow is colliding an object "Wall" in the TopLeft point
            //and a "Floor type in the TopRight point
            case UP:
                try{
                    entityTopRow = (entityTopY - playableCharacter.getSpeed()) / Tile.TILE_SIZE;

                    tileNum1 = room.getTileByCoordinates(entityLeftCol, entityTopRow);
                    tileNum2 = room.getTileByCoordinates(entityRightCol, entityTopRow);

                    if (room.tileIsColliding(tileNum1) || room.tileIsColliding(tileNum2)) {
                        playableCharacter.setCollision(true);
                    }
                    //If we get an arrayIndexOutOFBounds it means that the tile that we want to check is in another room, so based on the direction we
                    //update the room position of the player
                } catch (ArrayIndexOutOfBoundsException e){
                        location.y = location.y - 1;
                }
                break;

            case DOWN:
                try {
                    entityBottomRow = (entityBottomY + playableCharacter.getSpeed()) / Tile.TILE_SIZE;

                    tileNum1 = room.getTileByCoordinates(entityLeftCol, entityBottomRow);
                    tileNum2 = room.getTileByCoordinates(entityRightCol, entityBottomRow);

                    if (room.tileIsColliding(tileNum1) || room.tileIsColliding(tileNum2)) {
                        playableCharacter.setCollision(true);
                    }
                } catch (ArrayIndexOutOfBoundsException e){
                    location.y = location.y + 1;
                }
                break;
            case LEFT:
                try {
                    entityLeftCol = (entityLeftX - playableCharacter.getSpeed()) / Tile.TILE_SIZE;

                    tileNum1 = room.getTileByCoordinates(entityLeftCol, entityTopRow);
                    tileNum2 = room.getTileByCoordinates(entityLeftCol, entityBottomRow);

                    if (room.tileIsColliding(tileNum1) || room.tileIsColliding(tileNum2)) {
                        playableCharacter.setCollision(true);
                    }
                } catch (ArrayIndexOutOfBoundsException e){
                    location.x = location.x - 1;
                }
                break;
            case RIGHT:
                try {
                    entityRightCol = (entityRightX + playableCharacter.getSpeed()) / Tile.TILE_SIZE;

                    tileNum1 = room.getTileByCoordinates(entityRightCol, entityTopRow);
                    tileNum2 = room.getTileByCoordinates(entityRightCol, entityBottomRow);

                    if (room.tileIsColliding(tileNum1) || room.tileIsColliding(tileNum2)) {
                        playableCharacter.setCollision(true);
                    }
                }  catch (ArrayIndexOutOfBoundsException e){
                    location.x = location.x + 1;
                }
                break;
            /*
            case UP_RIGHT:
                try {
                    entityRightCol = (entityRightX + playableCharacter.getSpeed()) / Tile.TILE_SIZE;
                    entityTopRow = (entityTopY - playableCharacter.getSpeed()) / Tile.TILE_SIZE;

                    tileNum1 = room.getTileByCoordinates(entityRightCol, entityTopRow);
                    tileNum2 = room.getTileByCoordinates(entityRightCol, entityBottomRow);

                    if (room.tileIsColliding(tileNum1) || room.tileIsColliding(tileNum2)) {
                        playableCharacter.setCollision(true);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {

                }
                break;
            case UP_LEFT:
                try {
                    entityLeftCol = (entityLeftX - playableCharacter.getSpeed())/Tile.TILE_SIZE;
                    entityTopRow = (entityTopY - playableCharacter.getSpeed())/Tile.TILE_SIZE;

                    tileNum1 = room.getTileByCoordinates(entityLeftCol, entityTopRow);
                    tileNum2 = room.getTileByCoordinates(entityLeftCol, entityBottomRow);

                    if(room.tileIsColliding(tileNum1) || room.tileIsColliding(tileNum2)){
                        playableCharacter.setCollision(true);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {

                }

                break;
            case DOWN_LEFT:
                try {
                    entityLeftCol = (entityLeftX - playableCharacter.getSpeed()) / Tile.TILE_SIZE;
                    entityBottomRow = (entityBottomY + playableCharacter.getSpeed()) / Tile.TILE_SIZE;

                    tileNum1 = room.getTileByCoordinates(entityLeftCol, entityTopRow);
                    tileNum2 = room.getTileByCoordinates(entityLeftCol, entityBottomRow);

                    if (room.tileIsColliding(tileNum1) || room.tileIsColliding(tileNum2)) {
                        playableCharacter.setCollision(true);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {

                }
                break;
            case DOWN_RIGHT:
                try {
                    entityRightCol = (entityRightX + playableCharacter.getSpeed()) / Tile.TILE_SIZE;
                    entityBottomRow = (entityBottomY + playableCharacter.getSpeed()) / Tile.TILE_SIZE;

                    tileNum1 = room.getTileByCoordinates(entityRightCol, entityTopRow);
                    tileNum2 = room.getTileByCoordinates(entityRightCol, entityBottomRow);

                    if (room.tileIsColliding(tileNum1) || room.tileIsColliding(tileNum2)) {
                        playableCharacter.setCollision(true);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {

                }
                break;*/
        }
    }
}
