package thanksmatrix.tmtrucking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;

public class Scan extends Activity implements View.OnClickListener {

    private CompoundButton autoFocus;
    private CompoundButton useFlash;
    private Button addButton;
    private TextView userText;

    private String[] info;
    private String companyName;


    private static final int RC_OCR_CAPTURE = 9003;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan);

        userText = (TextView) findViewById(R.id.textValue);
        autoFocus = (CompoundButton) findViewById(R.id.auto_focus);
        useFlash = (CompoundButton) findViewById(R.id.use_flash);
        findViewById(R.id.scanButton).setOnClickListener(this);

        addButton = (Button) findViewById(R.id.addButton);
    }

    public void nextAdd(View view) {
        Intent intent = new Intent(getApplicationContext(), Add.class);
        //intent.putExtra("textLine", String.valueOf(info.length));
        intent.putExtra("company", companyName);
        //intent.putExtra("phone", phone);
        //intent.putExtra("wPhone", wPhone);
        //intent.putExtra("email", email);
        //intent.putExtra("name", name);

        startActivity(intent);
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.scanButton) {
            Intent intent = new Intent(this, OcrCaptureActivity.class);
            intent.putExtra(OcrCaptureActivity.AutoFocus, autoFocus.isChecked());
            intent.putExtra(OcrCaptureActivity.UseFlash, useFlash.isChecked());
            startActivityForResult(intent, RC_OCR_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RC_OCR_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    String text = data.getStringExtra(OcrCaptureActivity.TextBlockObject);
                    if (!userText.getText().toString().equals("")) {
                        userText.setText(userText.getText().toString() + "\n" + text);
                        //info = userText.getText().toString().split("\\r\\n|\\n|\\r");
                        companyName = text;
//                        phone = info[1];
                    } else {
                        userText.setText(text);
                        companyName = text;

                    }

                } else {
                    Toast.makeText(getBaseContext(), "Text is not recognized.",  Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getBaseContext(), "OCR is not working properly.",  Toast.LENGTH_SHORT).show();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}


