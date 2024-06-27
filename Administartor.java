public class Administartor extends User {
    public Administartor(String name, String password) {
        super(name, password);
    }

    @Override
    public UserType getUserType() {
        return UserType.ADMINISTRATOR;
    }

    @Override
    public String toString() {
        return "Administrator{" + getName() + '}';
    }
}