package com.example.david.inventorymng.Core;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.david.inventorymng.Core.Producto;
import com.example.david.inventorymng.R;
import com.example.david.inventorymng.View.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewAdapter extends ArrayAdapter<Producto> {

    private MainActivity activity;
    private List<Producto> friendList;
    private List<Producto> list;

    public ListViewAdapter(MainActivity context, int resource, List<Producto> objects) {
        super(context, resource, objects);
        this.activity = context;
        this.friendList = objects;
        this.list = new ArrayList<>();
        this.list.addAll(friendList);
    }

    @Override
    public int getCount() {
        return friendList.size();
    }

    @Override
    public Producto getItem(int position) {
        return friendList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        // If holder not exist then locate all view from UI file.
        if (convertView == null) {
            // inflate UI from XML file
            convertView = inflater.inflate(R.layout.item_listview, parent, false);
            // get all UI view
            holder = new ViewHolder(convertView);
            // set tag for holder
            convertView.setTag(holder);
        } else {
            // if holder created, get tag from view
            holder = (ViewHolder) convertView.getTag();
        }

        holder.friendName.setText(getItem(position).toString());


        //get first letter of each String item
        String firstLetter = String.valueOf(getItem(position).toString().charAt(0));

        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        // generate random color
        int color = generator.getColor(getItem(position));
        //int color = generator.getRandomColor();

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(firstLetter, color); // radius in px

        holder.imageView.setImageDrawable(drawable);

        return convertView;
    }

    // Filter method
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        friendList.clear();
        if (charText.length() == 0) {
            friendList.addAll(list);
        } else {
            for (Producto s : list) {
                if (s.toString().toLowerCase(Locale.getDefault()).contains(charText)) {
                    friendList.add(s);
                }
            }
        }
        notifyDataSetChanged();
    }

    private class ViewHolder {
        private ImageView imageView;
        private TextView friendName;

        public ViewHolder(View v) {
            imageView = (ImageView) v.findViewById(R.id.image_view);
            friendName = (TextView) v.findViewById(R.id.text);
        }
    }
}