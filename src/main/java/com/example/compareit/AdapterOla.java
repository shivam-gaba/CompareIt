package com.example.compareit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterOla extends RecyclerView.Adapter<AdapterOla.ViewHolder> {

    // adapter adapts the its view and its individual contents


    ArrayList<OlaListClass> olaList;

    public AdapterOla(Context context,ArrayList<OlaListClass> olaList)
    {
        this.olaList=olaList;
    }


//viewHolder will have each item in the list
    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView tvCabName,tvCabFare;
        ImageView ivCabType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCabName=itemView.findViewById(R.id.tvCabName);
            tvCabFare=itemView.findViewById(R.id.tvCabFare);
            ivCabType=itemView.findViewById(R.id.ivCabType);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(),tvCabName.getText().toString()+" : "+tvCabFare.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @NonNull
    @Override
    public AdapterOla.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cab_list_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterOla.ViewHolder holder, final int position) {


        class Fare
        {

            @SuppressLint("DefaultLocale")
            private void getFare(double baseFare, double minFare, double perKM, double perMin,double tax)
            {
                double distance,minutes;

                distance=olaList.get(position).getDistance();
                minutes=olaList.get(position).getMinutes();

                double totalFare = baseFare+(perKM*distance)+(perMin*minutes);

                if(totalFare>minFare)
                {
                    holder.tvCabFare.setText(String.format("%.2f", totalFare)+" ₹");
                }
                else
                {
                    holder.tvCabFare.setText(minFare+" ₹");
                }
            }
        }


        holder.itemView.setTag(olaList.get(position));
        holder.tvCabName.setText(olaList.get(position).getCabType());

        if(olaList.get(position).getCabType().equalsIgnoreCase("mini"))
        {
            holder.ivCabType.setImageResource(R.drawable.olamini);
            Fare obj=new Fare();
            obj.getFare(43.46,43.46,8.2,1.24,16.24);
        }
        else if(olaList.get(position).getCabType().equalsIgnoreCase("micro"))
        {
            holder.ivCabType.setImageResource(R.drawable.olamicro);
            Fare obj=new Fare();
            obj.getFare(37.5,37.5,7.5,1.5,15.39);
        }
        else if(olaList.get(position).getCabType().equalsIgnoreCase("auto"))
        {
            holder.ivCabType.setImageResource(R.drawable.olaauto);
            Fare obj=new Fare();
            obj.getFare(0,0,0,0,0);
        }
        else if(olaList.get(position).getCabType().equalsIgnoreCase("bike"))
        {
            holder.ivCabType.setImageResource(R.drawable.olabike);
            Fare obj=new Fare();
            obj.getFare(0,0,0,0,0);
        }
        else if(olaList.get(position).getCabType().equalsIgnoreCase("share"))
        {
            holder.ivCabType.setImageResource(R.drawable.olashare);
            Fare obj=new Fare();
            obj.getFare(0,0,0,0,0);
        }
        else if(olaList.get(position).getCabType().equalsIgnoreCase("Prime Sedan"))
        {
            holder.ivCabType.setImageResource(R.drawable.olaprimesedan);
            Fare obj=new Fare();
            obj.getFare(36,36,6.75,0.9,13.08);
        }
        else if(olaList.get(position).getCabType().equalsIgnoreCase("Prime Exec"))
        {
            holder.ivCabType.setImageResource(R.drawable.olaprimeexec);
            Fare obj=new Fare();
            obj.getFare(49,49,10.5,1.25,18.36);
        }
        else if(olaList.get(position).getCabType().equalsIgnoreCase("Prime suv"))
        {
            holder.ivCabType.setImageResource(R.drawable.olaprimesuv);
            Fare obj=new Fare();
            obj.getFare(99,99,14,2,25.65);
        }
    }

    @Override
    public int getItemCount() {
        return olaList.size();
    }
}
