package com.dpzislamic.girlspic.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.dpzislamic.girlspic.ui.EnglishCategory.EnglishCategoryFragment;
import com.dpzislamic.girlspic.ui.GifFrag.GifFragment;
import com.dpzislamic.girlspic.ui.HindiCategory.HindiCategoryFragment;
import com.dpzislamic.girlspic.ui.ImageCategories.ImageFragment;
import com.dpzislamic.girlspic.ui.LatestDP.LatestDPFragment;
import com.dpzislamic.girlspic.ui.TrendingDP.TrendingDpFragment;


public class PageAdapter extends FragmentPagerAdapter
{
    int tabcount;

    public PageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabcount=behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position)
    {
        switch (position)
        {
            case 0 : return new LatestDPFragment();
            case 1 : return new ImageFragment();
            case 2 : return new TrendingDpFragment();
            case 3 : return new GifFragment();
            case 4 : return new HindiCategoryFragment();
            case 5 : return new EnglishCategoryFragment();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return tabcount;
    }
}
