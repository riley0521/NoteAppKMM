//  AesCipher.swift
//  iosApp
//
//  Created by Riley Farro on 2/15/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import CryptoSwift
import NIOCore

class AesCipher {
    
    let encryptionKey: [UInt8] = "EUREKAREEUREKARE".bytes
    let sqlKey: String = "eurekare_key"
    
    let encoder: JSONEncoder = JSONEncoder()
    
    let hexArray: [Character] = Array("0123456789ABCDEF")
    
    private func bytesToHex() -> String {
        
        let bytes = AES.randomIV(8)
        print("IV COUNT: \(bytes.count)")
        
        var hexChars: [Character] = [Character](repeating: "0", count: 16)
        for (index, value) in bytes.enumerated() {
            let v = Int32(bitPattern: UInt32(value) & 0xFF)
            hexChars[index * 2] = hexArray[Int(UInt32(v) >> UInt32(4))]
            hexChars[index * 2 + 1] = hexArray[Int(UInt32(v) & 0x0F)]
        }
        
        return String(hexChars)
    }
    
    private func encryptor(value: String) throws -> String {
        do {
            
            let initVector = bytesToHex().bytes
            print("VECTOR: \(initVector)")
            
            let cipher = try? AES(key: encryptionKey, blockMode: CBC(iv: initVector), padding: .pkcs5)
            
            guard let cipher else { return "" }
            
            let encrypted = try cipher.encrypt(value.bytes)
            
            let mac = try HMAC(key: encryptionKey, variant: .sha2(.sha256)).authenticate(encrypted)
            print("MAC: \(mac)")
            
            
            let size = initVector.count + mac.count + encrypted.count
            var byteBuffer = ByteBufferAllocator().buffer(capacity: size)
            byteBuffer.writeBytes(initVector)
            byteBuffer.writeBytes(mac)
            byteBuffer.writeBytes(encrypted)
            
            byteBuffer.moveReaderIndex(to: 0)
            
            let result = byteBuffer.readBytes(length: byteBuffer.readableBytes)!
            print("RESULT: \(result.toBase64().count) &&& \(result.count)")
            
            return result.toBase64()
        } catch {
            return ""
        }
    }
    
    private func decryptor(value: String) throws -> String {
        do {
            let encryptedBytes = Data(base64Encoded: value)?.bytes
            var byteBuffer = ByteBuffer(bytes: encryptedBytes!)
            print("BYTE BUFFER: \(byteBuffer.readableBytes)")
            
            let iv = byteBuffer.readBytes(length: 16)!
            print("IV: \(iv.count)")
            
            let mac = byteBuffer.readBytes(length: 32)!
            print("MAC: \(mac.count)")
            
            let cipherText = byteBuffer.readBytes(length: byteBuffer.readableBytes)!
            print("\(cipherText.count) &&& \(byteBuffer.readableBytes)")
            
        
            let cipher = try? AES(key: encryptionKey, blockMode: CBC(iv: iv), padding: .pkcs5)
            
            guard let cipher else { return value }
            
            let plainText = try cipher.decrypt(cipherText)
            
            return String(bytes: plainText, encoding: .utf8)!
        } catch {
            print(error)
            return value
        }
    }
    
    func encrypt(decrypted: String) -> String {
        do {
            return try encryptor(value: decrypted)
        } catch {
            return ""
        }
    }
    
    func decrypt(encrypted: String?) -> String {
        guard let encrypted else { return "" }
        
        do {
            return try decryptor(value: encrypted)
        } catch {
            return encrypted
        }
    }
    
    
}
