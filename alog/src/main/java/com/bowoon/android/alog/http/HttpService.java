package com.bowoon.android.alog.http;

import android.net.Uri;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bowoon.android.alog.util.TransferType;
import com.bowoon.android.alog.vo.LogVO;

import org.acra.ReportField;
import org.acra.data.CrashReportData;
import org.json.JSONException;
import org.json.JSONObject;

public class HttpService implements HttpServiceList {
    private String makeURL(TransferType type) {
        Uri.Builder uri = new Uri.Builder();
        uri.scheme("http");
//        uri.encodedAuthority(Constant.getAddress() + ":" + Constant.getPort());
        uri.encodedAuthority("192.168.0.7:8080");

        switch (type) {
            case CRASH:
                uri.path("crash");
                break;
            case LOG_DATA:
                uri.path("log-data");
                break;
            default:
                break;
        }

        return uri.build().toString();
    }

    private JSONObject makeLogDataJSON(LogVO data) {
        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject memoryInfo = new JSONObject();
            JSONObject deviceInfo = new JSONObject();

            jsonObject.put("packageName", data.getPackageName());
            jsonObject.put("message", data.getMsg());
            jsonObject.put("tag", data.getTag());
            jsonObject.put("level", data.getLevel());
            jsonObject.put("time", data.getTime());

            if (data.getMemory() != null) {
                memoryInfo.put("totalMemory", data.getMemory().getTotalMemory());
                memoryInfo.put("availMemory", data.getMemory().getAvailMemory());
                memoryInfo.put("memoryPercentage", data.getMemory().getMemoryPercentage());
                memoryInfo.put("threshold", data.getMemory().getThreshold());
                memoryInfo.put("lowMemory", data.getMemory().isLowMemory());
                memoryInfo.put("debugNativeFree", data.getMemory().getDebugNativeFree());
                memoryInfo.put("debugNativeAllocated", data.getMemory().getDebugNativeAllocated());
                memoryInfo.put("debugNativeAvailable", data.getMemory().getDebugNativeAvailable());
                memoryInfo.put("nativeFreeMemory", data.getMemory().getNativeFreeMemory());
                memoryInfo.put("nativeMaxMemory", data.getMemory().getNativeMaxMemory());
                memoryInfo.put("nativeTotalMemory", data.getMemory().getNativeTotalMemory());

                jsonObject.put("memoryInfo", memoryInfo);
            }

            if (data.getBatteryCharge() != null) {
                deviceInfo.put("batteryCharge", data.getBatteryCharge());
            }

            if (data.getBatteryStatus() != null) {
                deviceInfo.put("batteryStatus", data.getBatteryStatus());
            }

            if (data.getLocation() != null) {
                deviceInfo.put("longitude", data.getLocation().getLongitude());
                deviceInfo.put("latitude", data.getLocation().getLatitude());
            }

            jsonObject.put("deviceInfo", deviceInfo);

            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONObject makeCrashDataJSON(CrashReportData report) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("packageName", report.get(String.valueOf(ReportField.PACKAGE_NAME)));
            jsonObject.put("androidVersion", report.get(String.valueOf(ReportField.ANDROID_VERSION)));
            jsonObject.put("appVersionCode", report.get(String.valueOf(ReportField.APP_VERSION_CODE)));
            jsonObject.put("appVersionName", report.get(String.valueOf(ReportField.APP_VERSION_NAME)));
            jsonObject.put("availableMemorySize", report.get(String.valueOf(ReportField.AVAILABLE_MEM_SIZE)));
            jsonObject.put("brand", report.get(String.valueOf(ReportField.BRAND)));
            jsonObject.put("build", report.get(String.valueOf(ReportField.BUILD)));
            jsonObject.put("deviceID", report.get(String.valueOf(ReportField.DEVICE_ID)));
            jsonObject.put("display", report.get(String.valueOf(ReportField.DISPLAY)));
            jsonObject.put("deviceFeatures", report.get(String.valueOf(ReportField.DEVICE_FEATURES)));
            jsonObject.put("environment", report.get(String.valueOf(ReportField.ENVIRONMENT)));
            jsonObject.put("packageName", report.get(String.valueOf(ReportField.PACKAGE_NAME)));
            jsonObject.put("totalMemorySize", report.get(String.valueOf(ReportField.TOTAL_MEM_SIZE)));
            jsonObject.put("applicationLog", report.get(String.valueOf(ReportField.APPLICATION_LOG)));
            jsonObject.put("logcat", report.get(String.valueOf(ReportField.LOGCAT)));
            jsonObject.put("time", System.currentTimeMillis());
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private HttpOption httpOptionSetting(String apiKey) {
        HttpOption option = new HttpOption();

        option.setBodyContentType("application/json");
        option.setContentType("application/json");
        option.setSecretKey(apiKey);

        return option;
    }

    public void requestCrashData(String apiKey, CrashReportData report, final HttpCallback callback) {
        String url = makeURL(TransferType.CRASH);

        HttpOption option = httpOptionSetting(apiKey);
        JSONObject jsonObject = makeCrashDataJSON(report);

        JsonCustomRequest request = new JsonCustomRequest(
                Request.Method.POST,
                url,
                option,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.i("Response", response.get("result").toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VolleyError", error.networkResponse + " " + error.getMessage());
                    }
                });

        request.setRetryPolicy(new DefaultRetryPolicy(2000, 5, 1));

        addRequestQueue(request);
    }

    public void requestLogData(String apiKey, LogVO data, final HttpCallback callback) {
        String url = makeURL(TransferType.LOG_DATA);

        HttpOption option = httpOptionSetting(apiKey);
        JSONObject jsonObject = makeLogDataJSON(data);

        JsonCustomRequest request = new JsonCustomRequest(
                Request.Method.POST,
                url,
                option,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.i("Response", response.get("result").toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VolleyError", error.networkResponse + " " + error.getMessage());
                    }
                });

        request.setRetryPolicy(new DefaultRetryPolicy(2000, 5, 1));

        addRequestQueue(request);
    }

    private void addRequestQueue(JsonCustomRequest request) {
        try {
            VolleyManager.getInstance().getRequestQueue().add(request);
        } catch (IllegalAccessException e) {
            Log.i("IllegalAccessException", e.getMessage());
        }
    }
}