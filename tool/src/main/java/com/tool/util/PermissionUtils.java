//package com.tool.util;
//
//import android.Manifest;
//import android.app.Activity;
//import android.app.Fragment;
//import android.content.pm.PackageManager;
//import android.os.Build;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
//import android.widget.Toast;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by csf on 2017/7/24.
// * 文件描述
// */
//
//public class PermissionUtils {
//    public static int REQUEST_PHONE_PERMISSIONS = 101;
//    public static int REQUEST_PHONE_WRITE_EXTERNAL_STORAGE = 102;
//    public static int REQUEST_PHONE_CAMERA = 103;
//    public static int REQUEST_PHONE_LOCATION = 104;
//    public static int REQUEST_MORE = 105;
//    public static int REQUEST_MAIKE=106;
//    /**
//     * 从fragment检查权限，如果传入的fragment是null，则后面请求权限时会调用ActivityCompat
//     * 的方法
//     *
//     * 检查6.0及以上版本时，应用是否拥有某个权限，拥有则返回true，未拥有则再判断上次
//     * 用户是否拒绝过该权限的申请（拒绝过则shouldShowRequestPermissionRationale返回
//     * true——这里有些手机如红米(红米 pro)永远返回 false
//     * 这里的处理是弹一个对话框引导用户去应用的设置界面打开权限，返回false时这里执行
//     * requestPermissions方法，此方法会显示系统默认的一个权限授权提示对话框，并在
//     * Activity或Fragment的onRequestPermissionsResult得到回调，注意方法中的requestCode
//     * 要与此处相同）
//     *
//     * @param permission：对应的权限名称，如：Manifest.permission.CAMERA
//     * @param hint：引导用户进入设置界面对话框的提示文字
//     * @param requestCode：请求码，对应Activity或Fragment的onRequestPermissionsResult
//     * 方法的requestCode
//     * @return：true-拥有对应的权限 false：未拥有对应的权限
//     */
//    public static boolean checkPermission(Object obj, String permission,int requestCode,String hint) {
//        //检查权限
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
//            return true;
//        }
//        Activity activity;
//        Fragment fragment = null;
//        if (obj instanceof Fragment){
//            fragment = (Fragment) obj;
//            activity = fragment.getActivity();
//        }else {
//            activity = (Activity) obj;
//        }
//        if (ContextCompat.checkSelfPermission(activity, permission) !=  PackageManager.PERMISSION_GRANTED) {
//            //申请权限
//            if (fragment == null) {
//                ActivityCompat.requestPermissions(activity,
//                        new String[]{permission},
//                        requestCode);
//            } else {
//                fragment.requestPermissions(
//                        new String[]{permission},
//                        requestCode);
//            }
//            Toast.makeText(activity,hint,Toast.LENGTH_SHORT).show();
//            return false;
//        } else {  //已经拥有权限
//            return true;
//        }
//    }
//
//    public static boolean checkPhonePermission(Object obj){
//        return checkPermission(obj, Manifest.permission.CALL_PHONE,REQUEST_PHONE_PERMISSIONS,"请打开电话权限");
//    }
//
//    /**
//     * 手机存储权限
//     * @param obj
//     * @return
//     */
//    public static boolean checkStoragePermission(Object obj){
//        return checkPermission(obj, Manifest.permission.WRITE_EXTERNAL_STORAGE,REQUEST_PHONE_WRITE_EXTERNAL_STORAGE,"请打开存储权限");
//    }
//    /**
//     * 麦克风权限
//     */
//    public static boolean checkAudioPermission(Object obj){
//        return checkPermission(obj, Manifest.permission.RECORD_AUDIO,REQUEST_MAIKE,"请手动打开麦克风权限");
//    }
//    /**
//     * 相机权限
//     * @param obj
//     * @return
//     */
//    public static boolean checkCameraPermission(Object obj){
//        return checkPermission(obj, Manifest.permission.CAMERA,REQUEST_PHONE_CAMERA,"请手动打开相机权限");
//    }
//
//    public static boolean checkLocationPermission(Object obj){
//        return checkPermission(obj, Manifest.permission.ACCESS_COARSE_LOCATION,REQUEST_PHONE_LOCATION,"请手动打开位置权限");
//    }
//
//    public static ArrayList<String> checkMorePermission(Object obj, String[] permissionsArray, int requestCode) {
//        List<String> permissionsList = new ArrayList<String>();
//        //检查权限
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
//            return (ArrayList<String>) permissionsList;
//        }
//        Activity activity;
//        Fragment fragment = null;
//        if (obj instanceof Fragment){
//            fragment = (Fragment) obj;
//            activity = fragment.getActivity();
//        }else {
//            activity = (Activity) obj;
//        }
//        for (String permission : permissionsArray) {
//            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
//                permissionsList.add(permission);
//            }
//        }
//        if (permissionsList.size() != 0){
//            if (fragment == null) {
//                ActivityCompat.requestPermissions(activity,
//                        permissionsList.toArray(new String[permissionsList.size()]),
//                        requestCode);
//            } else {
//                fragment.requestPermissions(
//                        permissionsList.toArray(new String[permissionsList.size()]),
//                        requestCode);
//            }
//        }
//
//        return (ArrayList<String>) permissionsList;
//    }
//}
