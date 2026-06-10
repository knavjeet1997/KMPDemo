import SwiftUI
import Shared

struct SplashView: View {
    let onNavigateToHome: () -> Void
    let onNavigateToLogin: () -> Void
    @Environment(\.colorScheme) var colorScheme
    @State private var scale: CGFloat = 0.5
    @State private var alpha: Double = 0.0

    private let viewModel: SplashViewModel = KoinHelper().getSplashViewModel()

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

            VStack(spacing: 15) {
                Text("TRACK NINJA")
                    .font(.system(size: 42, weight: .bold))
                    .foregroundColor(.orange)
                    .scaleEffect(scale)
                    .opacity(alpha)

                Text("Secure & Simple Tracking")
                    .font(.system(size: 16))
                    .foregroundColor(.gray)
                    .opacity(alpha)

                ProgressView()
                    .progressViewStyle(CircularProgressViewStyle(tint: .orange))
                    .scaleEffect(1.5)
                    .padding(.top, 40)
            }
        }
        .onAppear {
            withAnimation(.easeOut(duration: 0.8)) {
                self.scale = 1.0
                self.alpha = 1.0
            }

            DispatchQueue.main.asyncAfter(deadline: .now() + 1.5) {
                if viewModel.isUserLoggedIn() {
                    onNavigateToHome()
                } else {
                    onNavigateToLogin()
                }
            }
        }
    }
}
