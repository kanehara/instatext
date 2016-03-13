package com.example.kanehara.instatext;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Created by kanehara on 1/30/16.
 */
public class TreeMapAdapter extends BaseAdapter {
    private TreeMap<String, Recipient> treeMap = new TreeMap<String, Recipient>();
    private LayoutInflater layoutInflater;
    private final Context context;
    private String[] treeMapKeys;

    public class ContactViewHolder {
        ImageView contactImage;
        TextView contactName;
        TextView contactNumber;
        CheckBox contactFavorite;
        Recipient rec;
    }

    public TreeMapAdapter(TreeMap<String, Recipient> treeMap, Context context) {
        this.treeMap = treeMap;
        this.context = context;
        treeMapKeys = treeMap.keySet().toArray(new String[treeMap.keySet().size()]);
    }

    @Override
    public int getCount() {
        return treeMap.size();
    }
    @Override
    public Object getItem(int position) {
        return treeMap.get(treeMapKeys[position]);
    }
    @Override
    public long getItemId(int arg0) {
        return arg0;
    }
    @Override
    public View getView(int pos, View view, ViewGroup parent) {
        ContactViewHolder holder;
        if (view == null) {
            holder = new ContactViewHolder();
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.contact_row, null);
            holder.contactImage = (ImageView) view.findViewById(R.id.contactImage);
            holder.contactName = (TextView) view.findViewById(R.id.contactName);
            holder.contactNumber = (TextView) view.findViewById(R.id.contactNumber);
            holder.contactFavorite = (CheckBox) view.findViewById(R.id.contactFavorite);
            view.setTag(holder);
        }
        else {
            holder = (ContactViewHolder)view.getTag();
        }
        Recipient rec = (Recipient)getItem(pos);
        holder.rec = rec;
        holder.contactNumber.setText(rec.getPhoneNumber());
        holder.contactName.setText(rec.getFullName());
        holder.contactFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Recipient rec = ((ContactViewHolder)((View) buttonView.getParent()).getTag()).rec;
                rec.setIsFavorite(isChecked);
                treeMap.get(rec.getMapId()).setIsFavorite(isChecked);
                SharedPreferences.Editor settingsEditor = context.getSharedPreferences(RecipientSelect.PREFS_NAME, 0).edit();
                settingsEditor.putBoolean(rec.getPhoneNumber(), isChecked);
                settingsEditor.commit();
            }
        });
        holder.contactFavorite.setChecked(rec.getIsFavorite());
        holder.contactImage.setImageURI(rec.getPhoto());
        view.setMinimumHeight(150);
        return view;
    }

}
