package com.mmobite.as;

import com.mmobite.as.api.AntispamAPI;
import com.mmobite.as.api.model.NetworkSessionInfo;

public class Main {
    public static void main(String... args) throws Exception {
        AntispamAPI.init("antispam/client.properties", 273);
        AntispamAPI.openGameSession(new NetworkSessionInfo());
        AntispamAPI.openGameSession(new NetworkSessionInfo());
        AntispamAPI.openGameSession(new NetworkSessionInfo());
        AntispamAPI.openGameSession(new NetworkSessionInfo());
    }
}
