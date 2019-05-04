package com.ubelemir.homework2;



import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link Fragment} subclass.
 */
public class CENGFragment extends Fragment {
    private TextView textView;
    private String contentURL = "https://aybu.edu.tr/muhendislik/bilgisayar/";
    private String contentType;
    private String contentSelector = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_ceng, container, false);
        contentType = getArguments().getString("ContentType");
        if(contentType.equals("news")){
            contentSelector = ".cnContent";
        } else {
            contentSelector = ".caContent";
        }
        return fragmentView;
    }

    public AsyncHttpResponseHandler loadHTMLContent(){
        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("Yükleniyor...");
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
        return new AsyncHttpResponseHandler() {
            @Override
            public void onStart(){
                dialog.show();
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Document doc = Jsoup.parse(new String(responseBody));
                Elements textList = doc.select(contentSelector+" > .cncItem");
                Elements linkList = doc.select(contentSelector+" a");
                ContentItem[] cncItems = new ContentItem[textList.size()];
                for(int i = 0; i < cncItems.length; i++){
                    cncItems[i] = new ContentItem(textList.get(i).text(), linkList.get(i).attr("href"),contentType);
                }
                ListView konuListesi = (ListView) getView().findViewById(R.id.contentList);
                konuListesi.setAdapter(new ContentListAdapter(getContext(), cncItems));
                konuListesi.setOnItemClickListener(contentItemClick);
                dialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getActivity().getApplicationContext(),"Error while loading content",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        };
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            AsyncHttpClient httpClient = new AsyncHttpClient();
            httpClient.get(contentURL, loadHTMLContent());
        }
    }

    public AdapterView.OnItemClickListener contentItemClick = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ContentItem ci = (ContentItem) parent.getItemAtPosition(position);
            Intent tempIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.aybu.edu.tr/muhendislik/bilgisayar/"+ci.link));
            startActivity(tempIntent);
        }
    };
}
