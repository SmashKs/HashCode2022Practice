import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SolutionB implements Solution {

    @Override
    public List<String> getAnswer(List<ClientPreferences> clientPreferencesList) {

        Set<String> likedIngredients = clientPreferencesList.stream()
                .flatMap(preferences -> preferences.getLikes().stream())
                .collect(Collectors.toSet());

        System.out.println("Liked ingredients: " + likedIngredients.size());

        Set<String> dislikedIngredients = clientPreferencesList.stream()
                .flatMap(preferences -> preferences.getDislikes().stream())
                .collect(Collectors.toSet());

        System.out.println("Disliked ingredients: " + dislikedIngredients.size());

        Long numClientsDislikeZero = clientPreferencesList.stream()
                .filter(preferences -> preferences.getDislikes().size() == 0L)
                    .count();

        System.out.println("Number numClientsDislikeZero: " + numClientsDislikeZero);

        Set<String> intersection = new HashSet<>(likedIngredients);
        intersection.retainAll(dislikedIngredients);
        System.out.println("Ingredients both liked and disliked: " + intersection.size());

        Set<String> ingredientsSet = new HashSet<>(likedIngredients);
        ingredientsSet.addAll(dislikedIngredients);

        ingredientsSet.removeAll(dislikedIngredients);

        return new ArrayList<>(ingredientsSet);
    }
}
