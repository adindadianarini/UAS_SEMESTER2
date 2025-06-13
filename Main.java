// Main.java

public class Main {
    public static void main(String[] args) {

        // ================================
        // Tes: Linked List Mahasiswa
        // ================================
        System.out.println("\n--- Testing StudentLinkedList ---");
        StudentLinkedList list = new StudentLinkedList();

        // Tambahkan data mahasiswa ke list
        list.insertFirst("12347", "Budi Santoso", 3.65);
        list.insertFirst("12346", "Sari Dewi", 3.82);
        list.insertFirst("12345", "Andi Pratama", 3.75);
        list.insertAt(1, "12348", "Rina Anjani", 3.90); // sisipkan di tengah

        // Tampilkan semua data
        list.display();

        // Urutkan berdasarkan IPK dari yang tertinggi
        System.out.println("\nSetelah sortByGPA:");
        list.sortByGPA();
        list.display();

        // Balik urutan mahasiswa
        System.out.println("\nSetelah reverse:");
        list.reverse();
        list.display();

        // Cari mahasiswa dengan IPK tertinggi
        System.out.println("\nMahasiswa dengan IPK tertinggi:");
        Student top = list.findHighestGPA();
        System.out.println(top.name + " (" + top.nim + ") - " + top.gpa);

        // Bonus: tampilkan bentuk list seperti gambar
        list.visualizeList();

        // Ambil mahasiswa dengan IPK > 3.7
        System.out.println("\nMahasiswa dengan IPK di atas 3.7:");
        Student[] goodStudents = list.getStudentsAboveGPA(3.7);
        for (Student s : goodStudents)
            System.out.println(s.name + " - " + s.gpa);

        // Gabungkan dua list mahasiswa yang sudah terurut
        System.out.println("\nSetelah merge dengan list lain:");
        StudentLinkedList other = new StudentLinkedList();
        other.insertLast("12349", "Rudi Hidayat", 3.70);
        other.insertLast("12350", "Linda Sari", 3.95);
        other.sortByGPA();
        list.mergeSortedList(other);
        list.display();

        // ================================
        // Tes: Playlist Circular
        // ================================
        System.out.println("\n--- Testing CircularPlaylist ---");
        CircularPlaylist playlist = new CircularPlaylist();

        // Tambahkan lagu-lagu ke playlist
        playlist.addSong("Bohemian Rhapsody", "Queen", 363);
        playlist.addSong("Hotel California", "Eagles", 391);
        playlist.addSong("Stairway to Heaven", "Led Zeppelin", 482);
        playlist.addSong("Imagine", "John Lennon", 183);

        // Tampilkan isi playlist
        playlist.displayPlaylist();

        // Putar lagu selanjutnya
        System.out.println("\nSetelah playNext():");
        playlist.playNext();
        System.out.println(playlist.getCurrentSong());

        // Acak urutan lagu
        System.out.println("\nSetelah shuffle:");
        playlist.shuffle();
        playlist.displayPlaylist();

        // Hapus lagu dari playlist
        System.out.println("\nHapus 'Imagine':");
        playlist.removeSong("Imagine");
        playlist.displayPlaylist();

        // ================================
        // Tes: Text Editor (Undo/Redo)
        // ================================
        System.out.println("\n--- Testing TextEditor ---");
        TextEditor editor = new TextEditor();

        // Ketik "Hello World"
        editor.insertText("Hello World", 0);

        // Sisipkan " Beautiful" setelah kata "Hello"
        editor.insertText(" Beautiful", 5);
        System.out.println("Current Text: " + editor.getCurrentText());

        // Hapus " Beautiful"
        editor.deleteText(5, 10);
        System.out.println("After delete: " + editor.getCurrentText());

        // Ganti "Hello" jadi "Hi"
        editor.replaceText(0, 5, "Hi");
        System.out.println("After replace: " + editor.getCurrentText());

        // Undo terakhir (kembali ke sebelum replace)
        editor.undo();
        System.out.println("After undo: " + editor.getCurrentText());

        // Undo lagi (kembali ke sebelum delete)
        editor.undo();
        System.out.println("After another undo: " + editor.getCurrentText());

        // Redo (lakukan ulang yang tadi di-undo)
        editor.redo();
        System.out.println("After redo: " + editor.getCurrentText());

        // Lihat semua riwayat perubahan
        System.out.println("\nRiwayat Aksi:");
        editor.getActionHistory();
    }
}