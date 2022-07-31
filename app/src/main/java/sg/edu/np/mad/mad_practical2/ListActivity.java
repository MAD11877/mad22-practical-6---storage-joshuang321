package sg.edu.np.mad.mad_practical2;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListActivity extends AppCompatActivity {
    private UserAdapter adapter;
    private ActivityResultLauncher<UserDetails> userDetailsActivityResultLauncher;
    List<User> userList;
    SQLiteDB sqLiteDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        sqLiteDB = new SQLiteDB(this, null, null, 1);

        for (int i=0; i<20; i++)
        {
            Random random = new Random();
            boolean followed = random.nextBoolean();
            String name = String.valueOf(random.nextInt());
            String desc = String.valueOf(random.nextInt());

            User user = new User(name, desc, 0, followed);
            sqLiteDB.addUser(user);
        }

        userDetailsActivityResultLauncher = registerForActivityResult(new ViewProfileActivityResultContract(),
                new ViewProfileActivityResultCallback());

        adapter = new UserAdapter(sqLiteDB.getUsers(), this, userDetailsActivityResultLauncher);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = findViewById(R.id.list_recyclerview);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private List<User> createUserList() {
        ArrayList<User> userArrayList = new ArrayList<>();
        Random rand = new Random();
        for (int i=0; i<20; i++) {
            userArrayList.add(new User(String.valueOf(rand.nextInt()), String.valueOf(rand.nextInt()),
                    rand.nextInt(), rand.nextBoolean()));
        }
        return userArrayList;
    }

    public static class UserDetails implements Serializable {
        public User user;
        public int position;

        public UserDetails() { }

        public UserDetails (User user,
                            int position)
        {
            this.user = user;
            this.position = position;
        }
    }

    public class ViewProfileActivityResultContract extends ActivityResultContract<UserDetails, UserDetails> {

        @NonNull
        @Override
        public Intent createIntent(@NonNull Context context, UserDetails input) {
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra("user", input);
            Log.d("activity", String.valueOf(input.user.followed));

            return intent;
        }

        @Override
        public UserDetails parseResult(int resultCode, @Nullable Intent intent) {
            if (Activity.RESULT_OK != resultCode || null == intent)
                return null;
            return (UserDetails) intent.getSerializableExtra("user");
        }
    }

    public class ViewProfileActivityResultCallback implements ActivityResultCallback<UserDetails> {

        @Override
        public void onActivityResult(UserDetails result) {
            if (null != result) {
                Log.d("activity", String.valueOf(result.user.followed));
                userList.set(result.position, result.user);
                adapter.notifyItemChanged(result.position);
            }
        }
    }

    @Override
    public void onDestroy() {
        if (null != userDetailsActivityResultLauncher) {
            userDetailsActivityResultLauncher.unregister();
        }
        super.onDestroy();
    }
}