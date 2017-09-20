package com.huangdefa.todaynews.Fragment;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;

/**
 * Created by ken.huang on 9/20/2017.
 * 主界面的Fragment管理
 */

public class FragmentManager {
    private FragmentManager() {
    }

    private static FragmentManager sManager;

    static {
        sManager = new FragmentManager();
    }

    public static FragmentManager getManager() {
        return sManager;
    }

    private android.support.v4.app.FragmentManager mManager;
    private int mContentId;

    private int mCurrentTabIndex = -1;

    public FragmentManager init(android.support.v4.app.FragmentManager manager, @IdRes int contentId) {
        this.mManager = manager;
        this.mContentId = contentId;
        return this;
    }

    public void showFragment(int tabIndex) {
        if (mCurrentTabIndex == tabIndex) return;
        Fragment fragment = this.mManager.findFragmentByTag(tabIndex + "");
        if (fragment == null) {
            switch (tabIndex) {
                case 0:
                    fragment = new MainPageFragment();
                    break;
                case 1:
                    fragment = new VideoFragment();
                    break;
                case 2:
                    fragment = new ShortVideoFragment();
                    break;
                case 3:
                    fragment = new AboutFragment();
                    break;
            }
            if(mCurrentTabIndex==-1) {
                this.mManager.beginTransaction()
                        .add(mContentId, fragment, tabIndex + "")
                        .addToBackStack(null)
                        .commit();
            }else {
                this.mManager.beginTransaction()
                        .add(mContentId, fragment, tabIndex + "")
                        .addToBackStack(null)
                        .hide(this.mManager.findFragmentByTag(mCurrentTabIndex + ""))
                        .commit();
            }
        } else {
            this.mManager.beginTransaction()
                    .show(fragment)
                    .hide(this.mManager.findFragmentByTag(mCurrentTabIndex + ""))
                    .commit();
        }
        mCurrentTabIndex = tabIndex;
    }

    public void hideFragment(Class clazz) {
        Fragment fragment = this.mManager.findFragmentByTag(clazz.getSimpleName());
        this.mManager.beginTransaction()
                .hide(fragment)
                .commit();
    }


}
