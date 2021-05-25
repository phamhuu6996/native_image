import Foundation

class ConvertUntil{
    public static var convertUntil : ConvertUntil = ConvertUntil()
    static let addText:String = "add_text"
    
    public func getPath(arg: [String:Any])->String{
        return arg["path"] as? String ?? ""
    }
    
    public func getOptions(arg: [String:Any])-> [Option]{
        let mapList:[[String:Any]] = arg["result"] as! [[String:Any]]
        var optionList:[Option] = []
        for element in mapList {
            let key = element["key"] as? String ?? ""
            let value  = element["value"] as? [String:Any] ?? ["":""]
            switch key {
            case ConvertUntil.addText:
                optionList.append(convertToText(value: value))
                break
            default:
                break
            }
        }
        return optionList
    }
    
    private func convertToText(value:[String:Any]) ->Text{
        let label:String = value["label"] as? String ?? ""
        let size:Int = value["size"] as? Int ?? Text.size
        let textAlign:NSTextAlignment  = Text.setAlignText(align: value["text_align"] as? Int ?? Text.left)
        let gravity:Int = value["gravity"] as? Int ?? Text.left
        let color:String = value["color"] as? String ?? Text.color
        let x:Int = value["x"] as? Int ?? Text.horPadding
        let y:Int = value["y"] as? Int ?? Text.verPadding
        let text:Text = Text(label: label, sizeText: size, textAlign: textAlign, garvity: gravity, colorText: color, horPadding: x, verPadding: y)
        return text
    }
    
    public func getImageUi(path:String)->UIImage{
        return UIImage(contentsOfFile: path)!
    }
}
