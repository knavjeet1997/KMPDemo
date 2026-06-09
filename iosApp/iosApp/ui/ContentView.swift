import SwiftUI
import Shared

struct ContentView: View {
    @Environment(\.colorScheme) var colorScheme
    
    // Instantiate the shared logic Calculator
    private let calculator = Calculator()
    
    @State private var display: String = "0"
    @State private var expression: String = ""
    
    // Theme helper colors
    private var numberBg: Color {
        colorScheme == .dark ? Color(red: 0.2, green: 0.2, blue: 0.2) : Color.white
    }
    
    private var numberFg: Color {
        colorScheme == .dark ? Color.white : Color(red: 0.1, green: 0.1, blue: 0.1)
    }
    
    private var functionBg: Color {
        colorScheme == .dark ? Color(red: 0.65, green: 0.65, blue: 0.65) : Color(red: 0.85, green: 0.85, blue: 0.85)
    }
    
    private var functionFg: Color {
        Color.black
    }
    
    private var operatorBg: Color {
        Color.orange
    }
    
    private var operatorFg: Color {
        Color.white
    }
    
    var body: some View {
        ZStack {
            AppColors.backgroundColor(for: colorScheme)
                .ignoresSafeArea()
            
            VStack(spacing: 12) {
                Spacer()
                
                // Display Area
                VStack(alignment: .trailing, spacing: 8) {
                    if !expression.isEmpty {
                        Text(expression)
                            .font(.system(size: 24, weight: .regular))
                            .foregroundColor(AppColors.secondaryTextColor(for: colorScheme))
                            .lineLimit(1)
                    }
                    Text(display)
                        .font(.system(size: display.count > 12 ? 36 : (display.count > 8 ? 48 : 64), weight: .bold))
                        .foregroundColor(AppColors.textColor(for: colorScheme))
                        .lineLimit(1)
                        .minimumScaleFactor(0.5)
                }
                .frame(maxWidth: .infinity, alignment: .trailing)
                .padding(.horizontal, 24)
                .padding(.bottom, 20)
                .contentShape(Rectangle())
                .gesture(
                    DragGesture(minimumDistance: 20, coordinateSpace: .local)
                        .onEnded { value in
                            if abs(value.translation.width) > 50 {
                                calculator.backspace()
                                syncState()
                            }
                        }
                )
                
                // Keypad Area
                VStack(spacing: 12) {
                    // Row 1
                    HStack(spacing: 12) {
                        let isClearAll = display == "0" && expression.isEmpty
                        CalculatorButton(
                            text: isClearAll ? "AC" : "C",
                            action: { calculator.clear(); syncState() },
                            backgroundColor: functionBg,
                            textColor: functionFg
                        )
                        CalculatorButton(
                            text: "±",
                            action: { calculator.toggleSign(); syncState() },
                            backgroundColor: functionBg,
                            textColor: functionFg
                        )
                        CalculatorButton(
                            text: "%",
                            action: { calculator.percentage(); syncState() },
                            backgroundColor: functionBg,
                            textColor: functionFg
                        )
                        CalculatorButton(
                            text: "÷",
                            action: { calculator.inputOperator(op: "÷"); syncState() },
                            backgroundColor: operatorBg,
                            textColor: operatorFg
                        )
                    }
                    
                    // Row 2
                    HStack(spacing: 12) {
                        CalculatorButton(
                            text: "7",
                            action: { calculator.inputDigit(digit: "7"); syncState() },
                            backgroundColor: numberBg,
                            textColor: numberFg
                        )
                        CalculatorButton(
                            text: "8",
                            action: { calculator.inputDigit(digit: "8"); syncState() },
                            backgroundColor: numberBg,
                            textColor: numberFg
                        )
                        CalculatorButton(
                            text: "9",
                            action: { calculator.inputDigit(digit: "9"); syncState() },
                            backgroundColor: numberBg,
                            textColor: numberFg
                        )
                        CalculatorButton(
                            text: "×",
                            action: { calculator.inputOperator(op: "×"); syncState() },
                            backgroundColor: operatorBg,
                            textColor: operatorFg
                        )
                    }
                    
                    // Row 3
                    HStack(spacing: 12) {
                        CalculatorButton(
                            text: "4",
                            action: { calculator.inputDigit(digit: "4"); syncState() },
                            backgroundColor: numberBg,
                            textColor: numberFg
                        )
                        CalculatorButton(
                            text: "5",
                            action: { calculator.inputDigit(digit: "5"); syncState() },
                            backgroundColor: numberBg,
                            textColor: numberFg
                        )
                        CalculatorButton(
                            text: "6",
                            action: { calculator.inputDigit(digit: "6"); syncState() },
                            backgroundColor: numberBg,
                            textColor: numberFg
                        )
                        CalculatorButton(
                            text: "−",
                            action: { calculator.inputOperator(op: "−"); syncState() },
                            backgroundColor: operatorBg,
                            textColor: operatorFg
                        )
                    }
                    
                    // Row 4
                    HStack(spacing: 12) {
                        CalculatorButton(
                            text: "1",
                            action: { calculator.inputDigit(digit: "1"); syncState() },
                            backgroundColor: numberBg,
                            textColor: numberFg
                        )
                        CalculatorButton(
                            text: "2",
                            action: { calculator.inputDigit(digit: "2"); syncState() },
                            backgroundColor: numberBg,
                            textColor: numberFg
                        )
                        CalculatorButton(
                            text: "3",
                            action: { calculator.inputDigit(digit: "3"); syncState() },
                            backgroundColor: numberBg,
                            textColor: numberFg
                        )
                        CalculatorButton(
                            text: "+",
                            action: { calculator.inputOperator(op: "+"); syncState() },
                            backgroundColor: operatorBg,
                            textColor: operatorFg
                        )
                    }
                    
                    // Row 5
                    HStack(spacing: 12) {
                        CalculatorButton(
                            text: "0",
                            action: { calculator.inputDigit(digit: "0"); syncState() },
                            backgroundColor: numberBg,
                            textColor: numberFg,
                            isCapsule: true
                        )
                        CalculatorButton(
                            text: ".",
                            action: { calculator.inputDecimal(); syncState() },
                            backgroundColor: numberBg,
                            textColor: numberFg
                        )
                        CalculatorButton(
                            text: "=",
                            action: { calculator.calculate(); syncState() },
                            backgroundColor: operatorBg,
                            textColor: operatorFg
                        )
                    }
                }
                .padding(.horizontal, 16)
                .padding(.bottom, 24)
            }
        }
    }
    
    private func syncState() {
        display = calculator.displayValue
        expression = calculator.expressionValue
    }
}

struct CalculatorButton: View {
    let text: String
    let action: () -> Void
    let backgroundColor: Color
    let textColor: Color
    var isCapsule: Bool = false
    
    var body: some View {
        Button(action: action) {
            Text(text)
                .font(.system(size: text.count > 2 ? 22 : 30, weight: .semibold))
                .foregroundColor(textColor)
                .frame(maxWidth: isCapsule ? .infinity : 75, minHeight: 75, maxHeight: 75)
                .background(backgroundColor)
                .cornerRadius(37.5)
        }
        .buttonStyle(PressedButtonStyle())
    }
}

struct PressedButtonStyle: ButtonStyle {
    func makeBody(configuration: Configuration) -> some View {
        configuration.label
            .scaleEffect(configuration.isPressed ? 0.92 : 1.0)
            .animation(.easeOut(duration: 0.1), value: configuration.isPressed)
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
