package ui;

import model.Album;
import model.AlbumCollection;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
import java.util.Scanner;

// Code used from TellerApp

// Record collection application
public class CollectionApp extends JPanel implements ListSelectionListener {
    private static final String JSON_STORE = "./data/MyCollection2.json";
    private AlbumCollection myCollection;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private JList list;
    private DefaultListModel<Album> listModel;
//    private Album testAlbum;

    //    private static JLabel label;
    private static JButton searchButton;
    private JButton removeButton;
    private static JTextField searchField;
    private JTextField albumName;
    private JTextField albumArtist;
    private JTextField albumLength;
    private JButton saveButton;
    private JButton loadButton;
    private JButton addButton;


    // EFFECTS: runs the application
    public CollectionApp() throws FileNotFoundException {
        super(new BorderLayout());
        runCollection();
    }

    // MODIFIES: this
    // EFFECTS: builds GUI and processes user input
    private void runCollection() {
        init();

        listModel = new DefaultListModel<>();

        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);

        addButton = new JButton("Add Album");
        AddListener addListener = new AddListener(addButton);
        addButton.setActionCommand("Add Album");
        addButton.addActionListener(addListener);
        addButton.setEnabled(true);

        JPanel topPanel = createAlbumSearchPanel();

        JPanel albumName = createAlbumNamePanel(addListener);
        JPanel albumArtist = createAlbumArtistPanel(addListener);
        JPanel albumLength = createAlbumLengthPanel(addListener);
        JPanel addAlbum = createAlbumInputPanel(albumName, albumArtist, albumLength);

        JPanel saveLoad = createSaveLoadPanel();

        JPanel bottomPanel = createBottomPanel(addAlbum, saveLoad);

        add(topPanel, BorderLayout.PAGE_START);
        add(listScrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.PAGE_END);

    }

    // Creates bottom panel of GUI
    private JPanel createBottomPanel(JPanel addAlbum, JPanel saveLoad) {

        removeButton = new JButton("Remove Album");
        removeButton.setActionCommand("Remove Album");
        removeButton.addActionListener(new RemoveListener());

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.LINE_AXIS));
        bottomPanel.add(removeButton);
        bottomPanel.add(Box.createHorizontalStrut(5));
        bottomPanel.add(new JSeparator(SwingConstants.VERTICAL));
        bottomPanel.add(Box.createHorizontalStrut(5));
        bottomPanel.add(addAlbum);
        bottomPanel.add(Box.createHorizontalStrut(5));
        bottomPanel.add(addButton);
        bottomPanel.add(Box.createHorizontalStrut(5));
        bottomPanel.add(new JSeparator(SwingConstants.VERTICAL));
        bottomPanel.add(saveLoad);

        return bottomPanel;
    }

    // Creates album name entry panel
    private JPanel createAlbumNamePanel(AddListener addListener) {
        albumName = new JTextField(10);
        albumName.addActionListener(addListener);
        albumName.getDocument().addDocumentListener(addListener);

        JPanel albumNameEntry = new JPanel();
        albumNameEntry.setLayout(new BoxLayout(albumNameEntry, BoxLayout.LINE_AXIS));
        JLabel albumNameLabel = new JLabel("Album Name:");
        albumNameEntry.add(albumNameLabel);
        albumNameEntry.add(Box.createHorizontalStrut(5));
        albumNameEntry.add(albumName);

        return albumNameEntry;
    }

    // Creates album artist entry panel
    private JPanel createAlbumArtistPanel(AddListener addListener) {
        albumArtist = new JTextField(10);
        albumArtist.addActionListener(addListener);
        albumArtist.getDocument().addDocumentListener(addListener);

        JPanel albumArtistEntry = new JPanel();
        albumArtistEntry.setLayout(new BoxLayout(albumArtistEntry, BoxLayout.LINE_AXIS));
        JLabel albumArtistLabel = new JLabel("Album Artist:");
        albumArtistEntry.add(albumArtistLabel);
        albumArtistEntry.add(Box.createHorizontalStrut(5));
        albumArtistEntry.add(albumArtist);

        return albumArtistEntry;
    }

    // Creates album length entry panel
    private JPanel createAlbumLengthPanel(AddListener addListener) {
        albumLength = new JTextField(10);
        albumLength.addActionListener(addListener);
        albumLength.getDocument().addDocumentListener(addListener);

        JPanel albumLengthEntry = new JPanel();
        albumLengthEntry.setLayout(new BoxLayout(albumLengthEntry, BoxLayout.LINE_AXIS));
        JLabel albumLengthLabel = new JLabel("Album Length (minutes):");
        albumLengthEntry.add(albumLengthLabel);
        albumLengthEntry.add(Box.createHorizontalStrut(5));
        albumLengthEntry.add(albumLength);

        return albumLengthEntry;
    }

    // Combines album name, artist, and length panels and creates completed album entry panel
    private JPanel createAlbumInputPanel(JPanel name, JPanel artist, JPanel length) {
        JPanel albumEntryInputPanel = new JPanel();
        albumEntryInputPanel.setLayout(new BoxLayout(albumEntryInputPanel, BoxLayout.PAGE_AXIS));
        albumEntryInputPanel.add(name);
        albumEntryInputPanel.add((Box.createVerticalStrut(5)));
        albumEntryInputPanel.add(artist);
        albumEntryInputPanel.add((Box.createVerticalStrut(5)));
        albumEntryInputPanel.add(length);

        return albumEntryInputPanel;
    }

    // Creates search panel
    private JPanel createAlbumSearchPanel() {
        searchButton = new JButton("Search");
        SearchListener searchListener = new SearchListener(searchButton);
        searchButton.setActionCommand("Search");
        searchButton.addActionListener(searchListener);

        searchField = new JTextField(10);
        searchField.addActionListener(searchListener);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.LINE_AXIS));
        topPanel.add(searchButton);
        topPanel.add(Box.createHorizontalStrut(5));
        topPanel.add(searchField);

        return topPanel;
    }

    // Creates save/load panel
    private JPanel createSaveLoadPanel() {
        saveButton = new JButton("Save");
        saveButton.setActionCommand("Save");
        saveButton.addActionListener(new SaveListener());

        loadButton = new JButton("Load");
        loadButton.setActionCommand("Load");
        loadButton.addActionListener(new LoadListener());

        JPanel saveLoadPanel = new JPanel();
        saveLoadPanel.setLayout(new BoxLayout(saveLoadPanel, BoxLayout.PAGE_AXIS));
        saveLoadPanel.add(saveButton);
        saveLoadPanel.add(new JSeparator(SwingConstants.CENTER));
        saveLoadPanel.add(loadButton);

        return saveLoadPanel;
    }

    // MODIFIES: this
    // EFFECTS: initializes collection
    private void init() {
        myCollection = new AlbumCollection();
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }


    // CODE FOR TERMINAL INTERFACE VERSION
//    // MODIFIES: this
//    // EFFECTS: processes user command
//    private void processCommand(String command) {
//        if (command.equals("a")) {
//            doAddAlbum();
//        } else if (command.equals("g")) {
//            doGetAlbumInfo();
//        } else if (command.equals("abr")) {
//            doAlbumsByRating();
//        } else if (command.equals("m")) {
//            doMostListenedAlbum();
//        } else if (command.equals("p")) {
//            doPlayAlbum();
//        } else if (command.equals("r")) {
//            doRateAlbum();
//        } else if (command.equals("s")) {
//            saveAlbumCollection();
//        } else if (command.equals("l")) {
//            loadAlbumCollection();
//        } else {
//            System.out.println("Selection not valid...");
//        }
//    }
//
//    // EFFECTS: displays menu of options to user
//    private void displayMenu() {
//        System.out.println("\nSelect from:");
//        System.out.println("\ts -> save your album collection");
//        System.out.println("\tl -> load your album collection");
//        System.out.println("\ta -> add album");
//        System.out.println("\tp -> play an album");
//        System.out.println("\tr -> rate an album");
//        System.out.println("\tg -> get album info");
//        System.out.println("\tabr -> view albums dependant on rating");
//        System.out.println("\tm -> view your most listened to album");
//        System.out.println("\tq -> quit");
//    }
//
//    // MODIFIES: this
//    // EFFECTS: Adds an album to the collection
//    private void doAddAlbum() {
//        input.nextLine();
//        System.out.println("Enter the name of the album:");
//        String name = input.nextLine();
//        //input.nextLine();
//        System.out.println("Enter the name of the album artist:");
//        String artist = input.nextLine();
//        //input.nextLine();
//        System.out.println("Enter the length of the album in minutes:");
//        int length = input.nextInt();
//
//        Album newAlbum = new Album(name, artist, length);
//        myCollection.addAlbum(newAlbum);
//
//        System.out.println(name + " has been added to your collection!");
//    }
//
//    // EFFECTS: Returns the name, artist, length, rating, and listens of the given album
//    private void doGetAlbumInfo() {
//        input.nextLine();
//        System.out.println("Enter the name of the album you would like to view:");
//        String name = input.nextLine();
//        Album album = myCollection.searchForAlbum(name);
//
//        if (album != null) {
//            doPrintAlbum(album);
//        } else {
//            System.out.println("This album is not in your collection. Please check your spelling and try again.");
//        }
//    }
//
//    private void doAlbumsByRating() {
//        System.out.print("Enter the minimum rating:");
//        int rating = input.nextInt();
//        input.nextLine();
//        if (rating >= 0 && rating <= 5) {
//            List<Album> albumList = myCollection.getAlbumsWithRating(rating);
//            for (Album a: albumList) {
//                System.out.println(" ");
//                doPrintAlbum(a);
//            }
//        } else {
//            System.out.println("Please use an integer between 0-5");
//        }
//    }
//
//    // EFFECTS: Returns the information for the album in the collection with the highest listens
//    private void doMostListenedAlbum() {
//        Album album = myCollection.mostListenedAlbum();
//        if (album != null) {
//            System.out.println("Your most listened to album is:");
//            doPrintAlbum(album);
//        } else {
//            System.out.println("You haven't listened to any records!");
//        }
//    }
//
//    // MODIFIES: this
//    // EFFECTS: Plays the inputted album and adds 1 to the album's listens
//    private void doPlayAlbum() {
//        input.nextLine();
//        System.out.println("Enter the name of the album you wish to listen to:");
//        String name = input.nextLine();
//        Album album = myCollection.searchForAlbum(name);
//        if (album != null) {
//            System.out.println("Now Playing: " + album.getAlbumName() + " by " + album.getAlbumArtist());
//            album.addAListen();
//        } else {
//            System.out.println("This album is not in your collection.");
//        }
//    }
//
//    // MODIFIES: this
//    // EFFECTS: Sets the album's rating to the amount entered
//    private void doRateAlbum() {
//        input.nextLine();
//        System.out.println("Enter the name of the album you wish to rate:");
//        String name = input.nextLine();
//        Album album = myCollection.searchForAlbum(name);
//        if (album != null) {
//            System.out.println("Enter your rating from 0-5:");
//            int rating = input.nextInt();
//            album.setAlbumRating(rating);
//            System.out.println(album.getAlbumName() + " has been rated a " + album.getAlbumRating());
//        } else {
//            System.out.println("This album is not in your collection.");
//        }
//    }
//
//    // EFFECTS: Prints the name, artist, length, rating, and listens of an album
//    private void doPrintAlbum(Album album) {
//        System.out.println("Album Name: " + album.getAlbumName());
//        System.out.println("Album Artist: " + album.getAlbumArtist());
//        System.out.println("Album Length: " + album.getAlbumLength() + " minutes");
//        System.out.println("Your Rating: " + album.getAlbumRating());
//        System.out.println("Your Listens: " + album.getAlbumListens());
//    }

    // Code for the following 2 methods gotten from JsonSerializationDemo

    // EFFECTS: saves the album collection to file
    private void saveAlbumCollection() {

        try {
            jsonWriter.open();
            jsonWriter.write(myCollection);
            jsonWriter.close();
            System.out.println("Saved MyCollection to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads album collection from file
    private void loadAlbumCollection() {
        try {
            myCollection = jsonReader.read();
            System.out.println("Loaded MyCollection from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
    }

    // MODIFIES: this
    // Listener for Add album feature
    class AddListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public AddListener(JButton button) {
            this.button = button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String name = albumName.getText();
            String artist = albumArtist.getText();
            int length = Integer.parseInt(albumLength.getText());
            Album album = new Album(name, artist, length);

            if (alreadyInList(album)) {
                Toolkit.getDefaultToolkit().beep();
                albumName.requestFocusInWindow();
                albumName.selectAll();
                return;
            }

            int index = list.getSelectedIndex();
            if (index == -1) {
                index = 0;
            } else {
                index++;
            }

            listModel.insertElementAt(new Album(name, artist, length), index);

            //reset text fields
            albumName.requestFocusInWindow();
            albumName.setText("");
            albumArtist.setText("");
            albumLength.setText("");

            //select new album and make it visible
            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }

        private boolean alreadyInList(Album album) {
            boolean isThere = false;
            for (int i = 0; i < listModel.size(); i++) {
                Album a = listModel.getElementAt(i);
                isThere = album.equals(a);
            }
            return isThere;
        }
    }

    // MODIFIES: this
    // Listener for the remove album feature
    class RemoveListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            listModel.remove(index);


            int size = listModel.getSize();

            if (size == 0) {
                removeButton.setEnabled(false);
            } else {
                if (index == listModel.getSize()) {
                    index--;
                }
                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }
        }
    }

    // Listener for the search feature
    class SearchListener implements ActionListener {
        private JButton button;

        public SearchListener(JButton button) {
            this.button = button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String name = searchField.getText();
            int index = 0;

            if (!searchForAlbum(name)) {
                Toolkit.getDefaultToolkit().beep();
                searchField.requestFocusInWindow();
                searchField.selectAll();
                return;
            }
            index = getAlbumIndex(name);
            list.setSelectedIndex(index);
            searchField.requestFocusInWindow();
            searchField.setText("");
        }

        private boolean searchForAlbum(String name) {
            boolean isThere = false;
            for (int i = 0; i < listModel.size(); i++) {
                Album a = listModel.getElementAt(i);
                if (a.getAlbumName().equals(name)) {
                    isThere = true;
                }
            }
            return isThere;
        }

        private int getAlbumIndex(String name) {
            int index = 0;
            for (int i = 0; i < listModel.size(); i++) {
                Album a = listModel.getElementAt(i);
                if (a.getAlbumName().equals(name)) {
                    index = i;
                }
            }
            return index;
        }
    }

    // MODIFIES: MyCollection.json
    // Listener for the save collection feature
    class SaveListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            myCollection = new AlbumCollection();
            for (int i = 0; i < listModel.size(); i++) {
                Album a = listModel.getElementAt(i);
                myCollection.addAlbum(a);
            }
            saveAlbumCollection();
        }
    }

    // MODIFIES: this
    // Listener for the load collection feature
    class LoadListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            loadAlbumCollection();
            listModel.clear();
            for (Album a: myCollection.getListOfAlbums()) {
                listModel.addElement(a);
            }
        }
    }
}
