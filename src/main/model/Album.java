package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents an album having a name, artist, length (in minutes), personal rating out of 5, and number of listens
public class Album implements Writable {
    private String albumName;
    private String albumArtist;
    private int albumLength;
    private int rating;
    private int numOfListens;
    private List<AlbumCollection> collections;

    // Constructs an album
    // REQUIRES: name and artist to have non-zero string length
    // EFFECTS: Creates an album with albumName name, albumArtist artist, albumLength length,
    //          a rating of 0, and numOfListens of 0
    public Album(String name, String artist, int length) {
        albumName = name;
        albumArtist = artist;
        albumLength = length;
        rating = 0;
        numOfListens = 0;
        collections = new ArrayList<>();
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getAlbumArtist() {
        return albumArtist;
    }

    public int getAlbumLength() {
        return albumLength;
    }

    public int getAlbumRating() {
        return rating;
    }

    public int getAlbumListens() {
        return numOfListens;
    }

    // MODIFIES: this
    // EFFECTS:
    public void addAListen() {
        numOfListens++;
    }

    // REQUIRES: rating must be an integer in the range [0,5]
    // MODIFIES: this
    // EFFECTS: Sets this rating to rating
    public void setAlbumRating(int rating) {
        this.rating = rating;
    }

    public void setAlbumListens(int listens) {
        this.numOfListens = listens;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", albumName);
        json.put("artist", albumArtist);
        json.put("length", albumLength);
        json.put("rating", rating);
        json.put("listens", numOfListens);
        return json;
    }

    public boolean equals(Album a) {
        return albumName == a.getAlbumName()
                && albumArtist == a.getAlbumArtist()
                && albumLength == a.getAlbumLength();
    }

    @Override
    public String toString() {
        return albumName;
    }

    // MODIFIES: this, ac
    // EFFECTS: Adds ac to collections and this to ac.listOfAlbums
    public void addCollection(AlbumCollection ac) {
        if (!collections.contains(ac)) {
            collections.add(ac);
            ac.addAlbum(this);
        }
    }

    // MODIFIES: this, ac
    // EFFECTS: Removes ac from collections and this from ac.listOfAlbums
    public void removeCollection(AlbumCollection ac) {
        if (collections.contains(ac)) {
            collections.remove(ac);
            ac.removeAlbum(this);
        }
    }

    public List<AlbumCollection> getCollections() {
        return collections;
    }
}