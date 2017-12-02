package com.flyingburritoco.mobilemenu.ViewHolder;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.flyingburritoco.mobilemenu.Interface.ItemClickListener;
import com.flyingburritoco.mobilemenu.Model.Order;
import com.flyingburritoco.mobilemenu.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Zach on 11/30/2017.
 */
class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView itemName, itemPrice;
    public ImageView itemCount;

    private ItemClickListener itemClickListener;

    public TextView getItemName() {
        return itemName;
    }

    public void setItemName(TextView itemName) {
        this.itemName = itemName;
    }

    public TextView getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(TextView itemPrice) {
        this.itemPrice = itemPrice;
    }

    public ImageView getItemCount() {
        return itemCount;
    }

    public void setItemCount(ImageView itemCount) {
        this.itemCount = itemCount;
    }

    public CartViewHolder(View itemView) {
        super(itemView);
        itemName = itemView.findViewById(R.id.itemName);
        itemPrice = itemView.findViewById(R.id.itemPrice);
        itemCount = itemView.findViewById(R.id.itemCount);
    }

    @Override
    public void onClick(View view) {

    }
}

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder>{

    private List<Order> listData = new ArrayList<>();
    private Context context;

    public CartAdapter(List<Order> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.cart_layout, parent, false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(""+listData.get(position).getQuantity(), Color.RED);
        holder.itemCount.setImageDrawable(drawable);

        Locale locale = new Locale("en","US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        double price = (Double.parseDouble(listData.get(position).getPrice()))*(Double.parseDouble(listData.get(position).getQuantity()));
        holder.itemPrice.setText(fmt.format(price));
        holder.itemName.setText(listData.get(position).getProductName());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
