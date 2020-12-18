package persistence;

import model.Album;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Code gotten from JsonSerializationDemo

public class JsonTest {
    protected void checkAlbum(String name, String artist,
                              int length, int rating,
                              int listens, Album album) {
        assertEquals(name, album.getAlbumName());
        assertEquals(artist, album.getAlbumArtist());
        assertEquals(length, album.getAlbumLength());
        assertEquals(rating, album.getAlbumRating());
        assertEquals(listens, album.getAlbumListens());
    }
}
