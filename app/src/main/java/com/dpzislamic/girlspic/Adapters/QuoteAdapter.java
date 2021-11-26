package com.dpzislamic.girlspic.Adapters;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dpzislamic.girlspic.Model.QuoteModel;
import com.dpzislamic.girlspic.R;
import com.dpzislamic.girlspic.SinglequoteActivity;
import com.skydoves.elasticviews.ElasticImageView;

import java.util.List;
import java.util.Random;

public class QuoteAdapter extends RecyclerView.Adapter<QuoteAdapter.Viewholder> {
    List<QuoteModel> quoteModelList;
    private final static int FADE_DURATION = 1000; //FADE_DURATION in milliseconds
    private int lastPosition = -1;

    public QuoteAdapter(List<QuoteModel> quoteModelList) {
        this.quoteModelList = quoteModelList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_quote_item,parent,false);
        return new Viewholder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final Viewholder holder, final int position) {
        String text = quoteModelList.get(position).getQuote();

        holder.textView.setText(Html.fromHtml(text).toString().replaceAll("\r\n","").trim());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SinglequoteActivity.class);
                intent.putExtra("quote",holder.textView.getText().toString());
                intent.putExtra("position",holder.getAdapterPosition());
                view.getContext().startActivity(intent);
            }
        });

        holder.copy_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String body = holder.textView.getText().toString();
                ClipboardManager clipboard = (ClipboardManager)v.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(null,body);
                if (clipboard == null) return;
                clipboard.setPrimaryClip(clip);
                Toast.makeText(v.getContext(), "Copied", Toast.LENGTH_SHORT).show();

            }
        });

        holder.all_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String body = holder.textView.getText().toString();
                String sub = "Download Status App";
                myIntent.putExtra(Intent.EXTRA_SUBJECT,sub);
                myIntent.putExtra(Intent.EXTRA_TEXT,body+"\n\n"+"Download Now: "+ holder.itemView.getContext().getString(R.string.play_store_link)+holder.itemView.getContext().getPackageName().toString());
                view.getContext().startActivity(Intent.createChooser(myIntent, "Share Using"));
            }
        });

        holder.insta_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setPackage("com.instagram.android");
                shareIntent.setType("text/plain");

                String contentShare = holder.textView.getText().toString();
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, contentShare +"\n\n"+"Download Now: "+
                        holder.itemView.getContext().getString(R.string.play_store_link)+
                        holder.itemView.getContext().getPackageName().toString() );                view.getContext().startActivity(Intent.createChooser(shareIntent, "Share via"));

                try {
                    view.getContext().startActivity(shareIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(view.getContext(), "Instagram have not been installed", Toast.LENGTH_SHORT).show();
                    //ToastHelper.MakeShortText("Whatsapp have not been installed.");
                }
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
        });

        holder.wh_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setPackage("com.whatsapp");
                shareIntent.setType("text/plain");

                String contentShare = holder.textView.getText().toString();
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, contentShare +"\n\n"+"Download Now: "+
                        holder.itemView.getContext().getString(R.string.play_store_link)+
                        holder.itemView.getContext().getPackageName().toString() );
                view.getContext().startActivity(Intent.createChooser(shareIntent, "Share via"));

                try {
                    view.getContext().startActivity(shareIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(view.getContext(), "Whatsapp have not been installed", Toast.LENGTH_SHORT).show();
                    //ToastHelper.MakeShortText("Whatsapp have not been installed.");
                }
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
        });

        holder.fb_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setPackage("com.facebook.orca");
                shareIntent.setType("text/plain");

                String contentShare = holder.textView.getText().toString();
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, contentShare +"\n\n"+"Download Now: "+
                        holder.itemView.getContext().getString(R.string.play_store_link)+
                        holder.itemView.getContext().getPackageName().toString() );
                view.getContext().startActivity(Intent.createChooser(shareIntent, "Share via"));

                try {
                    view.getContext().startActivity(shareIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(view.getContext(), "Facebook have not been installed", Toast.LENGTH_SHORT).show();
                    //ToastHelper.MakeShortText("Whatsapp have not been installed.");
                }
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
        });
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }

    private void setScaleAnimation(View view) {
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(new Random().nextInt(501));//to make duration random number between [0,501)
            viewToAnimate.startAnimation(anim);
            lastPosition = position;
        }
    }
    @Override
    public int getItemCount() {
        return quoteModelList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView textView;
        ElasticImageView copy_btn, fb_share, insta_share, wh_share, all_share;
        public Viewholder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.quotetext);
            copy_btn = itemView.findViewById(R.id.copy);
            fb_share = itemView.findViewById(R.id.fb);
            insta_share = itemView.findViewById(R.id.ig);
            wh_share = itemView.findViewById(R.id.wt);
            all_share = itemView.findViewById(R.id.share);

        }
    }
}
