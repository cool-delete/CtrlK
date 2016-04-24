package nashui.ctrlk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

            SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (intent.getAction().equals("android.ctrlK.open")) {
            defaultSharedPreferences.edit().putString("key", "open").apply();
            Intent openIntent = new Intent(context, Open.class);
            openIntent.putExtra("phone", defaultSharedPreferences.getString("phone", "0"));
            openIntent.putExtra("pu", defaultSharedPreferences.getString("pu", "0"));
            openIntent.putExtra("userId", defaultSharedPreferences.getString("userId", "0"));
            openIntent.putExtra("kid", defaultSharedPreferences.getString("kid", "0"));
            context.startService(openIntent);
        } else if (intent.getAction().equals("android.ctrlK.close")) {
            defaultSharedPreferences.edit().putString("key", "close").apply();
            Intent intentServer = new Intent(context, Close.class);
            intentServer.putExtra("phone", defaultSharedPreferences.getString("phone", "0"));
            intentServer.putExtra("pu", defaultSharedPreferences.getString("pu", "0"));
            intentServer.putExtra("userId", defaultSharedPreferences.getString("userId", "0"));
            intentServer.putExtra("kid", defaultSharedPreferences.getString("kid", "0"));
            context.startService(intentServer);
        }

    }
}
