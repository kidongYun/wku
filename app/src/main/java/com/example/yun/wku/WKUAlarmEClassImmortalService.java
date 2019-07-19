package com.example.yun.wku;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by yun on 2018. 8. 19..
 */

public class WKUAlarmEClassImmortalService extends BroadcastReceiver {
    public static final String ACTION_RESTART_WKUALARMECLASSSERVICE = "ACTION_Restart.WKUAlarmEClassService";

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals(ACTION_RESTART_WKUALARMECLASSSERVICE)) {

            Intent i = new Intent(context, WKUAlarmEClassService.class);
            context.startService(i);
        }

        if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {

            Intent i = new Intent(context, WKUAlarmEClassService.class);
            context.startService(i);
        }
    }
}
