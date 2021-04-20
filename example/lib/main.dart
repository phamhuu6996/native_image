import 'dart:io';

import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:native_image/native_image.dart';
import 'package:http/http.dart' as http;
import 'package:path_provider/path_provider.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion;

  @override
  void initState() {
    super.initState();
    initEditImage();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initEditImage() async {
    String pathModify;
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      var response = await http.get('https://img.17qq.com/images/gqqwgqwsqky.jpeg');
      final documentDirectory = await getApplicationDocumentsDirectory();

      final file = File(documentDirectory.path +'/imagetest.png');

       file.writeAsBytesSync(response.bodyBytes);

      pathModify =await NativeImage(path: file.path).editImage("pham van hữu \n tôi la ai \n anncn\n jks", 30);
      imageCache.evict(FileImage(File(pathModify)));

    } on PlatformException {
      pathModify = 'Failed to get platform version.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _platformVersion = pathModify;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Container(
          child:_platformVersion!=null?Image.file(File(_platformVersion), width: 1000):CircularProgressIndicator())
      ),
    );
  }
}
