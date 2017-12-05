package heil1gd.cps496.cmich.edu.readwritetest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class cmtsPopup extends AppCompatActivity {

    private EditText editCmts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cmts_popup);

        editCmts = findViewById(R.id.editCmts);
    }

    public void cmtsFinished(View v) {
        Intent intent = new Intent();
        intent.putExtra("comment", editCmts.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }
}
