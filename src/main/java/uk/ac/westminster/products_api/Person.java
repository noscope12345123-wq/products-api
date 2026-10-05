package uk.ac.westminster.products_api;

/**
 * A simple person with a name and an email address.
 *
 * Fields are private and exposed through getters/setters that follow
 * the JavaBean naming convention (getName, getEmail, ...), so that
 * Spring's JSON converter (Jackson) can read them.
 */
public class Person {

    private String name;
    private String email;

    public Person() {
    }

    public Person(String name) {
        this.name = name;
    }

    public Person(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
