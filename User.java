import java.util.ArrayList;
import java.util.List;

class User {
    protected List<Book> bookList;

    public User() {
        bookList = new ArrayList<>(List.of(
                new HistoryBook("B1", "Sejarah Indonesia", "Alex", "History", 12),
                new StoryBook("B2", "Aljabar", "Sultan", "Story", 6),
                new TextBook("B3", "Matdis", "Bejo", "Text", 4),
                new TextBook("B4", "Bahasa Indonesia", "Max", "Text", 5)
        ));
    }

    public void displayBooks() {
        System.out.println("Available Books:");
        for (Book book : bookList) {
            System.out.println(book.getId() + " (" + book.getTitle() + ") " + " (" + book.getAuthor() + ") - " + book.getStock() + " copies available");
        }
    }
}