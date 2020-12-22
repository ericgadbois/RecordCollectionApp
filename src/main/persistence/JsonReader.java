package persistence;

import model.Album;
import model.AlbumCollection;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Code gotten from JsonSerializationDemo

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads album collection from file and returns it;
    // throws IOException if an error occurs reading data from file
    public AlbumCollection read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseAlbumCollection(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses album collection from JSON object and returns it
    private AlbumCollection parseAlbumCollection(JSONObject jsonObject) {
        AlbumCollection ac = new AlbumCollection();
        addAlbums(ac, jsonObject);
        return ac;
    }

    // MODIFIES: ac
    // EFFECTS: parses albums from JSON object and adds them to album collection
    private void addAlbums(AlbumCollection ac, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("albums");
        for (Object json : jsonArray) {
            JSONObject nextAlbum = (JSONObject) json;
            addAlbum(ac, nextAlbum);
        }
    }

    // MODIFIES: ac
    // EFFECTS: parses album from JSON object and adds it to album collection
    private void addAlbum(AlbumCollection ac, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String artist = jsonObject.getString("artist");
        int length = jsonObject.getInt("length");
        int rating = jsonObject.getInt("rating");
        int listens = jsonObject.getInt("listens");
        Album album = new Album(name, artist, length);
        album.setAlbumRating(rating);
        album.setAlbumListens(listens);
        ac.addAlbum(album);
    }
}