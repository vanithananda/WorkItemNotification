package tcs.org.bits.workitemnotification;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {
    ArrayAdapter<String> adapter;
    ArrayList<String> listids = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ListView listView ;
        listView = (ListView) findViewById(R.id.listView);

        ArrayList<String> listItems = new ArrayList<>();
        listItems.add(0,"Vanitha_worklist");
        listItems.add(1,"Sanat_worklist");
        listItems.add(2,"Test1_worklist");
        listItems.add(3, "Test2_worklist");
        // Defined Array values to show in ListView

        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_single_choice,
                listItems);
        listView.setAdapter(adapter);
        //listView.setItemChecked(0, true);
        listids.add(0, "15555215556");

        if(getIntent() !=null) {
            String incomingnumber = getIntent().getStringExtra("incomingNumber");

            if (incomingnumber!=null && !incomingnumber.isEmpty()) {
                int i = listids.indexOf(incomingnumber);
                listView.setItemChecked(i, true);
            }
        }
        TelephonyManager TelephonyMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        TelephonyMgr.listen(new TeleListener(), PhoneStateListener.LISTEN_CALL_STATE);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode ==1){
            this.finish();
        }
    }

    public void setListAdapter(ArrayAdapter<String> listAdapter) {
        this.adapter = listAdapter;
    }

    class TeleListener extends PhoneStateListener {
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    // CALL_STATE_IDLE;
                    Toast.makeText(getApplicationContext(), "CALL_STATE_IDLE :" + incomingNumber,
                            Toast.LENGTH_LONG).show();

                    int i = listids.indexOf(incomingNumber);
                    if(i>=0) {

                        Intent newIntent = new Intent(getApplicationContext(), PopupActivity.class);
                        newIntent.putExtra("incomingnumber", incomingNumber);
                        newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivityForResult(newIntent, 1);
                        //stopService(getIntent());
                    }

                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    // CALL_STATE_OFFHOOK;
                    Toast.makeText(getApplicationContext(), "CALL_STATE_OFFHOOK",
                            Toast.LENGTH_LONG).show();
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    // CALL_STATE_RINGING

                    Toast.makeText(getApplicationContext(), incomingNumber,
                            Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), "CALL_STATE_RINGING",
                            Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }



    }}
