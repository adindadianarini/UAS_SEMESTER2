// CircularPlaylist.java

import java.util.ArrayList;       // Untuk list acak 
import java.util.Collections;     // Untuk menggunakan Collections.shuffle()
import java.util.List;

// Kelas untuk menyimpan info lagu
class Song {
    String title;     // Judul lagu tipe: data string/text 
    String artist;    // Nama artis : data string/text 
    int duration;     // Durasi lagu dalam detik : tipe data integer/bilangan bulat 
    Song next;        // Pointer ke lagu berikutnya

    // Konstruktor
    public Song(String title, String artist, int duration) {
        this.title = title;
        this.artist = artist;
        this.duration = duration;
        this.next = null;
    }
}

// Kelas utama circular linked list untuk playlist
public class CircularPlaylist {
    private Song current; // Lagu yang sedang diputar

    // Tambahkan lagu ke playlist (di akhir)
    public void addSong(String title, String artist, int duration) {
        Song newSong = new Song(title, artist, duration); // buat node lagu
        if (current == null) { // jika playlist kosong
            current = newSong;
            current.next = current; // circular ke dirinya sendiri
        } else {
            Song temp = current;
            while (temp.next != current) // cari node terakhir
                temp = temp.next;
            temp.next = newSong;       // sambungkan node terakhir ke lagu baru
            newSong.next = current;    // lagu baru menunjuk ke current (sirkular)
        }
    }

    // Hapus lagu berdasarkan judul
    public void removeSong(String title) {
        if (current == null) return; // list kosong

        Song prev = current;
        Song temp = current;

        do {
            if (temp.title.equals(title)) {
                // jika hanya 1 lagu
                if (temp == current && temp.next == current) {
                    current = null;
                    return;
                }
                // jika lagu yang dihapus adalah current
                if (temp == current)
                    current = current.next;

                prev.next = temp.next; // buang node dari list
                return;
            }
            prev = temp;
            temp = temp.next;
        } while (temp != current); // loop hingga kembali ke awal
    }

    // Pindah ke lagu berikutnya
    public void playNext() {
        if (current != null)
            current = current.next;
    }

    // Pindah ke lagu sebelumnya
    public void playPrevious() {
        if (current == null) return;
        Song prev = current;
        while (prev.next != current) // cari node sebelum current
            prev = prev.next;
        current = prev;
    }

    // Kembalikan info lagu yang sedang diputar
    public String getCurrentSong() {
        if (current == null) return "Tidak ada lagu yang sedang diputar.";
        return String.format("Currently Playing: %s - %s (%s)",
                current.title, current.artist, formatTime(current.duration));
    }

    // Konversi detik ke format mm:ss
    private String formatTime(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%d:%02d", minutes, seconds);
    }

    // Acak urutan lagu dalam playlist
    public void shuffle() {
        if (current == null || current.next == current) return;

        List<Song> songs = new ArrayList<>();
        Song temp = current;
        do {
            songs.add(temp);       // ambil semua lagu
            temp = temp.next;
        } while (temp != current);

        Collections.shuffle(songs); // acak list

        for (int i = 0; i < songs.size(); i++) {
            songs.get(i).next = songs.get((i + 1) % songs.size()); // hubungkan circular
        }

        current = songs.get(0); // tetapkan lagu pertama setelah diacak
    }

    // Hitung total durasi lagu dalam playlist
    public String getTotalDuration() {
        if (current == null) return "0:00";

        int total = 0;
        Song temp = current;
        do {
            total += temp.duration;
            temp = temp.next;
        } while (temp != current);

        return formatTime(total); // kembalikan format mm:ss
    }

    // Tampilkan semua lagu dalam playlist
    public void displayPlaylist() {
        if (current == null) {
            System.out.println("Playlist kosong.");
            return;
        }

        Song temp = current;
        int i = 1;
        System.out.println("=== Current Playlist ===");
        do {
            String prefix = (temp == current) ? "-> " : "   "; // penanda lagu yang aktif
            System.out.printf("%s%d. %s - %s (%s)\n",
                    prefix, i++, temp.title, temp.artist, formatTime(temp.duration));
            temp = temp.next;
        } while (temp != current);

        System.out.println("Total Duration: " + getTotalDuration()); // total waktu
    }
}
