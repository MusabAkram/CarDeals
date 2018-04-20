package com.example.saad.carsales;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class Car_details extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    ArrayList<Integer> image=new ArrayList<Integer>();
TextView carname,location,regyear,color,mileage,contact,price;
    String strCarName,strYear,strContact,strRegYear,strColor,strMileage,strRegCity,strPrice;

    ViewPager viewPager;
    Image_slide customSwipe;

    public int dotsCount=5;
    public ImageView[] dots;
    public LinearLayout pager_indicator;
    private int [] imageResources ={
            R.drawable.icon,
            R.drawable.icon3,
            R.drawable.icon,
            R.drawable.civic,
            R.drawable.transmission};

    Firebase ref;
    String passed_add_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);
        Firebase.setAndroidContext(this);

        carname=(TextView) findViewById(R.id.car_name);
        color=(TextView) findViewById(R.id.exterior_colorshow);
        location=(TextView) findViewById(R.id.location);
        mileage=(TextView) findViewById(R.id.mileage_s);
        regyear=(TextView) findViewById(R.id.reg_year_s);
        price=(TextView) findViewById(R.id.car_price);
        contact=(TextView) findViewById(R.id.txt_Contact);



        passed_add_id=getIntent().getExtras().getString("Add ID");

        ref=new Firebase("https://car-sales-f4f9c.firebaseio.com/");
        ref=ref.child("Adverts").child(passed_add_id);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                strColor=dataSnapshot.child("Color").getValue().toString();
                strMileage=dataSnapshot.child("Mileage").getValue().toString();
                strYear=dataSnapshot.child("Model Year").getValue().toString();
                strCarName=dataSnapshot.child("Model").getValue().toString();
                strRegCity=dataSnapshot.child("Registration City").getValue().toString();
                strPrice=dataSnapshot.child("Price").getValue().toString();
                strContact=dataSnapshot.child("Contact").getValue().toString();

                carname.setText(strCarName);
                color.setText(strColor);
                location.setText(strRegCity);
                mileage.setText(strMileage);
                price.setText(strPrice);
                regyear.setText(strYear);
                contact.setText(strContact);

                //String strCarName,strYear,strAdd,strRegYear,strColor,strMileage;
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });



        image.add(R.drawable.buyer);
        image.add(R.drawable.sell);
        image.add(R.drawable.car);
        image.add(R.drawable.buyer);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        customSwipe = new Image_slide(this,imageResources);
        viewPager.setAdapter(customSwipe);
        setUiPageViewController();


        SharedPreferences details = getSharedPreferences("my_prefs", 0);
    /*    String car_name = details.getString("Model","");
        String clr=details.getString("Color","");
        String reg_year = details.getString("Registraion_Year", "");
        String address = details.getString("Address", "");
        String miles = details.getString("Mileage", "");*/
        Image_adapter car_img=new Image_adapter(Car_details.this,image);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
     /*   carname=(TextView) findViewById(R.id.car_name);
        color=(TextView) findViewById(R.id.exterior_colorshow);
        add=(TextView) findViewById(R.id.location);
//        mileage=(TextView) findViewById(R.id.miles);
        color.setText(clr);
        add.setText(address);
//        mileage.setText(miles);
        carname.setText(car_name);*/

    }
    public void setUiPageViewController() {

        dotsCount = customSwipe.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselected_item));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);

//            pager_indicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < dotsCount; i++) {
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselected_item));
        }

        dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    public void finish() {
        super.finish();
        onLeaveThisActivity();
    }

    protected void onLeaveThisActivity() {
        overridePendingTransition(R.anim.slide_in_back, R.anim.slide_out_back);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        onStartNewActivity();
    }

    @Override
    public void startActivity(Intent intent, Bundle options) {
        super.startActivity(intent, options);
        onStartNewActivity();
    }

    protected void onStartNewActivity() {
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @Override
    protected void onStart()
    {
        super.onStart();

    }
}
