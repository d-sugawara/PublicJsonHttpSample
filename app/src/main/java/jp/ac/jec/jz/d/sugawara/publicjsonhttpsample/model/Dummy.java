package jp.ac.jec.jz.d.sugawara.publicjsonhttpsample.model;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public final class Dummy {
    /// constant ///
    private static final String TAG = "DEBUG";

    /// variable ///
    private int id;
    private String name;
    private List<String> subjects;

    /// accessor ///
    public int getId() { return id; }
    public void setId(int value) { id = value; }

    public String getName() { return name; }
    public void setName(String value) { name = value; }

    public List<String> getSubjects() { return subjects; }

    /// constructor ///
    public Dummy() {
        id = -1;
        name = "";
        subjects = new ArrayList<>();
    }

    /// method ///
    public String toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("id", getId());
            object.put("name", getName());
            object.put("subjects", new JSONArray(getSubjects()));
        } catch (JSONException e) {
            Log.d(TAG, "toJson: " + e.getMessage());
        }
        return object.toString();
    }

    /**
     * JSON文字列をDummyオブジェクトに変換する
     * @param jsonString 変換する文字列
     * @return Dummyオブジェクト
     */
    public static Dummy parseJson(String jsonString) throws JSONException{
        try {
            JSONObject json = new JSONObject(jsonString);

            Dummy dummy = new Dummy();
            dummy.setId(json.getInt("id"));
            dummy.setName(json.getString("name"));
            JSONArray subjects = json.getJSONArray("subjects");

            ArrayList<String> temp;
            // JSONArray -> ArrayList変換（GSON利用）
            temp = new Gson().fromJson(subjects.toString(), new TypeToken<List<String>>(){}.getType());
//            // JSONArray -> ArrayList変換（ライブラリ未使用）
//            temp = new ArrayList<String>();
//            for (int i = 0; i < subjects.length(); i++) {
//                temp.add(subjects.getString(i));
//            }
            dummy.getSubjects().addAll(temp);
            return dummy;

        } catch (JSONException e) {
            throw e;
        }
    }
}
