package com.flyingburritoco.mobilemenu;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.flyingburritoco.mobilemenu.Common.Common;
import com.flyingburritoco.mobilemenu.Database.Database;
import com.flyingburritoco.mobilemenu.Model.Food;
import com.flyingburritoco.mobilemenu.Model.Order;
import com.flyingburritoco.mobilemenu.Model.Request;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FoodDetail extends AppCompatActivity {

    TextView food_name, food_price, food_description;
    ImageView food_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    ElegantNumberButton numberButton;

    String foodId="";
    String extra="";

    FirebaseDatabase database;
    DatabaseReference foods;

    Food currentFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        //Firebase Initialization
        database = FirebaseDatabase.getInstance();
        foods = database.getReference("Food");

        //View Initialization
        food_name = (TextView)findViewById(R.id.food_name);
        food_price = (TextView)findViewById(R.id.food_price);
        food_description = (TextView)findViewById(R.id.food_description);

        food_image = (ImageView)findViewById(R.id.img_food);
        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);

        btnCart = (FloatingActionButton)findViewById(R.id.btnCart);
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                extraDialog();
                //extraIngredients();
            }
        });
        numberButton = (ElegantNumberButton)findViewById(R.id.number_button);

        //Handle Intent Extras
        if(getIntent() != null){
            foodId = getIntent().getStringExtra("FoodId");
        }
        if(!foodId.isEmpty()) {
            getDetailFood(foodId);
        }
    }

    public void extraDialog(){
        Dialog dialog;
        final ArrayList extraSelected = new ArrayList();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Extras!");
        builder.setMultiChoiceItems(R.array.extras, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                if(b){
                    extraSelected.add(i);
                }
                else if(extraSelected.contains(i)){
                    extraSelected.remove(Integer.valueOf(i));
                }
            }
        })
                .setIcon(R.drawable.ic_restaurant_black_24dp)
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        convertExtras(extraSelected);
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

    private void convertExtras(ArrayList extraSelected){
        for(Object element : extraSelected){
            String[] extraList = getResources().getStringArray(R.array.extras);
            if (extra.isEmpty()){
                extra = String.valueOf(extraList[(int) element]);
            }
            else {
                extra += (", " + extraList[(int) element]);
            }
        }
    }

    private void submitOrder() {
        new Database(getBaseContext()).addToCart(new Order(
                foodId,
                currentFood.getName(),
                extra,
                numberButton.getNumber(),
                currentFood.getPrice(),
                currentFood.getDiscount()
        ));
        Toast.makeText(FoodDetail.this, "Added to Cart", Toast.LENGTH_SHORT).show();

        Intent menuIntent = new Intent(FoodDetail.this, Home.class);
        startActivity(menuIntent);
    }

    private void getDetailFood(String foodId){
        foods.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentFood = dataSnapshot.getValue(Food.class);

                Picasso.with(getBaseContext()).load(currentFood.getImage()).into(food_image);

                collapsingToolbarLayout.setTitle(currentFood.getName());
                food_price.setText(currentFood.getPrice());
                food_name.setText(currentFood.getName());
                food_description.setText(currentFood.getDescription());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
