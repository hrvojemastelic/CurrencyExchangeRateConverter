package com.example.uhpdigiital;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {



    Spinner buyingSpinner,sellingSpinner;
    List<CurrencyModel>currencyModel;
    Double buy ;
    Double sell;
    Double buyinputnumber=null;
    TextView result;
    Button calculate;
    EditText buyinginput ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        buyinginput=(EditText)findViewById(R.id.buying_edite_text);
        buyingSpinner=(Spinner)findViewById(R.id.buying_spinner);
        sellingSpinner=(Spinner)findViewById(R.id.selling_spinner);
        result=(TextView)findViewById(R.id.result);
        calculate=(Button)findViewById(R.id.calculate);




        // request
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://hnbex.eu/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface=retrofit.create(ApiInterface.class);

        Call<List<CurrencyModel>> call=apiInterface.getDailyCurrencyValues();
        call.enqueue(new Callback<List<CurrencyModel>>() {
            @Override
            public void onResponse(Call<List<CurrencyModel>> call, Response<List<CurrencyModel>> response) {

                List<CurrencyModel> currencyModels = response.body();
                currencyModel=new ArrayList<>(currencyModels);


                //Spinner Adapter
                ArrayAdapter<CurrencyModel>arrayAdapter=new ArrayAdapter<CurrencyModel>(MainActivity.this,android.R.layout.simple_spinner_item,currencyModel);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                buyingSpinner.setAdapter(arrayAdapter);
                sellingSpinner.setAdapter(arrayAdapter);



                //get value  from selected item
                buyingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        CurrencyModel currencyModelb = (CurrencyModel) parent.getSelectedItem();
                        getCurrencyBuyingValue(currencyModelb);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });



                //get value  from selected item
                sellingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        CurrencyModel currencyModels = (CurrencyModel) parent.getSelectedItem();
                        getCurrencyBuyingValue(currencyModels);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }
            @Override
            public void onFailure(Call<List<CurrencyModel>> call, Throwable t) {

            }
        });


        //On click result
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {






                CurrencyModel currencybuyindobject= (CurrencyModel) buyingSpinner.getSelectedItem();
                getCurrencyBuyingValue(currencybuyindobject);

                CurrencyModel currencysellingobject= (CurrencyModel) sellingSpinner.getSelectedItem();
                getCurrencySellingValue(currencysellingobject);


                buyinputnumber = parseDoubleBuy(String.valueOf(buyinginput.getText())) ;

                result.setText(String.valueOf(String.format("%.4f", buyinputnumber / sell)));


            }

            //check if editetext is empty buying value
            private Double parseDoubleBuy(String s) {

                if(s == null || s.isEmpty())
                    return buy;
                else
                    return Double.parseDouble(s) * buy;
            }
        });




    }



    //get buying value

    public void getCurrencyBuyingValue(CurrencyModel currencyModel){
         buy= Double.parseDouble(currencyModel.getBuyingRate());

    }


    //get selling value
    public void getCurrencySellingValue(CurrencyModel currencyModel){
        sell = Double.parseDouble(currencyModel.getSellingRate());

    }





}
