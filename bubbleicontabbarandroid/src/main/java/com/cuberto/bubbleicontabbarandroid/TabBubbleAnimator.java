package com.cuberto.bubbleicontabbarandroid;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;
import androidx.transition.ChangeBounds;
import androidx.transition.Fade;
import androidx.transition.TransitionManager;
import androidx.transition.TransitionSet;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class TabBubbleAnimator extends ViewPager.SimpleOnPageChangeListener {

    private final List<String> mFragmentTitleList = new ArrayList<>();
    private final List<Integer> mFragmentIconList = new ArrayList<>();
    private final List<Integer> mFragmentColorList = new ArrayList<>();
    private TabLayout tabLayout;
    private int unselectedColorId = Color.BLACK;

    public TabBubbleAnimator(TabLayout tabLayout) {
        this.tabLayout = tabLayout;
    }

    public void addTabItem(String title, int tabIcon, int tabColor) {
        mFragmentTitleList.add(title);
        mFragmentIconList.add(tabIcon);
        mFragmentColorList.add(tabColor);
    }

    private void getTabView(int position, TabLayout.Tab tab, boolean isSelected) {
        View view = tab.getCustomView() == null ? LayoutInflater.from(tabLayout.getContext()).inflate(R.layout.custom_tab, null) : tab.getCustomView();
        if (tab.getCustomView() == null) {
            tab.setCustomView(view);
        }
        ImageView bg = view.findViewById(R.id.bg);
        bg.setVisibility(isSelected ? VISIBLE : INVISIBLE);
        TextView tabTextView = view.findViewById(R.id.tabTextView);
        tabTextView.setTextColor(isSelected ? getColor(mFragmentColorList.get(position)) : unselectedColorId);
        tabTextView.setVisibility(isSelected ? VISIBLE : GONE);
        ImageView tabImageView = view.findViewById(R.id.tabImageView);
        tabImageView.setImageResource(mFragmentIconList.get(position));
        tabImageView.setColorFilter(isSelected ? getColor(mFragmentColorList.get(position)) : unselectedColorId, PorterDuff.Mode.SRC_ATOP);
        if (isSelected) {
            bg.setColorFilter(ContextCompat.getColor(tabLayout.getContext(), mFragmentColorList.get(position)), PorterDuff.Mode.SRC_ATOP);
            bg.setAlpha(0.2f);
            tabTextView.setText(mFragmentTitleList.get(position));
        }
    }

    public void highLightTab(int position) {
        if (tabLayout != null) {
            TransitionManager.beginDelayedTransition(tabLayout, getTransitionSet());
            for (int i = 0; i < tabLayout.getTabCount(); i++) {
                TabLayout.Tab tab = tabLayout.getTabAt(i);
                assert tab != null;
                getTabView(i, tab, i == position);
                LinearLayout layout = ((LinearLayout) ((LinearLayout) tabLayout.getChildAt(0)).getChildAt(i));
                layout.setBackground(null);
                layout.setClipChildren(false);
                layout.setClipToPadding(false);
                layout.setPaddingRelative(0, 0, 0, 0);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) layout.getLayoutParams();
                layoutParams.weight = i == position ? 1 : 0.9f;
                layout.setLayoutParams(layoutParams);
            }
        }
    }

    private TransitionSet getTransitionSet() {
        TransitionSet set = new TransitionSet();
        Fade fadeIn = new Fade();
        fadeIn.setMode(Fade.MODE_IN);
        Fade fadeOut = new Fade();
        fadeOut.setMode(Fade.MODE_OUT);
        set.addTransition(fadeIn.setDuration(500));
        set.addTransition(fadeOut.setDuration(150));
        set.addTransition(new ChangeBounds().setDuration(300));
        set.addTransition(new TextColorTransition().setDuration(100));
        set.setOrdering(TransitionSet.ORDERING_TOGETHER);
        return set;
    }

    public void onStart(TabLayout tabLayout) {
        this.tabLayout = tabLayout;
    }

    public void onStop() {
        this.tabLayout = null;
    }

    @Override
    public void onPageSelected(int position) {
        highLightTab(position);
    }

    public int getColor(@ColorRes int colorRes) {
        return ContextCompat.getColor(tabLayout.getContext(), colorRes);
    }

    public void setUnselectedColorId(int unselectedColorId) {
        this.unselectedColorId = unselectedColorId;
    }
}
