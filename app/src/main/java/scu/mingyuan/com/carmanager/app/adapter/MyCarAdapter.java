package scu.mingyuan.com.carmanager.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import scu.mingyuan.com.carmanager.R;
import scu.mingyuan.com.carmanager.app.bean.MyCar;
import scu.mingyuan.com.carmanager.myview.MyBanner;
import scu.mingyuan.com.carmanager.utils.MyImageLoader;
import scu.mingyuan.com.carmanager.zxing.activity.CaptureActivity;

/**
 * Created by 莫绪旻 on 16/3/3.
 */
public class MyCarAdapter extends BaseAdapter implements AbsListView.OnScrollListener {

    // View_Type
    final int VIEW_TYPE = 2;
    final int TYPE_1 = 0;
    final int TYPE_2 = 1;

    private int mStart; // 可见项第一项
    private int mEnd; // 可见项最后项

    private ArrayList<MyCar> mycarList;
    private Context context;
    private Fragment fragment;

    public MyCarAdapter(ArrayList<MyCar> mycarList, Context context, Fragment fragment) {
        this.context = context;
        this.mycarList = mycarList;
        this.fragment = fragment;
    }

    public void add(MyCar myCar) {
        mycarList.add(myCar);
        notifyDataSetChanged();
    }

    public void addAll(ArrayList<MyCar> mycarList) {
        this.mycarList.addAll(mycarList);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mycarList.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return mycarList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_1;
        else
            return TYPE_2;
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder1 holder1 = null;
        ViewHolder2 holder2 = null;
        int type = getItemViewType(position);

        //无convertView，需要new出各个控件
        if (convertView == null) {
            //按当前所需的样式，确定new的布局
            switch (type) {
                case TYPE_1:
                    convertView = View.inflate(context, R.layout.layout_mycar_top, null);
                    holder1 = new ViewHolder1();
                    holder1.myBanner = (MyBanner) convertView.findViewById(R.id.myBanner);
                    holder1.layout_add_mycar = (LinearLayout) convertView.findViewById(R.id.layout_add_mycar);
                    holder1.layout_add_mycar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //打开扫描界面扫描条形码或二维码
                            Intent openCameraIntent = new Intent(fragment.getContext(), CaptureActivity.class);
                            fragment.startActivityForResult(openCameraIntent, 0);
                        }
                    });
                    convertView.setTag(holder1);
                    break;
                case TYPE_2:
                    convertView = View.inflate(context, R.layout.item_mycar, null);
                    holder2 = new ViewHolder2();
                    holder2.tvType = (TextView) convertView.findViewById(R.id.tvType);
                    holder2.icon_car = (ImageView) convertView.findViewById(R.id.icon_car);
                    holder2.tvBrand = (TextView) convertView.findViewById(R.id.tvBrand);
                    holder2.tvCar = (TextView) convertView.findViewById(R.id.tvCar);
                    holder2.tvCarLocation = (TextView) convertView.findViewById(R.id.tvCarLocation);
                    holder2.tvlicensePlateNumber = (TextView) convertView.findViewById(R.id.tvlicensePlateNumber);
                    holder2.tvModel = (TextView) convertView.findViewById(R.id.tvModel);
                    holder2.tvBodyStructure = (TextView) convertView.findViewById(R.id.tvBodyStructure);
                    convertView.setTag(holder2);
                    break;
            }
        } else {
            //有convertView，按样式，取得不用的布局
            switch (type) {
                case TYPE_1:
                    holder1 = (ViewHolder1) convertView.getTag();
                    break;
                case TYPE_2:
                    holder2 = (ViewHolder2) convertView.getTag();
                    break;
            }
        }

        //设置资源
        switch (type) {
            case TYPE_1:
                holder1.myBanner.startPlay();
                break;
            case TYPE_2:
                MyCar myCar = mycarList.get(position - 1);
                holder2.tvType.setText(myCar.getCar_type());

                // 防止图片闪动
                //将url和ivicon绑定
                holder2.icon_car.setTag(myCar.getImg());
                if (holder2.icon_car.getTag().equals(myCar.getImg())) {
                    MyImageLoader.getInstance().displayFromNet(myCar.getImg(), holder2.icon_car);
                }

                holder2.tvBrand.setText(myCar.getBrand());
                holder2.tvCar.setText(myCar.getCar());
                holder2.tvCarLocation.setText(myCar.getCar_location());
                holder2.tvlicensePlateNumber.setText(myCar.getLicense_plate_number());
                holder2.tvModel.setText(myCar.getModel());
                holder2.tvBodyStructure.setText(myCar.getBody_structure());
                break;
        }
        return convertView;
    }

    /**
     * 滑动优化
     */
    // 停止滑动时才加载，滑动时停止加载
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE) {
            // 加载可见项
//            MiominImageLoader.loadImages(mStart, mEnd);
        } else {
            // 停止加载
//            MiominImageLoader.cancelAllTasks();
        }
    }

    // 在滑动过程中找到可见项的首尾下标
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mStart = firstVisibleItem;
        mEnd = firstVisibleItem + visibleItemCount;
    }

    class ViewHolder1 {
        public MyBanner myBanner;
        public LinearLayout layout_add_mycar;
    }

    class ViewHolder2 {
        public TextView tvType;
        public ImageView icon_car;
        public TextView tvBrand;
        public TextView tvCar;
        public TextView tvCarLocation;
        public TextView tvlicensePlateNumber;
        public TextView tvModel;
        public TextView tvBodyStructure;
    }

    // 插入数据
    public void insert(MyCar myCar) {
        mycarList.add(myCar);
        notifyDataSetChanged();
    }

    // 根据ID查找汽车
    public MyCar queryById(Integer id) {
        MyCar myCar = null;
        for (int i = 0; i < mycarList.size(); i++) {
            if (id == mycarList.get(i).getId()) {
                myCar = mycarList.get(i);
                break;
            }
        }
        return myCar;
    }

    //根据context获取的index删除一辆汽车
    public void deleteByIndex(Integer index) {
        mycarList.remove(index);
        notifyDataSetChanged();
    }

    public void modifyCar(Integer index, MyCar myCar) {
        mycarList.set(index, myCar);
        notifyDataSetChanged();
    }
}
