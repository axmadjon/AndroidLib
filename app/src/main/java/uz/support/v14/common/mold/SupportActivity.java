package uz.support.v14.common.mold;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
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
import uz.support.v14.common.fragment.ContentFragment;
import uz.support.v14.common.fragment.IndexContentFragment;
import uz.support.v14.common.fragment.SupportFragment;
import uz.support.v14.common.menu.SupportOptionMenu;
import uz.support.v14.lib.slidingmenu.SlidingMenu;
import uz.support.v14.lib.slidingmenu.app.SlidingFragmentActivity;
import uz.support.v14.widget.DrawerArrow;

public class SupportActivity extends SlidingFragmentActivity {

    //----------------------------------------------------------------------------------------------

    private static final String ARG_CLASS = "mac";
    private static final String ARG_BUNDLE = "mab";

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
    private Object contentResult;

    //----------------------------------------------------------------------------------------------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBehindContentView(R.layout.s_index);
        setContentView(R.layout.s_content);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
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

        init();

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
    }

    public void setDrawableFlipper(boolean flipped, float offset) {
        this.flip = !flipped;
        drawerArrow.setFlip(flipped);
        drawerArrow.setParameter(offset);
    }

    private void init() {
        Fragment fragment = createFragment();
        if (fragment instanceof IndexContentFragment) {
            isContent = false;
            openIndexContent(fragment);
        } else if (fragment instanceof ContentFragment) {
            isContent = true;
            replaceContent((ContentFragment) fragment);
        }
    }

    //----------------------------------------------------------------------------------------------

    protected void openIndexContent(Fragment f) {
        setFragment(R.id.index_frame, f);
        setSlidingActionBarEnabled(true);
        sm.showMenu();
    }

    private void setFragment(int resId, Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(resId, fragment)
                .commit();
    }

    //----------------------------------------------------------------------------------------------

    protected void setIndexGone() {
        Display d = getWindowManager().getDefaultDisplay();
        sm.setBehindOffsetInt(d.getWidth());
    }

    protected void setIndexVisible() {
        sm.setBehindOffsetRes(R.dimen.sliding_menu_offset);
    }

    public void showIndexContent() {
        sm.showMenu();
    }

    public void setContent(boolean isContent) {
        if (isContent) {
            setIndexGone();
        } else {
            setIndexVisible();
        }
    }

    //----------------------------------------------------------------------------------------------

    public IndexContentFragment getIndexContent() {
        return (IndexContentFragment) getSupportFragmentManager().findFragmentById(R.id.index_frame);
    }

    public ContentFragment getContentFragment() {
        return (ContentFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
    }

    public SupportOptionMenu getOptionMenuFragment() {
        return (SupportOptionMenu) getSupportFragmentManager().findFragmentById(R.id.content_menu);
    }

    //----------------------------------------------------------------------------------------------

    public void popContent() {
        getSupportFragmentManager().popBackStack();
    }

    public void popContent(Object contentResult) {
        this.contentResult = contentResult;
        popContent();
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
        isMenuShow = false;
    }

    protected void onPrepareMenu() {
        setFragment(R.id.content_menu, SupportOptionMenu.newInstance());
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
            int visible = SupportOptionMenu.SHOW;
            if (isMenuShow) {
                visible = SupportOptionMenu.HIDE;
                isMenuShow = false;
            } else {
                isMenuShow = true;
            }
            getOptionMenuFragment().visibility(visible);
            return true;
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
                if (contentResult != null) {
                    contentFragment.onAboveContentPopped(contentResult);
                }
            } finally {
                contentResult = null;
            }
        }
    }

    public void onContentDestroy(SupportFragment that) {
        ContentFragment contentFragment = getContentFragment();
        if (contentFragment == that) {
            contentFragment.vsRoot = null;
            System.gc();
        }
    }
}