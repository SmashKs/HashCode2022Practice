import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SolutionA implements Solution {

    @Override
    public List<String> getAnswer(List<ClientPreferences> clientPreferencesList) {

        // Pineapple -> 5 people
        Map<String, Long> likesAndCount = clientPreferencesList.stream()
                .flatMap(preferences -> preferences.getLikes().stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        Map<String, Long> dislikesAndCount = clientPreferencesList.stream()
                .flatMap(preferences -> preferences.getDislikes().stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        Set<String> ingredientsSet = new HashSet<>(likesAndCount.keySet());
        ingredientsSet.addAll(dislikesAndCount.keySet());

        List<String> includedIngredients = ingredientsSet.stream()
                .filter(ingredient -> doInclude(ingredient, likesAndCount, dislikesAndCount))
                .collect(Collectors.toList());

        return includedIngredients;
    }

    boolean doInclude(String ingredient, Map<String, Long> likesAndCount, Map<String, Long> dislikesAndCount) {
        Long likesCount = likesAndCount.getOrDefault(ingredient, 0L);
        Long dislikesCount = dislikesAndCount.getOrDefault(ingredient, 0L);

        if (likesCount > dislikesCount) {
            return true;
        } else if (likesCount < dislikesCount) {
            return false;
        }
        return true;
    }
}
