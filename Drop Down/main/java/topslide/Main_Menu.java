package com.example.vivek.appmodel2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Objects;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class Main_Menu extends AppCompatActivity implements View.OnClickListener,
                                                                        ServicesFragment.OnSwipeResult,
                                                                            ServicesFragment.OnServiceSelected{

    Toolbar toolbar;
    LinearLayout menuContainer;
    Button button1,button2,button3,button4,button5,button6,button7,button8;
    SharedPreferences preferences;
    
    private ServicesFragment servicesFragment;
    private FragmentManager serviceManager,listManager,menuManager;
    
    private boolean isDetails;
    private long mLastClickTime = 0;
    String serviceTag, rechStatus, TXID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //ServicesFragment servicesFragment = new ServicesFragment();
        //navigateTo(servicesFragment,true, "Service");

        menuContainer = findViewById(R.id.menuContainer);
        button1 = findViewById(R.id.myAccount);
        button2 = findViewById(R.id.home);
        button3 = findViewById(R.id.wallet);
        button4 = findViewById(R.id.history);
        button5 = findViewById(R.id.changePassword);
        button6 = findViewById(R.id.help);
        button7 = findViewById(R.id.about);
        button8 = findViewById(R.id.logout);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);

       
        preferences = getSharedPreferences("modIILock",MODE_PRIVATE);

        menuContainer.setVisibility(View.INVISIBLE);

        toolbar = findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.argb(255,158,158,158));

        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/arialr.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());

				menuContainer.post(new Runnable() {
                @Override
                public void run() {
                    setMenuClick();
                }
            });

				servicesFragment = new ServicesFragment();
                servicesFragment.setOnSwipeResult(Main_Menu.this);
                servicesFragment.setOnServiceSelected(Main_Menu.this);
                fragmentTransaction.replace(R.id.mainContainer,servicesFragment,"Services");
                fragmentTransaction.addToBackStack("Services");
                fragmentTransaction.commit();

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        if (GlobalVariable.isMenuOpen)
        {
            ((ViewGroup)toolbar).getChildAt(1).performClick();
        }
        else
        {
            if (getSupportFragmentManager().getBackStackEntryCount()==1)
            {
                if (SystemClock.elapsedRealtime()-mLastClickTime<1000)
                {
                    finish();
                }
                else
                {
                    Snackbar.make(findViewById(R.id.bottomLayout), "Press Again to Exit", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Action", null).setDuration(1000).show();
                }
                mLastClickTime = SystemClock.elapsedRealtime();
            }
            else
            {
                if (isDetails)
                {
                    super.onBackPressed();
                    isDetails = false;
                    Objects.requireNonNull(getSupportActionBar()).setTitle("Transaction History");
                }
                else
                {
                    super.onBackPressed();
                    if (servicesFragment.isInFront)
                    {
                        Objects.requireNonNull(getSupportActionBar()).setTitle("Your App");
                        setMenuClick();
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = menuManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_y, R.anim.exit_y, R.anim.pop_enter_y, R.anim.pop_exit_y);
        Fragment frag;
        if (GlobalVariable.isMenuOpen)
        {
            ((ViewGroup)toolbar).getChildAt(1).performClick();
        }
        switch (v.getId())
        {
            case R.id.myAccount:
				Toast.makeText(getActivity(), "Account",Toast.LENGTH_SHORT).show();
                
                break;
            case R.id.home:
				Toast.makeText(getActivity(), "Home",Toast.LENGTH_SHORT).show();
                
                break;
            case R.id.wallet:
				Toast.makeText(getActivity(), "Wallet",Toast.LENGTH_SHORT).show();
                
                break;
            case R.id.history:
				Toast.makeText(getActivity(), "History",Toast.LENGTH_SHORT).show();
                
                break;
            case R.id.changePassword:
				Toast.makeText(getActivity(), "Change Password",Toast.LENGTH_SHORT).show();
               
                break;
            case R.id.help:
				Toast.makeText(getActivity(), "Help",Toast.LENGTH_SHORT).show();
               
                break;
            case R.id.about:
				Toast.makeText(getActivity(), "About",Toast.LENGTH_SHORT).show();
               
                break;
            case R.id.logout:
                final android.support.v7.app.AlertDialog.Builder build = new android.support.v7.app.AlertDialog.Builder(Main_Menu.this);
                build.setTitle("Logout!!").setMessage("Are you sure you want to exit.");

                build.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                build.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       dialog.dismiss();
                    }
                });
                android.support.v7.app.AlertDialog dlg = build.create();
                dlg.show();
                break;
        }
    }

    

    public void setMenuClick()
    {
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        menuContainer.setVisibility(View.VISIBLE);
        toolbar.setNavigationOnClickListener(new NavigationIconClickListener(Main_Menu.this,
                findViewById(R.id.mainContainer),
                menuContainer.getHeight(),
                new AccelerateDecelerateInterpolator(),
                Main_Menu.this.getResources().getDrawable(R.drawable.close_to_menu), // Menu open icon
                Main_Menu.this.getResources().getDrawable(R.drawable.menu_to_close)));

    }

    public void setMenuBackClick()
    {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                if (servicesFragment.isInFront)
                {
                    ((ImageView) v).setImageResource(R.drawable.ic_menu);
                    setMenuClick();
                }
            }
        });
    }


   

    //Service Fragment On Swipe result....///////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onSwipe(boolean isSwipeDown) {
        if ((isSwipeDown && !GlobalVariable.isMenuOpen) || (!isSwipeDown && GlobalVariable.isMenuOpen))
        {
            //Open Menu
            ((ViewGroup)toolbar).getChildAt(1).performClick();
        }
    }

    // Service Fragment Service Selected.../////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void serviceSelected(String service) {
        if (GlobalVariable.isMenuOpen)
        {
            ((ViewGroup)toolbar).getChildAt(1).performClick();
        }
        serviceManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = serviceManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_y, R.anim.exit_y, R.anim.pop_enter_y, R.anim.pop_exit_y);
        Fragment frag;
        toolbar.setNavigationIcon(R.drawable.ic_back_arrow);
        setMenuBackClick();
        switch (service)
        {
            case "Phone":
			
				Toast.makeText(getActivity(), "Phone Recharge",Toast.LENGTH_SHORT).show();
                
            break;
            case "Electricity":
				Toast.makeText(getActivity(), "Electricity Bill Pay",Toast.LENGTH_SHORT).show();
               
                break;
            case "DTH":
				Toast.makeText(getActivity(), "DTH Bill Pay",Toast.LENGTH_SHORT).show();
                
                break;
            case "Datacard":
				Toast.makeText(getActivity(), "Datacard Recharge",Toast.LENGTH_SHORT).show();

                break;
            case "Landline":
				Toast.makeText(getActivity(), "Landline Bill Payment",Toast.LENGTH_SHORT).show();
                
                break;
            case "Gas":
				Toast.makeText(getActivity(), "Gas Bill Payment",Toast.LENGTH_SHORT).show();
                
                break;
            case "Broadband":
				Toast.makeText(getActivity(), "Broadband Bill Pay",Toast.LENGTH_SHORT).show();
               
                break;
            case "Water":
				Toast.makeText(getActivity(), "Water Bill Pay",Toast.LENGTH_SHORT).show();
                
                break;
            case "Bus":
				Toast.makeText(getActivity(), "Bus Ticket Booking",Toast.LENGTH_SHORT).show();
                
                break;
            case "Flight":
				Toast.makeText(getActivity(), "Flight Ticket Booking",Toast.LENGTH_SHORT).show();
                
                break;
        }
    }

}
