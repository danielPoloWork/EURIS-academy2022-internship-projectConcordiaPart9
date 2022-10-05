package com.euris.academy2022.concordia.utils;

import com.euris.academy2022.concordia.utils.enums.HttpResponseType;
import org.springframework.beans.factory.annotation.Value;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class PingUtil {

    @Value("${trello.ip.server-one}")
    private static String trelloIpServerOne;

    @Value("${trello.ip.server-two}")
    private static String trelloIpServerTwo;

    @Value("${trello.ip.server-three}")
    private static String trelloIpServerThree;

    public static List<String> getServerList() {
        List<String> serverList = new ArrayList<>();
        serverList.add(trelloIpServerOne);
        serverList.add(trelloIpServerTwo);
        serverList.add(trelloIpServerThree);

        return serverList;
    }

    private static List<HttpResponseType> sendRequest() throws IOException {
        List<HttpResponseType> httpResponseList = new ArrayList<>();

        for (String s : getServerList()) {
            InetAddress trelloAddress = InetAddress.getByName(s);
            HttpResponseType httpResponse = trelloAddress.isReachable(5000)
                    ? HttpResponseType.OK
                    : HttpResponseType.A_TIME_OUT_OCCURRED;

            httpResponseList.add(httpResponse);
        }
        return httpResponseList;
    }

    public static Boolean isServerReached() throws IOException {

        boolean check = Boolean.FALSE;

        for (HttpResponseType hrt : sendRequest()) {
            if (hrt.equals(HttpResponseType.OK)) {
                check = Boolean.TRUE; break;
            }
        }
        return check;
    }
}