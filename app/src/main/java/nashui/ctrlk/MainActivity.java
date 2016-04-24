package nashui.ctrlk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private EditText phone;
    private EditText pu;
    private EditText userId;
    private EditText kid;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        phone = (EditText) findViewById(R.id.phone);
        pu = (EditText) findViewById(R.id.pu);
        userId = (EditText) findViewById(R.id.userid);
        kid = (EditText) findViewById(R.id.key);
        phone.setText(sharedPreferences.getString("phone", "0"));
        pu.setText(sharedPreferences.getString("pu", "0"));
        userId.setText(sharedPreferences.getString("userId", "0"));
        kid.setText(sharedPreferences.getString("kid", "0-8cf9-0-0-0"));
        Switch aSwitch = (Switch) findViewById(R.id.switch1);
        if (aSwitch != null) {
            if ("close".equals(sharedPreferences.getString("key", "close")))
                aSwitch.setChecked(false);
            else aSwitch.setChecked(true);
            aSwitch.setOnCheckedChangeListener(this);
        }
        Button button = (Button) findViewById(R.id.button);
        if (button != null) {
            button.setTag(1);
            button.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch ((int) v.getTag()) {
            case 1:
                sharedPreferences.edit().putString("phone", phone.getText().toString()).apply();
                sharedPreferences.edit().putString("pu", pu.getText().toString()).apply();
                sharedPreferences.edit().putString("userId", userId.getText().toString()).apply();
                sharedPreferences.edit().putString("kid", kid.getText().toString()).apply();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Ctr(isChecked);
    }

    private void Ctr(boolean isChecked) {
        if (isChecked) {
            sharedPreferences.edit().putString("key", "open").apply();
            Intent openIntent = new Intent(this, Open.class);
            openIntent.putExtra("phone", phone.getText().toString());
            openIntent.putExtra("pu", pu.getText().toString());
            openIntent.putExtra("userId", userId.getText().toString());
            openIntent.putExtra("kid", kid.getText().toString());
            startService(openIntent);
        } else {
            sharedPreferences.edit().putString("key", "close").apply();
            Intent closeIntent = new Intent(this, Close.class);
            closeIntent.putExtra("phone", phone.getText().toString());
            closeIntent.putExtra("pu", pu.getText().toString());
            closeIntent.putExtra("userId", userId.getText().toString());
            closeIntent.putExtra("kid", kid.getText().toString());
            startService(closeIntent);
        }
    }


}
