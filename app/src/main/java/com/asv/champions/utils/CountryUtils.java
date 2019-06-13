package com.asv.champions.utils;

import android.content.Context;

import com.asv.champions.model.CountrySorted;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CountryUtils {
    public static final String[] IDIOMAS_ACTUALES = {"en","es"};
    public static String PATH_COUNTRIES = "countries/";
    public static String DEFAULT_LANGUAGE = "en";

    public static List<CountrySorted>  loadCountriesFromJSON(Context context){
        List<CountrySorted> countries = new ArrayList<>();
        try {
            InputStream stream = context.getClass()
                    .getClassLoader()
                    .getResourceAsStream(getCurrentLanguagePath());
            StringBuilder builder = new StringBuilder();
            BufferedReader in = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                builder.append(line);
            }
            in.close();


            String json = builder.toString();
            Gson gson = new Gson();
            Type listType = new TypeToken<List<CountrySorted>>(){}.getType();
            countries = gson.fromJson(json, listType);

        }
        catch (IOException ex){

        }
        return countries;
    }
    public static String getCurrentLanguagePath(){
        String idioma = DEFAULT_LANGUAGE;
        String idioma_local_usuario = Locale.getDefault().getLanguage();
        for (int i = 0; i< IDIOMAS_ACTUALES.length;i++){
            if (idioma_local_usuario.equals(new Locale(IDIOMAS_ACTUALES[i]).getLanguage())){
                idioma = IDIOMAS_ACTUALES[i];
            }
        }

        String result = PATH_COUNTRIES+idioma+".json";
        return result;

    }


}
