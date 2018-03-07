package com.example.xingxiaogang.testfingerprint;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.accessibility.AccessibilityClient;
import com.example.accessibility.Statics;
import com.example.xingxiaogang.testfingerprint.utils.DropzoneHelper;

public class MainActivity extends Activity implements View.OnClickListener, AccessibilityClient.AccessibilityTaskHandlerCallBack {

    private static final String DIALOG_FRAGMENT_TAG = "myFragment";
    private Button mFingerPrintButton;
    private Toast mProgressToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFingerPrintButton = (Button) findViewById(R.id.open_dialog);
        mFingerPrintButton.setOnClickListener(this);
        findViewById(R.id.start_accessibility).setOnClickListener(this);
        findViewById(R.id.open_float_permission).setOnClickListener(this);
        findViewById(R.id.open_protect_permission).setOnClickListener(this);
        findViewById(R.id.open_autostart_permission).setOnClickListener(this);
        findViewById(R.id.open_accessibility_permission).setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.open_accessibility_permission: {
                Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivity(intent);
                break;
            }
            case R.id.start_accessibility: {
                AccessibilityClient client = AccessibilityClient.getInstance(getApplication());
                if (client.isSupportAccessibility()) {
                    client.setCallBack(this);
                    client.startSettingAccessibility();
                } else {
                    Toast.makeText(getApplicationContext(), "暂不支持本机型!", Toast.LENGTH_LONG).show();
                }
                break;
            }
            case R.id.open_float_permission: {
                DropzoneHelper.launchSystemDropzoneManager(this);
                break;
            }
            case R.id.open_autostart_permission: {
                DropzoneHelper.launchAutoStartManager(this);
                break;
            }
            case R.id.open_protect_permission: {
                DropzoneHelper.launchProtectionManager(this);
                break;
            }
        }
    }

    @Override
    public void onError(int code, String msg) {
        Log.e("test_access", "MainActivity.onError  {code:" + code + ",msg:" + msg + "}");
        switch (code) {
            case Statics.Code.ERROR_CODE_NO_PERMISSION: {
                Toast.makeText(getApplication(), "请先开启辅助权限", Toast.LENGTH_SHORT).show();
                break;
            }
            case Statics.Code.ERROR_CODE_INTERRUPT: {
                Toast.makeText(getApplication(), "任务已中断", Toast.LENGTH_SHORT).show();
                break;
            }
            case Statics.Code.ERROR_CODE_JSON_PREPARE_FAILED: {
                Toast.makeText(getApplication(), "json信息关联不完整", Toast.LENGTH_SHORT).show();
                break;
            }
            case Statics.Code.ERROR_CODE_ROOT_NODE_NULL:
            case Statics.Code.ERROR_CODE_NO_NODE: {
                Toast.makeText(getApplication(), "未找到Node", Toast.LENGTH_SHORT).show();
                break;
            }
            case Statics.Code.ERROR_CODE_INTENT_OPEN_FAILED: {
                Toast.makeText(getApplication(), "包名不存在", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

    @Override
    public void onProgressUpdate(int all, int progress, String description) {
        Log.e("test_access", "MainActivity.onProgressUpdate {all:" + all + ",current:" + progress + ",:description:" + description + "}");
        if (mProgressToast == null) {
            mProgressToast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);
        }
        mProgressToast.setText("当前任务:" + progress + "/" + all + " (" + description + ")");
        mProgressToast.show();
    }

    @Override
    public void onFinish(boolean success) {
        Log.e("test_access", "MainActivity.onFinis：{" + success + "}");
        if (mProgressToast == null) {
            mProgressToast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG);
        }
        mProgressToast.setText("辅助任务执行完成：" + success);
        mProgressToast.show();
    }
}
