// TextEditor.java

// Kelas Action untuk menyimpan satu operasi teks
class Action {
    String type;          // Jenis aksi: INSERT, DELETE, REPLACE
    String text;          // Teks yang ditambahkan atau diganti
    int position;         // Posisi di dalam teks
    String previousText;  // Teks lama sebelum diubah (untuk undo)
    Action next;          // Aksi berikutnya
    Action prev;          // Aksi sebelumnya

    public Action(String type, String text, int position, String previousText) {
        this.type = type;
        this.text = text;
        this.position = position;
        this.previousText = previousText;
    }
}

// Editor teks dengan fitur undo dan redo
public class TextEditor {
    private StringBuilder content; // Teks saat ini
    private Action head;           // Aksi pertama
    private Action current;        // Aksi saat ini (posisi undo/redo)

    // Konstruktor
    public TextEditor() {
        content = new StringBuilder();
        head = null;
        current = null;
    }

    // Tambahkan teks di posisi tertentu
    public void insertText(String text, int position) {
        position = Math.min(position, content.length());
        content.insert(position, text);

        Action action = new Action("INSERT", text, position, null);
        addAction(action);
    }

    // Hapus teks dari posisi dengan panjang tertentu
    public void deleteText(int start, int length) {
        if (start < 0 || start + length > content.length()) return;

        String deleted = content.substring(start, start + length);
        content.delete(start, start + length);

        Action action = new Action("DELETE", "", start, deleted);
        addAction(action);
    }

    // Ganti teks tertentu dengan teks baru
    public void replaceText(int start, int length, String newText) {
        if (start < 0 || start + length > content.length()) return;

        String oldText = content.substring(start, start + length);
        content.replace(start, start + length, newText);

        Action action = new Action("REPLACE", newText, start, oldText);
        addAction(action);
    }

    // Tambahkan aksi ke riwayat (dan hapus redo setelahnya)
    private void addAction(Action action) {
        if (current != null && current.next != null) {
            current.next.prev = null;
            current.next = null; // buang redo setelah current
        }

        if (head == null) {
            head = action;
        } else {
            current.next = action;
            action.prev = current;
        }
        current = action;
    }

    // Undo aksi terakhir
    public void undo() {
        if (current == null) return;

        switch (current.type) {
            case "INSERT":
                content.delete(current.position, current.position + current.text.length());
                break;
            case "DELETE":
                content.insert(current.position, current.previousText);
                break;
            case "REPLACE":
                content.replace(current.position,
                                current.position + current.text.length(),
                                current.previousText);
                break;
        }
        current = current.prev;
    }

    // Redo aksi yang dibatalkan
    public void redo() {
        if (current == null || current.next == null) return;

        current = current.next;
        switch (current.type) {
            case "INSERT":
                content.insert(current.position, current.text);
                break;
            case "DELETE":
                content.delete(current.position, current.position + current.previousText.length());
                break;
            case "REPLACE":
                content.replace(current.position,
                                current.position + current.previousText.length(),
                                current.text);
                break;
        }
    }

    // Ambil teks saat ini
    public String getCurrentText() {
        return content.toString();
    }

    // Cetak riwayat aksi (debug/logging)
    public void getActionHistory() {
        System.out.println("=== Riwayat Aksi ===");
        Action temp = head;
        while (temp != null) {
            System.out.printf("%s: '%s' di posisi %d\n", temp.type, temp.text, temp.position);
            temp = temp.next;
        }
    }
}