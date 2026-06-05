import SwiftUI
import Shared

struct ContentView: View {
    @Environment(\.colorScheme) var colorScheme
    
    // Instantiate the shared logic Calculator
    let calculator = Calculator()
    
    @State private var num1: String = ""
    @State private var num2: String = ""
    @State private var result: String = ""
    
    var body: some View {
        ZStack {
            // Background color managed from AppColors
            AppColors.backgroundColor(for: colorScheme)
                .ignoresSafeArea()
            
            VStack(spacing: 24) {
                Text("iOS Native Calculator")
                    .font(.largeTitle)
                    .fontWeight(.bold)
                    .foregroundColor(AppColors.textColor(for: colorScheme))
                    .padding(.top, 24)
                
                VStack(spacing: 16) {
                    VStack(alignment: .leading, spacing: 8) {
                        Text("Number 1")
                            .font(.caption)
                            .fontWeight(.medium)
                            .foregroundColor(AppColors.secondaryTextColor(for: colorScheme))
                        TextField("Enter first number", text: $num1)
                            .textFieldStyle(RoundedBorderTextFieldStyle())
                            .keyboardType(.decimalPad)
                    }
                    
                    VStack(alignment: .leading, spacing: 8) {
                        Text("Number 2")
                            .font(.caption)
                            .fontWeight(.medium)
                            .foregroundColor(AppColors.secondaryTextColor(for: colorScheme))
                        TextField("Enter second number", text: $num2)
                            .textFieldStyle(RoundedBorderTextFieldStyle())
                            .keyboardType(.decimalPad)
                    }
                }
                .padding()
                .background(AppColors.cardBackgroundColor(for: colorScheme))
                .cornerRadius(12)
                
                HStack(spacing: 16) {
                    Button(action: {
                        calculate(op: "+")
                    }) {
                        Text("+")
                            .font(.title2)
                            .fontWeight(.bold)
                            .frame(maxWidth: .infinity, minHeight: 50)
                            .background(AppColors.primaryColor(for: colorScheme))
                            .foregroundColor(.white)
                            .cornerRadius(12)
                    }
                    
                    Button(action: {
                        calculate(op: "-")
                    }) {
                        Text("-")
                            .font(.title2)
                            .fontWeight(.bold)
                            .frame(maxWidth: .infinity, minHeight: 50)
                            .background(AppColors.primaryColor(for: colorScheme))
                            .foregroundColor(.white)
                            .cornerRadius(12)
                    }
                    
                    Button(action: {
                        calculate(op: "×")
                    }) {
                        Text("×")
                            .font(.title2)
                            .fontWeight(.bold)
                            .frame(maxWidth: .infinity, minHeight: 50)
                            .background(AppColors.primaryColor(for: colorScheme))
                            .foregroundColor(.white)
                            .cornerRadius(12)
                    }
                }
                
                VStack(alignment: .leading, spacing: 8) {
                    Text("Result")
                        .font(.headline)
                        .foregroundColor(AppColors.secondaryTextColor(for: colorScheme))
                    Text(result.isEmpty ? "--" : result)
                        .font(.title)
                        .fontWeight(.semibold)
                        .foregroundColor(AppColors.textColor(for: colorScheme))
                }
                .frame(maxWidth: .infinity, alignment: .leading)
                .padding()
                .background(AppColors.cardBackgroundColor(for: colorScheme))
                .cornerRadius(12)
                
                Spacer()
            }
            .padding()
        }
    }
    
    private func calculate(op: String) {
        let n1 = Double(num1) ?? 0.0
        let n2 = Double(num2) ?? 0.0
        
        switch op {
        case "+":
            result = String(calculator.add(a: n1, b: n2))
        case "-":
            result = String(calculator.sub(a: n1, b: n2))
        case "×":
            result = String(calculator.multiply(a: n1, b: n2))
        default:
            break
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            ContentView()
                .preferredColorScheme(.light)
                .previewDisplayName("Light Mode")
            
            ContentView()
                .preferredColorScheme(.dark)
                .previewDisplayName("Dark Mode")
        }
    }
}