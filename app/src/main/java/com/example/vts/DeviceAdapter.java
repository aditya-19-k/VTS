package com.example.vts;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class DeviceAdapter extends FirebaseRecyclerAdapter<DeviceModel,DeviceAdapter.DeviceViewHolder>{
    private final RecyclerViewInterface recyclerViewInterface;
    public DeviceAdapter(@NonNull FirebaseRecyclerOptions<DeviceModel> options, RecyclerViewInterface recyclerViewInterface) {
        super(options);
        this.recyclerViewInterface = recyclerViewInterface;

    }

    @Override
    protected void onBindViewHolder(@NonNull DeviceAdapter.DeviceViewHolder holder, int position, @NonNull DeviceModel model) {

        holder.deviceName.setText(model.getDeviceName());
        holder.deviceNum.setText(model.getDeviceNum());
        holder.tvLatitude.setText(model.getLatitude());
        holder.vtLongitude.setText(model.getLongitude());

        Glide.with(holder.deviceImage.getContext())
                .load(model.getDeviceImage())
                .placeholder(R.drawable.devices)
                .circleCrop()
                .error(R.drawable.error_image)
                .into(holder.deviceImage);

    }

    @NonNull
    @Override
    public DeviceAdapter.DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_items,parent,false);
        return new DeviceViewHolder(view);
    }

    public class DeviceViewHolder extends RecyclerView.ViewHolder {
        CircleImageView deviceImage;
        CardView cardView;
        TextView deviceName,deviceNum,tvLatitude,vtLongitude;

        public DeviceViewHolder(@NonNull View itemView) {
            super(itemView);

            deviceImage = itemView.findViewById(R.id.recDevImage);
            deviceName = itemView.findViewById(R.id.recDevName);
            deviceNum = itemView.findViewById(R.id.recDevNum);
            tvLatitude = itemView.findViewById(R.id.tvLatitude);
            vtLongitude = itemView.findViewById(R.id.tvLongitude);

            cardView = itemView.findViewById(R.id.recCard);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterface != null){

                        int pos = getBindingAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }

                    }

                }
            });

        }
    }
}
