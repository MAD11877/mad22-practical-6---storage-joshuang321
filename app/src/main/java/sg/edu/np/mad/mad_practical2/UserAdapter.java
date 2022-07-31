package sg.edu.np.mad.mad_practical2;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {
    private List<User> userList;
    private Context context;
    private ActivityResultLauncher<ListActivity.UserDetails> userDetailsActivityResultLauncher;

    public UserAdapter(List<User> userList,
                       Context context,
                       ActivityResultLauncher<ListActivity.UserDetails> userDetailsActivityResultLauncher) {
        this.userList = userList;
        this.context = context;
        this.userDetailsActivityResultLauncher = userDetailsActivityResultLauncher;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View userView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_recycleview,
                        parent,
                        false);
        return new UserViewHolder(userView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.nameTextView.setText(user.name);
        holder.descTextView.setText(user.description);

        holder.largeimageView.setVisibility(user.name.endsWith("7") ? View.VISIBLE : View.GONE);
        holder.imageView.setOnClickListener(view -> createUserAlert(new ListActivity.UserDetails(user, position)));
    }

    private void createUserAlert(ListActivity.UserDetails userDetails) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Profile");
        alertDialogBuilder.setMessage(userDetails.user.name);
        alertDialogBuilder.setPositiveButton("View", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                userDetailsActivityResultLauncher.launch(userDetails);
            }
        });
        alertDialogBuilder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDialogBuilder.create().show();
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
