package sg.edu.np.mad.mad_practical2;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class UserViewHolder extends RecyclerView.ViewHolder {
    protected TextView nameTextView;
    protected TextView descTextView;
    protected ImageView imageView;
    protected ImageView largeimageView;

    public UserViewHolder(View v) {
        super(v);

        nameTextView = v.findViewById(R.id.textView3);
        descTextView = v.findViewById(R.id.textView4);
        imageView = v.findViewById(R.id.imageView4);
        largeimageView = v.findViewById(R.id.imageView3);
    }
}