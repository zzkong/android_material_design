package lico.example.activity;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import lico.example.R;
import lico.example.app.BaseActivity;

/**
 * Created by Administrator on 2015/8/25.
 */
public class GsonActivity extends BaseActivity implements View.OnClickListener{


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.json_tandard)
    Button jsonTandard;
    @Bind(R.id.json_no_tandard)
    Button jsonNoTandard;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        jsonTandard.setOnClickListener(this);
        jsonNoTandard.setOnClickListener(this);
    }


    @Override
    protected int getLayoutView() {
        return R.layout.activity_gson;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar(toolbar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.json_tandard:
                parserTandard();
                break;
            case R.id.json_no_tandard:
                parserNoTandard();
                break;
        }
    }


    private void parserTandard(){
        String jsonStr = "{\"name\":\"李晓红\",\"age\":\"18\",\"info\":[{\"className\":\"高三三班\", \"classId\":1,\"classNum\":56},{\"className\":\"高三四班\", \"classId\":2,\"classNum\":50}]}\n";
        Gson gson = new GsonBuilder().serializeNulls().create();
        Person person = gson.fromJson(jsonStr.toString(), Person.class);
        Toast.makeText(this, person.info.get(0).className, Toast.LENGTH_SHORT).show();
    }

    private void parserNoTandard(){
        String jsonStr = "{\"name\":\"李晓红\",\"age\":null,\"info\":null}\n";
        Gson gson = new GsonBuilder().serializeNulls().create();
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            Person person = gson.fromJson(jsonObject.toString(), Person.class);
            if(person.info == null){
                Log.e("", "sssssssssssss"  + person.age);
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    static class Person implements Parcelable {
        public String name;
        public String age;
        public List<ClassInfo> info;


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.name);
            dest.writeString(this.age);
            dest.writeTypedList(info);
        }

        public Person() {
        }

        protected Person(Parcel in) {
            this.name = in.readString();
            this.age = in.readString();
            this.info = in.createTypedArrayList(ClassInfo.CREATOR);
        }

        public static final Creator<Person> CREATOR = new Creator<Person>() {
            public Person createFromParcel(Parcel source) {
                return new Person(source);
            }

            public Person[] newArray(int size) {
                return new Person[size];
            }
        };
    }

    static class ClassInfo implements Parcelable {
        public String className;
        public int classId;
        public int classNum;


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.className);
            dest.writeInt(this.classId);
            dest.writeInt(this.classNum);
        }

        public ClassInfo() {
        }

        protected ClassInfo(Parcel in) {
            this.className = in.readString();
            this.classId = in.readInt();
            this.classNum = in.readInt();
        }

        public static final Creator<ClassInfo> CREATOR = new Creator<ClassInfo>() {
            public ClassInfo createFromParcel(Parcel source) {
                return new ClassInfo(source);
            }

            public ClassInfo[] newArray(int size) {
                return new ClassInfo[size];
            }
        };
    }
}
