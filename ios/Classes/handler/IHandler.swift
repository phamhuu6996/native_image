import Foundation

public protocol IHandler {
    func runByte(image:UIImage)->UIImage
    func runFile()->String
}
