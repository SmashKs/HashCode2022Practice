import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SolutionF implements Solution {


    @Override
    public List<String> getAnswer(List<ClientPreferences> clientPreferencesList) {

        Set<String> likedIngredients = clientPreferencesList.stream()
                                                            .flatMap(preferences -> preferences.getLikes().stream())
                                                            .collect(Collectors.toSet());

        Set<String> dislikedIngredients = clientPreferencesList.stream()
                                                               .flatMap(preferences -> preferences.getDislikes().stream())
                                                               .collect(Collectors.toSet());

        Set<String> allIngredients = new HashSet<>(likedIngredients);
        allIngredients.addAll(dislikedIngredients);

        Set<String> includedIngredients = new HashSet<>();

        for (int i = 0; i  < 5; i++) {
            includedIngredients = greedyAlgo(includedIngredients, allIngredients, clientPreferencesList);
        }


        return new ArrayList<>(includedIngredients);
    }

    Set<String> greedyAlgo(Set<String> includedIngredients, Set<String> allIngredients, List<ClientPreferences> clientPreferences) {

        for (String currentIngredient : allIngredients) {
            boolean alreadyIncluded = includedIngredients.contains(currentIngredient);

            Set<String>  withoutCurrentIngredient;
            Set<String> withCurrentIngredient;
            if (alreadyIncluded) {
                withoutCurrentIngredient = new HashSet<>(includedIngredients);
                withoutCurrentIngredient.remove(currentIngredient);
                withCurrentIngredient = new HashSet<>(includedIngredients);
            } else {
                withoutCurrentIngredient = new HashSet<>(includedIngredients);
                withCurrentIngredient = new HashSet<>(includedIngredients);
                withCurrentIngredient.add(currentIngredient);
            }

            long numSatisfiedWithout = numSatisfiedClients(withoutCurrentIngredient, clientPreferences);
            long numSatisfiedWith = numSatisfiedClients(withCurrentIngredient, clientPreferences);


            if (numSatisfiedWith >= numSatisfiedWithout) {
                includedIngredients = withCurrentIngredient;
            } else {
                includedIngredients = withoutCurrentIngredient;
            }
        }
        return includedIngredients;
    }

    long numSatisfiedClients(Set<String> includedIngredients, List<ClientPreferences> clientPreferences) {
        return clientPreferences.stream()
                                .filter( pref -> willClientCome(includedIngredients, pref))
                                .count();
    }

    boolean willClientCome(Set<String> includedIngredients, ClientPreferences clientPreferences) {
        return includedIngredients.containsAll(clientPreferences.getLikes()) &&
            Collections.disjoint(clientPreferences.getDislikes(), includedIngredients);
    }
}
