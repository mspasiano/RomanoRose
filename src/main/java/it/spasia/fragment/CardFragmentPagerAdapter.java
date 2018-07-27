package it.spasia.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.View;

import it.spasia.R;
import it.spasia.activity.Tabbed;

public class CardFragmentPagerAdapter extends FragmentPagerAdapter {
    private Tabbed tabbed;

    public CardFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    public CardFragmentPagerAdapter(Tabbed context, FragmentManager fm) {
        super(fm);
        tabbed = context;
    }

    @Override
    public Fragment getItem(int position) {
        CardFragment cardFragment = new CardFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("card", tabbed.getCards().get(position));
        cardFragment.setArguments(bundle);
        return cardFragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabbed.getCards().get(position).getName();
    }

    @Override
    public int getCount() {
        return tabbed.getCards().size();
    }

}
