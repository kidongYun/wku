package com.example.yun.wku;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

/**
 * Created by yun on 2018. 8. 19..
 */

public class WKUAlarmBBSRoomImmortalService extends BroadcastReceiver {
    public static final String ACTION_RESTART_WKUALARMBBSROOMSERVICE = "ACTION_Restart.WKUAlarmBBSRoomService";

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals(ACTION_RESTART_WKUALARMBBSROOMSERVICE)) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(new Intent(context, WKUAlarmBBSRoomService.class));
            } else {
                context.startService(new Intent(context, WKUAlarmBBSRoomService.class));
            }
        }

        if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {

            Intent i = new Intent(context, WKUAlarmBBSRoomService.class);
            context.startService(i);
        }
    }
}
