package persistence.game.character;

import business.characters.Character;
import business.characters.NonPlayableCharacter;
import business.characters.PlayableCharacter;
import business.characters.attributes.Colour;
import business.characters.attributes.Perception;
import business.characters.roles.CrewMate;
import business.characters.roles.Impostor;
import business.characters.roles.Role;
import business.exceptions.BusinessException;
import persistence.exceptions.PersistenceException;
import persistence.exceptions.DBNotAccessibleException;
import persistence.database.SQLConnector;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * DAO of player connected to DB
 */
public class SQLPlayerDAO implements PlayerDAO {
    /**
     * Constructor of SQLPlayerDAO
     */
    public SQLPlayerDAO() {

    }

    @Override
    public void addPlayerInGame(Character character, int gameID) throws PersistenceException {
        String query ="INSERT INTO Player_In_Game(ID_Player, ID_Game, colour) VALUES ('" +
                character.getID() + "', '" +
                gameID + "', '" +
                character.getColour().label +
                "');";
        try {
            SQLConnector.getInstance().insertQuery(query);
        } catch (DBNotAccessibleException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deletePlayerInGame(int characterID, int gameID) throws PersistenceException {
        String query = "DELETE FROM Player_In_Game WHERE ID_Game = '" + gameID + "' AND ID_Player = '" + characterID + "';";
        try {
            SQLConnector.getInstance().deleteQuery(query);
        } catch (DBNotAccessibleException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int addPlayer(Character character, boolean playable) throws PersistenceException {
        int isDead = character.isDead() ? 1:0;
        int isImpostor = character.canKill() ? 1:0;
        int characterID = generateCharacterID();
        int playableValue = playable ? 1:0;
        String query ="INSERT INTO Player(ID_Player, colour, rol, dead, impostor, Xposition, Yposition, playable) VALUES ('" +
                characterID + "', '" +
                character.getColour().label + "', '" +
                character.getDefaultPerception() + "', '" +
                isDead + "', '" +
                isImpostor + "', '" +
                character.getRoom().x + "', '" +
                character.getRoom().y + "', '" +
                playableValue +
                "');";
        try {
            SQLConnector.getInstance().insertQuery(query);
        } catch (DBNotAccessibleException e) {
            throw new RuntimeException(e);
        }
        return characterID;
    }

    private int generateCharacterID() throws PersistenceException {
        String query = "SELECT MAX(ID_Player) as maxID FROM Player;";
        ResultSet result = null;
        try {
            result = SQLConnector.getInstance().selectQuery(query);
        } catch (DBNotAccessibleException e) {
            throw new RuntimeException(e);
        }
        int id = 1;
        try {
            while (result.next()) {
                int maxID = result.getInt("maxID");
                id = maxID + 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return id;
    }

    @Override
    public void deletePlayer(int characterID) throws PersistenceException {
        String query = "DELETE FROM Player WHERE ID_Player = '" + characterID + "';";
        try {
            SQLConnector.getInstance().deleteQuery(query);
        } catch (DBNotAccessibleException e) {
            throw new RuntimeException(e);
        }
    }

    public Character getPlayer(int playerID) throws PersistenceException {
        String query = "SELECT ID_Player, Colour, Rol, Dead, Impostor, Xposition, Yposition FROM Player WHERE ID_Player = '" + playerID + "';";
        ResultSet result = null;
        try {
            result = SQLConnector.getInstance().selectQuery(query);
        } catch (DBNotAccessibleException e) {
            throw new RuntimeException(e);
        }
        Character character = null;
        try {
            while (result.next()) {
                int id = result.getInt("ID_Player");
                String colour = result.getString("Colour");
                String perception = result.getString("Rol"); // rol label refers to player perception on the NPC
                boolean dead = result.getInt("Dead") == 1;
                int impostor = result.getInt("Impostor");
                float xPosition = result.getFloat("Xposition");
                float yPosition = result.getFloat("Yposition");
                boolean isPlayable = result.getInt("playable") == 1;

                Colour selectedColour = Colour.fromString(colour);
                Point location = new Point((int) xPosition, (int) yPosition);
                Role role = impostor == 1 ? new Impostor(): new CrewMate();

                if (isPlayable) {
                    character = new PlayableCharacter(selectedColour, role, location);
                    character.setID(id);


                } else {
                    //character = new NonPlayableCharacter(id, colour, null, dead, impostor, xPosition, yPosition);
                    character = new NonPlayableCharacter(selectedColour, role, location);
                    ((NonPlayableCharacter) character).setPerception(Perception.fromString(perception));
                    character.setID(id);
                    if (dead) {
                        character.die();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (BusinessException e) {
            throw new RuntimeException(e);
        }
        return character;
    }

    @Override
    public PlayableCharacter getMainCharacter(int gameID) throws PersistenceException {

        String query = "SELECT Player.ID_Player, Player.Colour, Rol, dead, Impostor, Xposition, Yposition, playable FROM Player " +
                "JOIN Player_In_Game ON Player_In_Game.ID_Player = Player.ID_Player WHERE ID_Game = '" + gameID +
                "' AND playable = '1';";

        ResultSet result = null;
        try {
            result = SQLConnector.getInstance().selectQuery(query);
            PlayableCharacter character = null;

                while (result.next()) {
                    int id = result.getInt("Player.ID_Player");
                    String colour = result.getString("Player.Colour");
                    float xPosition = result.getFloat("Xposition");
                    float yPosition = result.getFloat("Yposition");

                    Colour selectedColour = Colour.fromString(colour);
                    Point location = new Point((int) xPosition, (int) yPosition);

                    character = new PlayableCharacter(selectedColour, new CrewMate(), location);
                    character.setID(id);

                }
                return character;
        } catch (DBNotAccessibleException | BusinessException | SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public ArrayList<NonPlayableCharacter> getNPC(int gameID) throws PersistenceException {

        String query = "SELECT Player.ID_Player, Player.Colour, Rol, dead, Impostor, Xposition, Yposition, playable FROM Player " +
                "JOIN Player_In_Game ON Player_In_Game.ID_Player = Player.ID_Player WHERE ID_Game = '" + gameID +
                "' AND playable = '0';";

        ResultSet result = null;
        try {
            result = SQLConnector.getInstance().selectQuery(query);
        } catch (DBNotAccessibleException e) {
            throw new RuntimeException(e);
        }
        ArrayList<NonPlayableCharacter> characters = new ArrayList<>();
        try {
            while (result.next()) {
                int id = result.getInt("Player.ID_Player");
                String colour = result.getString("Player.Colour");
                String perception = result.getString("Rol");
                boolean dead = result.getInt("Dead") == 1;
                int impostor = result.getInt("Impostor");
                float xPosition = result.getFloat("Xposition");
                float yPosition = result.getFloat("Yposition");

                Colour selectedColour = Colour.fromString(colour);
                Point location = new Point((int) xPosition, (int) yPosition);
                Role role = impostor == 1 ? new Impostor(): new CrewMate();

                NonPlayableCharacter character = new NonPlayableCharacter(selectedColour, role, location);
                character.setPerception(Perception.fromString(perception));
                character.setID(id);
                if (dead) {
                    character.die();
                }

                characters.add(character);
            }
        } catch (SQLException | BusinessException e) {
            throw new RuntimeException(e);
        }
        return characters;
    }

    @Override
    public void deleteMovementHistorial(int characterID) throws PersistenceException {
        String query = "DELETE FROM Historial_Movements WHERE ID_Player = '" + characterID + "';";
        try {
            SQLConnector.getInstance().deleteQuery(query);
        } catch (DBNotAccessibleException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addMovementHistorial(int playerID, String roomName, int instant, String rol) throws PersistenceException {
        // TODO
        String query = "INSERT INTO Historial_Movements (ID_Player, Sala, instant, rol) VALUES ('" +
                playerID + "', '" +
                roomName + "', '" +
                instant + "', '" +
                rol + "');";
        SQLConnector.getInstance().insertQuery(query);
    }

    @Override
    public void updatePlayer(Character character) throws PersistenceException {
        int dead = character.isDead() ? 1:0;

        String query = "UPDATE Player SET dead = '" + dead + "', Rol = '" + character.getPerception().label +
                "', Xposition = '" + character.getRoom().x + "', Yposition = '" +
                character.getRoom().y + "' WHERE ID_Player = '" + character.getID() + "';";
        SQLConnector.getInstance().updateQuery(query);


    }
}
