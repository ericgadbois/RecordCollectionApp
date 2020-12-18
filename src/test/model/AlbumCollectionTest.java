package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AlbumCollectionTest {
    private AlbumCollection testCollection;
    Album album1;
    Album album2;

    @BeforeEach
    void runBefore() {
        testCollection = new AlbumCollection();
        album1 = new Album("Red (Deluxe Edition)", "Taylor Swift", 90);
        album2 = new Album("Savage Mode II", "21 Savage", 44);
        Album album3 = new Album("My Beautiful Dark Twisted Fantasy", "Kanye West", 68);
        testCollection.addAlbum(album1);
        testCollection.addAlbum(album2);
        testCollection.addAlbum(album3);
    }

//    @Test
//    void addAlbumTestOne() {
//        Album album1 = new Album("Red (Deluxe Edition)", "Taylor Swift", 90);
//        testCollection.addAlbum(album1);
//        assertEquals(1, testCollection.getCollectionSize());
//    }

    @Test
    void addAlbumTestNotAlreadyThere() {
        assertEquals(3, testCollection.getCollectionSize());
        assertTrue(album1.getCollections().contains(testCollection));
    }

    @Test
    void addAlbumTestAlreadyThere() {
        testCollection.addAlbum(album2);
        assertEquals(3, testCollection.getCollectionSize());
        assertTrue(album2.getCollections().contains(testCollection));
    }

    @Test
    void removeAlbumTestNotThere() {
        testCollection.removeAlbum(album1);
        assertEquals(2, testCollection.getCollectionSize());
        assertFalse(testCollection.getListOfAlbums().contains(album1));
        assertFalse(album1.getCollections().contains(testCollection));

        testCollection.removeAlbum(album1);
        assertEquals(2, testCollection.getCollectionSize());
    }

    @Test
    void removeAlbumTest() {
        testCollection.removeAlbum(album1);
        assertEquals(2, testCollection.getCollectionSize());
        assertFalse(testCollection.getListOfAlbums().contains(album1));
        assertFalse(album1.getCollections().contains(testCollection));
    }

    @Test
    void getAlbumsWithRatingTestNone() {
        List<Album> listOfAlbum = testCollection.getAlbumsWithRating(3);
        assertEquals(0, listOfAlbum.size());
    }

    @Test
    void testAlbumInCollectionTrue() {
        Album album2 = new Album("Savage Mode II", "21 Savage", 44);
        assertTrue(testCollection.albumInCollection(album2));
    }

    @Test
    void testAlbumInCollectionFalseName() {
        Album album2 = new Album("Savage Mode", "21 Savage", 44);
        assertFalse(testCollection.albumInCollection(album2));
    }

    @Test
    void testAlbumInCollectionFalseArtist() {
        Album album2 = new Album("Savage Mode II", "Metro Boomin", 44);
        assertFalse(testCollection.albumInCollection(album2));
    }

    @Test
    void testAlbumInCollectionFalseLength() {
        Album album2 = new Album("Savage Mode II", "21 Savage", 75);
        assertFalse(testCollection.albumInCollection(album2));
    }

    @Test
    void getAlbumsWithRatingTestSome() {
        testCollection.rateAnAlbum("My Beautiful Dark Twisted Fantasy", 5);
        testCollection.rateAnAlbum("Savage Mode II", 4);
        List<Album> listOfAlbum = testCollection.getAlbumsWithRating(4);
        assertEquals(2, listOfAlbum.size());
    }

    @Test
    void getAlbumsWithListensNone() {
        List<Album> listOfAlbum = testCollection.getAlbumsWithListens(10);
        assertEquals(0, listOfAlbum.size());
    }

    @Test
    void getAlbumsWithListensSome() {
        for (int i = 0; i < 5; i++) {
            testCollection.listenToAlbum("Savage Mode II");
            testCollection.listenToAlbum("Red (Deluxe Edition)");
        }
        List<Album> listOfAlbum = testCollection.getAlbumsWithListens(5);
        assertEquals(2, listOfAlbum.size());
    }

    @Test
    void mostListenedAlbumTest() {
        Album album = new Album("Yeezus", "Kanye West", 40);
       testCollection.addAlbum(album);
        for (int i = 0; i < 5; i++) {
            testCollection.listenToAlbum("Yeezus");
        }
        assertEquals(album, testCollection.mostListenedAlbum());
    }
}

