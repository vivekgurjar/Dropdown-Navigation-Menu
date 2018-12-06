package com.example.vivek.appmodel2;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class ServicesFragment extends Fragment implements View.OnScrollChangeListener, View.OnClickListener{

    boolean isInFront;
    public static final String BAL_LINK =  "http://www.apnarecharge.in/apisms/mapp.aspx?from=@&message=3%20BAL%20"+GlobalVariable.phone;
    LinearLayout ll1,ll2,ll5,ll6,ll7,ll8,ll9,ll10,ll11,ll12,ll13,ll15;

    public void setOnServiceSelected(ServicesFragment.OnServiceSelected onServiceSelected) {
        this.onServiceSelected = onServiceSelected;
    }
    OnServiceSelected onServiceSelected;
    interface OnServiceSelected
    {
        void serviceSelected(String service);
    }


    public void setOnSwipeResult(ServicesFragment.OnSwipeResult onSwipeResult) {
        this.onSwipeResult = onSwipeResult;
    }
    OnSwipeResult onSwipeResult;
    public interface OnSwipeResult
    {
        void onSwipe(boolean isSwipeDown);
    }

    ScrollView scrollView;
    LinearLayout scrollContainer;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_services, container, false);
        // Inflate the layout for this fragment
        scrollView = view.findViewById(R.id.scrollView);
        scrollContainer = view.findViewById(R.id.scrollContainer);

        if (scrollView.isFillViewport())
        {
            scrollView.setOnScrollChangeListener(this);
        }
        else
        {

            final GestureDetector gesture = new GestureDetector(getActivity(),
                    new GestureDetector.SimpleOnGestureListener() {

                        @Override
                        public boolean onDown(MotionEvent e) {
                            return true;
                        }


                        @Override
                        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                               float velocityY) {
                            //float duration = e2.getEventTime() - e1.getEventTime();
                            //float distance = e2.getY() - e1.getY();

                            //Toast.makeText(getActivity(), "Duration " +duration+ " Distance "+distance, Toast.LENGTH_SHORT).show();



                            final int SWIPE_THRESHOLD = 120;
                            final int SWIPE_MAX_OFF_PATH = 120;
                            final int SWIPE_VELOCITY_THRESHOLD = 150;
                            //boolean result = false;
                            try {
                                if (Math.abs(e1.getX() - e2.getX()) > SWIPE_MAX_OFF_PATH)
                                    return false;

                                float diffY = e2.getY() - e1.getY();

                                //Disabled No Use....../////////////////////////////////////////////////////////////////////////////////////////

                                //float diffX = e2.getX() - e1.getX();
                                /*if (Math.abs(diffX) > Math.abs(diffY)) {
                                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                                        if (diffX > 0) {
                                            Toast.makeText(getActivity(), "Left to Right", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getActivity(), "Right to Left", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    //result = true;
                                }
                                else*/

                                if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                                    if (diffY > 0) {
                                        //Toast.makeText(getActivity(), "Top to Bottom", Toast.LENGTH_SHORT).show();
                                        onSwipeResult.onSwipe(true);
                                    } else {
                                        //Toast.makeText(getActivity(), "Bottom to Top", Toast.LENGTH_SHORT).show();
                                        onSwipeResult.onSwipe(false);
                                    }
                                    //result = true;
                                }

                            } catch (Exception e) {
                                // nothing
                            }
                            return super.onFling(e1, e2, velocityX, velocityY);
                        }
                    });

            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return gesture.onTouchEvent(event);
                }
            });

        }


        ll1 = view.findViewById(R.id.linearLayout1);
        ll2 = view.findViewById(R.id.linearLayout2);
        ll5 = view.findViewById(R.id.linearLayout5);
        ll6 = view.findViewById(R.id.linearLayout6);
        ll7 = view.findViewById(R.id.linearLayout7);
        ll8 = view.findViewById(R.id.linearLayout8);
        ll9 = view.findViewById(R.id.linearLayout9);
        ll10 = view.findViewById(R.id.linearLayout10);
        ll11 = view.findViewById(R.id.linearLayout11);
        ll12 = view.findViewById(R.id.linearLayout12);
        ll13 = view.findViewById(R.id.linearLayout13);
        ll15 = view.findViewById(R.id.linearLayout15);

        ll1.setOnClickListener(this);
        ll2.setOnClickListener(this);
        ll5.setOnClickListener(this);
        ll6.setOnClickListener(this);
        ll7.setOnClickListener(this);
        ll8.setOnClickListener(this);
        ll9.setOnClickListener(this);
        ll10.setOnClickListener(this);
        ll11.setOnClickListener(this);
        ll12.setOnClickListener(this);
        ll13.setOnClickListener(this);
        ll15.setOnClickListener(this);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
     }

    @Override
    public void onDetach() {
        super.onDetach();
     }

    @Override
    public void onResume() {
        super.onResume();
        isInFront = true;
        getBal();
    }

    @Override
    public void onPause() {
        super.onPause();
        isInFront = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.linearLayout1:
                //onServiceSelected.serviceSelected("Send");
                break;
            case R.id.linearLayout2:
                onServiceSelected.serviceSelected("AEPS");
                break;
            case R.id.linearLayout5:
                onServiceSelected.serviceSelected("Phone");
                break;
            case R.id.linearLayout6:
                onServiceSelected.serviceSelected("DTH");
                break;
            case R.id.linearLayout7:
                onServiceSelected.serviceSelected("Datacard");
                break;
            case R.id.linearLayout8:
                onServiceSelected.serviceSelected("Electricity");
                break;
            case R.id.linearLayout9:
                onServiceSelected.serviceSelected("Gas");
                break;
            case R.id.linearLayout10:
                onServiceSelected.serviceSelected("Landline");
                break;
            case R.id.linearLayout11:
                onServiceSelected.serviceSelected("Broadband");
                break;
            case R.id.linearLayout12:
                onServiceSelected.serviceSelected("Water");
                break;
            case R.id.linearLayout13:
                onServiceSelected.serviceSelected("Bus");
                break;
            case R.id.linearLayout15:
                onServiceSelected.serviceSelected("Flight");
                break;
        }
    }

    private void getBal()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, BAL_LINK, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.split("\\r?\\n").length>1)
                {
                    //toast.showToast(R.drawable.toast_back_green,"lrnght :"+);
                    String tmp = (response.split("\\r?\\n")[0]).split(",")[0];

                    if ((response.split("\\r?\\n")[0]).split(",").length>1)
                    {
                        String stat = (response.split("\\r?\\n")[0]).split(",")[1];
                        if (stat.equalsIgnoreCase("Invalid User"))
                        {
                            //invalidUser.inValidUser();
                        }
                    }
                    String bal = tmp.split("\\.")[0];
                    GlobalVariable.wallet = bal;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (v.getTop() == scrollY)
        {
            Toast.makeText(getActivity(), "Top Reached", Toast.LENGTH_SHORT).show();
        }


        //View view = this.scrollView.getChildAt(this.scrollView.getChildCount()-1);

        //View view = (View) getChildAt(getChildCount()-1);
        int diff = (v.getBottom()-(v.getHeight()+v.getScrollY()+v.getTop()));// Calculate the scrolldiff
        if( diff <= 0 ){
            // if diff is zero, then the bottom has been reached
            Toast.makeText(getActivity(), "Bottom Reached", Toast.LENGTH_SHORT).show();
        }
        this.onScrollChange(v,scrollX, scrollY, oldScrollX, oldScrollY);
    }
}
