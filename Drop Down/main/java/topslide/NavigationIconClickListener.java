package com.example.vivek.appmodel2;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Path;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.ImageView;


public class NavigationIconClickListener implements View.OnClickListener {
    private final AnimatorSet animatorSet = new AnimatorSet();
    private Context context;
    private View sheet;
    private Interpolator interpolator;
    private int height;
    private int width;
    private boolean backdropShown = false;
    private Drawable openIcon;
    private Drawable closeIcon;
    private Path path = new Path();

    NavigationIconClickListener(Context context, View sheet, int height) {
        this(context, sheet, height, null);
    }

    NavigationIconClickListener(Context context, View sheet, int height,@Nullable Interpolator interpolator) {
        this(context, sheet, height, interpolator, null, null);
    }

    NavigationIconClickListener(
            Context context, View sheet, int height, @Nullable Interpolator interpolator,
            @Nullable Drawable openIcon, @Nullable Drawable closeIcon) {
        this.context = context;
        this.sheet = sheet;
        this.height = height;
        this.interpolator = interpolator;
        this.openIcon = openIcon;
        this.closeIcon = closeIcon;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        //height = displayMetrics.heightPixels;
        //width = displayMetrics.widthPixels;


        //height = holder.getHeight();
    }

    @Override
    public void onClick(View view) {
        backdropShown = !backdropShown;
        GlobalVariable.isMenuOpen = backdropShown;

        // Cancel the existing animations
        animatorSet.removeAllListeners();
        animatorSet.end();
        animatorSet.cancel();

        updateIcon(view);

        /*final int translateY = height -
                context.getResources().getDimensionPixelSize(R.dimen.reveal_hight);
        final int translateX = width -
                context.getResources().getDimensionPixelSize(R.dimen.reveal_width);*/

        final int translateY = height+5;


        //Path for Left to Right Motion///////////////////////////////////////////////
        /*if (backdropShown)
        {
            if (!path.isEmpty())
            {
                path.reset();
            }
            path.lineTo(300f,300f);
        }
        else
        {
            path.reset();
            path.moveTo(300f,300f);
            path.lineTo(0f,0f);
        }*/



        ObjectAnimator animator = ObjectAnimator.ofFloat(sheet, "translationY", backdropShown ? translateY : 0);
        animator.setDuration(500);

        //ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(sheet,"translationX","translationY", path);
        //objectAnimator.setDuration(500);

        if (interpolator != null) {
            animator.setInterpolator(interpolator);
            //objectAnimator.setInterpolator(interpolator);
        }
        animatorSet.play(animator);
        animator.start();

        /*animatorSet.play(objectAnimator);
        objectAnimator.start();*/
    }

    private void updateIcon(View view) {
        if (openIcon != null && closeIcon != null) {
            if (!(view instanceof ImageView)) {
                throw new IllegalArgumentException("updateIcon() must be called on an ImageView");
            }
            if (backdropShown) {
                ((ImageView) view).setImageDrawable(closeIcon);
            } else {
                ((ImageView) view).setImageDrawable(openIcon);
            }
            AnimatedVectorDrawable vectorDrawable = (AnimatedVectorDrawable) ((ImageView)view).getDrawable();
            vectorDrawable.start();

        }
    }
}
