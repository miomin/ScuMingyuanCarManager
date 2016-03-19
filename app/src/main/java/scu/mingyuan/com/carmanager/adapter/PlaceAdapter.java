package scu.mingyuan.com.carmanager.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;

import java.util.List;

import scu.mingyuan.com.carmanager.R;

/**
 * Created by 847912006 on 2016/3/4.
 */
public class PlaceAdapter extends BaseAdapter {

    private Context context;
    private List<PoiInfo> data;
    private int type;

    public static final int TYPE_SOURCE = 0;
    public static final int TYPE_DES = 1;

    public PlaceAdapter(Context context, List<PoiInfo> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_place, null);

            viewHolder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
            viewHolder.tv_place = (TextView) convertView.findViewById(R.id.tv_place);
            viewHolder.tv_city = (TextView) convertView.findViewById(R.id.tv_city);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        PoiInfo inf = data.get(position);
        switch (type) {
            case TYPE_SOURCE:
                viewHolder.iv_icon.setImageResource(R.drawable.ic_dot_blue);
                break;
            case TYPE_DES:
                viewHolder.iv_icon.setImageResource(R.drawable.ic_dot_red);
                break;
        }
        viewHolder.tv_place.setText(inf.name);
        viewHolder.tv_city.setText(inf.address);

        return convertView;
    }

    public void setType(int type) {

        this.type = type;
    }

    class ViewHolder {
        ImageView iv_icon;
        TextView tv_place;
        TextView tv_city;
    }
}
