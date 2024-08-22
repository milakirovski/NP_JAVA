package labs.lab8.zad1;


import java.util.ArrayList;
import java.util.List;

class Song {
    //title=first-title, artist=first-artist
    String title;
    String artist;

    public Song(String title, String artist) {
        this.title = title;
        this.artist = artist;
    }

    @Override
    public String toString() {
        return "Song{" +
                "title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                '}';
    }
}

interface IMP3 {
    void pressPlay();

    void pressStop();

    void pressFWD();

    void pressREW();
}


class MP3Player implements IMP3 {
    List<Song> songs;
    Song currSong;
    int hasBeenStopped = 0;
    int isPlaying = 0;

    public MP3Player(List<Song> songs) {
        this.songs = songs;
        currSong = songs.getFirst(); // by default
    }

    @Override
    public void pressPlay() {
        if (isPlaying == 1) {
            System.out.println("Song is already playing");
        } else {
            System.out.printf("Song %d is playing%n", songs.indexOf(currSong));
            isPlaying = 1;
            hasBeenStopped = 0;
        }
    }

    @Override
    public void pressStop() {
        if (hasBeenStopped == 1) {
            System.out.println("Songs are stopped");
            currSong = songs.getFirst();
            hasBeenStopped++;
        } else if (hasBeenStopped > 1) {
            System.out.println("Songs are already stopped");
        } else {
            System.out.printf("Song %d is paused%n", songs.indexOf(currSong));
            hasBeenStopped=1;
            isPlaying = 0;
        }

    }

    @Override
    public void pressFWD() {
        System.out.println("Forward...");
        hasBeenStopped = 1;
        isPlaying = 0;
        if (songs.indexOf(currSong) == songs.size() - 1) {
            currSong = songs.getFirst();
        } else {
            currSong = songs.get(songs.indexOf(currSong) + 1);
        }
    }

    @Override
    public void pressREW() {
        System.out.println("Reward...");
        hasBeenStopped = 1;
        isPlaying = 0;
        if (songs.indexOf(currSong) == 0) {
            currSong = songs.getLast();
        } else {
            currSong = songs.get(songs.indexOf(currSong) - 1);
        }
    }

    public void printCurrentSong() {
        System.out.println(currSong);
    }

    @Override
    public String toString() {
        return "MP3Player{" +
                "currentSong = " + songs.indexOf(currSong) +
                ", songList = " + songs.toString() + "}";

    }
}

public class PatternTest {
    public static void main(String args[]) {
        List<Song> listSongs = new ArrayList<Song>();
        listSongs.add(new Song("first-title", "first-artist"));
        listSongs.add(new Song("second-title", "second-artist"));
        listSongs.add(new Song("third-title", "third-artist"));
        listSongs.add(new Song("fourth-title", "fourth-artist"));
        listSongs.add(new Song("fifth-title", "fifth-artist"));
        MP3Player player = new MP3Player(listSongs);


        System.out.println(player.toString());
        System.out.println("First test");


        player.pressPlay();// A
        player.printCurrentSong();
        player.pressPlay();//A
        player.printCurrentSong();

        player.pressPlay();//A
        player.printCurrentSong();
        player.pressStop();// A
        player.printCurrentSong();

        player.pressPlay();// A
        player.printCurrentSong();
        player.pressFWD();
        player.printCurrentSong();

        player.pressPlay();
        player.printCurrentSong();
        player.pressREW();
        player.printCurrentSong();


        System.out.println(player.toString());
        System.out.println("Second test");


        player.pressStop();
        player.printCurrentSong();
        player.pressStop();
        player.printCurrentSong();

        player.pressStop();
        player.printCurrentSong();
        player.pressPlay();
        player.printCurrentSong();

        player.pressStop();
        player.printCurrentSong();
        player.pressFWD();
        player.printCurrentSong();

        player.pressStop();
        player.printCurrentSong();
        player.pressREW();
        player.printCurrentSong();


        System.out.println(player.toString());
        System.out.println("Third test");


        player.pressFWD();
        player.printCurrentSong();
        player.pressFWD();
        player.printCurrentSong();

        player.pressFWD();
        player.printCurrentSong();
        player.pressPlay();
        player.printCurrentSong();

        player.pressFWD();
        player.printCurrentSong();
        player.pressStop();
        player.printCurrentSong();

        player.pressFWD();
        player.printCurrentSong();
        player.pressREW();
        player.printCurrentSong();


        System.out.println(player.toString());
    }
}

//Vasiot kod ovde
