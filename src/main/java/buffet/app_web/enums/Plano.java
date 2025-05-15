package buffet.app_web.enums;

public enum Plano {
    BASICO("basico"),
    PREMIUM("premium");

    private String plano;

    Plano(String plano){
        this.plano = plano;
    }

    public String getPlano() {
        return plano;
    }
}
