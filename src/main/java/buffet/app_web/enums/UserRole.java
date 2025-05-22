package buffet.app_web.enums;


public enum UserRole {
    ADMIN("admin"),
    USUARIO("usuario"),
    NEXORA_ADMIN("nexora");

    private String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
