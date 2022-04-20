package animals;

public class Buzzard extends Animal {

    private String nickname;

    public Buzzard(String nickname) {
        this.nickname = nickname;
    }


    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public boolean isCompatibleWith(Animal animal) {
        // checks the given animal is a buzzard
        // since buzzards are only compatible with themselves
        if (animal instanceof Buzzard)
        {
            return true;
        }
        return false;
    }
}
