import Flutter
import UIKit

public class SwiftNativeImagePlugin: NSObject, FlutterPlugin {
    private static let channel = "native_image"
    private static let imageFile = "edit_image_file"
    private static let imageMemory = "edit_image_memory"
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "native_image", binaryMessenger: registrar.messenger())
    let instance = SwiftNativeImagePlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
    DispatchQueue.global(qos: .userInitiated).async {
        let convertUntil = ConvertUntil.convertUntil
        let arg = call.arguments as! [String : Any]
        switch call.method {
        case SwiftNativeImagePlugin.imageMemory:
            break
        default :
            let pathModify:String = self.editImageFile(options: convertUntil.getOptions(arg: arg), path: convertUntil.getPath(arg: arg))
            DispatchQueue.main.async {
                result(pathModify)
            }
            break
        }
    }
  }
    
    private func editImageFile(options:[Option], path:String)->String{
        var image: UIImage = ConvertUntil.convertUntil.getImageUi(path: path)
        image = HandlerImp().handlerImage(options: options, image: image)
        let pathModify:String = Output.instance.getOutputString(newImage: image, path: path)
        return pathModify
    }
    
    private func editImageMemory(options:[Option], path:String) -> String{
        return ""
    }
}
