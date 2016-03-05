package scu.mingyuan.com.carmanager.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import scu.mingyuan.com.carmanager.R;

/**
 * Created by 应俊康 on 2016/3/3.
 */
public class AutoCompleteAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private String[] data;

    public AutoCompleteAdapter(Context context, String[] data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.length;

    }

    @Override
    public Object getItem(int position) {
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_place, null);

            viewHolder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
            viewHolder.tv_place = (TextView) convertView.findViewById(R.id.tv_place);

            convertView.setTag(viewHolder);
        }

        viewHolder.iv_icon.setImageResource(R.drawable.ic_dot_blue);
        viewHolder.tv_place.setText(data[position]);

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    class ViewHolder {
        ImageView iv_icon;
        TextView tv_place;
    }
}
