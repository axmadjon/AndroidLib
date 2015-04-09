package uz.support.v14.demo;

import android.app.Activity;
import android.os.Bundle;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DemoIndexFragment.open(this);
        finish();
    }
}