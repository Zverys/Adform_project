package com.example.zverys.to_do;

import java.util.ArrayList;

public final class StaticItems {
    public static String UserName;
    public static String[] categories;
    private static int count = 0;
    public static void setCategories(String string)
    {
            categories[count++] = string;

        }
    }
