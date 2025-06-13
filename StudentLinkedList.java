// Kelas Student untuk menyimpan data mahasiswa
class Student {
    String nim;       // NIM mahasiswa
    String name;      // Nama mahasiswa
    double gpa;       // IPK mahasiswa
    Student next;     // Referensi ke node berikutnya (linked list)

    // Konstruktor Student
    public Student(String nim, String name, double gpa) {
        this.nim = nim;
        this.name = name;
        this.gpa = gpa;
        this.next = null;
    }
}

// Kelas utama untuk Single Linked List mahasiswa
public class StudentLinkedList {
    private Student head; // Pointer ke node pertama dalam list

    // Konstruktor list
    public StudentLinkedList() {
        head = null;
    }

    // Tambahkan node di awal
    public void insertFirst(String nim, String name, double gpa) {
        Student newNode = new Student(nim, name, gpa); // buat node baru
        newNode.next = head; // hubungkan ke node lama
        head = newNode; // perbarui head
    }

    // Tambahkan node di akhir
    public void insertLast(String nim, String name, double gpa) {
        Student newNode = new Student(nim, name, gpa); // buat node baru
        if (head == null) { // jika list kosong
            head = newNode;
            return;
        }
        Student current = head; // mulai dari head
        while (current.next != null) // cari node terakhir
            current = current.next;
        current.next = newNode; // hubungkan ke node baru
    }

    // Tambahkan node di posisi tertentu
    public void insertAt(int position, String nim, String name, double gpa) {
        if (position <= 0) { // jika posisi invalid, insert di depan
            insertFirst(nim, name, gpa);
            return;
        }
        Student newNode = new Student(nim, name, gpa);
        Student current = head;
        for (int i = 0; current != null && i < position - 1; i++) // cari posisi
            current = current.next;
        if (current == null) { // jika posisi lebih dari panjang list
            insertLast(nim, name, gpa);
        } else {
            newNode.next = current.next; // sambungkan node baru
            current.next = newNode;
        }
    }

    // Hapus node berdasarkan NIM
    public void deleteByNim(String nim) {
        if (head == null) return; // list kosong
        if (head.nim.equals(nim)) { // jika node pertama yg dihapus
            head = head.next;
            return;
        }
        Student current = head;
        while (current.next != null && !current.next.nim.equals(nim)) // cari node sebelumnya
            current = current.next;
        if (current.next != null) // lewati node yang dihapus
            current.next = current.next.next;
    }

    // Cari node berdasarkan NIM
    public Student search(String nim) {
        Student current = head;
        while (current != null) {
            if (current.nim.equals(nim)) // ditemukan
                return current;
            current = current.next;
        }
        return null; // tidak ditemukan
    }

    // Tampilkan seluruh data mahasiswa
    public void display() {
        System.out.println("=== Data Mahasiswa ===");
        Student current = head;
        while (current != null) {
            System.out.printf("NIM: %s, Nama: %s, IPK: %.2f\n", current.nim, current.name, current.gpa);
            current = current.next;
        }
        System.out.println("Total mahasiswa: " + size());
    }

    // Hitung jumlah node
    public int size() {
        int count = 0;
        Student current = head;
        while (current != null) {
            count++;
            current = current.next;
        }
        return count;
    }

    // Cek apakah list kosong
    public boolean isEmpty() {
        return head == null;
    }

    // BONUS: Visualisasi linked list dalam bentuk ASCII
    public void visualizeList() {
        System.out.println("=== Visualisasi Linked List ===");
        Student current = head;
        while (current != null) {
            System.out.printf("[ %s ] -> ", current.nim);
            current = current.next;
        }
        System.out.println("NULL");

        current = head;
        while (current != null) {
            System.out.printf(" NIM: %s | IPK: %.2f\n", current.nim, current.gpa);
            current = current.next;
        }
    }

    // Urutkan berdasarkan IPK (descending) menggunakan Bubble Sort
    public void sortByGPA() {
        if (head == null || head.next == null) return;

        boolean swapped;
        do {
            swapped = false;
            Student current = head;
            while (current.next != null) {
                if (current.gpa < current.next.gpa) {
                    // Tukar semua data antar node
                    double tempGpa = current.gpa;
                    String tempName = current.name;
                    String tempNim = current.nim;

                    current.gpa = current.next.gpa;
                    current.name = current.next.name;
                    current.nim = current.next.nim;

                    current.next.gpa = tempGpa;
                    current.next.name = tempName;
                    current.next.nim = tempNim;

                    swapped = true;
                }
                current = current.next;
            }
        } while (swapped); // ulangi jika ada pertukaran
    }

    // Balik urutan list
    public void reverse() {
        Student prev = null;
        Student current = head;
        Student next = null;
        while (current != null) {
            next = current.next;   // simpan next
            current.next = prev;   // balik arah
            prev = current;        // geser prev
            current = next;        // geser current
        }
        head = prev; // perbarui head
    }

    // Cari mahasiswa dengan IPK tertinggi
    public Student findHighestGPA() {
        if (head == null) return null;
        Student max = head;
        Student current = head;
        while (current != null) {
            if (current.gpa > max.gpa)
                max = current;
            current = current.next;
        }
        return max;
    }

    // Ambil semua mahasiswa dengan IPK di atas ambang
    public Student[] getStudentsAboveGPA(double threshold) {
        int count = 0;
        Student current = head;
        while (current != null) {
            if (current.gpa > threshold)
                count++;
            current = current.next;
        }

        Student[] result = new Student[count];
        current = head;
        int i = 0;
        while (current != null) {
            if (current.gpa > threshold)
                result[i++] = current;
            current = current.next;
        }
        return result;
    }

    // Gabungkan dua list yang telah terurut berdasarkan IPK
    public void mergeSortedList(StudentLinkedList otherList) {
        Student dummy = new Student("", "", 0); // node dummy awal
        Student tail = dummy;
        Student a = this.head;
        Student b = otherList.head;

        while (a != null && b != null) {
            if (a.gpa >= b.gpa) {
                tail.next = a;
                a = a.next;
            } else {
                tail.next = b;
                b = b.next;
            }
            tail = tail.next;
        }

        tail.next = (a != null) ? a : b; // gabungkan sisa list
        head = dummy.next; // perbarui head
    }
}