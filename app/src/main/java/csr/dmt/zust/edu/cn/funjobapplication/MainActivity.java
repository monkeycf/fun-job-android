package csr.dmt.zust.edu.cn.funjobapplication;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import csr.dmt.zust.edu.cn.funjobapplication.module.FunJobConfig;
import csr.dmt.zust.edu.cn.funjobapplication.service.api.NoteApi;
import csr.dmt.zust.edu.cn.funjobapplication.service.api.TopicApi;
import csr.dmt.zust.edu.cn.funjobapplication.service.api.UserApi;
import csr.dmt.zust.edu.cn.funjobapplication.service.core.BaseResult;
import csr.dmt.zust.edu.cn.funjobapplication.service.core.IHttpCallBack;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.note.select.NoteSelectResModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.topic.TopicInfoModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.topic.cancel.TopicCancelCollectReqModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.topic.cancel.TopicCancelCollectResModule;
import kotlin.Unit;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
//    private ActionBarDrawerToggle mActionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
//        mToolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.select_pictures
//                , R.string.app_finish);
//        mActionBarDrawerToggle.syncState();
//        mActionBarDrawerToggle.setDrawerIndicatorEnabled(true);
//        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
//        mDrawerLayout.setStatusBarBackgroundColor(ContextCompat.getColor(this, R.color.design_default_color_primary));

    }
}
