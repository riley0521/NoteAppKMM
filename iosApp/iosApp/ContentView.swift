import SwiftUI
import shared

struct ContentView: View {
    @ObservedObject private(set) var viewModel = ViewModel()
    
	var body: some View {
        VStack {
            Text(viewModel.text)
        }
	}
}

extension ContentView {
    class ViewModel: ObservableObject {
        @Published var text = "Loading..."
        
        init() {
            DatabaseModule().accountRepository.returnSomeString { value, error in
                DispatchQueue.main.async {
                    if let value {
                        self.text = value
                    } else {
                        self.text = error?.localizedDescription ?? "Unknown error occurred."
                    }
                }
            }
        }
    }
}
