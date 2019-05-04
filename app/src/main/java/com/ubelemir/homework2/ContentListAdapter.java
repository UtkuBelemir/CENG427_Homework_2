package com.ubelemir.homework2;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ContentListAdapter extends BaseAdapter {

    Context context;
    ContentItem[] data;
    private LayoutInflater inflater;

    public ContentListAdapter(Context context, ContentItem[] data) {
        this.context = context;
        this.data = data;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.length;
    }

    @Override
    public ContentItem getItem(int position) {
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.content_list_item, null);
        TextView contentTitle = (TextView) vi.findViewById(R.id.contentItem_title);
        ImageView contentIcon = (ImageView) vi.findViewById(R.id.contentItem_icon);
        ContentItem currentItem = getItem(position);
        contentTitle.setText(currentItem.title);
        if(currentItem.type.equals("news")){
            contentIcon.setImageResource(R.drawable.icon_news);
        }else {
            contentIcon.setImageResource(R.drawable.icon_announcement);
        }

        return vi;
    }
}