package br.com.karol.sistema.domain.enums;

public enum UserRole {
    
    ADMIN("admin"),
    USER("user"),
    CLIENT("client");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    
    public String getRole() {
        return role;
    }

    public static UserRole fromString(String role) {
        for (UserRole userRole : UserRole.values()) {
            if (userRole.role.equalsIgnoreCase(role)) {
                return userRole;
            }
        }
        throw new IllegalArgumentException("Invalid user role: " + role);
    }
}