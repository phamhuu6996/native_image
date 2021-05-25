import Foundation
class AddTextHandler:HandlerImp{
    let text:Text
    internal init(text: Text) {
        self.text = text
    }
    public func getAttribute(path:String, label:String, text:Text, image: UIImage)-> UIImage{
        
        let textStyle=NSMutableParagraphStyle()
        textStyle.alignment=text.textAlign
        let textColor = UIColor.init(ciColor: CIColor.init(string: text.colorText))
        let textFont = UIFont(name: "Helvetica Bold", size: CGFloat(text.sizeText))!
        let shadow : NSShadow = NSShadow()
        shadow.shadowColor = UIColor.black
        shadow.shadowBlurRadius = 1
        shadow.shadowOffset = CGSize(width: 2, height: 2)
        let textFontAttributes = [
            NSAttributedString.Key.font: textFont,
            NSAttributedString.Key.paragraphStyle:textStyle,
            NSAttributedString.Key.foregroundColor: textColor,
            NSAttributedString.Key.shadow:shadow
            ] as [NSAttributedString.Key : Any]
        let point = CGPoint(x: CGFloat(text.horPadding) , y:CGFloat(text.verPadding) )
        let newImage = textToImage(text: label, image: image, point: point, path: path, attribute: textFontAttributes)
        return newImage
    }
    
    public func textToImage(text: String, image: UIImage, point: CGPoint, path:String, attribute: [NSAttributedString.Key : Any]) -> UIImage {

        let scale = UIScreen.main.scale
        UIGraphicsBeginImageContextWithOptions(image.size, false, scale)

        image.draw(in: CGRect(origin: CGPoint.zero, size: image.size))

        let rect = CGRect(origin: point, size: image.size)
        text.draw(in: rect, withAttributes: attribute)

        let newImage = UIGraphicsGetImageFromCurrentImageContext()
        UIGraphicsEndImageContext()
        
        return newImage!
    }
}
