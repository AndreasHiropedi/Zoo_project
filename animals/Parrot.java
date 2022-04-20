package animals;

public class Parrot extends Animal {

    private String nickname;

    public Parrot(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public boolean isCompatibleWith(Animal animal) {
        // checks given animal is a parrot
        // since parrots are only compatible with themselves
        if (animal instanceof Parrot)
        {
            return true;
        }
        return false;
    }
}
