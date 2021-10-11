package com.phamhuu.native_image;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import com.phamhuu.native_image.handler.HandlerImp;
import com.phamhuu.native_image.option.Option;
import com.phamhuu.native_image.output.Output;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/**
 * NativeImagePlugin
 */
public class NativeImagePlugin implements FlutterPlugin, MethodCallHandler {
    private MethodChannel channel;
    private ExecutorService poolExecutor;
    private Handler uiThreadHandler;
    private static final String CHANNEL = "native_image";
    private static final String EDIT_IMAGE_FILE = "edit_image_file";
    private static final String EDIT_IMAGE_MEMORY = "edit_image_memory";

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), CHANNEL);
        channel.setMethodCallHandler(this);
        poolExecutor = Executors.newCachedThreadPool();
        uiThreadHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void onMethodCall(@NonNull final MethodCall call, @NonNull final Result result) {
        poolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                editImage(call, result);
            }
        });
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
    }

    private void editImage(MethodCall call, final MethodChannel.Result result) {
        ConvertUntil convertUntil = ConvertUntil.instance;
        try {
            switch (call.method) {
                case EDIT_IMAGE_FILE:
                    editImageFile(result, convertUntil.getOptions(call), convertUntil.getPath(call));
                    break;
                case EDIT_IMAGE_MEMORY:
                    editImageMemory(result, convertUntil.getOptions(call), convertUntil.getPath(call));
                    break;
                default:
                    break;
            }
        } catch (final Throwable throwable) {
            uiThreadHandler.post(() -> result.error("400", throwable.getMessage() != null ? throwable.getMessage() : "Error", ""));
        }
    }

    private void editImageFile(final MethodChannel.Result result, List<Option> options, String path) throws Throwable {
        if (options != null) {
            ConvertUntil.instance.replaceRotateWithExif(ConvertUntil.instance.getDegreeExif(path), options);
            Bitmap bitmap = ConvertUntil.instance.getBitMapPath(path);
            bitmap = new HandlerImp().handlerBitMap(options, bitmap);
            final String pathModify = Output.instance.getOutputFile(bitmap, path);
            uiThreadHandler.post(() -> result.success(pathModify));
        } else throw new NoSuchFieldException();
    }

    private void editImageMemory(final MethodChannel.Result result, List<Option> options, String path) throws Throwable {
        if (options != null) {
            ConvertUntil.instance.replaceRotateWithExif(ConvertUntil.instance.getDegreeExif(path), options);
            Bitmap bitmap = ConvertUntil.instance.getBitMapPath(path);
            bitmap = new HandlerImp().handlerBitMap(options, bitmap);
            final byte[] bytes = Output.instance.getOutputByte(bitmap);
            uiThreadHandler.post(() -> result.success(bytes));
        } else throw new NoSuchFieldException();
    }

}

