package com.example.musicsystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@WebServlet(name = "test", value = "/")

public class MusicServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MusicServlet.class);
    private final MusicApplication musicApplication = new MusicApplication();

    //for Post
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String body = req.getReader().lines().collect(Collectors.joining());
        LOG.info("doPost, body = {}", body);

        try {
            String jsonResponse = musicApplication.handleRequest(body, getHeadersMap(req)).get();
            LOG.info("Generated json = {}", jsonResponse);
            resp.setContentType("application/json");
            writeResponse(resp, jsonResponse);
        } catch (InterruptedException e) {
            handleError(resp, e);
        } catch (ExecutionException e) {
            handleError(resp, e);
        }
    }

    //for Get
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter()
                .println(
                        "ActionsServlet is listening but requires valid POST request to respond with Action response.");
    }

    private void writeResponse(HttpServletResponse resp, String jsonResponse) {
        try {
            resp.getWriter().write(jsonResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void handleError(HttpServletResponse res, Throwable throwable) {
        try {
            throwable.printStackTrace();
            LOG.error("Error in App.handleRequest ", throwable);
            res.getWriter().write("Error handling the intent - " + throwable.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String, String> getHeadersMap(HttpServletRequest req) {
        Map<String, String> map = new HashMap<>();
        Enumeration headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = req.getHeader(key);
            map.put(key, value);
        }
        return map;
    }
}