package com.example.anshraj.fblog;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.Image;
import android.net.LocalServerSocket;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.text.method.QwertyKeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.Normalizer;
import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener //interface that is responsible for event handling of navigatin view


{
    View thread;
    Context context;
    int item;
    ViewPager viewPager;
    String TAG="MyApplication";
String myemail;
    String jsondata="";
    GridView gridView;
    JSONObject response, profile_pic_data, profile_pic_url;
    TextView user_name, user_email;
    ImageView user_picture;
    NavigationView navigation_view; //navigation widget for naviga  tion drawer

   ArrayList<String> al=new ArrayList<String>();

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    TabLayout tabLayout;
    DrawerLayout drawer;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);//this is the whole view of actiity_main2,this means the only xml element in it can get its view object
       context=this;

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);//custom created toolbar
        setSupportActionBar(toolbar);//regestring the toolbar
        getSupportActionBar().setTitle("Bhopal");//setting the title
    toolbar.setBackgroundColor(getResources().getColor(R.color.mygrey));

        tabLayout = (TabLayout) findViewById(R.id.tabs); //tablout is the tabs jus below the toolbar which is the header of view pager i.e slide layouts (e.g view_all)

         viewPager = (ViewPager) findViewById(R.id.viewpager);  //just grabbing a reference to your ViewPager i.e sliding layouts(e.g view_all,view_girl)
    CustomPagerAdapter customPagerAdapter=new CustomPagerAdapter(this); // placing the contents from our java code to inside the viewpager layouts in the xml file
    viewPager.setAdapter(customPagerAdapter);// informing viewpager that u got an adapter

    Log.i(TAG,"NO OF PAGER   "+item);
        //actionTab();
        final TabLayout.Tab home = tabLayout.newTab(); //tablout is blank so in order to hae some changes we need object of each tabs of tablout
        final TabLayout.Tab inbox = tabLayout.newTab();
        final TabLayout.Tab star = tabLayout.newTab();
        final TabLayout.Tab colo = tabLayout.newTab();
        final TabLayout.Tab colob = tabLayout.newTab();

    tabLayout.setBackgroundColor(getResources().getColor(R.color.mygrey));
    tabLayout.setTabTextColors(getResources().getColor(R.color.myaq),getResources().getColor(R.color.wyt));
        home.setText("Family");//just making the changes with our so called tab objects
        inbox.setText("Boys");
        star.setText("Girls");
        colo.setText("Office");
        colob.setText("Shop");
        tabLayout.addTab(home, 0);//placing the order of tabs of tablayout
        tabLayout.addTab(inbox, 1);
        tabLayout.addTab(star, 2);
        tabLayout.addTab(colo, 3);
        tabLayout.addTab(colob, 4);

       // tabLayout.setTabTextColors(ContextCompat.getColorStateList(this, R.color.tab_selector));
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.indicator)); //color of tab strip

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    //resegestring layouts of viewpager for event handling and joining tablout,tablout change listener operates the tabs and perform action for change in page of view pager
    //TabLayout.TabLayoutOnPageChangeListener(tabLayout) implemts method onTabSelectedListener which is a callbback method for event handling in TabChangeListener

        tabLayout.setOnTabSelectedListener(onTabSelectedListener(viewPager));//even handling is done in this activity scroll down
//when the tab (say 0 index tab) is selected then the viewpage on the 0 index will be called by the above line
        Intent intent = getIntent();

       myemail=intent.getStringExtra("email");
    Log.i(TAG,"my email text-"+myemail);

    jsondata = intent.getStringExtra("jsondata");
    Log.i(TAG,"JSON VALUE"+jsondata);
        setNavigationHeader(); // call setNavigationHeader Method.
if(jsondata==null) {
    user_name.setText("Welcome");
user_email.setText(myemail);
    user_picture=(ImageView)findViewById(R.id.profile_pic);


    Log.i(TAG,"normal email");

}   else {
    setUserProfile(jsondata);
    Log.i(TAG,"JSON data scope");

}



       drawer = (DrawerLayout) findViewById(R.id.drawer_layout); //drawer layout initialized which is used for navigation view
     ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
              this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close); //drawer toggle button,which is animation of drawer
       toggle.syncState();
        //toggle.onDrawerOpened(navigation_view);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
      //registration of navigation view
       navigationView.setNavigationItemSelectedListener(this);
      // NavigationView.setNavigationItemSelectedListener();
    //ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
navigationView.getFocusedChild();
        drawer.requestLayout();

    /*
    try {
        int item = viewPager.getCurrentItem();
        Log.i(TAG,""+item);
        gridView = (GridView) findViewById(R.id.gridView);
        Log.i(TAG,"gridview initialized");
        MyAdapter myAdapter=new MyAdapter(this);
        if(gridView==null) {
            Log.i(TAG, "gridview is null");
        }
        gridView.setAdapter(myAdapter);
    }
    catch(Exception e)
    {
        Log.i(TAG,"execption at setgridadapter - "+e.getMessage());


    }
*/

}


//event hadling of tab and performing the change of viewpage as per the given tab index
    private TabLayout.OnTabSelectedListener onTabSelectedListener(final ViewPager pager) {
        return new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {//callback method
                pager.setCurrentItem(tab.getPosition());//getpostion return the index of tab selected by the user
                //setCurrentItem focus the viewpage that is told by the tab.getPosition
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
// when user
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        };


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);

 return;


        }
        else
        {
            super.onBackPressed();
        }
            moveTaskToBack(true);
       }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

       getMenuInflater().inflate(R.menu.main2, menu);
       // getMenuInflater().inflate(R.menu.activity_main2_drawer, menu);
        return true;
    }

    public void setNavigationHeader() {//setting up the header in the navigation view

        navigation_view = (NavigationView) findViewById(R.id.nav_view);
        View header = LayoutInflater.from(this).inflate(R.layout.nav_header_main2, null); // component of ,header layout initialized
        //i.e header depends upon us whether we gonna use it or not,it contains an imageview and two textview
        navigation_view.addHeaderView(header);

        user_name = (TextView) header.findViewById(R.id.username); //refrence of header eleemtn
        user_picture = (ImageView) header.findViewById(R.id.profile_pic);
        user_email = (TextView) header.findViewById(R.id.email);
    }

    public void setUserProfile(String jsondata) { //method which invoking the image,text,text in the header of navigation view

        try {
            response = new JSONObject(jsondata); //jsonobject coming from main actiivty in the form of  string
            user_email.setText(response.get("email").toString()); //email data is filtered from the object response
            user_name.setText(response.get("name").toString());
            profile_pic_data = new JSONObject(response.get("picture").toString()); //profile pic set
            profile_pic_url = new JSONObject(profile_pic_data.getString("data"));// email set

            Picasso.with(this).load(profile_pic_url.getString("url"))
                    .into(user_picture); //profile pic coming in round shape

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent=new Intent(Main2Activity.this,Main3Activity.class);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
@Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

            Intent intent=new Intent(Main2Activity.this,Main3Activity.class);
            startActivity(intent);

        } else if (id == R.id.logout) {

            Intent intent=new Intent(Main2Activity.this,MainActivity.class);
            startActivity(intent);

        }
       item.setChecked(false);
        drawer.closeDrawers();
        return true;


        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //drawer.closeDrawer(GravityCompat.START);
  //     return true;
    }



    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main2 Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.anshraj.fblog/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main2 Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.anshraj.fblog/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


class GridContent //this class is wrapping up all the contents of gridview in an object
{
    int imageId;
    String add;
    String price;
    String mobile;
    String type;

    GridContent(int imageId,String add,String price,String mobile,String type)
    {
        this.imageId=imageId;
        this.type=type;
        this.add=add;
        this.mobile=mobile;
        this.price=price;

    }
}

    //here is the adapter class for our gridview jus like array adapter in listview
 class MyAdapter extends BaseAdapter
{

ArrayList<GridContent> gridList=new ArrayList<GridContent>();//ew have to store all the data in an arrays as the gridview items can be increase or decrease
    Context context;
MyAdapter(Context context)
{
    this.context=context;

   int item=CustomPagerAdapter.position;
    Resources resources=context.getResources(); //this returns the object of resource folder where the strings.xml reside
    String [] add=resources.getStringArray(R.array.add);//getting the stringaarray which is made in the strings.xml file

    String [] price=resources.getStringArray(R.array.price);
    String [] mobile=resources.getStringArray(R.array.mobile);
    String [] type=resources.getStringArray(R.array.type);
    int [] imageR={R.drawable.boy,R.drawable.boy2,R.drawable.boy3,R.drawable.girl1,R.drawable.girl2,R.drawable.girl3,R.drawable.girl4,R.drawable.office,R.drawable.office2,R.drawable.shop1,};//imagR is a imageview
   // Log.i(TAG,"RESOURCES ADDED");
    switch(item)
    {
        case 0:
            gridList.clear();
            for(int i=0;i<10;i++)
            {
                //  Log.i(TAG,"LOOP IN");

                GridContent gridContent=new GridContent(imageR[i],add[i],price[i],mobile[i],type[i]); //we r feeding data in the grid content object of gridview elements like image,text
                gridList.add(gridContent);

            }

            break;
        case 1: gridList.clear();
            for(int i=0;i<3;i++)
            {
                //  Log.i(TAG,"LOOP IN");


                GridContent gridContent=new GridContent(imageR[i],add[i],price[i],mobile[i],type[i]); //we r feeding data in the grid content object of gridview elements like image,text
                gridList.add(gridContent);

            }
            break;
        case 2:   gridList.clear();
            for(int i=3;i<7;i++)
            {
                //  Log.i(TAG,"LOOP IN");


                GridContent gridContent=new GridContent(imageR[i],add[i],price[i],mobile[i],type[i]); //we r feeding data in the grid content object of gridview elements like image,text
                gridList.add(gridContent);

            }
            break;
        case 3:gridList.clear();
            for(int i=7;i<9;i++)
            {
                //  Log.i(TAG,"LOOP IN");


                GridContent gridContent=new GridContent(imageR[i],add[i],price[i],mobile[i],type[i]); //we r feeding data in the grid content object of gridview elements like image,text
                gridList.add(gridContent);

            }
            break;
        case 4: gridList.clear();
            for(int i=9;i<10;i++)
            {
                //  Log.i(TAG,"LOOP IN");


                GridContent gridContent=new GridContent(imageR[i],add[i],price[i],mobile[i],type[i]); //we r feeding data in the grid content object of gridview elements like image,text
                gridList.add(gridContent);

            }
            break;


    }

  //  Log.i(TAG,"LOOP CLOSE"+gridList.size());



}
@Override
    public int getCount() {

   // Log.i(TAG,"grid list size"+gridList.size());

        return gridList.size();

}

     @Override
    public Object getItem(int position) {
        //Log.i(TAG,"position"+gridList.get(position));

        return gridList.get(position);
    }

     @Override
    public long getItemId(int position) {
       // Log.i(TAG,"position"+position);

        return position;
    }

    //this is a very useful class which save lot of resources ,how? findViewById take lot of resources and after 1st time creation it returns refernce only of the set view
    class ViewHolder
    {
        ImageView imageView;
        TextView add;
        TextView mobile;
        TextView price;
        TextView type;

        ViewHolder(View v)
     {
    imageView=(ImageView)v.findViewById(R.id.imageView3);

         add=(TextView)v.findViewById(R.id.add);
         mobile=(TextView)v.findViewById(R.id.mobile);
         price=(TextView)v.findViewById(R.id.price);
         type=(TextView)v.findViewById(R.id.type);
         Log.i(TAG,"IMAGE VIEW ID SET");

     }
  }







    @Override
    public View getView(int position, View convertView, ViewGroup parent) {//for each box (gridview cells) of gridview this method is called
        Log.i(TAG,"getview called");

        View row=convertView;
        ViewHolder viewHolder=null;
        if(row==null)
        {
            Log.i(TAG,"FIRST TIME INITALIZED");

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.single_grid, parent, false);
            Log.i(TAG,"SINGLE LAYOUT SET");
            viewHolder=new ViewHolder(row);
         //   Log.i(TAG,"VIEW HOLDER OBJECT MADE");

            row.setTag(viewHolder);
        }
   else
        {
            viewHolder=(ViewHolder)row.getTag();

        }
        GridContent gridContent=gridList.get(position);
       // Log.i(TAG,"GRIDLIST POSTION SET");
        viewHolder.imageView.setImageResource(gridContent.imageId);
        viewHolder.imageView.setLayoutParams(new RelativeLayout.LayoutParams(GridView.AUTO_FIT,400));
        viewHolder.add.setText(gridContent.add);

        viewHolder.mobile.setText(gridContent.mobile);






        Log.i(TAG,"image view set");
        int i2=50;
      try {
       //   i2=(int) getResources().getDimension(R.dimen.rowHigh);


          }
      catch(Exception ex)
      {

          Log.i(TAG,i2+"excpetion occurred at setting dimension");
    }
        //viewHolder.add.setText(gridContent.add);
       // viewHolder.mobile.setText(gridContent.mobile);
        //viewHolder.price.setText(gridContent.price);
      // viewHolder.type.setText(gridContent.type);

        return row;
    }
}
}
