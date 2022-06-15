package com.wheic.arapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    String[] models = {"GOLD TEXT","CAR","MODEL","MONITER","PS4"};
    private ArFragment arCam; //object of ArFragment Class
    private int clickNo = 0; //helps to render the 3d model only once when we tap the screen
    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_expandable_list_item_1,models);
        final ListView listView = (ListView)findViewById(R.id.listview);
        final TextView textView = (TextView)findViewById(R.id.textView);
        listView.setAdapter(adapter);
        arCam = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.arCameraArea);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                String value = models[position];
                Toast.makeText(MainActivity.this, value, Toast.LENGTH_SHORT).show();
                if(value == "GOLD TEXT")
                {
                    clickNo = 0;
                    //ArFragment is linked up with its respective id used in the activity_main.xml
                    arCam.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
                        clickNo++;
                        //the 3d model comes to the scene only when clickNo is one that means once
                        if (clickNo == 1) {
                            Anchor anchor = hitResult.createAnchor();
                            ModelRenderable.builder()
                                    .setSource(MainActivity.this, R.raw.gold_text_stand_2)
                                    .setIsFilamentGltf(true)
                                    .build()
                                    .thenAccept(modelRenderable -> addModel(anchor, modelRenderable))
                                    .exceptionally(throwable -> {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                        builder.setMessage("Somthing is not right" + throwable.getMessage()).show();
                                        return null;
                                    });
                        }
                    });

                }
                else if(value == "CAR")
                {
                    clickNo = 0;
                    arCam.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
                        clickNo++;
                        //the 3d model comes to the scene only when clickNo is one that means once
                        if (clickNo == 1) {
                            Anchor anchor = hitResult.createAnchor();
                            ModelRenderable.builder()
                                    .setSource(MainActivity.this, R.raw.mitsu_lancer_1)
                                    .setIsFilamentGltf(true)
                                    .build()
                                    .thenAccept(modelRenderable -> addModel(anchor, modelRenderable))
                                    .exceptionally(throwable -> {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                        builder.setMessage("Somthing is not right" + throwable.getMessage()).show();
                                        return null;
                                    });
                        }
                    });
                }
                else if(value == "MODEL")
                {
                    clickNo = 0;
                    arCam.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
                        clickNo++;
                        //the 3d model comes to the scene only when clickNo is one that means once
                        if (clickNo == 1) {
                            Anchor anchor = hitResult.createAnchor();
                            ModelRenderable.builder()
                                    .setSource(MainActivity.this, R.raw.model_1)
                                    .setIsFilamentGltf(true)
                                    .build()
                                    .thenAccept(modelRenderable -> addModel(anchor, modelRenderable))
                                    .exceptionally(throwable -> {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                        builder.setMessage("Somthing is not right" + throwable.getMessage()).show();
                                        return null;
                                    });
                        }
                    });
                }
                else if(value == "MONITER")
                {
                    clickNo = 0;
                    arCam.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
                        clickNo++;
                        //the 3d model comes to the scene only when clickNo is one that means once
                        if (clickNo == 1) {
                            Anchor anchor = hitResult.createAnchor();
                            ModelRenderable.builder()
                                    .setSource(MainActivity.this, R.raw.monitor_1)
                                    .setIsFilamentGltf(true)
                                    .build()
                                    .thenAccept(modelRenderable -> addModel(anchor, modelRenderable))
                                    .exceptionally(throwable -> {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                        builder.setMessage("Somthing is not right" + throwable.getMessage()).show();
                                        return null;
                                    });
                        }
                    });
                }
            }

        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Đã xóa Model thành công", Toast.LENGTH_SHORT).show();
                Intent intent = getIntent();
                finish();
                overridePendingTransition(0, 0);
                startActivity(intent);
            }
        });
    }

    private void addModel(Anchor anchor, ModelRenderable modelRenderable) {

        AnchorNode anchorNode = new AnchorNode(anchor);
        // Creating a AnchorNode with a specific anchor
        anchorNode.setParent(arCam.getArSceneView().getScene());
        //attaching the anchorNode with the ArFragment
        TransformableNode model = new TransformableNode(arCam.getTransformationSystem());
        model.setParent(anchorNode);
        model.setLocalScale(new Vector3(0.1f, 0.1f, 0.1f));
        //attaching the anchorNode with the TransformableNode
        model.setRenderable(modelRenderable);
        //attaching the 3d model with the TransformableNode that is already attached with the node
        model.select();

    }

}