import SwiftUI
import shared

struct ContentView: View {
    
    private var accountRepository: AccountRepository
    @ObservedObject var viewModel: ViewModel
    
    init(accountRepository: AccountRepository) {
        self.accountRepository = accountRepository
        self.viewModel = ViewModel(accountRepository: accountRepository)
    }
    
	var body: some View {
        VStack {
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
