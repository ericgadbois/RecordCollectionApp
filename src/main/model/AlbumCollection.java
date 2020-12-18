package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AlbumCollection implements Writable {
    private List<Album> listOfAlbums;

    // Constructs an AlbumCollection
    // EFFECTS: Creates an empty list of albums
    public AlbumCollection() {
        listOfAlbums = new ArrayList<>();
    }

    // Adds an album to the collection if it is not already there
    // MODIFIES: this, album
    // EFFECTS: Add album to collection if not already there, adds collection to album
    public void addAlbum(Album album) {
        if (!albumInCollection(album)) {
            listOfAlbums.add(album);
            album.addCollection(this);
        }
    }

    // MODIFIES: this, album
    // EFFECTS: Remove album from collection if it is there, removes collection from album
    public void removeAlbum(Album album) {
        if (albumInCollection(album)) {
            listOfAlbums.remove(album);
            album.removeCollection(this);
        }
    }

    // Checks if an album is already in the collection
    // EFFECTS: Returns whether or not the given album is in the collection
    public boolean albumInCollection(Album album) {
        boolean isInCollection = false;
        for (Album a: listOfAlbums) {
            if (isSameAlbum(album, a)) {
                isInCollection = true;
            }
        }
        return isInCollection;
    }

    // Checks if 2 albums are identical
    // EFFECTS: Returns a boolean indicating if they are the same album or not
    public boolean isSameAlbum(Album a, Album b) {
        return a.getAlbumName() == b.getAlbumName()
                && a.getAlbumArtist() == b.getAlbumArtist()
                && a.getAlbumLength() == b.getAlbumLength();
    }

    // Return a list of user's albums with a rating >= the inputted value
    // REQUIRES: rating must be within the range [0,5]
    // EFFECTS: Creates a new list consisting of all albums in the collection that equal or exceed rating
    public List<Album> getAlbumsWithRating(int rating) {
        List<Album> listOfMatching = new ArrayList<>();
        for (Album a: listOfAlbums) {
            if (a.getAlbumRating() >= rating) {
                listOfMatching.add(a);
            }
        }
        return listOfMatching;
    }


    // Return a list of user's albums with listens >= the inputted value
    // EFFECTS: Creates a new list consisting of all albums in the collection that has equal or greater listens
    public List<Album> getAlbumsWithListens(int listens) {
        List<Album> listOfMatching = new ArrayList<>();
        for (Album a: listOfAlbums) {
            if (a.getAlbumListens() >= listens) {
                listOfMatching.add(a);
            }
        }
        return listOfMatching;
    }


    // EFFECTS: Returns the album in the collection with the most listens
    public Album mostListenedAlbum() {
        int highest = 0;
        Album mostListened = null;
        for (Album a: listOfAlbums) {
            int listens = a.getAlbumListens();
            if (listens > highest) {
                highest = listens;
                mostListened = a;
            }
        }
        return mostListened;
    }

    // EFFECTS: Return size of collection
    public int getCollectionSize() {
        return listOfAlbums.size();
    }

    // EFFECTS: Searches the collection for the album, returns the matching album if it is in collection.
    public Album searchForAlbum(String title) {
        Album matching = null;
        for (Album a: listOfAlbums) {
            if (title.equals(a.getAlbumName())) {
                matching = a;
            }
        }
        return matching;
    }

    // MODIFIES: matchingAlbum
    // EFFECTS: Adds 1 to the listen counter of matchingAlbum
    public void listenToAlbum(String name) {
        Album matchingAlbum = searchForAlbum(name);
        matchingAlbum.addAListen();
    }

    // MODIFIES: matchingAlbum
    // EFFECTS: Changes the rating of matchingAlbum to rating
    public void rateAnAlbum(String name, int rating) {
        Album matchingAlbum = searchForAlbum(name);
        matchingAlbum.setAlbumRating(rating);
    }

    // Code gotten from JsonSerializationDemo
    // EFFECTS: returns an unmodifiable list of albums in this collection
    public List<Album> getListOfAlbums() {
        return Collections.unmodifiableList(listOfAlbums);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("albums", albumsToJson());
        return json;
    }

    // EFFECTS: returns albums in this collection as a JSON array
    private JSONArray albumsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Album a :listOfAlbums) {
            jsonArray.put(a.toJson());
        }

        return jsonArray;
    }


}
