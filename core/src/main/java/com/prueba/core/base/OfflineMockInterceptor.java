package com.prueba.core.base;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class OfflineMockInterceptor implements Interceptor {

    private static final MediaType MEDIA_JSON = MediaType.parse("application/json");
    //private Context mContext;

//    public OfflineMockInterceptor(Context context) {
//        mContext = context;
//    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        /* http://sample.com/hello will return "/hello" */
        /*
         *  Cogemos los tres primeros elementos del path y el ultimo elemento. Por ejemplo
         *
         *  /api/v1/cryptocurrency-voucher/{voucherCode}/validate
         *
         *  obtendriamos
         *
         *  /api/v1/cryptocurrency-voucher/validate.json
         */
        List<String> pathElements = request.url().pathSegments();
        List<String> newPathElements = new ArrayList<String>(pathElements.subList(0, 3));
        newPathElements.add(pathElements.get(pathElements.size()-1)+".json");
        String path = StringUtils.join(newPathElements, "/");


        /* I put a 'hello' file in debug/assets/mockData */
        //InputStream stream = mContext.getAssets().open("mockData" + path);
        InputStream stream = this.getClass()
                                    .getClassLoader()
                                    .getResourceAsStream("mockData/" + path);

        /* Just read the file. */
        if( stream != null) {
            String json = parseStream(stream);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Response response = new Response.Builder()
                    .body(ResponseBody.create(MEDIA_JSON, json))
                    .request(chain.request())
                    .message("")
                    .protocol(Protocol.HTTP_2)
                    .code(200)
                    .build();

            return response;
        } else {
            Response response = new Response.Builder()
                    .request(chain.request())
                    .message("Error")
                    .protocol(Protocol.HTTP_2)
                    .code(400)
                    .build();
            return response;
        }
    }

    private String parseStream(InputStream stream) throws IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader in = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        String line;
        while ((line = in.readLine()) != null) {
            builder.append(line);
        }
        in.close();
        return builder.toString();
    }
}