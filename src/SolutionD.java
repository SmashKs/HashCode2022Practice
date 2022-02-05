import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SolutionD implements Solution {

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

        List<String> ingredients = new ArrayList<>(ingredientsSet);

        List<String> includedIngredients = new ArrayList<>();
        boolean isOdd = ingredients.size() % 2 != 0;
        int i = 0;
        for (; i < ingredients.size() / 2; i++) {
            String ingredient1 = ingredients.get(i * 2);
            String ingredient2 = ingredients.get(i * 2 + 1);
            if(doInclude(ingredient1, ingredient2, clientPreferencesList)) {
                includedIngredients.add(ingredient1);
                includedIngredients.add(ingredient2);
            }
        }
        if (isOdd) {
            String ingredient = ingredients.get(i * 2);

            Map<String, Long> likesAndCount = clientPreferencesList.stream()
                    .flatMap(preferences -> preferences.getLikes().stream())
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            Map<String, Long> dislikesAndCount = clientPreferencesList.stream()
                    .flatMap(preferences -> preferences.getDislikes().stream())
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            if (doInclude(ingredient, likesAndCount, dislikesAndCount)) {
                includedIngredients.add(ingredient);
            }
        }

        return includedIngredients;
    }

    boolean doInclude(String ingredient1, String ingredient2, List<ClientPreferences> clientPreferencesList) {
        long dislikeCount = clientPreferencesList.stream()
                .filter(preferences -> preferences.getDislikes().contains(ingredient1) ||
                        preferences.getDislikes().contains(ingredient2))
                .count();
        long otherCount = clientPreferencesList.size() - dislikeCount;
        if (otherCount >= dislikeCount) {
            return true;
        }
        return false;
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
