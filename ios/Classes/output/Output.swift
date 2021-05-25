import Foundation

public class Output{
    static let instance:Output = Output()
    public func getOutputByte(newImage:UIImage)->[UInt8]{
        let data = newImage.jpegData(compressionQuality: 1.0)!
        let byteArray = [UInt8](data)
        return byteArray
        }
    
    public func getOutputString(newImage:UIImage, path:String)->String{
        if let dataImage = newImage.jpegData(compressionQuality: 1.0){
            let url :URL! = URL(fileURLWithPath:path)
            try? dataImage.write(to: url)
            return path
            }
            return ""
        }
}
