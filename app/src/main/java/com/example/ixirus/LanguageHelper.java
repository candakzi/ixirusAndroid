package com.example.ixirus;

import java.util.Locale;

public class LanguageHelper {
    public String getLanguage()
    {
        String langId = "0";
        String language = Locale.getDefault().getDisplayLanguage();
        if (!language.equals("English"))
            langId = "1";
        return langId;
    }
}
