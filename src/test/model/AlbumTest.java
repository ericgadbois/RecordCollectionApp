package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlbumTest {
    private Album testAlbum;

    @BeforeEach
    void runBefore() { testAlbum =  new Album("Yeezus", "Kanye West", 40);
    }

    @Test
    void getAlbumNameTest() {
        assertEquals("Yeezus", testAlbum.getAlbumName());
    }

    @Test
    void getAlbumArtistTest() {
        assertEquals("Kanye West", testAlbum.getAlbumArtist());
    }

    @Test
    void getAlbumLengthTest() {
        assertEquals(40, testAlbum.getAlbumLength());
    }

    @Test
    void addAListenTest() {
        testAlbum.addAListen();
        assertEquals(1, testAlbum.getAlbumListens());
    }

    @Test
    void rateAlbumTest() {
        testAlbum.setAlbumRating(5);
        assertEquals(5, testAlbum.getAlbumRating());
    }

    @Test
    void equalsTestTrue() {
        Album album = new Album("Yeezus", "Kanye West", 40);
        assertTrue(testAlbum.equals(album));
    }

    @Test
    void equalsTestFalseName() {
        Album album = new Album("DONDA", "Kanye West", 40);
        assertFalse(testAlbum.equals(album));
    }

    @Test
    void equalsTestFalseArtist() {
        Album album = new Album("Yeezus", "Jay-Z", 40);
        assertFalse(testAlbum.equals(album));
    }

    @Test
    void equalsTestFalseLength() {
        Album album = new Album("Yeezus", "Kanye West", 60);
        assertFalse(testAlbum.equals(album));
    }

    @Test
    void toStringTest() {
        String name = testAlbum.toString();
        assertEquals("Yeezus", name);
    }

    @Test
    void addCollectionTest() {
        AlbumCollection testCollection = new AlbumCollection();
        testAlbum.addCollection(testCollection);
        assertEquals(1, testAlbum.getCollections().size());
        assertTrue(testCollection.albumInCollection(testAlbum));
    }

    @Test
    void addCollectionTestAlreadyThere() {
        AlbumCollection testCollection = new AlbumCollection();
        testAlbum.addCollection(testCollection);
        assertEquals(1, testAlbum.getCollections().size());
        assertTrue(testCollection.albumInCollection(testAlbum));

        testAlbum.addCollection(testCollection);
        assertEquals(1, testAlbum.getCollections().size());
        assertEquals(1, testCollection.getCollectionSize());
        assertTrue(testCollection.albumInCollection(testAlbum));
    }

    @Test
    void removeCollectionTestNotThere() {
        AlbumCollection testCollection = new AlbumCollection();
        testAlbum.removeCollection(testCollection);
        assertEquals(0, testAlbum.getCollections().size());
        assertFalse(testCollection.albumInCollection(testAlbum));
    }

    @Test
    void removeCollectionTest() {
        AlbumCollection testCollection = new AlbumCollection();
        testAlbum.addCollection(testCollection);
        assertEquals(1, testAlbum.getCollections().size());
        assertTrue(testCollection.albumInCollection(testAlbum));

        testAlbum.removeCollection(testCollection);
        assertEquals(0, testAlbum.getCollections().size());
        assertFalse(testCollection.albumInCollection(testAlbum));
    }

}