package animals;

public class Seal extends Animal {

    private String nickname;

    public Seal(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public boolean isCompatibleWith(Animal animal) {
        // checks given animal is a seal or a starfish
        // since seals are compatible with themselves and starfish
        if (animal instanceof Starfish || animal instanceof Seal)
        {
            return true;
        }
        return false;
    }
}
