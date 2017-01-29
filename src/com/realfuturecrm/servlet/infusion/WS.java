package com.realfuturecrm.servlet.infusion;


import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URLEncoder;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class WS {

    private static HttpClient httpClient;
    private static ThreadLocal<GetMethod> getMethod = new ThreadLocal<GetMethod>();
    private static ThreadLocal<PostMethod> postMethod = new ThreadLocal<PostMethod>();
    private static ThreadLocal<DeleteMethod> deleteMethod = new ThreadLocal<DeleteMethod>();
    private static ThreadLocal<OptionsMethod> optionsMethod = new ThreadLocal<OptionsMethod>();
    private static ThreadLocal<TraceMethod> traceMethod = new ThreadLocal<TraceMethod>();
    private static ThreadLocal<HeadMethod> headMethod = new ThreadLocal<HeadMethod>();
    private static ThreadLocal<PutMethod> putMethod = new ThreadLocal<PutMethod>();

    public static void invocationFinally() {
        if (getMethod.get() != null) {
            getMethod.get().releaseConnection();
        }
        if (postMethod.get() != null) {
            postMethod.get().releaseConnection();
        }
        if (deleteMethod.get() != null) {
            deleteMethod.get().releaseConnection();
        }
        if (optionsMethod.get() != null) {
            optionsMethod.get().releaseConnection();
        }
        if (traceMethod.get() != null) {
            traceMethod.get().releaseConnection();
        }
        if (headMethod.get() != null) {
            headMethod.get().releaseConnection();
        }
        if (putMethod.get() != null) {
            putMethod.get().releaseConnection();
        }
    }

    static {
        MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
        httpClient = new HttpClient(connectionManager);
    }

    public static HttpResponse GET(String url, Object... params) {
        return GET(null, url, params);
    }

    public static HttpResponse GET(Map<String, Object> headers, String url, Object... params) {
        url = params.length > 0 ? String.format(url, params) : url;
        if (getMethod.get() != null) {
            getMethod.get().releaseConnection();
        }
        getMethod.set(new GetMethod(url));
        return METHOD(getMethod.get(), headers, url);
    }

    public static HttpResponse PUT(String url, final String mimeType, String body) {
        return PUT(new HashMap<String, Object>() {{
            put("Content-Type", mimeType);
        }}, url, body);
    }

    public static HttpResponse PUT(Map<String, Object> headers, String url, String body) {
        if (putMethod.get() != null) {
            putMethod.get().releaseConnection();
        }
        putMethod.set(new PutMethod(url));
        try {
            putMethod.get().setRequestEntity(new StringRequestEntity(body, "application/x-www-form-urlencoded", "utf-8"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return METHOD(putMethod.get(), headers, url);
    }

    public static HttpResponse POST(String url, AbstractMap.SimpleEntry<String, String>... data) {
        String body = "";
        for (AbstractMap.SimpleEntry<String, String> entry : data) {
            try {
                body += (body.length() > 0 ? "&" : "") + entry.getKey() + "=" + (entry.getValue() == null ? "" : URLEncoder.encode(entry.getValue(), "UTF-8"));
            } catch (Exception e) {
                // ignore encoding exception
            }
        }
        return POST(url, "application/x-www-form-urlencoded", body);
    }

    public static HttpResponse POST(String url, final String mimeType, String body) {
        return POST(new HashMap<String, Object>() {{
            put("Content-Type", mimeType);
        }}, url, body);
    }

    public static HttpResponse POST(Map<String, Object> headers, String url, String body) {
        if (postMethod.get() != null) {
            postMethod.get().releaseConnection();
        }
        postMethod.set(new PostMethod(url));
        try {
            postMethod.get().setRequestEntity(new StringRequestEntity(body, "application/x-www-form-urlencoded", "utf-8"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return METHOD(postMethod.get(), headers, url);
    }

    public static HttpResponse DELETE(String url) {
        return DELETE(null, url);
    }

    public static HttpResponse DELETE(Map<String, Object> headers, String url) {
        if (deleteMethod.get() != null) {
            deleteMethod.get().releaseConnection();
        }
        deleteMethod.set(new DeleteMethod(url));
        return METHOD(deleteMethod.get(), headers, url);
    }

    public static HttpResponse HEAD(String url, Object... params) {
        return HEAD(null, url, params);
    }

    public static HttpResponse HEAD(Map<String, Object> headers, String url, Object... params) {
        url = String.format(url, params);
        if (headMethod.get() != null) {
            headMethod.get().releaseConnection();
        }
        headMethod.set(new HeadMethod(url));
        return METHOD(headMethod.get(), headers, url);
    }

    private static HttpResponse METHOD(HttpMethodBase method, Map<String, Object> headers, String url) {
        try {
            if (headers != null) {
                for (Map.Entry<String, Object> entry : headers.entrySet()) {
                    method.addRequestHeader(entry.getKey(), entry.getValue() + "");
                }
            }
            setCredentials(url);
            httpClient.executeMethod(method);
            return new HttpResponse(method);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void setCredentials(String url) {
        Pattern BASIC = Pattern.compile("https?://(\\w+):(\\w+)@.+", Pattern.CASE_INSENSITIVE);
        if (url.indexOf('@') != -1) {
            Matcher m = BASIC.matcher(url);
            if (m.matches()) {
                String username = m.group(1);
                String password = m.group(2);
                httpClient.getState().setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
                return;
            }
        }
        httpClient.getState().setCredentials(AuthScope.ANY, null);
    }

//    public static HttpResponse TRACE(String url, Object... params) {
//        return TRACE(null, url, params);
//    }
//
//    public static HttpResponse TRACE(Map<String, String> headers, String url, Object... params) {
//        url = String.format(url, params);
//        if (traceMethod.get() != null) {
//            traceMethod.get().releaseConnection();
//        }
//        traceMethod.set(new TraceMethod(url));
//
//        try {
//            if (headers != null) {
//                for (String key : headers.keySet()) {
//                    traceMethod.get().addRequestHeader(key, headers.get(key) + "");
//                }
//            }
//            httpClient.executeMethod(traceMethod.get());
//            return new HttpResponse(traceMethod.get());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public static HttpResponse OPTIONS(String url, Object... params) {
//        return OPTIONS(null, url, params);
//    }
//
//    public static HttpResponse OPTIONS(Map<String, String> headers, String url, Object... params) {
//        url = String.format(url, params);
//        if (optionsMethod.get() != null) {
//            optionsMethod.get().releaseConnection();
//        }
//        optionsMethod.set(new OptionsMethod(url));
//
//        try {
//            if (headers != null) {
//                for (String key : headers.keySet()) {
//                    optionsMethod.get().addRequestHeader(key, headers.get(key) + "");
//                }
//            }
//            httpClient.executeMethod(optionsMethod.get());
//            return new HttpResponse(optionsMethod.get());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

    public static String encode(String part) {
        try {
            return URLEncoder.encode(part, "utf-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reponse d'un Service Web.
     */
    public static class HttpResponse {

        private HttpMethodBase methodBase;

        public HttpResponse(HttpMethodBase method) {
            this.methodBase = method;
        }

        public HttpResponse assertStatus(int status) {
            if (getStatus() == null || !getStatus().equals(status)) {
                try {
                    System.out.println("Response: " + methodBase.getResponseBodyAsString());
                } catch (IOException e) {
                    // ignore
                    e.printStackTrace();
                }
                throw new RuntimeException("Expected status '" + status + "', actual is '" + getStatus() + "'");
            }
            return this;
        }

        public Integer getStatus() {
            return this.methodBase.getStatusCode();
        }

        public Document getXml() {
            try {
                String xml = methodBase.getResponseBodyAsString();
                StringReader reader = new StringReader(xml);
                return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(reader));
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                methodBase.releaseConnection();
            }
        }

        /*
         * Parses the xml with the given encoding.
         */
        public Document getXml(String encoding) {
            try {
                InputSource source = new InputSource(methodBase.getResponseBodyAsStream());
                source.setEncoding(encoding);
                Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(source);
                return doc;
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                methodBase.releaseConnection();
            }
        }

        public String getString() {
            try {
                return methodBase.getResponseBodyAsString();
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                methodBase.releaseConnection();
            }
        }

        public InputStream getStream() {
            try {
                return new ConnectionReleaserStream(methodBase.getResponseBodyAsStream(), methodBase);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public String getContentType() {
            try {
                methodBase.getResponseBodyAsString(); // without this line will return body = null
                if (methodBase.getResponseHeader("content-type") != null) {
                    return methodBase.getResponseHeader("content-type").getValue();
                }
                return "";
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                methodBase.releaseConnection();
            }
        }

        public Object getJSON() {
            try {
                String json = methodBase.getResponseBodyAsString();
                return new JSONParser().parse(json);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                methodBase.releaseConnection();
            }
        }

        public JSONObject getJSONObject() {
            try {
                String json = methodBase.getResponseBodyAsString();
                return (JSONObject)new JSONParser().parse(json);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                methodBase.releaseConnection();
            }
        }

        static class ConnectionReleaserStream extends InputStream {

            private InputStream wrapped;
            private HttpMethodBase method;

            public ConnectionReleaserStream(InputStream wrapped, HttpMethodBase method) {
                this.wrapped = wrapped;
                this.method = method;
            }

            @Override
            public int read() throws IOException {
                return this.wrapped.read();
            }

            @Override
            public int read(byte[] arg0) throws IOException {
                return this.wrapped.read(arg0);
            }

            @Override
            public synchronized void mark(int arg0) {
                this.wrapped.mark(arg0);
            }

            @Override
            public int read(byte[] arg0, int arg1, int arg2) throws IOException {
                return this.wrapped.read(arg0, arg1, arg2);
            }

            @Override
            public synchronized void reset() throws IOException {
                this.wrapped.reset();
            }

            @Override
            public long skip(long arg0) throws IOException {
                return this.wrapped.skip(arg0);
            }

            @Override
            public int available() throws IOException {
                return this.wrapped.available();
            }

            @Override
            public boolean markSupported() {
                return this.wrapped.markSupported();
            }

            @Override
            public void close() throws IOException {
                try {
                    this.wrapped.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    if (method != null) {
                        method.releaseConnection();
                    }
                }

            }
        }
    }
}