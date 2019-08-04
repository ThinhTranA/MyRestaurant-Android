package net.visualsharp.myrestaurant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nex3z.notificationbadge.NotificationBadge;
import com.squareup.picasso.Picasso;

import net.visualsharp.myrestaurant.Adapter.MyCategoryAdapter;
import net.visualsharp.myrestaurant.Common.Common;
import net.visualsharp.myrestaurant.Model.EventBus.MenuItemEvent;
import net.visualsharp.myrestaurant.Retrofit.IMyRestaurantAPI;
import net.visualsharp.myrestaurant.Retrofit.RetrofitClient;
import net.visualsharp.myrestaurant.Utils.SpacesItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MenuActivity extends AppCompatActivity {

    @BindView(R.id.img_restaurant)
    ImageView img_restaurant;
    @BindView(R.id.recycler_category)
    RecyclerView recycler_category;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton btn_cart;
    @BindView(R.id.badge)
    NotificationBadge badge;

    IMyRestaurantAPI myRestaurantAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    AlertDialog dialog;

    MyCategoryAdapter adapter;

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        init();
        initView();

    }

    private void initView() {
        ButterKnife.bind(this);

        btn_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Implement later
            }
        });

        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        // Select item view type
        // If item is last, set full width on Grid layout
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if(adapter != null)
                {
                    switch (adapter.getItemViewType(position))
                    {
                        case Common.DEFAULT_COLUMN_COUNT: return 1; //
                        case Common.FULL_WIDTH_COLUMN: return 2;
                        default: return -1;
                    }
                }
                else
                    return -1;
            }
        });
        recycler_category.setLayoutManager(layoutManager);
        recycler_category.addItemDecoration(new SpacesItemDecoration(8));
    }

    private void init() {
        dialog = new SpotsDialog.Builder().setContext(this).setCancelable(false).build();
        myRestaurantAPI = RetrofitClient.getInstance(Common.API_RESTAURANT_ENDPOINT).create(IMyRestaurantAPI.class);
    }

    //Event Bus
    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    //Subscribe to postSticky in MyRestaurantAdapter
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void loadMenuByRestaurant(MenuItemEvent event)
    {
        if(event.isSuccess())
        {
            Picasso.get().load(event.getRestaurant().getImage()).into(img_restaurant);
            toolbar.setTitle(event.getRestaurant().getName());

            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            //Request Category by restaurant id
            compositeDisposable.add(
                    myRestaurantAPI.getCategories(Common.API_KEY,event.getRestaurant().getId())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(menuModel -> {
                                        adapter = new MyCategoryAdapter(MenuActivity.this, menuModel.getResult());
                                        recycler_category.setAdapter(adapter);
                                    },
                                    throwable -> {
                                        Toast.makeText(this,"[GET CATEGORY]"+throwable.getMessage(),Toast.LENGTH_SHORT).show();
                                    })
            );
        }
        else
        {

        }
    }
}
