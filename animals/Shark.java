package animals;

public class Shark extends Animal {

    private String nickname;

    public Shark(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public boolean isCompatibleWith(Animal animal) {
        // checks given animal is a shark or a starfish
        // since sharks are compatible with themselves and starfish
        if (animal instanceof Starfish || animal instanceof Shark)
        {
            return true;
        }
        return false;
    }
}
