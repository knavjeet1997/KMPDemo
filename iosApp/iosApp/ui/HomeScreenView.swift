import SwiftUI

struct HomeScreenView: View {
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
                Text("Welcome to Home Screen!")
                    .font(.system(size: 28, weight: .bold))
                    .foregroundColor(colorScheme == .dark ? .white : .black)
            }
        }
    }
}

struct HomeScreenView_Previews: PreviewProvider {
    static var previews: some View {
        HomeScreenView()
    }
}
