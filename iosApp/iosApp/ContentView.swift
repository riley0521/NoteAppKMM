import SwiftUI
import shared

struct ContentView: View {
    
    private var accountRepository: AccountRepository
    @ObservedObject var viewModel: ViewModel
    
    init(accountRepository: AccountRepository) {
        self.accountRepository = accountRepository
        self.viewModel = ViewModel(accountRepository: accountRepository)
    }
    
    let cipher = AesCipher()
    
	var body: some View {
        let encrypted = cipher.encrypt(decrypted: "12345678")
        print(encrypted)
        let decrypted = cipher.decrypt(encrypted: "QTM2MUYzMkIzNTRCOUNGM4jeGPKHTw7tdG7xVLdW2f6x6FmPfvDI2dyx7FIm3RgZLJBgfCnW/tXNJOI4vUfqpgzeuwgPxTijaWYlwy7LnAIfAYr38CpiHKkrpPedpb/Q")
        print("DECRYPTED: \(decrypted)")
        
        return VStack {
            Text(viewModel.text)
        }
	}
}

extension ContentView {
    @MainActor class ViewModel: ObservableObject {
        
        private var accountRepository: AccountRepository
        
        @Published var text = "Loading..."
        
        init(accountRepository: AccountRepository) {
            self.accountRepository = accountRepository
            
            accountRepository.returnSomeString(completionHandler: { value, error in
                if let value {
                    self.text = value
                } else {
                    self.text = error?.localizedDescription ?? "Unknown error occurred."
                }
            })
        }
    }
}
