import java.util.*;

class Book {
    private String title;
    private String author;
    private int year;

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    @Override
    public String toString() {
        return "Книга: " + title + ", Автор: " + author + ", Год издания: " + year;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return year == book.year && Objects.equals(title, book.title) && Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, year);
    }
}

class Library {
    private List<Book> books;
    private Set<String> uniqueAuthors;
    private Map<String, Integer> authorStatistics;

    public Library() {
        this.books = new ArrayList<>();
        this.uniqueAuthors = new HashSet<>();
        this.authorStatistics = new HashMap<>();
    }

    public void addBook(Book book) {
        books.add(book);
        uniqueAuthors.add(book.getAuthor());
        updateAuthorStatistics(book.getAuthor(), 1);
    }

    public void removeBook(Book book) {
        books.remove(book);
        updateAuthorStatistics(book.getAuthor(), -1);
        if (books.stream().noneMatch(b -> b.getAuthor().equals(book.getAuthor()))) {
            uniqueAuthors.remove(book.getAuthor());
        }
    }

    public List<Book> findBooksByAuthor(String author) {
        return books.stream()
                .filter(book -> book.getAuthor().equals(author))
                .toList();
    }

    public List<Book> findBooksByYear(int year) {
        return books.stream()
                .filter(book -> book.getYear() == year)
                .toList();
    }

    public void printAllBooks() {
        books.forEach(System.out::println);
    }

    public void printUniqueAuthors() {
        uniqueAuthors.forEach(System.out::println);
    }

    public void printAuthorStatistics() {
        authorStatistics.forEach((author, count) -> System.out.println(author + ": " + count));
    }

    private void updateAuthorStatistics(String author, int delta) {
        authorStatistics.put(author, authorStatistics.getOrDefault(author, 0) + delta);
    }
}

class LibraryTest {
    public static void main(String[] args) {
        Library library = new Library();

        // Добавление книг
        library.addBook(new Book("Война и мир", "Лев Толстой", 1869));
        library.addBook(new Book("Преступление и наказание", "Федор Достоевский", 1866));
        library.addBook(new Book("Анна Каренина", "Лев Толстой", 1877));
        library.addBook(new Book("Идиот", "Федор Достоевский", 1869));
        library.addBook(new Book("Евгений Онегин", "Александр Пушкин", 1833));

        // Тестирование методов
        System.out.println("Все книги в библиотеке:");
        library.printAllBooks();
        System.out.println();

        System.out.println("Книги Льва Толстого:");
        library.findBooksByAuthor("Лев Толстой").forEach(System.out::println);
        System.out.println();

        System.out.println("Книги, изданные в 1869 году:");
        library.findBooksByYear(1869).forEach(System.out::println);
        System.out.println();

        System.out.println("Уникальные авторы:");
        library.printUniqueAuthors();
        System.out.println();

        System.out.println("Статистика по количеству книг каждого автора:");
        library.printAuthorStatistics();
        System.out.println();

        library.removeBook(new Book("Евгений Онегин", "Александр Пушкин", 1833));

        System.out.println("Все книги в библиотеке после удаления:");
        library.printAllBooks();
        System.out.println();

        System.out.println("Уникальные авторы после удаления:");
        library.printUniqueAuthors();
        System.out.println();

        System.out.println("Статистика по количеству книг каждого автора после удаления:");
        library.printAuthorStatistics();
    }
}