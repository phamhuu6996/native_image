import Flutter
import UIKit

public class SwiftNativeImagePlugin: NSObject, FlutterPlugin {
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "native_image", binaryMessenger: registrar.messenger())
    let instance = SwiftNativeImagePlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
    DispatchQueue.global(qos: .userInitiated).async {
        let arg = call.arguments as? [String:Any] ?? ["path":""]
        let pathModify: String = self.getAttribute(arg: arg)
        DispatchQueue.main.async {
            result(pathModify)
        }
    }
  }
    
    public func getAttribute(arg: [String:Any])-> String{
        let path = arg["path"] as? String ?? ""
        let label = arg["label"] as? String ?? ""
        let size = arg["size"] as? Int ?? 30
        
        let text_style=NSMutableParagraphStyle()
           text_style.alignment=NSTextAlignment.right
        let textColor = UIColor.white
        let textFont = UIFont(name: "Helvetica Bold", size: CGFloat(size))!
        let shadow : NSShadow = NSShadow()
        shadow.shadowColor = UIColor.black
        shadow.shadowBlurRadius = 1
        shadow.shadowOffset = CGSize(width: 2, height: 2)
        let textFontAttributes = [
            NSAttributedString.Key.font: textFont,
            NSAttributedString.Key.paragraphStyle:text_style,
            NSAttributedString.Key.foregroundColor: textColor,
            NSAttributedString.Key.shadow:shadow
            ] as [NSAttributedString.Key : Any]
        
        let image = UIImage(contentsOfFile: path)
        let point = CGPoint(x: -10.0, y: 10.0)
        let pathModify = textToImage(text: label, image: image!, point: point, path: path, attribute: textFontAttributes)
        return pathModify;
        
    }

    public func textToImage(text: String, image: UIImage, point: CGPoint, path:String, attribute: [NSAttributedString.Key : Any]) -> String {

        let scale = UIScreen.main.scale
        UIGraphicsBeginImageContextWithOptions(image.size, false, scale)

        image.draw(in: CGRect(origin: CGPoint.zero, size: image.size))

        let rect = CGRect(origin: point, size: image.size)
        text.draw(in: rect, withAttributes: attribute)

        let newImage = UIGraphicsGetImageFromCurrentImageContext()
        UIGraphicsEndImageContext()
        
        if let dataImage = newImage?.jpegData(compressionQuality: 1.0){
            let url :URL! = URL(fileURLWithPath:path)
            try? dataImage.write(to: url)
            return path
            }
            return ""
        }
}
