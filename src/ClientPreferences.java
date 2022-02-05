import java.util.List;

public class ClientPreferences {

    final List<String> likes;
    final List<String> dislikes;

    public ClientPreferences(List<String> likes, List<String> dislikes) {
        this.likes = likes;
        this.dislikes = dislikes;
    }

    public List<String> getLikes() {
        return likes;
    }

    public List<String> getDislikes() {
        return dislikes;
    }
}
