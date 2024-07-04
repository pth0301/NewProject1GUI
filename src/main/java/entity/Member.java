package entity;

public class Member {
    private String displayName;
    private String userId;
    private String email;
    private String roles;

    public Member(String displayName, String userId, String email, String roles) {
        this.displayName = displayName;
        this.userId = userId;
        this.email = email;
        this.roles = roles;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getRoles() {
        return roles;
    }

}