package com.retaileragrsmb.common;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.retaileragrsmb.R;

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

    /**
     * Used to get a boolean value stored in SharedPreferences.
     *
     * @param context ,application context
     * @param pref    a boolean name with which the preference would be stored.
     * @param def     a default boolean value , nothing is stored in preference
     * @return boolean
     */
    public static String getPrefrence(Context context, String pref, String def) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(pref, def);
    }

    /**
     * Used to get a boolean value stored in SharedPreferences.
     *
     * @param context ,application context
     * @param pref    a boolean name with which the preference would be stored
     * @param def     a boolean value to be stored in preference
     */
    public static void setPrefrence(Context context, String pref, String def) {
        SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(context).edit();
        e.putString(pref, def);
        e.commit();
    }


//    {
//        "jsonrpc": "2.0",
//            "method": "query",
//            "params": {
//        "type": 1,
//                "chaincodeID": {
//            "name":  "8171b74a650862f32a027dfa11d8859aa9d167cdb29847f3f76eaebea88165ef099a410d79add751ca189cb4c7946e69b152f89c3e3e696fc38aaff1b8934ddf"
//        },
//        "ctorMsg": {
//            "function": "read",
//                    "args": [
//            "Distributer"
//             ]
//        },
//        "secureContext": "WebAppAdmin"
//    },
//        "id": 1
//    }

    public static JsonObject getAmountRequestBody(){
        JsonObject rootObject = new JsonObject();

        rootObject.addProperty("jsonrpc", "2.0");
        rootObject.addProperty("method", "query");
        rootObject.addProperty("id", 1);

        JsonObject paramsObject = new JsonObject();
        paramsObject.addProperty("type", 1);
        paramsObject.addProperty("secureContext", "WebAppAdmin");

        JsonObject chaincodeIDObject = new JsonObject();
        chaincodeIDObject.addProperty("name", "8171b74a650862f32a027dfa11d8859aa9d167cdb29847f3f76eaebea88165ef099a410d79add751ca189cb4c7946e69b152f89c3e3e696fc38aaff1b8934ddf");

        JsonObject ctorMsgObject = new JsonObject();
        ctorMsgObject.addProperty("function", "read");
        JsonArray array = new JsonArray();
        array.add("Distributer");

        ctorMsgObject.add("args", array);

        paramsObject.add("chaincodeID", chaincodeIDObject);
        paramsObject.add("ctorMsg", ctorMsgObject);


        rootObject.add("params", paramsObject);

        return  rootObject;
    }

    public static JsonObject getTransferAmountRequestBody(String amount){
        JsonObject rootObject = new JsonObject();

        rootObject.addProperty("jsonrpc", "2.0");
        rootObject.addProperty("method", "invoke");
        rootObject.addProperty("id", 3);

        JsonObject paramsObject = new JsonObject();
        paramsObject.addProperty("type", 1);
        paramsObject.addProperty("secureContext", "user_type1_0");

        JsonObject chaincodeIDObject = new JsonObject();
        chaincodeIDObject.addProperty("name", "8171b74a650862f32a027dfa11d8859aa9d167cdb29847f3f76eaebea88165ef099a410d79add751ca189cb4c7946e69b152f89c3e3e696fc38aaff1b8934ddf");

        JsonObject ctorMsgObject = new JsonObject();
        ctorMsgObject.addProperty("function", "write");
        JsonArray array = new JsonArray();
        array.add("Idea_Products");
        array.add("Distributer");
        array.add(amount);

        ctorMsgObject.add("args", array);

        paramsObject.add("chaincodeID", chaincodeIDObject);
        paramsObject.add("ctorMsg", ctorMsgObject);


        rootObject.add("params", paramsObject);

        return  rootObject;
    }

    public static void showAlert(final Context activity, String title, String alertMsg){
        if (activity instanceof Activity && ((Activity) activity).isFinishing()) {
            return;
        }
        final AlertDialog.Builder alert = new AlertDialog.Builder(activity, R.style.AppDialogTheme);
        alert.setTitle(title);
        alert.setCancelable(true);
        alert.setMessage(alertMsg);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

}
