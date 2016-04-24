package nashui.ctrlk;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class Close extends IntentService {


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     * <p/>
     * //     * @param name Used to name the worker thread, important only for debugging.
     */
    public Close() {
        super("Close");
    }


//    myHandler handler = new my(){
//        public void handleMessage ( Message msg )
//        {
//            super.handleMessage(msg);
//            Bundle data = msg.getData();
//            String Data=data.getString("value") + "";


//输出反馈
    //flash(Data);
//            flash("关");


//        }
//    };


    public void flash(final String S) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (S.contains("success")) {
            defaultSharedPreferences.edit().putString("key", "close").apply();
        }else {
            Handler handler = new Handler(getMainLooper());

            handler.post(new Runnable() {
                @Override
                public void run() {

                    Toast.makeText(getApplicationContext(), S, Toast.LENGTH_SHORT).show();
                }
            });
            defaultSharedPreferences.edit().putString("key", "open").apply();
        }
    }

    @Override
    protected void onHandleIntent(final Intent intent) {
        String clientId = "0";
        String clientSecret = "0";
        String numb = intent.getStringExtra("phone");
        String pu = intent.getStringExtra("pu");
        String userId = intent.getStringExtra("userId");
        String kid = intent.getStringExtra("kid");

        String html = TC.getAccessToken(numb, pu, clientId, clientSecret, userId, kid, "close");

        flash(html);

    }
}
