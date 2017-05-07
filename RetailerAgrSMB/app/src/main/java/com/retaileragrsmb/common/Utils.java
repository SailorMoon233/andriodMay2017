package com.retaileragrsmb.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by hitesh on 5/6/2017.
 */

public class Utils {

    /**
     * Used to get a boolean value stored in SharedPreferences.
     *
     * @param context ,application context
     * @param pref    a boolean name with which the preference would be stored.
     * @param def     a default boolean value , nothing is stored in preference
     * @return boolean
     */
    public static boolean getPrefrence(Context context, String pref, boolean def) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(pref, def);
    }

    /**
     * Used to get a boolean value stored in SharedPreferences.
     *
     * @param context ,application context
     * @param pref    a boolean name with which the preference would be stored
     * @param def     a boolean value to be stored in preference
     */
    public static void setPrefrence(Context context, String pref, boolean def) {
        SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(context).edit();
        e.putBoolean(pref, def);
        e.commit();
    }

}
