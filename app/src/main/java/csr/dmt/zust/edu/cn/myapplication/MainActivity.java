package csr.dmt.zust.edu.cn.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import csr.dmt.zust.edu.cn.myapplication.BottomNavigationBar.BottomNavigation;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationBar bottomNavigationBar =
                (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        BottomNavigation.InitBottomNavigationBar(bottomNavigationBar);
    }
}
