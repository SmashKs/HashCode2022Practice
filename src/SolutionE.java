import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SolutionE implements Solution {

    @Override
    public List<String> getAnswer(List<ClientPreferences> clientPreferencesList) {

        Map<String, Long> likesAndCount = clientPreferencesList.stream()
                .flatMap(preferences -> preferences.getLikes().stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        Map<String, Long> dislikesAndCount = clientPreferencesList.stream()
                .flatMap(preferences -> preferences.getDislikes().stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        Map<String, Long> newLikesAndCount = remove5LikeCount(likesAndCount);
        Map<String, Long> newDislikesAndCount = remove5LikeCount(dislikesAndCount);

        Set<String> ingredientsSet = new HashSet<>(newLikesAndCount.keySet());
        ingredientsSet.addAll(newDislikesAndCount.keySet());

        List<String> includedIngredients = ingredientsSet.stream()
                .filter(ingredient -> doInclude(ingredient, newLikesAndCount, newDislikesAndCount))
                .collect(Collectors.toList());

        return includedIngredients;

    }

    private Map<String, Long> remove5LikeCount(Map<String, Long> map) {
        List<String> topTenPercentKeys = map.entrySet().stream()
                .filter(e -> e.getValue() == 5L)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        Map<String, Long> newMap = new HashMap<>(map);
        topTenPercentKeys.forEach(newMap::remove);

        return newMap;
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
