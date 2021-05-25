
import Foundation
class Text: Option{
    public init(label: String, sizeText: Int, textAlign: NSTextAlignment, garvity: Int, colorText: Int, horPadding: Int, verPadding: Int) {
        self.label = label
        self.sizeText = sizeText
        self.textAlign = textAlign
        self.garvity = garvity
        self.colorText = colorText
        self.horPadding = horPadding
        self.verPadding = verPadding
    }
    
    var label: String = ""
    var sizeText: Int
    var textAlign: NSTextAlignment
    var garvity: Int
    var colorText: Int
    var horPadding:Int
    var verPadding:Int
    
    static func setAlignText(align:Int)->NSTextAlignment{
        switch align {
        case center:
            return NSTextAlignment.center
        case right:
            return NSTextAlignment.right
        default :
        return NSTextAlignment.left
        }
    }
    
}
