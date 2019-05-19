package com.example.toastinterceptor;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toastinterceptor.db.ToastEntity;
import com.example.toastinterceptor.db.ToastRepository;

import java.text.DateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ToastListAdapter extends RecyclerView.Adapter<ToastListAdapter.ToastViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<ToastEntity> toasts = Collections.emptyList();
    private ToastRepository toastRepository;

    ToastListAdapter(Application application) {
        this.context = application.getApplicationContext();
        inflater = LayoutInflater.from(context);
        toastRepository = new ToastRepository(application);
    }

    void setToasts(List<ToastEntity> toasts) {
        this.toasts = toasts;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ToastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rv_list, parent, false);
        return new ToastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ToastViewHolder holder, final int position) {
        if (toasts != null) {
            final ToastEntity toastEntity = toasts.get(position);
            ApplicationInfo applicationInfo = null;
            try {
                applicationInfo = context.getPackageManager().getApplicationInfo(toastEntity.getPackageName(), PackageManager.GET_META_DATA);
                CharSequence applicationLabel = context.getPackageManager().getApplicationLabel(applicationInfo);
                holder.tv_pkgName.setText(applicationLabel);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                holder.tv_pkgName.setText(toastEntity.getPackageName());
            }
            holder.tv_msg.setText(toastEntity.getContent());
            Date date = toastEntity.getDate();
            DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
            holder.tv_date.setText(df.format(date));
            try {
                holder.iv_icon.setImageDrawable(context.getPackageManager().getApplicationIcon(toastEntity.getPackageName()));
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            holder.iv_options.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (toastRepository != null) {
                        toastRepository.delete(toastEntity);
                    }
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        if (toasts != null)
            return toasts.size();
        else return 0;
    }

    class ToastViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_date, tv_pkgName, tv_msg;
        private ImageView iv_icon, iv_options;

        ToastViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_pkgName = itemView.findViewById(R.id.tv_pkgName);
            tv_msg = itemView.findViewById(R.id.tv_msg);
            iv_options = itemView.findViewById(R.id.iv_options);
            iv_icon = itemView.findViewById(R.id.iv_icon);

        }
    }
}
