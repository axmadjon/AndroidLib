package uz.support.v14.app;


import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import uz.support.v14.R;
import uz.support.v14.app.content.ContentFragment;
import uz.support.v14.app.index.IndexFragment;
import uz.support.v14.collection.MyArray;
import uz.support.v14.lib.slidingmenu.SlidingMenu;
import uz.support.v14.lib.slidingmenu.app.SlidingFragmentActivity;
import uz.support.v14.widget.DrawerArrow;

public final class SupportActivity extends SlidingFragmentActivity {

    //----------------------------------------------------------------------------------------------

    private static final String ARG_CLASS = "uz.support.v14.app.class";
    private static final String ARG_BUNDLE = "uz.support.v14.app.bundle";

    private static final String ARG_DATA = "uz.support.v14.app.data";
    private static final String ARG_FLIP = "uz.support.v14.app.flip";
    private static final String ARG_IS_CONTENT = "uz.support.v14.app.content";

    //----------------------------------------------------------------------------------------------

    private static void open(Activity activity, Class<? extends Fragment> cls, Bundle args) {
        Intent i = new Intent(activity, SupportActivity.class);
        i.putExtra(ARG_CLASS, cls);
        if (args != null) {
            i.putExtra(ARG_BUNDLE, args);
        }
        activity.startActivity(i);
    }

    public static void openIndex(Activity ctx, Class<? extends SupportFragment> cls, Bundle args) {
        open(ctx, cls, args);
    }

    public static void openContent(Activity ctx, Class<? extends SupportFragment> cls, Bundle args) {
        open(ctx, cls, args);
    }

    //----------------------------------------------------------------------------------------------

    private SlidingMenu sm;
    private DrawerArrow drawerArrow;
    private Toolbar toolbar;
    private MyArray<SupportMenuContainer> mMenuItems;
    private int mMenuIdSeq;

    private Object popContentResult;
    private Parcelable data;
    private boolean flip = true;
    private boolean isContent = false;

    //----------------------------------------------------------------------------------------------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBehindContentView(R.layout.s_index);
        setContentView(R.layout.s_content);
        mMenuIdSeq = 10;

        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        Resources resources = getResources();
        drawerArrow = new DrawerArrow(resources);
        drawerArrow.setStrokeColor(resources.getColor(R.color.white));
        toolbar.setTitleTextColor(resources.getColor(R.color.white));
        toolbar.setNavigationIcon(drawerArrow);

        sm = getSlidingMenu();
        sm.setShadowWidthRes(R.dimen.shadow_width);
        sm.setShadowDrawable(R.drawable.shadow);
        sm.setBehindOffsetRes(R.dimen.sliding_menu_offset);
        sm.setFadeDegree(0.35f);
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

        if (savedInstanceState != null) {
            data = savedInstanceState.getParcelable(ARG_DATA);
            flip = savedInstanceState.getBoolean(ARG_FLIP);
            isContent = savedInstanceState.getBoolean(ARG_IS_CONTENT);
        } else {
            Fragment fragment = createFragment();
            if (fragment instanceof IndexFragment) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.index_frame, fragment)
                        .commit();
                setSlidingActionBarEnabled(true);
                showIndexContent();
                isContent = false;
            } else if (fragment instanceof ContentFragment) {
                replaceContent((ContentFragment) fragment);
                isContent = true;
            }
        }

        setOnSlidingListener(new SlidingMenu.OnSlidingListener() {
            @Override
            public void onListening(int position, float positionOffset, int positionOffsetPixels) {
                if (flip) {
                    String s = Float.toString(positionOffset);
                    float flo = Float.parseFloat(s.substring(1));
                    if (flo == 1) {
                        drawerArrow.setFlip(true);
                    } else if (flo >= 0) {
                        drawerArrow.setFlip(false);
                    }
                    drawerArrow.setParameter(flo);
                }
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isContent) {
                    onBackPressed();
                } else {
                    if (sm.isMenuShowing()) {
                        showContent();
                    } else {
                        showIndexContent();
                    }
                }
            }
        });
        setContent();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(ARG_FLIP, flip);
        outState.putParcelable(ARG_DATA, data);
        outState.putBoolean(ARG_IS_CONTENT, isContent);
    }

    public Parcelable getData() {
        return data;
    }

    public void setData(Parcelable data) {
        this.data = data;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public void setDrawableFlipper(boolean flipped, float offset) {
        this.flip = !flipped;
        drawerArrow.setFlip(flipped);
        drawerArrow.setParameter(offset);
    }


    //----------------------------------------------------------------------------------------------

    public void showIndexContent() {
        sm.showMenu();
    }

    public void setContent() {
        if (isContent) {
            Display d = getWindowManager().getDefaultDisplay();
            sm.setBehindOffsetInt(d.getWidth());
            setDrawableFlipper(true, (float) 1.0);
        } else {
            sm.setBehindOffsetRes(R.dimen.sliding_menu_offset);
            setDrawableFlipper(false, (float) 0.0);
        }
    }

    //----------------------------------------------------------------------------------------------

    public IndexFragment getIndexContent() {
        return (IndexFragment) getSupportFragmentManager().findFragmentById(R.id.index_frame);
    }

    public ContentFragment getContentFragment() {
        return (ContentFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
    }

    //----------------------------------------------------------------------------------------------

    public void popContent(Object popContentResult) {
        this.popContentResult = popContentResult;
        getSupportFragmentManager().popBackStack();
    }

    //----------------------------------------------------------------------------------------------

    public void replaceContent(ContentFragment content) {
        createContent(content, false);
    }

    public void addContent(ContentFragment content) {
        createContent(content, true);
    }

    public void openContent(ContentFragment content) {
        addContent(content);
    }

    //----------------------------------------------------------------------------------------------

    private Fragment createFragment() {
        try {
            Bundle extras = getIntent().getExtras();
            Bundle args = extras.getBundle(ARG_BUNDLE);

            Class<?> cls = (Class<?>) extras.getSerializable(ARG_CLASS);
            Fragment fragment = (Fragment) cls.newInstance();
            if (args != null) {
                fragment.setArguments(args);
            }
            return fragment;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void createContent(ContentFragment cf, boolean addToBackStack) {
        hideSoftKeyboard();
        if (!addToBackStack) {
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, cf);
        if (addToBackStack) {
            ft.addToBackStack(null);
        }
        ft.commit();
        showContent();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        this.mMenuItems = MyArray.emptyArray();

        MyArray<SupportMenuContainer> menuItems = MyArray.emptyArray();

        ContentFragment contentFragment = getContentFragment();
        if (contentFragment != null) {
            menuItems.append(MyArray.from(contentFragment.getMenus()));
        }

        for (SupportMenuContainer m : menuItems) {
            if (m.id == 0) {
                m.id = mMenuIdSeq++;
            }
            MenuItem menuItem = menu.add(Menu.NONE, m.id, Menu.NONE, "");
            if (m.view != null) {
                MenuItemCompat.setActionView(menuItem, m.view);
            } else {
                menuItem.setIcon(m.iconResId);
            }
            MenuItemCompat.setShowAsAction(menuItem, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
        }

        this.mMenuItems = menuItems;
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        for (SupportMenuContainer m : mMenuItems) {
            if (m.id == id) {
                if (m.command != null) {
                    m.command.apply();
                }
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }


    public void onContentStarted(SupportFragment that) {
        SupportFragment contentFragment = getContentFragment();
        if (contentFragment == that) {
            try {
                supportInvalidateOptionsMenu();
                if (popContentResult != null) {
                    contentFragment.onAboveContentPopped(popContentResult);
                }
                if (contentFragment instanceof ContentFragment) {
                    ContentFragment cf = (ContentFragment) contentFragment;
                    this.isContent = cf.isContent();
                    setContent();
                }
            } finally {
                popContentResult = null;
            }
        }
    }
}