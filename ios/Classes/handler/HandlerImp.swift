import Foundation
public class HandlerImp:IHandler{
    public func runByte(image: UIImage) -> UIImage {
        return UIImage()
    }
    
    public func runFile() -> String {
        return ""
    }
    
    public func handlerImage(options:[Option],image:UIImage)->UIImage {
        var coppyImage:UIImage = image
        for option in options {
            if option is Text {
                let text:Text = option as! Text
                let addText:IHandler = AddTextHandler(text: text)
                coppyImage = addText.runByte(image: coppyImage)
            }
        }
        return coppyImage
    }
}
