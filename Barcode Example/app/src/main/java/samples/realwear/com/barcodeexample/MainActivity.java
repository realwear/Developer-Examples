package samples.realwear.com.barcodeexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    public static final int RequestCode = 2232;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onScanCode(View v){

        String ACTION_BARCODE = "com.wearnext.intent.action.barcodescann";
        String EXTRA_METHOD = "get_barcode";

        Intent intent = new Intent(ACTION_BARCODE);
        intent.putExtra(EXTRA_METHOD, "");

        startActivityForResult(intent, RequestCode);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RequestCode:
                if (resultCode == RESULT_OK) {
                    String result = data.getStringExtra("code");
                    ((TextView)findViewById(R.id.barcode)).setText(result);
                }
                break;
        }
    }
}
