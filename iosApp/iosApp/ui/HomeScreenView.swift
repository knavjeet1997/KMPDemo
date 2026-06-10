import SwiftUI

struct HomeScreenView: View {
    let onLogout: () -> Void
    @Environment(\.colorScheme) var colorScheme

    private var gradientStart: Color {
        colorScheme == .dark ? Color(red: 13.0/255.0, green: 27.0/255.0, blue: 42.0/255.0) : Color(red: 227.0/255.0, green: 242.0/255.0, blue: 253.0/255.0)
    }

    private var gradientEnd: Color {
        colorScheme == .dark ? Color(red: 18.0/255.0, green: 18.0/255.0, blue: 18.0/255.0) : Color(red: 249.0/255.0, green: 251.0/255.0, blue: 252.0/255.0)
    }

    var body: some View {
        ZStack {
            LinearGradient(
                gradient: Gradient(colors: [gradientStart, gradientEnd]),
                startPoint: .top,
                endPoint: .bottom
            )
            .ignoresSafeArea()

            VStack {
                HStack {
                    Spacer()
                    Button(action: {
                        onLogout()
                    }) {
                        Text("Log Out")
                            .font(.system(size: 14, weight: .medium))
                            .foregroundColor(.red)
                            .padding(.horizontal, 12)
                            .padding(.vertical, 8)
                            .background(Color.red.opacity(0.2))
                            .cornerRadius(8)
                    }
                    .padding(.top, 20)
                    .padding(.trailing, 20)
                }

                Spacer()

                Text("Welcome to Home Screen!")
                    .font(.system(size: 28, weight: .bold))
                    .foregroundColor(colorScheme == .dark ? .white : .black)

                Spacer()
            }
        }
    }
}
