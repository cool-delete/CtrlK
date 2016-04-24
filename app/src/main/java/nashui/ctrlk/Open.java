package nashui.ctrlk;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class Open extends IntentService {


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     * <p/>
     * //     * @param name Used to name the worker thread, important only for debugging.
     */
    public Open() {
        super("Close");
    }

    @Override
    protected void onHandleIntent(final Intent intent) {
        System.out.println("onStart");

        String clientId = "0";
        String clientSecret = "0";
        String numb = intent.getStringExtra("phone");
        String pu = intent.getStringExtra("pu");
        String userId = intent.getStringExtra("userId");
        String kid = intent.getStringExtra("kid");

        String html = TC.getAccessToken(numb, pu, clientId, clientSecret, userId, kid, "open");

        flash(html);

    }

    public void flash(final String S) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (S.contains("success")) {
            defaultSharedPreferences.edit().putString("key", "open").apply();
        }else {
            Handler handler = new Handler(getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {

                    Toast.makeText(getApplicationContext(), S, Toast.LENGTH_SHORT).show();
                }
            });
            defaultSharedPreferences.edit().putString("key", "close").apply();
        }
    }
}
