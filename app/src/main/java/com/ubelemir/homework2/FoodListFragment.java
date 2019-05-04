package com.ubelemir.homework2;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import cz.msebera.android.httpclient.Header;

public class FoodListFragment extends Fragment {
    private String contentURL = "https://aybu.edu.tr/sks";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_food_list, container, false);
        AsyncHttpClient httpClient = new AsyncHttpClient();
        httpClient.get(contentURL, loadHTMLContent());
        return fragmentView;
    }
    public AsyncHttpResponseHandler loadHTMLContent(){
        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("Yükleniyor...");
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
        dialog.show();
        return new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Document doc = Jsoup.parse(new String(responseBody));
                Elements dailyList = doc.select("table table tr font");
                int[] textViews = {R.id.foodList_soup,R.id.foodList_primary,R.id.foodList_secondary,R.id.foodList_drink};
                TextView tmpTextView = (TextView) getActivity().findViewById(R.id.foodList_title);
                tmpTextView.setText(dailyList.get(0).text()+" Menu");
                for(int i = 0; i < textViews.length; i++){
                    tmpTextView = (TextView) getActivity().findViewById(textViews[i]);
                    tmpTextView.setText(dailyList.get(i+1).text());
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getActivity().getApplicationContext(),"Error while loading content",Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        };
    }
}


