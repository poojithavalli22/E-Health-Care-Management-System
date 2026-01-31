import java.util.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/* ================= INTERFACE (ABSTRACTION) ================= */
interface HospitalOperations {
    void authMenu();
    void menu();
    void addPatient();
    void addDiagnosis();
    void viewHistory();
    void generateBill();
    void help();
}

/* ================= USER CLASS (ENCAPSULATION) ================= */
class User {
    private String username;
    private String password;

    public User(String u, String p) {
        username = u;
        password = p;
    }

    public String getData() {
        return username + "," + password;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
}

/* ================= PATIENT CLASS (ENCAPSULATION) ================= */
class Patient {
    private String name, address, sex, bloodGroup, disease;
    private long contact, patientId;
    private int age;

    public Patient(String name, String address, long contact, int age,
                   String sex, String bloodGroup, String disease, long patientId) {
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.age = age;
        this.sex = sex;
        this.bloodGroup = bloodGroup;
        this.disease = disease;
        this.patientId = patientId;
    }

    public String getDetails() {
        return "Name : " + name +
               "\nAddress : " + address +
               "\nContact : " + contact +
               "\nAge : " + age +
               "\nSex : " + sex +
               "\nBlood Group : " + bloodGroup +
               "\nPrevious Disease : " + disease +
               "\nPatient ID : " + patientId;
    }
}

/* ================= MAIN SYSTEM CLASS ================= */
class Info implements HospitalOperations {

    Scanner scan = new Scanner(System.in);

    /* ---------- Utility Methods ---------- */
    private String getCurrentDateTime() {
        DateTimeFormatter dtf =
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return dtf.format(LocalDateTime.now());
    }

    private void pause() {
        System.out.println("\nPress Enter to continue...");
        try { System.in.read(); } catch (Exception e) {}
    }

    /* ================= AUTHENTICATION ================= */

    public void authMenu() {
        while (true) {
            System.out.println("\n===== AUTHENTICATION =====");
            System.out.println("1. Sign Up");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");

            int ch = scan.nextInt();

            switch (ch) {
                case 1: signUp(); break;
                case 2:
                    if (login()) menu();
                    break;
                case 3: System.exit(0);
                default: System.out.println("Invalid choice!");
            }
        }
    }

    private void signUp() {
        try {
            scan.nextLine();
            System.out.print("Create Username: ");
            String u = scan.nextLine();
            System.out.print("Create Password: ");
            String p = scan.nextLine();

            User user = new User(u, p);

            BufferedWriter bw =
                    new BufferedWriter(new FileWriter("users.txt", true));
            bw.write(user.getData());
            bw.newLine();
            bw.close();

            System.out.println("Signup successful! Please login.");
        } catch (IOException e) {
            System.out.println("Error during signup!");
        }
    }

    private boolean login() {
        try {
            scan.nextLine();
            System.out.print("Username: ");
            String u = scan.nextLine();
            System.out.print("Password: ");
            String p = scan.nextLine();

            BufferedReader br =
                    new BufferedReader(new FileReader("users.txt"));
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(u) && data[1].equals(p)) {
                    System.out.println("Login Successful!");
                    br.close();
                    return true;
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("No users found. Please signup first.");
        }

        System.out.println("Invalid credentials!");
        return false;
    }

    /* ================= HOSPITAL MENU ================= */
    public void menu() {
        while (true) {
            System.out.println("\n===== E-HEALTH CARE MANAGEMENT SYSTEM =====");
            System.out.println("1. Add New Patient");
            System.out.println("2. Add Diagnosis");
            System.out.println("3. View Patient History");
            System.out.println("4. Generate Bill");
            System.out.println("5. Help");
            System.out.println("6. Logout");
            System.out.print("Enter choice: ");

            int choice = scan.nextInt();

            switch (choice) {
                case 1: addPatient(); break;
                case 2: addDiagnosis(); break;
                case 3: viewHistory(); break;
                case 4: generateBill(); break;
                case 5: help(); break;
                case 6: return;
                default: System.out.println("Invalid choice!");
            }
        }
    }

    /* ================= ADD PATIENT ================= */
    public void addPatient() {
        try {
            System.out.print("Enter patient file name: ");
            String file = scan.next();

            scan.nextLine();
            System.out.print("Name: ");
            String name = scan.nextLine();
            System.out.print("Address: ");
            String address = scan.nextLine();
            System.out.print("Contact: ");
            long contact = scan.nextLong();
            System.out.print("Age: ");
            int age = scan.nextInt();
            System.out.print("Sex: ");
            String sex = scan.next();
            System.out.print("Blood Group: ");
            String bg = scan.next();
            scan.nextLine();
            System.out.print("Previous Disease: ");
            String disease = scan.nextLine();
            System.out.print("Patient ID: ");
            long id = scan.nextLong();

            Patient p = new Patient(name, address, contact, age,
                                    sex, bg, disease, id);

            BufferedWriter bw =
                    new BufferedWriter(new FileWriter(file + ".txt"));
            bw.write("Date of Admission: " + getCurrentDateTime() + "\n");
            bw.write(p.getDetails());
            bw.close();

            System.out.println("Patient record saved successfully!");
        } catch (IOException e) {
            System.out.println("File error!");
        }
        pause();
    }

    /* ================= ADD DIAGNOSIS ================= */
    public void addDiagnosis() {
        try {
            System.out.print("Enter patient file name: ");
            String file = scan.next();

            BufferedWriter bw =
                    new BufferedWriter(new FileWriter(file + ".txt", true));
            scan.nextLine();

            System.out.print("Doctor Name: ");
            bw.write("\nDoctor: " + scan.nextLine());
            System.out.print("Symptoms: ");
            bw.write("\nSymptoms: " + scan.nextLine());
            System.out.print("Diagnosis: ");
            bw.write("\nDiagnosis: " + scan.nextLine());
            System.out.print("Medicines: ");
            bw.write("\nMedicines: " + scan.nextLine());
            bw.write("\nDate: " + getCurrentDateTime());
            bw.write("\n--------------------------------------\n");

            bw.close();
            System.out.println("Diagnosis added successfully!");
        } catch (IOException e) {
            System.out.println("File error!");
        }
        pause();
    }

    /* ================= VIEW HISTORY ================= */
    public void viewHistory() {
        try {
            System.out.print("Enter patient file name: ");
            String file = scan.next();

            BufferedReader br =
                    new BufferedReader(new FileReader(file + ".txt"));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Patient file not found!");
        }
        pause();
    }

    /* ================= BILL ================= */
    public void generateBill() {
        try {
            System.out.print("Enter patient file name: ");
            String file = scan.next();

            System.out.print("Days stayed: ");
            int days = scan.nextInt();
            System.out.print("Ward charge per day: ");
            double ward = scan.nextDouble();
            System.out.print("Doctor fee: ");
            double doc = scan.nextDouble();
            System.out.print("Service charges: ");
            double service = scan.nextDouble();

            double total = days * ward + doc + service;

            BufferedWriter bw =
                    new BufferedWriter(new FileWriter(file + ".txt", true));
            bw.write("\nTotal Bill Amount: " + total);
            bw.close();

            System.out.println("TOTAL AMOUNT: " + total);
        } catch (IOException e) {
            System.out.println("Billing error!");
        }
        pause();
    }

    /* ================= HELP ================= */
    public void help() {
        System.out.println("\nThis is a Java-based E-Health Care Management System.");
        System.out.println("It supports authentication, patient records, diagnosis, and billing.");
        pause();
    }
}

/* ================= MAIN CLASS ================= */
public class Ehospital {
    public static void main(String[] args) {
        Info system = new Info();
        system.authMenu();   // Signup → Login → System
    }
}
