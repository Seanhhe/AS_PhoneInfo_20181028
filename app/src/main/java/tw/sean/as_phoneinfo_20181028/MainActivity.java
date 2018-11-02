package tw.sean.as_phoneinfo_20181028;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private TelephonyManager tmgr;
    private AccountManager amgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS,
                    Manifest.permission.},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);

        }else{
            init();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        init();
    }

    public void init() {
        tmgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String num = tmgr.getLine1Number();//中國的手機看得到電話號碼,台灣看不到
        Log.v("brad", "num: " + num);
        String did = tmgr.getDeviceId();// IMEI (綁定硬體手機用這招)
        Log.v("brad", "device id: " + did);
        String imsi = tmgr.getSubscriberId(); // IMSI (SIM卡識別; 綁定SIM卡用這招; MNC table)
        Log.v("brad", "device id: " + did);

        //取得用戶帳號
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N){
            amgr = (AccountManager)getSystemService(Context.ACCOUNT_SERVICE);

            Account[] accounts = amgr.getAccounts();
            for (Account account : accounts) {
                Log.v("brad", account.name + " : " + account.type);
            }
        }else{
            amgr = AccountManager.get(this);// Android 8之後的版本
        }


    }

}
// Build > Build Bundle / APK > Build APK (檔案在)