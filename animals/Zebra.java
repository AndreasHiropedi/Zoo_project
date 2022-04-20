package animals;

public class Zebra extends Animal {

    private String nickname;

    public Zebra(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public boolean isCompatibleWith(Animal animal) {
        // checks given animal is a zebra or a gazelle
        // since zebras are compatible with themselves and gazelles
        if (animal instanceof Gazelle || animal instanceof Zebra)
        {
            return true;
        }
        return false;
    }
}
