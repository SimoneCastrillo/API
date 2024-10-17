package buffet.app_web.service;


import buffet.app_web.config.Google;
import buffet.app_web.entities.Orcamento;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.stereotype.Service;

import java.io.*;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class GoogleApiService {

    private static final String APPLICATION_NAME = "Buffet";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    private static final String TOKENS_DIRECTORY_PATH = "AIzaSyAC4lVDu_Uz3E3de29ziN4uYYA8dFWie5c";

    private static final List<String> SCOPES =
            Collections.singletonList(CalendarScopes.CALENDAR);

    private Calendar getCalendarService() throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        return new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
            throws IOException {

        InputStream in = GoogleApiService.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");

        return credential;
    }


    public void criarEvento(Orcamento orcamento) {
        try {
            Google google = new Google();

            google.setStartDateTime(orcamento.getDataEvento().atTime(orcamento.getInicio()));
            google.setEndDateTime(orcamento.getDataEvento().atTime(orcamento.getFim()));

            LocalDateTime startDateTime = google.getStartDateTime();
            LocalDateTime endDateTime = google.getEndDateTime();

            DateTime startGoogleDateTime = new DateTime(ZonedDateTime.of(startDateTime, ZoneId.systemDefault()).toInstant().toEpochMilli());
            DateTime endGoogleDateTime = new DateTime(ZonedDateTime.of(endDateTime, ZoneId.systemDefault()).toInstant().toEpochMilli());

            EventDateTime start = new EventDateTime().setDateTime(startGoogleDateTime);
            EventDateTime end = new EventDateTime().setDateTime(endGoogleDateTime);

            Calendar service = getCalendarService();
            Event event = new Event()
                    .setSummary(orcamento.getTipoEvento().getNome())
                    .setDescription(
                            "Orçamento de: " + orcamento.getUsuario().getNome() + "\n" +
                                    "Telefone: " + orcamento.getUsuario().getTelefone() + "\n" +
                                    "Dados do Orçamento:\n" +
                                    "- Número de Convidados: " + orcamento.getQtdConvidados() + "\n" +
                                    "- Tipo de Evento: " + orcamento.getTipoEvento().getNome() + "\n" +
                                    "- Sugestão: " + orcamento.getSugestao()
                    )
                    .setStart(start)
                    .setEnd(end);

            service.events().insert("primary", event).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

