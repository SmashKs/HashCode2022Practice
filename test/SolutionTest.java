import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class SolutionTest {

    private Solution solutionA;
    private Solution solutionB;
    private Solution solutionD;
    private Solution solutionE;

    @BeforeEach
    public void setUp() {
        solutionA = new SolutionA();
        solutionB = new SolutionB();
        solutionD = new SolutionD();
        solutionE = new SolutionE();
    }

    public static Stream<Arguments> dataSource() {
        return Stream.of(
                Arguments.of(
                        "5\n" +
                                "2 akuof byyii\n" +
                                "0\n" +
                                "2 dlust luncl\n" +
                                "0\n" +
                                "2 akuof luncl\n" +
                                "0\n" +
                                "2 dlust vxglq\n" +
                                "0\n" +
                                "2 dlust xveqd\n" +
                                "0\n"
                ),
                Arguments.of(
                        "10\n" +
                                "3 akuof byyii dlust\n" +
                                "1 xdozp\n" +
                                "3 dlust luncl qzfyo\n" +
                                "1 xdozp\n" +
                                "3 akuof luncl vxglq\n" +
                                "1 qzfyo\n" +
                                "3 dlust vxglq luncl\n" +
                                "0\n" +
                                "3 dlust xveqd tfeej\n" +
                                "0\n" +
                                "3 qzfyo vxglq luncl\n" +
                                "1 byyii\n" +
                                "3 luncl xdozp xveqd\n" +
                                "1 sunhp\n" +
                                "3 byyii xdozp tfeej\n" +
                                "1 qzfyo\n" +
                                "3 dlust akuof tfeej\n" +
                                "1 xveqd\n" +
                                "3 vxglq dlust byyii\n" +
                                "1 akuof\n"
                )
        );
    }

    @ParameterizedTest
    @MethodSource("dataSource")
    public void testA(String input) {
        List<ClientPreferences> clientPreferencesList = parseString(input);

        List<String> answer = solutionA.getAnswer(clientPreferencesList);

        long score = clientPreferencesList.stream()
                .filter(preferences -> willEatPizza(preferences, answer))
                .count();

        System.out.println("------- Output (A) -------");
        System.out.println(answer.size() + " " + String.join(" ", answer));
        System.out.println("------- Score (A) -------");
        System.out.println(score + " / " + clientPreferencesList.size());
    }

    @ParameterizedTest
    @MethodSource("dataSource")
    public void testB(String input) {
        List<ClientPreferences> clientPreferencesList = parseString(input);

        List<String> answer = solutionB.getAnswer(clientPreferencesList);

        long score = clientPreferencesList.stream()
                .filter(preferences -> willEatPizza(preferences, answer))
                .count();

        System.out.println("------- Output (B) -------");
        System.out.println(answer.size() + " " + String.join(" ", answer));
        System.out.println("------- Score (B) -------");
        System.out.println(score + " / " + clientPreferencesList.size());
    }

    @ParameterizedTest
    @MethodSource("dataSource")
    public void testD(String input) {
        List<ClientPreferences> clientPreferencesList = parseString(input);

        List<String> answer = solutionD.getAnswer(clientPreferencesList);

        long score = clientPreferencesList.stream()
                .filter(preferences -> willEatPizza(preferences, answer))
                .count();

        System.out.println("------- Output (D) -------");
        System.out.println(answer.size() + " " + String.join(" ", answer));
        System.out.println("------- Score (D) -------");
        System.out.println(score + " / " + clientPreferencesList.size());
    }

    @ParameterizedTest
    @MethodSource("dataSource")
    public void testE(String input) {
        List<ClientPreferences> clientPreferencesList = parseString(input);

        List<String> answer = solutionE.getAnswer(clientPreferencesList);

        long score = clientPreferencesList.stream()
                .filter(preferences -> willEatPizza(preferences, answer))
                .count();

        System.out.println("------- Output (D) -------");
        System.out.println(answer.size() + " " + String.join(" ", answer));
        System.out.println("------- Score (D) -------");
        System.out.println(score + " / " + clientPreferencesList.size());
    }

    boolean willEatPizza(ClientPreferences clientPreferences, List<String> ingredients) {
        return ingredients.containsAll(clientPreferences.getLikes()) &&
                Collections.disjoint(clientPreferences.getDislikes(), ingredients);
    }

    List<ClientPreferences> parseString(String input) {
        try {
            String inputNoNewLines = input.replace("\n", " ");
            String[] parsed = inputNoNewLines.split(" ");
            int index = 0;
            int numClients = Integer.parseInt(parsed[index++]);
            List<ClientPreferences> clientPreferencesList = new ArrayList<>();
            for (int i = 0; i < numClients; i++) {
                List<String> likes = new ArrayList<>();
                List<String> dislikes = new ArrayList<>();
                int numLikes = Integer.parseInt(parsed[index++]);
                for (int j = 0; j < numLikes; j++) {
                    likes.add(parsed[index++]);
                }
                int numDislikes = Integer.parseInt(parsed[index++]);
                for (int j = 0; j < numDislikes; j++) {
                    dislikes.add(parsed[index++]);
                }
                clientPreferencesList.add(new ClientPreferences(likes, dislikes));
            }
            return clientPreferencesList;
        } catch (Exception e) {
            System.out.println(e);
        }
        return Collections.emptyList();
    }
}
