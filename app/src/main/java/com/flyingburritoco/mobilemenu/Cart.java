package com.flyingburritoco.mobilemenu;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.flyingburritoco.mobilemenu.Common.Common;
import com.flyingburritoco.mobilemenu.Database.Database;
import com.flyingburritoco.mobilemenu.Model.Order;
import com.flyingburritoco.mobilemenu.Model.Request;
import com.flyingburritoco.mobilemenu.ViewHolder.CartAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import info.hoang8f.widget.FButton;

public class Cart extends AppCompatActivity {


    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;

    TextView txtTotalPrice;
    FButton btnPlace;
    FButton btnCancel;

    List<Order> cart = new ArrayList<>();
    CartAdapter adapter;


    String cartStatus="empty";

    String storeLocation="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //Firebase Initialization
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Request");

        recyclerView = (RecyclerView)findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice = (TextView)findViewById(R.id.total);
        btnPlace = (FButton)findViewById(R.id.btnPlaceOrder);
        btnCancel = (FButton)findViewById(R.id.btnCancelOrder);

        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Objects.equals(cartStatus, "full")) {
                    showPlaceDialog();
                }
                else{
                    Toast.makeText(Cart.this, "Your Cart Is Empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCancelDialog();
            }
        });

        loadListFood();
    }

    private void showCancelDialog(){
        AlertDialog.Builder alertDialog =new AlertDialog.Builder(Cart.this);
        alertDialog.setTitle("Cancel Order");
        alertDialog.setMessage("Would you like to cancel your order?");

        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);

        alertDialog.setPositiveButton("Continue Order", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertDialog.setNegativeButton("Cancel Order", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                new Database(getBaseContext()).cleanCart();
                Toast.makeText(Cart.this, "Your Order Has Been Cancelled", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        alertDialog.show();
    }
    private void showPlaceDialog(){
        final Dialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Location");
        builder.setSingleChoiceItems(R.array.locations, 0, null)
                .setIcon(R.drawable.ic_restaurant_black_24dp)
                .setPositiveButton("ENTER", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int selectedPosition = ((AlertDialog)dialogInterface).getListView().getCheckedItemPosition();
                        convertLocation(selectedPosition);
                        submitOrder();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        dialog = builder.create();
        dialog.show();
    }

    private void convertLocation(int selectedPosition){
        String[] locationList = getResources().getStringArray(R.array.locations);
        storeLocation = locationList[selectedPosition];


    }
    private void submitOrder(){
        Request request = new Request(
                Common.currentUser.getPhone(),
                Common.currentUser.getName(),
                storeLocation,
                txtTotalPrice.getText().toString(),
                cart
        );

        requests.child(String.valueOf(System.currentTimeMillis()))
                .setValue(request);

        new Database(getBaseContext()).cleanCart();
        Toast.makeText(Cart.this, "Your Order Has Been Placed", Toast.LENGTH_SHORT).show();
        finish();
    }
    private void loadListFood(){
        cart = new Database(this).getCarts();
        adapter = new CartAdapter(cart, this);
        recyclerView.setAdapter(adapter);

        //Calculate Total Price
        int total = 0;
        for(Order order:cart){
            cartStatus = "full";
            total += (Double.parseDouble(order.getPrice())) * (Double.parseDouble(order.getQuantity()));
        }
        Locale locale = new Locale("en","US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        txtTotalPrice.setText(fmt.format(total));
    }
}
