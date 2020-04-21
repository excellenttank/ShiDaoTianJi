package com.yuce.shidaotianji;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.yuce.application.YcApplication;
import com.yuce.dbutil.SQLCopy;

public class MainActivity extends Activity {

    private YcApplication ycApp;

    private EditText lingDongShu;

    private TextView tianPan_result;
    private TextView diPan_result;

    private ImageButton tianPanChoose;
    private ImageButton diPanChoose;

    private Button yuCeResult;

    private PopupWindow tianPanPopupWindow;
    private PopupWindow diPanPopupWindow;

    private LayoutInflater tianPanInflater;
    private LayoutInflater diPanInflater;

    private View tianPanLayout;
    private View diPanLayout;

    private TableLayout tianPanTableLayout;
    private TableLayout diPanTableLayout;

    private TableRow tianPanTableRow;
    private TableRow diPanTableRow;

    private TextView tianPanTv;
    private TextView diPanTv;

    private CharSequence cs = " 天地";

    private String xuanMapStr = "82167453";

    private String splitStr[];

    private long mExitTime = 0;

    private String tianPanString = new String("天一,学业 1 一,考试 1 一, ,天五,生意 5 五,销货 5 五," + " ,运势 1 一,胜负 1 一, , ,建造 5 五,离婚 5 五," + " ,气象 1 一,行业 1 一, , ,官司 5 五,文书 5 五," + " ,债务 1 一,疾病 1 一, , ,决策 5 五,求职 5 五," + "天二,吉凶 2 二,情变 2 二, ,天六,孕产 6 六,祖业 6 六," + " ,计划 2 二,迁动 2 二, , ,交易 6 六,调动 6 六," + " ,求医 2 二,灾厄 2 二, , ,姻缘 6 六,商谈 6 六," + " ,办事 2 二,房产 2 二, , ,晋升 6 六,命关 6 六," + "天三,投资 3 三,赌运 3 三, ,天七,求官 7 七,玄机 7 七," + " ,开办 3 三,彼此 3 三, , ,购买 7 七,会见 7 七," + " ,求財 3 三,升学 3 三, , ,造化 7 七,雇请 7 七," + " ,幸运 3 三,借贷 3 三, , ,求师 7 七,时机 7 七," + "天四,交友 4 四,出国 4 四, ,天八,失物 8 八,转职 8 八," + " ,胎育 4 四,是非 4 四, , ,家庭 8 八,音讯 8 八," + " ,出行 4 四,竞选 4 四, , ,方所 8 八,走失 8 八," + " ,收益 4 四,签约 4 四, , ,合伙 8 八,行情 8 八,");

    private String diPanString = new String("地一,气象 1 一,收益 1 一, ,地五,房产 5 五,姻缘 5 五," + " ,求财 1 一,迁动 1 一, , ,购买 5 五,幸运 5 五," + " ,销货 1 一,造化 1 一, , ,交友 5 五,胜负 5 五," + " ,晋升 1 一,行情 1 一, , ,走失 5 五,建造 5 五," + "地二,办事 2 二,借貸 2 二, ,地六,签约 6 六,孕产 6 六," + " ,竞选 2 二,行业 2 二, , ,运势 6 六,吉凶 6 六," + " ,会见 2 二,祖业 2 二, , ,音讯 6 六,彼此 6 六," + " ,文书 2 二,家庭 2 二, , ,生意 6 六,求官 6 六," + "地三,胎育 3 三,愿望 3 三, ,地七,升学 7 七,求师 7 七," + " ,调动 3 三,求职 3 三, , ,出国 7 七,决策 7 七," + " ,学业 3 三,投资 3 三, , ,方所 7 七,交易 7 七," + " ,玄机 3 三,合伙 3 三, , ,求医 7 七,疾病 7 七," + "地四,开办 4 四,出行 4 四, ,地八,考试 8 八,命关 8 八," + " ,债务 4 四,商谈 4 四, , ,赌运 8 八,官司 8 八," + " ,离婚 4 四,情变 4 四, , ,灾厄 8 八,是非 8 八," + " ,时机 4 四,失物 4 四, , ,雇请 8 八,转职 8 八");

    private String numberString;

    private String lingDongNumber;

    private int tianStrNumber;

    private int diStrNumber;

    private String tianThing;

    private String diThing;

    private SQLCopy sqlCopy;

    private SQLiteDatabase db;

    private Cursor data_cursor;

    private Dialog resultDialog;

    private TextView data_title;

    private TextView data_content;
    private InputMethodManager imm;
    private WindowManager windowManager;
    private Display display;
    private WindowManager.LayoutParams lp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        ycApp = YcApplication.getInstance();
        ycApp.addActivity(this);
        imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        windowManager = this.getWindowManager();
        display = windowManager.getDefaultDisplay();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        sqlCopy = new SQLCopy();

        db = sqlCopy.openDatabase(getApplicationContext());

        initView();
    }

    public void initView() {

        tianPanInflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);

        diPanInflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);

        lingDongShu = (EditText) findViewById(R.id.lingDongShu);

        lingDongShu.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

                lingDongShu.setGravity(Gravity.CENTER);

            }
        });

        tianPan_result = (TextView) findViewById(R.id.tianPan_result);

        diPan_result = (TextView) findViewById(R.id.diPan_result);

        tianPanChoose = (ImageButton) findViewById(R.id.tianPanChoose);
        tianPanChoose.setOnClickListener(new OnClick());

        diPanChoose = (ImageButton) findViewById(R.id.diPanChoose);
        diPanChoose.setOnClickListener(new OnClick());

        yuCeResult = (Button) findViewById(R.id.yuCeResult);
        yuCeResult.setOnClickListener(new OnClick());

    }

    private class OnClick implements OnClickListener {

        public void onClick(View v) {
            // TODO Auto-generated method stub
            switch (v.getId()) {

                case R.id.tianPanChoose:
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    int i = 0;
                    tianPanLayout = tianPanInflater.inflate(R.layout.layout_tianpan, null);
                    tianPanLayout.setBackgroundResource(R.drawable.circle_corner);
                    tianPanTableLayout = (TableLayout) tianPanLayout.findViewById(R.id.tianPanTable);
                    String tianPanSpli[] = tianPanString.split(",");
                    tianPanTableLayout.setStretchAllColumns(true);
                    // 生成10行，8列的表格
                    for (int row = 0; row < 16; row++) {
                        tianPanTableRow = new TableRow(getApplicationContext());
                        for (int col = 0; col < 7; col++) {
                            // tv用于显示
                            tianPanTv = new TextView(getApplicationContext());
                            tianPanTv.setTextColor(Color.BLACK);
                            if (!" ".equals(tianPanSpli[i])) {
                                splitStr = null;
                                splitStr = tianPanSpli[i].split(" ");
                                tianPanTv.setText(splitStr[0] == "" ? " " : splitStr[0]);
                                tianPanTv.setId(i);
                                if (!splitStr[0].contains(" ") && !splitStr[0].contains("天") && !splitStr[0].contains("地")) {
                                    tianPanTv.setOnClickListener(new OnClickListener() {

                                        @Override
                                        public void onClick(View v) {
                                            // TODO Auto-generated method stub
                                            int id = v.getId();
                                            v.setBackgroundColor(Color.parseColor("#64492b"));
                                            String tianPanSpli[] = tianPanString.split(",");
                                            splitStr = tianPanSpli[id].split(" ");
                                            tianPan_result.setText("天" + splitStr[2] + " " + splitStr[0]);
                                            tianThing = splitStr[0];
                                            tianStrNumber = Integer.parseInt(splitStr[1]);
                                            tianPanPopupWindow.dismiss();
                                        }
                                    });
                                }
                            } else {

                                tianPanTv.setText(tianPanSpli[i]);

                            }
                            tianPanTv.setBackgroundResource(R.drawable.table_frame_gray);
                            tianPanTv.setPadding(1, 2, 1, 2);
                            tianPanTv.setGravity(Gravity.CENTER);

                            tianPanTableRow.addView(tianPanTv, new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
                            i++;
                        }
                        tianPanTableLayout.addView(tianPanTableRow);
                    }

                    tianPanPopupWindow = new PopupWindow(tianPanLayout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                    tianPanPopupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

                    break;
                case R.id.diPanChoose:
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                    }
                    int j = 0;
                    diPanLayout = diPanInflater.inflate(R.layout.layout_tianpan, null);
                    diPanLayout.setBackgroundResource(R.drawable.circle_corner);
                    diPanTableLayout = (TableLayout) diPanLayout.findViewById(R.id.tianPanTable);
                    String diPanSpli[] = diPanString.split(",");
                    diPanTableLayout.setStretchAllColumns(true);
                    // 生成10行，8列的表格
                    for (int row = 0; row < 16; row++) {
                        diPanTableRow = new TableRow(getApplicationContext());
                        // tableRow.setBackgroundColor(Color.parseColor("#f26522"));
                        for (int col = 0; col < 7; col++) {
                            // tv用于显示
                            diPanTv = new TextView(getApplicationContext());
                            diPanTv.setTextColor(Color.BLACK);
                            if (!" ".equals(diPanSpli[j])) {
                                splitStr = null;
                                splitStr = diPanSpli[j].split(" ");
                                diPanTv.setText(splitStr[0] == "" ? " " : splitStr[0]);
                                diPanTv.setId(j);
                                if (!splitStr[0].contains(" ") && !splitStr[0].contains("天") && !splitStr[0].contains("地")) {
                                    diPanTv.setOnClickListener(new OnClickListener() {

                                        @Override
                                        public void onClick(View v) {
                                            // TODO Auto-generated method stub
                                            int id = v.getId();
                                            String diPanSpli[] = diPanString.split(",");
                                            splitStr = diPanSpli[id].split(" ");
                                            v.setBackgroundColor(Color.parseColor("#64492b"));
                                            diPan_result.setText("地" + splitStr[2] + " " + splitStr[0]);
                                            diThing = splitStr[0];
                                            diStrNumber = Integer.parseInt(splitStr[1]);
                                            diPanPopupWindow.dismiss();
                                        }
                                    });

                                } else {
                                    diPanTv.setText(diPanSpli[j]);
                                }
                                diPanTv.setBackgroundResource(R.drawable.table_frame_gray);
                                diPanTv.setPadding(1, 2, 1, 2);
                                diPanTv.setGravity(Gravity.CENTER);

                            }

                            diPanTableRow.addView(diPanTv, new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
                            j++;
                        }
                        diPanTableLayout.addView(diPanTableRow);
                    }

                    diPanPopupWindow = new PopupWindow(diPanLayout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                    diPanPopupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

                    break;
                case R.id.yuCeResult:

                    lingDongNumber = lingDongShu.getText().toString();

                    if ("".equals(lingDongNumber)) {
                        Toast.makeText(MainActivity.this, "请输入灵动数", Toast.LENGTH_LONG).show();
                        return;
                    }

                    if (tianStrNumber == 0) {
                        Toast.makeText(MainActivity.this, "请选择天盘中要预测的事", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (diStrNumber == 0) {
                        Toast.makeText(MainActivity.this, "请选择地盘中要预测的事", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (!tianThing.equals(diThing)) {
                        Toast.makeText(MainActivity.this, "天地盘中需选择相同的事项", Toast.LENGTH_LONG).show();
                        return;
                    }

                    String resultId = GetResultId(Integer.parseInt(lingDongNumber), tianStrNumber, diStrNumber);

                    resultDialog = new Dialog(MainActivity.this, R.style.Dialog);
                    resultDialog.setCanceledOnTouchOutside(true);
                    resultDialog.setContentView(R.layout.layout_dialog);

                    data_cursor = db.rawQuery("select data_title,data_content from result_data where data_num =?", new String[]{resultId});

                    data_title = (TextView) resultDialog.findViewById(R.id.data_title);
                    data_content = (TextView) resultDialog.findViewById(R.id.data_content);

                    data_cursor.moveToNext();

                    data_title.setText(data_cursor.getString(data_cursor.getColumnIndex("data_title")));
                    data_content.setText(data_cursor.getString(data_cursor.getColumnIndex("data_content")).replace("\\n", "\n"));

                    lp = resultDialog.getWindow().getAttributes();
                    lp.width = (int) (display.getWidth());
                    resultDialog.getWindow().setAttributes(lp);
                    resultDialog.show();

                    data_cursor.close();

                    break;

            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                if (null != db && db.isOpen()) {
                    db.close();
                }
                MobclickAgent.onKillProcess(MainActivity.this);
                ycApp.exit();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private String GetResultId(int lingDongShu, int tianStrNumber, int diStrNumber) {
        String resultId = "";

        int tianNum;
        int diNum;

        // xuanMapStr = "82167453";

        tianNum = xuanMapStr.indexOf(String.valueOf(tianStrNumber)) + (lingDongShu - 1);
        diNum = xuanMapStr.indexOf(String.valueOf(diStrNumber)) + (lingDongShu - 1);

        if (tianNum >= 8) {
            tianNum = (tianNum - 8);
        }

        if (diNum >= 8) {
            diNum = (diNum - 8);
        }

        resultId = String.valueOf(lingDongShu) + xuanMapStr.charAt(tianNum) + xuanMapStr.charAt(diNum);

        return resultId;

    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
