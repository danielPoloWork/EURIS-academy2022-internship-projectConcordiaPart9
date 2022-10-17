package com.euris.academy2022.concordia.utils;

import org.json.JSONObject;

import static com.euris.academy2022.concordia.utils.constants.TrelloConstant.ID;

public class JsonUtil {

    public static String getIdFromResponse(String jsonTrelloResponse) {
        JSONObject object = new JSONObject(jsonTrelloResponse);
        return object.getString(ID);
    }
}
