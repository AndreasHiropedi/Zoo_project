package animals;

public class Starfish extends Animal {

    private String nickname;

    public Starfish(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public boolean isCompatibleWith(Animal animal) {
        // checks given animal is either a shark, seal or a starfish
        // since starfish are compatible with both sharks and seals, as well as themselves
        if (animal instanceof Shark || animal instanceof Seal || animal instanceof Starfish)
        {
            return true;
        }
        return false;
    }
}
