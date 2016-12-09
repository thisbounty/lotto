package com.kenzap.luckylotto;

import com.kenzap.luckylotto.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;
import java.util.Random;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class FullscreenActivity extends Activity implements ShakeEventManager.ShakeListener {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    Context mContext;
    SharedPreferences pref;
    private static final boolean AUTO_HIDE = false;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = true;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;
    private NumberPicker max_picker = null;
    private ShakeEventManager sd;

    TextView num1,num2,num3,num4,num5,num6,num7,num8,num9,num10,num11,num12,num13,num14,num15,num16,num17,num18;
    Integer nMax = 1, combo = 0;
    RadioButton radio1, radio2, radio3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);
        mContext = this;
        pref = PreferenceManager.getDefaultSharedPreferences(this);

        num1 = (TextView) findViewById(R.id.num1);
        num2 = (TextView) findViewById(R.id.num2);
        num3 = (TextView) findViewById(R.id.num3);
        num4 = (TextView) findViewById(R.id.num4);
        num5 = (TextView) findViewById(R.id.num5);
        num6 = (TextView) findViewById(R.id.num6);
        num7 = (TextView) findViewById(R.id.num7);
        num8 = (TextView) findViewById(R.id.num8);
        num9 = (TextView) findViewById(R.id.num9);
        num10 = (TextView) findViewById(R.id.num10);
        num11 = (TextView) findViewById(R.id.num11);
        num12 = (TextView) findViewById(R.id.num12);
        num13 = (TextView) findViewById(R.id.num13);
        num14 = (TextView) findViewById(R.id.num14);
        num15 = (TextView) findViewById(R.id.num15);
        num16 = (TextView) findViewById(R.id.num16);
        num17 = (TextView) findViewById(R.id.num17);
        num18 = (TextView) findViewById(R.id.num18);


        radio1 = (RadioButton) findViewById(R.id.radio1);
        radio2 = (RadioButton) findViewById(R.id.radio2);
        radio3 = (RadioButton) findViewById(R.id.radio3);

        radio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                radio1.setChecked(true);
                radio2.setChecked(false);
                radio3.setChecked(false);
                num12.setVisibility(View.GONE);
                num13.setVisibility(View.GONE);
                num14.setVisibility(View.GONE);
                num15.setVisibility(View.GONE);
                num16.setVisibility(View.GONE);
                num17.setVisibility(View.GONE);
                num18.setVisibility(View.GONE);
                combo = 0;
            }
        });

        radio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                radio1.setChecked(false);
                radio2.setChecked(true);
                radio3.setChecked(false);
                num12.setVisibility(View.VISIBLE);
                num13.setVisibility(View.VISIBLE);
                num14.setVisibility(View.GONE);
                num15.setVisibility(View.GONE);
                num16.setVisibility(View.GONE);
                num17.setVisibility(View.GONE);
                num18.setVisibility(View.GONE);
                combo = 1;
            }
        });

        radio3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                radio1.setChecked(false);
                radio2.setChecked(false);
                radio3.setChecked(true);
                num12.setVisibility(View.VISIBLE);
                num13.setVisibility(View.VISIBLE);
                num14.setVisibility(View.VISIBLE);
                num15.setVisibility(View.VISIBLE);
                num16.setVisibility(View.VISIBLE);
                num17.setVisibility(View.VISIBLE);
                num18.setVisibility(View.VISIBLE);
                combo = 2;
            }
        });
        //C.log("AAAAAAA");
        final View controlsView = findViewById(R.id.fullscreen_content_controls);
        final View contentView = findViewById(R.id.fullscreen_content);

        //INIT SENSOR
        sd = new ShakeEventManager();
        sd.setListener(this);
        sd.init(this);

        //INIT PICKER
        int max = 255;
        String[] nums = new String[max];
        max_picker = (NumberPicker) findViewById(R.id.max_picker);
        max_picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        for (int i = 0; i < max; i++) {


            nums[i] = (i+1)+"";

        }
        max_picker.setMinValue(1);
        max_picker.setMaxValue(max);
        max_picker.setDisplayedValues(nums);


        //SensorManager sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
        //sensorMgr.registerListener(this, SensorManager.SENSOR_ACCELEROMETER, SensorManager.SENSOR_DELAY_GAME);

        // Set up an instance of SystemUiHider to control the system UI for
        // this activity.
        mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS);
        mSystemUiHider.setup();
        mSystemUiHider
                .setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
                    // Cached values.
                    int mControlsHeight;
                    int mShortAnimTime;

                    @Override
                    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
                    public void onVisibilityChange(boolean visible) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                            // If the ViewPropertyAnimator API is available
                            // (Honeycomb MR2 and later), use it to animate the
                            // in-layout UI controls at the bottom of the
                            // screen.
                            if (mControlsHeight == 0) {
                                mControlsHeight = controlsView.getHeight();
                            }
                            if (mShortAnimTime == 0) {
                                mShortAnimTime = getResources().getInteger(
                                        android.R.integer.config_shortAnimTime);
                            }
                            controlsView.animate()
                                    .translationY(visible ? 0 : mControlsHeight)
                                    .setDuration(mShortAnimTime);
                        } else {
                            // If the ViewPropertyAnimator APIs aren't
                            // available, simply show or hide the in-layout UI
                            // controls.
                            controlsView.setVisibility(visible ? View.VISIBLE : View.GONE);
                        }

                        if (visible && AUTO_HIDE) {
                            // Schedule a hide().
                            delayedHide(AUTO_HIDE_DELAY_MILLIS);
                        }
                    }
                });

        // Set up the user interaction to manually show or hide the system UI.
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TOGGLE_ON_CLICK) {
                    mSystemUiHider.toggle();
                } else {
                    mSystemUiHider.show();
                }
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
    }

    @Override
    public void onPause(){

        //CACHE MAX VALUE
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("max_picker", max_picker.getValue());
        editor.putInt("combo", combo);
        editor.commit();
        super.onPause();
    }

    @Override
    public void onResume(){

        //CACHE MAX VALUE
        max_picker.setValue(pref.getInt("max_picker", 1));
        super.onResume();
        switch(pref.getInt("combo",0)){

            case 0: radio1.callOnClick(); break;
            case 1: radio2.callOnClick(); break;
            case 2: radio3.callOnClick(); break;
        }

    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(500);
    }


    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    Handler mHideHandler = new Handler();
    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            mSystemUiHider.hide();
        }
    };

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }


    private void shakeFields() {

        Random r = new Random();
        nMax = max_picker.getValue()+1;
        int dur = 200;
        int durm = 10;
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        Animation shake2 = AnimationUtils.loadAnimation(this, R.anim.shake);
        Animation shake3 = AnimationUtils.loadAnimation(this, R.anim.shake);
        shake.setDuration(700);
        shake2.setDuration(200);
        shake2.setDuration(400);
        shake.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationEnd(Animation animation) {

                //noteHint.setAlpha((float)0.5);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationStart(Animation animation) {

            }

        });
        int n = 0;
        n = r.nextInt(nMax - 1) + 1;
        C.log(nMax+"RANDOM:"+n);
  //      /*

        n = r.nextInt(nMax - 1) + 1;num1.startAnimation(shake2);shake.setDuration(dur + n * durm);num1.setText(n + "");
        n = r.nextInt(nMax - 1) + 1;num2.startAnimation(shake);shake.setDuration(dur + n * durm);num2.setText(n+"");
        n = r.nextInt(nMax - 1) + 1;num3.startAnimation(shake3);shake.setDuration(dur + n * durm);num3.setText(n+"");
        n = r.nextInt(nMax - 1) + 1;num4.startAnimation(shake2);shake.setDuration(dur + n * durm);num4.setText(n+"");
        n = r.nextInt(nMax - 1) + 1;num5.startAnimation(shake);shake.setDuration(dur + n * durm);num5.setText(n+"");
        n = r.nextInt(nMax - 1) + 1;num6.startAnimation(shake3);shake.setDuration(dur + n * durm);num6.setText(n+"");
        n = r.nextInt(nMax - 1) + 1;num7.startAnimation(shake2);shake.setDuration(dur + n * durm);num7.setText(n+"");
        n = r.nextInt(nMax - 1) + 1;num8.startAnimation(shake);shake.setDuration(dur + n * durm);num8.setText(n+"");
        n = r.nextInt(nMax - 1) + 1;num9.startAnimation(shake);shake.setDuration(dur + n * durm);num9.setText(n+"");
        n = r.nextInt(nMax - 1) + 1;num10.startAnimation(shake2);shake.setDuration(dur + n * durm);num10.setText(n+"");
        n = r.nextInt(nMax - 1) + 1;num11.startAnimation(shake);shake.setDuration(dur + n * durm);num11.setText(n+"");
        n = r.nextInt(nMax - 1) + 1;num12.startAnimation(shake3);shake.setDuration(dur + n * durm);num12.setText(n+"");
        n = r.nextInt(nMax - 1) + 1;num13.startAnimation(shake);shake.setDuration(dur + n * durm);num13.setText(n+"");
        n = r.nextInt(nMax - 1) + 1;num14.startAnimation(shake2);shake.setDuration(dur + n * durm);num14.setText(n+"");
        n = r.nextInt(nMax - 1) + 1;num15.startAnimation(shake);shake.setDuration(dur + n * durm);num15.setText(n+"");
        n = r.nextInt(nMax - 1) + 1;num16.startAnimation(shake);shake.setDuration(dur + n * durm);num16.setText(n+"");
        n = r.nextInt(nMax - 1) + 1;num17.startAnimation(shake2);shake.setDuration(dur + n * durm);num17.setText(n+"");
        n = r.nextInt(nMax - 1) + 1;num18.startAnimation(shake);shake.setDuration(dur + n * durm);num18.setText(n+"");
 //       */

    }

    private void vibrateDevice(){

        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(150);
        v.vibrate(120);
    }

    @Override
    public void onShake() {

        shakeFields();
        vibrateDevice();
    }
}
