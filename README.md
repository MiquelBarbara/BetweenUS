# BetweenUs

BetweenUs is a Java Swing social deduction game inspired by *Among Us*. Players explore a tile-based map, complete room events, and try to identify the impostor. The project was built as a five-person team assignment for the Object-Oriented Programming course at La Salle, 2022–2023.

<img width="603" height="387" alt="image" src="https://github.com/user-attachments/assets/87139f3b-75cc-4ca7-960d-fc008660467a" />


## My contribution

I implemented the map-generation system, which reads plain-text map files to place tiles; the viewport-aware rendering that selects images based on world position and the visible area; collision detection; and the player-sprite animation system.

## Features

- Explore multiple maps and rooms built from text-based tile layouts.
- Move and animate a player character while the viewport renders the visible map.
- Interact with room events and game logs.
- Play as crew members or an impostor, with configurable game settings.
- Register and log in, save or resume games, and view game history and statistics.

## Architecture

- `src/main/java/business/` contains game rules, characters, maps, and game state.
- `src/main/java/persistence/` contains database access and map/resource loaders.
- `src/main/java/presentation/` contains the Swing interface and controllers.
- `src/main/resources/` contains maps, rooms, events, tiles, sprites, and UI images.
- `database/` contains scripts to create or drop the database tables.

## Requirements

- Java 18
- Maven 3.8 or later
- MySQL 8.0 or later

## Build and run

1. Create the database and tables from the repository root:

   ```sh
   mysql -u root -p < database/create-schema.sql
   ```

2. Create a MySQL user with access to the `betweenus` database. For example, from a MySQL administrator session:

   ```sql
   CREATE USER 'betweenus'@'localhost' IDENTIFIED BY 'choose-a-password';
   GRANT ALL PRIVILEGES ON betweenus.* TO 'betweenus'@'localhost';
   ```

3. Copy `config/database.example.json` to `config/database.json` and set your local MySQL username and password. The local file is ignored by Git.

4. Build and launch from the repository root:

   ```sh
   mvn clean package
   java -jar target/betweenus.jar
   ```

   On Windows PowerShell, copy the configuration with `Copy-Item config/database.example.json config/database.json`.

The game uses **W**, **A**, **S**, and **D** to move and **Esc** to leave and save a game. The original project notes that keyboard input may require switching focus away from and back to the game window after a game starts.

## Database scripts

`database/create-schema.sql` creates the `betweenus` database and its tables. `database/drop-schema.sql` deletes those tables; use it only when you intend to reset the database. The original exported database dump is not included because it contained a plaintext credential. No sample user data is required; create an account through the game. This is an academic prototype and has not been hardened for production use.

## Project team

- Miquel Barbarà
- Ainhoa Corominas
- Marc Joan Sabater
- Oleguer Almuni
- David Casadó

This is a collaborative academic project. The project team retains the original course context and attribution; the contribution above describes my work within that team.
