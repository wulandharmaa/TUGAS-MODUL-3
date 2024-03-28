import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Admin extends User {
    private List<Student> userStudent;
    private Scanner scanner;

    public Admin() {
        userStudent = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public void menuAdmin() {
        int choice;
        do {
            System.out.println("===== Admin Menu =====");
            System.out.println("1. Add Student");
            System.out.println("2. Display Registered Students");
            System.out.println("3. Input Book");
            System.out.println("4. Display Books");
            System.out.println("5. Logout");
            System.out.print("Choose option (1-5): ");
            choice = readIntegerInput();

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    displayStudent();
                    break;
                case 3:
                    inputBook();
                    break;
                case 4:
                    displayBooks();
                    break;
                case 5:
                    System.out.println("Logging out from admin account.");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (choice != 5);
    }

    private int readIntegerInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter an integer.");
            scanner.next(); // consume the invalid input
        }
        return scanner.nextInt();
    }

    public void addStudent() {
        scanner.nextLine(); // Consume the newline character

        System.out.print("Enter student name: ");
        String name = scanner.nextLine();

        String nim;
        while (true) {
            System.out.print("Enter student NIM: ");
            nim = scanner.nextLine();
            if (nim.length() == 15) {
                break;
            }
            System.out.println("NIM must be 15 digits.");
        }

        System.out.print("Enter student faculty: ");
        String faculty = scanner.nextLine();

        System.out.print("Enter student study program: ");
        String studyProgram = scanner.nextLine();

        userStudent.add(new Student(name, nim, faculty, studyProgram));
        System.out.println("Student successfully registered.");
    }

    public void displayStudent() {
        System.out.println("List of Registered Students:");
        for (Student student : userStudent) {
            System.out.println("Name: " + student.getName());
            System.out.println("Faculty: " + student.getFaculty());
            System.out.println("NIM: " + student.getNim());
            System.out.println("Program: " + student.getStudyProgram());
            System.out.println();
        }
    }

    public void inputBook() {
        System.out.println("Select book category:");
        System.out.println("1. History Book");
        System.out.println("2. Story Book");
        System.out.println("3. Text Book");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        System.out.print("Enter book title: ");
        String title = scanner.nextLine();

        System.out.print("Enter book author: ");
        String author = scanner.nextLine();

        System.out.print("Enter book stock: ");
        int stock = scanner.nextInt();

        String bookId = generateId();

        switch (choice) {
            case 1:
                bookList.add(new HistoryBook(bookId, title, author, "History", stock));
                break;
            case 2:
                bookList.add(new StoryBook(bookId, title, author, "Story", stock));
                break;
            case 3:
                bookList.add(new TextBook(bookId, title, author, "Text", stock));
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }

        System.out.println("Book added successfully. ID: " + bookId);
    }

    @Override
    public void displayBooks() {
        super.displayBooks();
    }

    public boolean isAdmin(String username, String password) {
        return username.equals("admin") && password.equals("admin");
    }

    private String generateId() {
        return "B" + (bookList.size() + 1);
    }
}

class Student extends User {
    private String name;
    private String nim;
    private String faculty;
    private String studyProgram;
    private List<Book> borrowedBooks;
    private Scanner scanner;

    public Student(String name, String nim, String faculty, String studyProgram) {
        this.name = name;
        this.nim = nim;
        this.faculty = faculty;
        this.studyProgram = studyProgram;
        this.borrowedBooks = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public void menuStudent() {
        int choice;
        do {
            System.out.println("===== Student Menu =====");
            System.out.println("1. Display Borrowed Books");
            System.out.println("2. Borrow Book");
            System.out.println("3. Return Books");
            System.out.println("4. Logout");
            System.out.print("Choose option (1-4): ");
            choice = readIntegerInput();

            switch (choice) {
                case 1:
                    showBorrowedBooks();
                    break;
                case 2:
                    displayBooks();
                    break;
                case 3:
                    returnBooks();
                    break;
                case 4:
                    logout();
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (choice != 4);
    }

    public int readIntegerInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter an integer.");
            scanner.next(); // consume the invalid input
        }
        return scanner.nextInt();
    }

    public void displayInfo() {
        System.out.println("Name: " + name);
        System.out.println("NIM: " + nim);
        System.out.println("Faculty: " + faculty);
        System.out.println("Study Program: " + studyProgram);
    }

    public void showBorrowedBooks() {
        if (borrowedBooks.isEmpty()) {
            System.out.println("No borrowed books.");
        } else {
            System.out.println("Borrowed Books:");
            for (Book book : borrowedBooks) {
                System.out.println(book.getTitle() + " (" + book.getAuthor() + ")");
            }
        }
    }

    public void logout() {
        if (borrowedBooks.isEmpty()) {
            System.out.println("Logging out...");
        } else {
            System.out.println("You have borrowed books. Do you want to:");
            System.out.println("1. Cancel borrowing");
            System.out.println("2. Proceed with borrowing");
            int choice = scanner.nextInt();

            if (choice == 1) {
                for (Book book : borrowedBooks) {
                    book.returnBook();
                }
                borrowedBooks.clear();
                System.out.println("Borrowing cancelled.");
            } else {
                System.out.println("Proceeding with borrowing...");
            }
        }
        System.out.println("Logging out...");
    }

    @Override
    public void displayBooks() {
        super.displayBooks();
        System.out.print("Enter the ID of the book to borrow: ");
        String bookId = scanner.next();

        for (Book book : bookList) {
            if (book.getId().equals(bookId)) {
                if (book.getStock() > 0) {
                    System.out.print("Enter number of days to borrow: ");
                    int duration = scanner.nextInt();

                    book.borrowBook();
                    borrowedBooks.add(book);
                    System.out.println("Book borrowed successfully.");
                } else {
                    System.out.println("Book is out of stock.");
                }
                return;
            }
        }

        System.out.println("Invalid book ID.");
    }

    public void returnBooks() {
        if (borrowedBooks.isEmpty()) {
            System.out.println("No books to return.");
        } else {
            for (Book book : borrowedBooks) {
                book.returnBook();
            }
            borrowedBooks.clear();
            System.out.println("All books returned successfully.");
        }
    }

    public String getName() {
        return name;
    }

    public String getNim() {
        return nim;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getStudyProgram() {
        return studyProgram;
    }
}

abstract class Book {
    protected String id;
    protected String title;
    protected String author;
    protected String category;
    protected int stock;
    protected int duration;

    public Book(String id, String title, String author, String category, int stock) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.stock = stock;
    }

    // Getters and setters

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void borrowBook() {
        stock--;
    }

    public void returnBook() {
        stock++;
    }
}

class HistoryBook extends Book {
    public HistoryBook(String id, String title, String author, String category, int stock) {
        super(id, title, author, category, stock);
    }
}

class StoryBook extends Book {
    public StoryBook(String id, String title, String author, String category, int stock) {
        super(id, title, author, category, stock);
    }
}

class TextBook extends Book {
    public TextBook(String id, String title, String author, String category, int stock) {
        super(id, title, author, category, stock);
    }
}

public class Main {
    static String[][] users = {{"admin", "admin"}};

    public static void mahasiswaLogin() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter your NIM (input 99 to go back): ");
        String nim = input.nextLine();
        if (nim.equals("99")) {
            return;
        }
        if (nim.length() == 15) {
            System.out.println("Login successful for student with NIM " + nim);
            Student student = new Student("", nim, "", "");
            student.menuStudent();
        } else {
            System.out.println("NIM must be 15 characters. Please try again.");
        }
    }

    public static void adminLogin() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter your username (admin): ");
        String username = input.next();
        System.out.print("Enter your password (admin): ");
        String password = input.next();
        if (username.equals(users[0][0]) && password.equals(users[0][1])) {
            System.out.println("Login successful as admin");
            Admin admin = new Admin();
            admin.menuAdmin();
        } else {
            System.out.println("Invalid credentials for admin.");
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String choice;

        do {
            System.out.println("===== Library System =====");
            System.out.println("1. Login as Student");
            System.out.println("2. Login as Admin");
            System.out.println("3. Exit");
            System.out.print("Choose option (1-3): ");
            choice = input.next();

            switch (choice) {
                case "1":
                    mahasiswaLogin();
                    break;
                case "2":
                    adminLogin();
                    break;
                case "3":
                    System.out.println("Thank you. See you!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (!choice.equals("3"));
    }
}