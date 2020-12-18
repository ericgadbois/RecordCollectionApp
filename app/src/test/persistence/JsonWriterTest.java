package persistence;

import model.Album;
import model.AlbumCollection;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Code gotten from JsonSerializationDemo

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            AlbumCollection ac = new AlbumCollection();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyAlbumCollection() {
        try {
            AlbumCollection ac = new AlbumCollection();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyAlbumCollection.json");
            writer.open();
            writer.write(ac);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyAlbumCollection.json");
            ac = reader.read();
            assertEquals(0, ac.getCollectionSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralAlbumCollection() {
        try {
            AlbumCollection ac = setUpForTestWriterGeneralAlbumCollection();

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralAlbumCollection.json");
            writer.open();
            writer.write(ac);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralAlbumCollection.json");
            ac = reader.read();
            List<Album> albums = ac.getListOfAlbums();
            assertEquals(2, albums.size());
            checkAlbum("Savage Mode II", "21 Savage", 44, 4, 3, albums.get(0));
            checkAlbum("Yeezus", "Kanye West", 40, 5, 5, albums.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    private AlbumCollection setUpForTestWriterGeneralAlbumCollection() {
        AlbumCollection ac = new AlbumCollection();
        ac.addAlbum(new Album("Savage Mode II", "21 Savage", 44));
        ac.addAlbum(new Album("Yeezus", "Kanye West", 40));
        ac.rateAnAlbum("Savage Mode II", 4);
        ac.rateAnAlbum("Yeezus", 5);
        for (int i = 0; i < 3; i++) {
            ac.listenToAlbum("Savage Mode II");
        }
        for (int i = 0; i < 5; i++) {
            ac.listenToAlbum("Yeezus");
        }
        return ac;
    }
}
