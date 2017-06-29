package fpt.doan.tap.hcm.practic;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private DatePicker datePicker;
    private Calendar calendar;
    private Dialog dialog;
    private CheckBox chk1;
    private CheckBox chk2;
    private CheckBox chk3;
    private CheckBox chk4;
    private double[] prices = {200,300,400,500};
    private int year, month, day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        dateView = (TextView) findViewById(R.id.textView3);
        calendar = Calendar.getInstance();
        chk1 = (CheckBox) findViewById(R.id.chk1);
        chk2 = (CheckBox) findViewById(R.id.chk2);
        chk3 = (CheckBox) findViewById(R.id.chk3);
        chk4 = (CheckBox) findViewById(R.id.chk4);
        chk1.setText(chk1.getText()+" "+prices[0]);
        chk2.setText(chk2.getText()+" "+prices[1]);
        chk3.setText(chk3.getText()+" "+prices[2]);
        chk4.setText(chk4.getText()+" "+prices[3]);
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.date_picker_dialog);
        datePicker = (DatePicker) dialog.findViewById(R.id.datePicker);
        final TextView txtDate = (TextView) dialog.findViewById(R.id.txtTime);
        txtDate.setText(calendar.get(Calendar.DAY_OF_MONTH)+"/"+(calendar.get(Calendar.MONTH)+1)+"/"+
                calendar.get(Calendar.YEAR));
        datePicker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) +1,  calendar.get(Calendar.DAY_OF_MONTH) ,
                new DatePicker.OnDateChangedListener()
                {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                    {
                        txtDate.setText(dayOfMonth+"/"+monthOfYear+"/"+year);
                        Log.d("FSA",dayOfMonth+"/"+monthOfYear+"/"+year);
                    }
                }
        );
        Button btnChon = (Button) findViewById(R.id.btnDate);
        btnChon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double price = 0;
                if (chk1.isChecked()) price += prices[0];
                if (chk2.isChecked()) price += prices[1];
                if (chk3.isChecked()) price += prices[2];
                if (chk4.isChecked()) price += prices[3];
                ((TextView)dialog.findViewById(R.id.txtPrice)).setText("Tổng chi phí:" +price);

                Button btnDat = (Button) dialog.findViewById(R.id.btnDat);
                btnDat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        DBUtils db = new DBUtils(MainActivity.this);
                        db = db.open();
                        db.insertDate("Androis User",txtDate.getText().toString());
                        Toast.makeText(MainActivity.this,"đã lưu "+" Andoid User "+" thời gian "+txtDate.getText().toString(),Toast.LENGTH_SHORT).show();
                    }
                });
                Button btnThoat = (Button) dialog.findViewById(R.id.btnThoat);
                btnThoat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

    }
}
