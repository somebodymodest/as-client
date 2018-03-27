package com.mmobite.as;

import com.mmobite.as.api.AntispamAPI;
import com.mmobite.as.api.model.NetworkSessionInfo;

public class Main {
    public static void main(String... args) throws Exception {
        AntispamAPI.init(273);
        AntispamAPI.openGameSession(new NetworkSessionInfo());
    }
}
