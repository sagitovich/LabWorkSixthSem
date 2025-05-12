import java.util.*;

class Cinema {
    String name;
    List<Hall> halls;

    Cinema(String name) {
        this.name = name;
        this.halls = new ArrayList<>();
    }

    void addHall(Hall hall) {
        halls.add(hall);
    }
}

class Hall {
    String name;
    int rows;
    int columns;
    List<Session> sessions;

    Hall(String name, int rows, int columns) {
        this.name = name;
        this.rows = rows;
        this.columns = columns;
        this.sessions = new ArrayList<>();
    }

    void addSession(Session session) {
        sessions.add(session);
    }

    void printSeatingPlan(Session session) {
        System.out.println("\nSeating plan for hall: " + name);
        for (int i = 0; i < session.seats.length; i++) {
            for (int j = 0; j < session.seats[i].length; j++) {
                System.out.print(session.seats[i][j].isBooked ? "X " : "O ");
            }
            System.out.println();
        }
    }
}

class Seat {
    boolean isBooked;

    void book() {
        isBooked = true;
    }
}

class Session {
    String movie;
    String time;
    int duration;
    Hall hall;
    Seat[][] seats;

    Session(String movie, String time, int duration, Hall hall) {
        this.movie = movie;
        this.time = time;
        this.duration = duration;
        this.hall = hall;
        this.seats = new Seat[hall.rows][hall.columns];
        for (int i = 0; i < hall.rows; i++) {
            for (int j = 0; j < hall.columns; j++) {
                this.seats[i][j] = new Seat();
            }
        }
    }
}

class TicketSystem {
    List<Cinema> cinemas;

    TicketSystem() {
        cinemas = new ArrayList<>();
    }

    void addCinema(Cinema cinema) {
        cinemas.add(cinema);
    }

    void listCinemas() {
        if (cinemas.isEmpty()) {
            System.out.println("No cinemas available.");
            return;
        }
        System.out.println("\nAvailable cinemas:");
        for (int i = 0; i < cinemas.size(); i++) {
            System.out.println((i + 1) + ". " + cinemas.get(i).name);
        }
    }

    void findNextAvailableSession(String movie) {
        String closestTime = null;
        Cinema closestCinema = null;
        Hall closestHall = null;

        for (Cinema cinema : cinemas) {
            for (Hall hall : cinema.halls) {
                for (Session session : hall.sessions) {
                    if (session.movie.equals(movie) && hasAvailableSeats(session)) {
                        if (closestTime == null || isEarlier(session.time, closestTime)) {
                            closestTime = session.time;
                            closestCinema = cinema;
                            closestHall = hall;
                        }
                    }
                }
            }
        }

        if (closestTime != null) {
            System.out.println("Next available session of " + movie + " is at " + closestTime +
                    " in " + closestCinema.name + " - Hall " + closestHall.name);
        } else {
            System.out.println("No available sessions for " + movie + " with free seats.");
        }
    }

    private boolean hasAvailableSeats(Session session) {
        for (int i = 0; i < session.seats.length; i++) {
            for (int j = 0; j < session.seats[i].length; j++) {
                if (!session.seats[i][j].isBooked) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isEarlier(String time1, String time2) {
        String[] parts1 = time1.split(":");
        String[] parts2 = time2.split(":");
        int hours1 = Integer.parseInt(parts1[0]);
        int minutes1 = Integer.parseInt(parts1[1]);
        int hours2 = Integer.parseInt(parts2[0]);
        int minutes2 = Integer.parseInt(parts2[1]);

        return hours1 < hours2 || (hours1 == hours2 && minutes1 < minutes2);
    }
}

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TicketSystem ticketSystem = new TicketSystem();
        String adminLogin = "admin";
        String adminPassword = "password";

        boolean applicationRunning = true;

        while (applicationRunning) {
            System.out.println("Choose your role:");
            System.out.println("1. Admin");
            System.out.println("2. User");
            System.out.println("0. Exit");
            int roleChoice = getValidInteger(scanner);

            if (roleChoice == 1) {
                boolean validCredentials = false;
                while (!validCredentials) {
                    System.out.print("\nEnter admin login (admin): ");
                    String login = scanner.nextLine();
                    System.out.print("Enter admin password (password): ");
                    String password = scanner.nextLine();

                    if (login.equals(adminLogin) && password.equals(adminPassword)) {
                        validCredentials = true;
                    } else {
                        System.out.println("Invalid login or password. Try again.");
                    }
                }

                boolean running = true;

                while (running) {
                    System.out.println("\nChoose an action:");
                    System.out.println("1. Add Cinema");
                    System.out.println("2. Add Hall to Cinema");
                    System.out.println("3. Create Movie Session");
                    System.out.println("9. Change User");
                    System.out.println("0. Exit");

                    int choice = getValidInteger(scanner);

                    switch (choice) {
                        case 1:
                            System.out.print("\nEnter cinema name: ");
                            String cinemaName = scanner.nextLine();
                            Cinema cinema = new Cinema(cinemaName);
                            ticketSystem.addCinema(cinema);
                            System.out.println("Cinema added successfully!");
                            break;

                        case 2:
                            if (ticketSystem.cinemas.isEmpty()) {
                                System.out.println("No cinemas available. Please add a cinema first.");
                            } else {
                                ticketSystem.listCinemas();
                                System.out.print("Enter the number of the cinema to add hall: ");
                                int cinemaIndex = getValidInteger(scanner) - 1;
                                if (cinemaIndex >= 0 && cinemaIndex < ticketSystem.cinemas.size()) {
                                    Cinema existingCinema = ticketSystem.cinemas.get(cinemaIndex);
                                    System.out.print("Enter hall name: ");
                                    String hallName = scanner.nextLine();

                                    int rows;
                                    while (true) {
                                        System.out.print("Enter number of rows: ");
                                        rows = getValidInteger(scanner);
                                        if (rows > 0) {
                                            break;
                                        } else {
                                            System.out.println("Please enter a positive integer for rows.");
                                        }
                                    }
                                    int columns;
                                    while (true) {
                                        System.out.print("Enter number of columns: ");
                                        columns = getValidInteger(scanner);
                                        if (columns > 0) {
                                            break;
                                        } else {
                                            System.out.println("Please enter a positive integer for columns.");
                                        }
                                    }

                                    Hall hall = new Hall(hallName, rows, columns);
                                    existingCinema.addHall(hall);
                                    System.out.println("Hall added successfully!");
                                } else {
                                    System.out.println("Invalid cinema selection!");
                                }
                            }
                            break;

                        case 3:
                            if (ticketSystem.cinemas.isEmpty()) {
                                System.out.println("No cinemas available. Please add a cinema first.");
                            } else {
                                ticketSystem.listCinemas();
                                System.out.print("Enter the number of the cinema for session: ");
                                int sessionCinemaIndex = getValidInteger(scanner) - 1;
                                if (sessionCinemaIndex >= 0 && sessionCinemaIndex < ticketSystem.cinemas.size()) {
                                    Cinema cinemaForSessionObj = ticketSystem.cinemas.get(sessionCinemaIndex);
                                    if (cinemaForSessionObj.halls.isEmpty()) {
                                        System.out.println("No halls available in this cinema. Please add a hall first.");
                                    } else {
                                        System.out.println("Available halls:");
                                        for (int i = 0; i < cinemaForSessionObj.halls.size(); i++) {
                                            System.out.println((i + 1) + ". " + cinemaForSessionObj.halls.get(i).name);
                                        }
                                        System.out.print("Enter hall number for session: ");
                                        int hallForSessionIndex = getValidInteger(scanner) - 1;
                                        if (hallForSessionIndex >= 0 && hallForSessionIndex < cinemaForSessionObj.halls.size()) {
                                            Hall hallForSession = cinemaForSessionObj.halls.get(hallForSessionIndex);
                                            System.out.print("Enter movie name for session: ");
                                            String movieName = scanner.nextLine();
                                            
                                            String time;
                                            while (true) {
                                                System.out.print("Enter session time (HH:MM): ");
                                                time = scanner.nextLine();
                                                if (time.matches("([01]\\d|2[0-3]):[0-5]\\d")) {
                                                    break;
                                                } else {
                                                    System.out.println("Invalid time format. Please enter time in HH:MM format.");
                                                }
                                            }

                                            System.out.print("Enter duration (minutes): ");
                                            int duration = getValidInteger(scanner);
                                            Session session = new Session(movieName, time, duration, hallForSession);
                                            hallForSession.addSession(session);
                                            System.out.println("Session created successfully!");
                                        } else {
                                            System.out.println("Invalid hall selection!");
                                        }
                                    }
                                } else {
                                    System.out.println("Invalid cinema selection!");
                                }
                            }
                            break;

                        case 9:
                            running = false;
                            System.out.println("Switching user...\n");
                            break;

                        case 0:
                            running = false;
                            applicationRunning = false;
                            System.out.println("Exiting the application...");
                            break;

                        default:
                            System.out.println("Invalid choice! Please try again.\n");
                    }
                }
            } else if (roleChoice == 2) {
                boolean userRunning = true;

                while (userRunning) {
                    System.out.println("\nChoose an action:");
                    System.out.println("1. Book Ticket");
                    System.out.println("2. Find Next Available Session");
                    System.out.println("9. Change User");
                    System.out.println("0. Exit");

                    int userChoice = getValidInteger(scanner);

                    switch (userChoice) {
                        case 1:
                            if (ticketSystem.cinemas.isEmpty()) {
                                System.out.println("No cinemas available for booking tickets.");
                            } else {
                                Set<String> movies = new HashSet<>();
                                for (Cinema cinema : ticketSystem.cinemas) {
                                    for (Hall hall : cinema.halls) {
                                        for (Session session : hall.sessions) {
                                            movies.add(session.movie);
                                        }
                                    }
                                }

                                if (movies.isEmpty()) {
                                    System.out.println("No movies available for booking.");
                                    break;
                                }

                                System.out.println("\nAvailable movies:");
                                List<String> movieList = new ArrayList<>(movies);
                                for (int i = 0; i < movieList.size(); i++) {
                                    System.out.println((i + 1) + ". " + movieList.get(i));
                                }
                                System.out.print("Select a movie (number): ");
                                int movieChoice = getValidInteger(scanner) - 1;

                                if (movieChoice >= 0 && movieChoice < movieList.size()) {
                                    String selectedMovie = movieList.get(movieChoice);

                                    List<Cinema> availableCinemas = new ArrayList<>();
                                    for (Cinema cinema : ticketSystem.cinemas) {
                                        for (Hall hall : cinema.halls) {
                                            for (Session session : hall.sessions) {
                                                if (session.movie.equals(selectedMovie)) {
                                                    availableCinemas.add(cinema);
                                                    break; // Выход из цикла, если фильм найден
                                                }
                                            }
                                        }
                                    }

                                    if (availableCinemas.isEmpty()) {
                                        System.out.println("No cinemas available for this movie.");
                                        break;
                                    }

                                    System.out.println("Available cinemas for " + selectedMovie + ":");
                                    List<Cinema> uniqueCinemas = new ArrayList<>();
                                    Set<String> uniqueCinemaNames = new HashSet<>();
                                    for (Cinema cinema : availableCinemas) {
                                        if (uniqueCinemaNames.add(cinema.name)) {
                                            uniqueCinemas.add(cinema);
                                        }
                                    }

                                    for (int i = 0; i < uniqueCinemas.size(); i++) {
                                        System.out.println((i + 1) + ". " + uniqueCinemas.get(i).name);
                                    }

                                    System.out.print("Select a cinema (number): ");
                                    int cinemaChoice = getValidInteger(scanner) - 1;

                                    if (cinemaChoice >= 0 && cinemaChoice < uniqueCinemas.size()) {
                                        Cinema selectedCinema = uniqueCinemas.get(cinemaChoice);
                                        List<Hall> availableHalls = new ArrayList<>();

                                        for (Hall hall : selectedCinema.halls) {
                                            for (Session session : hall.sessions) {
                                                if (session.movie.equals(selectedMovie)) {
                                                    availableHalls.add(hall);
                                                }
                                            }
                                        }

                                        if (availableHalls.isEmpty()) {
                                            System.out.println("No sessions available for this movie in the selected cinema.");
                                            break;
                                        }

                                        System.out.println("Available halls for " + selectedMovie + ":");
                                        List<Hall> uniqueHalls = new ArrayList<>();
                                        Set<String> uniqueHallNames = new HashSet<>();
                                        for (Hall hall : availableHalls) {
                                            if (uniqueHallNames.add(hall.name)) {
                                                uniqueHalls.add(hall);
                                            }
                                        }

                                        for (int i = 0; i < uniqueHalls.size(); i++) {
                                            System.out.println((i + 1) + ". " + uniqueHalls.get(i).name);
                                        }

                                        System.out.print("Select a hall (number): ");
                                        int hallChoice = getValidInteger(scanner) - 1;

                                        if (hallChoice >= 0 && hallChoice < uniqueHalls.size()) {
                                            Hall selectedHall = uniqueHalls.get(hallChoice);
                                            System.out.println("Available sessions for " + selectedMovie + " in hall " + selectedHall.name + ":");

                                            List<Session> availableSessions = new ArrayList<>();
                                            for (Session session : selectedHall.sessions) {
                                                if (session.movie.equals(selectedMovie)) {
                                                    availableSessions.add(session);
                                                    System.out.println("- " + session.time);
                                                }
                                            }

                                            System.out.print("Select a session time (HH:MM): ");
                                            String selectedTime = scanner.nextLine();

                                            boolean timeFound = false;
                                            for (Session session : availableSessions) {
                                                if (session.time.equals(selectedTime)) {
                                                    timeFound = true;
                                                    boolean hallRunning = true;

                                                    while (hallRunning) {
                                                        System.out.println("\nChoose an action:");
                                                        System.out.println("1. View Seating Plan");
                                                        System.out.println("2. Book a Seat");
                                                        System.out.println("9. Go Back");
                                                        int hallAction = getValidInteger(scanner);

                                                        switch (hallAction) {
                                                            case 1:
                                                                selectedHall.printSeatingPlan(session);
                                                                break;

                                                            case 2:
                                                                System.out.print("Enter row number: ");
                                                                int row = getValidInteger(scanner) - 1;
                                                                System.out.print("Enter column number: ");
                                                                int column = getValidInteger(scanner) - 1;

                                                                if (row >= 0 && row < selectedHall.rows &&
                                                                    column >= 0 && column < selectedHall.columns) {
                                                                    if (!session.seats[row][column].isBooked) {
                                                                        session.seats[row][column].book();
                                                                        System.out.println("Seat booked successfully!");
                                                                    } else {
                                                                        System.out.println("This seat is already booked.");
                                                                    }
                                                                } else {
                                                                    System.out.println("Invalid seat selection.");
                                                                }
                                                                break;

                                                            case 9:
                                                                hallRunning = false;
                                                                break;

                                                            default:
                                                                System.out.println("Invalid choice! Please try again.");
                                                        }
                                                    }
                                                    break; // Выход из цикла, если время найдено
                                                }
                                            }

                                            if (!timeFound) {
                                                System.out.println("Invalid session time selected.");
                                            }
                                        } else {
                                            System.out.println("Invalid hall selection!");
                                        }
                                    } else {
                                        System.out.println("Invalid cinema selection!");
                                    }
                                } else {
                                    System.out.println("Invalid movie selection!");
                                }
                            }
                            break;

                        case 2:
                            System.out.print("Enter the movie name to find the next available session: ");
                            String movieToFind = scanner.nextLine();
                            ticketSystem.findNextAvailableSession(movieToFind);
                            break;

                        case 9:
                            userRunning = false;
                            System.out.println("Switching user...");
                            break;

                        case 0:
                            userRunning = false;
                            applicationRunning = false;
                            System.out.println("Exiting the application...");
                            break;

                        default:
                            System.out.println("Invalid choice! Please try again.");
                    }
                }
            } else if (roleChoice == 0) {
                applicationRunning = false;
                System.out.println("Exiting the application...");
            } else {
                System.out.println("Invalid role selection.");
            }
        }

        scanner.close();
    }

    private static int getValidInteger(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
}