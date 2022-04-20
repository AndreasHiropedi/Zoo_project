package animals;

public class Lion extends Animal {

    private String nickname;

    public Lion(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public boolean isCompatibleWith(Animal animal) {
        // checks given animal is a lion
        // since lions are only compatible with themselves
        if (animal instanceof Lion)
        {
            return true;
        }
        return false;
    }
}
