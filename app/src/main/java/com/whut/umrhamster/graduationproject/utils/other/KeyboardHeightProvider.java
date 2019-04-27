package com.whut.umrhamster.graduationproject.utils.other;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.whut.umrhamster.graduationproject.R;
import com.whut.umrhamster.graduationproject.utils.other.KeyboardHeightObserver;

public class KeyboardHeightProvider extends PopupWindow {
    /** The tag for logging purposes */
    private final static String TAG = "sample_KeyboardHeightProvider";

    /** The keyboard height observer */
    private KeyboardHeightObserver observer;

    /** The cached landscape height of the keyboard */
    private int keyboardLandscapeHeight;

    /** The cached portrait height of the keyboard */
    private int keyboardPortraitHeight;

    /** The view that is used to calculate the keyboard height */
    private View popupView;

    /** The parent view */
    private View parentView;

    /** The root activity that uses this KeyboardHeightProvider */
    private Activity activity;

    /**
     * Construct a new KeyboardHeightProvider
     *
     * @param activity The parent activity
     */
    public KeyboardHeightProvider(Activity activity) {
        super(activity);
        this.activity = activity;

        LayoutInflater inflator = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        this.popupView = inflator.inflate(R.layout.popupwindow, null, false);
        setContentView(popupView);

        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);

        parentView = activity.findViewById(android.R.id.content);

        setWidth(0);
        setHeight(WindowManager.LayoutParams.MATCH_PARENT);

        popupView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                if (popupView != null) {
                    handleOnGlobalLayout();
                }
            }
        });
    }

    /**
     * Start the KeyboardHeightProvider, this must be called after the onResume of the Activity.
     * PopupWindows are not allowed to be registered before the onResume has finished
     * of the Activity.
     */
    public void start() {

        if (!isShowing() && parentView.getWindowToken() != null) {
            setBackgroundDrawable(new ColorDrawable(0));
            showAtLocation(parentView, Gravity.NO_GRAVITY, 0, 0);
        }
    }

    /**
     * Close the keyboard height provider,
     * this provider will not be used anymore.
     */
    public void close() {
        this.observer = null;
        dismiss();
    }

    /**
     * Set the keyboard height observer to this provider. The
     * observer will be notified when the keyboard height has changed.
     * For example when the keyboard is opened or closed.
     *
     * @param observer The observer to be added to this provider.
     */
    public void setKeyboardHeightObserver(KeyboardHeightObserver observer) {
        this.observer = observer;
        keyboardPortraitHeight = 0;
        keyboardLandscapeHeight = 0;
    }

    /**
     * Get the screen orientation
     *
     * @return the screen orientation
     */
    private int getScreenOrientation() {
        return activity.getResources().getConfiguration().orientation;
    }

    /**
     * Popup window itself is as big as the window of the Activity.
     * The keyboard can then be calculated by extracting the popup view bottom
     * from the activity window height.
     */
    private void handleOnGlobalLayout() {

        Point screenSize = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(screenSize);

        Rect rect = new Rect();
        popupView.getWindowVisibleDisplayFrame(rect);

        // REMIND, you may like to change this using the fullscreen size of the phone
        // and also using the status bar and navigation bar heights of the phone to calculate
        // the keyboard height. But this worked fine on a Nexus.
        int orientation = getScreenOrientation();
        int keyboardHeight = screenSize.y - rect.bottom;

        WindowManager manager = activity.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width2 = outMetrics.widthPixels;
        int height2 = outMetrics.heightPixels;

        int statusBarHeight = 0;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);
        }
        Log.d("适配测试",rect.height()+" "+rect.width()+" "+width2+" "+height2+" "+statusBarHeight+"  "+keyboardHeight+" "+" "+keyboardPortraitHeight+" "+keyboardLandscapeHeight);


        if (orientation == Configuration.ORIENTATION_LANDSCAPE){
            Log.d("testing","landscpe");
            if (keyboardHeight <= 0 && keyboardLandscapeHeight > 0){
                notifyKeyboardHeightChanged(keyboardHeight,orientation,false);
                this.keyboardLandscapeHeight = keyboardHeight;
            }else if (keyboardHeight > 200 && keyboardLandscapeHeight <= 0){
                notifyKeyboardHeightChanged(keyboardHeight,orientation,true);
                this.keyboardLandscapeHeight = keyboardHeight;
            }
        }else if (orientation == Configuration.ORIENTATION_PORTRAIT){
            Log.d("testing","portrrait"+" "+keyboardHeight+" "+keyboardPortraitHeight);
            if (keyboardHeight <= 0 && keyboardPortraitHeight > 0){
                notifyKeyboardHeightChanged(keyboardHeight,orientation,false);
                this.keyboardPortraitHeight = keyboardHeight;
            }else if (keyboardHeight > 200 && keyboardPortraitHeight <= 0){
                notifyKeyboardHeightChanged(keyboardHeight,orientation,true);
                this.keyboardPortraitHeight = keyboardHeight;
            }
        }
//        if (keyboardHeight == 0) {
//            if (keyboardPortraitHeight > 0 || keyboardLandscapeHeight > 0){
//                notifyKeyboardHeightChanged(0, orientation,false);
//            }
//        }
//        else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
//            this.keyboardPortraitHeight = keyboardHeight;
//            notifyKeyboardHeightChanged(keyboardPortraitHeight, orientation, true);
//        }
//        else {
//            this.keyboardLandscapeHeight = keyboardHeight;
//            notifyKeyboardHeightChanged(keyboardLandscapeHeight, orientation,true);
//        }
    }

    /**
     *
     */
    private void notifyKeyboardHeightChanged(int height, int orientation, boolean status) {
        if (observer != null) {
            observer.onKeyboardHeightChanged(height, orientation,status);
        }
    }
}