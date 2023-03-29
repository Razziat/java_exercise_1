import java.nio.file.Files;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Scanner;

public class Launcher {
    public static void main(String[] args) {
        System.out.println("Welcome");
        Scanner console = new Scanner(System.in);
        String name = console.nextLine();
        if (!name.equals("quit")) {
            System.out.println("Unknown command");
        }
    }
}