package sg.edu.np.mad.mad_practical2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ListActivity.UserDetails userDetails;
    SQLiteDB sqLiteDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sqLiteDB = new SQLiteDB(this, null, null, 1);
        Intent intent = getIntent();
        userDetails = (ListActivity.UserDetails) intent.getSerializableExtra("user");

        ((TextView)findViewById(R.id.textView)).setText("MAD " +  userDetails.user.name);
        ((TextView)findViewById(R.id.textView2)).setText("Description" + userDetails.user.description);

        Button button = findViewById(R.id.button);
        button.setText( userDetails.user.followed? "Unfollow" : "Follow");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,  userDetails.user.followed? "UnFollowed!" : "Followed!", Toast.LENGTH_SHORT)
                        .show();
                userDetails.user.followed = !userDetails.user.followed;
                sqLiteDB.updateUser(userDetails.user);
                button.setText(userDetails.user.followed? "UnFollow" : "Follow");
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("user", userDetails);
        setResult(Activity.RESULT_OK, intent);

        super.onBackPressed();
    }
}