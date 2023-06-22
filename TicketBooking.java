import java.util.Scanner;

// Struktur data untuk menyimpan informasi tiket
class Ticket {
    int NoTiket;
    String NamaKapal;
    String Tujuan;
    String date;
    double Harga;
    String NamaPemesan;
    int IdPemesan;
    String MetodePembayaran;

    public Ticket(int NoTiket, String NamaKapal, String Tujuan, String date, double Harga) {
        this.NoTiket = NoTiket;
        this.NamaKapal = NamaKapal;
        this.Tujuan = Tujuan;
        this.date = date;
        this.Harga = Harga;
    }

    public String toString() {
        return "No. Tiket: " + NoTiket +
                "\nNama Kapal: " + NamaKapal +
                "\nTujuan: " + Tujuan +
                "\nTanggal: " + date +
                "\nHarga: Rp." + Harga;
    }
}

// Struktur data linked list untuk menyimpan tiket yang dipilih
class TicketNode {
    Ticket ticket;
    TicketNode next;

    public TicketNode(Ticket ticket) {
        this.ticket = ticket;
        this.next = null;
    }
}

class TicketLinkedList {
    TicketNode head;

    public TicketLinkedList() {
        this.head = null;
    }

    public void addTicket(Ticket ticket) {
        TicketNode newNode = new TicketNode(ticket);
        if (head == null) {
            head = newNode;
        } else {
            TicketNode current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        System.out.println("Ticket berhasil ditambahkan");
    }
}

public class TicketBooking {
    Ticket[] tickets;
    int ticketCount;
    static Scanner scanner;
    TicketLinkedList selectedTickets;

    public TicketBooking() {
        tickets = new Ticket[100];
        ticketCount = 0;
        scanner = new Scanner(System.in);
        selectedTickets = new TicketLinkedList();

        Ticket ticket1 = new Ticket(1, "KMP Trisila Bhakti", "Bali", "2021-06-16", 100.000);
        Ticket ticket2 = new Ticket(2, "Jatra II", "Lombok", "2023-06-17", 350.000);
        Ticket ticket3 = new Ticket(3, "Srikandi Marina", "Bali", "2022-06-14", 200.000);
        Ticket ticket4 = new Ticket(4, "Dharma Ferry", "Lombok", "2020-06-15", 300.000);

        addTicket(ticket1);
        addTicket(ticket2);
        addTicket(ticket3);
        addTicket(ticket4);
    }

    public void addTicket(Ticket ticket) {
        tickets[ticketCount] = ticket;
        ticketCount++;
    }

    public void DaftarTicket() {
        if (ticketCount == 0) {
            System.out.println("Tiket Tidak Tersedia");
        } else {
            for (int i = 0; i < ticketCount; i++) {
                System.out.println(tickets[i]);
                System.out.println("-------------------------");
            }
        }
    }

    public void sortTicketByDate() {
        if (ticketCount == 0) {
            System.out.println("Tiket Tidak Tersedia");
        } else {
            mergeSortTicketsByDate(0, ticketCount - 1);
            System.out.println("Urutan Tiket Berdasarkan Tanggal");
            DaftarTicket();
        }
    }

    private void mergeSortTicketsByDate(int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSortTicketsByDate(left, mid);
            mergeSortTicketsByDate(mid + 1, right);
            mergeTicketsByDate(left, mid, right);
        }
    }

    private void mergeTicketsByDate(int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        Ticket[] leftArray = new Ticket[n1];
        Ticket[] rightArray = new Ticket[n2];

        for (int i = 0; i < n1; i++) {
            leftArray[i] = tickets[left + i];
        }
        for (int j = 0; j < n2; j++) {
            rightArray[j] = tickets[mid + 1 + j];
        }

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (leftArray[i].date.compareTo(rightArray[j].date) <= 0) {
                tickets[k] = leftArray[i];
                i++;
            } else {
                tickets[k] = rightArray[j];
                j++;
            }
            k++;
        }
        while (i < n1) {
            tickets[k] = leftArray[i];
            i++;
            k++;
        }

        while (j < n2) {
            tickets[k] = rightArray[j];
            j++;
            k++;
        }
    }

    public void searchTicketByTujuan() {
        if (ticketCount == 0) {
            System.out.println("Tiket Tidak Tersedia");
        } else {
            System.out.print("Masukkan Tujuan Yang ingin Dicari: ");
            String cariTujuan = scanner.nextLine();

            boolean found = false;
            for (int i = 0; i < ticketCount; i++) {
                if (tickets[i].Tujuan.equalsIgnoreCase(cariTujuan)) {
                    System.out.println(tickets[i]);
                    System.out.println("-------------------------");
                    found = true;
                }
            }

            if (!found) {
                System.out.println("No.Tiket Ditemukan Untuk Tujuan : " + cariTujuan);
            }
        }
    }

    public int linearSearchTicket(int NoTiket) {
        for (int i = 0; i < ticketCount; i++) {
            if (tickets[i].NoTiket == NoTiket) {
                return i;
            }
        }
        return -1; // Ticket not found
    }

    public void PesanTicket() {
        if (ticketCount == 0) {
            System.out.println("No tickets available.");
        } else {
            System.out.print("Masukkan No.Tiket Yang dipesan: ");
            int selectNoTicket = scanner.nextInt();
            scanner.nextLine();
            int index = linearSearchTicket(selectNoTicket);
            if (index != -1) {
                Ticket selectedTicket = tickets[index];
                System.out.print("Masukkan Nama Pemesan: ");
                String namaPemesan = scanner.nextLine();
                System.out.print("Masukkan Id Pemesan: ");
                int IdPemesan = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Masukkan Metode Pembayaran: ");
                String metodePembayaran = scanner.nextLine();
                selectedTicket.NamaPemesan = namaPemesan;
                selectedTicket.IdPemesan = IdPemesan;
                selectedTicket.MetodePembayaran = metodePembayaran;
                selectedTickets.addTicket(selectedTicket);
            } else {
                System.out.println("Tiket tidak ditemukan");
            }
        }
    }

    public void cancelTicket() {
        if (selectedTickets.head == null) {
            System.out.println("Tidak ada tiket dipesan");
        } else {
            System.out.print("Masukkan No. Tiket Yang dibatalkan : ");
            int cancelNoTicket = scanner.nextInt();
            scanner.nextLine();

            TicketNode current = selectedTickets.head;
            TicketNode prev = null;
            while (current != null) {
                if (current.ticket.NoTiket == cancelNoTicket) {
                    if (prev == null) {
                        selectedTickets.head = current.next;
                    } else {
                        prev.next = current.next;
                    }
                    System.out.println("Tiket berhasil dibatalkan");
                    return;
                }
                prev = current;
                current = current.next;
            }

            System.out.println("Tiket tidak ditemukan");
        }
    }

    public void displaySelectedTicket() {
        if (selectedTickets.head == null) {
            System.out.println("Tidak ada tiket dipesan");
        } else {
            TicketNode current = selectedTickets.head;
            while (current != null) {
                System.out.println(current.ticket);
                System.out.println("Nama Pemesan: " + current.ticket.NamaPemesan);
                System.out.println("Metode Pembayaran: " + current.ticket.MetodePembayaran);
                System.out.println("----------------------------");
                current = current.next;
            }
        }
    }

    public static void main(String[] args) {
        TicketBooking booking = new TicketBooking();

        boolean exit = false;
        while (!exit) {
            System.out.println("=== Ticket Booking System ===");
            System.out.println("1. Daftar ticket");
            System.out.println("2. Urutan ticket berdasarkan tanggal");
            System.out.println("3. Cari tiket berdasarkan tujuan");
            System.out.println("4. Pesan tiket");
            System.out.println("5. Pembatalan tiket");
            System.out.println("6. Tampilkan tiket yang dipesan");
            System.out.println("0. Exit");
            System.out.println("==============================");
            System.out.print("Masukkan pilihan: ");
            int pilihan = TicketBooking.scanner.nextInt();
            TicketBooking.scanner.nextLine();

            switch (pilihan) {
                case 0:
                    exit = true;
                    break;
                case 1:
                    booking.DaftarTicket();
                    break;
                case 2:
                    booking.sortTicketByDate();
                    break;
                case 3:
                    booking.searchTicketByTujuan();
                    break;
                case 4:
                    booking.PesanTicket();
                    break;
                case 5:
                    booking.cancelTicket();
                    break;
                case 6:
                    booking.displaySelectedTicket();
                    break;
                default:
                    System.out.println("Pilihan tidak valid");
                    break;
            }
            System.out.println();
        }
        System.out.println("Selamat Menikmati Perjalanan!");
    }
}