package com.flyingburritoco.mobilemenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.flyingburritoco.mobilemenu.Common.Common;
import com.flyingburritoco.mobilemenu.Model.Request;
import com.flyingburritoco.mobilemenu.ViewHolder.OrderViewHolder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrderStatus extends AppCompatActivity {

    public RecyclerView recyclerView;

    FirebaseRecyclerAdapter<Request, OrderViewHolder> adapter;

    FirebaseDatabase database;
    DatabaseReference requests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        //Initialize Firebase
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Request");

        recyclerView = (RecyclerView)findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadOrders(Common.currentUser.getPhone());
    }

    private void loadOrders(String phone) {
        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class,
                R.layout.order_layout,
                OrderViewHolder.class,
                requests.orderByChild("phone")
                    .equalTo(phone)

        ) {
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, Request model, int position) {
                String orderId = "Order Number #" + adapter.getRef(position).getKey();
                String userPhone = "Member: " + model.getPhone();
                String pickUp = "Pickup at: " + model.getAddress();

                viewHolder.txtOrderId.setText(orderId);
                viewHolder.txtOrderStatus.setText(convertCodeToStatus(model.getStatus()));
                viewHolder.txtOrderPhone.setText(userPhone);
                viewHolder.txtOrderAddress.setText(pickUp);
            }
        };
        recyclerView.setAdapter(adapter);

    }

    private String convertCodeToStatus(String status) {
        switch (status) {
            case "0":
                return "Placed";
            case "1":
                return "Cooking";
            default:
                return "Ready";
        }
    }
}
