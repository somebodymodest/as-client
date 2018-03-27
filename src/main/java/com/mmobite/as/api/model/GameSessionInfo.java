package com.mmobite.as.api.model;

public class GameSessionInfo {
    public int char_dbid = 0;
    public int account_dbid = 0;
    public String account_name = "";
    public String character_name = "";
    public String hwid = "";
    public int online_time = 0;

    public boolean isComplete()
    {
        if(account_name.isEmpty())
            return false;

        if(character_name.isEmpty())
            return false;

        if(char_dbid == 0)
            return false;

        return true;
    }
}
