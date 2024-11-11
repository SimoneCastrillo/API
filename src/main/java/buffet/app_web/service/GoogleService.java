package buffet.app_web.service;

import buffet.app_web.configuration.Google;
import buffet.app_web.entities.Orcamento;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class GoogleService {

    private static final String APPLICATION_NAME = "Buffet";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);

    private final Calendar calendar;

    public GoogleService() throws IOException, GeneralSecurityException {
        this.calendar = getCalendarService();
    }

    private Calendar getCalendarService() throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        return new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        InputStream in = GoogleService.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    public void criarEvento(Orcamento orcamento) {
        try {
            Google google = createGoogleFromOrcamento(orcamento);
            Event event = buildEvent(orcamento, google);

            Event eventoCriado = calendar.events().insert("primary", event).execute();
            String googleEventoId = eventoCriado.getId();

            orcamento.setGoogleEventoId(googleEventoId);
        } catch (Exception e) {
            System.err.println("Error creating event: " + e.getMessage());
        }
    }

    public void atualizarEvento(String calendarId, Orcamento orcamento) throws IOException, GeneralSecurityException {
        Calendar service = getCalendarService();
        String eventId = orcamento.getGoogleEventoId();

        if (eventId != null) {
            Event event = service.events().get(calendarId, eventId).execute();

            if (orcamento.getStatus().equals("FINALIZADO")){
                event.setSummary(orcamento.getStatus());
            } else {
                event.setSummary(orcamento.getTipoEvento().getNome());
            }
            event.setDescription(buildDescription(orcamento));

            ZoneId zoneId = ZoneId.systemDefault();

            LocalDateTime inicio = LocalDateTime.of(orcamento.getDataEvento(), orcamento.getInicio());
            long startTimeInMillis = inicio.atZone(zoneId).toInstant().toEpochMilli();
            EventDateTime start = new EventDateTime().setDateTime(new DateTime(startTimeInMillis));
            event.setStart(start);

            LocalDateTime fim = LocalDateTime.of(orcamento.getDataEvento(), orcamento.getFim());
            long endTimeInMillis = fim.atZone(zoneId).toInstant().toEpochMilli();
            EventDateTime end = new EventDateTime().setDateTime(new DateTime(endTimeInMillis));
            event.setEnd(end);

            service.events().update(calendarId, eventId, event).execute();
            System.out.println("Evento do orçamento atualizado.");
        } else {
            System.out.println("ID do evento não encontrado para este orçamento.");
        }
    }

    public void deletarEvento(String calendarId, Orcamento orcamento) throws IOException, GeneralSecurityException {
        Calendar service = getCalendarService();
        String eventId = orcamento.getGoogleEventoId();

        if (eventId != null) {
            service.events().delete(calendarId, eventId).execute();
            System.out.println("Evento do orçamento deletado.");
        } else {
            System.out.println("ID do evento não encontrado para este orçamento.");
        }
    }

    private Google createGoogleFromOrcamento(Orcamento orcamento) {
        Google google = new Google();
        google.setStartDateTime(orcamento.getDataEvento().atTime(orcamento.getInicio()));
        google.setEndDateTime(orcamento.getDataEvento().atTime(orcamento.getFim()));
        return google;
    }

    private Event buildEvent(Orcamento orcamento, Google google) {
        DateTime startGoogleDateTime = new DateTime(ZonedDateTime.of(google.getStartDateTime(),
                ZoneId.systemDefault()).toInstant().toEpochMilli());
        DateTime endGoogleDateTime = new DateTime(ZonedDateTime.of(google.getEndDateTime(),
                ZoneId.systemDefault()).toInstant().toEpochMilli());

        EventDateTime start = new EventDateTime().setDateTime(startGoogleDateTime);
        EventDateTime end = new EventDateTime().setDateTime(endGoogleDateTime);

        return new Event()
                .setSummary(orcamento.getTipoEvento().getNome())
                .setDescription(buildDescription(orcamento))
                .setStart(start)
                .setEnd(end);
    }

    private String buildDescription(Orcamento orcamento) {
       if (orcamento.getDecoracao() == null){
           return String.format("Orçamento de: %s\n" +
                           "Telefone: %s\n" +
                           "Dados do Orçamento:\n" +
                           "- Número de Convidados: %d\n" +
                           "- Tipo de Evento: %s\n" +
                           "- Status: %s\n" +
                           "- Decoracao: %s\n" +
                           "- Sabor do bolo: %s\n" +
                           "- Prato principal: %s\n" +
                           "- Sugestão: %s",
                   orcamento.getUsuario().getNome(),
                   orcamento.getUsuario().getTelefone(),
                   orcamento.getQtdConvidados(),
                   orcamento.getTipoEvento().getNome(),
                   orcamento.getStatus(),
                   "Sem decoração",
                   orcamento.getSaborBolo(),
                   orcamento.getPratoPrincipal(),
                   orcamento.getSugestao());
       }

        return String.format("Orçamento de: %s\n" +
                        "Telefone: %s\n" +
                        "Dados do Orçamento:\n" +
                        "- Número de Convidados: %d\n" +
                        "- Tipo de Evento: %s\n" +
                        "- Status: %s\n" +
                        "- Decoracao: %s\n" +
                        "- Sabor do bolo: %s\n" +
                        "- Prato principal: %s\n" +
                        "- Sugestão: %s",
                orcamento.getUsuario().getNome(),
                orcamento.getUsuario().getTelefone(),
                orcamento.getQtdConvidados(),
                orcamento.getTipoEvento().getNome(),
                orcamento.getStatus(),
                orcamento.getDecoracao().getNome(),
                orcamento.getSaborBolo(),
                orcamento.getPratoPrincipal(),
                orcamento.getSugestao());
    }

    public List<Google> listarEventos() throws GeneralSecurityException, IOException {
        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = calendar.events().list("primary")
                .setMaxResults(10)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();

        List<Google> listaItems = new ArrayList<>();
        for (Event event : events.getItems()) {
            listaItems.add(criarGoogle(event));
        }
        return listaItems;
    }

    private Google criarGoogle(Event event) {
        Google google = new Google();
        google.setSummary(event.getSummary());
        google.setDescription(event.getDescription());

        DateTime startDateTime = event.getStart().getDateTime();
        DateTime endDateTime = event.getEnd().getDateTime();

        if (startDateTime != null) {
            google.setStartDateTime(formatDateTimeToLocalDateTime(startDateTime));
        }
        if (endDateTime != null) {
            google.setEndDateTime(formatDateTimeToLocalDateTime(endDateTime));
        }

        return google;
    }

    private static LocalDateTime formatDateTimeToLocalDateTime(DateTime dateTime) {
        Instant instant = Instant.ofEpochMilli(dateTime.getValue());
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.of("America/Sao_Paulo"));
        return zonedDateTime.toLocalDateTime();
    }

    public Google[] listarEventosAlfabetica() throws GeneralSecurityException, IOException {
        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = calendar.events().list("primary")
                .setMaxResults(10)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();

        List<Google> listaItems = new ArrayList<>();

        for (Event event : events.getItems()) {
            listaItems.add(criarGoogle(event));
        }

        Google[] vetorEventos = transformarListaParaVetor(listaItems);
        preencherVetorRecursivo(listaItems, vetorEventos, 0);


        mergeSort(0, vetorEventos.length, vetorEventos);
        return vetorEventos;
    }

    public Google[] transformarListaParaVetor(List<Google> lista) {
        Google[] vetorEventos = new Google[lista.size()];
        preencherVetorRecursivo(lista, vetorEventos, 0);
        return vetorEventos;
    }

    private void preencherVetorRecursivo(List<Google> lista, Google[] vetorEventos, int indice) {
        if (indice == lista.size()) {
            return;
        }

        vetorEventos[indice] = lista.get(indice);

        preencherVetorRecursivo(lista, vetorEventos, indice + 1);
    }


    public static void mergeSort(int inicio, int fim, Google[] v) {
        if (inicio < fim - 1) {
            int meio = (inicio + fim) / 2;
            mergeSort(inicio, meio, v);
            mergeSort(meio, fim, v);
            intercalar(inicio, meio, fim, v);
        }
    }

    public static void intercalar(int inicio, int meio, int fim, Google[] v) {
        int i = inicio, j = meio, k = 0;
        Google[] w = new Google[fim - inicio];

        while (i < meio && j < fim) {
            if (v[i].getSummary().compareToIgnoreCase(v[j].getSummary()) <= 0) {
                w[k++] = v[i++];
            } else {
                w[k++] = v[j++];
            }
        }

        while (i < meio) {
            w[k++] = v[i++];
        }

        while (j < fim) {
            w[k++] = v[j++];
        }

        for (i = inicio; i < fim; i++) {
            v[i] = w[i - inicio];
        }
    }

    public int pesquisaBinaria(Google[] v, String summary) {
        int indinf = 0;
        int indsup = v.length - 1;

        while (indinf <= indsup) {
            int meio = (indinf + indsup) / 2;

            int comparacao = summary.compareToIgnoreCase(v[meio].getSummary());

            if (comparacao == 0) {
                return meio;
            } else if (comparacao < 0) {
                indsup = meio - 1;
            } else {
                indinf = meio + 1;
            }
        }

        return -1;
    }
}
