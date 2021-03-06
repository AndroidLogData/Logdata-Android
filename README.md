# Android Logdata Android Library
|  | Current Status |
|--------|--------|
| JitPack | [![](https://jitpack.io/v/AndroidLogData/Logdata-Android.svg)](https://jitpack.io/#AndroidLogData/Logdata-Android) |
| Travis-CI | [![Build Status](https://travis-ci.org/AndroidLogData/ALog.svg?branch=develop)](https://travis-ci.org/AndroidLogData/ALog) |
| CircleCI | [![CircleCI](https://circleci.com/gh/AndroidLogData/ALog/tree/develop.svg?style=svg)](https://circleci.com/gh/AndroidLogData/ALog/tree/develop) |

안드로이드 로그 데이터 관리를 위한 안드로이드 라이브러리

## Installation
* build.gradle에 추가
```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

* dependencies 추가
```gradle
dependencies {
    compile 'com.github.AndroidLogData:Logdata-Android:{lasted version}'
}
```

## 사용법
[Example](https://github.com/AndroidLogData/Logdata-Android/tree/develop/example)
* AndroidManifest.xml에 ```meta-data```추가
```xml
<meta-data android:name="com.logcat.apiKey"
	android:value="<InsertYourKey>"/>
```
* Application 진입점에 코드 추가
```java
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
        * setting
        * Logcat을 사용하기 전에 해야할 설정
        *
        * @param Android Context
        * @param Logcat에서 로그를 보기위한 설정
        * @param 서버로 로그 데이터를 보내기 위한 설정
        */
        ALog.logSetting(getApplicationContext(), true, true);
        // 서버로 메모리 데이터도 같이 전송
        ALog.setDebug(true);
    }
}
```

## JSON Structure
[JSON Structure](https://github.com/AndroidLogData/Logdata-Server-Spring/wiki/JSON-Data)

## Development Stack
* Language : Java
* Framework : Android

## 사용된 오픈소스
* Http 전송을 위한 Volley
	* https://github.com/google/volley
* Crash Data 만들기 위한 ACRA
	* https://github.com/ACRA/acra

## License
```
MIT License

Copyright (c) 2018 KimBoWoon

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
