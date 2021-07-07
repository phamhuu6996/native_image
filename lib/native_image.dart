import 'dart:typed_data';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

const String channel = 'native_image';
const String pathMethod = 'edit_image_file';
const String memoryMethod = 'edit_image_memory';

typedef CallMethod = Function(String method, Map<String, dynamic> mapData);

class NativeImage {
  final String path;
  final Uint8List memory;
  final platform = const MethodChannel(channel);

  NativeImage({this.path, this.memory});

  Future<T> run<T>(String method, List<Option> list) async {
    if (path == null) return null;
    List<Map<String, dynamic>> maps = list.map((e) {
      return {'key': e.key, 'value': e.toJson()};
    }).toList();
    try {
      return await platform.invokeMethod<T>(method, {'result': maps, 'path': path});
    } catch (e) {
      if (e is PlatformException) print(e.message);
      return null;
    }
  }

  Future<String> editImageFile(List<Option> list) async {
    return run<String>(pathMethod, list);
  }

  Future<Uint8List> editImageMemory(List<Option> list) async {
    return run<Uint8List>(memoryMethod, list);
  }

  Future<String> editImage(String label, int size) async {
    if (path == null) return null;
    try {
      return await platform
          .invokeMethod<String>(pathMethod, {'result': [], 'path': path, "label": label, "size": size});
    } catch (e) {
      if (e is PlatformException) print(e.message);
      return null;
    }
  }
}

abstract class Option {
  final String key;

  Option(this.key);

  Map<String, dynamic> toJson();
}

class EditTextData extends Option {
  final String label;
  final int size;
  final int textAlign;
  final int gravity;
  final int colorCode;
  final int horPadding;
  final int verPadding;

  EditTextData({
    @required this.label,
    this.size,
    this.textAlign,
    this.gravity,
    this.colorCode,
    this.horPadding,
    this.verPadding,
  }) : super("add_text");

  @override
  Map<String, dynamic> toJson() {
    Map<String, dynamic> mapData = {'label': label};
    if (size != null) mapData['size'] = size;
    if (textAlign != null) mapData['text_align'] = textAlign;
    if (gravity != null) mapData['gravity'] = gravity;
    if (colorCode != null) mapData['color'] = colorCode;
    if (horPadding != null) mapData['x'] = horPadding;
    if (verPadding != null) mapData['y'] = verPadding;
    return mapData;
  }
}
