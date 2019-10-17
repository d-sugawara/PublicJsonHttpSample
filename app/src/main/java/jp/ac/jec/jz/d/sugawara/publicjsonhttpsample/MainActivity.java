package jp.ac.jec.jz.d.sugawara.publicjsonhttpsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;

import jp.ac.jec.jz.d.sugawara.publicjsonhttpsample.model.Dummy;
import jp.ac.jec.jz.d.sugawara.publicjsonhttpsample.model.JsonRequestManager;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    /// constant ///
    private static final String TAG = "DEBUG";

    /// view ///
    Button buttonGet, buttonPost;
    TextView textViewResult;

    /// other variable ///
    JsonRequestManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = JsonRequestManager.getInstance();
        initializeView();
    }

    /**
     * ビューの初期化
     */
    private void initializeView() {
        buttonGet = findViewById(R.id.buttonGet);
        buttonPost = findViewById(R.id.buttonPost);
        textViewResult = findViewById(R.id.textViewResult);

        buttonGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.asyncGetJson(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Log.d(TAG, "onFailure: " + e.getMessage());
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        final String content = response.body().string();
                        final Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                textViewResult.setText(content);
                            }
                        });
                    }
                });
            }
        });
        buttonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dummy dummy = new Dummy();
                dummy.setId(1000);
                dummy.setName("菅原");
                dummy.getSubjects().add("卒業制作");
                dummy.getSubjects().add("Java II");

                manager.asyncPostJson(dummy.toJson(), new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Log.d(TAG, "onFailure: " + e.getMessage());
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        final String content = response.body().string();
                        final Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                textViewResult.setText(content);
                            }
                        });
                    }
                });
            }
        });
    }
}
