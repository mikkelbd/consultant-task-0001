package no.unit.transformer;

public class User implements Comparable<User> {
    private String name;
    private int sequence;
    private String role;
    private String began;

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getBegan() {
        return began;
    }

    public void setBegan(String began) {
        this.began = began;
    }

    @Override
    public int compareTo(User other) {
        return this.sequence - other.sequence;
    }
}
