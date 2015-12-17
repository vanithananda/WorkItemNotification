package tcs.org.bits.workitemnotification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.Toast;

/**
 * Created by vanitha on 12/14/2015.
 */
public class MissedCallReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        try
        {


            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);



            if(state.equals(TelephonyManager.EXTRA_STATE_RINGING))
            {
                Toast.makeText(context, "Phone Is Ringing", Toast.LENGTH_LONG).show();
                // Your Code
            }

            if(state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))
            {
                Toast.makeText(context, "Call Recieved", Toast.LENGTH_LONG).show();
                // Your Code
            }

            if (state.equals(TelephonyManager.EXTRA_STATE_IDLE))
            {

                Toast.makeText(context, "Phone Is Idle", Toast.LENGTH_LONG).show();
                try {
                    Bundle bundle = intent.getExtras();
                    String message = bundle.getString("alarm_message");

                    Intent newIntent = new Intent(context, PopupActivity.class);
                    newIntent.putExtra("alarm_message", message);
                    newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(newIntent);

                } catch (Exception e) {
                    e.printStackTrace();

                }
                // Your Code

            }
        }
        catch(Exception e)
        {
            //your custom message
        }

    }
}
