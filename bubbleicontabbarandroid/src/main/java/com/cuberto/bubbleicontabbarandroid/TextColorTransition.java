package com.cuberto.bubbleicontabbarandroid;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.transition.Transition;
import androidx.transition.TransitionValues;

public class TextColorTransition extends Transition {
    private static final String PROPNAME_TEXT_COLOR = "kvest:textColorTransition:textColor";
    private static final String[] TRANSITION_PROPERTIES = {PROPNAME_TEXT_COLOR};

    public TextColorTransition() {
    }

    public TextColorTransition(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Nullable
    @Override
    public String[] getTransitionProperties() {
        return TRANSITION_PROPERTIES;
    }

    @Override
    public void captureEndValues(@NonNull TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override
    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    private void captureValues(@NonNull TransitionValues transitionValues) {
        if (transitionValues.view instanceof TextView) {
            int color = ((TextView) transitionValues.view).getCurrentTextColor();
            transitionValues.values.put(PROPNAME_TEXT_COLOR, color);
        }
        if (transitionValues.view instanceof ImageView) {
            int color = transitionValues.view.getSolidColor();
            transitionValues.values.put(PROPNAME_TEXT_COLOR, color);
        }
    }

    @Nullable
    @Override
    public Animator createAnimator(@NonNull ViewGroup sceneRoot, @Nullable TransitionValues startValues,
                                   @Nullable TransitionValues endValues) {
        if (startValues == null || endValues == null) {
            return null;
        }

        final Integer startTextColor = (Integer)startValues.values.get(PROPNAME_TEXT_COLOR);
        final Integer endTextColor = (Integer)endValues.values.get(PROPNAME_TEXT_COLOR);
        final TextView textView = (TextView)endValues.view;
        final ArgbEvaluator argbEvaluator = new ArgbEvaluator();

        ValueAnimator animator = ValueAnimator.ofFloat(0, 1.0f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int color = (Integer) argbEvaluator.evaluate(animation.getAnimatedFraction(), startTextColor, endTextColor);
                textView.setTextColor(color);
            }
        });

        return animator;
    }
}
