package com.flipkart.todo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by monish.kumar on 15/12/15.
 */

public class BootCompleteReceiver extends BroadcastReceiver {

    ServiceSchedular scheduler;
    public BootCompleteReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        scheduler = new ServiceSchedular(context);
        scheduler.startReporting();
    }
}
