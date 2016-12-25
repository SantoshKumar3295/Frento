package com.example.anshraj.fblog;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import static com.example.anshraj.fblog.Main2Activity.*;


/**
 * Created by ansh raj on 17-May-16.
 */

 public class CustomPagerAdapter extends PagerAdapter {

    Context context;
    static int  position;
    public CustomPagerAdapter(Context main2Activity) {

        context=main2Activity;//refrence of main2activity

    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {

        this.position=position;
        //for every view page(view_all) this method will be call
        CustomPagerEnum customPagerEnum = CustomPagerEnum.values()[position];
        //getting  layout id stored in custompageEnum at the postion=0 (on first call)
        LayoutInflater inflater = LayoutInflater.from(context);
        //object of layout main2activity is initailized
        ViewGroup layout = (ViewGroup) inflater.inflate(customPagerEnum.getLayoutResId(), collection, false);
        //setting the layout at the postion=0 (on first call) on main2activity
        collection.addView(layout);
        GridView gridView = (GridView) layout.findViewById(R.id.gridView);


        Main2Activity s=new Main2Activity();
        Main2Activity.MyAdapter myAdapter=s.new MyAdapter(context);
        gridView.setAdapter(myAdapter);
      return layout;
    }


    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return CustomPagerEnum.values().length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        CustomPagerEnum customPagerEnum = CustomPagerEnum.values()[position];
        return context.getString(customPagerEnum.getTitleResId());

    }

}
