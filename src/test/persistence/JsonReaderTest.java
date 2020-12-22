package persistence;

import model.Album;
import model.AlbumCollection;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Code gotten from JsonSerializationDemo

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            AlbumCollection ac = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyAlbumCollection() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyAlbumCollection");
        try {
            AlbumCollection ac = reader.read();
            assertEquals(0, ac.getCollectionSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralAlbumCollection() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralAlbumCollection");
        try {
            AlbumCollection ac = reader.read();
            List<Album> albums = ac.getListOfAlbums();
            assertEquals(2, albums.size());
            checkAlbum("Savage Mode II", "21 Savage", 44, 4, 3, albums.get(0));
            checkAlbum("Yeezus", "Kanye West", 40, 5, 5, albums.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
