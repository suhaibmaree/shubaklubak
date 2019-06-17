package edu.mareeaaup.s.shubaklubak.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import edu.mareeaaup.s.shubaklubak.R;

public class Help extends AppCompatActivity {

    private Button report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        report = findViewById(R.id.report_button);
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mailClient = new Intent(Intent.ACTION_VIEW);
                mailClient.setClassName("com.google.android.gm",
                        "com.google.android.gm.ConversationListActivityGmail");
                startActivity(mailClient);
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar_help);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }
}
