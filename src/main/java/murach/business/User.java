package murach.business;

import java.io.Serializable;

public class User implements Serializable {
    private String firstName;
    private String lastName;
    private String email;
    private String dateOfBirth; // để đơn giản dùng String (yyyy-mm-dd)
    private String ref; // nguồn biết tới
    private boolean wantOffers;
    private boolean wantEmail;
    private String contact; // phương thức liên hệ

    public User() {
    }

    public User(String firstName, String lastName, String email, String dateOfBirth, String ref, boolean wantOffers,
                boolean wantEmail, String contact) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.ref = ref;
        this.wantOffers = wantOffers;
        this.wantEmail = wantEmail;
        this.contact = contact;
    }

    // Getters & Setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String v) {
        this.firstName = v;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String v) {
        this.lastName = v;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String v) {
        this.email = v;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String v) {
        this.dateOfBirth = v;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String v) {
        this.ref = v;
    }

    public boolean isWantOffers() {
        return wantOffers;
    }

    public String getWantOffers() {
        return wantOffers ? "Yes" : "No";
    }

    public void setWantOffers(boolean v) {
        this.wantOffers = v;
    }

    public boolean isWantEmail() {
        return wantEmail;
    }

    public String getWantEmail() {
        return wantEmail ? "Yes" : "No";
    }

    public void setWantEmail(boolean v) {
        this.wantEmail = v;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String v) {
        this.contact = v;
    }
}