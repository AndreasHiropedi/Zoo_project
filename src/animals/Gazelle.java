package animals;

public class Gazelle extends Animal {

    private String nickname;

    public Gazelle(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public boolean isCompatibleWith(Animal animal) {
        // checks given animal is a zebra or a gazelle
        // gazelles are compatible with themselves and zebras
        if (animal instanceof Zebra || animal instanceof Gazelle)
        {
            return true;
        }
        return false;
    }
}
