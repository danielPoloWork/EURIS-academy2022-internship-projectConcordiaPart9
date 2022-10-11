package com.euris.academy2022.concordia.utils;

import com.euris.academy2022.concordia.utils.enums.HttpResponseType;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

import static com.euris.academy2022.concordia.utils.constants.TrelloConstant.*;

public class PingUtil {

    public static List<String> getServerList() {
        List<String> serverList = new ArrayList<>();
        serverList.add(IP_1);
        serverList.add(IP_2);
        serverList.add(IP_3);

        return serverList;
    }

    private static List<HttpResponseType> sendRequest() throws IOException {
        List<HttpResponseType> httpResponseList = new ArrayList<>();

        for (String s : getServerList()) {
            InetAddress trelloAddress = InetAddress.getByName(s);
            HttpResponseType httpResponse = trelloAddress.isReachable(5000)
                    ? HttpResponseType.OK
                    : HttpResponseType.WEB_SERVER_IS_UNREACHABLE;

            httpResponseList.add(httpResponse);
        }
        return httpResponseList;
    }

    public static Boolean isInetAddressReached() throws IOException {

        for (HttpResponseType hrt : sendRequest())
            if (hrt.equals(HttpResponseType.OK)) {
                return Boolean.TRUE;
            }
        return Boolean.FALSE;
    }

    public static Boolean isSocketReached(String host, int port, int timeout) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), timeout);
            return Boolean.TRUE;
        } catch (IOException e) {
            return Boolean.FALSE;
            // Either timeout or unreachable or failed DNS lookup.
        }
    }

    /**
     * Pings an HTTP URL. This effectively sends a HEAD request and returns <code>TRUE</code> if the response code is in
     * the 200-399 range.
     * @var trelloUrl The HTTP URL to be pinged.
     * @var timeout The timeout in millis for both the connection timeout and the response read timeout. Note that
     * the total timeout is effectively two times the given timeout.
     * @return <code>TRUE</code> if the given HTTP URL has returned response code 200-399 on a HEAD request within the
     * given timeout, otherwise <code>FALSE</code>.
     */
    public static Boolean isHttpURLConnectionReached(String url) {
        String cleaned = url.replaceFirst("^https", "http");
        // Otherwise an exception may be thrown on invalid SSL certificates.
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(cleaned).openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            return (200 <= responseCode && responseCode <= 399);
        } catch (IOException exception) {
            return Boolean.FALSE;
        }
    }
}