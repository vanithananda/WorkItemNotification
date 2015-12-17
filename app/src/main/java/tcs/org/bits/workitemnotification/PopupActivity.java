package tcs.org.bits.workitemnotification;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by vanitha on 12/14/2015.
 */
public class PopupActivity extends Activity{
    @Override
    public Intent getIntent() {
        return super.getIntent();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sayHello(new View(getApplicationContext()), getIntent().getStringExtra("incomingnumber"));
    }

    public void sayHello(View view, final String incomingNumber) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setMessage("You got a missed call from " + incomingNumber);
        builder.setTitle("Missed Task Notificatoin");
        builder.setPositiveButton("Open App", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getPackageManager().getLaunchIntentForPackage("tcs.org.bits.workitemnotification"));
                intent.putExtra("incomingNumber",incomingNumber);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Ignore", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            setResult(1,null);
                finish();

               // getApplicationContext().stopService(getIntent());
               // getApplicationContext().stopService(getParentActivityIntent());
                //TextureView textureView = new TextureView(getApplicationContext());
                //Canvas canvas = new Canvas();
                //canvas.clipRect(3, 3, 3, 3);
                //textureView.draw(canvas);
            }
        });

        android.support.v7.app.AlertDialog alert11 = builder.create();
        alert11.show();

    }
}
