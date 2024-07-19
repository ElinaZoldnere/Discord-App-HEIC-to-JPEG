package com.my.discordbot.service;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
class HttpService {

    @Autowired
    private OkHttpClient client;

    public Response uploadFile(File file, String cloudFunctionUrl, String secretToken) throws IOException {
        //OkHttpClient client = new OkHttpClient();
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(),
                        RequestBody.create(file, MediaType.parse("image/heic")))
                .build();
        Request request = new Request.Builder()
                .url(cloudFunctionUrl)
                .post(body)
                .addHeader("Authorization", "Bearer " + secretToken)
                .build();

        return client.newCall(request).execute();
    }

}
