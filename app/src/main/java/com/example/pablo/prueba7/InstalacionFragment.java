package com.example.pablo.prueba7;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.pablo.prueba7.Listas.Array;
import com.example.pablo.prueba7.Modelos.GetMuestraRelOrdenesTecnicosListResult;
import com.example.pablo.prueba7.Request.Request;


import java.util.Calendar;
import java.util.Iterator;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class InstalacionFragment extends Fragment implements View.OnClickListener {

    public static EditText selectDate, selectTime, selectDate1, selectDate2, selectTime2;
   // public static String clv_TecSec_seleccion="-1"
    public static int ejecutada=1, visita=0, visita1=0, TecSecSelecc=-1;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private View contenedorParticular;
    private View contenedorCorporativo;
    TextView coordenadas, direccion, coordenadas1,coordenadas2;
    public static TextView Obs;
    public static Spinner TecSec;
Request request = new Request();
    RadioButton btn1, bt2;



    public InstalacionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle onSaveInstanceState) {
        // Inflate the layout for this fragment
        super.onActivityCreated(onSaveInstanceState);
        View view = inflater.inflate(R.layout.activity_hora, container, false);
        Obs = view.findViewById(R.id.obs);

            request.getTecSec(getContext());


        //////////// acciones de botones de hora y fecha//////
        selectDate = view.findViewById(R.id.ejecureal);
        selectDate1 = view.findViewById(R.id.visita1);
        selectDate2 = view.findViewById(R.id.visita2);


        selectTime = view.findViewById(R.id.horai);
        selectTime2 = view.findViewById(R.id.horaf);
        ///////////////////////////////////////////////////////

        ///////////contenedores y acciones de radiobuttons////
        contenedorParticular = view.findViewById(R.id.RE);
        contenedorCorporativo = view.findViewById(R.id.RV);

        btn1 = view.findViewById(R.id.ejutada);
        bt2 = view.findViewById(R.id.visitada);
        /////////////////////////////////////////////////////
        TecSec = view.findViewById(R.id.tecnicosec);
        TecSec.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                TecSecSelecc = Array.clv_tecnicoSecundario.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ///////////////////GPS//////////////////////////////
        coordenadas = view.findViewById(R.id.txtCoordenadas);
        coordenadas1 =view.findViewById(R.id.txtCoordenadas1);
        coordenadas2 = view.findViewById(R.id.txtCoordenadas2);


        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {
            locationStart();
        }

        //////////////////////////////////////////////////


        ////////// fecaha, hora y radio buttons/////////
        selectDate.setOnClickListener(this);
        selectDate1.setOnClickListener(this);
        selectDate2.setOnClickListener(this);
        selectTime.setOnClickListener(this);
        selectTime2.setOnClickListener(this);
        bt2.setOnClickListener(this);
        btn1.setOnClickListener(this);

        return view;
    }


    public void onClick(View view) {
        btn1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(btn1.isChecked()==true){
                    ejecutada=0;
                    visita=1;
                }

            }
        });
        bt2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(bt2.isChecked()==true){
                    ejecutada=1;
                    visita=0;
                }

            }
        });
        switch (view.getId()) {
            case R.id.ejutada:
                {
                    mostrarParticular(false);
                    selectDate.setText("");
                    selectTime.setText("");
                    selectTime.setEnabled(false);
                    selectDate1.setText("");
                    selectDate2.setText("");
                    selectDate2.setEnabled(false);
                    selectTime2.setText("");
                    selectTime2.setEnabled(false);
                    ejecutada=0;
                    visita=1;
                }
                break;
            case R.id.visitada:
                 {
                    mostrarParticular(true);
                     selectDate.setText("");
                     selectTime.setText("");
                     selectTime.setEnabled(true);
                     selectDate1.setText("");
                     selectDate2.setText("");
                     selectTime2.setText("");
                     selectTime2.setEnabled(true);
                     ejecutada=1;
                     visita=0;

                }
                break;
        }


        if (view == selectDate) {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    if(monthOfYear<10){
                        if(dayOfMonth<10){
                            selectDate.setText("0"+dayOfMonth + "/0" + (monthOfYear + 1) + "/" + year);
                        }else {
                            selectDate.setText(dayOfMonth + "/0" + (monthOfYear + 1) + "/" + year);
                        }
                    }else {
                        selectDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }


                }
            }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (view == selectTime) {

// Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {

                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
                if(minutes<10){
                    selectTime.setText(hourOfDay + ":0" + minutes);
                }else{
                    selectTime.setText(hourOfDay + ":" + minutes);
                }

                }
            }, mHour, mMinute, false);
            timePickerDialog.show();
        }

        if (view == selectDate1) {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog1 = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                    if(monthOfYear<10){
                        if(dayOfMonth<10){
                            selectDate1.setText("0"+dayOfMonth + "/0" + (monthOfYear + 1) + "/" + year);
                        }else{
                            selectDate1.setText(dayOfMonth + "/0" + (monthOfYear + 1) + "/" + year);
                        }
                    }else {
                        selectDate1.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }

                }
            }, mYear, mMonth, mDay);
            datePickerDialog1.show();
        }


        if (view == selectDate2) {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                    if(monthOfYear<10){
                        if(dayOfMonth<10){
                            selectDate2.setText("0"+dayOfMonth + "/0" + (monthOfYear + 1) + "/" + year);
                        }else{
                            selectDate2.setText(dayOfMonth + "/0" + (monthOfYear + 1) + "/" + year);
                        }
                    }else {
                        selectDate2.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }
            }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (view == selectTime2) {

// Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {

                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    if(minute<10){
                        selectTime2.setText(hourOfDay + ":0" + minute);
                    }else{
                        selectTime2.setText(hourOfDay + ":" + minute);
                    }
                }
            }, mHour, mMinute, false);
            timePickerDialog.show();
        }


    }


    private void mostrarParticular(boolean b) {
        contenedorParticular.setVisibility(b ? View.VISIBLE: View.GONE);
        contenedorCorporativo.setVisibility(b ? View.GONE: View.VISIBLE);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////Aqui empieza el GPS///////////////////////////

    private void locationStart() {
        LocationManager mlocManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setMainActivity(this);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, Local);

        coordenadas.setText("Localización agregada");
//        direccion.setText("");
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationStart();
                return;
            }
        }
    }

    public void setLocation(Location loc) {

    }

    /* Aqui empieza la Clase Localizacion */
    public class Localizacion implements LocationListener {
        InstalacionFragment mainActivity;

        public InstalacionFragment getMainActivity() {
            return mainActivity;
        }

        public void setMainActivity(InstalacionFragment mainActivity) {
            this.mainActivity = mainActivity;
        }

        @Override
        public void onLocationChanged(Location loc) {
            // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
            // debido a la deteccion de un cambio de ubicacion

            loc.getLatitude();
            loc.getLongitude();

            String Text1 = "Coordenadas" ;
            String Text2 =  "\n Lat = " + loc.getLatitude();
            String Text3 = "\n Long = " + loc.getLongitude();
            coordenadas2.setText(Text3);
            coordenadas1.setText(Text2);
            coordenadas.setText(Text1);
            this.mainActivity.setLocation(loc);
        }

        @Override
        public void onProviderDisabled(String provider) {
            /* Este metodo se ejecuta cuando el GPS es desactivado*/
            coordenadas.setText("GPS Desactivado");
        }

        @Override
        public void onProviderEnabled(String provider) {
            /* Este metodo se ejecuta cuando el GPS es activado*/
            coordenadas.setText("GPS Activado");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Log.d("debug", "LocationProvider.AVAILABLE");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                    break;
            }

        }
    }
}