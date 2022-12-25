package com.example.musicsystem;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.actions.api.App;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;

public class MusicAWSHandler implements RequestStreamHandler {
    private final App musicApplication = new MusicApplication();
    private final JSONParser parser = new JSONParser();


    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        JSONObject awsResponse = new JSONObject();
        LambdaLogger logger = context.getLogger();

        try {
            JSONObject awsRequest = (JSONObject) parser.parse(reader);
            JSONObject headers = (JSONObject) awsRequest.get("headers");
            String body = (String) awsRequest.get("body");
            logger.log("AWS request = {} " + awsRequest);
            logger.log("AWS request body = {}" + body);

            musicApplication.handleRequest(body, headers)
                    .thenAccept((webhookResponseJson) -> {
                        logger.log("Generated json = " + webhookResponseJson);
                        JSONObject responseHeaders = new JSONObject();
                        responseHeaders.put("Content-Type", "application/json");
                        awsResponse.put("statusCode", "200");
                        awsResponse.put("headers", responseHeaders);
                        awsResponse.put("body", webhookResponseJson);
                        writeResponse(outputStream, awsResponse);
                    });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void writeResponse(OutputStream outputStream, JSONObject responseJson) {
        try {
            OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
            writer.write(responseJson.toJSONString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
