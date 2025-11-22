package com.juanfran.accountsmanager.services;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.v1.Customsearch;
import com.google.api.services.customsearch.v1.model.Result;
import com.google.api.services.customsearch.v1.model.Search;
import com.juanfran.accountsmanager.di.OrchestratorProyectDependences;

import java.util.List;

public final class GETCustomSearchsServiceProvider {

    private GETCustomSearchsServiceProvider() {}
    private static final String APPLICATION_NAME = "AccountsManager";
    private static final String API_KEY = "YOUR_GOOGLE_API_KEY";
    private static final String SEARCH_ENGINE_ID = "7726df04956eb42d9";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /**
     * Este método a través de la información
     * que recibe el método utilizamos la API
     * de Google Custom Search para buscar
     * páginas webs relacionadas posteriormente
     * obtiene los resultados de la búsqueda
     * y los devuelve
     * @param webPage
     * @return
     */
    public static List<Result> getResults(String webPage) {
        Customsearch customsearch = null;
        List<Result> items = null;

        try{
            customsearch = new Customsearch.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    JSON_FACTORY,
                    null)
                    .setGoogleClientRequestInitializer(
                            request -> request.setDisableGZipContent(true))
                    .setApplicationName(APPLICATION_NAME)
                    .build();

            Customsearch.Cse.List list = customsearch.cse().list()
                    .setQ(webPage)
                    .setCx(SEARCH_ENGINE_ID)
                    .setKey(API_KEY);

            Search results = list.execute();
            items = results.getItems();
        }catch(Exception e){
            OrchestratorProyectDependences.getLogger().error(e.getMessage());
        }

        return items;
    }

}
