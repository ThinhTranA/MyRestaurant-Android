package net.visualsharp.myrestaurant.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import net.visualsharp.myrestaurant.Common.Common;
import net.visualsharp.myrestaurant.Interface.IOnRecyclerViewClickListener;
import net.visualsharp.myrestaurant.Model.Category;
import net.visualsharp.myrestaurant.R;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyCategoryAdapter extends RecyclerView.Adapter<MyCategoryAdapter.MyViewHolder> {

    Context context;
    List<Category> categoryList;

    public MyCategoryAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.layout_category,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Picasso.get().load(categoryList.get(position).getImage()).into(holder.img_category);
        holder.txt_categotry.setText(categoryList.get(position).getName());

        holder.setListener(new IOnRecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                //TODO: Implement later
                Toast.makeText(context,""+categoryList.get(position).getName(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.img_category)
        ImageView img_category;
        @BindView(R.id.txt_category)
        TextView txt_categotry;

        IOnRecyclerViewClickListener listener;

        public void setListener(IOnRecyclerViewClickListener listener) {
            this.listener = listener;
        }

        Unbinder unbinder;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            unbinder = ButterKnife.bind(this,itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v,getAdapterPosition());
        }

    }

    @Override
    public int getItemViewType(int position) {
        if(categoryList.size() == 1)
            return Common.DEFAULT_COLUMN_COUNT;
        else
        {
            if(categoryList.size() % 2 == 0)
                return Common.DEFAULT_COLUMN_COUNT;
            else
                return (position > 1 && position == categoryList.size()- 1) ? Common.FULL_WIDTH_COLUMN : Common.DEFAULT_COLUMN_COUNT;
        }
    }
}
