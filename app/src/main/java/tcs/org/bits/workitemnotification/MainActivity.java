package tcs.org.bits.workitemnotification;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {
    ArrayAdapter<String> adapter;
    static ArrayList<String>  listids = new ArrayList<>();
    Button b1,b2;
    EditText ed1,ed2;
   static ArrayList<String> listItems = new ArrayList<>();

    static {

        listids.add(0, "15555215556");
        listItems.add(0, "Vanitha_WorkItem");
        listItems.add(1, "Sanat_WorkItem");
        listItems.add(2, "WorkItem2");
        listItems.add(3, "WorkItem3");
        listItems.add(4, "WorkItem4");
        listItems.add(5, "WorkItem5");

    }
    TextView tx1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getIntent()!=null) {

                // Defined Array values to show in ListView
            ListView listView;
            adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_single_choice,
                    listItems);

            String incomingnumber = getIntent().getStringExtra("incomingNumber");

            if (incomingnumber != null && !incomingnumber.isEmpty()) {
                setContentView(R.layout.activity_main);
                listView = (ListView) findViewById(R.id.listView);
                listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);


                listView.setAdapter(adapter);
                //listView.setItemChecked(0, true);
                int i = listids.indexOf(incomingnumber);
                listView.setItemChecked(i, true);
            } else {

                setContentView(R.layout.login_page);

                b1 = (Button) findViewById(R.id.button);
                ed1 = (EditText) findViewById(R.id.editText);
                ed2 = (EditText) findViewById(R.id.editText2);

                b2 = (Button) findViewById(R.id.button2);


                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setContentView(R.layout.activity_main);
                        ListView listView;
                        listView = (ListView) findViewById(R.id.listView);
                        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

                        listView.setAdapter(adapter);

                    }
                });

                b2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
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
               /* case TelephonyManager.CALL_STATE_OFFHOOK:
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
                    break;*/
                default:
                    break;
            }
        }



    }}
