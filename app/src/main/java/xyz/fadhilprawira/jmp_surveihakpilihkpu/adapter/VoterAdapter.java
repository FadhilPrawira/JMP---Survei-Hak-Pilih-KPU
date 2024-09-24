package xyz.fadhilprawira.jmp_surveihakpilihkpu.adapter;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import xyz.fadhilprawira.jmp_surveihakpilihkpu.R;
import xyz.fadhilprawira.jmp_surveihakpilihkpu.entity.Voter;

public class VoterAdapter extends RecyclerView.Adapter<VoterAdapter.ListViewHolder> {
    private final ArrayList<Voter> listVoter;

    public VoterAdapter(ArrayList<Voter> list) {
        this.listVoter = list;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_voter, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        Voter voter = listVoter.get(position);

        holder.tvName.setText("Nama : "+voter.getNama());
        holder.tvItemNik.setText("NIK : "+voter.getNik());
        holder.tvItemTanggalInput.setText(voter.getTanggalInput());
        holder.tvItemAlamat.setText("Alamat : "+voter.getAlamat());
        holder.tvItemNoHp.setText("No Hp : "+voter.getNoHp());
        holder.tvItemJenisKelamin.setText("Jenis Kelamin : "+voter.getJenisKelamin());
        // Load the image file from the stored URI if available
        if (voter.getGambar() != null) {
            Uri imageUri = Uri.parse(voter.getGambar());  // Parse the saved URI
            Log.d("Image URI", "Loading image from URI: " + imageUri.toString());

            // Use Glide to load the image
            Glide.with(holder.itemView.getContext())
                    .load(imageUri)
                    .into(holder.imgPhoto);
        } else {
            holder.imgPhoto.setImageResource(R.drawable.ic_place_holder);  // Placeholder image
            Log.d("Image Missing", "No image found for this voter.");
        }
    }


    @Override
    public int getItemCount() {
        return listVoter.size();

    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {

        ImageView imgPhoto;
        TextView tvName, tvItemNik, tvItemTanggalInput,tvItemAlamat,tvItemNoHp,tvItemJenisKelamin;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvItemNik = itemView.findViewById(R.id.tv_item_nik);
            tvItemTanggalInput = itemView.findViewById(R.id.tv_item_tanggal_input);
            tvItemJenisKelamin =  itemView.findViewById(R.id.tv_item_jenis_kelamin);
            tvItemAlamat = itemView.findViewById(R.id.tv_item_alamat);
            tvItemNoHp = itemView.findViewById(R.id.tv_item_no_hp);

        }
    }
}
