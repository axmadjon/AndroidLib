package uz.support.v14.app;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import uz.support.v14.R;
import uz.support.v14.lib.slidingmenu.SlidingMenu;
import uz.support.v14.lib.slidingmenu.app.SlidingFragmentActivity;
import uz.support.v14.widget.DrawerArrow;

public final class SupportActivity extends SlidingFragmentActivity {

    //----------------------------------------------------------------------------------------------

    private static final String ARG_CLASS = "uz.support.v14.app.ma.class";
    private static final String ARG_BUNDLE = "uz.support.v14.app.ma.bundle";

    private static final String ARG_DATA = "uz.support.v14.app.ma.data";
    private static final String ARG_FLIP = "uz.support.v14.app.ma.flip";
    private static final String ARG_IS_MENU_SHOW = "uz.support.v14.app.ma.menu";
    private static final String ARG_IS_CONTENT = "uz.support.v14.app.ma.content";

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
    private boolean isContent = false;
    private boolean isMenuShow = false;
    private boolean flip = true;
    private DrawerArrow drawerArrow;
    private Object popContentResult;
    private Parcelable data;
    private Toolbar toolbar;

    //----------------------------------------------------------------------------------------------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBehindContentView(R.layout.s_index);
        setContentView(R.layout.s_content);

        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        onPrepareMenu();

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
            isMenuShow = savedInstanceState.getBoolean(ARG_IS_MENU_SHOW);
        } else {
            init();
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
        if (isContent) {
            setDrawableFlipper(true, (float) 1.0);
        }
        setContent(isContent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(ARG_FLIP, flip);
        outState.putParcelable(ARG_DATA, data);
        outState.putBoolean(ARG_IS_CONTENT, isContent);
        outState.putBoolean(ARG_IS_MENU_SHOW, isMenuShow);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public void setDrawableFlipper(boolean flipped, float offset) {
        this.flip = !flipped;
        drawerArrow.setFlip(flipped);
        drawerArrow.setParameter(offset);
    }

    private void init() {
        Fragment fragment = createFragment();
        if (fragment instanceof IndexFragment) {
            openIndexContent(fragment);
            isContent = false;
        } else if (fragment instanceof ContentFragment) {
            replaceContent((ContentFragment) fragment);
            isContent = true;
        }
    }

    //----------------------------------------------------------------------------------------------

    protected void openIndexContent(Fragment f) {
        setFragment(R.id.index_frame, f);
        setSlidingActionBarEnabled(true);
        showIndexContent();
    }

    private void setFragment(int resId, Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(resId, fragment)
                .commit();
    }

    //----------------------------------------------------------------------------------------------

    public void showIndexContent() {
        sm.showMenu();
    }

    public void setContent(boolean isContent) {
        if (isContent) {
            Display d = getWindowManager().getDefaultDisplay();
            sm.setBehindOffsetInt(d.getWidth());
        } else {
            sm.setBehindOffsetRes(R.dimen.sliding_menu_offset);
        }
    }

    //----------------------------------------------------------------------------------------------

    public IndexFragment getIndexContent() {
        return (IndexFragment) getSupportFragmentManager().findFragmentById(R.id.index_frame);
    }

    public ContentFragment getContentFragment() {
        return (ContentFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
    }

    public SupportOptionMenu getOptionMenuFragment() {
        return (SupportOptionMenu) getSupportFragmentManager().findFragmentById(R.id.content_menu);
    }

    //----------------------------------------------------------------------------------------------

    public void popContent() {
        popContent(null);
    }

    public void popContent(Object popContentResult) {
        this.popContentResult = popContentResult;
        getSupportFragmentManager().popBackStack();
    }

    //----------------------------------------------------------------------------------------------

    public void replaceContent(ContentFragment content) {
        openContent(content, false);
    }

    public void addContent(ContentFragment content) {
        openContent(content, true);
    }

    public void openContent(ContentFragment content) {
        setContent(true);
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

    private void openContent(ContentFragment cf, boolean addToBackStack) {
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

        if (isContent) {
            setContent(isContent);
        }
        showContent();
        if (getOptionMenuFragment() != null) {
            getOptionMenuFragment().hide();
        }
        onPrepareMenu();
        isMenuShow = false;
    }

    protected void onPrepareMenu() {
        ContentFragment content = getContentFragment();
        if (content != null) {
            SupportOptionMenu optionMenu = content.getOptionMenu();
            if (optionMenu != null) {
                setFragment(R.id.content_menu, content.getOptionMenu());
            }
        }

    }

    //----------------------------------------------------------------------------------------------

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        View v = getCurrentFocus();

        if (v instanceof EditText) {
            View w = getCurrentFocus();
            int scrcoords[] = new int[2];
            w.getLocationOnScreen(scrcoords);
            float x = event.getRawX() + w.getLeft() - scrcoords[0];
            float y = event.getRawY() + w.getTop() - scrcoords[1];

            if (event.getAction() == MotionEvent.ACTION_UP && (x < w.getLeft() || x >= w.getRight() || y < w.getTop() || y > w.getBottom())) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
            }
            return true;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onKeyUp(int key, KeyEvent event) {
        if (KeyEvent.KEYCODE_MENU == key) {
            SupportOptionMenu optionMenu = getOptionMenuFragment();
            if (optionMenu != null) {
                int visible = SupportOptionMenu.SHOW;
                if (isMenuShow) {
                    visible = SupportOptionMenu.HIDE;
                    isMenuShow = false;
                } else {
                    isMenuShow = true;
                }
                optionMenu.visibility(visible);
                return true;
            }
        }
        return super.onKeyUp(key, event);
    }

    protected void hideSoftKeyboard() {
        View currentFocus = getCurrentFocus();
        if (currentFocus != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
    }
    //----------------------------------------------------------------------------------------------

    public void setOnSlidingListener(SlidingMenu.OnSlidingListener slidingListener) {
        sm.setOnSlidingListener(slidingListener);
    }

    public void onContentStarted(SupportFragment that) {
        SupportFragment contentFragment = getContentFragment();
        if (contentFragment == that) {
            try {
                if (popContentResult != null) {
                    contentFragment.onAboveContentPopped(popContentResult);
                }
            } finally {
                popContentResult = null;
            }
        }
    }
}