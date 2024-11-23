package buffet.app_web.fixture;

import buffet.app_web.entities.TipoEvento;

public class tipoEventoFixture {
    public static TipoEvento buildTipoEvento(){
        TipoEvento tipoEvento = new TipoEvento();
        tipoEvento.setId(1);
        tipoEvento.setNome("Infatil");
        return tipoEvento;
    }
}
