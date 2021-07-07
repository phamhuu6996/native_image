import Foundation
class AddTextHandler:HandlerImp{
    let text:Text
    internal init(text: Text) {
        self.text = text
    }
    
    override func runByte(image: UIImage) -> UIImage {
        return self.getAttribute( text: self.text, image: image)
    }
    
    public func getAttribute(text:Text, image: UIImage)-> UIImage{
        
        let textStyle=NSMutableParagraphStyle()
        textStyle.alignment=text.textAlign
        let textColor = UIColor(rgb: text.colorText)
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
        let newImage = textToImage(text: text.label, image: image, point: point, attribute: textFontAttributes)
        return newImage
    }
    
    public func textToImage(text: String, image: UIImage, point: CGPoint, attribute: [NSAttributedString.Key : Any]) -> UIImage {
        UIGraphicsBeginImageContextWithOptions(image.size, false, 1)
        image.draw(in: CGRect(origin: CGPoint.zero, size: image.size))

        let rect = CGRect(origin: point, size: image.size)
        text.draw(in: rect, withAttributes: attribute)

        let newImage = UIGraphicsGetImageFromCurrentImageContext()
        UIGraphicsEndImageContext()
        
        return newImage!
    }
}
