import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SolutionFileTest {

    private Solution solutionA;
    private Solution solutionB;
    private Solution solutionC;
    private Solution solutionD;
    private Solution solutionE;
    private Solution solutionF;

    @BeforeEach
    public void setUp() {
        solutionA = new SolutionA();
        solutionB = new SolutionB();
        solutionC = new SolutionC();
        solutionD = new SolutionD();
        solutionE = new SolutionE();
        solutionF = new SolutionF();
    }

    /*
    Optimal Solutions: (number of clients)
    A - 2 (and we got 2)
    B - 5 (and we got 5)
    C - 5 (and we got 4)
    D - 1805 (and we got 1781)
    E - 2085 (and we got 1531)
     */

    /*
    Problem D
     - Optimal: 1805 Clients
     - Our best so far: 1781 clients
     - Total: 9368 clients

     - Dislikes == 0: 1441
     - Dislikes < 4: 8817 clients
     - Dislikes < 5: 9310 clients
     - Likes == 1: 4609 clients
     - Likes <= 2: 7359

     - Liked ingredients - 600 -> 100 ingredients which are only liked
     - Disliked ingredients - 500
     - Intersection - 500

    Problem E
     - Optimal: 2085 Clients
     - Our best so far: 1531 clients
     - Total: 4986 clients

     - Dislikes == 0: 840
     - Dislikes < 4: 3304 clients
     - Dislikes < 5: 4138 clients
     - Likes == 1: 1056 clients ( liked 1 intersect all disliked -> 758 (1056-758=298
     - Likes <= 2: 2026 clients
     - likes == 2: 970 clients ( liked 2 intersect all disliked ->

     - Liked ingredients - 10000 -> 2863 ingredients which are only liked
     - Disliked ingredients - 7137
     - Intersection - 7137
     */

    public static Stream<Arguments> dataSource() {
        return Stream.of(
                Arguments.of("resources/a.txt", 1),
                Arguments.of("resources/b.txt", 2),
                Arguments.of("resources/c.txt", 3),
                Arguments.of("resources/d.txt", 4),
                Arguments.of("resources/e.txt", 5)
        );
    }

    @ParameterizedTest
    @MethodSource("dataSource")
    public void testA(String fileName, int test) {
        List<ClientPreferences> clientPreferencesList = parseFile(fileName);

        List<String> answer = solutionA.getAnswer(clientPreferencesList);

        long score = clientPreferencesList.stream()
                .filter(preferences -> willEatPizza(preferences, answer))
                .count();

        System.out.println("------- #" + test + " Score (A) -------");
        System.out.println(score + " / " + clientPreferencesList.size());
    }

    @ParameterizedTest
    @MethodSource("dataSource")
    public void testB(String fileName, int test) {
        List<ClientPreferences> clientPreferencesList = parseFile(fileName);

        List<String> answer = solutionB.getAnswer(clientPreferencesList);

        long score = clientPreferencesList.stream()
                .filter(preferences -> willEatPizza(preferences, answer))
                .count();

        System.out.println("------- #" + test + " Score (B) -------");
        System.out.println(score + " / " + clientPreferencesList.size());
    }

    @ParameterizedTest
    @MethodSource("dataSource")
    public void testC(String fileName, int test) {
        // if (test > 3) {
        //     return;
        // }

        List<ClientPreferences> clientPreferencesList = parseFile(fileName);

        List<String> answer = solutionC.getAnswer(clientPreferencesList);

        long score = clientPreferencesList.stream()
                .filter(preferences -> willEatPizza(preferences, answer))
                .count();

        System.out.println("------- #" + test + " Score (C) -------");
        System.out.println(score + " / " + clientPreferencesList.size());
    }

    @ParameterizedTest
    @MethodSource("dataSource")
    public void testD(String fileName, int test) {
        List<ClientPreferences> clientPreferencesList = parseFile(fileName);

        List<String> answer = solutionD.getAnswer(clientPreferencesList);

        long score = clientPreferencesList.stream()
                .filter(preferences -> willEatPizza(preferences, answer))
                .count();

        System.out.println("------- #" + test + " Score (D) -------");
        System.out.println(score + " / " + clientPreferencesList.size());
    }

    @ParameterizedTest
    @MethodSource("dataSource")
    public void testE(String fileName, int test) {
        List<ClientPreferences> clientPreferencesList = parseFile(fileName);

        List<String> answer = solutionE.getAnswer(clientPreferencesList);

        long score = clientPreferencesList.stream()
                .filter(preferences -> willEatPizza(preferences, answer))
                .count();

        System.out.println("------- #" + test + " Score (E) -------");
        System.out.println(score + " / " + clientPreferencesList.size());
    }

    @ParameterizedTest
    @MethodSource("dataSource")
    public void testF(String fileName, int test) {
        List<ClientPreferences> clientPreferencesList = parseFile(fileName);

        List<String> answer = solutionF.getAnswer(clientPreferencesList);

        createOutputFile(answer, fileName.replace("resources", "output"));

        long score = clientPreferencesList.stream()
                                          .filter(preferences -> willEatPizza(preferences, answer))
                                          .count();

        System.out.println("------- #" + test + " Score (F) -------");
        System.out.println(score + " / " + clientPreferencesList.size());
    }

    boolean willEatPizza(ClientPreferences clientPreferences, List<String> ingredients) {
        return ingredients.containsAll(clientPreferences.getLikes()) &&
                Collections.disjoint(clientPreferences.getDislikes(), ingredients);
    }

    List<ClientPreferences> parseFile(String fileName) {

        List<ClientPreferences> clientPreferencesList = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String firstLine = reader.readLine();
            int numClients = Integer.parseInt(firstLine);
            for (int i = 0; i < numClients; i++) {
                String likesLine = reader.readLine();
                List<String> likes = Arrays.asList(likesLine.split(" "));
                List<String> likesSublist = likes.subList(1, likes.size()); // remove count

                String dislikesLine = reader.readLine();
                List<String> dislikes = Arrays.asList(dislikesLine.split(" "));
                List<String> dislikesSublist = dislikes.subList(1, dislikes.size()); // remove count

                clientPreferencesList.add(
                        new ClientPreferences(likesSublist, dislikesSublist)
                );
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return clientPreferencesList;
    }

    void createOutputFile(List<String> answer, String filename) {
        try {
            PrintWriter writer = new PrintWriter(filename, "UTF-8");

            writer.println(answer.size() + " " + answer.stream().collect(Collectors.joining(" ")));

            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
