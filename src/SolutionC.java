import com.google.common.collect.Sets;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SolutionC implements Solution {

    @Override
    public List<String> getAnswer(List<ClientPreferences> clientPreferencesList) {

        Set<String> likedIngredients = clientPreferencesList.stream()
                .flatMap(preferences -> preferences.getLikes().stream())
                .collect(Collectors.toSet());

        Set<String> dislikedIngredients = clientPreferencesList.stream()
                .flatMap(preferences -> preferences.getDislikes().stream())
                .collect(Collectors.toSet());

        Set<String> ingredientsSet = new HashSet<>(likedIngredients);
        ingredientsSet.addAll(dislikedIngredients);

        long highScore = -1;
        Set<String> highScoreIngredients = Collections.emptySet();

        Set<Set<String>> allCombinations = IntStream.range(1, ingredientsSet.size() + 1)
                .mapToObj(i -> Sets.combinations(ingredientsSet, i))
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

        for(Set<String> ingredientCombination : allCombinations) {

            long currentScore = getScore(clientPreferencesList, ingredientCombination);
            if (currentScore > highScore) {
                highScore = currentScore;
                highScoreIngredients = ingredientCombination;
            }
        }

        return new ArrayList<>(highScoreIngredients);
    }

    private long getScore(List<ClientPreferences> clientPreferencesList, Set<String> ingredients) {
        return clientPreferencesList.stream()
                .filter(preferences -> willEatPizza(preferences, ingredients))
                .count();
    }

    boolean willEatPizza(ClientPreferences clientPreferences, Set<String> ingredients) {
        return ingredients.containsAll(clientPreferences.getLikes()) &&
                Collections.disjoint(clientPreferences.getDislikes(), ingredients);
    }
}
